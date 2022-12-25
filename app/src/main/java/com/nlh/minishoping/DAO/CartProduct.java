package com.nlh.minishoping.DAO;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Product.class,
        parentColumns = "id",
        childColumns = "id",
        onDelete = CASCADE))

public class CartProduct {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "count")
    public int count;
}
