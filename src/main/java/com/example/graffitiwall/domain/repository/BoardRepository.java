package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBoardByUser_Id(Long id);
}
