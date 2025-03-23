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
import com.example.story.adapter.CategoryAdapter;
import com.example.story.data.AppDatabase;
import com.example.story.data.Category;

import java.util.List;
import java.util.concurrent.Executors;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryActionListener {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private ImageButton addButton;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addButton = view.findViewById(R.id.add_button);
        database = AppDatabase.getInstance(getContext());

        addButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddCategoryFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories();
    }

    private void loadCategories() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Category> categoryList = database.categoryDao().getAllCategories();

            getActivity().runOnUiThread(() -> {
                adapter = new CategoryAdapter(categoryList, CategoryFragment.this);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public void onUpdate(Category category) {
        UpdateCategoryFragment updateFragment = new UpdateCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("catID", category.getCatID());
        updateFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, updateFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDelete(Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.delete_category, null);
        builder.setView(view);

        Button yesButton = view.findViewById(R.id.yes_button);
        Button noButton = view.findViewById(R.id.no_button);

        AlertDialog dialog = builder.create();

        yesButton.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                database.categoryDao().deleteCategory(category);

                getActivity().runOnUiThread(() -> {
                    loadCategories();
                    Toast.makeText(getContext(), "Danh mục đã được xóa", Toast.LENGTH_SHORT).show();
                });
            });

            dialog.dismiss();
        });

        noButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
