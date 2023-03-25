package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.board.BoardUpdateDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BoardConverterTest {

    BoardConverter boardConverter = new BoardConverter();

    @Test
    void 보드_포스트_DTO를_엔티티로_변환한다() {
        // given
        BoardSaveDto boardSaveDto = DummyObjectFactory.createFakeBoardSaveDto(null);

        // when
        Board board = boardConverter.boardPostDtoToEntity(boardSaveDto);

        // then
        assertThat(board.getTitle()).isEqualTo(boardSaveDto.getTitle());
        assertThat(board.getCategory()).isEqualTo(boardSaveDto.getCategory());
        assertThat(board.getPassword()).isEqualTo(boardSaveDto.getPassword());
        assertThat(board.isPrivate()).isEqualTo(boardSaveDto.isPrivate());
    }

    @Test
    void 보드_엔티티를_보드_조회_dto로_변환한다() {
        // given
        User user = DummyObjectFactory.createFakeUser();
        user.setId(1L);
        Board board = DummyObjectFactory.createFakeBoard();
        board.setUser(user);
        board.setId(1L);

        // when
        BoardResponseDto boardResponseDto = boardConverter.entityToBoardResponseDto(board);

        // then
        assertThat(boardResponseDto.getBoardId()).isEqualTo(board.getId());
        assertThat(boardResponseDto.getCategory()).isEqualTo(board.getCategory());
        assertThat(boardResponseDto.getPassword()).isEqualTo(board.getPassword());
        assertThat(boardResponseDto.getTitle()).isEqualTo(board.getTitle());
        assertThat(boardResponseDto.getCreatedAt()).isEqualTo(board.getCreatedAt());
        assertThat(boardResponseDto.getUserId()).isEqualTo(board.getUser().getId());
        assertThat(boardResponseDto.getUpdatedAt()).isEqualTo(board.getUpdatedAt());
    }

    @Test
    void 보드_엔티티를_보드_업데이트_dto로_수정한다() {
        // given
        Board board = DummyObjectFactory.createFakeBoard();
        BoardUpdateDto boardUpdateDto = DummyObjectFactory.createFakeBoardUpdateDto();

        // when
        boardConverter.update(board, boardUpdateDto);

        // then
        assertThat(board.getPassword()).isEqualTo(boardUpdateDto.getPassword());
        assertThat(board.getCategory()).isEqualTo(boardUpdateDto.getCategory());
        assertThat(board.getTitle()).isEqualTo(boardUpdateDto.getTitle());
        assertThat(board.isPrivate()).isEqualTo(boardUpdateDto.isPrivate());
    }
}