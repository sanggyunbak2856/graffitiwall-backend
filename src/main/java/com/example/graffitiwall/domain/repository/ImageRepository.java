package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findMyImage(Long id);
}
