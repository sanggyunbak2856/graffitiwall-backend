package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Postit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostitRepository extends JpaRepository<Postit, Long> {
    List<Postit> findPostitByBoard_Id(Long boardId);
    List<Postit> findPostitByUser_Id(Long userRawId);
}
