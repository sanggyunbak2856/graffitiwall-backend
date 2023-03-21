package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
