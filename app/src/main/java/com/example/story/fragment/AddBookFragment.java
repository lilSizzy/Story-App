package com.example.story.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.story.R;
import com.example.story.data.AppDatabase;
import com.example.story.data.Book;

import java.util.concurrent.Executors;

public class AddBookFragment extends Fragment {

    private EditText titleEditText, authorEditText;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_book_fragment, container, false);

        titleEditText = view.findViewById(R.id.title_edit_text);
        authorEditText = view.findViewById(R.id.author_edit_text);
        Button saveButton = view.findViewById(R.id.save_button);
        database = AppDatabase.getInstance(requireContext());

        saveButton.setOnClickListener(v -> saveBook());

        return view;
    }

    private void saveBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book(title, author);

        Executors.newSingleThreadExecutor().execute(() -> {
            database.bookDao().insertBook(book);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Đã thêm sách", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack(); // Quay lại fragment trước
            });
        });
    }
}
