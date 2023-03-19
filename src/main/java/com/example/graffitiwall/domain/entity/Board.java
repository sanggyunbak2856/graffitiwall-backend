package com.example.graffitiwall.domain.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Postit> postits = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Board(String title, String category, boolean isPrivate, String password, User user) {
        this.title = title;
        this.category = category;
        this.isPrivate = isPrivate;
        this.password = password;
        this.user = user;
    }

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getBoards().remove(this);
        }
        this.user = user;
        this.user.getBoards().add(this);
    }
}
