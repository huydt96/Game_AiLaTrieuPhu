package com.techja.game_ailatrieuphu.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.techja.game_ailatrieuphu.db.entities.Question;

import java.util.List;


@Dao
public interface QuestionDAO {
    String SELECT_15_Q = "SELECT * FROM(SELECT * FROM Question WHERE level=1 ORDER by random() LIMIT 1)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=2 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=3 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=4 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=5 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=6 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=7 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=8 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=9 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=10 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=11 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=12 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=13 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=14 ORDER by random() LIMIT 1\t)\n" +
            "UNION\tSELECT * FROM(SELECT * FROM Question WHERE level=15 ORDER by random() LIMIT 1\t)";

    @Query(SELECT_15_Q)
    List<Question> getAllQuestion();

    @Query("SELECT * FROM Question WHERE level = (:level) ORDER by random() LIMIT 1")
    Question getQuestionByLevel(int level);

}
