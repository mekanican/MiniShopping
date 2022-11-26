package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        StoreFragment storeFragment = new StoreFragment();
        CartFragment cartFragment = new CartFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            switch (item.getItemId()) {
                case R.id.home:
                    // selected = new StoreFragment();
                    getSupportFragmentManager().beginTransaction()
                            .hide(cartFragment)
                            .show(storeFragment)
                            .commit();
                    break;
                case R.id.cart:
                    getSupportFragmentManager().beginTransaction()
                            .hide(storeFragment)
                            .show(cartFragment)
                            .commit();
                    break;
                default:
                    return false;
            }
            return true;
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {
        }); // disable reselect functional

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, storeFragment)
                .add(R.id.fragment_container, cartFragment)
                .hide(cartFragment)
                .commit();
    }
}