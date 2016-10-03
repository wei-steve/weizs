package com.meizu.voiceassistant.activity;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.TaskAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskListFragment extends Fragment {
    private Context context;
    private TaskAdapter adapter;
    private NotesDB notesDB = new NotesDB(SpeechApp.getContext());
    private SQLiteDatabase dbWriter = notesDB.getWritableDatabase();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";



    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TaskListFragment newInstance(int sectionNumber) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        Cursor cursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.LIST_ID + "=" + 1 + " and " + NotesDB.TASK.CLEARED + "=0 and " + NotesDB.TASK.DELETED + "=0", null,
                null, null, null, null);
        adapter = new TaskAdapter(SpeechApp.getContext(), cursor, R.layout.todo_listview_cell);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ListView listView = getListView();

    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((todo) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
//    }
}
