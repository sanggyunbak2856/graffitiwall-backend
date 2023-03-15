package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
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

    public Postit postitSaveDtoToEntity(PostitSaveDto postitSaveDto) {
        return Postit.builder()
                .angle(postitSaveDto.getAngle())
                .contents(postitSaveDto.getContent())
                .color(postitSaveDto.getColor())
                .positionX(postitSaveDto.getPositionX())
                .positionY(postitSaveDto.getPositionY())
                .title(postitSaveDto.getTitle())
                .build();
    }
}
