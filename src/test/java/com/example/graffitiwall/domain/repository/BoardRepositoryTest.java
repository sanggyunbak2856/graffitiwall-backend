package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.factory.DummyObjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
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
        user = createFakeUser();
        board = createFakeBoard();
        postit = createFakePostit();
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
        Board savedBoard = boardRepository.save(board);
        Postit postIt1 = createFakePostit();
        Postit postIt2 = createFakePostit();
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
        Board board = createFakeBoard();
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
    void 유저_아이디로_보드들을_조회한다() {
        // given
        User savedUser = userRepository.save(user);
        board.setUser(savedUser);
        boardRepository.save(board);
        Board board2 = createFakeBoard();
        board2.setUser(user);
        boardRepository.save(board2);

        // when
        List<Board> foundBoards = boardRepository.findBoardByUser_Id(savedUser.getId());

        // then
        assertThat(foundBoards).hasSize(2);
        assertThat(foundBoards).contains(board, board2);
    }
}
