package com.meizu.voiceassistant.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.SpeechApp;

import java.util.Calendar;
import java.util.TimeZone;

public class editTodoTask extends Activity{

    private EditText et;
    private TextView tv;
    private Button btn1,btn2;
    private NotesDB notesDB = new NotesDB(SpeechApp.getContext());
    private SQLiteDatabase dbWriter = notesDB.getWritableDatabase();
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_task);
        tv= (TextView) findViewById(R.id.tv);
        et= (EditText) findViewById(R.id.et);
        btn1= (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put(NotesDB.TASK.TITLE,et.getText().toString());
                cv.put(NotesDB.TASK.STATUS,1);
                cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
                dbWriter.update(NotesDB.TASK.TABLE_NAME,cv,NotesDB.TASK.ID+"="+id,null);
                finish();
            }
        });
        btn2= (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        Intent intent=getIntent();
        String content=intent.getStringExtra("text");
        id=intent.getIntExtra("id",0);
        et.setText(content);
    }



}
