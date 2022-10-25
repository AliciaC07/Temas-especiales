package com.aip.examen;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView description;
    private TextView cost;

    private Button delete;
    private Button send;

    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.txt_name);;
        this.description = itemView.findViewById(R.id.txt_descrip);
        this.cost = itemView.findViewById(R.id.txt_cost);
        this.delete = itemView.findViewById(R.id.delete);
        this.send = itemView.findViewById(R.id.share);
    }

    public Button getSend() {
        return send;
    }

    public void setSend(Button send) {
        this.send = send;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }





    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getCost() {
        return cost;
    }

    public void setCost(TextView cost) {
        this.cost = cost;
    }
}
