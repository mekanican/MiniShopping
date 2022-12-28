package com.nlh.minishoping;

import static com.nlh.minishoping.NotificationClass.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlh.minishoping.Cart.CartMap;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
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
                .hide(cartFragment)
                .hide(favoriteListFragment)
                .hide(mapsFragment)
                .commit();
        currentFragment = storeFragment;

        sendWelcomeNotification();
    }

    private void sendWelcomeNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_24);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Welcome to MiniShopping")
                .setContentText("Hope you will enjoy this app!! Sure")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setLargeIcon(bitmap)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }


}