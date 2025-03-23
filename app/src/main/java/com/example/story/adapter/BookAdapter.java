package com.example.story.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.story.R;
import com.example.story.data.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private OnBookActionListener listener;

    // Interface để xử lý các sự kiện cập nhật và xóa sách
    public interface OnBookActionListener {
        void onUpdate(Book book);
        void onDelete(Book book);
    }

    // Constructor
    public BookAdapter(List<Book> bookList, OnBookActionListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view từ layout item_book.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Lấy dữ liệu từ danh sách và gán vào ViewHolder
        Book book = bookList.get(position);
        holder.bind(book);

        // Xử lý sự kiện khi nhấn vào nút cập nhật
        holder.updateButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpdate(book);
            }
        });

        // Xử lý sự kiện khi nhấn vào nút xóa
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // Cập nhật danh sách sách
    public void setBooks(List<Book> books) {
        this.bookList = books;
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật giao diện
    }

    // ViewHolder để quản lý các view trong mỗi item
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView;
        View updateButton, deleteButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout item_book.xml
            titleTextView = itemView.findViewById(R.id.title_text_view);
            authorTextView = itemView.findViewById(R.id.author_text_view);
            updateButton = itemView.findViewById(R.id.update_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        // Gán dữ liệu vào các view
        public void bind(Book book) {
            titleTextView.setText(book.getBookName());
            authorTextView.setText(book.getAuthor());
        }
    }
}
