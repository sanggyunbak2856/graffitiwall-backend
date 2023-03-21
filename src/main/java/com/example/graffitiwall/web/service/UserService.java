package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public IdResponseDto save(UserSaveDto userSaveDto) {
        User savedUser = userRepository.save(userConverter.userSaveDtoToEntity(userSaveDto));
        return IdResponseDto.builder()
                .id(savedUser.getId())
                .build();
    }
}
