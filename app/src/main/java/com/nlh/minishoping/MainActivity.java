package com.nlh.minishoping;

import static com.nlh.minishoping.NotificationClass.CHANNEL_1;
import static com.nlh.minishoping.NotificationClass.CHANNEL_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.nlh.minishoping.Connector.ServerConnector;

public class MainActivity extends AppCompatActivity {

    private static final int WELCOME_NOTIFICATION_ID = 1;
    private static final int DISCOUNT_NOTIFICATION_ID = 2;

    Fragment currentFragment;
    CartMap cartMap;

    String hashValue;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        cartMap = CartMap.getInstance();

        StoreFragment storeFragment = new StoreFragment();
        CartFragment cartFragment = new CartFragment();
        FavoriteListFragment favoriteListFragment = new FavoriteListFragment();
        MapsFragment mapsFragment = new MapsFragment();
        NotificationFragment notificationFragment = new NotificationFragment();

        String notificationString = getIntent().getStringExtra("Fragment");
        email = getIntent().getStringExtra("Email");
        Log.i("EMAIL", email);
        if (notificationString != null) {
            Log.i("NOTIFICATION STRING", notificationString);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, storeFragment)
                    .add(R.id.fragment_container, cartFragment)
                    .add(R.id.fragment_container, favoriteListFragment)
                    .add(R.id.fragment_container, notificationFragment)
                    .add(R.id.fragment_container, mapsFragment)
                    .hide(cartFragment)
                    .hide(favoriteListFragment)
                    .hide(storeFragment)
                    .hide(mapsFragment)
                    .commit();
            currentFragment = storeFragment;
            bottomNavigationView.setSelectedItemId(R.id.notification);
        } else {

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, storeFragment)
                    .add(R.id.fragment_container, cartFragment)
                    .add(R.id.fragment_container, favoriteListFragment)
                    .add(R.id.fragment_container, notificationFragment)
                    .add(R.id.fragment_container, mapsFragment)
                    .hide(cartFragment)
                    .hide(favoriteListFragment)
                    .hide(notificationFragment)
                    .hide(mapsFragment)
                    .commit();
            currentFragment = storeFragment;
            bottomNavigationView.setSelectedItemId(R.id.home);
            sendWelcomeNotification();

            sendDiscountNotification();
        }

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
                case R.id.notification:
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment)
                            .show(notificationFragment)
                            .commit();
                    currentFragment = notificationFragment;
                    break;
                default:
                    return false;
            }
            return true;
        });

        bottomNavigationView.setOnItemReselectedListener(item -> {
        }); // disable reselect functional

        // Debug only
//        Log.d("Email", getIntent().getExtras().getString("Email"));
        Toast.makeText(this, "Chào mừng " + getIntent().getExtras().getString("Email"), Toast.LENGTH_SHORT).show();


        sendDiscountNotification();
//
//        // GET SEARCH RESULTS
//        int[] arr = ServerConnector.GetSearchResults("voucher");
//
        // TODO: EDIT CODE HERE, REPLACE THE HARD-CODED EMAIL WITH THE USER'S EMAIL
        // GET THE HASH VALUE OF THIS EMAIL
        hashValue = ServerConnector.RegisterOrLogin(email);
        Log.i("REGISTER ANSWER", String.valueOf(hashValue));
//
//        // GET PRODUCTS BY CATEGORY
//        int[] catArr = ServerConnector.GetProductsByCategory("Bách hóa");
//
//        // TEST ADD PRODUCT TO FAVORITE
//        int ans = ServerConnector.AddProductToFavorite("bac265735b6b4d63d1d2c33e6ddb314dcb33c37ebe747213b3186c92ab37956d", 3);
//        Log.i("FAVORITE RETURNED", String.valueOf(ans));

//        double dis = ServerConnector.GetVoucherDiscount("HAHA");
//        Log.i("VOUCHER DISCOUNT", String.valueOf(dis));
    }

    // https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
    public String getHashValue() {
        return hashValue;
    }

    private void sendWelcomeNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_24);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setContentTitle(getString(R.string.welcome_noti_title))
                .setContentText(getString(R.string.welcome_noti_content))
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(WELCOME_NOTIFICATION_ID, notification);
        }
    }


    private void sendDiscountNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_24);

        // Create an Intent for the activity you want to start
        Intent notificationIntent = new Intent(this, MainActivity.class);
        //notificationIntent.putExtra("ID", 2);
        notificationIntent.putExtra("Fragment", "NotificationFragment");
        notificationIntent.putExtra("Email", email);


        // Create the TaskStackBuilder and add the notificationIntent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);

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
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(DISCOUNT_NOTIFICATION_ID, notification);
        }
    }
}