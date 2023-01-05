package com.nlh.minishoping;

import static com.nlh.minishoping.Connector.ServerConnector.HOST_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ExpandableHeightGridView;
import com.koushikdutta.ion.Ion;
import com.nlh.minishoping.Cart.CartMap;
import com.nlh.minishoping.Connector.ServerConnector;
import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.DAO.ProductDatabase;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetails extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    ImageView iv_product_image;
    TextView tv_product_name;
    TextView tv_product_price;
    TextView tv_product_category;
    TextView tv_product_description;

    int ID;
    String name;
    String price;
    String imageLink;
    String category;
    String description;

    String hashValue;

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
        int ans = ServerConnector.AddProductToFavorite(hashValue, ID);
        if (ans == 0) {
            Toast.makeText(this, "Sản phẩm đã được thêm vào danh sách yêu thích thành công", Toast.LENGTH_LONG).show();
        } else if (ans == 1) {
            Toast.makeText(this, "Sản phẩm đã có trong danh sách yêu thích", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra khi thêm sản phẩm vào danh sách yêu thích", Toast.LENGTH_LONG).show();
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
        boolean success = CartMap.getInstance().addItem(ID);

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
    }

    private void getDataFromPreviousActivity() {
        ID = bundle.getInt("ID");

        hashValue = bundle.getString("HASH");
        Log.i("HASH VALUE GOTTEN IN PRODUCT DETAILS", hashValue);

        // Warning: may slow on UI thread
        com.nlh.minishoping.DAO.Product product =
                ProductDatabase.getInstance().productDao().getByIDProduct(ID);

        name = product.name;
        price = Integer.toString((int) product.price);
        imageLink = HOST_NAME + product.imageLink;
        category = product.category;
        description = product.description;
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
        // List<GeneralInfo> categoryProducts = ProductDatabase.getInstance().productDao().getCategoryProducts(category);

        RecyclerView recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.initCategory(category);

        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view1);
            GeneralInfo gi = productViewModel.productList.getValue().get(itemPosition);
            Intent intent = new Intent(this, ProductDetails.class)
                    .putExtra("ID", gi.id);
            this.startActivity(intent);
        });
        productViewModel.productList.observe(this, productAdapter::submitList);
        recyclerView.setAdapter(productAdapter);
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