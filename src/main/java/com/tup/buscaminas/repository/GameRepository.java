package com.tup.buscaminas.repository;

import com.tup.buscaminas.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Game g WHERE g.user.id = :userId AND g.isGameOver = false")
    boolean existsUnfinishedGamesByUserId(@Param("userId") Long userId);
}
