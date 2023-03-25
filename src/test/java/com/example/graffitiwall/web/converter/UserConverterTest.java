package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.graffitiwall.factory.DummyObjectFactory.createFakeUser;
import static com.example.graffitiwall.factory.DummyObjectFactory.createFakeUserSaveDto;
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
}