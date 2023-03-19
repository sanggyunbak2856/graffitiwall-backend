package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    UserRepository userRepository;

    @Spy
    BoardConverter boardConverter;

    @InjectMocks
    BoardService boardService;

    @Test
    @Transactional
    void BoardService로_보드를_저장한다() {
        // given
        User user = User.builder().build();
        user.setId(1L);
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .title("hello world")
                .category("lecture")
                .isPrivate(false)
                .userId(1L)
                .build();
        Board board = boardConverter.boardPostDtoToEntity(boardSaveDto);
        board.setId(1L);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        board.setUser(user);
        when(boardRepository.save(any())).thenReturn(board);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // when
        Long savedId = boardService.save(boardSaveDto);

        // then
        Assertions.assertThat(savedId).isEqualTo(board.getId());
    }
}