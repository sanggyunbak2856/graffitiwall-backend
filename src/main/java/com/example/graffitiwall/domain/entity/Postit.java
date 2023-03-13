package com.example.graffitiwall.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "postit")
public class Postit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "color", length = 50, nullable = false)
    private String color;

    @Column(name = "position_x", nullable = false)
    private int positionX;

    @Column(name = "position_y", nullable = false)
    private int positionY;

    @Column(name = "contents", length = 2048, nullable = true)
    @Lob
    private String contents;

    @Column(name = "angle", nullable = false)
    private int angle;

    @Column(columnDefinition = "integer default 0")
    private Integer views;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Postit(String title, String color, int positionX, int positionY, String contents,
                  int angle, LocalDateTime createdAt, LocalDateTime updatedAt, Board board) {
        this.title = title;
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        this.contents = contents;
        this.angle = angle;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.board = board;
    }
}
