package com.example.story;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.story.data.AppDatabase;
import com.example.story.fragment.BookFragment;
import com.example.story.fragment.CategoryFragment;
import com.example.story.fragment.ChapterFragment;
import com.example.story.fragment.HistoryFragment;
import com.example.story.fragment.UserFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    public static AppDatabase database;
    private BottomNavigationView bottomNavigation2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        AppDatabase db = AppDatabase.getInstance(this);
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UserFragment())
                    .commit();
        }

        bottomNavigation2 = findViewById(R.id.bottomNavigation2);
        bottomNavigation2.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_user) {
                selectedFragment = new UserFragment();
            } else if (itemId == R.id.nav_category) {
                selectedFragment = new CategoryFragment();
            } else if (itemId == R.id.nav_book) {
                selectedFragment = new BookFragment();
            } else if (itemId == R.id.nav_chapter) {
                selectedFragment = new ChapterFragment();
            } else if (itemId == R.id.nav_history) {
                selectedFragment = new HistoryFragment();}

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

}