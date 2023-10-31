package com.base.crud1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class AddProducts extends AppCompatActivity {

    private static final int GALLERY_REQ_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    EditText name, price, category, description;
    Button addProduct, image;
    ImageView selectedImage;
    Uri selectedImageUri;
    ProductEntity productEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);



        name = findViewById(R.id.et_productname);
        price = findViewById(R.id.et_price);
        category = findViewById(R.id.et_category);
        description = findViewById(R.id.et_description);
        image = findViewById(R.id.btn_image);
        selectedImage = findViewById(R.id.iv_selectedimage);

        addProduct = findViewById(R.id.btn_add);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AddProducts.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission already granted, open the image picker
                    openImagePicker();
                } else {
                    // Permission not granted, request it from the user
                    ActivityCompat.requestPermissions(AddProducts.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                }
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                String userId = preferences.getString("userId", "");


                productEntity = new ProductEntity();
                productEntity.setUserId(userId);
                productEntity.setProductName(name.getText().toString());
                productEntity.setProductPrice(Integer.parseInt(price.getText().toString()));
                productEntity.setProductCategory(category.getText().toString());
                productEntity.setProductDescription(description.getText().toString());
                productEntity.setProductImage(selectedImageUri.toString());

                if (validateProductDetails(productEntity)) {
                    ProductDatabase productDatabase = ProductDatabase.getProductDatabase(getApplicationContext());
                    ProductsDao productsDao = productDatabase.productsDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String productImage = productEntity.getProductImage();
                            Log.d("Image URI", productImage);
                            long productId = productsDao.addProducts(productEntity);
                            if (productId > 0) {
                                // Insert the user-product mapping into the new table
                                Integer productIntegerId = (int) productId;
                                UserProductMapping userProductMapping = new UserProductMapping();
                                userProductMapping.setUserId(userId);
                                userProductMapping.setProductId(productIntegerId);

                                // Insert the mapping into the new table
                                productsDao.addUserProductMapping(userProductMapping);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Product is Added!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Failed to add the product", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQ_CODE);
    }

    private void loadImageFromUri(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            Picasso.get()
                    .load(selectedImageUri)
                    .into(selectedImage); // Load the image into the ImageView

            // Optionally, you can resize the image and center crop it:
            // Picasso.with(this)
            //     .load(selectedImageUri)
            //     .resize(500, 500)
            //     .centerCrop()
            //     .into(selectedImage);

            // Make sure to remove the previous code where you used MediaStore to load the image.
        } else {
            // Handle the case when the selectedImageUri is null or invalid
            Toast.makeText(this, "Invalid image URI", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            Log.d("SelectedImageUri", selectedImageUri.toString());
            // Check if permission is granted before loading the image
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load the image
                if (productEntity == null) {
                    productEntity = new ProductEntity();
                }
                productEntity.setProductImage(selectedImageUri.toString());
                loadImageFromUri(selectedImageUri);
            } else {
                // Permission denied, handle this case
                Toast.makeText(this, "Permission denied to access images", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load the image
                loadImageFromUri(selectedImageUri);
            } else {
                // Permission denied, handle this case
                Toast.makeText(this, "Permission denied to access images", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
