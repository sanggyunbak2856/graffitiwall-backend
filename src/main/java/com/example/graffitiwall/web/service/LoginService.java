package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.login.LoginRequestDto;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUserId(requestDto.getUserId());
        if(user == null) {
            return null;
        }
        if(!user.getPassword().equals(requestDto.getPassword())) {
            return null;
        }
        return userConverter.entityToUserResponseDto(user);
    }
}
