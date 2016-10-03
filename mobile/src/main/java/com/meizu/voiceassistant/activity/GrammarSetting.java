package com.meizu.voiceassistant.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.sunflower.FlowerCollector;
import com.meizu.voiceassistant.iatService.Grammar;
import com.meizu.voiceassistant.iatService.RecognizeIntentService;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.ApkInstaller;
import com.meizu.voiceassistant.util.FucUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GrammarSetting extends Activity implements OnClickListener {
    private static String TAG = GrammarSetting.class.getSimpleName();

    // 语音识别对象
    private SpeechRecognizer mAsr;
    private Toast mToast;
    // 本地语法文件
    private String mLocalGrammar = null;
    private String mCloudGrammar = null;
    // 本地词典
    private String mLocalLexicon = null;
    private String mLocalAppName = "";
    // 云端语法文件
    private static final String GRAMMAR_TYPE_BNF = "bnf";
    private static final String GRAMMAR_TYPE_ABNF = "abnf";

    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语记安装助手类
    ApkInstaller mInstaller;
    SharedPreferences preferences;
    File file = new File("/mnt/sdcard/msc", "call.txt");
    String writetxt;


    @SuppressLint("ShowToast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.grammar_setting);
        initLayout();

        // 初始化识别对象
        mAsr = SpeechRecognizer.createRecognizer(GrammarSetting.this, mInitListener);

        // 初始化语法、命令词
        mLocalLexicon = "110\n";
        mLocalGrammar = FucUtil.readFile(this, "call.bnf", "utf-8");
        mCloudGrammar = FucUtil.readFile(this,"grammar_sample.abnf","utf-8");

        // 获取联系人，本地更新词典时使用
        ContactManager mgr = ContactManager.createManager(GrammarSetting.this, mContactListener);
        mgr.asyncQueryAllContactsName();
        checkapp();
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        mInstaller = new ApkInstaller(GrammarSetting.this);

        preferences = getSharedPreferences("first_pref", MODE_PRIVATE);

    }

    /**
     * 初始化Layout。
     */
    private void initLayout() {
        findViewById(R.id.isr_grammar).setOnClickListener(GrammarSetting.this);
        findViewById(R.id.open_grammar).setOnClickListener(GrammarSetting.this);
        findViewById(R.id.isr_lexcion).setOnClickListener(GrammarSetting.this);
        findViewById(R.id.isr_lexcionapp).setOnClickListener(GrammarSetting.this);

        //选择云端or本地
        RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioCloud) {
                    ((EditText)findViewById(R.id.isr_text)).setText(mCloudGrammar);
                    findViewById(R.id.isr_lexcion).setEnabled(false);
                    mEngineType = SpeechConstant.TYPE_CLOUD;
                } else {
                    ((EditText)findViewById(R.id.isr_text)).setText(mLocalGrammar);
                    findViewById(R.id.isr_lexcion).setEnabled(true);
                    mEngineType = SpeechConstant.TYPE_LOCAL;
                    /**
                     * 选择本地合成
                     * 判断是否安装语记,未安装则跳转到提示安装页面
                     */
                    if (!SpeechUtility.getUtility().checkServiceInstalled()) {
                        mInstaller.install();
                    }
                }
            }
        });

        ((EditText) findViewById(R.id.isr_text)).setText(mLocalGrammar);
        findViewById(R.id.isr_lexcion).setEnabled(true);
        mEngineType = SpeechConstant.TYPE_LOCAL;
        /**
         * 选择本地合成
         * 判断是否安装语记,未安装则跳转到提示安装页面
         */
        if (!SpeechUtility.getUtility().checkServiceInstalled()) {
            mInstaller.install();
        }
    }

    // 语法、词典临时变量
    String mContent;
    // 函数调用返回值
    int ret = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_grammar:
                if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                            WriteFiles(mLocalGrammar);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        showTip("文件保存在sdcard/msc/call");
                    }
                    readFiles();
                }else {
                    ((EditText)findViewById(R.id.isr_text)).setText(mCloudGrammar);
                    mContent = new String(mCloudGrammar);
                    Grammar dgrammar = new Grammar();
                    dgrammar.buildGrammarCloud(RecognizeIntentService.mIat,mContent);
                    //指定引擎类型

                }
                break;
            case R.id.isr_grammar:
                showTip("上传预设关键词/语法文件");
                mContent = ((EditText) findViewById(R.id.isr_text)).getText().toString();

                Grammar grammar = new Grammar();
                grammar.buildGrammar(RecognizeIntentService.mIat, mContent);

