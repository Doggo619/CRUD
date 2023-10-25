package com.base.crud1;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductsRepository {
    private ProductsDao productsDao;
    private LiveData<List<ProductEntity>> allProducts;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProductsRepository(Application application) {
        ProductDatabase database = ProductDatabase.getProductDatabase(application);
        productsDao = database.productsDao();
        allProducts = productsDao.getAllProducts();
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }

    public void insertProduct(ProductEntity product) {
        executorService.execute(() -> {
            // Execute database insertion in the background thread
            productsDao.addProducts(product);
        });
    }

    public void updateProduct(ProductEntity product) {
        executorService.execute(() -> {
            // Execute database update in the background thread
            productsDao.updateProducts(product);
        });
    }

    public void deleteProduct(ProductEntity product) {
        executorService.execute(() -> {
            // Execute database deletion in the background thread
            productsDao.deleteProducts(product);
        });
    }
}

