package com.example.graffitiwall.domain.repository;

import com.example.graffitiwall.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
    User findByUserId(String userId);
}
