package com.meizu.voiceassistant.activity;


import android.app.Activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Window;

import com.meizu.voiceassistant.iatService.Grammar;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.FucUtil;
import com.meizu.voiceassistant.util.TTS;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity{
    private static String TAG = MainActivity.class.getSimpleName();
    boolean isFirstIn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        KeyguardManager mKeyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        if(flag){
//            isForeground(this,"");
            ((SpeechApp) getApplicationContext()).setResultShowed(true);
            Intent result_show =new Intent(this,ShowResultActivity.class);
            result_show.putExtra("suoping",true);
            startActivity(result_show);
        }
        SharedPreferences preferences = getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        if (isFirstIn) {  //初次打开，先构建语法
            String mGrammar = FucUtil.readFile(this, "call.bnf", "utf-8");
            Intent intent = new Intent(MainActivity.this, Grammar.class);
            intent.putExtra("grammar",mGrammar);
            intent.putExtra("flag", Grammar.BuildGrammar);
            startService(intent);



            TTS.start("正在构建语法", TTS.zh);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        } else {

//            Intent bintent = new Intent(this, MyService.class);
//            bintent.putExtra("recognizer",true);
//            startService(bintent);
//            sendBroadcast(new Intent("android.intent.action.openVoice"));

            Intent intent = new Intent(this, RecognizeIntentService.class);
            intent.putExtra("recognizer",true);
            intent.putExtra("type",1);
            startService(intent);


            Intent sintent = new Intent("Intent.voice.tasker.kai");
            sendBroadcast(sintent);
        }

        timer.schedule(task, 500);
    }



    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
        public void run() {
            MainActivity.this.finish();
        }
    };

//    private boolean isForeground(Context context, String className) {
//        if (context == null || TextUtils.isEmpty(className)) {
//            return false;
//        }
//
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
//        if (list != null && list.size() > 0) {
//            ComponentName cpn = list.get(0).topActivity;
//            Log.d(TAG, "isForeground: "+cpn.getClassName());
//            if (className.equals(cpn.getClassName())) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}


