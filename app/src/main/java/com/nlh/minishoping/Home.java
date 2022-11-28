package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Home extends AppCompatActivity {

    GridView gvProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gvProductList = findViewById(R.id.grid_view_product_list);

        ArrayList<HomeProduct> homeProductArrayList = SharedInfo.getInstance().getProductHome();
        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(this, homeProductArrayList);
        gvProductList.setAdapter(productGridViewAdapter);
        gvProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeProduct product = (HomeProduct) gvProductList.getItemAtPosition(i);
                String name = product.getName();
                String price = Integer.toString(product.getPrice()) + " VND";
                String imageLink = product.getImageLink();
                String category = product.getCategory();
                String description = product.getDescription();

                Intent intent = new Intent(Home.this, ProductDetails.class);

                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("link", imageLink);
                intent.putExtra("category", category);
                intent.putExtra("description", description);

                startActivity(intent);
            }
        });
    }

    public void productFindClicked(View view) {

    }

    public void shoppingCartClicked(View view) {

    }

    public void favoriteListClicked(View view) {
        Intent intent = new Intent(this, FavoriteList.class);
        startActivity(intent);
    }
}

