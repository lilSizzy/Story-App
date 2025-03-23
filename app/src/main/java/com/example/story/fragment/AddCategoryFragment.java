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

public class AddCategoryFragment extends Fragment {
    private EditText etCatID, etCatName;
    private Button btnAddCategory;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);

        etCatID = view.findViewById(R.id.etCatID);
        etCatName = view.findViewById(R.id.etCatName);
        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        database = AppDatabase.getInstance(getContext());

        btnAddCategory.setOnClickListener(v -> addCategory());

        return view;
    }

    private void addCategory() {
        String catID = etCatID.getText().toString().trim();
        String catName = etCatName.getText().toString().trim();

        if (TextUtils.isEmpty(catID) || TextUtils.isEmpty(catName)) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            Category newCategory = new Category(catID, catName);
            database.categoryDao().insertCategory(newCategory);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack(); // Quay lại màn hình trước đó
            });
        });
    }
}

