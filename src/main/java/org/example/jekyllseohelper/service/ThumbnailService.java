package org.example.jekyllseohelper.service;

import org.example.jekyllseohelper.util.DownloadUtil;
import org.example.jekyllseohelper.util.DrawUtil;
import org.example.jekyllseohelper.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ThumbnailService {

    public void createThumbnail(String postAbsoluteFilePath, String title) throws IOException {
        DownloadUtil.downloadRandomImage();
        DrawUtil.draw(title);

        String filename = new File(postAbsoluteFilePath).getName();
        String filenameWithoutExt = filename.substring(0, filename.lastIndexOf("."));

        String currentThumbnailPath = String.format("%s/thumbnail.jpg", FileUtil.currentPath());
        String newThumbnailPath = String.format("%s/tech-blog/assets/thumbnails/%s.jpg", FileUtil.parentPath(), filenameWithoutExt);
        Files.copy(
                Path.of(currentThumbnailPath),
                Path.of(newThumbnailPath)
        );
    }
}
