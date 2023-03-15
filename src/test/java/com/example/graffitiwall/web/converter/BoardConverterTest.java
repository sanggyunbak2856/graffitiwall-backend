package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.web.dto.board.BoardPostDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class BoardConverterTest {

    @Test
    void 보드_포스트_DTO를_엔티티로_변환한다() {
        // given
        BoardConverter boardConverter = new BoardConverter();
        BoardPostDto boardPostDto = BoardPostDto.builder()
                .category("class")
                .isPrivate(true)
                .title("hello world")
                .password("password")
                .build();

        // when
        Board board = boardConverter.boardPostDtoToEntity(boardPostDto);

        // then
        assertThat(board.getTitle()).isEqualTo(boardPostDto.getTitle());
        assertThat(board.getCategory()).isEqualTo(boardPostDto.getCategory());
        assertThat(board.getPassword()).isEqualTo(boardPostDto.getPassword());
        assertThat(board.isPrivate()).isEqualTo(boardPostDto.isPrivate());
    }
}