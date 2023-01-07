package com.nlh.minishoping.DAO;

import static java.sql.Types.REAL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "api_product")
public class Product {
    @PrimaryKey
    public int id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "description")
    public String description;

    @NonNull
    @ColumnInfo(name = "price", typeAffinity = REAL)
    public float price;

    @NonNull
    @ColumnInfo(name = "category")
    public String category;

    @NonNull
    @ColumnInfo(name = "image")
    public String imageLink;
}
