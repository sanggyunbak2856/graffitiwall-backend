package com.example.graffitiwall.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "postit")
@DynamicInsert
public class Postit extends BaseTime{
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

    @Column(name = "views")
    @ColumnDefault("0")
    private Integer views;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Postit(String title, String color, int positionX, int positionY, String contents,
                  int angle, Board board, User user) {
        this.title = title;
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        this.contents = contents;
        this.angle = angle;
        this.board = board;
        this.user = user;
    }

    public void setBoard(Board board) {
        if(this.board != null) {
            this.board.getPostits().remove(this);
        }
        this.board = board;
        this.board.getPostits().add(this);
    }

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getPostits().remove(this);
        }
        this.user = user;
        this.user.getPostits().add(this);
    }
}
