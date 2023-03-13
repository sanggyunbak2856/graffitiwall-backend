package com.example.graffitiwall.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "category", length = 255, nullable = true)
    private String category;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "password", length = 30, nullable = true)
    private String password;

    @OneToMany(mappedBy = "board")
    private List<Postit> postits = new ArrayList<>();

    @Builder
    public Board(String title, String category, boolean isPrivate, String password) {
        this.title = title;
        this.category = category;
        this.isPrivate = isPrivate;
        this.password = password;
    }
}
