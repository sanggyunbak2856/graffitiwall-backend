package com.example.graffitiwall.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdResponseDto {
    public Long id;

    @Builder
    public IdResponseDto(Long id) {
        this.id = id;
    }
}
