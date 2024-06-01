package com.gps.camera.timestamp.photo.geotag.map.utils.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MyNotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION = "notification";
    private static final String NOTIFICATION_CHANNEL_ID = "NotificationIDDD";
    public static String NOTIFICATION_ID = "notification-id";

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = (Notification) intent.getParcelableExtra(NOTIFICATION);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", 4));
        }
        notificationManager.notify(intent.getIntExtra(NOTIFICATION_ID, 0), notification);
    }
}
