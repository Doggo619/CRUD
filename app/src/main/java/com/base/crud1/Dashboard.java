package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView welcome;
    Button addProducts, viewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcome = findViewById(R.id.tv_welcome);
        addProducts = findViewById(R.id.btn_addproducts);
        viewProducts = findViewById(R.id.btn_viewproducts);

        String name = getIntent().getStringExtra("name");
        welcome.setText("Welcome," + name);

        addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, AddProducts.class));
            }
        });
    }
}