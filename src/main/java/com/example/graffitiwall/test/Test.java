package com.example.graffitiwall.test;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.graffitiwall.test.DummyObjectFactory.createFakeBoard;
import static com.example.graffitiwall.test.DummyObjectFactory.createFakeUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class Test {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @PostConstruct
    void postConstruct() {
        log.info("test post construct");
        User user = createFakeUser();
        userRepository.save(user);

        for(int i = 0; i < 1; i++) {
            Board board = createFakeBoard();
            board.setUser(user);
            boardRepository.save(board);
        }
    }
}
