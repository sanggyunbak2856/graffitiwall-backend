package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    UserSaveDto userSaveDto;

    @BeforeEach
    void beforeEach() {
        userSaveDto = UserSaveDto.builder()
                .userId("userA")
                .password("password")
                .email("email@email.com")
                .introduce("hello world")
                .build();
    }

    @Test
    void 유저_컨트롤러_저장_테스트() throws Exception {
        // given
        String json = objectMapper.writer().writeValueAsString(userSaveDto);
        IdResponseDto idResponseDto = IdResponseDto.builder().id(1L).build();

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        IdResponseDto jsonToIdResponseDto = objectMapper.readValue(contentAsString, IdResponseDto.class);
        assertThat(idResponseDto).usingRecursiveComparison().isEqualTo(jsonToIdResponseDto);
    }
}