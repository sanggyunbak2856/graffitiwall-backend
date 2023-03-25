package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Spy
    UserConverter userConverter;

    @InjectMocks
    UserService userService;
    User user;

    @BeforeEach
    void beforeEach() {
        user = createFakeUser();
    }

    @Test
    void 유저를_저장한다() {
        // given
        user.setId(1L);
        when(userRepository.save(any())).thenReturn(user);
        UserSaveDto userSaveDto = createFakeUserSaveDto();

        // when
        userService.save(userSaveDto);

        // then
        then(userConverter).should().userSaveDtoToEntity(any());
        then(userRepository).should().save(any());

    }
}