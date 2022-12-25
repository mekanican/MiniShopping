package com.nlh.minishoping.DAO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "api_product")
public class Product {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "image")
    public String imageLink;
}
