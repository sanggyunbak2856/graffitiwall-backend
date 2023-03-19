package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
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
public class BoardRepositoryTest {

    @Autowired
    public BoardRepository boardRepository;

    @Autowired
    public PostitRepository postitRepository;

    @Autowired
    public UserRepository userRepository;

    Board board;
    Postit postit;
    User user;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .status(UserStatus.ACTIVE)
                .password("p")
                .email("email@email.c")
                .imageUrl("po.p")
                .userId("userA")
                .build();
        board = Board.builder()
                .category("category")
                .isPrivate(false)
                .title("hello")
                .user(user)
                .build();
        postit = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .board(board)
                .user(user)
                .angle(10)
                .build();
    }

    @AfterEach
    void afterEach() {
        postitRepository.deleteAll();
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void 디비에_보드를_저장한다() {
        // given
        userRepository.save(user);

        // when
        Board savedBoard = boardRepository.save(board);

        // then
        assertThat(savedBoard).isEqualTo(board);
    }

    @Test
    @Transactional
    void 디비에서_보드를_삭제한다() {
        // given
        userRepository.save(user);
        Board savedBoard = boardRepository.save(board);

        // when
        boardRepository.delete(board);

        // then
        Optional<Board> foundBoard = boardRepository.findById(savedBoard.getId());
        assertThat(foundBoard).isEmpty();

    }

    @Test
    @Transactional
    void 디비에서_보드를_삭제시_보드에_있던_포스트잇들이_삭제된다() {
        // given
        Board board = Board.builder()
                .title("hello board")
                .isPrivate(false)
                .category("category")
                .user(user)
                .build();
        Board savedBoard = boardRepository.save(board);
        Postit postIt1 = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .board(board)
                .user(user)
                .angle(10)
                .build();
        Postit postIt2 = Postit.builder()
                .title("hello")
                .positionY(10)
                .positionX(10)
                .contents("bye world")
                .color("blue")
                .board(board)
                .user(user)
                .angle(10)
                .build();
        Postit savedPostit1 = postitRepository.save(postIt1);
        Postit savedPostit2 = postitRepository.save(postIt2);
        board.getPostits().add(savedPostit1);
        board.getPostits().add(savedPostit2);
        boardRepository.save(board);

        // when
        boardRepository.delete(board);

        // then
        Optional<Board> foundBoard = boardRepository.findById(savedBoard.getId());
        assertThat(foundBoard).isEmpty();
        Optional<Postit> optionalPostit1 = postitRepository.findById(savedPostit1.getId());
        Optional<Postit> optionalPostit2 = postitRepository.findById(savedPostit2.getId());
        assertThat(optionalPostit1).isEmpty();
        assertThat(optionalPostit2).isEmpty();
    }
    
    @Test
    @Transactional
    void 디비에서_보드를_조회한다() {
        // given
        Board board = Board.builder()
                .title("hello board")
                .isPrivate(false)
                .category("category")
                .build();
        Board savedBoard = boardRepository.save(board);

        // when
        Optional<Board> foundBoard = boardRepository.findById(savedBoard.getId());

        // then
        assertThat(foundBoard.get()).isEqualTo(savedBoard);
    }

    @Test
    @Transactional
    void 디비에서_보드를_수정한다() {
        // given
        Board board = Board.builder()
                .title("hello board")
                .isPrivate(false)
                .category("category")
                .build();
        Board savedBoard = boardRepository.save(board);

        // when
        board.setTitle("bye board");
        Board updatedBoard = boardRepository.save(board);

        // then
        assertThat(updatedBoard.getTitle()).isEqualTo("bye board");
        assertThat(updatedBoard.getId()).isEqualTo(savedBoard.getId());
    }

    @Test
    @Transactional
    void 보드가_삭제될_때_포스트잇도_함께_삭제된다() {
        // given
        userRepository.save(user);
        Board board = Board.builder()
                .title("hello board")
                .isPrivate(false)
                .category("category")
                .build();
        board.setUser(user);
        Board savedBoard = boardRepository.save(board);
        Postit postit = Postit.builder()
                .title("postit")
                .positionY(10)
                .positionX(10)
                .contents("hello world")
                .color("red")
                .user(user)
                .angle(10)
                .build();
        postit.setBoard(board);
        postit.setUser(user);
        Postit savedPostit = postitRepository.save(postit);

        // when
        boardRepository.delete(board);
    }
}
