package com.meizu.voiceassistant.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weichangtan on 16/5/4.
 */
public class NotesDB extends SQLiteOpenHelper {

    public NotesDB(Context context) {
        super(context, "notes", null, 1);
    }


    public static final class TASK {
        public static final String TABLE_NAME = "Tasks";  //表名
        public static final String ID = "_id";            //表id
        public static final String GOOGLE_ID = "GoogleId";
        public static final String LIST_ID = "ListId";
        public static final String COMPLETED = "Completed";   //完成=1
        public static final String COMPLETED_DATE = "CompletedDate";//完成时间    一般为空
        public static final String TITLE = "Title";
        public static final String NOTES = "Notes";
        public static final String AUDIO = "Audio";
        public static final String TASK_DATE = "TaskDate";
        public static final String LOCAL_PARENT_ID = "LocalParentId";         //在哪list
        public static final String LOCAL_PRIOR_SIBLING_ID = "LocalPriorSiblingId";    //在那个title后面
        public static final String SERVER_MODIFY_TIME = "ServerModifyTime";//
        public static final String LOCAL_MODIFY_TIME = "LocalModifyTime";//创建的时间
        public static final String CLEARED = "Cleared";
        public static final String REMINDER_TIME = "ReminderTime";    //提醒时间
        public static final String REPEAT_FLAG = "RepeatFlag";
        public static final String PRIORITY = "Priority";
        public static final String BACKUP_STATUS = "backup_status";
        public static final String DELETED = "_deleted";  //删除
        public static final String STATUS = "_status";
        public static final String RRULE = "RRule";
        public static final String TIMEZONE = "TimeZone";
        public static final String POSITION = "position";
        public static final String ETAG = "etag";
        public static final String MOVED = "_moved";
        public static final String OLD_GOOGLE_ID = "OldGoogleId";

    }

    public static final class REMINDER {
        public static final String TABLE_NAME = "Reminder";  //表名
        public static final String TASK_ID = "taskId";  //表名
        public static final String REMINDER_TIME = "reminderTime";  //表名
        public static final String IS_SNOOZER = "isSnoozer";  //表名
        public static final String LAST_MODIFY_DATE = "lastModifyDate";  //表名
        public static final String USER_TYPE = "USER_TYPE";  //表名
        public static final String STATUS = "status";  //表名
    }

    public static final class TaskLists {
        public static final String TABLE_NAME = "TaskLists";  //表名
        public static final String ID = "_id";  //表名
        public static final String GOOGLE_ID = "GoogleId";  //表名
        public static final String NAME = "Name";  //表名
        public static final String ACCOUNT = "Account";  //表名
        public static final String IS_DEFAULT = "IsDefault";  //表名
        public static final String LATEST_SYNC_POINT = "LatestSyncPoint";  //表名
        public static final String SERVER_MODIFY_TIME = "ServerModifyTime";  //表名
        public static final String LOCAL_MODIFY_TIME = "LocalModifyTime";  //表名
        public static final String CLEARED = "Cleared";  //表名
        public static final String DELETED = "_deleted";  //表名
        public static final String STATUS = "_status";  //表名
        public static final String ORDER = "_order";  //表名
        public static final String COLOR = "Color";  //表名
        public static final String SORT_TYPE = "SortType";  //表名
        public static final String BACKUP_STAUS = "backup_status";  //表名
        public static final String ETAG = "etag";  //表名
        public static final String GOOGLE_UPDATED = "google_updated";  //表名
        public static final String OLD_GOOGLE_ID = "OldGoogleId";  //表名
        public static final String SHOW_IN_ALL = "show_in_all";  //表名
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TaskLists.TABLE_NAME + "("
                + TaskLists.ID + " integer PRIMARY KEY AUTOINCREMENT,"
                + TaskLists.GOOGLE_ID + " text,"
                + TaskLists.NAME + " text NOT NULL,"
                + TaskLists.ACCOUNT + " text NOT NULL,"
                + TaskLists.IS_DEFAULT + " integer NOT NULL DEFAULT(0),"
                + TaskLists.LATEST_SYNC_POINT + " integer,"
                + TaskLists.SERVER_MODIFY_TIME + " integer,"
                + TaskLists.LOCAL_MODIFY_TIME + " integer,"
                + TaskLists.CLEARED + " integer NOT NULL DEFAULT(0),"
                + TaskLists.DELETED + " integer NOT NULL DEFAULT(0),"
                + TaskLists.STATUS + " integer NOT NULL DEFAULT(0),"
                + TaskLists.ORDER + " integer NOT NULL DEFAULT(-1),"
                + TaskLists.COLOR + " integer,"
                + TaskLists.SORT_TYPE + " integer NOT NULL DEFAULT(1),"
                + TaskLists.BACKUP_STAUS + " integer NOT NULL DEFAULT(2),"
                + TaskLists.ETAG + " text,"
                + TaskLists.GOOGLE_UPDATED + " integer,"
                + TaskLists.OLD_GOOGLE_ID + " text,"
                + TaskLists.SHOW_IN_ALL + " integer NOT NULL DEFAULT(1)"
                + ")"

        );





        db.execSQL("CREATE TABLE " + TASK.TABLE_NAME + "("
                + TASK.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASK.GOOGLE_ID + " TEXT,"
                + TASK.LIST_ID + " INTEGER NOT NULL,"
                + TASK.COMPLETED + " integer NOT NULL DEFAULT(0),"
                + TASK.COMPLETED_DATE + " integer,"
                + TASK.TITLE + " TEXT NOT NULL,"
                + TASK.NOTES + " TEXT,"
                + TASK.AUDIO + " TEXT,"
                + TASK.TASK_DATE + " integer,"
                + TASK.LOCAL_PARENT_ID + " TEXT,"
                + TASK.LOCAL_PRIOR_SIBLING_ID + " integer,"
                + TASK.SERVER_MODIFY_TIME + " integer,"
                + TASK.LOCAL_MODIFY_TIME + " integer,"
                + TASK.CLEARED + " integer NOT NULL DEFAULT(0),"
                + TASK.REMINDER_TIME + " integer,"
                + TASK.REPEAT_FLAG + " integer NOT NULL DEFAULT(0),"
                + TASK.PRIORITY + " integer NOT NULL DEFAULT(0),"
                + TASK.BACKUP_STATUS + " integer NOT NULL DEFAULT(2),"
                + TASK.DELETED + " integer NOT NULL DEFAULT(0),"
                + TASK.STATUS + " integer NOT NULL DEFAULT(0),"
                + TASK.RRULE + " TEXT,"
                + TASK.TIMEZONE + " TEXT,"
                + TASK.POSITION + " TEXT,"
                + TASK.ETAG + " TEXT,"
                + TASK.MOVED + " integer NOT NULL DEFAULT(1),"
                + TASK.OLD_GOOGLE_ID + " TEXT"
                + ")"

        );

        db.execSQL("CREATE TABLE " + REMINDER.TABLE_NAME + "("
                + REMINDER.TASK_ID + " INTEGER PRIMARY KEY,"
                + REMINDER.REMINDER_TIME + " ingeger,"
                + REMINDER.IS_SNOOZER + " ingeger NOT NULL DEFAULT(0),"
                + REMINDER.LAST_MODIFY_DATE + " integer,"
                + REMINDER.USER_TYPE + " integer NOT NULL DEFAULT(0),"
                + REMINDER.STATUS + " ingeger NOT NULL DEFAULT(0)"
                + ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
