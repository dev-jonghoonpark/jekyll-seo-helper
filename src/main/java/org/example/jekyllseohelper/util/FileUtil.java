package org.example.jekyllseohelper.util;

import java.io.File;

public class FileUtil {

    public static String currentPath() {
        return new File("").getAbsoluteFile().getAbsolutePath();
    }

    public static String parentPath() {
        return new File("").getAbsoluteFile().getParentFile().getAbsolutePath();
    }

}
