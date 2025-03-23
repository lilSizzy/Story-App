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

public class UpdateBookFragment extends Fragment {

    private EditText titleEditText, authorEditText;
    private AppDatabase database;
    private String bookId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_book_fragment, container, false);

        titleEditText = view.findViewById(R.id.title_edit_text);
        authorEditText = view.findViewById(R.id.author_edit_text);
        Button updateButton = view.findViewById(R.id.update_button);
        database = AppDatabase.getInstance(requireContext());

        // Lấy bookId từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookId = bundle.getString("bookId");
            loadBookDetails(bookId);
        }

        updateButton.setOnClickListener(v -> updateBook());

        return view;
    }

    private void loadBookDetails(String bookId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Book book = database.bookDao().getBookById(bookId);
            requireActivity().runOnUiThread(() -> {
                titleEditText.setText(book.getBookName());
                authorEditText.setText(book.getAuthor());
            });
        });
    }

    private void updateBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book(title, author);
        book.setBookID(bookId);

        Executors.newSingleThreadExecutor().execute(() -> {
            database.bookDao().updateBook(book);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Đã cập nhật sách", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack(); // Quay lại fragment trước
            });
        });
    }
}
