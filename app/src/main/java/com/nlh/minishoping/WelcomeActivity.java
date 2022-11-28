package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    public static AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();

        // Init data before starting!
        SharedInfo.getInstance().initData();
        ArrayList<HomeProduct> t = SharedInfo.getInstance().getProductHome(); // For testing only!
        for (int i = 0; i < 20; i += 2) {
            SharedInfo.getInstance().addProductToCart(t.get(i));
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}