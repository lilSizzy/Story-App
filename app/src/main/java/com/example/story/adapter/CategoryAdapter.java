package com.example.story.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.story.R;
import com.example.story.data.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private OnCategoryActionListener listener;

    public interface OnCategoryActionListener {
        void onUpdate(Category category);
        void onDelete(Category category);
    }

    public CategoryAdapter(List<Category> categoryList, OnCategoryActionListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getCatName());

        holder.updateButton.setOnClickListener(v -> listener.onUpdate(category));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(category));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        Button updateButton, deleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            updateButton = itemView.findViewById(R.id.update_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
