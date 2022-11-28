package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.ContextUtils;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductDetails extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    ImageView iv_product_image;
    TextView tv_product_name;
    TextView tv_product_price;
    TextView tv_product_category;
    TextView tv_product_description;
    GridView gv_recommendation_list;
    
    int ID;
    String name;
    String price;
    String imageLink;
    String category;
    String description;

    private final int NUMBER_OF_RECOMMENDATIONS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        intent = getIntent();
        bundle = intent.getExtras();
        iv_product_image = findViewById(R.id.iv_product_image_details);
        tv_product_name = findViewById(R.id.tv_product_name_details);
        tv_product_price = findViewById(R.id.tv_product_price_details);
        tv_product_category = findViewById(R.id.tv_product_category_details);
        tv_product_description = findViewById(R.id.tv_product_description_details);
        gv_recommendation_list = findViewById(R.id.grid_view_recommendation_list);

        ID = bundle.getInt("ID");
        name = bundle.getString("name");
        price = bundle.getString("price");
        imageLink = bundle.getString("link");
        category = bundle.getString("category");
        description = bundle.getString("description");


        setupImageView(this, iv_product_image);
        tv_product_name.setText(name);
        tv_product_price.setText(price);
        tv_product_category.setText(category);
        tv_product_description.setText(description);

        //Log.i("name", name);

        // https://www.geeksforgeeks.org/how-to-add-and-customize-back-button-of-action-bar-in-android/
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupGridViewRecommendationsList();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupImageView(Context c, ImageView iv) {
        //Log.d("meow", "GetProducts: " + imageLink);
        Ion.getDefault(c).getConscryptMiddleware().enable(false);
        Ion.with(c)
                .load(imageLink)
                .withBitmap()
                .placeholder(R.drawable.hung)
                .error(R.drawable.icon)
                .animateLoad(R.anim.loading)
                .intoImageView(iv);
    }

    // https://developer.android.com/training/data-storage/shared-preferences?hl=en
    // https://viblo.asia/p/shared-preferences-trong-android-1Je5EEvY5nL
    public void onFavoriteClicked(View view) {
        //Context context = getActivity
        SharedPreferences spFavorite = this.getSharedPreferences(
                "FAVORITE", Context.MODE_PRIVATE);

        Integer number;
        if (!spFavorite.contains("Number")) {
            boolean res = spFavorite.edit().putString("Number", "0").commit();
            number = 0;
        } else {
            number = Integer.parseInt(spFavorite.getString("Number", null));
        }

        boolean isDuplicated = false;

        // check if exists duplication
        if (number > 0) {
            for (int i = 1; i <= number; i++) {
                String tempName = spFavorite.getString("Name " + i, null);
                if (!tempName.equals(name)) {
                    continue;
                }

                String tempPrice = spFavorite.getString("Price " + i, null);
                if (!tempPrice.equals(price)) {
                    continue;
                }

                String tempImageLink = spFavorite.getString("Image " + i, null);
                if (!tempImageLink.equals(imageLink)) {
                    continue;
                }

                // if come to this, then 3 things equal
                isDuplicated = true;
                break;
            }
        }

        // if duplicated, do nothing
        if (isDuplicated) {
            return;
        }

        String nameKey = "Name " + (number + 1);
        String priceKey = "Price " + (number + 1);
        String imageKey = "Image " + (number + 1);
        String categoryKey = "Category " + (number + 1);
        String descriptionKey = "Description " + (number + 1);

        boolean res;

        res = spFavorite.edit().putString(nameKey, name).commit();
        if (!res) {
            showFailNotification();
        }

        res = spFavorite.edit().putString(priceKey, price).commit();
        if (!res) {
            showFailNotification();
        }

        res = spFavorite.edit().putString(imageKey, imageLink).commit();
        if (!res) {
            showFailNotification();
        }

        res = spFavorite.edit().putString(categoryKey, category).commit();
        if (!res) {
            showFailNotification();
        }

        res =spFavorite.edit().putString(descriptionKey, description).commit();
        if (!res) {
            showFailNotification();
        }

        number += 1;
        res = spFavorite.edit().putString("Number", Integer.toString(number)).commit();
        if (!res) {
            showFailNotification();
        }
    }

    public void onAddToCartClicked(View view) {
        SharedInfo.getInstance().addProductToCart(ID);
    }

    public void onInstantBuyClicked(View view) {

    }

    public void onHotlineClicked(View view) {

    }

    public void showFailNotification() {
        Toast.makeText(this, "Fail to add this product to favorite list", Toast.LENGTH_LONG).show();
    }

    public void setupGridViewRecommendationsList() {
        String priceWithoutSuffix = "";
        for (int j = 0; j < price.length() - 4; j++) {
            priceWithoutSuffix += price.charAt(j);
        }

        Product p = new Product(1, imageLink, name, category, description, Integer.parseInt(priceWithoutSuffix));

        ArrayList<Product> recommendationListRaw = DataHandler.GetRecommendProducts(p, NUMBER_OF_RECOMMENDATIONS);
        ArrayList<HomeProduct> recommendationList = recommendationListRaw.stream()
                .map(HomeProduct::new)
                .collect(Collectors.toCollection(ArrayList::new));

        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(this, recommendationList);
        gv_recommendation_list.setAdapter(productGridViewAdapter);
        gv_recommendation_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeProduct product = (HomeProduct) gv_recommendation_list.getItemAtPosition(i);
                String name = product.getName();
                String price = Integer.toString(product.getPrice()) + " VND";
                String imageLink = product.getImageLink();
                String category = product.getCategory();
                String description = product.getDescription();

                Intent intent = new Intent(ProductDetails.this, ProductDetails.class);

                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("link", imageLink);
                intent.putExtra("category", category);
                intent.putExtra("description", description);

                startActivity(intent);
            }
        });
    }
}