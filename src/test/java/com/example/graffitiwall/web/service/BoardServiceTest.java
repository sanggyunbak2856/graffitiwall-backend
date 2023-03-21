package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.BoardConverter;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
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

    User user;
    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .introduce("hello world")
                .email("email@email.email")
                .status(UserStatus.ACTIVE)
                .userId("userA")
                .password("password")
                .build();
        user.setId(1L);
    }

    @Test
    void BoardService로_보드를_저장한다() {
        // given
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
        assertThat(savedId).isEqualTo(board.getId());
    }

    @Test
    void 보드_서비스로_보드를_조회한다() {
        // given
        Board board = Board.builder().build();
        board.setUser(user);
        board.setId(1L);
        when(boardRepository.findById(any())).thenReturn(Optional.of(board));

        // when
        BoardResponseDto responseDto = boardService.findById(1L);

        // then
        then(boardRepository).should().findById(any());
        then(boardConverter).should().entityToBoardResponseDto(any());
        assertThat(responseDto.getBoardId()).isEqualTo(board.getId());
    }
}