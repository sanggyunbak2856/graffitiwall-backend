package com.example.graffitiwall.web.dto.postit;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
public class PostitResponseDto {
    private Long postitId;
    private String title;
    private String contents;
    private String color;
    private int views;
    private int positionX;
    private int positionY;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int angle;
    private Long boardId;
    private Long userId;
    private String writer;
}
