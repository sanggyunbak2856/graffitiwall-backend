package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.BoardConverter;
import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.board.BoardUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardConverter boardConverter;

    @Transactional
    public List<BoardResponseDto> findAllBoards() {
        return boardRepository.findAll()
                .stream().map(boardConverter::entityToBoardResponseDto).toList();
    }

    @Transactional
    public Long save(BoardSaveDto boardSaveDto) {
        Board board = boardConverter.boardPostDtoToEntity(boardSaveDto);
        User user = userRepository.findById(boardSaveDto.getUserId()).get();
        board.setUser(user);
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    @Transactional
    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).get();
        board.setViews(board.getViews() + 1);
        return boardConverter.entityToBoardResponseDto(board);
    }

    @Transactional
    public IdResponseDto update(Long id, BoardUpdateDto updateDto) {
        Board board = boardRepository.findById(id).get();
        boardConverter.update(board, updateDto);
        return IdResponseDto.builder().id(board.getId()).build();
    }

    @Transactional
    public IdResponseDto deleteById(Long id) {
        boardRepository.deleteById(id);
        return IdResponseDto.builder().id(id).build();
    }

    @Transactional
    public List<BoardResponseDto> findBoardsRandom() {
        return boardRepository.findBoardRandom()
                .stream().map(boardConverter::entityToBoardResponseDto).toList();
    }

    @Transactional
    public List<BoardResponseDto> findPopularBoards() {
        return boardRepository.findPopularBoards()
                .stream().map(boardConverter::entityToBoardResponseDto).toList();
    }
}
