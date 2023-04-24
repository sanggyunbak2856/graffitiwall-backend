package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBoardByUser_Id(Long id);
    @Query(value = "SELECT * FROM board ORDER BY RAND() limit 20", nativeQuery = true)
    List<Board> findBoardRandom();
}
