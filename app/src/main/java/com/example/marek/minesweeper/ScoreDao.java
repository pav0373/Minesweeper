package com.example.marek.minesweeper;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT COUNT(*) from score")
    int countScores();

    @Insert
    void insertAll(Score... scores);

    @Delete
    void delete(Score score);
}