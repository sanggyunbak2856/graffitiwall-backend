package com.example.graffitiwall.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "s3Url", length = 2000, nullable = false)
    private String s3Url;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Image(String title, String s3Url, User user) {
        this.title = title;
        this.s3Url = s3Url;
        this.user = user;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", s3Url='" + s3Url + '\'' +
                '}';
    }

    public void setBoard(Board board) {
        if(this.board != null) {
            this.board.getImages().remove(this);
        }
        this.board = board;
        this.board.getImages().add(this);
    }

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getImages().remove(this);
        }
        this.user = user;
        this.user.getImages().add(this);
    }
}