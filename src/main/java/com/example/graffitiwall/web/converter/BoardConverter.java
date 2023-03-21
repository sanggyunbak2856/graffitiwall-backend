package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
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

    public BoardResponseDto entityToBoardResponseDto(Board board) {
        return BoardResponseDto.builder()
                .category(board.getCategory())
                .createdAt(board.getCreatedAt())
                .isPrivate(board.isPrivate())
                .password(board.getPassword())
                .updatedAt(board.getUpdatedAt())
                .title(board.getTitle())
                .boardId(board.getId())
                .userId(board.getUser().getId())
                .build();
    }
}
