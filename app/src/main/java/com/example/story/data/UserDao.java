package com.example.story.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM User WHERE Username = :username LIMIT 1")
    User getUser(String username);
    @Query("SELECT * FROM User WHERE Username = :username AND Password = :password")
    User login(String username, String password);
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
    @Query("SELECT * FROM User WHERE Username = :username LIMIT 1")
    User getUserByUsername(String username);
}


