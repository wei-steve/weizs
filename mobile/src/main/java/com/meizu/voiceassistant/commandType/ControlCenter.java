package com.meizu.voiceassistant.commandType;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.meizu.voiceassistant.util.IatResult;
import com.meizu.voiceassistant.util.JsonParser;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.ShowResult;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.IatSettings;
import com.meizu.voiceassistant.SpeechApp;

/**
 * Created by weichangtan on 16/6/8.
 */
public class ControlCenter {
    private static String TAG = "ControlCenter";
    public static int mixRecognizer = 1;
    public static int englishRecognizer = 2;
    public static int offlinerecognizer = 3;
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
    public static final boolean IsJason=true;
    public static final boolean NoJason=false;
    public Handler handler;



    public static void check (IatResult jie){
        boolean match=false;
//        ShowResult.show(jie, ListData.voicecommand);

        match=Command.start(jie.getResult());

        if (!match){
            NLP.check(jie);
        }
//            //            if (voiceType == Type_asktime) {
////                Log.d(TAG, "结果匹配: 时间二次层级");
////
////                Matcher matchertime = Pattern.compile("(?<ocation>.+)呢").matcher(jie);
////                if (matchertime.find()) {
////                    Log.d(TAG, "matchertime1: ");
////                    String timelocation = matchertime.group("ocation");
////                    String saytime = AskTime.saynowTime(timelocation);
////                    ShowResult.show(saytime, ListData.answer);
////                    TTS.justtext(saytime, TTS.zh);
////
////                    break;
////                }
////            }
////                    if (voiceType == Type_reminder) {
////                        Log.d(TAG, "结果匹配:提醒的二次层级 ");
//////                        CalEvent.putString("RTODO", jie);
//////                        Intent sintent = new Intent("Intent.voice.tasker.CalEvent");
//////                        sintent.putExtras(CalEvent);
//////                        sendBroadcast(sintent);
////
////                        ((SpeechApp) getApplicationContext()).setVoiceType(0);
////                        break;
////                    }
//

                    givetasker(jie.getResult());

    }

    private static void givetasker(String jie) {
        Intent sintent = new Intent("Intent.voice.tasker");
        sintent.putExtra("text", jie);
        SpeechApp.getContext().sendBroadcast(sintent);
    }
}
