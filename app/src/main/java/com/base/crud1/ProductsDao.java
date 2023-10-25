package com.base.crud1;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ProductsDao {

    @Insert
    void addProducts(ProductEntity productEntity);
}
