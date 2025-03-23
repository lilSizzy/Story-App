package com.example.story.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReadingHistoryDao {
    @Insert
    void insert(ReadingHistory history);

    @Query("SELECT * FROM ReadingHistory WHERE Username = :username")
    List<ReadingHistory> getHistoryByUser(String username);
}

