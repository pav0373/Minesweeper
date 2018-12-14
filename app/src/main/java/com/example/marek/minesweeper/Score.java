package com.example.marek.minesweeper;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Score")
public class Score {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Difficulty")
    private int difficulty;

    @ColumnInfo(name = "Time")
    private int time;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


}