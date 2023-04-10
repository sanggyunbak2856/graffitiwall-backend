package com.example.graffitiwall.web.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserSaveDto {
    private String userId;
    private String password;
    private String email;
    private String introduce;
    private String nickname;
}
