package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 보드_저장_api_성공_테스트() throws Exception {
        User user = User.builder()
                .imageUrl("123")
                .email("efe")
                .password("grae")
                .status(UserStatus.ACTIVE)
                .introduce("hello")
                .userId("userA")
                .build();
        User savedUser = userRepository.save(user);

        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .title("title")
                .isPrivate(false)
                .userId(savedUser.getId())
                .build();
        String json = objectMapper.writer().writeValueAsString(boardSaveDto);
        mockMvc.perform(post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

}