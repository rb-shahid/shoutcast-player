package byteshaft.com.shoutcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

public class Notification extends ContextWrapper {
    final int ID = 404;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    public Notification(Context base) {
        super(base);
        mBuilder = new NotificationCompat.Builder(base);
    }

    android.app.Notification getNotification() {
        setupVisuals(mBuilder);
        setOnTapIntentAction(mBuilder);
        return mBuilder.build();
    }

    private void setupVisuals(NotificationCompat.Builder builder) {
        builder.setContentTitle("shoutcast");
        builder.setContentText("Tap to open app");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // dismiss notification when its tapped.
        builder.setAutoCancel(false);
        // disable slide to remove for the notification.
        builder.setOngoing(false);
    }

    void setOnTapIntentAction(NotificationCompat.Builder builder) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(ID, builder.build());
        Intent intent = new Intent("byteshaft.com.shoutcast.OPEN_ACTIVITY");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
    }

    TelephonyManager getTelephonyManager() {
        return (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    void removeNotification() {
        mNotificationManager.cancel(ID);
    }
}
