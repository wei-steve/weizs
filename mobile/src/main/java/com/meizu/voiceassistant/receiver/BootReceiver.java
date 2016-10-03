package com.meizu.voiceassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.meizu.voiceassistant.TodoAlarm.ReminderAlarm;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.MyService;
import com.meizu.voiceassistant.SpeechApp;

/**
 * Created by weichangtan on 16/5/1.
 */
public class BootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent mintent = new Intent(SpeechApp.getContext(), MyService.class);
            mintent.putExtra("recognizer",false);
            SpeechApp.getContext().startService(mintent);

//            TTS.justtext("重启开机了" , TTS.zh);
            new ReminderAlarm().setAlarm(SpeechApp.getContext());
        }
        if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {

        }
    }
}
