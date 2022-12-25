package com.nlh.minishoping.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface ProductDao {
    @Query("select * from api_product")
    ArrayList<Product> getAllProducts();

    @Query("select * from api_product where :from <= id and id <= :to ")
    ArrayList<Product> getFromToProducts(int from, int to);

    @Query("select * from api_product where category = :category")
    ArrayList<Product> getCategoryProducts(String category);

    @Query("select * from api_product where price < :price")
    ArrayList<Product> getPriceLowerProducts(int price);

    @Query("select id, name, price, image from api_product")
    ArrayList<GeneralInfo> getGeneralInfoProducts();
}
