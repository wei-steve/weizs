package com.meizu.voiceassistant;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.voiceassistant.SQL.NotesDB;

/**
 * Created by weichangtan on 16/9/12.
 */

public class TaskAdapter extends CursorAdapter {
    Context  context=null;
    int viewResId;

    public TaskAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        viewResId=flags;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = null;
        ViewHolder viewHolder = new ViewHolder();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(viewResId, parent, false);

        viewHolder.title= (TextView) view.findViewById(R.id.tv1);
        //v =(TextView)vi.inflate(textViewResourceId,null);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder) view.getTag();
        String title = cursor.getString(cursor.getColumnIndex(NotesDB.TASK.TITLE));
        // Set the name
        viewHolder.title.setText(title);
    }

    public static class ViewHolder {
        public ViewGroup rowBody;
        public TextView title;
    }
}
