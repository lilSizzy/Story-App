package com.example.story.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {


    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();
    @Insert
    void insertCategory(Category category);

    @Delete
    void deleteCategory(Category category);
    @Query("SELECT * FROM Category WHERE CatName = :categoryName LIMIT 1")
    Category getCategoryByName(String categoryName);

    @Update
    void updateCategory(Category category);
}
