package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    GridView gvProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gvProductList = findViewById(R.id.grid_view_product_list);
        ArrayList<HomeProduct> homeProductArrayList = new ArrayList<HomeProduct>();

        homeProductArrayList.add(new HomeProduct("NMLT", "Nhap mon lap trinh", R.drawable.nmlt, 100));
        homeProductArrayList.add(new HomeProduct("KTLT", "Ky thuat lap trinh", R.drawable.ktlt, 100));
        homeProductArrayList.add(new HomeProduct("OOP", "Phuong phap lap trinh huong doi tuong", R.drawable.oop, 100));
        homeProductArrayList.add(new HomeProduct("MMT", "Mang may tinh", R.drawable.mmt, 100));
        homeProductArrayList.add(new HomeProduct("NVH", "Nguyen Van Hung", R.drawable.hung, 0));

        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(this, homeProductArrayList);
        gvProductList.setAdapter(productGridViewAdapter);
    }

    public void productFindClicked(View view) {

    }

    public void shoppingCartClicked(View view) {

    }

}

class HomeProduct {
    public HomeProduct(String id, String name, int image, int price) {
        mId = id;
        mName = name;
        mImageId = image;
        mPrice = Integer.max(price, 0);
    }

    public HomeProduct(String name, int image, int price) {
        mName = name;
        mImageId = image;
        mPrice = price;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getImageId() {
        return mImageId;
    }

    public int getmPrice() {
        return mPrice;
    }

    private String mId;
    private String mName;
    private int mImageId;
    private int mPrice;
}