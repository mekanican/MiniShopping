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

        List<Product> raw = DataHandler.GetProducts().subList(0, 20); // 20 first item
        ArrayList<HomeProduct> homeProductArrayList = raw.stream()
                .map(HomeProduct::new)
                .collect(Collectors.toCollection(ArrayList::new));

//        homeProductArrayList.add(new HomeProduct("NMLT", "Nhap mon lap trinh", R.drawable.nmlt, 100));
//        homeProductArrayList.add(new HomeProduct("KTLT", "Ky thuat lap trinh", R.drawable.ktlt, 100));
//        homeProductArrayList.add(new HomeProduct("OOP", "Phuong phap lap trinh huong doi tuong", R.drawable.oop, 100));
//        homeProductArrayList.add(new HomeProduct("MMT", "Mang may tinh", R.drawable.mmt, 100));
//        homeProductArrayList.add(new HomeProduct("NVH", "Nguyen Van Hung", R.drawable.hung, 0));

        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(this, homeProductArrayList);
        gvProductList.setAdapter(productGridViewAdapter);
        gvProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeProduct product = (HomeProduct) gvProductList.getItemAtPosition(i);
                String name = product.getName();

                Intent intent = new Intent(Home.this, ProductDetails.class);
                startActivity(intent);
            }
        });
    }

    public void productFindClicked(View view) {

    }

    public void shoppingCartClicked(View view) {

    }

}

