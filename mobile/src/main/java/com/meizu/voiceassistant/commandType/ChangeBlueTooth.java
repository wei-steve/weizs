package com.meizu.voiceassistant.commandType;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.meizu.voiceassistant.util.TTS;

/**
 * Created by weichangtan on 16/9/21.
 */

public class ChangeBlueTooth extends baseTask {
    private static final String TAG = ChangeBlueTooth.class.getSimpleName();
    private int state;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public ChangeBlueTooth() {
    }

    public ChangeBlueTooth(int state) {
        this.state = state;
    }

    @Override
    public void doSomething(String text) {
        if (state == 1) {
            askstate();
        } else {
            if (text.contains("开")) {
                Log.d(TAG, "doSomething: " + "开飞蓝牙");
                change(true);
            } else if (text.contains("关")) {
                Log.d(TAG, "doSomething: " + "关飞蓝牙");
                change(false);
            }
        }
    }

    private void change(boolean turn) {

        if (bluetoothAdapter == null) {
            //如果设备没有蓝牙
            return;
        } else {

            String Address = bluetoothAdapter.getAddress(); //获取本机蓝牙MAC地址
            String Name = bluetoothAdapter.getName();   //获取本机蓝牙名称
            // 若蓝牙没打开
            if (turn) {
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();  //打开蓝牙，需要BLUETOOTH_ADMIN权限
                    TTS.start("蓝牙已经打开", TTS.zh);
                } else {
                    TTS.start("蓝牙本已经打开", TTS.zh);
                }
            } else if (!turn) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    TTS.start("蓝牙已经关闭", TTS.zh);
                } else {
                    TTS.start("蓝牙本已经关闭", TTS.zh);
                }
                Log.i("getAddress() : ", Address);
                Log.i("getName() : ", Name);
            }
        }
    }

    private void askstate() {
        if (bluetoothAdapter.isEnabled()) {
            TTS.start("蓝牙状态,开启", TTS.zh);
        } else {
            TTS.start("蓝牙状态,关闭", TTS.zh);
        }
    }
}
