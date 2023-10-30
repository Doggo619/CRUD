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
    long addProducts(ProductEntity productEntity);

    @Update
    void updateProducts(ProductEntity productEntity);
    @Delete
    void deleteProducts(ProductEntity productEntity);
    @Insert
    void addUserProductMapping(UserProductMapping mapping);

    @Query("SELECT productId FROM user_product_mapping WHERE userId = :userId")
    List<Integer> getProductIdsForUser(String userId);
    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProducts();
    @Query("SELECT * FROM products WHERE userId = :userId")
    LiveData<List<ProductEntity>> getProductsForUser(String userId);
}
