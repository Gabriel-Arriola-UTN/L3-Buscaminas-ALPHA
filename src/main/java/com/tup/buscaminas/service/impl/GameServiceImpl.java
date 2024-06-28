package com.tup.buscaminas.service.impl;

import com.tup.buscaminas.entity.Cell;
import com.tup.buscaminas.entity.Game;
import com.tup.buscaminas.entity.User;
import com.tup.buscaminas.repository.GameRepository;
import com.tup.buscaminas.repository.UserRepository;
import com.tup.buscaminas.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Game createGame(int rows, int cols, int mineCount, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Game game = new Game();

        //TODO: completar metodo para crear una partida
        // debe iniciar con el puntaje en cero 0
        // tip: ver metodos getCells, placeMinesRandom, calculateAdjacentMines

        return gameRepository.save(game);
    }



    @Override
    public Game revealCell(Long gameId, int row, int col) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.isGameOver()) {
            throw new RuntimeException("Game is already over");
        }

        Cell cell = getCellAt(game, row, col);
        if (cell.isRevealed() || cell.isFlagged()) {
            return game;
        }

        cell.setRevealed(true);

        if (cell.isMine()) {
            game.setGameOver(true);
            return gameRepository.save(game);
        }

        game.setScore(game.getScore()+10);

        if (cell.getAdjacentMines() == 0) {
            revealAdjacentCells(game, row, col);
        }

        if (isGameWon(game)) {
            game.setWon(true);
            game.setGameOver(true);
            game.setScore(game.getScore()+100);

            User user = game.getUser();
            user.setTotalScore(user.getTotalScore()+game.getScore());
            userRepository.save(user);
        }

        return gameRepository.save(game);
    }

    @Override
    public Game flagCell(Long gameId, int row, int col) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.isGameOver()) {
            throw new RuntimeException("Game is already over");
        }
        List<Cell> cells = game.getCells();
        if (cells == null || cells.isEmpty()) {
            throw new RuntimeException("No cells found for this game");
        }

        Cell cell = cells.stream()
                .filter(c -> c.getRow() == row && c.getCol() == col)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cell not found"));

        if (!cell.isRevealed()) {
            cell.setFlagged(!cell.isFlagged());
        }
        return gameRepository.save(game);
    }

    private int countAdjacentMines(List<Cell> cells, int row, int col, int maxRows, int maxCols) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < maxRows && newCol >= 0 && newCol < maxCols) {
                    Cell adjacentCell = cells.get(newRow * maxCols + newCol);
                    if (adjacentCell.isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void revealAdjacentCells(Game game, int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < game.getRows() && newCol >= 0 && newCol < game.getCols()) {
                    Cell adjacentCell = getCellAt(game, newRow, newCol);
                    if (!adjacentCell.isRevealed() && !adjacentCell.isFlagged()) {
                        adjacentCell.setRevealed(true);
                        if (adjacentCell.getAdjacentMines() == 0) {
                            revealAdjacentCells(game, newRow, newCol);
                        }
                    }
                }
            }
        }
    }

    private boolean isGameWon(Game game) {
        // TODO: validar si la partida ha finalizado -> TRUE: las cedas contienen una mina o ya fueron descubiertas

    }

    private Cell getCellAt(Game game, int row, int col) {
        return game.getCells().get(row * game.getCols() + col);
    }

    public List<List<String>> getGameBoard(Long gameId) {
        //TODO: buscar un juego por ID validando su existencia, caso contrario arrojar la excepcion
        //tip: ver metodo paintBoard

    }

    private void paintBoard(Game game, List<List<String>> board) {
        for (int i = 0; i < game.getRows(); i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < game.getCols(); j++) {
                Cell cell = getCellAt(game, i, j);
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        row.add("*");
                    } else {
                        row.add(String.valueOf(cell.getAdjacentMines()));
                    }
                } else if (cell.isFlagged()) {
                    row.add("F");
                } else {
                    row.add("-");
                }
            }
            board.add(row);
        }
    }

    private void calculateAdjacentMines(int rows, int cols, List<Cell> cells) {
        for (Cell cell : cells) {
            if (!cell.isMine()) {
                cell.setAdjacentMines(countAdjacentMines(cells, cell.getRow(), cell.getCol(), rows, cols));
            }
        }
    }

    private void placeMinesRandom(int mineCount, List<Cell> cells) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < mineCount) {
            int index = random.nextInt(cells.size());
            Cell cell = cells.get(index);
            if (!cell.isMine()) {
                cell.setMine(true);
                minesPlaced++;
            }
        }
    }

    private List<Cell> getCells(int rows, int cols, Game game) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell();
                cell.setRow(i);
                cell.setCol(j);
                cell.setMine(false);
                cell.setRevealed(false);
                cell.setFlagged(false);
                cell.setAdjacentMines(0);
                cell.setGame(game);
                cells.add(cell);
            }
        }
        return cells;
    }

}
