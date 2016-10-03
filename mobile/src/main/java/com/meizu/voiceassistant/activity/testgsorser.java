package com.meizu.voiceassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.util.time.dateRegex;
import com.time3.nlp.TimeNormalizer;
import com.time3.nlp.TimeUnit;
import com.time3.util.DateUtil;


public class testgsorser extends Activity {
    private static final String TAG = testgsorser.class.getSimpleName();
    //    float x, y, z;
//
//    SensorManager sensormanager = null;
//
//    Sensor accSensor = null;
//
//    Sensor lightSensor = null;
//
//    Sensor proximitySensor = null;
    String first;
    String second;
    String other;
    String mills;
    String unittime;
    String year;
    String month;
    String day;
    String week;
    String which_week;
    String day_of_week;
    String ampm;
    String hour;
    String minute;

    TextView textView1, textView2;
    EditText editText, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testgsorser);
        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);
        editText = (EditText) findViewById(R.id.edittext1);
        editText2 = (EditText) findViewById(R.id.edittext2);


        editText.setText("明年2月4日上午3点半");
        editText2.setText("(?<other>[^明今]*)");


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String path = "/sdcard/TimeExp.m";
                TimeNormalizer normalizer = new TimeNormalizer(path);
                normalizer.parse(s.toString());
                TimeUnit[] unit = normalizer.getTimeUnit();

                String text1 = "TimerNLP工具-结果:" + "\n";
                text1 = text1 + "待处理：" + s.toString();

                for (int i = 0; i < unit.length; i++) {
                    text1 = text1 + "文本:" + unit[i].Time_Expression + ",  对应时间:" + DateUtil.formatDateDefault(unit[i].getTime()) + "    全天:" + unit[i].getIsAllDayTime() + "\n";
                }
                textView1.setText(text1);
//
//
                test(s.toString());

            }
        });


    }


    private void test(String time) {
        textView2.setText("");

        String abc = dateRegex.test(time);


        textView2.setText(abc);
    }

    private void test2(String time) {
        textView2.setText("");
        Matcher matcher = Pattern.compile("(?<remind>[^提醒我吃饭]+)?提醒我(?<remind>.+)?吃饭?").matcher(time);
        if (matcher.find()) {
            textView2.setText("matcher.toString" + matcher.toString() + "\n" +
                    "remindgroud" + matcher.group("remind") + "\n");
        }
    }
}
