package com.techja.game_ailatrieuphu.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.ToString;

@ToString
@Entity(tableName = "HighScore")
public class HighScore {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int id;

    @NonNull
    @ColumnInfo(name = "Name")
    public String name;

    @NonNull
    @ColumnInfo(name = "Score")
    public int scoreUser;


}
