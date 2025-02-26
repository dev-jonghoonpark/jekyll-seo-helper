package org.example.jekyllseohelper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.example.jekyllseohelper.dto.PostInfo;

import java.io.File;
import java.io.IOException;

public class YamlUtil {

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    static {
        mapper.findAndRegisterModules();
    }

    public static PostInfo getPostInfo(String filename) throws IOException {
        return mapper.readValue(new File(filename), PostInfo.class);
    }

    public static String toYamlString(PostInfo postInfo) throws IOException {
        return mapper.writeValueAsString(postInfo);
    }

}
