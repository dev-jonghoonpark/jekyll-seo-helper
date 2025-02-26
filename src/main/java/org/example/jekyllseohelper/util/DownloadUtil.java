package org.example.jekyllseohelper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class DownloadUtil {

    public static void downloadRandomImage() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 다운로드할 이미지 URL
        String imageUrl = "https://picsum.photos/1200/630";
        String savePath = "downloaded_image.jpg";

        CompletableFuture<Void> downloadFuture = CompletableFuture.supplyAsync(() -> downloadImage(imageUrl), executor)
                .thenAccept(bytes -> saveImage(bytes, savePath))
                .exceptionally(e -> {
                    log.error("Error occurred", e);
                    return null;
                });

        downloadFuture.join();
        executor.shutdown();
    }

    private static byte[] downloadImage(String imageUrl) {
        try (InputStream inputStream = URI.create(imageUrl).toURL().openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download image", e);
        }
    }

    private static void saveImage(byte[] imageBytes, String savePath) {
        try {
            Files.write(Paths.get(savePath), imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
