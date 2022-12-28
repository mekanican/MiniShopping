package com.nlh.minishoping;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class NotificationClass extends Application {
    public static final String CHANNEL_1 = "CHANNEL1";
    public static final String CHANNEL_2 = "CHANNEL2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name1 = getString(R.string.channel1_name);
        String description1 = getString(R.string.channel1_description);
        CharSequence name2 = getString(R.string.channel2_name);
        String description2 = getString(R.string.channel2_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel1 = new NotificationChannel(CHANNEL_1, name1, importance);
        channel1.setDescription(description1);

        NotificationChannel channel2 = new NotificationChannel(CHANNEL_2, name2, importance);
        channel2.setDescription(description2);

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
        }
    }
}
