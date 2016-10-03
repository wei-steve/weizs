package com.meizu.voiceassistant.util.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.TodoAlarm.ReminderAlarm;
import com.meizu.voiceassistant.util.sqlDate;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SpeechApp;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by weichangtan on 16/5/16.
 */
public class Adapter_Todo extends BaseAdapter{

    private Context context;
    private List<sqlDate> list;
    private LinearLayout layout;
    private SQLiteDatabase dbReader;



    public Adapter_Todo(Context context,List<sqlDate> list,SQLiteDatabase dbReader) {
        this.context = context;
        this.list = list;
        this.dbReader=dbReader;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.todo_listview_cell,null);
        CheckBox checkbox = (CheckBox) layout.findViewById(R.id.checkbox1);
        TextView contenttv = (TextView) layout.findViewById(R.id.tv1);
        ImageButton imageButton= (ImageButton) layout.findViewById(R.id.iv1);
        final String content=list.get(position).getTitle();
        final int id =list.get(position).getId();
        if (list.get(position).getCompleted()==1){
            contenttv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        }

        contenttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                Intent intent =new Intent("androdi.intent.action.editTodoTask");
                intent.putExtra("text",content);
                intent.putExtra("id",list.get(position).getId());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SpeechApp.getContext().startActivity(intent);
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues cv = new ContentValues();
                if (isChecked) {
                Toast.makeText(SpeechApp.getContext()," isChecked="+isChecked+"",Toast.LENGTH_SHORT).show();
                    cv.put(NotesDB.TASK.COMPLETED, 1);
                    cv.put(NotesDB.TASK.STATUS, 1);
                    cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance().getTimeInMillis());
                    cv.put(NotesDB.TASK.COMPLETED_DATE, Calendar.getInstance().getTimeInMillis());
                    dbReader.update(NotesDB.TASK.TABLE_NAME, cv, NotesDB.TASK.ID + "=" + id, null);
                    new ReminderAlarm().cancelAlarm(id);
//                    list.remove(position);


                    notifyDataSetChanged();
                }else {
                    cv.put(NotesDB.TASK.COMPLETED, 0);
                    cv.put(NotesDB.TASK.STATUS, 1);
                    cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
                    cv.put(NotesDB.TASK.COMPLETED_DATE, "");
                    dbReader.update(NotesDB.TASK.TABLE_NAME, cv, NotesDB.TASK.ID + "=" + id, null);
                    Cursor cursor=dbReader.query(NotesDB.TASK.TABLE_NAME,null,NotesDB.TASK.ID+"="+id+" and "+NotesDB.TASK.REMINDER_TIME+"is not null ",null,null,null,null);
                    if (cursor.moveToFirst()){
                        int reminder=cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.REMINDER_TIME));
                        int now=cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.LOCAL_MODIFY_TIME));
                        ContentValues rcv = new ContentValues();
                        rcv.put(NotesDB.REMINDER.TASK_ID,id);
                        rcv.put(NotesDB.REMINDER.REMINDER_TIME,reminder);
                        rcv.put(NotesDB.REMINDER.LAST_MODIFY_DATE,now);
                        dbReader.insert(NotesDB.REMINDER.TABLE_NAME,null,rcv);
                    }
                    cursor.close();
                }
                cv.clear();
            }
        });
        contenttv.setText(content);


        return layout;
    }


}
