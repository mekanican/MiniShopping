package com.nlh.minishoping.DAO;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;

@Dao
public interface CartProductDao {
    @Transaction
    @Query("select * from cartproduct")
    ArrayList<CartProduct> getAllProducts();

    @Transaction
    @Query("select product.id as id, product.name as name, product.price as price, product.image as imageLink, cartproduct.count as count "
            + "from cartproduct, api_product as product "
            + "where cartproduct.id = product.id")
    ArrayList<ProductCount> getAllWithInfoProducts();

    @Insert(onConflict = REPLACE)
    public void insertOneProduct(CartProduct cp);

    @Query("DELETE FROM cartproduct")
    public void deleteAll();

    @Query("select sum(cartproduct.count * product.price) "
            + "from cartproduct, api_product as product "
            + "where cartproduct.id = product.id")
    public int getTotalPrice();

    static class ProductCount {
        @Embedded
        public GeneralInfo generalInfo;
        // From cart product
        public int count;
    }
}
