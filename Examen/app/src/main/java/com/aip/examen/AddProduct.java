package com.aip.examen;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<>();

    private TextView txtName;
    private TextView txtDecription;
    private TextView txtCost;
    private Button clean;
    private Button add;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

         txtName = findViewById(R.id.name_txt);
         txtDecription = findViewById(R.id.descri_txt);
         txtCost = findViewById(R.id.cost);

        clean = findViewById(R.id.clean_btn);
        add = findViewById(R.id.add_btn);
        back = findViewById(R.id.volver);

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanInputs();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product(txtName.getText().toString(), txtDecription.getText().toString(), Float.parseFloat( txtCost.getText().toString()));
                if (SingletonB.getInstance().repeat(product.getName())){
                    SingletonB.getInstance().addProduct(product);
                }else {
                    Toast.makeText(AddProduct.this, "Esta repetido", Toast.LENGTH_SHORT).show();
                }

                cleanInputs();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });







    }

    public void cleanInputs(){
        txtName.setText("");
        txtDecription.setText("");
        txtCost.setText("");



    }
}