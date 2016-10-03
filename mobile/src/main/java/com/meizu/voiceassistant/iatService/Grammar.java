package com.meizu.voiceassistant.iatService;

import android.app.IntentService;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.util.ContactManager;
import com.meizu.voiceassistant.IatSettings;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.ApkInstaller;
import com.meizu.voiceassistant.util.TTS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class Grammar {
    private static String TAG = Grammar.class.getSimpleName();
    public static int BuildGrammar = 1;
    public static int UpdateLexicon = 2;

    // 语音识别对象
    private SpeechRecognizer mAsr;
    private Toast mToast;
    private SharedPreferences mSharedPreferences = SpeechApp.getContext().getSharedPreferences(IatSettings.PREFER_NAME,	MODE_PRIVATE);


    // 本地语法文件
    private String mLocalGrammar = null;
    // 云端语法文件
    private String mCloudGrammar = null;
    // 本地词典
    private String mLocalLexicon = "110\n";
    // 云端语法文件
    private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
    private static final String GRAMMAR_TYPE_ABNF = "abnf";
    private static final String GRAMMAR_TYPE_BNF = "bnf";

    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语记安装助手类
    ApkInstaller mInstaller;
    File file = new File("/mnt/sdcard/msc", "call.txt");
    String writetxt;
    private boolean InitOK;


    public Grammar() {
    }

    // 语法、词典临时变量
    String mContent;
    // 函数调用返回值
    int ret = 0;


    public void buildGrammar(SpeechRecognizer mAsr, String grammar) {
        mContent = grammar;
        mAsr.setParameter(SpeechConstant.PARAMS, null);
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        //指定引擎类型
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        ret = mAsr.buildGrammar(GRAMMAR_TYPE_BNF, grammar, mLocalGrammarListener);
        if (ret != ErrorCode.SUCCESS) {
            if (ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
                mInstaller.install();
            } else {
                showTip("语法构建失败,错误码：" + ret);
            }
        }
    }

    public void buildGrammarCloud(SpeechRecognizer mAsr, String grammar) {
        mContent = grammar;
        mAsr.setParameter(SpeechConstant.PARAMS, null);
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        //指定引擎类型
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, mCloudGrammarListener);
        if (ret != ErrorCode.SUCCESS)
            showTip("语法构建失败,错误码：" + ret);
    }

    public void updateLexicon(SpeechRecognizer mAsr, String lexicon, String type) {
        //获取联系人

        mAsr.setParameter(SpeechConstant.PARAMS, null);
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        mAsr.setParameter(SpeechConstant.GRAMMAR_LIST, "call");
        ret = mAsr.updateLexicon(type, lexicon, mLexiconListener);
        if (ret != ErrorCode.SUCCESS) {
            if (ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
                mInstaller.install();
            } else {
                showTip("更新词典失败,错误码：" + ret);
            }
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
                showTip("初始化失败,错误码：" + code);
            } else {
                InitOK = true;
            }
        }
    };

    /**
     * 更新词典监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error == null) {
                TTS.start("词典更新成功", TTS.zh);
                showTip("词典更新成功");
            } else {
                showTip("词典更新失败,错误码：" + error.getErrorCode());
            }
        }
    };

    /**
     * 本地构建语法监听器。
     */
    private GrammarListener mLocalGrammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if (error == null) {
                TTS.start("语法构建成功", TTS.zh);
                showTip("语法构建成功：" + grammarId);
                WriteFiles(mContent);
            } else {
                showTip("语法构建失败,错误码：" + error.getErrorCode());
            }
        }
    };

    /**
     * 云端构建语法监听器。
     */
    private GrammarListener mCloudGrammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if (error == null) {
                String grammarID = new String(grammarId);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(KEY_GRAMMAR_ABNF_ID, grammarID);
                editor.commit();
                TTS.start("语法构建成功"+grammarId,TTS.zh);
                showTip("语法构建成功：" + grammarId);
            } else {
                TTS.start("语法构建失败,错误码："+ error.getErrorCode(),TTS.zh);
                showTip("语法构建失败,错误码：" + error.getErrorCode());
            }
        }
    };

    /**
     * 获取联系人监听器。
     */
    private ContactManager.ContactListener mContactListener = new ContactManager.ContactListener() {
        @Override
        public void onContactQueryFinish(String contactInfos, boolean changeFlag) {
            //获取联系人
            mLocalLexicon = contactInfos;
        }
    };

    private void showTip(final String str) {
        new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        };
    }

    //写入文件
    public void WriteFiles(final String content) {
        //新线程把语法写入sdcard
        new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(content.getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//            Message msg = new Message();
//            msg.what = 1;
//            handler.sendMessage(msg);

            }

        }).start();

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String txt = b.getString("txt");
        }
    };


}
