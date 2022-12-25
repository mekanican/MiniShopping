package com.nlh.minishoping.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 3, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    private static volatile ProductDatabase INSTANCE;

    public static ProductDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ProductDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ProductDatabase.class, "Product.db")
                            .createFromAsset("db.sqlite3")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
