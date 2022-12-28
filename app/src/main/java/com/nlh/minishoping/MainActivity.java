package com.nlh.minishoping;

import static com.nlh.minishoping.NotificationClass.CHANNEL_1;
import static com.nlh.minishoping.NotificationClass.CHANNEL_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlh.minishoping.Cart.CartMap;

public class MainActivity extends AppCompatActivity {

    private static final int WELCOME_NOTIFICATION_ID = 1;
    private static final int DISCOUNT_NOTIFICATION_ID = 2;

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

        sendDiscountNotification();
    }

    private void sendWelcomeNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_24);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setContentTitle(getString(R.string.welcome_noti_title))
                .setContentText(getString(R.string.welcome_noti_content))
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setLargeIcon(bitmap)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(WELCOME_NOTIFICATION_ID, notification);
        }
    }


    private void sendDiscountNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_24);

        // Create an Intent for the activity you want to start
        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("ID", 2);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2)
                .setContentTitle("Sản phẩm hot giảm sâu!")
                .setContentText("Bạn ơi, sản phẩm hot đang giảm sâu nè. Xem ngay thôi!")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setLargeIcon(bitmap)
                .setContentIntent(resultPendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(DISCOUNT_NOTIFICATION_ID, notification);
        }
    }
}