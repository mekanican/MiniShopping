package com.nlh.minishoping;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    public static AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        assetManager = getAssets();

        // Init data before starting!
        SharedInfo.getInstance().initData();
        ArrayList<HomeProduct> t = SharedInfo.getInstance().getProductHome(); // For testing only!
        for (int i = 0; i < 20; i += 2) {
            SharedInfo.getInstance().addProductToCart(t.get(i));
        }

        // Hide the "ActionBar" in this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Manually Blurred background
        ConstraintLayout constraintLayout = findViewById(R.id.init_page);
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        Bitmap background_blur = BlurBuilder.blur(this, image);
        constraintLayout.setBackground(
                new BitmapDrawable(getResources(), background_blur)
        );
        // Button to switch activity (e.g. Cart activity)
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }
}