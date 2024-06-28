package com.tup.buscaminas.controller;

import com.tup.buscaminas.entity.Game;
import com.tup.buscaminas.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game createGame(@RequestParam int rows, @RequestParam int cols, @RequestParam int mineCount, @RequestParam Long userId) {
        return gameService.createGame(rows, cols, mineCount, userId);
    }

    @PutMapping("/{id}/reveal")
    public Game revealCell(@PathVariable Long id, @RequestParam int row, @RequestParam int col) {
        return gameService.revealCell(id, row, col);
    }

    @PutMapping("/{id}/flag")
    public Game flagCell(@PathVariable Long id, @RequestParam int row, @RequestParam int col) {
        return gameService.flagCell(id, row, col);
    }

    @GetMapping("/{id}/board")
    public List<List<String>> getGameBoard(@PathVariable Long id) {
        return gameService.getGameBoard(id);
    }
}
