package com.meizu.voiceassistant.data;

import android.database.Cursor;

import com.meizu.voiceassistant.SQL.NotesDB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weichangtan on 16/5/29.
 */
public class cursorTolist {

    public Map getSqltasklist(Cursor cursor){
        Map map = new HashMap();
        while (cursor.moveToNext()) {
            map.put(cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID)),cursor.getLong(cursor.getColumnIndex(NotesDB.TaskLists.LOCAL_MODIFY_TIME)));
//            map.put(NotesDB.TaskLists.ID, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.ID)));
//            map.put(NotesDB.TaskLists.NAME, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.NAME)));
//            map.put(NotesDB.TaskLists.ACCOUNT, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.ACCOUNT)));
//            map.put(NotesDB.TaskLists.IS_DEFAULT, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.IS_DEFAULT)));
//            map.put(NotesDB.TaskLists.LATEST_SYNC_POINT, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.LATEST_SYNC_POINT)));
//            map.put(NotesDB.TaskLists.SERVER_MODIFY_TIME, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.SERVER_MODIFY_TIME)));
//            map.put(NotesDB.TaskLists.LOCAL_MODIFY_TIME, );
//            map.put(NotesDB.TaskLists.CLEARED, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.CLEARED)));
//            map.put(NotesDB.TaskLists.DELETED, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.DELETED)));
//            map.put(NotesDB.TaskLists.STATUS, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.STATUS)));
//            map.put(NotesDB.TaskLists.ORDER, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.ORDER)));
//            map.put(NotesDB.TaskLists.COLOR, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.COLOR)));
//            map.put(NotesDB.TaskLists.SORT_TYPE, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.SORT_TYPE)));
//            map.put(NotesDB.TaskLists.BACKUP_STAUS, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.BACKUP_STAUS)));
//            map.put(NotesDB.TaskLists.ETAG, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.ETAG)));
//            map.put(NotesDB.TaskLists.GOOGLE_UPDATED, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.GOOGLE_UPDATED)));
//            map.put(NotesDB.TaskLists.OLD_GOOGLE_ID, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.OLD_GOOGLE_ID)));
//            map.put(NotesDB.TaskLists.SHOW_IN_ALL, cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.SHOW_IN_ALL)));
//            Log.d("getSqltasklist", "getSqltasklist: "+cursor.getString(cursor.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID)));
        }
        return map;
    }
}
