package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.factory.DummyObjectFactory;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.*;

class PostitConverterTest {

    PostitConverter postitConverter = new PostitConverter();

    User user;
    Board board;
    Postit postit;

    @BeforeEach
    void beforeEach() {
        user = createFakeUser();
        board = createFakeBoard();
        postit = createFakePostit();
    }

    @Test
    void 포스트잇_엔티티를_포스트잇_응답_dto로_변환() {
        // given
        user.setId(1L);
        board.setId(1L);
        board.setUser(user);
        postit.setId(1L);
        postit.setCreatedAt(LocalDateTime.now());
        postit.setUpdatedAt(LocalDateTime.now());
        postit.setViews(1);
        postit.setBoard(board);
        postit.setUser(user);

        // when
        PostitResponseDto postitResponseDto = postitConverter.entityToPostitResponseDto(postit);

        // then
        assertThat(postitResponseDto.getPostitId()).isEqualTo(postit.getId());
        assertThat(postitResponseDto.getAngle()).isEqualTo(postit.getAngle());
        assertThat(postitResponseDto.getColor()).isEqualTo(postit.getColor());
        assertThat(postitResponseDto.getContents()).isEqualTo(postit.getContents());
        assertThat(postitResponseDto.getCreatedAt()).isEqualTo(postit.getCreatedAt());
        assertThat(postitResponseDto.getUpdatedAt()).isEqualTo(postit.getUpdatedAt());
        assertThat(postitResponseDto.getPositionX()).isEqualTo(postit.getPositionX());
        assertThat(postitResponseDto.getPositionY()).isEqualTo(postit.getPositionY());
        assertThat(postitResponseDto.getBoardId()).isEqualTo(postit.getBoard().getId());
        assertThat(postitResponseDto.getViews()).isEqualTo(postit.getViews());
        assertThat(postitResponseDto.getUserId()).isEqualTo(postit.getUser().getId());
        assertThat(postitResponseDto.getSizeX()).isEqualTo(postit.getSizeX());
        assertThat(postitResponseDto.getSizeY()).isEqualTo(postit.getSizeY());
    }

    @Test
    void 포스트잇을_업데이트한다() {
        // given
        board.setId(1L);
        PostitUpdateDto postitUpdateDto = createFakePostitUpdateDto();

        // when
        postitConverter.postitUpdate(postit, postitUpdateDto);

        // then
        assertThat(postit.getTitle()).isEqualTo(postitUpdateDto.getTitle());
        assertThat(postit.getAngle()).isEqualTo(postitUpdateDto.getAngle());
        assertThat(postit.getContents()).isEqualTo(postitUpdateDto.getContents());
        assertThat(postit.getColor()).isEqualTo(postitUpdateDto.getColor());
        assertThat(postit.getPositionX()).isEqualTo(postitUpdateDto.getPositionX());
        assertThat(postit.getPositionY()).isEqualTo(postitUpdateDto.getPositionY());
        assertThat(postit.getSizeX()).isEqualTo(postitUpdateDto.getSizeX());
        assertThat(postit.getSizeY()).isEqualTo(postitUpdateDto.getSizeY());
        assertThat(postit.getFont()).isEqualTo(postitUpdateDto.getFont());
    }
}