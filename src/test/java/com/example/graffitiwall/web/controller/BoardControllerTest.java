package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.board.BoardPostDto;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 보드_저장_api_성공_테스트() throws Exception {
        BoardPostDto boardPostDto = BoardPostDto.builder()
                .category("category")
                .title("title")
                .isPrivate(false)
                .build();
        String json = objectMapper.writer().writeValueAsString(boardPostDto);
        mockMvc.perform(post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

}