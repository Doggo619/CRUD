package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

public class EditProductActivity extends AppCompatActivity {
    private EditText etProductName;
    private EditText etProductPrice;
    private EditText etProductCategory;
    private EditText etProductDescription;
    private ImageView productImageView;
    private Uri selectedImageUri;
    private ProductViewModel productViewModel;

    private static final int GALLERY_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        etProductName = findViewById(R.id.et_editproductname);
        etProductPrice = findViewById(R.id.et_editprice);
        etProductCategory = findViewById(R.id.et_editcategory);
        etProductDescription = findViewById(R.id.et_editdescription);
        productImageView = findViewById(R.id.iv_editselectedimage);
        Button btnSelectImage = findViewById(R.id.btn_editimage);
        Button btnUpdate = findViewById(R.id.btn_update);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        int productId = getIntent().getIntExtra("productId", -1);
        String userId = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("name");
        Integer price = getIntent().getIntExtra("price",0);
        String category = getIntent().getStringExtra("category");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");


            // Prefill the EditText fields with product data
            etProductName.setText(name);
            etProductPrice.setText(String.valueOf(price));
            etProductCategory.setText(category);
            etProductDescription.setText(description);
            Picasso.get()
                .load(image)
                .error(R.drawable.ic_delete) // Error placeholder
                .into(productImageView);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = etProductName.getText().toString();
                int updatedPrice = Integer.parseInt(etProductPrice.getText().toString());
                String updatedCategory = etProductCategory.getText().toString();
                String updatedDescription = etProductDescription.getText().toString();
                String updatedImage = selectedImageUri.toString();


                ProductEntity updatedProduct = new ProductEntity();
                updatedProduct.setId(productId);
                updatedProduct.setUserId(userId);// Set the ID of the original product
                updatedProduct.setProductName(updatedName);
                updatedProduct.setProductPrice(updatedPrice);
                updatedProduct.setProductCategory(updatedCategory);
                updatedProduct.setProductDescription(updatedDescription);
                updatedProduct.setProductImage(updatedImage);

                productViewModel.updateProduct(updatedProduct);

                Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQ_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            Picasso.get()
                    .load(selectedImageUri)
                    .error(R.drawable.ic_delete) // Error placeholder
                    .into(productImageView);
        }
    }
}
