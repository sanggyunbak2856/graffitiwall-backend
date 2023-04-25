package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.dto.login.LoginRequestDto;
import com.example.graffitiwall.web.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    String url = "/api/v1";

    User user;

    @BeforeEach
    @Transactional
    void beforeEach() {
        user = createFakeUser();
        userRepository.save(user);
    }

    @Test
    void 빈칸_요청을_주어_로그인에_실패한다() throws Exception {
        // given
        LoginRequestDto requestDto = LoginRequestDto.builder().build();
        String json = objectMapper.writer().writeValueAsString(requestDto);

        // when, then
        mockMvc.perform(post(url + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 비밀번호_또는_아이디를_다르게_입력하여_로그인에_실패한다() throws Exception {
        // given
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword() + "rewa")
                .build();
        String json = objectMapper.writeValueAsString(requestDto);

        // when, then
        mockMvc.perform(post(url + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 로그인에_성공하고_쿠키에_세션아이디값이_저장된다() throws Exception {
        // given
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();
        String json = objectMapper.writeValueAsString(requestDto);

        // when, then
        MvcResult mvcResult = mockMvc.perform(post(url + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Cookie jsessionid = mvcResult.getResponse().getCookie("JSESSIONID");
        log.info(jsessionid.toString());
    }

    @Test
    void 로그아웃_테스트() throws Exception {
        // given
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();
        String json = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(post(url + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // when
        MvcResult mvcResult = mockMvc.perform(post(url + "/logout"))
                .andExpect(status().isOk())
                .andReturn();
    }
}