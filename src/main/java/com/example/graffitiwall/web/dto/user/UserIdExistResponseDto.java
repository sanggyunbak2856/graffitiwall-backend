package com.example.graffitiwall.web.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIdExistResponseDto {
    private boolean idExist;
}
