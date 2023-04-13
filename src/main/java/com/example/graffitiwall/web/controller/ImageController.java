package com.example.graffitiwall.web.controller;

import com.example.graffitiwall.domain.entity.Image;
import com.example.graffitiwall.web.dto.image.ImageDto;
import com.example.graffitiwall.web.dto.image.UploadImageDto;
import com.example.graffitiwall.web.service.ImageService;
import com.example.graffitiwall.web.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final S3Service s3Service;
    private final ImageService imageService;

    @GetMapping("/api/upload")
    public String goToUpload() {
        return "upload";
    }

    @PostMapping("/api/upload")
    public String uploadFile(UploadImageDto uploadImageDto) throws IOException {
        String url = s3Service.uploadFile(uploadImageDto.getFile());

        uploadImageDto.setUrl(url);
        imageService.save(uploadImageDto);

        return "redirect:/api/all-image-list";
    }

    @GetMapping("/api/all-image-list")
    public String imgListPage(Model model) {
        List<Image> imageList = imageService.getAllImages();
        model.addAttribute("imageList", imageList);
        return "list";
    }

    @GetMapping("/api/one-image")
    public ImageDto findById(@PathVariable Long imageId) {
        return imageService.findById(imageId);
    }

    @DeleteMapping("/{imageId}")
    public Long deleteById(@PathVariable Long imageId) {

        return imageService.deleteById(imageId);
    }
}
