package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostitConverterTest {

    @Test
    void 포스트잇_엔티티를_포스트잇_응답_dto로_변환() {
        // given
        PostitConverter postitConverter = new PostitConverter();
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
        postit.setId(1L);
        postit.setCreatedAt(LocalDateTime.now());
        postit.setUpdatedAt(LocalDateTime.now());
        postit.setViews(1);


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
    }
}