package com.example.graffitiwall.web.dto.image;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadImageDto {
    private String title;
    private String url;
    private Long userId;
    private Long boardId;
    private MultipartFile file;
}