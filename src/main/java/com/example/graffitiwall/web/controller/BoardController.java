package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.board.BoardSaveDto;
import com.example.graffitiwall.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public Long save(@RequestBody BoardSaveDto boardSaveDto) {
        Long savedId = boardService.save(boardSaveDto);
        return savedId;
    }
}
