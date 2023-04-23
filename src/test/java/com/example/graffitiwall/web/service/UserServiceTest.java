package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.BoardConverter;
import com.example.graffitiwall.web.converter.PostitConverter;
import com.example.graffitiwall.web.converter.UserConverter;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.user.UserResponseDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.graffitiwall.factory.DummyObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    BoardRepository boardRepository;

    @Mock
    PostitRepository postitRepository;

    @Spy
    UserConverter userConverter;

    @Spy
    BoardConverter boardConverter;

    @Spy
    PostitConverter postitConverter;

    @InjectMocks
    UserService userService;
    User user;

    @BeforeEach
    void beforeEach() {
        user = createFakeUser();
    }

    @Test
    void 유저를_저장한다() {
        // given
        user.setId(1L);
        when(userRepository.save(any())).thenReturn(user);
        UserSaveDto userSaveDto = createFakeUserSaveDto();

        // when
        userService.save(userSaveDto);

        // then
        then(userConverter).should().userSaveDtoToEntity(any());
        then(userRepository).should().save(any());

    }

    @Test
    void 유저를_수정한다() {
        // given
        user.setId(1L);
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        UserUpdateDto userUpdateDto = createFakeUserUpdateDto();

        // when
        IdResponseDto updatedUserId = userService.update(user.getId(), userUpdateDto);

        // then
        then(userConverter).should().userUpdate(any(), any());
        assertThat(updatedUserId.getId()).isEqualTo(user.getId());
    }

    @Test
    void 유저를_조회한다() {
        // given
        user.setId(1L);
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        // when
        UserResponseDto userResponseDto = userService.findById(user.getId());

        // then
        then(userRepository).should().findById(any());
    }

    @Test
    void 유저를_비활성화한다() {
        // given
        user.setId(1L);
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        // when
        userService.deactivateUser(user.getId());

        // then
        then(userRepository).should().findById(any());
        assertThat(user.getStatus()).isEqualTo(UserStatus.INACTIVE);
    }

    @Test
    void 유저_아이디로_보드들을_조회한다() {
        // given
        user.setId(1L);
        Board board1 = createFakeBoard();
        Board board2 = createFakeBoard();
        board1.setUser(user);
        board2.setUser(user);
        given(boardRepository.findBoardByUser_Id(any())).willReturn(List.of(board1, board2));

        // when
        List<BoardResponseDto> responseDtoList = userService.findBoardByUserId(user.getId());

        // then
        assertThat(responseDtoList).hasSize(2);
    }

    @Test
    void 유저_아이디로_포스트잇들을_조회한다() {
        // given
        user.setId(1L);
        Board board = createFakeBoard();
        board.setUser(user);
        Postit postit1 = createFakePostit();
        Postit postit2 = createFakePostit();
        postit1.setBoard(board);
        postit1.setUser(user);
        postit2.setBoard(board);
        postit2.setUser(user);
        given(postitRepository.findPostitByUser_Id(any())).willReturn(List.of(postit1, postit2));

        // when
        List<PostitResponseDto> postitByUserId = userService.findPostitByUserId(1L);

        // then
        assertThat(postitByUserId).hasSize(2);
    }
}