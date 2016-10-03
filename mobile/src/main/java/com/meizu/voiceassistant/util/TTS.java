package com.meizu.voiceassistant.util;

import android.content.Intent;
import android.util.Log;

import com.meizu.voiceassistant.iatService.IntentService_TTS;
import com.meizu.voiceassistant.SpeechApp;

/**
 * Created by weichangtan on 16/1/6.
 */
public class TTS {
    private static String TAG = "TTS";
    public static String zh="xiaoyan";
    public static String us="aiscatherine";

    public static final int Type_empty = 0;
    public static final int Type_reminder = 1;
    public static final int Type_asktime = 2;
    public static final int Type_askdate = 3;
    public static final int Type_askweek = 4;
    public static final int Type_stock = 5;
    public static final int Type_translate = 6;
    public static final int Type_search = 7;
    public static final int Type_call = 8;
    public static final int Type_textDateChange = 9;
    public static final int Type_timer = 10;

    public static void start(String text, String language) {
        Intent intent = new Intent(SpeechApp.getContext(), IntentService_TTS.class);
        intent.putExtra("text", text);
        intent.putExtra("language", language);
        SpeechApp.getContext().startService(intent);
        try {
            Log.d(TAG, "正在sleep300");
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
