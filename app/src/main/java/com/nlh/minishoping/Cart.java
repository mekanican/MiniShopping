package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


// Dummy class
class Product {
    public int ID;
    public int price;
    public String name;
    public Drawable image;
    public int numberOfItem;

    public Product(Context context, int ID, int price, String name) {
        this.ID = ID;
        this.price = price;
        this.name = name;
        this.image = context.getDrawable(R.drawable.icon);
        this.numberOfItem = 0;
    }

    public void addItem() {
        numberOfItem++;
    }

    public void removeItem() {
        if (numberOfItem > 0) {
            numberOfItem--;
        }
    }

    public int total() {
        return numberOfItem * price;
    }

}

public class Cart extends AppCompatActivity {

    ArrayList<Product> productList;
    ProductCartViewAdapter pcvAdapter;
    ListView productListView;

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


        // Setup list view
        pcvAdapter = new ProductCartViewAdapter(productList, () -> update());
        productListView = findViewById(R.id.cart_lv);
        productListView.setAdapter(pcvAdapter);

        setPriceByID(R.id.product_price, 0);
        setPriceByID(R.id.shipping_price, 30000);
        setPriceByID(R.id.total_price, 0);

    }

    private void update() {
        int total = totalProductsPrice(productList);
        setPriceByID(R.id.product_price, total);
        setPriceByID(R.id.total_price, (int) Math.round((total + 30000) * (1 + 0.15)));
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

    private void setPriceByID(int ID, int price) {
        ((TextView) findViewById(ID)).setText(String.format("%d VND", price));
    }

    // Hacky fp btw <(")
    private int totalProductsPrice(ArrayList<Product> products) {
        return products.stream()
                .mapToInt(a -> a.total())
                .sum();
    }
}