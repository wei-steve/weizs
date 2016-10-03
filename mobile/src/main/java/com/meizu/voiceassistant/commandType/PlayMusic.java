package com.meizu.voiceassistant.commandType;

import android.content.*;
import android.os.*;
import android.view.*;

import com.meizu.voiceassistant.*;

/**
 * Created by weichangtan on 16/9/15.
 */

public class PlayMusic extends baseTask {
    private static final String TAG = PlayMusic.class.getSimpleName();
    private static int Keyplay = KeyEvent.KEYCODE_MEDIA_PLAY;
    private static int Keystop = KeyEvent.KEYCODE_MEDIA_STOP;
    private static int KeyplayPause = KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
    private static int Keynext = KeyEvent.KEYCODE_MEDIA_NEXT;
    //PREVIOUS 以前的
    private static int Keyprevious = KeyEvent.KEYCODE_MEDIA_PREVIOUS;
    private static int Keypause = KeyEvent.KEYCODE_MEDIA_PAUSE;




    @Override
    public void doSomething(String text) {
        if (text.matches("^(播放|听)?(音乐|歌曲?)")){
        play();
        } else if (text.matches("下一首|换歌")){
            next();
        }else if (text.matches("^暂停")){
           stop();
        }else if (text.matches("(上|前)一首")){
            previous();
        }
    }

    public static void play() {
        send(Keyplay);
    }

    public static void next() {
        send(Keynext);
    }

    public static void stop() {
        send(Keystop);
    }

    public static void previous() {
        send(Keyprevious);
    }

    private static void send(int musicKey) {

        long eventtime = SystemClock.uptimeMillis();

        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		downIntent.setClassName("com.netease.cloudmusic","com.netease.cloudmusic.receiver.MediaButtonEventReceiver");
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
                KeyEvent.ACTION_DOWN, musicKey, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        SpeechApp.getContext().sendOrderedBroadcast(downIntent, null);

        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		upIntent.setClassName("com.netease.cloudmusic","com.netease.cloudmusic.receiver.MediaButtonEventReceiver");
        KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
                KeyEvent.ACTION_UP, musicKey, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        SpeechApp.getContext().sendOrderedBroadcast(upIntent, null);
    }
}
