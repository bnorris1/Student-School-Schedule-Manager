package com.example.c196bnorris.src.ui;
import static android.content.Context.NOTIFICATION_SERVICE;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.example.c196bnorris.R;

public class MyReceiver extends BroadcastReceiver {

    String channelID = "ch1";
    static int notifID;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,intent.getStringExtra("a"),Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channelID);

        Notification notif=new NotificationCompat.Builder(context,channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("a"))
                .setContentTitle("C195 Notification").build();
        NotificationManager nm=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(notifID++,notif);
    }

    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.

    private void createNotificationChannel(Context context,String channelID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}