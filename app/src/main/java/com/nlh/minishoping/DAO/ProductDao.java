package com.nlh.minishoping.DAO;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
//    @Query("select * from api_product")
//    List<Product> getAllProducts();

    @Query("select id, name, price, image as imageLink from api_product")
    public abstract DataSource.Factory<Integer, GeneralInfo> getPageProducts();

    @Query("select id, name, price, image as imageLink from api_product where category = :category limit 10")
        // At most 10 item
    List<GeneralInfo> getCategoryProducts(String category);

    @Query("select id, name, price, image as imageLink from api_product where price < :price")
    public abstract DataSource.Factory<Integer, GeneralInfo> getPriceLowerProducts(float price);

    @Query("select * from api_product where id = :id limit 1")
    Product getByIDProduct(int id);

    @Query("select id, name, price, image as imageLink from api_product where id = :id limit 1")
    GeneralInfo getInfoByIDProduct(int id);
}
