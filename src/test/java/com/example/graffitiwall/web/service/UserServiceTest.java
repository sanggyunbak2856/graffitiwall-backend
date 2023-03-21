package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    UserConverter userConverter;

    @InjectMocks
    UserService userService;

    UserSaveDto userSaveDto;

    User userA;

    @BeforeEach
    void beforeEach() {
        userSaveDto = UserSaveDto.builder()
                .userId("userA")
                .password("password")
                .email("email@email.com")
                .introduce("hello world")
                .build();
        userA = User.builder()
                .userId(userSaveDto.getUserId())
                .password(userSaveDto.getPassword())
                .status(UserStatus.ACTIVE)
                .email(userSaveDto.getEmail())
                .introduce(userSaveDto.getIntroduce())
                .build();
    }

    @Test
    void 유저를_저장한다() {
        // given
        userA.setId(1L);
        when(userRepository.save(any())).thenReturn(userA);
        when(userConverter.userSaveDtoToEntity(any())).thenReturn(userA);

        // when
        userService.save(userSaveDto);

        // then
        then(userConverter).should().userSaveDtoToEntity(any());
        then(userRepository).should().save(any());

    }
}