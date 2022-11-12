package com.aip.tarea_room;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.aip.tarea_room.model.Product;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<Product> products = new ArrayList<>();

    public ItemAdapter() {

    }
    public interface OnClickListener{
        void onItemClick(View itemView, int position);
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        Product product = products.get(position);
        holder.getName().setText(product.getName());
        holder.getBrand().setText(product.getBrand());
        holder.getPrice().setText(product.getPrice().toString());
        Picasso.get().load(product.getImageUrl())
                .into(holder.getImageView());
        holder.getEdit().setOnClickListener(view -> {
            if (listener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(holder.itemView, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setList(List<Product> productList) {
        this.products = productList;
        notifyDataSetChanged();
    }
    public List<Product> getProducts(){
        return products;
    }
}
