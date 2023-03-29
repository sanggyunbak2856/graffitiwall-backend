package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
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
import org.springframework.transaction.annotation.Transactional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    String url = "/api/v1/users";

    @Test
    void 유저_컨트롤러_저장_테스트() throws Exception {
        // given
        UserSaveDto userSaveDto = createFakeUserSaveDto();
        String json = objectMapper.writer().writeValueAsString(userSaveDto);
        IdResponseDto idResponseDto = IdResponseDto.builder().id(1L).build();

        // when
        MvcResult mvcResult = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    void 유저_업데이트_테스트() throws Exception {
        // given
        User user = createFakeUser();
        User savedUser = userRepository.save(user);
        UserUpdateDto userUpdateDto = createFakeUserUpdateDto();
        String json = objectMapper.writeValueAsString(userUpdateDto);

        // when
        mockMvc.perform(patch(url + "/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk())
         .andDo(print());
    }

    @Test
    @Transactional
    void 유저_조회_테스트() throws Exception {
        // given
        User user = createFakeUser();
        User savedUser = userRepository.save(user);

        // when
        mockMvc.perform(get(url + "/" + savedUser.getId()))
                .andDo(print());
    }
}