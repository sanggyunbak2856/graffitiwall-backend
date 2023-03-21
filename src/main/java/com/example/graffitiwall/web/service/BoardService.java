package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.BoardConverter;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardConverter boardConverter;

    public Long save(BoardSaveDto boardSaveDto) {
        Board board = boardConverter.boardPostDtoToEntity(boardSaveDto);
        User user = userRepository.findById(boardSaveDto.getUserId()).get();
        board.setUser(user);
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).get();
        return boardConverter.entityToBoardResponseDto(board);
    }


}
