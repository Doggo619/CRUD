package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProductActivity extends AppCompatActivity {
    private EditText etProductName;
    private EditText etProductPrice;
    private EditText etProductCategory;
    private EditText etProductDescription;
    private EditText etProductImage;
    private Button btnUpdate;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Initialize your EditText and Button views
        etProductName = findViewById(R.id.et_editproductname);
        etProductPrice = findViewById(R.id.et_editprice);
        etProductCategory = findViewById(R.id.et_editcategory);
        etProductDescription = findViewById(R.id.et_editdescription);
        etProductImage = findViewById(R.id.editimageurl);
        btnUpdate = findViewById(R.id.btn_update);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Retrieve the product data from the Intent
        int productId = getIntent().getIntExtra("productId", -1);
        String name = getIntent().getStringExtra("name");
        Integer price = getIntent().getIntExtra("price",0);
        String category = getIntent().getStringExtra("category");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");

        if (name != null) {
            // Prefill the EditText fields with product data
            etProductName.setText(name);
            etProductPrice.setText(String.valueOf(price));
            etProductCategory.setText(category);
            etProductDescription.setText(description);
            etProductImage.setText(image);
        }

        // Set an onClickListener for the "Update" button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = etProductName.getText().toString();
                int updatedPrice = Integer.parseInt(etProductPrice.getText().toString());
                String updatedCategory = etProductCategory.getText().toString();
                String updatedDescription = etProductDescription.getText().toString();
                String updatedImage = etProductImage.getText().toString();

                // Create an updated product entity
                ProductEntity updatedProduct = new ProductEntity();
                updatedProduct.setId(productId); // Set the ID of the original product
                updatedProduct.setProductName(updatedName);
                updatedProduct.setProductPrice(updatedPrice);
                updatedProduct.setProductCategory(updatedCategory);
                updatedProduct.setProductDescription(updatedDescription);
                updatedProduct.setProductImage(updatedImage);

                productViewModel.updateProduct(updatedProduct);

                // Show a toast message indicating the update
                Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
