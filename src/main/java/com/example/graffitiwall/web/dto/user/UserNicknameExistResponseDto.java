package com.example.graffitiwall.web.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserNicknameExistResponseDto {
    boolean nicknameExist;
}
