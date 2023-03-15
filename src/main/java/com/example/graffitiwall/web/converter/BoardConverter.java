package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter {
    public Board boardPostDtoToEntity(BoardSaveDto boardSaveDto) {
        return Board.builder()
                .category(boardSaveDto.getCategory())
                .isPrivate(boardSaveDto.isPrivate())
                .title(boardSaveDto.getTitle())
                .password(boardSaveDto.getPassword())
                .build();
    }
}
