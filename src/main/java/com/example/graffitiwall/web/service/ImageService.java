package com.example.graffitiwall.web.service;

import com.example.graffitiwall.domain.entity.Board;
import com.example.graffitiwall.domain.entity.Image;
import com.example.graffitiwall.domain.entity.User;
import com.example.graffitiwall.domain.repository.BoardRepository;
import com.example.graffitiwall.domain.repository.ImageRepository;
import com.example.graffitiwall.domain.repository.UserRepository;
import com.example.graffitiwall.web.converter.ImageConverter;
import com.example.graffitiwall.web.dto.image.ImageDto;
import com.example.graffitiwall.web.dto.image.UploadImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageConverter imageConverter;

    public Long save(UploadImageDto uploadImageDto) {
        Image img = imageConverter.imagePostDtoToEntity(uploadImageDto);
        User user = userRepository.findById(uploadImageDto.getUserId()).get();
        Board board = boardRepository.findById(uploadImageDto.getBoardId()).get();
        img.setBoard(board);
        img.setUser(user);
        Image saveImage = imageRepository.save(img);
        return saveImage.getId();
    }

    @Transactional
    public ImageDto findById(Long id){
        Image img = imageRepository.findById(id).get();
        return imageConverter.entityToImageResponseDto(img);
    }

    @Transactional
    public List<Image> getAllImages() {
        List<Image> all = imageRepository.findAll();
        return all;
    }

    @Transactional
    public Long deleteById(Long id) {
        imageRepository.deleteById(id);
        return id;
    }
}