-- Eliminar tablas si existen
DROP TABLE IF EXISTS cell;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS app_user;

-- Crear tabla de usuarios
CREATE TABLE app_user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      total_score INT DEFAULT 0
);

-- Crear tabla de juegos
CREATE TABLE game (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      rows INT NOT NULL,
                      cols INT NOT NULL,
                      mine_count INT NOT NULL,
                      is_game_over BOOLEAN DEFAULT FALSE,
                      is_won BOOLEAN DEFAULT FALSE,
                      score INT DEFAULT 0,
                      user_id BIGINT,
                      FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- Crear tabla de celdas
CREATE TABLE cell (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      cell_row INT NOT NULL,
                      cell_col INT NOT NULL,
                      is_mine BOOLEAN DEFAULT FALSE,
                      is_revealed BOOLEAN DEFAULT FALSE,
                      is_flagged BOOLEAN DEFAULT FALSE,
                      adjacent_mines INT DEFAULT 0,
                      game_id BIGINT,
                      FOREIGN KEY (game_id) REFERENCES game(id)
);

-- Insertar algunos usuarios de prueba
INSERT INTO app_user (username, password) VALUES ('demo', 'password');