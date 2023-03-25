package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PostitConverterTest {

    PostitConverter postitConverter = new PostitConverter();

    @Test
    void 포스트잇_엔티티를_포스트잇_응답_dto로_변환() {
        // given
        User user = User.builder().build();
        user.setId(1L);
        user.setUserId("userA");
        Board board = Board.builder().build();
        board.setId(1L);
        board.setUser(user);
        Postit postit = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .angle(10)
                .build();
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
    }

    @Test
    void 포스트잇을_업데이트한다() {
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
        PostitUpdateDto postitUpdateDto = PostitUpdateDto.builder()
                .angle(100)
                .contents("bye")
                .color("blue")
                .positionX(22)
                .positionY(33)
                .title("sllsl")
                .build();

        // when
        postitConverter.postitUpdate(postit, postitUpdateDto);

        // then
        assertThat(postit.getTitle()).isEqualTo(postitUpdateDto.getTitle());
        assertThat(postit.getAngle()).isEqualTo(postitUpdateDto.getAngle());
        assertThat(postit.getContents()).isEqualTo(postitUpdateDto.getContents());
        assertThat(postit.getColor()).isEqualTo(postitUpdateDto.getColor());
        assertThat(postit.getPositionX()).isEqualTo(postitUpdateDto.getPositionX());
        assertThat(postit.getPositionY()).isEqualTo(postitUpdateDto.getPositionY());
    }
}