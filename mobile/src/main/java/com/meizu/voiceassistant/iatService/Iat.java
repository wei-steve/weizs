package com.meizu.voiceassistant.iatService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.meizu.voiceassistant.IatSettings;
import com.meizu.voiceassistant.NotificationService.Notify;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.activity.ShowResultActivity;

/**
 * Created by weichangtan on 16/6/10.
 */
public class Iat {
    public static String recType = "recType";
    public static int mixRecognizer = 1;
    public static int englishRecognizer = 2;
    public static int offlinerecognizer = 3;

    private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
    private IatGetResultListener iatGetResultListener;
    private String TAG = "Iat";
    // 语音听写对象
    private Toast mToast;
    private SharedPreferences mSharedPreferences = SpeechApp.getContext().getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
    // 语记安装助手类
    private String jie;
    private boolean tixing = false;
    private boolean Onit = false;
    private boolean isDown = false;
    private String result = null;
    private SpeechRecognizer mIat;
    // 函数调用返回值
    private int ret = 0; // 函数调用返回值

    private int recvoiceType;


    public static void startMixRecognizer(IatGetResultListener atGetResultListener){
    }

    public String start( int mrecvoiceType) {
        mIat = SpeechApp.getIat();
        Recognizertype(mrecvoiceType);
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
//            showTip("听写失败,错误码：" + ret);
        } else {
//            showTip(SpeechApp.getContext().getString(R.string.text_begin));
        }

        while (!isDown) {
            try {
                Log.d(TAG, "正在sleep500 ");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    //todo 语音识别参数
    private void Recognizertype(int recvoiceType) {
        mIat.setParameter(SpeechConstant.PARAMS, null);
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1200"));
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));


        switch (recvoiceType) {
            case 1:
                // mix
                mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_MIX);
                mIat.setParameter(SpeechConstant.MIXED_TYPE, "realtime");
//                mIat.setParameter(SpeechConstant.MIXED_TIMEOUT, "4000");
                mIat.setParameter(SpeechConstant.MIXED_THRESHOLD, "60");
                mIat.setParameter(SpeechConstant.LOCAL_GRAMMAR, "call");
                mIat.setParameter("asr_sch", "1");//是否进行语义识别
                mIat.setParameter(SpeechConstant.NLP_VERSION, "2.0");//通过此参数，设置开放语义协议版本号。
                mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
                mIat.setParameter(SpeechConstant.FILTER_AUDIO_TIME, "330");
                mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/asr.wav");
                // 不显示听写对话框
                break;
            case 2:
                //英语
                mIat.setParameter(SpeechConstant.FILTER_AUDIO_TIME, "330");
                mIat.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
                mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");

//                showTip("英文开始");
                String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID, null);
                Log.d(TAG, "grammarId: "+grammarId);
                //设置云端识别使用的语法id
                mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);

                break;
            case 3:
                //听写
                mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_MIX);
                mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
                mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
                mIat.setParameter(SpeechConstant.ASR_SOURCE_PATH, "sdcard//msc/asr.wav");
                break;

        }

    }


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                showTip("初始化失败，错误码：" + code);
            }

            Onit = true;


        }
    };


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onBeginOfSpeech() {
            Notify.listener();
            Log.d(TAG, "onBeginOfSpeech: ");
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            if (((SpeechApp) SpeechApp.getContext().getApplicationContext()).getResultShowed()) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 1;
                ShowResultActivity.handler.sendMessage(msg);
            }
        }

        @Override
        public void onError(SpeechError error) {
            Log.d(TAG, "onError: ");
            Log.d(TAG, "识别错误码：" + error.getErrorCode());

            boolean isOffLineRecognizer = mSharedPreferences.getBoolean(
                    SpeechApp.getContext().getString(R.string.iat_off_line_asr_preference), false);
            if (20005 == error.getErrorCode()) {
                Message msgiat = new Message();
                msgiat.what = 1;
                msgiat.arg1 = 4;
                RecognizeIntentService.handler.sendMessage(msgiat);
            }
            isDown = true;
//            if (20005 == error.getErrorCode() && isOffLineRecognizer) {
//                Intent intent = new Intent(RecognizeIntentService.this, RecognizeIntentService.class);
//                intent.putExtra(recType, 1);
//                startService(intent);
//                // Tips：
//                // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//                // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
//            } else {
//                showTip(error.getPlainDescription(true));
//            }
//            mIat.cancel();

            if (((SpeechApp) SpeechApp.getContext().getApplicationContext()).getResultShowed()) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 0;
                ShowResultActivity.handler.sendMessage(msg);
            }


        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech: ");
            Notify.cansel(19);
            Message msgiat = new Message();
            msgiat.what = 1;
            msgiat.arg1 = 5;
            RecognizeIntentService.handler.sendMessage(msgiat);

            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            if (((SpeechApp) SpeechApp.getContext().getApplicationContext()).getResultShowed()) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 0;
                ShowResultActivity.handler.sendMessage(msg);
            }

        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if (isLast){
            }
            if (recvoiceType != 2 || !isLast) {
                Log.d(TAG, "#########################"+"\n" +"isLast:" + isLast + "\n" + "onResult: " + results.getResultString() );
                isDown = true;
                result = results.getResultString();

            }

        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            if (!tixing) {

                if (((SpeechApp) SpeechApp.getContext().getApplicationContext()).getResultShowed()) {

                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = volume;
                    ShowResultActivity.handler.sendMessage(msg);
                } else {
                    toast(String.valueOf(volume));
                }
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            Log.d(TAG, "onEvent: ");
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void toast(String str) {
        Bundle bd = new Bundle();
        bd.putString("toast", str);
        Message msg = new Message();
        msg.what = 6;
        msg.setData(bd);
        RecognizeIntentService.handler.sendMessage(msg);
    }


//    private void showTip(final String str) {
//        mToast.setText(str);
//        mToast.show();
//    }

}
