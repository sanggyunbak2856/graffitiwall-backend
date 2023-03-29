package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public IdResponseDto save(UserSaveDto userSaveDto) {
        User savedUser = userRepository.save(userConverter.userSaveDtoToEntity(userSaveDto));
        return IdResponseDto.builder()
                .id(savedUser.getId())
                .build();
    }

    @Transactional
    public UserResponseDto findById(Long userRawId) {
        User user = userRepository.findById(userRawId).get();
        return userConverter.entityToUserResponseDto(user);
    }

    @Transactional
    public IdResponseDto update(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).get();
        userConverter.userUpdate(user, userUpdateDto);
        return IdResponseDto.builder().id(user.getId()).build();
    }

    @Transactional
    public IdResponseDto deactivateUser(Long userRawId) {
        User user = userRepository.findById(userRawId).get();
        user.setStatus(UserStatus.INACTIVE);
        return IdResponseDto.builder().id(user.getId()).build();
    }
}
