package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

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
    }

    @Test
    @Transactional
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
    }
}