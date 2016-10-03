package com.meizu.voiceassistant.commandType;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.SpeechApp;

import java.util.List;

/**
 * Created by weichangtan on 16/9/22.
 */

public class OpenApp extends baseTask {
    @Override
    public void doSomething(String text) {
        open(text);
    }

    private void open(String text){
        String packageName=null;
        Matcher matcher=Pattern.compile("打开(?<app>.+)").matcher(text);
        while (matcher.find()){
            if (!matcher.group().equals("")){
                packageName=matcher.group("app");
            }
        }
        PackageManager packageManager = SpeechApp.getContext().getPackageManager();

        // 获取手机里的应用列表

        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);

        for (int i = 0; i < pInfo.size(); i++)

        {

            PackageInfo p = pInfo.get(i);

            // 获取相关包的<application>中的label信息，也就是-->应用程序的名字

            String label = packageManager.getApplicationLabel(p.applicationInfo).toString();

//            System.out.println(label);

            if (label.equals(packageName)){ //比较label

                String pName = p.packageName; //获取包名

                Intent intent = new Intent();

                //获取intent

                intent =packageManager.getLaunchIntentForPackage(pName);

                SpeechApp.getContext().startActivity(intent);

            }

        }
    }
}
