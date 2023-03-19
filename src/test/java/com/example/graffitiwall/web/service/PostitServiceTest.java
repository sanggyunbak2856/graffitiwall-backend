package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.PostitConverter;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostitServiceTest {

    @Mock
    PostitRepository postitRepository;

    @Mock
    BoardRepository boardRepository;

    @Mock
    UserRepository userRepository;

    @Spy
    PostitConverter postitConverter;

    @InjectMocks
    PostitService postitService;

    @Test
    void 포스트잇을_포스트잇서비스로_저장한다() {
        // given
        User user = User.builder().build();
        user.setId(1L);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Board board = Board.builder().build();
        board.setId(1L);
        when(boardRepository.findById(any())).thenReturn(Optional.of(board));
        PostitSaveDto postitSaveDto = PostitSaveDto.builder()
                .boardId(1L)
                .build();
        Postit postit = postitConverter.postitSaveDtoToEntity(postitSaveDto);
        postit.setId(1L);
        when(postitRepository.save(any())).thenReturn(postit);

        // when
        Long savedId = postitService.save(postitSaveDto);

        // then
        log.info("board postits : {}", board.getPostits());
        log.info("user postits : {}", user.getPostits());
        assertThat(savedId).isEqualTo(1L);
        assertThat(board.getPostits().size()).isEqualTo(1);
    }

    @Test
    void 포스트잇서비스로_포스트잇을_조회한다() {
        // given
        User user = User.builder().build();
        user.setId(1L);
        user.setUserId("userA");
        Board board = Board.builder().build();
        board.setId(1L);
        Postit postit = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .angle(10)
                .build();
        postit.setViews(1);
        postit.setBoard(board);
        postit.setUser(user);
        when(postitRepository.findById(any())).thenReturn(Optional.of(postit));

        // when
        PostitResponseDto postitResponseDto = postitService.findById(postit.getId());

        // then
        assertThat(postitResponseDto.getPostitId()).isEqualTo(postit.getId());
        assertThat(postitResponseDto.getWriter()).isEqualTo(postit.getUser().getUserId());
    }

    @Test
    void 포스트잇_서비스로_포스트잇을_수정한다() {
        // given
        Board board = Board.builder().build();
        board.setId(1L);
        Postit postit = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .board(board)
                .angle(10)
                .build();
        postit.setViews(1);
        postit.setId(1L);
        when(postitRepository.findById(any())).thenReturn(Optional.of(postit));
        PostitUpdateDto postitUpdateDto = PostitUpdateDto.builder()
                .content("b").title("c").positionY(100).positionX(100)
                .color("white").angle(55)
                .build();


        // when
        Long updatedId = postitService.update(1L, postitUpdateDto);

        // then
        then(postitConverter).should().postitUpdate(any(), any());
        assertThat(updatedId).isEqualTo(postit.getId());

        Postit updatedPostit = postitRepository.findById(updatedId).get();
        assertThat(updatedPostit.getPositionY()).isEqualTo(postitUpdateDto.getPositionY());
        assertThat(updatedPostit.getPositionX()).isEqualTo(postitUpdateDto.getPositionX());
        assertThat(updatedPostit.getColor()).isEqualTo(postitUpdateDto.getColor());
        assertThat(updatedPostit.getContents()).isEqualTo(postitUpdateDto.getContent());
        assertThat(updatedPostit.getAngle()).isEqualTo(postitUpdateDto.getAngle());
        assertThat(updatedPostit.getTitle()).isEqualTo(postitUpdateDto.getTitle());
    }

    @Test
    void 포스트잇_서비스로_포스트잇을_삭제한다() {
        // when
        postitService.deleteById(1L);

        // then
        then(postitRepository).should().deleteById(any());
    }

}
