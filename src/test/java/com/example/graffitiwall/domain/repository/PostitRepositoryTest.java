package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.repository.PostitRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostitRepositoryTest {

    @Autowired
    private PostitRepository postitRepository;
    @Autowired
    private BoardRepository boardRepository;

    Board board;
    Postit postit;

    @BeforeEach
    void beforeEach() {
        board = Board.builder()
                .category("category")
                .isPrivate(false)
                .title("hello")
                .build();
        postit = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .board(board)
                .angle(10)
                .build();
    }

    @AfterEach
    void afterEach() {
        postitRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @Transactional
    void 디비에_포스트잇을_저장한다() {
        // given
        boardRepository.save(board);

        // when
        Postit savedPostit = postitRepository.save(postit);

        // then
        assertThat(postit).isEqualTo(savedPostit);
    }

    @Test
    @Transactional
    void 디비에서_포스트잇을_조회한다() {
        // given
        boardRepository.save(board);
        postitRepository.save(postit);

        // when
        Optional<Postit> optionalPostit = postitRepository.findById(postit.getId());

        // then
        assertThat(optionalPostit.get()).isEqualTo(postit);
    }

    @Test
    @Transactional
    void 디비에서_포스트잇을_삭제한다() {
        // given
        boardRepository.save(board);
        Postit savedPostit = postitRepository.save(postit);

        // when
        postitRepository.delete(postit);

        // then
        Optional<Postit> optionalPostit = postitRepository.findById(savedPostit.getId());
        assertThat(optionalPostit).isEmpty();
    }

    @Test
    @Transactional
    void 디비에서_포스트잇을_수정한다() {
        // given
        boardRepository.save(board);
        Postit savedPostit = postitRepository.save(postit);

        // when
        postit.setColor("blue");
        postit.setTitle("bye");
        Postit updatedPostit = postitRepository.save(postit);

        // then
        assertThat(updatedPostit).isEqualTo(postit);
        assertThat(updatedPostit.getId()).isEqualTo(savedPostit.getId());
        assertThat(updatedPostit.getColor()).isEqualTo("blue");
        assertThat(updatedPostit.getTitle()).isEqualTo("bye");
    }
}