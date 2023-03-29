package com.example.graffitiwall.factory;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.entity.UserStatus;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.board.BoardUpdateDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import com.example.graffitiwall.web.dto.user.UserSaveDto;
import com.example.graffitiwall.web.dto.user.UserUpdateDto;
import com.github.javafaker.Faker;

public class DummyObjectFactory {
    private static Faker faker = new Faker();
    private static int TITLE_LENGTH = 100;
    private static int CONTENTS_LENGTH = 1024;

    public static User createFakeUser() {
        return User.builder()
                .password(faker.internet().password())
                .userId(faker.name().username())
                .email(faker.internet().emailAddress())
                .status(UserStatus.ACTIVE)
                .introduce(faker.lorem().characters(CONTENTS_LENGTH))
                .imageUrl(faker.internet().url())
                .build();
    }

    public static UserSaveDto createFakeUserSaveDto() {
        return UserSaveDto.builder()
                .password(faker.internet().password())
                .userId(faker.name().username())
                .email(faker.internet().emailAddress())
                .introduce(faker.lorem().characters())
                .build();
    }

    public static UserUpdateDto createFakeUserUpdateDto() {
        return UserUpdateDto.builder()
                .password(faker.internet().password())
                .userId(faker.name().username())
                .email(faker.internet().emailAddress())
                .introduce(faker.lorem().characters())
                .build();
    }

    public static Board createFakeBoard() {
        return Board.builder()
                .password(faker.internet().password())
                .isPrivate(faker.random().nextBoolean())
                .category(faker.zelda().character())
                .title(faker.lorem().characters(TITLE_LENGTH))
                .build();
    }

    public static BoardSaveDto createFakeBoardSaveDto(Long userId) {
        return BoardSaveDto.builder()
                .password(faker.internet().password())
                .isPrivate(faker.random().nextBoolean())
                .category(faker.zelda().character())
                .title(faker.lorem().characters())
                .userId(userId)
                .build();
    }

    public static BoardUpdateDto createFakeBoardUpdateDto() {
        return BoardUpdateDto.builder()
                .password(faker.internet().password())
                .isPrivate(faker.random().nextBoolean())
                .category(faker.zelda().character())
                .title(faker.lorem().characters(TITLE_LENGTH))
                .build();
    }

    public static Postit createFakePostit() {
        return Postit.builder()
                .title(faker.lorem().characters(TITLE_LENGTH))
                .contents(faker.lorem().characters(CONTENTS_LENGTH))
                .positionY(faker.random().nextInt(0, 500))
                .positionX(faker.random().nextInt(0, 500))
                .color(faker.color().name())
                .angle(faker.random().nextInt(0, 360))
                .build();
    }

    public static PostitSaveDto createFakePostitSaveDto(Long boardId, Long userId) {
        return PostitSaveDto.builder()
                .title(faker.lorem().characters(TITLE_LENGTH))
                .contents(faker.lorem().characters(CONTENTS_LENGTH))
                .positionY(faker.random().nextInt(0, 500))
                .positionX(faker.random().nextInt(0, 500))
                .color(faker.color().name())
                .angle(faker.random().nextInt(0, 360))
                .boardId(boardId)
                .userId(userId)
                .build();
    }

    public static PostitUpdateDto createFakePostitUpdateDto() {
        return PostitUpdateDto.builder()
                .title(faker.lorem().characters(TITLE_LENGTH))
                .contents(faker.lorem().characters(CONTENTS_LENGTH))
                .positionY(faker.random().nextInt(0, 500))
                .positionX(faker.random().nextInt(0, 500))
                .color(faker.color().name())
                .angle(faker.random().nextInt(0, 360))
                .build();
    }


}
