package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProducts extends AppCompatActivity {

    EditText name, price, category, description, image;
    Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        name = findViewById(R.id.et_productname);
        price = findViewById(R.id.et_price);
        category = findViewById(R.id.et_category);
        description = findViewById(R.id.et_description);
        image = findViewById(R.id.imageurl);

        addProduct = findViewById(R.id.btn_add);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductEntity productEntity = new ProductEntity();
                productEntity.setProductName(name.getText().toString());
                productEntity.setProductPrice(Integer.parseInt(price.getText().toString()));
                productEntity.setProductCategory(category.getText().toString());
                productEntity.setProductDescription(description.getText().toString());
                productEntity.setProductImage(image.getText().toString());

                if (validateProductDetails(productEntity)) {
                    ProductDatabase productDatabase = ProductDatabase.getProductDatabase(getApplicationContext());
                    ProductsDao productsDao = productDatabase.productsDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            productsDao.addProducts(productEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"Product is Added!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateProductDetails(ProductEntity productEntity) {
        if (productEntity.getProductName().isEmpty()) {
            name.setError("Please fill out the name of the product");
            return false;

        }
        Integer productPrice = productEntity.getProductPrice();
        if (productPrice == null || productPrice <= 0) {
            price.setError("Please enter the valid Price");
            return false;
        }

        if (productEntity.getProductCategory().isEmpty()) {
            category.setError("Please enter Category");
            return false;
        }

        if(productEntity.getProductDescription().isEmpty()) {
            description.setError("Please enter description");
            return false;
        }
        if (productEntity.getProductImage().isEmpty()) {
            image.setError("Please paste Image URL");
            return false;
        }
        return true;

    }
}