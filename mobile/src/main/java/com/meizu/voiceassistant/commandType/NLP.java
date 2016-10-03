package com.meizu.voiceassistant.commandType;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.meizu.voiceassistant.IatSettings;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.IatResult;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.ShowResult;
import com.meizu.voiceassistant.util.TTS;

/**
 * Created by weichangtan on 16/9/8.
 */
public class NLP {
    private static String TAG = "NLP";


    public static void check(IatResult iatResult){
        SharedPreferences mSharedPreferences= SpeechApp.getContext().getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);

        if (iatResult.isHasNLP()){
            if (iatResult.getNLPanswer() != null && mSharedPreferences.getBoolean(iatResult.getNLPname(), true)) {
                Log.d(TAG, "结果匹配:开放语义");
                ShowResult.show(iatResult.getNLPanswer(), iatResult.getNLPtype());

                if (iatResult.getNLPtype() != ListData.stock) {
                    TTS.start(iatResult.getNLPanswer(), TTS.zh);
                }
            }
        }

    }
}
