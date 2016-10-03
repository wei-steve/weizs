package com.meizu.voiceassistant.activity;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.SQL.NotesDB;
import com.meizu.voiceassistant.SpeechApp;
import com.meizu.voiceassistant.TaskAdapter;
import com.meizu.voiceassistant.util.sqlDate;

import java.util.List;

public class todo extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "todoview";

//    private ListView lv;
    private Intent i;
    private TaskAdapter adapter;
    private List<sqlDate> list;
    private NotesDB notesDB = new NotesDB(SpeechApp.getContext());
    private SQLiteDatabase dbWriter = notesDB.getWritableDatabase();
//    public static Handler handler;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        //设置侧边栏
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, new TaskListFragment() );
        transaction.commit();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, new TaskListFragment(),null)
//                    .commit();
//        Cursor cursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.LIST_ID + "=" + 1 + " and " + NotesDB.TASK.CLEARED + "=0 and " + NotesDB.TASK.DELETED + "=0", null,
//                null, null, null, null);
//        adapter = new TaskAdapter(this, cursor, R.layout.todo_listview_cell);
//        lv.setAdapter(adapter);
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//
//                int flag = msg.arg1;
//                Log.d(TAG, "flag: " + flag);
//
////                selectDB(flag);
//
//                super.handleMessage(msg);
//            }
//        };


    }




//    public void selectDB(int flag) {
//        Log.d(TAG, "selectDB执行了 ");
//        list.clear();
//        Cursor cr = dbWriter.query(NotesDB.TaskLists.TABLE_NAME, null, null, null, null, null, null);
//        if (cr.getCount()!=0) {
//            cr.moveToPosition(flag);
//            int listid = cr.getInt(cr.getColumnIndex(NotesDB.TaskLists.ID));
//            cr.close();
//            Cursor cursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.LIST_ID + "=" + listid + " and " + NotesDB.TASK.CLEARED + "=0 and " + NotesDB.TASK.DELETED + "=0", null,
//                    null, null, null, null);
//
//            Log.d(TAG, "selectDB: " + cursor.getCount());
//            while (cursor.moveToNext()) {
//                list.add(new sqlDate(cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.ID)), cursor.getString(cursor.getColumnIndex(NotesDB.TASK.TITLE)), cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID)),cursor.getInt(cursor.getColumnIndex(NotesDB.TASK.COMPLETED))));
//            }
//            cursor.close();
////            adapter.notifyDataSetChanged();
//        }


//为以后添加子任务功能而备用
//        if (cursor.getCount()==0) {
//
//        }else {
//            int abccc=0;
//            while (cursor.getCount() != list.size()){
//                while (abccc<cursor.getCount()) {
//                    cursor.moveToPosition(abccc);
//                    Log.d(TAG, "cursor.getPosition():" + cursor.getPosition());
////                    Log.d(TAG, "cursor.moveToNext:  cursor.getCount():" + cursor.getCount());
////                    Log.d(TAG, "cursor.moveToNext:  cursor.getString(title)" + cursor.getString(cursor.getColumnIndex(NotesDB.TITLE)));
//                    Log.d(TAG, "list" + list.size());
//                    if (list.size() == 0) {
//                        if (cursor.isNull(cursor.getColumnIndex(NotesDB.LOCAL_PRIOR_SIBLING_ID))) {
//                            list.add(new sqlDate(cursor.getInt(cursor.getColumnIndex(NotesDB.ID)), cursor.getString(cursor.getColumnIndex(NotesDB.TITLE)), null));
//                        }
//                    } else{
//                    if (cursor.getString(cursor.getColumnIndex(NotesDB.LOCAL_PRIOR_SIBLING_ID))!=null&&cursor.getString(cursor.getColumnIndex(NotesDB.LOCAL_PRIOR_SIBLING_ID)).equals(String.valueOf(list.get(list.size()-1).getId()))) {
//                        list.add(new sqlDate(cursor.getInt(cursor.getColumnIndex(NotesDB.ID)), cursor.getString(cursor.getColumnIndex(NotesDB.TITLE)), String.valueOf(list.get(list.size() - 1).getId())));
//                    }
//                    }
//                    abccc++;
//                }
//                abccc=0;
//            }
//        }


//    }

    @Override
    protected void onResume() {
        // TODO: Implement this method
        super.onResume();
//        selectDB(0);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        adapter.notifyDataSetChanged();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, TaskListFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    private void loadTaskListFragment(TaskListFragment taskListFragment) {
//        filter = taskListFragment.filter;
//        ThemeColor themeColor = filter.tint >= 0
//                ? themeCache.getThemeColor(filter.tint)
//                : theme.getThemeColor();
//        themeColor.applyStatusBarColor(drawerLayout);
//        themeColor.applyTaskDescription(this, filter.listingTitle);
//        theme.withColor(themeColor).applyToContext(this);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        fragmentManager.beginTransaction()
//                .replace(isDoublePaneLayout() ? R.id.master_dual : R.id.single_pane, taskListFragment, FRAG_TAG_TASK_LIST)
//                .addToBackStack(FRAG_TAG_TASK_LIST)
//                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

}
