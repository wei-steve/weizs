package com.meizu.voiceassistant.TodoAlarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;

import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.activity.editTodoTask;

/**
 * Created by weichangtan on 16/5/18.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager manager;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;


    @Override
    public void onReceive(Context context, Intent intent) {

        notesDB = new NotesDB(context);
        dbReader = notesDB.getReadableDatabase();

        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //例如这个id就是你传过来的
        int id = intent.getIntExtra("id",0);

        Cursor cursor= dbReader.query(NotesDB.TASK.TABLE_NAME,null,NotesDB.TASK.ID+"="+id,null,
                null,null,null);
        cursor.moveToNext();
        String title=cursor.getString(cursor.getColumnIndex(NotesDB.TASK.TITLE));
        TTS.start("您有一个提醒,内容是,"+title, TTS.zh);

        //在REMINDER中，删掉数据
        dbReader.delete(NotesDB.REMINDER.TABLE_NAME,NotesDB.REMINDER.TASK_ID+"="+id,null);


        //MainActivity是你点击通知时想要跳转的Activity
        Intent playIntent = new Intent(context, editTodoTask.class);
        playIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        playIntent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("ToDo 提醒").setContentText(title).setSmallIcon(R.drawable.vc_settings).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true).setSubText("二级text");
        manager.notify(9, builder.build());

        new ReminderAlarm().setAlarm(SpeechApp.getContext());

    }
}
