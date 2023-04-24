package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.converter.BoardConverter;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.board.BoardUpdateDto;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
        user = createFakeUser();
        user.setId(1L);
    }

    @Test
    void 모든_보드_리스트를_가져온다() {
        // given
        Board board1 = createFakeBoard();
        Board board2 = createFakeBoard();
        board1.setUser(user);
        board2.setUser(user);
        given(boardRepository.findAll()).willReturn(List.of(board1, board2));

        // when
        List<BoardResponseDto> boards = boardService.findAllBoards();


        // then
        assertThat(boards).hasSize(2);
    }

    @Test
    void BoardService로_보드를_저장한다() {
        // given
        BoardSaveDto boardSaveDto = createFakeBoardSaveDto(user.getId());
        Board board = boardConverter.boardPostDtoToEntity(boardSaveDto);
        board.setId(1L);
        board.setUser(user);
        when(boardRepository.save(any())).thenReturn(board);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // when
        Long savedId = boardService.save(boardSaveDto);

        // then
        assertThat(savedId).isEqualTo(board.getId());
        then(boardRepository).should().save(any());
    }

    @Test
    void 보드_서비스로_보드를_조회한다() {
        // given
        Board board = DummyObjectFactory.createFakeBoard();
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

    @Test
    void 보드_서비스로_보드를_수정한다() {
        // given
        Board board = createFakeBoard();
        BoardUpdateDto boardUpdateDto = createFakeBoardUpdateDto();
        board.setId(1L);
        when(boardRepository.findById(any())).thenReturn(Optional.of(board));

        // when
        IdResponseDto updatedId = boardService.update(1L, boardUpdateDto);

        // then
        then(boardRepository).should().findById(any());
        then(boardConverter).should().update(any(), any());
        assertThat(updatedId.getId()).isEqualTo(1L);
    }

    @Test
    void 보드_서비스로_보드를_삭제한다() {
        // given

        // when
        boardService.deleteById(1L);

        // then
        then(boardRepository).should().deleteById(any());
    }

    @Test
    void 보드를_랜덤으로_가져온다() {
        // given
        List<Board> boardList = new LinkedList<>();
        for(int i = 0; i < 20; i++) {
            Board board = createFakeBoard();
            board.setUser(user);
            boardList.add(board);
        }
        given(boardRepository.findBoardRandom()).willReturn(boardList);

        // when
        List<BoardResponseDto> boardResponseDtoList = boardService.findBoardsRandom();

        // then
        assertThat(boardResponseDtoList).hasSize(20);
        verify(boardConverter, times(20)).entityToBoardResponseDto(any());
    }

    @Test
    void 보드_조회시_조회수가_증가한다() {
        // given
        Board board = createFakeBoard();
        board.setUser(user);
        given(boardRepository.findById(any())).willReturn(Optional.of(board));

        // when
        boardService.findById(any());

        // then
        assertThat(board.getViews()).isEqualTo(1);

    }

    @Test
    void 조회수가_높은_보드를_가져온다() {
        // given
        List<Board> boards = new LinkedList<>();
        for(int i = 0; i < 20; i++) {
            Board board = createFakeBoard();
            board.setUser(user);
            boards.add(board);

        }

        given(boardRepository.findPopularBoards()).willReturn(boards);

        // when
        List<BoardResponseDto> popularBoards = boardService.findPopularBoards();

        // then
        assertThat(popularBoards).hasSize(20);
    }
}