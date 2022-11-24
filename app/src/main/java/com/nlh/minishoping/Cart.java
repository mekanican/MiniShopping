package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    public static double VAT = 1.15; // 15%
    public static int DEFAULT_SHIPPING_PRICE = 20000;

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
        productList.add(new Product(this, 0, 1000, "Iphone"));
        productList.add(new Product(this, 1, 3000, "Táo"));
        productList.add(new Product(this, 2, 2000, "Gà"));
        productList.add(new Product(this, 3, 5000, "Gạo"));
        productList.add(new Product(this, 4, 5000, "a"));
        productList.add(new Product(this, 5, 5000, "b"));
        productList.add(new Product(this, 6, 5000, "c"));
        productList.add(new Product(this, 7, 5000, "d"));
        productList.add(new Product(this, 8, 5000, "e"));
        productList.add(new Product(this, 9, 5000, "f"));
        productList.add(new Product(this, 10, 5000, "g"));
        productList.add(new Product(this, 11, 5000, "h"));
        productList.add(new Product(this, 12, 5000, "i"));


        // Setup list view
        pcvAdapter = new ProductCartViewAdapter(productList, () -> update());
        productListView = findViewById(R.id.cart_lv);
        productListView.setAdapter(pcvAdapter);

        resetValue();

        // Handle deleteAll
        ((Button) findViewById(R.id.delete_)).setOnClickListener(view -> {
            productList.clear();
            pcvAdapter.notifyDataSetChanged();
            resetValue();
        });


        // Handle proceed
        ((Button) findViewById(R.id.proceed_)).setOnClickListener(view -> {
            Toast.makeText(this,
                    String.format("Chúc mừng bạn đã tốn %d VND tiền ngu <(\")",
                            (int) Math.round((totalProductsPrice(productList) + DEFAULT_SHIPPING_PRICE) * VAT)),
                    Toast.LENGTH_LONG).show();
        });

    }

    private void resetValue() {
        setPriceByID(R.id.product_price, 0);
        setPriceByID(R.id.shipping_price, DEFAULT_SHIPPING_PRICE);
        setPriceByID(R.id.total_price, 0);
    }

    private void update() {
        int total = totalProductsPrice(productList);
        setPriceByID(R.id.product_price, total);
        setPriceByID(R.id.total_price, (int) Math.round((total + DEFAULT_SHIPPING_PRICE) * VAT));
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