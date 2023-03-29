package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
import com.example.graffitiwall.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public IdResponseDto register(@RequestBody UserSaveDto userSaveDto) {
        return userService.save(userSaveDto);
    }

    @PatchMapping("/{userRawId}")
    public IdResponseDto update(@PathVariable Long userRawId, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.update(userRawId, userUpdateDto);
    }

    @GetMapping("/{userRawId}")
    public UserResponseDto findById(@PathVariable Long userRawId) {
        return userService.findById(userRawId);
    }

    @DeleteMapping("/{userRawId}")
    public IdResponseDto deactivateUser(@PathVariable Long userRawId) {
        return userService.deactivateUser(userRawId);
    }
}
