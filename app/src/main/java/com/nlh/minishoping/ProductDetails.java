package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

public class ProductDetails extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    ImageView iv_product_image;
    TextView tv_product_name;
    TextView tv_product_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        intent = getIntent();
        bundle = intent.getExtras();
        iv_product_image = findViewById(R.id.iv_product_image_details);
        tv_product_name = findViewById(R.id.tv_product_name_details);
        tv_product_price = findViewById(R.id.tv_product_price_details);

        String name = bundle.getString("name");
        String price = bundle.getString("price");
        String imageLink = bundle.getString("link");

        setupImageView(this, iv_product_image, imageLink);
        tv_product_name.setText(name);
        tv_product_price.setText(price);

        //Log.i("name", name);

    }

    public void setupImageView(Context c, ImageView iv, String imageLink) {
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

    public void shoppingCartDetailsClicked(View view) {

    }

    public void onContactClicked(View view) {

    }

    public void onAddToCartClicked(View view) {

    }

    public void onInstantBuyClicked(View view) {

    }
}