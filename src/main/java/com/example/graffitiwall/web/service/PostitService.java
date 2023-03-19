package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.PostitRepository;
import com.example.graffitiwall.web.converter.PostitConverter;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostitService {

    private final PostitRepository postitRepository;
    private final BoardRepository boardRepository;
    private final PostitConverter postitConverter;

    @Transactional
    public PostitResponseDto findById(Long id) {
        Optional<Postit> optionalPostit = postitRepository.findById(id);
        return optionalPostit.isEmpty() ? null : postitConverter.entityToPostitResponseDto(optionalPostit.get());
    }

    @Transactional
    public Long save(PostitSaveDto postitSaveDto) {
        Postit postit = postitConverter.postitSaveDtoToEntity(postitSaveDto);
        // 추후 예외처리 필요함
        Board board = boardRepository.findById(postitSaveDto.getBoardId()).get();
        postit.setBoard(board);
        Postit savedPostit = postitRepository.save(postit);
        return savedPostit.getId();
    }

    @Transactional
    public Long update(Long id, PostitUpdateDto postitUpdateDto) {
        Postit foundPostit = postitRepository.findById(id).get();
        postitConverter.postitUpdate(foundPostit, postitUpdateDto);
        return foundPostit.getId();
    }

    @Transactional
    public Long deleteById(Long id) {
        postitRepository.deleteById(id);
        return id;
    }
}
