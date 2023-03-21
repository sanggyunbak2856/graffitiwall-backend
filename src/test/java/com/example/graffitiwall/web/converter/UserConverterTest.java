package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserConverterTest {

    UserConverter userConverter = new UserConverter();
    User user;

    UserSaveDto userSaveDto;

    @BeforeEach
    void beforeEach() {
        userSaveDto = UserSaveDto.builder()
                .email("helloworld@email.e")
                .userId("helloworld")
                .password("byeworld")
                .introduce("introduce")
                .build();
        user = User.builder()
                .status(UserStatus.ACTIVE)
                .introduce(userSaveDto.getIntroduce())
                .email(userSaveDto.getEmail())
                .password(userSaveDto.getPassword())
                .userId(userSaveDto.getUserId())
                .build();
    }

    @Test
    void userSaveDto를_유저_엔티티로_변환한다() {
        // given, when
        User convertedUser = userConverter.userSaveDtoToEntity(userSaveDto);

        // then
        assertThat(convertedUser).usingRecursiveComparison().isEqualTo(user);
    }
}