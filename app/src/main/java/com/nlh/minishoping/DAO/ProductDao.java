package com.nlh.minishoping.DAO;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ProductDao {

    @Query("select id, name, price, image as imageLink from api_product")
    DataSource.Factory<Integer, GeneralInfo> getPageProducts();

    @Query("select id, name, price, image as imageLink from api_product where category = :category limit 10")
        // At most 10 item
    DataSource.Factory<Integer, GeneralInfo> getCategoryProducts(String category);

    @Query("select * from api_product where id = :id limit 1")
    Product getByIDProduct(int id);

    @Query("select id, name, price, image as imageLink from api_product where id = :id limit 1")
    GeneralInfo getInfoByIDProduct(int id);

    @Query("select id, name, price, image as imageLink from api_product where id in (:idList)")
    DataSource.Factory<Integer, GeneralInfo> getSearchProductFromIdList(int[] idList);

}
