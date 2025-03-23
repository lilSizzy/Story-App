package com.example.story.fragment;


import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.story.data.Category;

import java.util.concurrent.Executors;

public class UpdateCategoryFragment extends Fragment {
    private EditText edtCategoryName;
    private Button btnUpdate, btnCancel;
    private AppDatabase database;
    private String categoryId; // Lưu CatID của danh mục cần cập nhật

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_category, container, false);

        edtCategoryName = view.findViewById(R.id.edtCategoryName);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnCancel = view.findViewById(R.id.btnCancel);
        database = AppDatabase.getInstance(getContext());

        // Lấy CatID từ Bundle
        if (getArguments() != null) {
            categoryId = getArguments().getString("catID");
            loadCategoryDetails(categoryId);
        }

        btnUpdate.setOnClickListener(v -> updateCategory());
        btnCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    // Load thông tin danh mục hiện tại lên EditText
    private void loadCategoryDetails(String catID) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Category category = database.categoryDao().getCategoryByName(catID);
            if (category != null) {
                getActivity().runOnUiThread(() -> edtCategoryName.setText(category.getCatName()));
            }
        });
    }

    // Hàm cập nhật danh mục
    private void updateCategory() {
        String newCategoryName = edtCategoryName.getText().toString().trim();

        if (TextUtils.isEmpty(newCategoryName)) {
            Toast.makeText(getContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            Category category = new Category(categoryId, newCategoryName);
            database.categoryDao().updateCategory(category);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Cập nhật danh mục thành công", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }
}
