package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BoardConverterTest {

    BoardConverter boardConverter = new BoardConverter();

    @Test
    void 보드_포스트_DTO를_엔티티로_변환한다() {
        // given
        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .category("class")
                .isPrivate(true)
                .title("hello world")
                .password("password")
                .build();

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
        User user = User.builder().build();
        user.setId(1L);
        Board board = Board.builder()
                .user(user)
                .password("password")
                .isPrivate(true)
                .title("board")
                .category("category")
                .build();
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
}