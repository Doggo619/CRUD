package com.base.crud1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductsDao {

    @Insert
    void addProducts(ProductEntity productEntity);
    @Update
    void updateProducts(ProductEntity productEntity);
    @Delete
    void deleteProducts(ProductEntity productEntity);
    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProducts();
    @Query("SELECT * FROM products WHERE userId = :userId")
    LiveData<List<ProductEntity>> getProductsForUser(String userId);
}
