package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BoardConverterTest {

    @Test
    void 보드_포스트_DTO를_엔티티로_변환한다() {
        // given
        BoardConverter boardConverter = new BoardConverter();
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
}