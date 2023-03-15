package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.web.dto.board.BoardPostDto;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter {
    public Board boardPostDtoToEntity(BoardPostDto boardPostDto) {
        return Board.builder()
                .category(boardPostDto.getCategory())
                .isPrivate(boardPostDto.isPrivate())
                .title(boardPostDto.getTitle())
                .password(boardPostDto.getPassword())
                .build();
    }
}
