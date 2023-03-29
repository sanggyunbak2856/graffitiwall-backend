package com.example.graffitiwall.web.dto.user;

import com.example.graffitiwall.domain.entity.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String userId;
    private String password;
    private String email;
    private String imageUrl;
    private String introduce;
    private UserStatus status;
}
