package com.base.crud1;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductsRepository repository;
    private LiveData<List<ProductEntity>> allProducts;

    public ProductViewModel(Application application) {
        super(application);
        repository = new ProductsRepository(application);
        allProducts = repository.getAllProducts();
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }
    public void deleteProduct(ProductEntity product) {
        repository.deleteProduct(product);
    }
    public void updateProduct(ProductEntity product) {
        repository.updateProduct(product);
    }
}

