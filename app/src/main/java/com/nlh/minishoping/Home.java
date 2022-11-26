package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

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
    }

    public void productFindClicked(View view) {

    }

    public void shoppingCartClicked(View view) {

    }

}

