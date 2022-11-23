package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;


// Dummy class
class Product {
    public int ID;
    public int price;
    public String name;
    public Drawable image;

    public Product(Context context, int ID, int price, String name) {
        this.ID = ID;
        this.price = price;
        this.name = name;
        this.image = context.getDrawable(R.drawable.icon);
    }

}

public class Cart extends AppCompatActivity {

    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        // Modify action bar for back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Your cart");

        // Setup productList
        productList = new ArrayList<>();
        productList.add(new Product(this, 0, 100, "Iphone"));
        productList.add(new Product(this, 1, 300, "Táo"));
        productList.add(new Product(this, 2, 200, "Gà"));
        productList.add(new Product(this, 3, 500, "Gạo"));
    }

    // Back button -> Previous page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}