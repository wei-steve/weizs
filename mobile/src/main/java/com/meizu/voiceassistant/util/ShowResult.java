package com.meizu.voiceassistant.util;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.activity.ShowResultActivity;

/**
 * Created by weichangtan on 16/5/27.
 */
public class ShowResult {
    private static String TAG = "ShowResult";

    public static void show(final String voicecommand, final int viewtype){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "show: ");
                while (!((SpeechApp) SpeechApp.getContext().getApplicationContext()).getResultShowed()) {
                    ComponentName cn = new ComponentName("com.meizu.voiceassistant", "com.meizu.voiceassistant.activity.ShowResultActivity");
                    Intent bintent = new Intent();
                    bintent.setComponent(cn);
                    bintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SpeechApp.getContext().startActivity(bintent);
                    try {
                        Log.d(TAG, "正在sleep1000");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("voicecommand", voicecommand);
                bundle.putInt("viewtype", viewtype);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what=3;
                ShowResultActivity.handler.sendMessage(msg);
            }
        }).start();

    }
}
