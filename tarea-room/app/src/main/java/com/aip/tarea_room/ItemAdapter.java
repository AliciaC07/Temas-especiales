package com.aip.tarea_room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aip.tarea_room.model.Product;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<Product> products = new ArrayList<>();

    public ItemAdapter() {

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

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setList(List<Product> productList) {
        this.products = productList;
        notifyDataSetChanged();
    }
}
