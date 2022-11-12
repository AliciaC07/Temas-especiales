package com.aip.tarea_room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView brand;
    private TextView price;
    private ImageView imageView;
    private Button edit;
    private Button addToCart;

    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.product_name);
        this.brand = itemView.findViewById(R.id.brand);
        this.imageView = itemView.findViewById(R.id.imageView);
        this.addToCart = itemView.findViewById(R.id.btn_add);
        this.price = itemView.findViewById(R.id.price_txt);
        this.edit = itemView.findViewById(R.id.btn_edit);
    }
}
