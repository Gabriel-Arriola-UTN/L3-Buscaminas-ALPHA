package com.tup.buscaminas.entity;

import com.tup.buscaminas.service.GameService;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cell_row")
    private int row;
    @Column(name = "cell_col")
    private int col;
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
