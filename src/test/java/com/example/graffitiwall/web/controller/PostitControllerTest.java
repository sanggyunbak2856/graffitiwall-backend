package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.service.BoardService;
import com.example.graffitiwall.web.service.PostitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PostitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoardService boardService;
    
    @Autowired
    PostitService postitService;
    
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 포스트잇_컨트롤러로_포스트잇을_저장한다() throws Exception {
        // given
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .isPrivate(false)
                .title("hello world")
                .build();
        Long boardId = boardService.save(boardSaveDto);
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .angle(0)
                .boardId(boardId)
                .color("red")
                .content("hello world")
                .positionX(0)
                .positionY(0)
                .title("hello world")
                .build();
        String json = objectMapper.writer().writeValueAsString(postitSaveDto);
        log.info(json);

        // when, then
        mockMvc.perform(post("/api/v1/postit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    void 포스트잇_아이디로_포스트잇을_조회한다() throws Exception {
        // given
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .isPrivate(false)
                .title("hello world")
                .build();
        Long boardId = boardService.save(boardSaveDto);
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .angle(0)
                .boardId(boardId)
                .color("red")
                .content("hello world")
                .positionX(0)
                .positionY(0)
                .title("hello world")
                .build();
        Long postitId = postitService.save(postitSaveDto);

        // when, then
        mockMvc.perform(get("/api/v1/postit/" + postitId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}