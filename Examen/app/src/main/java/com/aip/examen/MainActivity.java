package com.aip.examen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aip.examen.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button addProduct;

    private Integer SPAM_COUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        ArrayList<Product> products = SingletonB.getInstance().getProducts();
        addProduct = findViewById(R.id.add_product);



        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            SPAM_COUNT = 2;
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), SPAM_COUNT));

        recyclerView.setAdapter( new ItemAdapter(this, products));

        ItemAdapter itemAdapter = (ItemAdapter) recyclerView.getAdapter();
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Product p = SingletonB.getInstance().find(itemView.findViewById(R.id.txt_name).toString());
                String message = "";
                message = "Producto: "+p.getName()+", Descripcion: "+ p.getDescription()+", Costo: "+p.getCost();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);



                Toast.makeText(MainActivity.this," was clicked!", Toast.LENGTH_SHORT).show();
            }
        });



        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProduct.class);
                startActivity(intent);
            }
        });
    }


}