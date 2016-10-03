package com.meizu.voiceassistant.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.meizu.voiceassistant.iatService.IntentService_TTS;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.commandType.stock.HttpGetStock;
import com.meizu.voiceassistant.commandType.stock.HttpGetStockDateListener;
import com.meizu.voiceassistant.commandType.stock.SinaStockInfo;
import com.meizu.voiceassistant.commandType.translate.HttpGetEnglish;
import com.meizu.voiceassistant.commandType.translate.HttpGetEnglishDateListener;
import com.meizu.voiceassistant.ui.DynamicWave;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.util.adapter.Adapter_IatResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ShowResultActivity extends Activity {
    private static String TAG = ShowResultActivity.class.getSimpleName();
    private LinearLayout layout;
    private ListView lv;
    private List<ListData> lists;
    private Adapter_IatResult adapter;
    private screenactionreceiver clossactivity = new screenactionreceiver();
    public static Handler handler;
    public DynamicWave wave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        if (getIntent().getBooleanExtra("lockscreen", false)) {
//            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
//            setTheme(R.style.AppTheme_NoActionBar);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_show);
//        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        registerReceiver(clossactivity, new IntentFilter(Intent.ACTION_SCREEN_OFF));

        layout = (LinearLayout) findViewById(R.id.layout);
        lv = (ListView) findViewById(R.id.lv);
        lists = new ArrayList<ListData>();
        adapter = new Adapter_IatResult(lists, this);
        lv.setAdapter(adapter);
        wave = (DynamicWave) findViewById(R.id.wave);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //1=声音wave大小
                if (msg.what == 1) {
                    if (msg.arg1 == 0) {
                        layout.setVisibility(View.GONE);
                        finish();
                    }
                    //2
                } else if (msg.what == 2) {
                    if (msg.arg1 > 0) {
                        layout.setVisibility(View.VISIBLE);
                    }
                    wave.setFactor(msg.arg1 * 5);
                    //3
                } else if (msg.what == 3) {
                    Bundle bundle = msg.getData();
                    String voicecommand = bundle.getString("voicecommand");
                    Log.d(TAG, "handleMessage: " + voicecommand);
                    if (bundle.getInt("viewtype") == ListData.stock && ((SpeechApp) getApplicationContext()).getResultShowed()) {
                        int refreshNum = bundle.getInt("refreshNum", lists.size());
                        new HttpGetStock(voicecommand, refreshNum, msg.getData().getBoolean("refresh", false), httpGetStockDateListener, handler).execute();

                    } else if (bundle.getInt("viewtype") == ListData.english) {
                        try {
                            new HttpGetEnglish(URLEncoder.encode(voicecommand, "utf-8"), httpGetEnglishDateListener).execute();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    } else {
                        lists.add(new ListData(bundle.getString("voicecommand"), bundle.getInt("viewtype", 3), null));
                        adapter.notifyDataSetChanged();
                    }

                }
                super.handleMessage(msg);
            }
        };

//        TimerTask task = new TimerTask() {
//            public void run() {
//                finish();
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, 10000);


    }

    private HttpGetEnglishDateListener httpGetEnglishDateListener = new HttpGetEnglishDateListener() {
        @Override
        public void getEnglishDateUrl(String date) {
            JSONTokener engtokener = new JSONTokener(date);
            JSONObject engResult = null;
            try {
                engResult = new JSONObject(engtokener);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (engResult != null) {
                    lists.add(new ListData(engResult.getString("query"), ListData.english, null));
                    lists.add(new ListData(engResult.getString("translation"), ListData.english, null));
                    adapter.notifyDataSetChanged();
                }
                assert engResult != null;
                Log.d(TAG, "正在sleep800 ");
                Thread.sleep(800);
//                String speeker = null;
//                if (engResult.getString("translation").matches("[a-zA-Z]{0,1}.+")) {
//                    speeker = TTS.us;
//                    justtext("英文是", TTS.zh);
//
//                } else {
//                    speeker = TTS.zh;
//                    justtext("中文是", TTS.zh);
//                }

                ttsstart(engResult.getString("translation"), TTS.zh);
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    private HttpGetStockDateListener httpGetStockDateListener = new HttpGetStockDateListener() {

        @Override
        public void getStockDateUrl(Bundle stockdate, boolean refresh, int refreshNum, String stockcode) {
            String stockresult = stockdate.getString("voicecommand");
            Bitmap bm = stockdate.getParcelable("bitmap");

            if (refresh == false) {
                lists.add(new ListData(stockresult, ListData.stock, bm));


                ArrayList<SinaStockInfo> list = new ArrayList<SinaStockInfo>(10);
                try {
                    list.add(SinaStockInfo.parseStockInfo(stockresult));
                } catch (SinaStockInfo.ParseStockInfoException e) {
                    e.printStackTrace();
                }
                StringBuilder sb = new StringBuilder();
                int size = list.size();
                sb.append(list.get(0));

                for (int i = 1; i != size; ++i) {
                    list.get(i).getName();
                    sb.append("\n\n" + list.get(i).getName());
                }

                String text = "" + list.get(0).getName() + "当前的股价是" + list.get(0).getNowPrice() + "元" +
                        "涨跌" + list.get(0).getPercentPrice();
                ttsstart(text, TTS.zh);

            } else {
                lists.set(refreshNum, new ListData(stockresult, ListData.stock, bm));
            }
            adapter.notifyDataSetChanged();


            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("refreshNum", refreshNum);
            bundle.putString("voicecommand", stockcode);
            bundle.putInt("viewtype", ListData.stock);
            bundle.putBoolean("refresh", true);
            message.setData(bundle);
            handler.sendMessageDelayed(message, 5000);

        }
    };


    private void ttsstart(String text, String language) {
        Intent intent = new Intent(this, IntentService_TTS.class);
        intent.putExtra("text", text);
        intent.putExtra("language", language);
        startService(intent);
    }


//    @Override
//    public void onClick(View v) {
//        Intent closstts = new Intent(this, IntentService_TTS.class);
//        closstts.putExtra("closs", true);
//        startService(closstts);
//        this.finish();
//    }

    @Override
    protected void onResume() {
        ((SpeechApp) getApplicationContext()).setResultShowed(true);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent closstts = new Intent(this, IntentService_TTS.class);
        closstts.putExtra("closs", true);
        startService(closstts);
        this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        ((SpeechApp) getApplicationContext()).setResultShowed(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(clossactivity);
        super.onDestroy();
    }


    class screenactionreceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }


}
