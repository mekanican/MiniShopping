package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ExpandableHeightGridView;
import com.koushikdutta.ion.Ion;

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
    ExpandableHeightGridView gv_recommendation_list;

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

        setupViewsOnCreate();

        getDataFromPreviousActivity();

        setupViewsToDisplay();

        // https://www.geeksforgeeks.org/how-to-add-and-customize-back-button-of-action-bar-in-android/
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupGridViewRecommendationsList();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // https://developer.android.com/training/data-storage/shared-preferences?hl=en
    // https://viblo.asia/p/shared-preferences-trong-android-1Je5EEvY5nL
    public void onFavoriteClicked(View view) {
        // get the data store in SharedPreferences
        SharedPreferences spFavorite = this.getSharedPreferences(
                "FAVORITE", Context.MODE_PRIVATE);

        Integer number = getFavoriteNumber(spFavorite);

        boolean isDuplicated = false;

        // check if exists duplication
        if (number > 0) {
            for (int i = 1; i <= number; i++) {
                String tempID = spFavorite.getString("ID " + i, null);
                String stringID = Integer.toString(ID);
                if (tempID.equals(stringID)) {
                    isDuplicated = true;
                    break;
                }

            }
        }

        // if duplicated, do nothing
        if (isDuplicated) {
            Toast.makeText(this, "Sản phẩm đã có trong danh sách yêu thích", Toast.LENGTH_LONG).show();
            return;
        }

        String idKey = "ID " + (number + 1);
        String nameKey = "Name " + (number + 1);
        String priceKey = "Price " + (number + 1);
        String imageKey = "Image " + (number + 1);
        String categoryKey = "Category " + (number + 1);
        String descriptionKey = "Description " + (number + 1);

        // add key - value pairs to data
        boolean res;
        res = addToFavoriteData(spFavorite, idKey, nameKey, priceKey, imageKey, categoryKey, descriptionKey);
        if (!res) {
            showFailNotification();
            return;
        }

        number += 1;
        res = spFavorite.edit().putString("Number", Integer.toString(number)).commit();
        if (res) {
            Toast.makeText(this, "Sản phẩm đã được thêm vào danh sách yêu thích", Toast.LENGTH_LONG).show();
            SharedInfo.getInstance().addFavoriteTrigger();
        } else {
            showFailNotification();
        }
    }

    private boolean addToFavoriteData(SharedPreferences spFavorite, String idKey, String nameKey, String priceKey, String imageKey, String categoryKey, String descriptionKey) {
        if (!spFavorite.edit().putString(idKey, Integer.toString(ID)).commit() ||
                !spFavorite.edit().putString(nameKey, name).commit() ||
                !spFavorite.edit().putString(priceKey, price).commit() ||
                !spFavorite.edit().putString(imageKey, imageLink).commit() ||
                !spFavorite.edit().putString(categoryKey, category).commit() ||
                !spFavorite.edit().putString(descriptionKey, description).commit()) {
            return false;
        }
        return true;
    }

    private Integer getFavoriteNumber(SharedPreferences sp) {
        if (!sp.contains("Number")) {
            sp.edit().putString("Number", "0").commit();
        } else {
            return Integer.parseInt(sp.getString("Number", null));
        }
        return 0;
    }

    public void onAddToCartClicked(View view) {
        boolean success = SharedInfo.getInstance().addProductToCart(ID);

        if (success) {
            Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sản phẩm có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showFailNotification() {
        Toast.makeText(this, "Lỗi! Thêm sản phẩm vào danh sách yêu thích không thành công", Toast.LENGTH_LONG).show();
    }

    private void setupViewsOnCreate() {
        iv_product_image = findViewById(R.id.iv_product_image_details);
        tv_product_name = findViewById(R.id.tv_product_name_details);
        tv_product_price = findViewById(R.id.tv_product_price_details);
        tv_product_category = findViewById(R.id.tv_product_category_details);
        tv_product_description = findViewById(R.id.tv_product_description_details);
        gv_recommendation_list = findViewById(R.id.grid_view_recommendation_list);
        gv_recommendation_list.setExpanded(true);
    }

    private void getDataFromPreviousActivity() {
        ID = bundle.getInt("ID");
        name = bundle.getString("name");
        price = bundle.getString("price");
        imageLink = bundle.getString("link");
        category = bundle.getString("category");
        description = bundle.getString("description");
    }

    private void setupViewsToDisplay() {
        setupImageView(iv_product_image);
        tv_product_name.setText(name);
        tv_product_price.setText(price);
        tv_product_category.setText(category);
        tv_product_description.setText(description);
    }

    private void setupImageView(ImageView iv) {
        Ion.with(iv)
                .placeholder(R.drawable.loading)
                .error(R.drawable.icon)
                .animateLoad(R.anim.loading)
                .load(imageLink);
    }

    private void setupGridViewRecommendationsList() {
        ArrayList<HomeProduct> recommendationList = getRecommendations();

        // setup adapter for recommendation list
        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(this, recommendationList);
        gv_recommendation_list.setAdapter(productGridViewAdapter);

        // set on click listener for each item in recommendation list
        gv_recommendation_list.setOnItemClickListener((adapterView, view, i, l) -> {
            HomeProduct product = (HomeProduct) gv_recommendation_list.getItemAtPosition(i);
            int id = product.getId();
            String name = product.getName();
            String price = product.getPrice() + " VND";
            String imageLink = product.getImageLink();
            String category = product.getCategory();
            String description = product.getDescription();

            Intent intent = new Intent(ProductDetails.this, ProductDetails.class);
            intent.putExtra("ID", id);
            intent.putExtra("name", name);
            intent.putExtra("price", price);
            intent.putExtra("link", imageLink);
            intent.putExtra("category", category);
            intent.putExtra("description", description);

            startActivity(intent);
        });
    }

    private String parsePrice(String originalPrice) {
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < originalPrice.length() - 4; j++) { // " VND".length() = 4
            res.append(originalPrice.charAt(j));
        }
        return res.toString();
    }

    private ArrayList<HomeProduct> getRecommendations() {
        String priceWithoutSuffix = parsePrice(price);
        // temp product, created for get recommendations
        Product p = new Product(1, imageLink, name, category, description, Integer.parseInt(priceWithoutSuffix));

        return DataHandler.GetRecommendProducts(p, NUMBER_OF_RECOMMENDATIONS)
                .stream()
                .map(HomeProduct::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // https://www.stechies.com/add-share-button-android-app/
    public void onShareClicked(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String shareBody = "";
        shareBody = shareBody + "Tên sản phẩm: " + name + "\n";
        shareBody = shareBody + "Giá sản phẩm: " + price + "\n";
        shareBody = shareBody + "Hình ảnh sản phẩm: " + imageLink + "\n";
        shareBody = shareBody + "Thể loại: " + category + "\n";
        shareBody = shareBody + "Mô tả sản phẩm: " + description + "\n";

        String shareTitle = "Chia sẻ sản phẩm trên MiniShopping ";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(shareIntent, "Chia sẻ bằng"));
    }
}