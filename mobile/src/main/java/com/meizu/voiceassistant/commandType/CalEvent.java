package com.meizu.voiceassistant.commandType;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.util.time.DateChange;
import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.TodoAlarm.ReminderAlarm;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.util.ShowResult;
import com.meizu.voiceassistant.util.sqlDate;
import com.meizu.voiceassistant.SpeechApp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.meizu.voiceassistant.util.SayDate;
import com.meizu.voiceassistant.util.time.dateRegex;

/**
 * Created by weichangtan on 16/5/15.
 */
public class CalEvent extends baseTask{
    private static final String TAG = "CalEvent";
    //    private String str;
    private static Bundle reminder = new Bundle();
    private static Calendar nowTime = Calendar.getInstance();
    private static String remindtime1;
    private static String remindtime2;
    private static String remindtype;
    private static String remindtitle;
    private static NotesDB notesDB = new NotesDB(SpeechApp.getContext());
    private static SQLiteDatabase dbWriter = notesDB.getWritableDatabase();
    private static DateChange dateChange;
    private static long beginTime = 0;


    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";


    @Override
    public void doSomething(String text) {
        if (text.matches("(.+)?下一个(约会|遇见|旅程|日程|安排|活动|议程)(.+)?")){
            readNextEvents();
        }else if (text.matches("(.+)?购物清单(.+)?")){
            buylist();
        }else if (text.matches("(.+)?安排(.+(点(半|整)?|(秒钟?|分钟?|小时|天)后))?(.+)?")){
            creatEvent(text);
        }

    }

    public static void creatEvent(String str) {
        dateChange = new DateChange();
        nowTime = Calendar.getInstance();

        Matcher matcher_tixing = Pattern.compile("(?<rtime1>.+)?(?<remindtype>记得|提醒我|安排)(?<rtime2>" + dateRegex.timeregex + ")?(?<rdo>.+)?").matcher(str);
        if (matcher_tixing.find()) {
            Log.d(TAG, "结果匹配:提醒 ");
            remindtime1 = matcher_tixing.group("rtime1");
            remindtime2 = matcher_tixing.group("rtime2");
            remindtype = matcher_tixing.group("remindtype");
            remindtitle = matcher_tixing.group("rdo");
            if (remindtime1 != null) {
                beginTime = dateChange.parse(remindtime1);
            } else if (remindtime2 != null) {
                beginTime = dateChange.parse(remindtime2);
            }


            if (remindtitle != null) {

                if (beginTime != 0) {
                    if (remindtype.matches("安排")) {
                        addCalEvent();
                    } else {
                        addDB(remindtitle, beginTime);
                    }
                } else {
                    addDB(remindtitle);
                }

                ((SpeechApp) SpeechApp.getContext().getApplicationContext()).setVoiceType(0);

            } else {
                TTS.start("提醒内容是什么?", TTS.zh);
//                startRecognizer(RecognizeIntentService.mixRecognizer);
                ((SpeechApp) SpeechApp.getContext().getApplicationContext()).setVoiceType(TTS.Type_reminder);
            }
//                    offlinerecognizer();

        }
    }

    public static void readNextEvents() {
        Calendar now = Calendar.getInstance();
        Cursor eventCursor = SpeechApp.getContext().getContentResolver().query(Uri.parse(calanderEventURL), null,
                "(" + CalendarContract.Events.ORGANIZER + "=? or "
                        + CalendarContract.Events.ORGANIZER + "=? ) and "
                        + CalendarContract.Events.DELETED + "=0 and "
                        + CalendarContract.Events.DTSTART + ">?",
                new String[]{"dgtan888@gmail.com", "13826967888@163.com", String.valueOf(now.getTimeInMillis())},
                null);
        Log.d(TAG, "readNextEvents: cursor.getCount=" + eventCursor.getCount());
//        for (int i = 0; i < eventCursor.getCount(); i++) {
//            eventCursor.moveToPosition(i);
//            String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
//            long eventDtstart = eventCursor.getLong(eventCursor.getColumnIndex(CalendarContract.Events.DTSTART));
//            long now = nowTime.getTimeInMillis();
//            Log.d(TAG, "readNextEvents: " + eventTitle);
//            Log.d(TAG, "readNextEvents: " + eventDtstart);
//            Log.d(TAG, "readNextEvents: " + now);
//            Toast.makeText(SpeechApp.getContext(), eventTitle, Toast.LENGTH_SHORT).show();
//        }
        while (eventCursor.moveToNext()) {
            String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
            long eventdtstart = eventCursor.getLong(eventCursor.getColumnIndex("dtstart"));
            TTS.start("下一个日程在" + new SayDate().saydate(eventdtstart) + "，内容是" + eventTitle, TTS.zh);
        }
        if (eventCursor.getCount() == 0) {
            TTS.start("最近没有日程安排", TTS.zh);
        }
        eventCursor.close();


    }


