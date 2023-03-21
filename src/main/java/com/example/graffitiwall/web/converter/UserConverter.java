package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User userSaveDtoToEntity(UserSaveDto userSaveDto) {
        return User.builder()
                .userId(userSaveDto.getUserId())
                .password(userSaveDto.getPassword())
                .email(userSaveDto.getEmail())
                .introduce(userSaveDto.getIntroduce())
                .status(UserStatus.ACTIVE)
                .build();
    }
}
