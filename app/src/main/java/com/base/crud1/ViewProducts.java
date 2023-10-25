package com.base.crud1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

public class ViewProducts extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;
    private ProductEntity productEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this);

        // Set the click listener
        productAdapter.setOnItemClickListener(this);

        // Initialize the ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Observe the list of products and update the RecyclerView when the data changes
        productViewModel.getAllProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> products) {
                productAdapter.setProducts(products);
            }
        });

        recyclerView.setAdapter(productAdapter);
    }

    public void onEditClick(int position) {
        // Handle edit action for the item at the specified position
        ProductEntity product = productAdapter.getProductAt(position);

        if (product != null) {
            String name = product.getProductName();
            Integer price = product.getProductPrice();
            String category = product.getProductCategory();
            String description = product.getProductDescription();
            String image = product.getProductImage();

            // You can start an EditProductActivity and pass the product details for editing.
            startActivity(new Intent(ViewProducts.this, EditProductActivity.class)
                    .putExtra("productId", product.getId()) // Pass the product ID
                    .putExtra("name", name)
                    .putExtra("price", price)
                    .putExtra("category", category)
                    .putExtra("description", description)
                    .putExtra("image", image));
        } else {
            // Handle the case where the product is null (not found)
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDeleteClick(int position) {
        // Handle delete action for the item at the specified position
        ProductEntity product = productAdapter.getProductAt(position);

        // Create a confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call the ViewModel to delete the product
                productViewModel.deleteProduct(product);

                // Notify the user that the product has been deleted
                Toast.makeText(ViewProducts.this, "Product deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null); // Do nothing on cancel

        // Show the dialog
        builder.create().show();
    }
}