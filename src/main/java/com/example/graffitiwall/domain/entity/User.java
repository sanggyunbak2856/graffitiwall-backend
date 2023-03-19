package com.example.graffitiwall.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "image_url", length = 500, nullable = true)
    private String imageUrl;

    @Lob
    @Column(name = "introduce", length = 2048, nullable = true)
    private String introduce;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Board> boards;

    @OneToMany(mappedBy = "user")
    private List<Postit> postits;

    @Builder
    public User(String userId, String password, String email, String imageUrl, String introduce, UserStatus status) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
        this.status = status;
    }
}
