package com.tup.buscaminas.service;

import com.tup.buscaminas.entity.Game;

import java.util.List;

public interface GameService {
    public Game createGame(int rows, int cols, int mineCount, Long userId);
    public Game revealCell(Long gameId, int row, int col);
    public Game flagCell(Long gameId, int row, int col);
    public List<List<String>> getGameBoard(Long gameId);
}
