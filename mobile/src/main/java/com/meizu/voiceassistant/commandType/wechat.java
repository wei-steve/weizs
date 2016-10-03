package com.meizu.voiceassistant.commandType;

import android.content.Intent;
import android.util.Log;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.util.TTS;
import com.meizu.voiceassistant.SpeechApp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by weichangtan on 16/6/19.
 */
public class wechat extends baseTask{

    @Override
    public void doSomething(String text) {
        talk(text);
    }

    public static void talk(String str) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> id = new HashMap<>();


//        str = "微信谭嘉辉你好";
        id.put("王桂茵", "simple860928");
        id.put("老婆", "simple860928");
        id.put("谭嘉辉", "candandeshenhuo");
        id.put("嘉辉", "candandeshenhuo");
        id.put("谭嘉宝", "wxid_zxkxk0lhvf4x11");
        id.put("嘉宝", "wxid_zxkxk0lhvf4x11");
        id.put("谭继贤", "wxid_7wz2524j9sp312");
        id.put("爸", "wxid_7wz2524j9sp312");
        id.put("李锦弟", "wxid_08ie92vmoobm12");
        id.put("妈", "wxid_08ie92vmoobm12");
        id.put("胡俊杰", "junjie442555");
        id.put("俊杰", "junjie442555");
        id.put("小胡", "junjie442555");


        Iterator<Map.Entry<String, String>> it = id.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey());
            if (it.hasNext()){
                sb.append("|");
            }
        }


        String haha = "微信(?<name>" + sb.toString() + ")(?<content>.+)";
        Log.d("callcontact", "haha: " + haha);
        Matcher wechat = Pattern.compile(haha).matcher(str);
        if (wechat.find()) {
            String mname = wechat.group("name");
            String content = wechat.group("content");
            String wechatid=id.get(mname);

            Log.d("callcontact", "name: " + wechatid);
            if (wechatid!=null){
                SpeechApp.getContext().sendBroadcast(new Intent("tanweichang.com").putExtra("wechatid",wechatid).putExtra("text",content));
                TTS.start("已经发送"+mname+content, TTS.zh);
            }else {
                TTS.start("发送失败"+mname+content, TTS.zh);

            }


        }


    }
}
