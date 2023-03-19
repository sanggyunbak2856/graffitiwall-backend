package com.example.graffitiwall.web.dto.board;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardSaveDto {
    private String title;
    private String category;
    private boolean isPrivate;
    private String password;
    private Long userId;
}
