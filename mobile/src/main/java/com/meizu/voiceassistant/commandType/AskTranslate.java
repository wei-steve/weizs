package com.meizu.voiceassistant.commandType;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.ShowResult;
import com.meizu.voiceassistant.util.TTS;

import java.util.ArrayList;


/**
 * Created by weichangtan on 16/9/7.
 */
public class AskTranslate extends baseTask implements android.speech.RecognitionListener {
    private static final String TAG = AskTranslate.class.getSimpleName();

    @Override
    public void doSomething(String text) {
        start(text);
    }

    public void start(String result) {
        Message msgiat = new Message();
        msgiat.what = 2;
        msgiat.arg1 = 2;


        //翻译第二层回答
        RecognizeIntentService.handler.sendMessage(msgiat);


    }


    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        String str = "";
        Log.d(TAG, "onResults " + results);
        ArrayList data = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
//        for (int i = 0; i < data.size(); i++)
//        {
//            Log.d(TAG, "result " + data.get(i));
//            str += data.get(i);
//        }
        assert data != null;
        if (data.size() > 0) {
            str = String.valueOf(data.get(0));
            Toast.makeText(SpeechApp.getContext(), str, Toast.LENGTH_LONG).show();

            if (str.matches("^[a-zA-Z].+")) {
                Log.d(TAG, "结果匹配:英文字母 ");
                String text = null;
                if (str.matches("\\w\\s\\w\\s.+")) {
                    text = str.replaceAll("\\s", "");
                } else {
                    text = str;
                }

                TTS.start(text, TTS.us);
                ShowResult.show(text, ListData.english);
            }
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
