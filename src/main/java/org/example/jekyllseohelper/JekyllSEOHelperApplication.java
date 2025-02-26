package org.example.jekyllseohelper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jekyllseohelper.dto.PostInfo;
import org.example.jekyllseohelper.service.DescriptionService;
import org.example.jekyllseohelper.service.ThumbnailService;
import org.example.jekyllseohelper.util.JekyllUtil;
import org.example.jekyllseohelper.util.YamlUtil;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
@SpringBootApplication
@RegisterReflectionForBinding({PostInfo.class, OpenAiChatOptions.class})
public class JekyllSEOHelperApplication implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final DescriptionService descriptionService;
    private final ThumbnailService thumbnailService;

    public static void main(String[] args) {
        SpringApplication.run(JekyllSEOHelperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug(Arrays.toString(args));
        Arrays.stream(args).filter((filename) -> filename.startsWith("/_posts/"))
                .map((filename) -> filename.substring(1))
                .forEach((filename) -> {
                    try {
                        PostInfo postInfo = YamlUtil.getPostInfo(filename);
                        log.debug(postInfo.toString());

                        String description;
                        if (postInfo.getDescription() == null) {
                            description = descriptionService.createDescription(filename);
                        } else {
                            description = postInfo.getDescription();
                        }

                        if (postInfo.getImage() == null) {
                            thumbnailService.createThumbnail(filename, postInfo.getTitle());
                        }

                        postInfo.update(filename, description);

                        JekyllUtil.updatePost(filename, postInfo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        SpringApplication.exit(applicationContext, () -> 0);
    }

}