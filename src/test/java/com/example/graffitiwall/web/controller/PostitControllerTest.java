package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import com.example.graffitiwall.web.service.BoardService;
import com.example.graffitiwall.web.service.PostitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostitRepository postitRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    User user;
    Board board;

    String url = "/api/v1/postit";

    @BeforeEach
    void beforeEach() {
        user = createFakeUser();
        board = createFakeBoard();
        board.setUser(user);
    }

    @Test
    @Transactional
    void 포스트잇_컨트롤러로_포스트잇을_저장한다() throws Exception {
        // given
        Long userId = userRepository.save(user).getId();
        Long boardId = boardRepository.save(board).getId();
        PostitSaveDto postitSaveDto = createFakePostitSaveDto(boardId, userId);
        String json = objectMapper.writer().writeValueAsString(postitSaveDto);
        log.info(json);

        // when, then
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    @Transactional
    void 포스트잇_아이디로_포스트잇을_조회한다() throws Exception {
        // given
        User savedUser = userRepository.save(user);
        Board savedBoard = boardRepository.save(board);
        Postit postit = createFakePostit();
        postit.setBoard(savedBoard);
        postit.setUser(user);

        Long postitId = postitRepository.save(postit).getId();

        // when, then
        mockMvc.perform(get(url + "/" + postitId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 포스트잇을_수정한다() throws Exception {
        // given
        User savedUser = userRepository.save(user);
        Board savedBoard = boardRepository.save(board);
        Postit postit = createFakePostit();
        postit.setUser(savedUser);
        postit.setBoard(savedBoard);
        Postit savedPostit = postitRepository.save(postit);

        PostitUpdateDto postitUpdateDto = createFakePostitUpdateDto();
        String json = objectMapper.writer().writeValueAsString(postitUpdateDto);

        // when, then
        mockMvc.perform(patch(url + "/" + savedPostit.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk())
         .andDo(print());
    }

    @Test
    @Transactional
    void 포스트잇을_삭제한다() throws Exception {
        // given
        User savedUser = userRepository.save(user);
        Board savedBoard = boardRepository.save(board);
        Postit postit = createFakePostit();
        postit.setBoard(savedBoard);
        postit.setUser(savedUser);
        Postit savedPostit = postitRepository.save(postit);

        // when, then
        mockMvc.perform(delete(url + "/" + savedPostit.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}