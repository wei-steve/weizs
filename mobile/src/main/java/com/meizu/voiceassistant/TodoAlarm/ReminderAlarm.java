package com.meizu.voiceassistant.TodoAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.SpeechApp;

import java.util.Calendar;

/**
 * Created by weichangtan on 16/5/18.
 */
public class ReminderAlarm {
    private static final String TAG="ReminderAlarm";
    private static final String reminderAlarm="com.weichang.reminderAlarm";
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;

    public void setAlarm(Context mContent) {
        Calendar now=Calendar.getInstance();
        notesDB = new NotesDB(mContent);
        dbReader = notesDB.getReadableDatabase();
        Cursor cursor= dbReader.query(NotesDB.REMINDER.TABLE_NAME,null,null,null,
                null,null,NotesDB.REMINDER.REMINDER_TIME +" asc");
        while (cursor.moveToNext()) {
            long alarmtime = cursor.getLong(cursor.getColumnIndex(NotesDB.REMINDER.REMINDER_TIME));
            int alarmID = cursor.getInt(cursor.getColumnIndex(NotesDB.REMINDER.TASK_ID));
            Log.d(TAG, "适配到的提醒内容为: " + alarmID + ";  时间:" + alarmtime);


            Intent intent = new Intent(mContent, AlarmReceiver.class);
            intent.setAction(reminderAlarm+alarmID);
            intent.putExtra("id", alarmID);
            // 以上给intent设置的四个属性是用来区分你发给系统的闹钟请求的，当你想取消掉之前发的闹钟请求，这四个属性，必须严格相等，所以你需要一些比较独特的属性，比如服务器返回给你的json中某些特定字段。
            //当然intent中也可以放一些你要传递的消息。
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContent, 1, intent, PendingIntent.FLAG_NO_CREATE);
            //alarmCount是你需要记录的闹钟数量，必须保证你所发的alarmCount不能相同，最后一个参数填0就可以。
            AlarmManager am = (AlarmManager) mContent.getSystemService(mContent.ALARM_SERVICE);
//            if (pendingIntent==null) {
//                new TTS().justtext("id"+alarmID+"未设置提醒" , TTS.zh);
//            }else {
//                new TTS().justtext("id"+alarmID+"ok" , TTS.zh);
//
//            }
            if (pendingIntent==null) {
                PendingIntent pIntent = PendingIntent.getBroadcast(mContent, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.set(AlarmManager.RTC_WAKEUP, alarmtime, pIntent);
            }
            //这样闹钟的请求就发送出去了。time是你要被提醒的时间，单位毫秒，注意不是时间差。第一个参数提醒的需求用我给出的就可以，感兴趣的朋友，可以去google一下，这方面的资料非常多，一共有种，看一下就知道区别了。
            //System.currentTimeMillis()是获取系统时间 ,+120000是120秒即两分钟后提醒
        }
    }
    public void cancelAlarm(int id){
        notesDB = new NotesDB(SpeechApp.getContext());
        dbReader = notesDB.getReadableDatabase();
        Cursor cursor_cansel=dbReader.query(NotesDB.REMINDER.TABLE_NAME,null,NotesDB.REMINDER.TASK_ID+"="+id,null,null,null,null,null);
        while (cursor_cansel.moveToNext()) {
            dbReader.delete(NotesDB.REMINDER.TABLE_NAME, NotesDB.REMINDER.TASK_ID + "=" + id, null);
        }
        Intent intent = new Intent(SpeechApp.getContext(), AlarmReceiver.class);
        intent.setAction(reminderAlarm+id);
        intent.putExtra("id", id);
        // 以上给intent设置的四个属性是用来区分你发给系统的闹钟请求的，当你想取消掉之前发的闹钟请求，这四个属性，必须严格相等，所以你需要一些比较独特的属性，比如服务器返回给你的json中某些特定字段。
        //当然intent中也可以放一些你要传递的消息。
        PendingIntent pIntent = PendingIntent.getBroadcast(SpeechApp.getContext(), 1, intent,PendingIntent.FLAG_NO_CREATE);
        //alarmCount是你需要记录的闹钟数量，必须保证你所发的alarmCount不能相同，最后一个参数填0就可以。
        AlarmManager am = (AlarmManager) SpeechApp.getContext().getSystemService(SpeechApp.getContext().ALARM_SERVICE);
        if (pIntent!=null) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(SpeechApp.getContext(), 1, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            am.cancel(pendingIntent);
        }
        cursor_cansel.close();
    }

}
