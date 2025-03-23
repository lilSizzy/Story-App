package com.example.story.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "ReadingHistory",indices = {@Index(value = "BookID"),@Index(value = "Username"), @Index(value = "LastChapterID")},
        primaryKeys = {"Username", "BookID", "LastReading"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "Username",
                        childColumns = "Username"),
                @ForeignKey(entity = Book.class,
                        parentColumns = "BookID",
                        childColumns = "BookID"),
                @ForeignKey(entity = Chapter.class,
                        parentColumns = "ChapterID",
                        childColumns = "LastChapterID")
        })
public class ReadingHistory {
    @NonNull
    public String Username;
    @NonNull
    public String BookID;
    public String LastChapterID;
    @NonNull
    public String LastReading;
}

