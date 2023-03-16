package com.example.graffitiwall.web.dto.postit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostitSaveAndUpdateDto {
    private String title;
    private String content;
    private String color;
    private int positionX;
    private int positionY;
    private int angle;
    private Long boardId;
}
