package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.Postit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostitRepository extends JpaRepository<Postit, Long> {
}
