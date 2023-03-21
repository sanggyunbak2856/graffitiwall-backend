package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    User user;
    User savedUser;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .imageUrl("123")
                .email("efe")
                .password("grae")
                .status(UserStatus.ACTIVE)
                .introduce("hello")
                .userId("userA")
                .build();
        savedUser = userRepository.save(user);
    }

    @Test
    void 보드_저장_api_성공_테스트() throws Exception {
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

    @Test
    @Transactional
    void 보드_조회_테스트() throws Exception {
        // given
        Board board = Board.builder()
                .user(savedUser)
                .title("new board")
                .category("category")
                .isPrivate(true)
                .password("password")
                .build();
        Board savedBoard = boardRepository.save(board);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/boards/" + savedBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info(contentAsString);
    }

}