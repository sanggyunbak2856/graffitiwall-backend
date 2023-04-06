package com.example.graffitiwall.web.converter;

import com.example.graffitiwall.domain.entity.Postit;
import com.example.graffitiwall.web.dto.postit.PostitResponseDto;
import com.example.graffitiwall.web.dto.postit.PostitSaveDto;
import com.example.graffitiwall.web.dto.postit.PostitUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class PostitConverter {
    public PostitResponseDto entityToPostitResponseDto(Postit postit) {
        return PostitResponseDto.builder()
                .angle(postit.getAngle())
                .boardId(postit.getBoard().getId())
                .userId(postit.getUser().getId())
                .postitId(postit.getId())
                .color(postit.getColor())
                .contents(postit.getContents())
                .createdAt(postit.getCreatedAt())
                .updatedAt(postit.getUpdatedAt())
                .positionX(postit.getPositionX())
                .positionY(postit.getPositionY())
                .title(postit.getTitle())
                .views(postit.getViews())
                .writer(postit.getUser().getUserId())
                .font(postit.getFont())
                .sizeX(postit.getSizeX())
                .sizeY(postit.getSizeY())
                .build();
    }

    public Postit postitSaveDtoToEntity(PostitSaveDto postitSaveDto) {
        return Postit.builder()
                .angle(postitSaveDto.getAngle())
                .contents(postitSaveDto.getContents())
                .color(postitSaveDto.getColor())
                .positionX(postitSaveDto.getPositionX())
                .positionY(postitSaveDto.getPositionY())
                .title(postitSaveDto.getTitle())
                .font(postitSaveDto.getFont())
                .sizeX(postitSaveDto.getSizeX())
                .sizeY(postitSaveDto.getSizeY())
                .build();
    }

    public void postitUpdate(Postit postit, PostitUpdateDto postitUpdateDto) {
        postit.setTitle(postitUpdateDto.getTitle());
        postit.setColor(postitUpdateDto.getColor());
        postit.setAngle(postitUpdateDto.getAngle());
        postit.setContents(postitUpdateDto.getContents());
        postit.setPositionX(postitUpdateDto.getPositionX());
        postit.setPositionY(postitUpdateDto.getPositionY());
        postit.setSizeX(postitUpdateDto.getSizeX());
        postit.setSizeY(postitUpdateDto.getSizeY());
        postit.setFont(postitUpdateDto.getFont());
    }
}
