package com.meizu.voiceassistant.commandType;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.SpeechApp;

import java.util.TimeZone;

/**
 * Created by weichangtan on 16/6/15.
 */
public class callcontact extends baseTask{
    private static String TAG = "callcontact";

    @Override
    public void doSomething(String text) {
        call(text);
    }

    public static void call(String name) {

        Matcher call = Pattern.compile("(拨打|打?电话给?)(?<name>.+)的?电?话?").matcher(name);
        if (call.find())

        {
//        Log.d(TAG, "结果匹配:拨打电话");
            String calllocation = call.group("name");
//                        String callsb = AskTime.saynowTime(calllocation);
//
            String key = calllocation;
            String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = SpeechApp.getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection,
                    ContactsContract.PhoneLookup.DISPLAY_NAME + "=" + "?" + " and (" +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM + " or " +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + " or " +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_HOME + " or " +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_WORK +
                            ")"
                    , new String[]{key},
                    ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM + " desc," +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + " desc," +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_HOME + " desc," +
                            ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_WORK + " desc");

//            Log.d("callcontact", "timezone: "+TimeZone.getDefault().getDisplayName());
            if (cursor == null) {
                Log.v("tag", "getPeople null");
            } else {
                Log.v("tag", "getPeople not null");
                while (cursor.moveToNext()) {
                    Log.d("callcontact", "call: " + cursor.getString(0));
                    Log.d("callcontact", "call: " + cursor.getString(1));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if (TimeZone.getDefault().getID().equals("America/Vancouver")) {
                        Log.v("tag", "America/Vancouver");
                        if (number.matches("(^+?0?1?\\(?778\\)?.+)|(^+?0?1?604.+)")) {
                            Log.d("callcontact", "call: " + cursor.getString(0));
                            Log.d("callcontact", "call: " + cursor.getString(1));
                            dial(number);
                            break;
                        }
                    } else {
                        if (number.matches("(^[1][3,4,5,8][0-9]{9})|(^([0][0-9]{2,3})?-?[0-9]{5,10})")) {
                            Log.d("callcontact", "call: " + cursor.getString(0));
                            Log.d("callcontact", "call: " + cursor.getString(1));
                            dial(number);
                            break;
                        }
                    }
                }
            }

//            Log.d("tag", "getPeople cursor.getCount() = " + cursor.getCount());
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToPosition(i);
//                String uname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                Log.v("TAG", "Name is : " + uname);
//
//                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                Log.v("TAG ", "Number is : " + number);
//
////                            calltasker(number);
//
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                Uri data = Uri.parse("tel:" + number);
//                intent.setData(data);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                SpeechApp.getContext().startActivity(intent);
//                try {
//            Log.d(TAG, "正在sleep1000");
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                AudioManager baudioManager = (AudioManager) SpeechApp.getContext().getSystemService(Context.AUDIO_SERVICE);
//                baudioManager.setMode(AudioManager.ROUTE_SPEAKER);
//                baudioManager.setMicrophoneMute(false);
//                baudioManager.setSpeakerphoneOn(true);
//            }

            cursor.close();
        }
    }

    private static void dial(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SpeechApp.getContext().startActivity(intent);
        try {
            Log.d(TAG, "正在sleep1000");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AudioManager baudioManager = (AudioManager) SpeechApp.getContext().getSystemService(Context.AUDIO_SERVICE);
        baudioManager.setMode(AudioManager.ROUTE_SPEAKER);
        baudioManager.setMicrophoneMute(false);
        baudioManager.setSpeakerphoneOn(true);
    }
}
