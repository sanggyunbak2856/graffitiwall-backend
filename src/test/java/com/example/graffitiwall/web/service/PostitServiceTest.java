package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.web.converter.PostitConverter;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostitServiceTest {

    @Mock
    PostitRepository postitRepository;

    @Mock
    BoardRepository boardRepository;

    @Spy
    PostitConverter postitConverter;

    @InjectMocks
    PostitService postitService;

    @Test
    void 포스트잇을_포스트잇서비스로_저장한다() {
        // given
        Board board = Board.builder()
                .build();
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
        assertThat(savedId).isEqualTo(1L);
        assertThat(board.getPostits().size()).isEqualTo(1);
    }

    @Test
    void 포스트잇서비스로_포스트잇을_조회한다() {
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
        when(postitRepository.findById(any())).thenReturn(Optional.of(postit));

        // when
        PostitResponseDto postitResponseDto = postitService.findById(postit.getId());

        // then
        assertThat(postitResponseDto.getPostitId()).isEqualTo(postit.getId());
    }

}
