package com.meizu.voiceassistant.commandType.weather;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.commandType.baseTask;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;
import android.support.v4.app.*;

/**
 * Created by weichangtan on 16/9/25.
 */

public class AskWeather extends baseTask {
    @Override
    public void doSomething(String text) {
        check();
    }
    private void check(){
        WeatherGet weatherGet = new WeatherGet() {
            @Override
            public void getweather(String json) {
                WeatherBean weatherBean = Parse.resolveWeatherInf(json);
                get(weatherBean);
            }
        };
        new heweather("city="+"beijing",weatherGet).execute();
    }
    private void get(WeatherBean bean){
        Bitmap icon= BitmapFactory.decodeResource(SpeechApp.getContext().getResources(), R.mipmap.ic_launcher);
        NotificationManager manager = (NotificationManager) SpeechApp.getContext().getSystemService(SpeechApp.getContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder testBuild = new NotificationCompat.Builder(SpeechApp.getContext());

        RemoteViews remoteViews = new RemoteViews(SpeechApp.getContext().getPackageName(), R.layout.item_weather);
        remoteViews.setTextViewText(R.id.tv_weather, bean.getCity()+" , "+bean.getCnty());
        remoteViews.setTextViewText(R.id.tv_weather2, bean.getLoc());



        Intent aintent = new Intent(SpeechApp.getContext(), RecognizeIntentService.class);
        PendingIntent apendingIntent = PendingIntent.getActivity(SpeechApp.getContext(), 0, aintent, PendingIntent.FLAG_UPDATE_CURRENT);

        testBuild.setContent(remoteViews)
                .setContentIntent(apendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(android.app.Notification.VISIBILITY_PRIVATE)
                .setTicker("开始识别");
//        manager.notify(NOTIFICATION_FLAG, notify);  //发起通知

        manager.notify(2, testBuild.build());



        //        Intent intent = new Intent(context, IatSettings.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, w100, intent, 0);
//        remoteViews.setOnClickPendingIntent(R.id.settingsbnt, pendingIntent);

//        Intent dintent = new Intent(context, GrammarSetting.class);
//        PendingIntent ppendingIntent = PendingIntent.getActivity(context, w100, dintent, 0);
//        remoteViews.setOnClickPendingIntent(R.id.goujianyufa, ppendingIntent);
    }
}
