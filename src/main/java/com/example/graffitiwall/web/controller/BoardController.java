package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.IdResponseDto;
import com.example.graffitiwall.web.dto.board.BoardResponseDto;
import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.dto.board.BoardUpdateDto;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.service.BoardService;
import com.example.graffitiwall.web.service.PostitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;
    private final PostitService postitService;

    @PostMapping
    public Long save(@RequestBody BoardSaveDto boardSaveDto) {
        Long savedId = boardService.save(boardSaveDto);
        return savedId;
    }

    @GetMapping("/{boardId}")
    public BoardResponseDto findById(@PathVariable  Long boardId) {
        return boardService.findById(boardId);
    }

    @GetMapping("/{boardId}/postits")
    public List<PostitResponseDto> findPostitByBoardId(@PathVariable Long boardId) {
        return postitService.findPostitByBoardId(boardId);
    }

    @PatchMapping("/{boardId}")
    public IdResponseDto update(@PathVariable Long boardId, @RequestBody BoardUpdateDto updateDto) {
        return boardService.update(boardId, updateDto);
    }

    @DeleteMapping("/{boardId}")
    public IdResponseDto deleteById(@PathVariable Long boardId) {
        return boardService.deleteById(boardId);
    }
}
