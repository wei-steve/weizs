package com.meizu.voiceassistant.commandType.AirplaneMode;

import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.commandType.baseTask;

/**
 * Created by weichangtan on 16/9/16.
 */

public class ChangeAirplaneMode extends baseTask {
    private static final String TAG = ChangeAirplaneMode.class.getSimpleName();
    @Override
    public void doSomething(String text) {
//        Log.d(TAG, "doSomething: "+text);
        if (text.contains("开")){
            change(true);
        }else if (text.contains("关")){
            change(false);
        }
    }

    public static void change(boolean turn){
        airplane.setAirplane(SpeechApp.getContext(),turn);
    }
}
