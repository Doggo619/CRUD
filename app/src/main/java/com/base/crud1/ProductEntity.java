package com.base.crud1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity {

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "productName")
    String productName;
    @ColumnInfo(name = "productPrice")
    Integer productPrice;
    @ColumnInfo(name = "productCategory")
    String productCategory;
    @ColumnInfo(name = "productDescription")
    String productDescription;

    @ColumnInfo(name = "productImage")
    String productImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
