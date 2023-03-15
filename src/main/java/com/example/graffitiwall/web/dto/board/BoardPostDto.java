package com.example.graffitiwall.web.dto.board;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardPostDto {
    private String title;
    private String category;
    private boolean isPrivate;
    private String password;
}
