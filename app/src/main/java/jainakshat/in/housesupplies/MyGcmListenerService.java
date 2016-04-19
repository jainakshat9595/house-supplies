package jainakshat.in.housesupplies;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Akshat Jain on 12/1/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "HouseSupplies_MyGcmListenerService";
    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) { } else { }
        sendNotification(message);
    }

    private void sendNotification(String message) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.customnotification);
        remoteViews.setTextViewText(R.id.textView2, message);
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent aIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                aIntent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_ico)
                        .setAutoCancel(true)
                        .setContentText(message)
                        .setContent(remoteViews);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
