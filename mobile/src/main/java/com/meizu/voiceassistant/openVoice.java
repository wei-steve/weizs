package com.meizu.voiceassistant;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.meizu.voiceassistant.activity.ShowResultActivity;
import com.meizu.voiceassistant.iatService.Grammar;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;
import com.meizu.voiceassistant.util.FucUtil;
import com.meizu.voiceassistant.util.TTS;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by weichangtan on 16/6/12.
 */
public class openVoice extends BroadcastReceiver {
    boolean isFirstIn = false;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            //execute the task
            startiat();
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        if(flag){
//            isForeground(this,"");
            ((SpeechApp) context.getApplicationContext()).setResultShowed(true);
            Intent result_show =new Intent(context,ShowResultActivity.class);
            result_show.putExtra("suoping",true);
            context.startActivity(result_show);
        }
        SharedPreferences preferences = context.getSharedPreferences("first_pref",context.MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);


        if (isFirstIn) {  //初次打开，先构建语法
            String mGrammar = FucUtil.readFile(context, "call.bnf", "utf-8");
            Intent bintent = new Intent(context, Grammar.class);
            bintent.putExtra("grammar",mGrammar);
            bintent.putExtra("flag", Grammar.BuildGrammar);
            context.startService(bintent);



            new TTS().start("正在构建语法", TTS.zh);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        } else {


//            if (isServiceWork(context, "com.meizu.voiceassistant.iatService.RecognizeIntentService")) {
//                startiat();
//            } else {
			Intent mintent=new Intent(context,RecognizeIntentService.class)
			.putExtra("iat",true);
                context.startService(mintent);
               
//            }
        }
    }


    /**
     * @param mContext
     * @param serviceName
     * @return
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
//        Log.d("openVoice", "总共多少个service: " + myList.size());
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            Log.d("openVoice", "isServiceWork: " + mName);
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public void startiat() {
        RecognizeIntentService.handler.sendEmptyMessage(1);
        RecognizeIntentService.handler.sendEmptyMessage(5);
    }
}
