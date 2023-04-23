package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
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
    BoardRepository boardRepository;

    @Autowired
    PostitRepository postitRepository;

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

    @Test
    @Transactional
    void 유저_비활성화_테스트() throws Exception {
        // given
        User user = createFakeUser();
        User savedUser = userRepository.save(user);

        // when
        mockMvc.perform(delete(url + "/" + savedUser.getId()))
                .andDo(print());

    }

    @Test
    @Transactional
    void 유저_아이디로_보드_여러개_조회() throws Exception {
        // given
        User user = createFakeUser();
        User savedUser = userRepository.save(user);
        Board board1 = createFakeBoard();
        Board board2 = createFakeBoard();
        board1.setUser(savedUser);
        board2.setUser(savedUser);
        boardRepository.save(board1);
        boardRepository.save(board2);

        // when
        mockMvc.perform(get(url + "/" + savedUser.getId() + "/boards"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 유저_아이디로_포스트잇_조회() throws Exception {
        // given
        User user = createFakeUser();
        Board board = createFakeBoard();
        board.setUser(user);
        Postit postit1 = createFakePostit();
        Postit postit2 = createFakePostit();
        postit1.setUser(user);
        postit2.setUser(user);
        postit1.setBoard(board);
        postit2.setBoard(board);
        userRepository.save(user);
        boardRepository.save(board);
        postitRepository.save(postit1);
        postitRepository.save(postit2);

        // when
        mockMvc.perform(get(url + "/" + user.getId() + "/postit"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}