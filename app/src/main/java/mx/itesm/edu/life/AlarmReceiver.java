package mx.itesm.edu.life;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.io.Serializable;

import mx.itesm.edu.life.models.CalEvent;

public class AlarmReceiver extends BroadcastReceiver implements Serializable {

    private static long[] VIBRATION_TIME = {500,1000};

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context,MainActivity.class);
        String desc = ((CalEvent)intent.getSerializableExtra("event")).getTitle();
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent =stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setVibrate(VIBRATION_TIME);

        Notification notification = builder.setContentTitle("Nuevo evento añadido").setContentText("Evento de "+ desc).setTicker("Nuevo evento LiFE").setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,notification);
    }
}
