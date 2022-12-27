package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlh.minishoping.Cart.CartMap;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment;
    CartMap cartMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        cartMap = CartMap.getInstance();

        // Debug only
        Log.d("Email", getIntent().getExtras().getString("Email"));
        Toast.makeText(this, "Chao mung " + getIntent().getExtras().getString("Email"), Toast.LENGTH_SHORT).show();

        StoreFragment storeFragment = new StoreFragment();
        CartFragment cartFragment = new CartFragment();
        FavoriteListFragment favoriteListFragment = new FavoriteListFragment();
        MapsFragment mapsFragment = new MapsFragment();
        AboutUsFragment aboutUsFragment = new AboutUsFragment();

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
                case R.id.favorite:
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment)
                            .show(favoriteListFragment)
                            .commit();
                    currentFragment = favoriteListFragment;
                    break;
                case R.id.map:
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment)
                            .show(mapsFragment)
                            .commit();
                    currentFragment = mapsFragment;
                    break;
                case R.id.about:
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment)
                            .show(aboutUsFragment)
                            .commit();
                    currentFragment = aboutUsFragment;
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
                .add(R.id.fragment_container, favoriteListFragment)
                .add(R.id.fragment_container, mapsFragment)
                .add(R.id.fragment_container, aboutUsFragment)
                .hide(cartFragment)
                .hide(favoriteListFragment)
                .hide(mapsFragment)
                .hide(aboutUsFragment)
                .commit();
        currentFragment = storeFragment;
    }
}