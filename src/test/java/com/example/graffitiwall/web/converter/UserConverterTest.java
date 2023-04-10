package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.*;

class UserConverterTest {

    UserConverter userConverter = new UserConverter();
    User user;

    @BeforeEach
    void beforeEach() {
        user = createFakeUser();
    }

    @Test
    void userSaveDto를_유저_엔티티로_변환한다() {
        // given, when
        UserSaveDto userSaveDto = createFakeUserSaveDto();
        User convertedUser = userConverter.userSaveDtoToEntity(userSaveDto);

        // then
        assertThat(convertedUser.getUserId()).isEqualTo(userSaveDto.getUserId());
        assertThat(convertedUser.getEmail()).isEqualTo(userSaveDto.getEmail());
        assertThat(convertedUser.getPassword()).isEqualTo(userSaveDto.getPassword());
        assertThat(convertedUser.getNickname()).isEqualTo(userSaveDto.getNickname());
    }

    @Test
    void 유저_엔티티를_UserUpdateDto로_수정한다() {
        // given
        UserUpdateDto userUpdateDto = createFakeUserUpdateDto();
        User user = createFakeUser();

        // when
        userConverter.userUpdate(user, userUpdateDto);

        // then
        assertThat(user.getUserId()).isEqualTo(userUpdateDto.getUserId());
        assertThat(user.getPassword()).isEqualTo(userUpdateDto.getPassword());
        assertThat(user.getEmail()).isEqualTo(userUpdateDto.getEmail());
        assertThat(user.getIntroduce()).isEqualTo(userUpdateDto.getIntroduce());
        assertThat(user.getNickname()).isEqualTo(userUpdateDto.getNickname());
    }

    @Test
    void 유저_엔티티를_UserResponseDto로_변환한다() {
        // given
        User user = createFakeUser();

        // when
        UserResponseDto userResponseDto = userConverter.entityToUserResponseDto(user);

        // then
        assertThat(userResponseDto.getUserId()).isEqualTo(user.getUserId());
        assertThat(userResponseDto.getId()).isEqualTo(user.getId());
        assertThat(userResponseDto.getImageUrl()).isEqualTo(user.getImageUrl());
        assertThat(userResponseDto.getStatus()).isEqualTo(user.getStatus());
        assertThat(userResponseDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResponseDto.getIntroduce()).isEqualTo(user.getIntroduce());
        assertThat(userResponseDto.getPassword()).isEqualTo(user.getPassword());
        assertThat(userResponseDto.getNickname()).isEqualTo(user.getNickname());
    }
}