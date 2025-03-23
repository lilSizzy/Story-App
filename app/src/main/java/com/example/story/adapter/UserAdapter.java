package com.example.story.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.story.R;
import com.example.story.data.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onUpdate(User user);
        void onDelete(User user);
    }

    public UserAdapter(List<User> userList, OnUserActionListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.roleTextView.setText(user.isRole() ? "Admin" : "User"); // Hiển thị quyền

        holder.updateButton.setOnClickListener(v -> listener.onUpdate(user));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateList(List<User> newList) {
        userList = newList;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView, roleTextView;
        ImageButton updateButton, deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.name_text_view);
            roleTextView = itemView.findViewById(R.id.role_text_view);
            updateButton = itemView.findViewById(R.id.update_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
