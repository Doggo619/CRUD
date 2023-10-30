package com.base.crud1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProductEntity.class, UserProductMapping.class}, version = 4)
public abstract class ProductDatabase extends RoomDatabase {

    private static final  String dbName = "products";
    private static ProductDatabase productDatabase;

    public static synchronized ProductDatabase getProductDatabase(Context context) {
        if(productDatabase == null) {
            productDatabase = Room.databaseBuilder(context, ProductDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return productDatabase;
    }

    public abstract ProductsDao productsDao();
}
