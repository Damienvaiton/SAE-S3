/*
package com.example.pageacceuil;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuList;
    private OnMenuItemClickListener listener;

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.listener = listener;
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView menuImage;
        TextView menuName;

        public MenuViewHolder(@NonNull View itemView, final OnMenuItemClickListener listener) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.menu_image);
            menuName = itemView.findViewById(R.id.menu_name);

            menuImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onMenuItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MenuAdapter(List<MenuItem> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        MenuViewHolder mvh = new MenuViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem currentItem = menuList.get(position);
        holder.menuImage.setImageResource(currentItem.getItemId());
        holder.menuName.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}*/
