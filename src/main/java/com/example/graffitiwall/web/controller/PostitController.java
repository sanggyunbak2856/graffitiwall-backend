package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.service.PostitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postit")
public class PostitController {

    private final PostitService postitService;

    @PostMapping
    public Long save(@RequestBody PostitSaveDto postitSaveDto) {
        return postitService.save(postitSaveDto);
    }

    @GetMapping("/{postitId}")
    public PostitResponseDto findByid(@PathVariable Long postitId) {
        return postitService.findById(postitId);
    }
}
