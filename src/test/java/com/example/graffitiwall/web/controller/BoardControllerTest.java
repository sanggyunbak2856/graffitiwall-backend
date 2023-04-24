package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.board.BoardUpdateDto;
import com.example.graffitiwall.web.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostitRepository postitRepository;

    @Autowired
    private ObjectMapper objectMapper;

    User user;
    User savedUser;

    private String url = "/api/v1/boards";

    @BeforeEach
    void beforeEach() {
        user = createFakeUser();
        savedUser = userRepository.save(user);
    }

    @Test
    void 보드_저장_api_성공_테스트() throws Exception {
        BoardSaveDto boardSaveDto = createFakeBoardSaveDto(savedUser.getId());
        String json = objectMapper.writer().writeValueAsString(boardSaveDto);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 보드_조회_테스트() throws Exception {
        // given
        Board board = createFakeBoard();
        board.setUser(savedUser);
        Board savedBoard = boardRepository.save(board);

        // when, then
        MvcResult mvcResult = mockMvc.perform(get(url + "/" + savedBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info(contentAsString);
    }

    @Test
    @Transactional
    void 보드_수정_테스트() throws Exception {
        // given
        Board board = createFakeBoard();
        BoardUpdateDto updateDto = createFakeBoardUpdateDto();
        board.setUser(user);
        Board savedBoard = boardRepository.save(board);
        String json = objectMapper.writer().writeValueAsString(updateDto);

        // when
        mockMvc.perform(patch(url + "/" + savedBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        Board foundBoard = boardRepository.findById(savedBoard.getId()).get();
        assertThat(foundBoard.getTitle()).isEqualTo(savedBoard.getTitle());
    }

    @Test
    @Transactional
    void 보드_삭제_테스트() throws Exception {
        // given
        Board board = createFakeBoard();
        Board savedBoard = boardRepository.save(board);

        // when
        mockMvc.perform(delete(url + "/" + savedBoard.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    void 보드아이디로_포스트잇_목록_조회_테스트() throws Exception {
        // givwn
        Board board = createFakeBoard();
        board.setUser(savedUser);
        Postit postit1 = createFakePostit();
        Postit postit2 = createFakePostit();
        postit1.setBoard(board);
        postit2.setBoard(board);
        postit1.setUser(user);
        postit2.setUser(user);
        Board savedBoard = boardRepository.save(board);
        postitRepository.save(postit1);
        postitRepository.save(postit2);

        // when
        MvcResult mvcResult = mockMvc.perform(get(url + "/" + savedBoard.getId() + "/postits"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Transactional
    void 보드_전체_조회_테스트() throws Exception {
        // given
        Board board1 = createFakeBoard();
        Board board2 = createFakeBoard();
        board1.setUser(savedUser);
        board2.setUser(savedUser);
        boardRepository.save(board1);
        boardRepository.save(board2);

        // when
        mockMvc.perform(get(url))
                .andDo(print())
                .andReturn();

        // then
    }

    @Test
    @Transactional
    void 보드_랜덤_조회_테스트() throws Exception {
        // given
        User user = createFakeUser();
        userRepository.save(user);
        for(int i = 0; i < 50; i++) {
            Board board = createFakeBoard();
            board.setUser(user);
            boardRepository.save(board);
        }

        // when
        mockMvc.perform(get(url + "/random"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
    }

}