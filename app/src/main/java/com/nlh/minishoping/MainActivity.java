package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        StoreFragment storeFragment = new StoreFragment();
        CartFragment cartFragment = new CartFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment)
                            .show(storeFragment)
                            .commit();
                    currentFragment = storeFragment;
                    break;
                case R.id.cart:
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment)
                            .show(cartFragment)
                            .commit();
                    currentFragment = cartFragment;
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
        currentFragment = storeFragment;
    }
}