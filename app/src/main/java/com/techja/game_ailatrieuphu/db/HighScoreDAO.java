package com.techja.game_ailatrieuphu.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.techja.game_ailatrieuphu.db.entities.HighScore;

import java.util.List;


@Dao
public interface HighScoreDAO {
    @Query("SELECT * FROM HighScore")
    List<HighScore> getAllScore();

    @Insert
    void insertScore(HighScore... highScores);
}
