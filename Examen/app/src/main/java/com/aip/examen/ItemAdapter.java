package com.aip.examen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private final Context context;
    private ArrayList<Product> products = new ArrayList<>();
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ItemAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.products = list;
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
        holder.getDescription().setText(product.getDescription());
        holder.getCost().setText(product.getCost().toString());
        holder.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product p = SingletonB.getInstance().find(holder.getName().toString());
                String message = "";
                message = "Producto: "+p.getName()+", Descripcion: "+ p.getDescription()+", Costo: "+p.getCost();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
