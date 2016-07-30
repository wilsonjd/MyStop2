package us.jeff_wilson.mystop;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created by Jeff on 4/30/2016.
 */
public class NearStopActivity extends Activity {

    public void sendNotificaiton() {
        final int NOTIF_ID = 1234;

        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);

        // notifcation builder instead
        Notification noti = new Notification.Builder(this)
                .setTicker("Stop Near")
                .setContentTitle("Stop Near")
                .setContentText("Your stop is near")
                .setContentIntent(pIntent).getNotification();

// .setSmallIcon(R.drawable.icon)

        noti.flags=Notification.FLAG_AUTO_CANCEL;


        notifManager.notify(NOTIF_ID, noti);
    }
}
