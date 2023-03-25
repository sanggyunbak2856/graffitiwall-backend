package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import com.example.graffitiwall.web.service.BoardService;
import com.example.graffitiwall.web.service.PostitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    void 포스트잇_컨트롤러로_포스트잇을_저장한다() throws Exception {
        // given
        User user = User.builder()
                .imageUrl("123")
                .email("efe")
                .password("grae")
                .status(UserStatus.ACTIVE)
                .introduce("hello")
                .userId("userA")
                .build();
        userRepository.save(user);
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .isPrivate(false)
                .title("hello world")
                .userId(user.getId())
                .build();
        Long boardId = boardService.save(boardSaveDto);
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .angle(0)
                .boardId(boardId)
                .color("red")
                .contents("hello world")
                .positionX(0)
                .positionY(0)
                .title("hello world")
                .boardId(boardId)
                .userId(user.getId())
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
    @Transactional
    void 포스트잇_아이디로_포스트잇을_조회한다() throws Exception {
        // given
        User user = User.builder()
                .imageUrl("123")
                .email("efe")
                .password("grae")
                .status(UserStatus.ACTIVE)
                .introduce("hello")
                .userId("userA")
                .build();
        userRepository.save(user);
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .isPrivate(false)
                .title("hello world")
                .userId(user.getId())
                .build();
        Long boardId = boardService.save(boardSaveDto);
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .angle(0)
                .boardId(boardId)
                .color("red")
                .contents("hello world")
                .positionX(0)
                .positionY(0)
                .title("hello world")
                .userId(user.getId())
                .boardId(boardId)
                .build();
        Long postitId = postitService.save(postitSaveDto);

        // when, then
        mockMvc.perform(get("/api/v1/postit/" + postitId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 포스트잇을_수정한다() throws Exception {
        // given
        User user = User.builder()
                .imageUrl("123")
                .email("efe")
                .password("grae")
                .status(UserStatus.ACTIVE)
                .introduce("hello")
                .userId("userA")
                .build();
        userRepository.save(user);
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .isPrivate(false)
                .title("hello world")
                .userId(user.getId())
                .build();
        Long boardId = boardService.save(boardSaveDto);
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .angle(0)
                .boardId(boardId)
                .color("red")
                .contents("hello world")
                .positionX(0)
                .positionY(0)
                .title("hello world")
                .boardId(boardId)
                .userId(user.getId())
                .build();
        Long postitId = postitService.save(postitSaveDto);
        PostitUpdateDto postitUpdateDto = PostitUpdateDto.builder()
                .angle(100)
                .color("blue")
                .positionX(20)
                .positionY(30)
                .contents("bye world")
                .title("bye world")
                .build();
        String json = objectMapper.writer().writeValueAsString(postitUpdateDto);

        // when, then
        mockMvc.perform(patch("/api/v1/postit/" + postitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk())
         .andDo(print());
    }

    @Test
    @Transactional
    void 포스트잇을_삭제한다() throws Exception {
        // given
        User user = User.builder()
                .imageUrl("123")
                .email("efe")
                .password("grae")
                .status(UserStatus.ACTIVE)
                .introduce("hello")
                .userId("userA")
                .build();
        userRepository.save(user);
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("category")
                .isPrivate(false)
                .title("hello world")
                .userId(user.getId())
                .build();
        Long boardId = boardService.save(boardSaveDto);
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .angle(0)
                .boardId(boardId)
                .color("red")
                .contents("hello world")
                .positionX(0)
                .positionY(0)
                .title("hello world")
                .boardId(boardId)
                .userId(user.getId())
                .build();
        Long postitId = postitService.save(postitSaveDto);

        // when, then
        mockMvc.perform(delete("/api/v1/postit/" + postitId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}