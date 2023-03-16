package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.web.converter.BoardConverter;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Spy
    BoardConverter boardConverter;

    @InjectMocks
    BoardService boardService;

    @Test
    @Transactional
    void BoardService로_보드를_저장한다() {
        // given
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .title("hello world")
                .category("lecture")
                .isPrivate(false)
                .build();
        Board board = boardConverter.boardPostDtoToEntity(boardSaveDto);
        board.setId(1L);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        when(boardRepository.save(any())).thenReturn(board);

        // when
        Long savedId = boardService.save(boardSaveDto);

        // then
        Assertions.assertThat(savedId).isEqualTo(board.getId());
    }
}