package com.example.story.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {
    @PrimaryKey
    @NonNull
    public String Username;
    public String Password;
    public boolean Role;

    @NonNull
    public String getUsername() {
        return Username;
    }

    public void setUsername(@NonNull String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isRole() {
        return Role;
    }

    public void setRole(boolean role) {
        Role = role;
    }


}

