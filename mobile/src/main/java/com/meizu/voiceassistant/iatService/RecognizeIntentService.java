package com.meizu.voiceassistant.iatService;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.SpeechRecognizer;
import com.meizu.voiceassistant.IatSettings;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.commandType.AskTranslate;
import com.meizu.voiceassistant.commandType.ControlCenter;
import com.meizu.voiceassistant.util.FucUtil;
import com.meizu.voiceassistant.util.IatResult;
import com.meizu.voiceassistant.util.JsonParser;
import com.meizu.voiceassistant.util.TTS;

import java.util.HashMap;


public class RecognizeIntentService extends Service {

    private static String TAG = RecognizeIntentService.class.getSimpleName();
    // 语音听写对象
    HashMap<Integer, Integer> soundMap = new HashMap<>();
    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 语记安装助手类
    String jie;
    private SoundPool soundPool;
    private Context context = this;
    boolean Onit = false;
    private boolean loaded;
    public static Handler handler;
    boolean isFirstIn = false;
    public static SpeechRecognizer mIat;
    private SharedPreferences preferences;
    private android.speech.SpeechRecognizer speechRecognizer;
    private KeyguardManager mKeyguardManager;
    boolean isLockscreen;
    private PowerManager pm;
    private PowerManager.WakeLock mWakelock;

    public RecognizeIntentService() {

    }

    @Override
    public void onCreate() {
        //声音初始化
        initsound();
        //google 语音识别实例
        speechRecognizer = android.speech.SpeechRecognizer.createSpeechRecognizer(SpeechApp.getContext());
        speechRecognizer.setRecognitionListener(new AskTranslate());
//        startForeground(1,mnotification.showForeGround());
        //讯飞 语音识别初始化
        mIat = SpeechApp.getIat();
        preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 1: //播放音乐
                        playsound(msg.arg1);
                        break;
                    case 2:
                        speech();
                        break;
                    case 4:
                        updateNotify(msg.getData().getString("time"));
                        break;
                    case 5:
                        //开始语音录制
                        iat();
                        break;
                    case 6:
                        showTip(msg.getData().getString("toast"));
//                        updateNotify(msg.getData().getString("toast"));
                        break;
                }


            }
        };
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getBooleanExtra("iat", false)) {

                //检测屏幕状态
                checkscreen();

                //初次使用自动构建语法
                isFirstIn = preferences.getBoolean("isFirstIn", true);
                if (isFirstIn) {
                    String mGrammar = FucUtil.readFile(this, "call.bnf", "utf-8");
                    Intent bintent = new Intent(this, Grammar.class);
                    bintent.putExtra("grammar", mGrammar);
                    bintent.putExtra("flag", Grammar.BuildGrammar);
                    startService(bintent);

                    TTS.start("正在构建语法", TTS.zh);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstIn", false);
                    editor.apply();


                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean issayzai = mSharedPreferences.getBoolean(getString(R.string.say_zai), true);
                            if (issayzai) {
                                try {
                                    while (!loaded) {
                                        Log.d(TAG, "正在sleep40 ");
                                        Thread.sleep(40);
                                    }
                                    playsound(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            handler.sendEmptyMessage(5);

                        }
                    }).start();

                }

            }
        }

        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }


    private void iat() {
        Log.d(TAG, "SpeechRecognizer.getRecognizer(): " + SpeechRecognizer.getRecognizer());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Iat mIat = new Iat();
                String result = mIat.start(Iat.mixRecognizer);

                IatResult iatResult = JsonParser.parseLocalGrammarResult(result);

                ControlCenter.check(iatResult);

                if (isLockscreen) {
                    mWakelock.release();//关闭
                }
            }
        }).start();
    }

    /*
    *检测屏幕状态，黑屏状态时启动showRESULT
    */
    private void checkscreen() {

        isLockscreen = mKeyguardManager.inKeyguardRestrictedInputMode();
        if (isLockscreen) {
//            ((SpeechApp) getApplicationContext()).setResultShowed(true);
//            Intent result_show = new Intent(this, ShowResultActivity.class);
//            result_show.putExtra("lockscreen", true)
//                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(result_show);
            mWakelock.acquire();//点亮
        }
    }

    //声音初始化
    private void initsound() {
        AudioAttributes attr = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM) // 设置音效使用场景
                    // 设置音效的类型
                    .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr) // 设置音效池的属性
                    .setMaxStreams(10) // 设置最多可容纳10个音频流
                    .build();  //
        }
        soundMap.put(1, soundPool.load(RecognizeIntentService.this, R.raw.open2, 1));
        soundMap.put(2, soundPool.load(RecognizeIntentService.this, R.raw.open_mp3, 1));
        soundMap.put(3, soundPool.load(RecognizeIntentService.this, R.raw.en, 1));
        soundMap.put(4, soundPool.load(RecognizeIntentService.this, R.raw.viafly_tone_error, 1));
        soundMap.put(5, soundPool.load(RecognizeIntentService.this, R.raw.viafly_tone_stop, 1));

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
    }

    /*
    播放短促音
     */
    private void playsound(int soundType) {
        soundPool.play(soundMap.get(soundType), 1, 1, 0, 0, 1);
    }


    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }


    @Override
    public void onDestroy() {
        mIat.destroy();
        Intent intent = new Intent(this, RecognizeIntentService.class);
        startService(intent);
        Log.d(TAG, "MyService onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void updateNotify(String text) {
//        testBuild.setContentTitle("识别结果:")
//                .setContentText(text);
//				.setPriority(NotificationCompat.PRIORITY_HIGH);
//        startForeground(2,testBuild.build());
    }

    private void speech() {

        final Intent sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");

        speechRecognizer.startListening(sttIntent);
    }


}


