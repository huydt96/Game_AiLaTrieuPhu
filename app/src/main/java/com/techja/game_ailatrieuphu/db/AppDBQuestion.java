package com.techja.game_ailatrieuphu.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techja.game_ailatrieuphu.db.entities.HighScore;
import com.techja.game_ailatrieuphu.db.entities.Question;


@Database(entities = {Question.class, HighScore.class}, version = 2)
public abstract class AppDBQuestion extends RoomDatabase {
    public abstract QuestionDAO questionDAO();
    public abstract HighScoreDAO highScoreDAO();
}
