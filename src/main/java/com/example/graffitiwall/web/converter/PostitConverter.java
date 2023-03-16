package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveAndUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class PostitConverter {
    public PostitResponseDto entityToPostitResponseDto(Postit postit) {
        return PostitResponseDto.builder()
                .angle(postit.getAngle())
                .boardId(postit.getBoard().getId())
                .postitId(postit.getId())
                .color(postit.getColor())
                .contents(postit.getContents())
                .createdAt(postit.getCreatedAt())
                .updatedAt(postit.getUpdatedAt())
                .positionX(postit.getPositionX())
                .positionY(postit.getPositionY())
                .title(postit.getTitle())
                .views(postit.getViews())
                .build();
    }

    public Postit postitSaveDtoToEntity(PostitSaveAndUpdateDto postitSaveAndUpdateDto) {
        return Postit.builder()
                .angle(postitSaveAndUpdateDto.getAngle())
                .contents(postitSaveAndUpdateDto.getContent())
                .color(postitSaveAndUpdateDto.getColor())
                .positionX(postitSaveAndUpdateDto.getPositionX())
                .positionY(postitSaveAndUpdateDto.getPositionY())
                .title(postitSaveAndUpdateDto.getTitle())
                .build();
    }
}
