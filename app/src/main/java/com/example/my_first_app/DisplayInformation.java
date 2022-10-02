package com.example.my_first_app;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_information);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Message");

        TextView textView = findViewById(R.id.txt_message);
        textView.setText(message);
    }
}