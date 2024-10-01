package com.bpointer.rkofficial.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.bpointer.rkofficial.Activity.MainActivity;
import com.bpointer.rkofficial.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Log.e(TAG, "check_title: " + title);
        Log.e(TAG, "check_body: " + body);

        createNotification(title, body);
    }

    private void createNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       /* NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this, "121212")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent)
                .setFullScreenIntent(resultIntent, true)
                .setDefaults(Notification.DEFAULT_SOUND);
*/
        // Custom Notification view
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this, "121212");
        String current_time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.title, title.toUpperCase());
        remoteViews.setTextViewText(R.id.body, body);
        remoteViews.setTextViewText(R.id.notification_time, current_time);
        mNotificationBuilder.setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setCustomContentView(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setCustomBigContentView(remoteViews)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent)
                .setFullScreenIntent(resultIntent, true)
                .setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "121212";
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(channelId, "App Name", NotificationManager.IMPORTANCE_MAX);
            notificationManager.createNotificationChannel(channel);
            mNotificationBuilder.setSound(notificationSoundURI);
            mNotificationBuilder.setDefaults(Notification.DEFAULT_ALL);
            mNotificationBuilder.setChannelId(channelId);
        }

        notificationManager.notify(121212, mNotificationBuilder.build());
    }
}