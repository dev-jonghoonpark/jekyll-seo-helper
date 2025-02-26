package org.example.jekyllseohelper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties
public class PostInfo {
    private String layout;
    private String title;
    private String description;
    private String[] categories;
    private String[] tags;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private ZonedDateTime date;
    private Boolean toc;
    private Image image;

    public record Image(String path) {}

    public void update(String filename, String description) {
        this.description = description;
        if (this.toc == null) {
            this.toc = true;
        }
        this.image = new Image(String.format("/assets/thumbnails/%s.jpg", filename.substring(filename.lastIndexOf("/") + 1, filename.lastIndexOf("."))));
    }
}


