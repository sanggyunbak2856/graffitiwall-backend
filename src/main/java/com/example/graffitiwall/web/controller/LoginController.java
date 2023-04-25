package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.SessionConst;
import com.example.graffitiwall.web.dto.login.LoginRequestDto;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import com.example.graffitiwall.web.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Validated @RequestBody LoginRequestDto requestDto,
                                                  BindingResult bindingResult,
                                                  HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserResponseDto responseDto = loginService.login(requestDto);
        if(responseDto == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public HttpStatus logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return HttpStatus.OK;
    }
}
