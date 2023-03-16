package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
