package com.meizu.voiceassistant.activity;


import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.api.services.tasks.TasksScopes;
import com.meizu.voiceassistant.R;
import com.meizu.voiceassistant.commandType.wechat;


public class googleacount extends Activity {
    private static String TAG = googleacount.class.getSimpleName();
    //    private static final String TAG = "gpm-login-activity";
    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private SignInButton sign_in_button;
    private TextView mOutputText;

    private static final String PREF_ACCOUNT_NAME = "accountName";


    private static final String scope = "oauth2:" + TasksScopes.TASKS;
    private TODOAsync user;
    String mEmail;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_acount);
        new wechat().talk("");
//        Log.i(TAG, "onCreate");
        sign_in_button = (SignInButton) findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsername();
            }
        });

        mOutputText = (TextView) findViewById(R.id.tv);
        mOutputText.setText("hello");
    }

    private void getUsername() {
        Log.d(TAG, "getUsername被调用");
        if (mEmail == null) {
            pickUserAccount();
        } else {
            if (isDeviceOnline()) {
                user = new TODOAsync(googleacount.this, mEmail, scope);
                user.execute();
//                user = new GetUsernameTask(googleacount.this, mEmail, scope);
//                user.execute();
            } else {
                Toast.makeText(this, "not online", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pickUserAccount() {
        Log.d(TAG, "pickUserAccount被调用");
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult被调用");
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                getUsername();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Please pick an account", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void handleException(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    int statusCode = ((GooglePlayServicesAvailabilityException) e).getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode, googleacount.this, REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    Intent intent = ((UserRecoverableAuthException) e).getIntent();
                    startActivityForResult(intent, REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }
            }
        });
    }

    boolean isDeviceOnline() {
//        Log.i(TAG, "isDeviceOnline");
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        return isMobileConn || isWifiConn;
    }

//    private class GetUsernameTask extends AsyncTask<Void, Void, Void> {
//        private static final String TAG = "getUsernameTask";
//        private static final String PREF_ACCOUNT_NAME = "accountName";
//        //    private static final String TAG = "gpm-getUsername-task";
//        private ProgressDialog dialog;
//        googleacount mActivity;
//        private Context context;
//        String mScope;
//        String mEmail;
//        String token;
//
//
//        private com.google.api.services.tasks.Tasks mService = null;
//        private Exception mLastError = null;
//
//        GetUsernameTask(googleacount activity, String name, String scope) {
////        Log.i(TAG, "getUsernameTaskConstructor");
//            this.mActivity = activity;
//            context = activity;
//            this.mScope = scope;
//            this.mEmail = name;
////        Log.i(TAG, mActivity+mScope+mEmail);
//
//        }
//
//        protected void onPreExecute() {
////        Log.i(TAG, "onPreExecute");
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("同步中...");
//            dialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
////        Log.i(TAG, "inBackground");
//            NotesDB notesDB = new NotesDB(SpeechApp.getContext());
//            SQLiteDatabase dbWriter = notesDB.getWritableDatabase();
//            try {
//                token = fetchToken();
//            } catch (IOException e) {
//                Log.d("", "exception", e);
//            }
//
//
//            if (token != null) {
//
//
//                GoogleCredential cred = new GoogleCredential().setAccessToken(token);
//
//                HttpTransport transport = AndroidHttp.newCompatibleTransport();
//                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//                mService = new com.google.api.services.tasks.Tasks.Builder(
//                        transport, jsonFactory, cred)
//                        .setApplicationName("Google Tasks API Android Quickstart")
//                        .build();
//
//                TaskLists result;
//                try {
//                    result = getTaskListsFromApi();
//                } catch (Exception e) {
//                    mLastError = e;
//                    Log.d(TAG, "出错了: " + mLastError);
//                    new TTS().justtext("0同步出错了", TTS.zh);
//
//                    cancel(true);
//                    return null;
//                }
//                if (result == null || result.size() == 0) {
//                    Log.d(TAG, "没有task列表");
//                } else {
//                    Log.d(TAG, "获取到的tasklist个数: " + result.getItems().size());
//                    List<TaskList> tasklists = result.getItems();
//
//                    Cursor getsqlcursor = dbWriter.query(NotesDB.TaskLists.TABLE_NAME, null, NotesDB.TaskLists.STATUS + "=2 or " + NotesDB.TaskLists.STATUS + "=1", null, null, null, null);
//                    Log.d(TAG, "getsqlcursor: " + getsqlcursor.getCount());
//                    Map sqltasklist = new cursorTolist().getSqltasklist(getsqlcursor);
//                    getsqlcursor.close();
//                    if (tasklists != null) {
//                        for (int i = 0; i < tasklists.size(); i++) {
//                            TaskList tasklist = tasklists.get(i);
//                            if (sqltasklist.containsKey(tasklist.getId())) {   //如果数据库tasklist已有数据，匹配gtask api
//
//                                //云 大于 地，更新本地数据
//                                if (tasklist.getUpdated().getValue() > (long) sqltasklist.get(tasklist.getId())) {
//                                    ContentValues cv = new ContentValues();
//                                    cv.put(NotesDB.TaskLists.NAME, tasklist.getTitle());
//                                    cv.put(NotesDB.TaskLists.SERVER_MODIFY_TIME, 0);
////                                        cv.put(NotesDB.TaskLists.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                                    cv.put(NotesDB.TaskLists.STATUS, 2);
//                                    cv.put(NotesDB.TaskLists.GOOGLE_UPDATED, tasklist.getUpdated().getValue());
//
//                                    dbWriter.update(NotesDB.TaskLists.TABLE_NAME, cv, NotesDB.TaskLists.GOOGLE_ID + "=?", new String[]{tasklist.getId()});
//                                    cv.clear();
//                                    sqltasklist.remove(tasklist.getId());
//                                }
//                            } else {    //云的数据 ，本地没有
//
//                                ContentValues listcv = new ContentValues();
//                                listcv.put(NotesDB.TaskLists.GOOGLE_ID, tasklist.getId());
//                                listcv.put(NotesDB.TaskLists.NAME, tasklist.getTitle());
//                                listcv.put(NotesDB.TaskLists.ACCOUNT, mEmail);
//                                listcv.put(NotesDB.TaskLists.SERVER_MODIFY_TIME, 0);
//                                listcv.put(NotesDB.TaskLists.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                                listcv.put(NotesDB.TaskLists.STATUS, 2);
//                                listcv.put(NotesDB.TaskLists.GOOGLE_UPDATED, tasklist.getUpdated().getValue());
////                                listcv.put(NotesDB.TaskLists.ORDER,);
////                                listcv.put(NotesDB.TaskLists.OLD_GOOGLE_ID,);
//                                if (i == 0) {
//                                    listcv.put(NotesDB.TaskLists.IS_DEFAULT, 1);
//                                }
//                                dbWriter.insert(NotesDB.TaskLists.TABLE_NAME, null, listcv);
//
//                            }
//                        }//for 结束
//
//
//                        Log.d(TAG, "看看更新完后，sqltasklist还有多少：" + sqltasklist.size());
//
////                            if (sqltasklist.size() > 0) {
////                                for (Object obj : sqltasklist.keySet()) {
////                                    String id = (String) obj;
////                                    if (!id.equals("") || id != null) {
////                                        dbWriter.delete(NotesDB.TaskLists.TABLE_NAME, NotesDB.TaskLists.GOOGLE_ID + "=?", new String[]{id});
////                                        sqltasklist.remove(id);
////                                    }
////
////                                }
////                            }
//                        Log.d(TAG, "看看更新完后，sqltasklist还有多少：" + sqltasklist.size());
//
//                    }
//
//
//                }
//
//                Cursor sqltoApi = dbWriter.query(NotesDB.TaskLists.TABLE_NAME, null, NotesDB.TaskLists.STATUS + "=1", null, null, null, null);
//                while (sqltoApi.moveToNext()) {
//                    //更新到云
//                    if (sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID)).equals("") || sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID)) == null) {
//                        TaskList list = new TaskList();
//                        list.setTitle(sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.NAME)));
//
//
//                        TaskList mlist;
//
//                        try {
//                            mlist = insertTaskListsFromApi(list);
//                        } catch (Exception e) {
//                            mLastError = e;
//                            Log.d(TAG, "出错了: " + mLastError);
//                            new TTS().justtext("1同步出错了", TTS.zh);
//
//                            cancel(true);
//                            return null;
//                        }
//                        ContentValues cv = new ContentValues();
//                        cv.put(NotesDB.TaskLists.NAME, mlist.getTitle());
//                        cv.put(NotesDB.TaskLists.GOOGLE_ID, mlist.getId());
//                        cv.put(NotesDB.TaskLists.SERVER_MODIFY_TIME, 0);
//                        cv.put(NotesDB.TaskLists.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                        cv.put(NotesDB.TaskLists.STATUS, 2);
//                        cv.put(NotesDB.TaskLists.GOOGLE_UPDATED, mlist.getUpdated().getValue());
////                                listcv.put(NotesDB.TaskLists.ORDER,);
////                                listcv.put(NotesDB.TaskLists.OLD_GOOGLE_ID,);
//
//
//                        dbWriter.update(NotesDB.TaskLists.TABLE_NAME, cv, NotesDB.TaskLists.ID + "=?", new String[]{sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.ID))});
//                        cv.clear();
//                    } else {
//                        TaskList list = new TaskList();
//                        list.setTitle(sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.NAME)));
//
//
//                        TaskList mlist;
//
//                        try {
//                            mlist = updateTaskListsFromApi(sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID)), list);
//                        } catch (Exception e) {
//                            mLastError = e;
//                            Log.d(TAG, "出错了: " + mLastError);
//                            new TTS().justtext("2同步出错了", TTS.zh);
//
//                            cancel(true);
//                            return null;
//                        }
//                        ContentValues cv = new ContentValues();
//                        cv.put(NotesDB.TaskLists.NAME, mlist.getTitle());
//                        cv.put(NotesDB.TaskLists.SERVER_MODIFY_TIME, 0);
//                        cv.put(NotesDB.TaskLists.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                        cv.put(NotesDB.TaskLists.STATUS, 2);
//                        cv.put(NotesDB.TaskLists.GOOGLE_UPDATED, mlist.getUpdated().getValue());
////                                listcv.put(NotesDB.TaskLists.ORDER,);
////                                listcv.put(NotesDB.TaskLists.OLD_GOOGLE_ID,);
//
//
//                        dbWriter.update(NotesDB.TaskLists.TABLE_NAME, cv, NotesDB.TaskLists.ID + "=?", new String[]{sqltoApi.getString(sqltoApi.getColumnIndex(NotesDB.TaskLists.ID))});
//                        cv.clear();
//                    }
//
//                }
//                sqltoApi.close();
//                //TODO 还有个list的本地取消，要更新给云端。没写   DELETE
//
//
//                //关于Task部分
//                Log.d(TAG, "进入task云端下载部分");
//                //query出所有task数据
//                Cursor acursor = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.STATUS + "=2 or " + NotesDB.TASK.STATUS + "=1", null, null, null, null);
//                Map mmap = new HashMap();
//                while (acursor.moveToNext()) {
//                    mmap.put(acursor.getString(acursor.getColumnIndex(NotesDB.TASK.GOOGLE_ID)), acursor.getLong(acursor.getColumnIndex(NotesDB.TASK.LOCAL_MODIFY_TIME)));
//                }
//                acursor.close();
//
//                Log.d(TAG, "看看更新完后，task开始有多少：" + mmap.size());
//                //query出所有list的googleId
//                Cursor bcursor = dbWriter.query(NotesDB.TaskLists.TABLE_NAME, null, NotesDB.TaskLists.DELETED + "=0 and (" + NotesDB.TaskLists.STATUS + "=2 or " + NotesDB.TaskLists.STATUS + "=1)", null, null, null, null, null);
//                while (bcursor.moveToNext()) {
//
//
//                    Tasks mresult;
//                    try {
//                        mresult = getTasksFromApi(bcursor.getString(bcursor.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID)));
//                    } catch (Exception e) {
//                        mLastError = e;
//                        Log.d(TAG, "出错了: " + mLastError);
//                        new TTS().justtext("3同步出错了", TTS.zh);
//
//                        cancel(true);
//                        return null;
//                    }
//
//
//                    List<Task> mtasks = mresult.getItems();
//                    if (mtasks != null) {
//
//
//                        for (int b = 0; b < mtasks.size(); b++) {
//                            Task mtask = mtasks.get(b);
//                            if (mmap.containsKey(mtask.getId())) {
//                                if (mtask.containsKey("due")) {
//                                    Log.d(TAG, "mtask.getDue().getTimeZoneShift(): " + mtask.getDue().getTimeZoneShift());
//                                }
//                                if (mtask.getUpdated().getValue() > (long) mmap.get(mtask.getId())) {
//                                    ContentValues cv = new ContentValues();
//                                    cv.put(NotesDB.TASK.TITLE, mtask.getTitle());
//                                    cv.put(NotesDB.TASK.NOTES, mtask.getNotes());
//                                    cv.put(NotesDB.TASK.POSITION, mtask.getPosition());
//                                    cv.put(NotesDB.TASK.ETAG, mtask.getEtag());
//                                    cv.put(NotesDB.TASK.SERVER_MODIFY_TIME, mtask.getUpdated().getValue());
//                                    cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                                    cv.put(NotesDB.TASK.STATUS, 2);
//                                    if (mtask.getStatus().equals("completed")) {
//                                        cv.put(NotesDB.TASK.COMPLETED, 1);
//                                    } else {
//                                        cv.put(NotesDB.TASK.COMPLETED, 0);
//                                    }
//                                    if (mtask.containsKey("completed")) {
//                                        cv.put(NotesDB.TASK.COMPLETED_DATE, mtask.getCompleted().getValue());
//                                    } else {
//                                        cv.put(NotesDB.TASK.COMPLETED_DATE, "");
//                                    }
//                                    if (mtask.containsKey("parent")) {
//                                        cv.put(NotesDB.TASK.LOCAL_PARENT_ID, mtask.getParent());
//                                    }
////                                    cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, siblingID);
//                                    if (mtask.containsKey("due")) {
//                                        cv.put(NotesDB.TASK.REMINDER_TIME, mtask.getDue().getValue());
//                                    }
//                                    dbWriter.update(NotesDB.TASK.TABLE_NAME, cv, NotesDB.TASK.GOOGLE_ID + "=?", new String[]{mtask.getId()});
//                                    cv.clear();
//                                    mmap.remove(mtask.getId());
//                                }
//                            } else {
//                                ContentValues cv = new ContentValues();
//                                cv.put(NotesDB.TASK.GOOGLE_ID, mtask.getId());
//                                cv.put(NotesDB.TASK.LIST_ID, bcursor.getString(bcursor.getColumnIndex(NotesDB.TaskLists.ID)));
//                                cv.put(NotesDB.TASK.TITLE, mtask.getTitle());
//                                cv.put(NotesDB.TASK.NOTES, mtask.getNotes());
//                                cv.put(NotesDB.TASK.TIMEZONE, TimeZone.getDefault().getID());
//                                cv.put(NotesDB.TASK.POSITION, mtask.getPosition());
//                                cv.put(NotesDB.TASK.ETAG, mtask.getEtag());
//                                cv.put(NotesDB.TASK.STATUS, 2);
//                                cv.put(NotesDB.TASK.SERVER_MODIFY_TIME, mtask.getUpdated().getValue());
//                                cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                                if (mtask.getStatus().equals("completed")) {
//                                    cv.put(NotesDB.TASK.COMPLETED, 1);
//                                } else {
//                                    cv.put(NotesDB.TASK.COMPLETED, 0);
//                                }
//                                if (mtask.containsKey("completed")) {
//                                    cv.put(NotesDB.TASK.COMPLETED_DATE, mtask.getCompleted().getValue());
//                                }
//                                if (mtask.containsKey("parent")) {
//                                    cv.put(NotesDB.TASK.LOCAL_PARENT_ID, mtask.getParent());
//                                }
////                                    cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, siblingID);
//                                if (mtask.containsKey("due")) {
//                                    cv.put(NotesDB.TASK.REMINDER_TIME, mtask.getDue().getValue());
//                                }
//                                dbWriter.insert(NotesDB.TASK.TABLE_NAME, null, cv);
//                                cv.clear();
//                            }
//
//                        }//for 结束
//                    }
//                    Log.d(TAG, "看看更新完后，task还有多少：" + mmap.size());
//
//
//                }//while结束
//
//                bcursor.close();
//
//                /**
//                 * 进入task本地上传部分
//                 */
//                Log.d(TAG, "进入task本地上传部分");
//
//
//                Cursor sqltasktoApi = dbWriter.query(NotesDB.TASK.TABLE_NAME, null, NotesDB.TASK.STATUS + "=1", null, null, null, null);
//                Log.d(TAG, "sqltasktoApi 有  " + sqltasktoApi.getCount());
//                while (sqltasktoApi.moveToNext()) {
//                    Task mtask = new Task();
//
//                    mtask.setTitle(sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.TITLE)));
//                    mtask.setNotes(sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.NOTES)));
//                    if (sqltasktoApi.getInt(sqltasktoApi.getColumnIndex(NotesDB.TASK.COMPLETED)) == 1) {
//                        mtask.setStatus("completed");
//                        mtask.setCompleted(new DateTime(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.COMPLETED_DATE))));
//                    } else {
//                        mtask.setStatus("needsAction");
//                        mtask.setCompleted(null);
//                    }
//                    long tasktime = 0;
//                    tasktime = sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.TASK_DATE));
//                    if (tasktime != 0) {
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTimeInMillis(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.TASK_DATE)));
//                        cal.setTimeZone(TimeZone.getTimeZone("0"));
//                        DateTime datetime = new DateTime(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.TASK_DATE)));
//                        mtask.setDue(datetime);
//                        Log.d(TAG, "datetime时区: " + datetime.getTimeZoneShift());
//
//
////                        mtask.setDue(new DateTime(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.REMINDER_TIME))));
//                    }
//
//
//                    Cursor lcursor = dbWriter.query(NotesDB.TaskLists.TABLE_NAME, null, NotesDB.TaskLists.ID + "=" + sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.LIST_ID)), null, null, null, null, null);
//                    String listgoogleid = null;
//                    while (lcursor.moveToNext()) {
//                        listgoogleid = lcursor.getString(lcursor.getColumnIndex(NotesDB.TaskLists.GOOGLE_ID));
//                    }
//                    lcursor.close();
//
//                    Log.d(TAG, "进入task本地上传部分1");
//
//                    if (sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.GOOGLE_ID)) == null) {
//
//                        Task newtask;
//                        try {
//                            newtask = insertTaskFromApi(listgoogleid, mtask);
//                        } catch (Exception e) {
//                            mLastError = e;
//                            Log.d(TAG, "出错了: " + mLastError);
//                            new TTS().justtext("4同步出错了", TTS.zh);
//
//                            cancel(true);
//                            return null;
//                        }
//
//
//                        ContentValues cv = new ContentValues();
//                        cv.put(NotesDB.TASK.TITLE, newtask.getTitle());
//                        cv.put(NotesDB.TASK.NOTES, newtask.getNotes());
//                        cv.put(NotesDB.TASK.POSITION, newtask.getPosition());
//                        cv.put(NotesDB.TASK.ETAG, newtask.getEtag());
//                        cv.put(NotesDB.TASK.SERVER_MODIFY_TIME, newtask.getUpdated().getValue());
//                        cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                        cv.put(NotesDB.TASK.STATUS, 2);
//                        if (mtask.getStatus().equals("completed")) {
//                            cv.put(NotesDB.TASK.COMPLETED, 1);
//                        } else {
//                            cv.put(NotesDB.TASK.COMPLETED, 0);
//                        }
//                        if (mtask.containsKey("completed")) {
//                            cv.put(NotesDB.TASK.COMPLETED_DATE, newtask.getCompleted().getValue());
//                        } else {
//                            cv.put(NotesDB.TASK.COMPLETED_DATE, "");
//                        }
//                        if (mtask.containsKey("parent")) {
//                            cv.put(NotesDB.TASK.LOCAL_PARENT_ID, newtask.getParent());
//                        }
////                                    cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, siblingID);
//                        if (mtask.containsKey("due")) {
//                            cv.put(NotesDB.TASK.REMINDER_TIME, newtask.getDue().getValue());
//                        }
//                        Log.d(TAG, " 20 ");
//                        dbWriter.update(NotesDB.TASK.TABLE_NAME, cv, NotesDB.TASK.TITLE + "=?", new String[]{newtask.getTitle()});
//                        Log.d(TAG, " 21 ");
//
//                    } else {
//                        /**
//                         * 进入task本地上传部分2
//                         *
//                         */
//                        Log.d(TAG, "进入task本地上传部分2");
//
//                        Task task;
//                        try {
//                            task = getTaskFromApi(listgoogleid, sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.GOOGLE_ID)));
//                        } catch (Exception e) {
//                            mLastError = e;
//                            Log.d(TAG, "出错了: " + mLastError);
//                            new TTS().justtext("5同步出错了", TTS.zh);
//
//                            cancel(true);
//                            return null;
//                        }
//                        task.setTitle(sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.TITLE)));
//                        task.setNotes(sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.NOTES)));
//                        if (sqltasktoApi.getInt(sqltasktoApi.getColumnIndex(NotesDB.TASK.COMPLETED)) == 1) {
//                            task.setStatus("completed");
//                            task.setCompleted(new DateTime(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.COMPLETED_DATE))));
//                        } else {
//                            task.setStatus("needsAction");
//                            task.setCompleted(null);
//                        }
//                        tasktime = sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.TASK_DATE));
//                        if (tasktime != 0) {
//                            Calendar cal = Calendar.getInstance();
//                            cal.setTimeInMillis(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.TASK_DATE)));
//                            cal.setTimeZone(TimeZone.getTimeZone("0"));
//                            DateTime datetime = new DateTime(sqltasktoApi.getLong(sqltasktoApi.getColumnIndex(NotesDB.TASK.TASK_DATE)));
//                            task.setDue(datetime);
//                            Log.d(TAG, "datetime时区: " + datetime.getTimeZoneShift());
//
//                        }
//
//
//                        Task newtask;
//
//                        try {
//                            newtask = updateTaskFromApi(listgoogleid, sqltasktoApi.getString(sqltasktoApi.getColumnIndex(NotesDB.TASK.GOOGLE_ID)), task);
//
//                        } catch (Exception e) {
//                            mLastError = e;
//                            Log.d(TAG, "出错了: " + mLastError);
//                            new TTS().justtext("6同步出错了", TTS.zh);
//
//                            cancel(true);
//                            return null;
//                        }
//
//
//                        ContentValues cv = new ContentValues();
//                        cv.put(NotesDB.TASK.TITLE, newtask.getTitle());
//                        cv.put(NotesDB.TASK.NOTES, newtask.getNotes());
//                        cv.put(NotesDB.TASK.POSITION, newtask.getPosition());
//                        cv.put(NotesDB.TASK.ETAG, newtask.getEtag());
//                        cv.put(NotesDB.TASK.SERVER_MODIFY_TIME, newtask.getUpdated().getValue());
//                        cv.put(NotesDB.TASK.LOCAL_MODIFY_TIME, Calendar.getInstance(TimeZone.getTimeZone("0")).getTimeInMillis());
//                        cv.put(NotesDB.TASK.STATUS, 2);
//                        if (mtask.getStatus().equals("completed")) {
//                            cv.put(NotesDB.TASK.COMPLETED, 1);
//                        } else {
//                            cv.put(NotesDB.TASK.COMPLETED, 0);
//                        }
//                        if (mtask.containsKey("completed")) {
//                            cv.put(NotesDB.TASK.COMPLETED_DATE, newtask.getCompleted().getValue());
//                        } else {
//                            cv.put(NotesDB.TASK.COMPLETED_DATE, "");
//                        }
//                        if (mtask.containsKey("parent")) {
//                            cv.put(NotesDB.TASK.LOCAL_PARENT_ID, newtask.getParent());
//                        }
////                                    cv.put(NotesDB.TASK.LOCAL_PRIOR_SIBLING_ID, siblingID);
//                        if (mtask.containsKey("due")) {
//                            cv.put(NotesDB.TASK.REMINDER_TIME, newtask.getDue().getValue());
//                        }
//                        dbWriter.update(NotesDB.TASK.TABLE_NAME, cv, NotesDB.TASK.GOOGLE_ID + "=?", new String[]{newtask.getId()});
//                    }
//                }
//
//                sqltasktoApi.close();
//
//                Log.d(TAG, "完成 ");
//
//
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//            return null;
//        }
//
//        private TaskLists getTaskListsFromApi() throws IOException {
//            TaskLists result = mService.tasklists().list()
//                    .setMaxResults(Long.valueOf(w100))
//                    .execute();
////
//
//            return result;
//        }
//
//        private TaskList insertTaskListsFromApi(TaskList taskList) throws IOException {
//            TaskList result = mService.tasklists().insert(taskList)
//                    .execute();
////
//            return result;
//        }
//
//        private TaskList updateTaskListsFromApi(String list, TaskList taskList) throws IOException {
//            TaskList result = mService.tasklists().update(list, taskList)
//                    .execute();
////
//            return result;
//        }
//
//        private Tasks getTasksFromApi(String list) throws IOException {
//            Tasks result = mService.tasks().list(list)
//                    .execute();
////
//            return result;
//        }
//
//        private Task getTaskFromApi(String list, String task) throws IOException {
//            Task result = mService.tasks().get(list, task)
//                    .execute();
////
//            return result;
//        }
//
//        private Task insertTaskFromApi(String list, Task task) throws IOException {
//            Task result = mService.tasks().insert(list, task).execute();
//            return result;
//        }
//
//        private Task updateTaskFromApi(String listid, String taskid, Task content) throws IOException {
//            Task result = mService.tasks().update(listid, taskid, content).execute();
//            return result;
//        }
//
//
//        protected String fetchToken() throws IOException {
////        Log.i(TAG, "fetchToken");
//            try {
//                String tokenn = GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
////            Log.i(TAG, tokenn);
//                return tokenn;
//            } catch (UserRecoverableAuthException userRecoverableException) {
////            Log.i(TAG, "userRecoverableException");
//                mActivity.handleException(userRecoverableException);
//            } catch (GoogleAuthException fatalException) {
////            Log.i(TAG, "fatalException");
//                fatalException.printStackTrace();
//            }
//            return null;
//        }
//    }

}
