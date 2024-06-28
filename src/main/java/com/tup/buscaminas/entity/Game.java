package com.tup.buscaminas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rows;
    private int cols;
    private int mineCount;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Cell> cells;
    private boolean isGameOver;
    private boolean isWon;
    private int score;
    @ManyToOne
    private User user;
}
