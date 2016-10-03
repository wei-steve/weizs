package com.meizu.voiceassistant.NotificationService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;

/**
 * Created by weichangtan on 16/9/24.
 */

public class Notify {
    private static Context context = SpeechApp.getContext();
    private static Bitmap icon;
    private static NotificationManager manager;

    public Notify() {
    }

    public static void listener() {
        RemoteViews rv = new RemoteViews(SpeechApp.getContext().getPackageName(), R.layout.item_listener);
        manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder testBuild = new NotificationCompat.Builder(context);

        icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Intent aintent = new Intent(context, RecognizeIntentService.class);
        PendingIntent apendingIntent = PendingIntent.getActivity(context, 0, aintent, PendingIntent.FLAG_UPDATE_CURRENT);


        long[] vibrates = {0, 1, 0, 0};
        testBuild.setContentIntent(apendingIntent)
                .setContent(rv)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("识别结果:")
                .setVibrate(vibrates)
                .setContentIntent(apendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
//                .setLargeIcon(icon)
        ;
        //当设置的时候，通知图标显示这个，而setSmallIcon显示在大图标的右下角，没设置的时候通知栏显示SmallIcon

//        startForeground(1,testBuild.build());
        manager.notify(19, testBuild.build());
//        return testBuild.build();
    }

    public static void justtext(String text) {
        manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder testBuild = new NotificationCompat.Builder(context);

        icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        Intent aintent = new Intent(context, RecognizeIntentService.class);
        PendingIntent apendingIntent = PendingIntent.getActivity(context, 0, aintent, PendingIntent.FLAG_UPDATE_CURRENT);

        testBuild.setContentIntent(apendingIntent);
//        testBuild = new NotificationCompat.Builder(context);
        testBuild.setVisibility(android.app.Notification.VISIBILITY_PRIVATE);
        long[] vibrates = {0, 1, 0, 0};
//   Notify.vibrate = vibrates;
        testBuild.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("识别结果:")
                .setContentText(text)
                .setVibrate(vibrates)
                .setContentIntent(apendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
//                .setLargeIcon(icon)
        ;
        //当设置的时候，通知图标显示这个，而setSmallIcon显示在大图标的右下角，没设置的时候通知栏显示SmallIcon

//        startForeground(1,testBuild.build());
        manager.notify(3, testBuild.build());
//        return testBuild.build();
    }

    private void create(){

    }

    public static void cansel(int num){
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.cancel(num);
    }
}
