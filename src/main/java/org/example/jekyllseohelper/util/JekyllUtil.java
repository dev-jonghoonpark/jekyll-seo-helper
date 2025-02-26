package org.example.jekyllseohelper.util;

import org.example.jekyllseohelper.dto.PostInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JekyllUtil {

    public static void updatePost(String filename, PostInfo postInfo) throws IOException {
        Path filePath = Path.of(filename);
        String content = Files.readString(filePath);

        // 2번째 `---` 이후부터 content 시작
        int index = content.indexOf("---", 3);
        if (index != -1) {
            content = YamlUtil.toYamlString(postInfo) +
                    content.substring(index);
        }

        // 새로 덮어쓰기
        Files.write(filePath, content.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

}