    public static void buylist() {
        Cursor buycursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.COMPLETED + "=0 and " + NotesDB.TASK.CLEARED + "=0 and " + NotesDB.TASK.DELETED + "=0 and (" + NotesDB.TASK.TITLE + " like ? or " + NotesDB.TASK.TITLE + " like ? )", new String[]{"%卖%", "%买%"}, null,
                null, null, null);
        String buylistitem = null;
        while (buycursor.moveToNext()) {
            String buytitle = buycursor.getString(buycursor.getColumnIndex(NotesDB.TASK.TITLE));
            buylistitem = buytitle + "\n";
            Log.d(TAG, "buylist: " + buytitle);
        }
        ShowResult.show(buylistitem, ListData.voicecommand);

    }

    public static void addDB(String title, long reminder) {
        long now = Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis();
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.TASK.LIST_ID, 1);
        cv.put(NotesDB.TASK.TITLE, title);
        cv.put(NotesDB.TASK.LOCAL_PARENT_ID, 1);
        cv.put(NotesDB.TASK.REMINDER_TIME, reminder);
        cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, now);
        cv.put(NotesDB.TASK.TIMEZONE, TimeZone.getDefault().getID());
        cv.put(NotesDB.TASK.STATUS, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(reminder);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cv.put(NotesDB.TASK.TASK_DATE, cal.getTimeInMillis());

        List<sqlDate> list = new ArrayList<sqlDate>();
        Cursor cursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.COMPLETED + "=0 and " + NotesDB.TASK.CLEARED + "=0 and " + NotesDB.TASK.DELETED + "=0", null, null,
                null, NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID + " asc", null);

        if (cursor.getCount() == 0) {
            cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, 1);
        } else {
            cursor.moveToLast();
            cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID)) + 1);
        }
        dbWriter.insert(NotesDB.TASK.TABLE_NAME, null, cv);
        cursor.close();
        cv.clear();


        Cursor rcs = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.LOCAL_MODIFY_TIME + "=" + now, null, null,
                null, NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID + " asc", null);

        while (rcs.moveToNext()) {
            ContentValues rcv = new ContentValues();
            rcv.put(NotesDB.REMINDER.TASK_ID, rcs.getInt(rcs.getColumnIndex(NotesDB.TASK.ID)));
            rcv.put(NotesDB.REMINDER.REMINDER_TIME, reminder);
            rcv.put(NotesDB.REMINDER.LAST_MODIFY_DATE, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
            dbWriter.insert(NotesDB.REMINDER.TABLE_NAME, null, rcv);
            rcs.close();
            rcv.clear();
        }
        new ReminderAlarm().setAlarm(SpeechApp.getContext());
        TTS.start(new SayDate().saydate(beginTime) + title, TTS.zh);


    }

    public static void addDB(String title) {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.TASK.LIST_ID, 1);
        cv.put(NotesDB.TASK.TITLE, title);
        cv.put(NotesDB.TASK.LOCAL_PARENT_ID, 1);
        cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
        cv.put(NotesDB.TASK.TIMEZONE, TimeZone.getDefault().getID());
        cv.put(NotesDB.TASK.STATUS, 1);


        List<sqlDate> list = new ArrayList<sqlDate>();
        Cursor cursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.COMPLETED + "=0 and " + NotesDB.TASK.CLEARED + "=0 and " + NotesDB.TASK.DELETED + "=0", null, null,
                null, NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID + " asc", null);


        if (cursor.getCount() == 0) {
            cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, 1);
        } else {
            cursor.moveToLast();
            cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID)) + 1);
        }
        dbWriter.insert(NotesDB.TASK.TABLE_NAME, null, cv);
        cursor.close();
        cv.clear();

        new ReminderAlarm().setAlarm(SpeechApp.getContext());
        TTS.start(title, TTS.zh);
    }


    public static void addCalEvent() {
        // 获取要出入的gmail账户的id
        String calId = "";
        Cursor userCursor = SpeechApp.getContext().getContentResolver().query(Uri.parse(calanderURL), null, "account_type=? "+"and"+" isPrimary=1", new String[]{"com.google"}, null);
        assert userCursor != null;
        if (userCursor.getCount() > 0) {
            userCursor.moveToFirst();  //注意：是向最后一个账户添加，开发者可以根据需要改变添加事件 的账户
            calId = userCursor.getString(userCursor.getColumnIndex("_id"));
        } else {
            Toast.makeText(SpeechApp.getContext(), "没有账户，请先添加账户", Toast.LENGTH_SHORT).show();
            return;
        }


        ContentValues event = new ContentValues();
        event.put("title", remindtitle);
//                event.put("description", "Frankie受空姐邀请,今天晚上10点以后将在Sheraton动作交流.lol~");
        // 插入账户
        event.put("calendar_id", calId);
        System.out.println("calId: " + calId);
//                event.put("eventLocation", "地球-华夏");
//                Calendar mCalendar = Calendar.getInstance();
//                mCalendar.set(Calendar.HOUR_OF_DAY, 11);
//                mCalendar.set(Calendar.MINUTE, 45);
//                long justtext = mCalendar.getTime().getTime();
//                mCalendar.set(Calendar.HOUR_OF_DAY, 12);
//                long end = mCalendar.getTime().getTime();
        event.put("dtstart", beginTime);
        event.put("dtend", beginTime + 3600000);
        event.put("hasAlarm", 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());  //这个是时区，必须有，
        //添加事件
        Uri newEvent = SpeechApp.getContext().getContentResolver().insert(Uri.parse(calanderEventURL), event);
        //事件提醒的设定
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put("event_id", id);
        // 提前10分钟有提醒
        values.put("minutes", 10);
        SpeechApp.getContext().getContentResolver().insert(Uri.parse(calanderRemiderURL), values);

//        Toast.makeText(SpeechApp.getContext(), "插入事件成功!!!", Toast.LENGTH_LONG).show();
        TTS.start(new SayDate().saydate(beginTime) + remindtitle, TTS.zh);
        userCursor.close();
    }

    public void event() {

    }
}
