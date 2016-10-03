package com.meizu.voiceassistant;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by weichangtan on 15/9/19.
 */
public class SpeechApp extends Application{
    private static final String TAG = SpeechApp.class.getSimpleName();
    private static Context context;
    private Boolean resultShowed =false;

    private static SpeechRecognizer Iat;

    public int getIatTyep() {
        return iatTyep;
    }

    public void setIatTyep(int iatTyep) {
        this.iatTyep = iatTyep;
    }

    private int iatTyep=1;

    private Boolean noTTS=false;
    private int VoiceType;
    public void setNoTTS(Boolean noTTS) {
        this.noTTS = noTTS;
    }

    public Boolean getNoTTS() {
        return noTTS;
    }

    public void setVoiceType(int voiceType) {
        this.VoiceType = voiceType;
    }

    public int getVoiceType() {
        return VoiceType;
    }

    public void setResultShowed(boolean resultShowed){
        this.resultShowed = resultShowed;
    }

    public Boolean getResultShowed() {
        Log.d(TAG, "getResultShowed: "+resultShowed);
        return resultShowed;
    }

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(SpeechApp.this, "appid=" + getString(R.string.app_id)+",engine_mode=plus,"+SpeechConstant.FORCE_LOGIN+"=true");
        context = getApplicationContext();
        Iat = SpeechRecognizer.createRecognizer(context, mInitListener);
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误


        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.showLogcat(false);



        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
    public static SpeechRecognizer getIat() {
        return Iat;
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(context,"初始化失败，错误码：" + code,Toast.LENGTH_LONG).show();
            }
        }
    };
}
