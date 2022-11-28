package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteList extends AppCompatActivity {
    TextView tvNoFavoriteItem;
    GridView gvFavoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        tvNoFavoriteItem = findViewById(R.id.tv_no_favorite_item);
        gvFavoriteList = findViewById(R.id.grid_view_favorite_list);

        SharedPreferences spFavorite = this.getSharedPreferences("FAVORITE" , Context.MODE_PRIVATE);

        Integer number = 0;
        if (!spFavorite.contains("Number")) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
            return;
        } else {
            number = Integer.parseInt(spFavorite.getString("Number", null).toString());
        }

        // this case does not happen =))
        if (number == 0) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
            return;
        }

        Log.i("Number", Integer.toString(number));

        ArrayList<HomeProduct> favoriteList = new ArrayList<HomeProduct>();

        String name;
        String price;
        String imageLink;

        for (int i = 1; i <= number; i++) {
            name = spFavorite.getString("Name " + Integer.toString(i), null).toString();
            price = spFavorite.getString("Price " + Integer.toString(i), null).toString();
            imageLink = spFavorite.getString("Image " + Integer.toString(i), null).toString();

            String priceWithoutSuffix = "";
            for (int j = 0; j < price.length() - 4; j++) {
                priceWithoutSuffix += price.charAt(j);
            }

            Product p = new Product(i, name, imageLink, Integer.parseInt(priceWithoutSuffix));
            HomeProduct hp = new HomeProduct(p);

            favoriteList.add(hp);
        }

        ProductGridViewAdapter favoriteProductsGridViewAdapter = new ProductGridViewAdapter(this, favoriteList);
        gvFavoriteList.setAdapter(favoriteProductsGridViewAdapter);
        gvFavoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeProduct product = (HomeProduct) gvFavoriteList.getItemAtPosition(i);
                String productName = product.getName();
                String productPrice = Integer.toString(product.getPrice()) + " VND";
                String productImageLink = product.getImageLink();

                Intent intent = new Intent(FavoriteList.this, ProductDetails.class);

                intent.putExtra("name", productName);
                intent.putExtra("price", productPrice);
                intent.putExtra("link", productImageLink);

                startActivity(intent);
            }
        });
    }
}