package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
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
                .nickname(userSaveDto.getNickname())
                .build();
    }

    public void userUpdate(User user, UserUpdateDto userUpdateDto) {
        user.setUserId(userUpdateDto.getUserId());
        user.setEmail(userUpdateDto.getEmail());
        user.setIntroduce(userUpdateDto.getIntroduce());
        user.setPassword(userUpdateDto.getPassword());
        user.setNickname(userUpdateDto.getNickname());
    }

    public UserResponseDto entityToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .imageUrl(user.getImageUrl())
                .status(user.getStatus())
                .email(user.getEmail())
                .introduce(user.getIntroduce())
                .password(user.getPassword())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
