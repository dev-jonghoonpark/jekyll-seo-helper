package org.example.jekyllseohelper.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
public class DrawUtil {

    public static void draw(String title) {
        try {
            // 이미지 로드
            BufferedImage backgroundImage = ImageIO.read(new File(String.format("%s/downloaded_image.jpg", FileUtil.currentPath())));

            // 배경 이미지 크기 설정
            int width = 1200;
            int height = 630;
            BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = thumbnail.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // 배경 이미지 그리기 및 투명도 설정
            g2d.drawImage(backgroundImage, 0, 0, width, height, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            // 텍스트 속성 설정
            g2d.setFont(new Font("Pretendard", Font.BOLD, 64));
            g2d.setColor(Color.WHITE);

            // 텍스트 줄바꿈
            FontRenderContext frc = g2d.getFontRenderContext();
            String[] lines = wrapText(title, g2d.getFont(), width - 40, frc);

            // 텍스트 중앙 정렬 및 그리기
            int lineHeight = g2d.getFontMetrics().getHeight();
            int lineSpacing = 10; // 줄 간격 추가
            int totalHeight = (lineHeight + lineSpacing) * lines.length - lineSpacing; // 줄 간격 고려
            int y = (height - totalHeight) / 2 + 52;

            for (String line : lines) {
                GlyphVector gv = g2d.getFont().createGlyphVector(frc, line);
                Shape outline = gv.getOutline();

                // 텍스트 중앙 정렬 계산
                Rectangle bounds = outline.getBounds();
                int x = (width - bounds.width) / 2;

                // 외곽선 설정
                g2d.translate(x, y);
                g2d.setStroke(new BasicStroke(4f));
                g2d.setColor(Color.BLACK);
                g2d.draw(outline); // 외곽선 그리기

                // 텍스트 그리기
                g2d.setColor(Color.WHITE);
                g2d.fill(outline);

                g2d.translate(-x, -y); // 위치 초기화
                y += lineHeight + lineSpacing; // 줄 간격 추가
            }

            g2d.dispose();

            // 이미지 저장
            ImageIO.write(thumbnail, "png", new File("thumbnail.jpg"));
            log.debug("Thumbnail created successfully!");

        } catch (Exception e) {
            log.error("failed to draw", e);
        }
    }

    private static String[] wrapText(String text, Font font, int maxWidth, FontRenderContext frc) {
        String[] words = text.split(" ");
        StringBuilder lineBuilder = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            String testLine = !lineBuilder.isEmpty() ? lineBuilder + " " + word : word; // 공백 포함
            Rectangle2D bounds = font.getStringBounds(testLine, frc);

            if (bounds.getWidth() > maxWidth) {
                // 이전 줄을 저장하고 공백 유지
                lines.add(lineBuilder.toString().trim());
                lineBuilder = new StringBuilder(word); // 새 줄 시작
                if (lines.size() == 3) { // 최대 3줄 제한
                    lines.set(2, lines.get(2) + "...");
                    break;
                }
            } else {
                // 공백을 포함한 단어 조합
                if (!lineBuilder.isEmpty()) lineBuilder.append(" ");
                lineBuilder.append(word);
            }
        }

        if (!lineBuilder.isEmpty()) {
            lines.add(lineBuilder.toString().trim());
        }

        return lines.toArray(new String[0]);
    }
}
