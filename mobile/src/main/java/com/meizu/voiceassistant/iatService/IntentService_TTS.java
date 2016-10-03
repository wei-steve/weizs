package com.meizu.voiceassistant.iatService;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.meizu.voiceassistant.IatSettings;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.ApkInstaller;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentService_TTS extends IntentService {

    private static String TAG = IntentService_TTS.class.getSimpleName();

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人
    private String voicer;

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue ;
    boolean chushihua=true;
    String text;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_LOCAL;
    // 语记安装助手类
    ApkInstaller mInstaller ;
    private SharedPreferences mSharedPreferences;
    Boolean duwan;


    public IntentService_TTS() {

        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");

        mEngineType =  SpeechConstant.TYPE_LOCAL;
        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, MODE_PRIVATE);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(IntentService_TTS.this, mTtsInitListener);
        // 云端发音人名称列表
        mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
//        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        super.onCreate();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart: ");

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        if(intent != null&&intent.getBooleanExtra("closs",false)){
            mTts.stopSpeaking();
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            if(!intent.getBooleanExtra("closs",false)) {
                ((SpeechApp) getApplicationContext()).setNoTTS(true);
                Log.d(TAG, "onHandleIntent: "+intent.getStringExtra("text"));
                while (chushihua) {
                    try {
                        Log.d(TAG, "正在sleep800 ");
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Log.d(TAG, "onStart");
                String language = intent.getStringExtra("language");
                duwan = true;
                // 清空参数
                mTts.setParameter(SpeechConstant.PARAMS, null);
                // 根据合成引擎设置相应参数
                mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
                // 设置在线合成发音人
                mTts.setParameter(SpeechConstant.LOCAL_SPEAKERS, language);
                //设置合成语速
                mTts.setParameter(SpeechConstant.SPEED, "60");
                //设置合成音调
                mTts.setParameter(SpeechConstant.PITCH, "50");
                //设置合成音量
                mTts.setParameter(SpeechConstant.VOLUME, "80");
                //设置播放器音频流类型
                mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "4"));
                // 设置播放合成音频打断音乐播放，默认为true
                mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
                // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
                // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
                mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
                mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
                int code = mTts.startSpeaking(intent.getStringExtra("text"), mTtsListener);
                Log.d(TAG, "onHandleIntent: "+code);

                if (code != ErrorCode.SUCCESS) {
                    if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                        //未安装则跳转到提示安装页面
                        mInstaller.install();
                    } else {
                        Log.d(TAG, "onHandleIntent: "+code);
                    }
                }
                while (duwan) {
                    try {
                        Log.d(TAG, "正在sleep1000 ");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }




    }





    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.d(TAG, "onInit: "+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                chushihua=false;
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Log.d(TAG, "onSpeakBegin: 开始播放");
        }

        @Override
        public void onSpeakPaused() {
            duwan=false;

            Log.d(TAG, "onSpeakPaused: 暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Log.d(TAG, "onSpeakResumed: 继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.d(TAG, "onCompleted: 播放完成");
                duwan=false;

            } else if (error != null) {
                Log.d(TAG, "onCompleted: "+error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

//    private void showTip(final String str) {
//        mToast.setText(str);
//        mToast.show();
//    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        ((SpeechApp) getApplicationContext()).setNoTTS(false);
        duwan=false;
        mTts.stopSpeaking();
        mTts.destroy();
        super.onDestroy();
    }
}
