package com.meizu.voiceassistant.commandType;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import com.meizu.voiceassistant.SpeechApp;

/**
 * Created by weichangtan on 16/5/17.
 */
public class Light extends baseTask{



    @Override
    public void doSomething(String text) {
        start(text);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void start(String turn) {
        CameraManager manager;
        Camera camera = null;
        Camera.Parameters parameters = null;
        boolean isChecked = false;


        if (turn.matches("开灯")){
            isChecked=true;
        }
        if (turn.matches("关灯")){
            isChecked=false;
        }


        manager = (CameraManager) SpeechApp.getContext().getSystemService(Context.CAMERA_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //android6.0调用的手电筒接口
            try {
                manager.setTorchMode("0", isChecked);
            }catch(CameraAccessException e){
                e.printStackTrace();
            }
        }else{
            //低于6.0系统的手电筒
            if ( isChecked){
                camera = Camera.open();
                parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);// 开启
                camera.setParameters(parameters);
                camera.startPreview();
            }else{
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);// 关闭
                camera.setParameters(parameters);
                camera.stopPreview();
                camera.release();
            }

        }
    }
}
