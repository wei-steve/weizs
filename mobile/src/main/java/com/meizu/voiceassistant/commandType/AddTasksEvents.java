package com.meizu.voiceassistant.commandType;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.util.time.DateChange;
import com.meizu.voiceassistant.util.time.dateRegex;

import java.util.Calendar;

/**
 * Created by weichangtan on 16/9/14.
 */

public class AddTasksEvents extends baseTask {
    private static final String TAG = AddTasksEvents.class.getSimpleName();
    private static final ContentResolver resolver = SpeechApp.getContext().getContentResolver();
    private static String notes;
    private static String time;
    private static String secondtime;
    private static long dueDate = 0;


    @Override
    public void doSomething(String text) {
        add(text);
    }

    public static void add(String event) {
        String title = null;
        boolean hasDueDate = false;

        Matcher matcher_tixing = Pattern
                .compile("(?<time1>.+)?(记得|提醒我)(?<time2>" + dateRegex.timeregex + ")?(?<do>.+)?")
                .matcher(event);

        if (matcher_tixing.find()) {
            Log.d(TAG, "结果匹配:提醒 ");
            time = matcher_tixing.group("time1");
            secondtime = matcher_tixing.group("time2");
            title = matcher_tixing.group("do");


            if (time != null && !time.equals("")) {
                dueDate = new DateChange().parse(time) + 1000;
                hasDueDate = true;

            } else if (secondtime != null && !secondtime.equals("")) {
                dueDate = new DateChange().parse(secondtime) + 1000;
                hasDueDate = true;
            }

            Calendar now = Calendar.getInstance();
            if (time != null && secondtime != null && dueDate < now.getTimeInMillis()) {
                TTS.start("对不起,我无法回到过去提醒你", TTS.zh);
            } else {
                insert(title, hasDueDate);
            }

        }
    }

    private static void insert(String title, boolean hasDueDate) {
        Calendar now = Calendar.getInstance();
        long created = now.getTimeInMillis();
        long modified = now.getTimeInMillis() + 1000;

        ContentValues values = new ContentValues();
        values.put("completed", 0);
        values.put("created", created);
        if (hasDueDate) {
            values.put("dueDate", dueDate);
        }
        values.put("elapsedSeconds", 0);
        values.put("estimatedSeconds", 0);
        values.put("hideUntil", 0);
        values.put("importance", 3);
        values.put("modified", modified);
        values.put("notes", notes);
        values.put("recurrence", "");
        values.put("notificationFlags", 6);
        values.put("title", title);
        Uri uri = Uri.parse("content://org.tasks/tasks");
        Uri newuri = resolver.insert(uri, values);

        if (newuri != null) {
            Log.d(TAG, "task action id=" + newuri.getLastPathSegment());
            TTS.start("已经设置提醒", TTS.zh);
        } else {
            Log.d(TAG, "task action id=" + newuri.getLastPathSegment());
        }


//        SpeechApp.getContext().sendBroadcast(new Intent("android.intent.action.BOOT_COMPLETED"));

    }
}
