package com.nlh.minishoping;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.internal.ContextUtils;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

public class ProductDetails extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    ImageView iv_product_image;
    TextView tv_product_name;
    TextView tv_product_price;
    String name;
    String price;
    String imageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        intent = getIntent();
        bundle = intent.getExtras();
        iv_product_image = findViewById(R.id.iv_product_image_details);
        tv_product_name = findViewById(R.id.tv_product_name_details);
        tv_product_price = findViewById(R.id.tv_product_price_details);

        name = bundle.getString("name");
        price = bundle.getString("price");
        imageLink = bundle.getString("link");

        setupImageView(this, iv_product_image);
        tv_product_name.setText(name);
        tv_product_price.setText(price);

        //Log.i("name", name);

        // 
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
               "FAVORITE" , Context.MODE_PRIVATE);

        Integer number;
        if (!spFavorite.contains("Number")) {
            boolean res = spFavorite.edit().putString("Number", "0").commit();
            number = 0;
        } else {
            number = Integer.parseInt(spFavorite.getString("Number", null).toString());
        }

        boolean isDuplicated = false;

        // check if exists duplication
        if (number > 0) {
            for (int i = 1; i <= number; i++) {
                String tempName = spFavorite.getString("Name " + Integer.toString(i), null).toString();
                if (tempName != name) {
                    continue;
                }

                String tempPrice = spFavorite.getString("Price " + Integer.toString(i), null).toString();
                if (tempPrice != price) {
                    continue;
                }

                String tempImageLink = spFavorite.getString("Image " + Integer.toString(i), null).toString();
                if (tempImageLink != imageLink) {
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

        String nameKey = "Name " + Integer.toString(number + 1);
        String priceKey = "Price " + Integer.toString(number + 1);
        String imageKey = "Image " + Integer.toString(number + 1);

        boolean res;
        res = spFavorite.edit().putString(nameKey, name).commit();
        res = spFavorite.edit().putString(priceKey, price).commit();
        res = spFavorite.edit().putString(imageKey, imageLink).commit();

        number += 1;
        res = spFavorite.edit().putString("Number", Integer.toString(number)).commit();
        Log.i("Result", Boolean.toString(res));
        number = Integer.parseInt(spFavorite.getString("Number", null).toString());
        Log.i("Number", Integer.toString(number));
    }

    public void onAddToCartClicked(View view) {

    }

    public void onInstantBuyClicked(View view) {

    }

    public void onHotlineClicked(View view) {

    }
}