package com.meizu.voiceassistant;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import java.util.HashMap;

import android.app.NotificationManager;

import com.meizu.voiceassistant.iatService.Iat;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;


public class MyService extends Service {



    private static String TAG = MyService.class.getSimpleName();
    // 语音听写对象
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 语记安装助手类
//    String jie;
    boolean tixing = false;
    private SoundPool soundPool;
    HashMap<Integer, Integer> soundMap = new HashMap<>();
    private Context context = this;

    private NotificationManager manager;
    public static Handler handler;



    public MyService() {

    }

    @Override
    public void onCreate() {
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);

        HandlerThread handlerThread = new HandlerThread("com.meizu.handler");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        handler = new Handler(looper) {

        };
        super.onCreate();
    }

//    //language zh中文，否则随意填
//    private void justtext(String text, String language) {
//        Intent intent = new Intent(this, IntentService_TTS.class);
//        intent.putExtra("text", text);
//        intent.putExtra("language", language);
//        startService(intent);
//        try {
//    Log.d(TAG, "正在sleep300 ");
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }



    // 函数调用返回值
    int ret = 0; // 函数调用返回值

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    private void startRecognizer(int recType) {
        ((SpeechApp) getApplicationContext()).setIatTyep(Iat.mixRecognizer);
        Intent intent = new Intent(MyService.this, RecognizeIntentService.class);
//        intent.putExtra(RecognizeIntentService.recType, recType);
        startService(intent);
    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }


    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        Log.d(TAG, "MyService onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





    private void givetasker(String result) {
        Intent sintent = new Intent("Intent.voice.tasker");
        sintent.putExtra("HELLL", result);
        sendBroadcast(sintent);
    }

    private void calltasker(String result) {
        Intent sintent = new Intent("Intent.voice.tasker.call");
        sintent.putExtra("HELLL", result);
        sendBroadcast(sintent);
    }





    public void gettimezone() {
    }

}


