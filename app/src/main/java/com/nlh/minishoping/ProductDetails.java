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
import android.widget.Button;
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

    boolean isFavorite;

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

        isFavorite = false;
        int[] favoriteArray = ServerConnector.GetFavoriteList(hashValue);
        for (int i = 0; i < favoriteArray.length; i++) {
            if (favoriteArray[i] == ID) {
                isFavorite = true;
                break;
            }
        }
        Log.i("FAVORITE CHECK RESULT", String.valueOf(isFavorite));


        adjustFavoriteButtonText(isFavorite);
    }

    private void adjustFavoriteButtonText(boolean isFavorite) {
        if (!isFavorite) {
            return;
        }

        Button btn = findViewById(R.id.favorite_button);
        btn.setText("Bỏ thích");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFavoriteClicked(View view) {
        Log.i("PRODUCT DETAILS HASH VALUE", hashValue);
        Log.i("PRODUCT DETAIL ID", String.valueOf(ID));
        int ans = ServerConnector.AddProductToFavorite(hashValue, ID, isFavorite);
        Log.i("PRODUCT DETAILS RETURN FROM CONNECTOR", String.valueOf(ans));

        if (!isFavorite) {
            if (ans == 0) {
                Toast.makeText(this, "Sản phẩm đã được thêm vào danh sách yêu thích thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi thêm sản phẩm vào danh sách yêu thích", Toast.LENGTH_LONG).show();
            }
        } else {
            if (ans == 0) {
                Toast.makeText(this, "Sản phẩm đã được xóa khỏi danh sách yêu thích thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi xóa sản phẩm khỏi danh sách yêu thích", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onAddToCartClicked(View view) {
        boolean success = CartMap.getInstance().addItem(ID);

        if (success) {
            Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sản phẩm có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
        }
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
                    .putExtra("ID", gi.id)
                    .putExtra("HASH", hashValue);
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