package org.example.jekyllseohelper.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@AllArgsConstructor
public class DescriptionService {

    private ChatModel chatModel;

    public String createDescription(String postAbsoluteFilePath) throws IOException {
        Path filepath = Paths.get(postAbsoluteFilePath);
        String content = Files.readString(filepath);
        String prompt = "SEO 를 위한 description 내용을 작성하려고합니다. `---` 아래 내용을 한문단으로 요약해주세요.\n" +
                "바로 description 으로 적용할 수 있도록 불필요한 말은 하지 말아주세요.\n" +
                "기본적으로 한글로 요약해주세요. 다만 본문이 영어일 경우에는 영어로 요약해주세요. \n" +
                "---\n" +
                content;

        ChatResponse response = chatModel.call(
                new Prompt(prompt,
                        OpenAiChatOptions.builder()
                                .model(OpenAiApi.ChatModel.GPT_4_O_MINI)
                                .temperature(0.4)
                                .build()
                ));

        return response.getResult().getOutput().getText();
    }
}
