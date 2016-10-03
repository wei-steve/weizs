package com.meizu.voiceassistant.commandType;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.TTS;

/**
 * Created by weichangtan on 16/9/21.
 */

public class ChangeWiFi extends baseTask {
    private static final String TAG = ChangeWiFi.class.getSimpleName();
    private int state;
    private WifiManager wifiManager = (WifiManager) SpeechApp.getContext().getSystemService(Context.WIFI_SERVICE);


    public ChangeWiFi() {
    }

    public ChangeWiFi(int state) {
        this.state = state;
    }

    @Override
    public void doSomething(String text) {

        if (state==1){
            askstate();
        }else {
            if (text.contains("开")) {
                Log.d(TAG, "doSomething: " + "开飞wifi");
                change(true);
            } else if (text.contains("关")) {
                Log.d(TAG, "doSomething: " + "关飞wifi");
                change(false);
            }
        }
    }

    private void change(boolean turn) {


        if (turn) {        //打开

            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
                TTS.start("wifi已经打开", TTS.zh);
            } else {
                TTS.start("wifi本已打开", TTS.zh);
            }


        } else if (!turn) {   //关闭

            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
                TTS.start("wifi已关闭", TTS.zh);
            } else {
                TTS.start("wifi本已关闭", TTS.zh);
            }

        }

    }

    private void askstate() {
        if (wifiManager.isWifiEnabled()) {
            TTS.start("wifi状态,开启", TTS.zh);
        } else {
            TTS.start("wifi状态,关闭", TTS.zh);
        }
    }
}
