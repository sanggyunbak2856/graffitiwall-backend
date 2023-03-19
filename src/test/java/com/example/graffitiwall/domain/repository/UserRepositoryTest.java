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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostitRepository postitRepository;

    User user;
    Board board;
    Postit postit;

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
    void 유저를_저장한다() {
        // given

        // when
        User savedUser = userRepository.save(user);

        // then
        User foundUser = userRepository.findById(savedUser.getId()).get();
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @Transactional
    void 유저를_조회한다() {
        // given
        User savedUser = userRepository.save(user);

        // when
        User foundUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @Test
    @Transactional
    void 조회된_유저와_연관된_보드를_조회한다() {
        // given
        board.setUser(user);
        postit.setUser(user);
        postit.setBoard(board);
        User savedUser = userRepository.save(user); // cascade none
        boardRepository.save(board);
        postitRepository.save(postit);

        // when
        User foundUser = userRepository.findById(savedUser.getId()).get();

        // then
        assertThat(foundUser.getBoards()).hasSize(1);
        assertThat(foundUser.getPostits()).hasSize(1);
        assertThat(foundUser.getBoards().get(0).getId()).isNotNull();
        assertThat(foundUser.getPostits().get(0).getId()).isNotNull();
    }

}
