package com.shiftscheduler.application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.util.Log;

import com.shiftscheduler.R;
import com.shiftscheduler.objects.Account;
import com.shiftscheduler.presentation.GlanceActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String employeeId = intent.getStringExtra("employeeId");
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int notificationId = intent.getIntExtra("notificationId", 0);

        Account loginAccount = ((MyApplication)context.getApplicationContext()).getLoginAccount();
        if (loginAccount == null) {
            Log.d("NotificationReceiver", "Login account is null");
            return;
        }
        if (loginAccount.getType().equals("manager")) {
            sendNotification(context, title, message, notificationId);
        } else if (loginAccount.getId().equals(employeeId)) {
            sendNotification(context, title, message, notificationId);
        }
    }

    private static void sendNotification(Context context, String title, String message, int notificationId) {
        Intent notificationIntent = new Intent(context, GlanceActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Log.d("NotificationReceiver", "Sending notification with title: " + title + " and message: " + message + " and id: " + notificationId);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }
}
