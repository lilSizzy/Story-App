package com.example.story.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.story.R;
import com.example.story.adapter.BookAdapter;
import com.example.story.data.AppDatabase;
import com.example.story.data.Book;

import java.util.List;
import java.util.concurrent.Executors;

public class BookFragment extends Fragment implements BookAdapter.OnBookActionListener {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private Button addButton;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);
        recyclerView = view.findViewById(R.id.book_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addButton = view.findViewById(R.id.floatingActionButton);
        database = AppDatabase.getInstance(getContext()); // Lấy database instance

        addButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddBookFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBooks();
    }

    private void loadBooks() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Book> bookList = database.bookDao().getAllBooks();

            getActivity().runOnUiThread(() -> {
                adapter = new BookAdapter(bookList, BookFragment.this);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public void onUpdate(Book book) {
        UpdateBookFragment updateBookFragment = new UpdateBookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("bookId", book.getBookID()); // Truyền bookId thay vì Parcelable
        updateBookFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, updateBookFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDelete(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.delete_book_fragment, null);
        builder.setView(view);

        // Ánh xạ các Button từ layout
        Button yesButton = view.findViewById(R.id.yes_button);
        Button noButton = view.findViewById(R.id.no_button);

        // Tạo AlertDialog
        AlertDialog dialog = builder.create();

        // Xử lý sự kiện khi bấm "Yes"
        yesButton.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                database.bookDao().deleteBook(book);

                getActivity().runOnUiThread(() -> {
                    loadBooks(); // Refresh danh sách sách sau khi xóa
                    Toast.makeText(getContext(), "Đã xóa sách", Toast.LENGTH_SHORT).show();
                });
            });

            dialog.dismiss(); // Đóng hộp thoại sau khi xóa
        });
        noButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}