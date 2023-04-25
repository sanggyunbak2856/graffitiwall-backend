package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.login.LoginRequestDto;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    UserRepository userRepository;

    @Spy
    UserConverter userConverter;

    @InjectMocks
    LoginService loginService;

    @Test
    void 로그인_성공() {
        // given
        User user = createFakeUser();
        user.setId(1L);
        given(userRepository.findByUserId(any())).willReturn(user);
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();

        // when
        UserResponseDto responseDto = loginService.login(requestDto);

        // then
        assertThat(responseDto.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    void 비밀번호를_다르게_입력하여_null을_반환함 () {
        // given
        User user = createFakeUser();
        user.setId(1L);
        given(userRepository.findByUserId(any())).willReturn(user);
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword() + "faef")
                .build();

        // when
        UserResponseDto responseDto = loginService.login(requestDto);

        // then
        assertThat(responseDto).isNull();
    }

    @Test
    void 디비에서_유저를_못찾아서_null을_반환함() {
        // given
        given(userRepository.findByUserId(any())).willReturn(null);
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .password("1234")
                .userId("feawf")
                .build();

        // when
        UserResponseDto responseDto = loginService.login(requestDto);

        // then
        assertThat(responseDto).isNull();
    }
}