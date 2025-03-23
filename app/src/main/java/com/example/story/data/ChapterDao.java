package com.example.story.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChapterDao {
    @Insert
    void insertChapter(Chapter chapter);

    @Query("SELECT * FROM Chapter WHERE BookID = :bookId")
    List<Chapter> getChaptersByBook(String bookId);
}
