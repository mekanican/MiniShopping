package com.nlh.minishoping;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the "ActionBar" in this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Manually Blurred background
        ConstraintLayout constraintLayout = findViewById(R.id.init_page);
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.background_init);
        Bitmap background_blur = BlurBuilder.blur(this, image);
        constraintLayout.setBackground(
                new BitmapDrawable(getResources(), background_blur)
        );


    }
}