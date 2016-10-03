package com.meizu.voiceassistant.commandType;

import android.content.Intent;
import android.provider.AlarmClock;
import android.util.Log;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.util.time.DateChange;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.SpeechApp;
import com.time3.nlp.stringPreHandlingModule;

/**
 * Created by weichangtan on 16/5/14.
 */
public class Timer extends baseTask{
    private static final String TAG="timer";

    @Override
    public void doSomething(String text) {
        start(text);
    }


    public static void start(String time_str) {
        time_str = stringPreHandlingModule.numberTranslator(time_str);

        Matcher matcher = Pattern.compile("(?<minute>\\d{1,12})(?<unittime>秒钟?|分钟?|小时|天|日)倒计时").matcher(time_str);
                Log.d(TAG, "结果匹配:倒计时");
        if (matcher.find()){
        String minute = matcher.group("minute");
        String unittime = matcher.group("unittime");



        if (minute.matches("\\d{1,12}")) {

            int ms=0;
            if (unittime.matches("秒钟?")){
                ms=1;}
            if (unittime.matches("分钟?")){
                ms=60;}
            if (unittime.matches("小时")){
                ms=3600;}
            if (unittime.matches("天|日")){
                ms=86400;}
            int timer = Integer.parseInt(minute)*ms;

            Intent intent_a = new Intent(AlarmClock.ACTION_SET_TIMER);
            intent_a.putExtra(AlarmClock.EXTRA_MESSAGE, "...");
            intent_a.putExtra(AlarmClock.EXTRA_LENGTH, timer);
            intent_a.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            intent_a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent_a.resolveActivity(SpeechApp.getContext().getPackageManager()) != null) {
                SpeechApp.getContext().startActivity(intent_a);
            }
            TTS.start(minute+unittime+"倒计时开始", TTS.zh);

            ((SpeechApp) SpeechApp.getContext().getApplicationContext()).setVoiceType(TTS.Type_reminder);

        }

    }
    }
}