//				Intent intent = new Intent(GrammarSetting.this, Grammar.class);
//				intent.putExtra("grammar",mContent);
//				intent.putExtra("flag", Grammar.BuildGrammar);
//				startService(intent);
                break;
            // 本地-更新词典
            case R.id.isr_lexcion:
                ((EditText) findViewById(R.id.isr_text)).setText(mLocalLexicon);
                mContent = mLocalLexicon;

                Grammar bgrammar = new Grammar();
                bgrammar.updateLexicon(RecognizeIntentService.mIat,mLocalLexicon,"<contact>");
//
//				//指定引擎类型
//				mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
//				mAsr.setParameter(SpeechConstant.GRAMMAR_LIST, "call");
//				ret = mAsr.updateLexicon("<contact>", mContent, mLexiconListener);
//				if(ret != ErrorCode.SUCCESS){
//					if(ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
//						//未安装则跳转到提示安装页面
//						mInstaller.install();
//					}else {
//						showTip("更新词典失败,错误码：" + ret);
//					}
//				}
                break;
            case R.id.isr_lexcionapp:
                ((EditText) findViewById(R.id.isr_text)).setText(mLocalAppName);
                mContent = mLocalAppName;

                Grammar cgrammar = new Grammar();
                cgrammar.updateLexicon(RecognizeIntentService.mIat,mLocalAppName,"<appname>");
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
                showTip("初始化失败,错误码：" + code);
            }

            if (preferences.getBoolean("isFirstIn", true)) {
                mContent = new String(mLocalGrammar);
                mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
                //指定引擎类型
                mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
//				ret = mAsr.Grammar(GRAMMAR_TYPE_BNF, mContent, mLocalGrammarListener);
//				if(ret != ErrorCode.SUCCESS){
//					if(ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
//						//未安装则跳转到提示安装页面
//						mInstaller.install();
//					}else {
//						showTip("语法构建失败,错误码：" + ret);
//					}
//				}

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstIn", false);
                editor.commit();
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

    private void checkapp(){
        PackageManager packageManager = SpeechApp.getContext().getPackageManager();

        // 获取手机里的应用列表

        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);

        for (int i = 0; i < pInfo.size(); i++) {

            PackageInfo p = pInfo.get(i);

            // 获取相关包的<application>中的label信息，也就是-->应用程序的名字

            String label = packageManager.getApplicationLabel(p.applicationInfo).toString();

//            System.out.println(label);

            mLocalAppName=mLocalAppName+label+"\n";

//            if (label.equals(packageName)){ //比较label
//
//                String pName = p.packageName; //获取包名
//
//                Intent intent = new Intent();
//
//                //获取intent
//
//                intent =packageManager.getLaunchIntentForPackage(pName);
//
//                SpeechApp.getContext().startActivity(intent);
//
//            }

        }
    }

    private void showTip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }

    //写入文件
    public void WriteFiles(String content) {
        writetxt = content;
        Thread tw = new Thread(w);
        tw.start();


    }

    //读取文件
    public void readFiles() {
        Thread tr = new Thread(r);
        tr.start();
    }

    //新线程把语法写入sdcard
    Runnable w = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(writetxt.getBytes());
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

    };

    Runnable r = new Runnable() {
        public void run() {
            try {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("txt", baos.toString());
                msg.setData(b);
                handler.sendMessage(msg);
                fis.close();
                baos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String txt = b.getString("txt");
            ((EditText) findViewById(R.id.isr_text)).setText(txt);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接

    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(GrammarSetting.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(GrammarSetting.this);
        super.onPause();
    }

}
