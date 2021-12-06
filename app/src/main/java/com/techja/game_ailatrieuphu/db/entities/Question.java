package com.techja.game_ailatrieuphu.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    public int id;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "question")
    public String question;

    @ColumnInfo(name = "casea")
    public String caseA;

    @ColumnInfo(name = "caseb")
    public String caseB;

    @ColumnInfo(name = "casec")
    public String caseC;

    @ColumnInfo(name = "cased")
    public String caseD;

    @ColumnInfo(name = "truecase")
    public int trueCase;

    @NonNull
    @Override
    public String toString() {
        return "question='" + question + '\'';
    }
}
