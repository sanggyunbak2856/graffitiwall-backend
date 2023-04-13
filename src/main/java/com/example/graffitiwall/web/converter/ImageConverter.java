package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Image;
import com.example.graffitiwall.web.dto.image.ImageDto;
import com.example.graffitiwall.web.dto.image.UploadImageDto;

public class ImageConverter {

    public Image imagePostDtoToEntity(UploadImageDto imageDto) {
        return Image.builder()
                .title(imageDto.getTitle())
                .build();
    }

    public ImageDto entityToImageResponseDto(Image img){
        return ImageDto.builder()
                .title(img.getTitle())
                .url(img.getS3Url())
                .build();
    }
}
