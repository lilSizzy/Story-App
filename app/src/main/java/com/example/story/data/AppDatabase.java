package com.example.story.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, Book.class, Chapter.class, User.class, ReadingHistory.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract BookDao bookDao();
    public abstract ChapterDao chapterDao();
    public abstract UserDao userDao();
    public abstract ReadingHistoryDao readingHistoryDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "story_database"
                    ).fallbackToDestructiveMigration().build();
                    // Thêm Admin mặc định (Chỉ chạy khi database được tạo lần đầu)
                    new Thread(() -> {
                        UserDao userDao = INSTANCE.userDao();
                        if (userDao.getUserByUsername("admin") == null) {
                            User admin = new User();
                            admin.setUsername("admin");
                            admin.setPassword("admin123");
                            admin.setRole(true); // Role = true → Admin
                            userDao.insertUser(admin);
                        }
                    }).start();
                }
            }
        }
        return INSTANCE;
    }
}

