package com.example.graffitiwall.web.dto.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
    private String title;
    private String url;
    private Long userId;
    private Long boardId;
    private S3FileDto s3FileDto;
}
