package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView welcome;
    Button addProducts, viewProducts;
    ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcome = findViewById(R.id.tv_welcome);
        addProducts = findViewById(R.id.btn_addproducts);
        viewProducts = findViewById(R.id.btn_viewproducts);
        logout = findViewById(R.id.ib_logout);

        String name = getIntent().getStringExtra("name");
        welcome.setText("Welcome," + name);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                 preferences.edit().clear().apply();
                startActivity(new Intent(Dashboard.this, Login.class));
                finish();
            }
        });
        addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, AddProducts.class));
            }
        });

        viewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, ViewProducts.class));
            }
        });
    }
}