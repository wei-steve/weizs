package com.meizu.voiceassistant.commandType;

import android.util.Log;

import com.meizu.voiceassistant.commandType.AirplaneMode.ChangeAirplaneMode;
import com.meizu.voiceassistant.commandType.weather.AskWeather;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weichangtan on 16/9/7.
 */
public class Command {
    private static final String TAG = Command.class.getSimpleName();

    public static boolean start(String result) {
        boolean match=false;
        Map<String, baseTask> map = new HashMap<>();

        //问时间日期
        map.put("(.+)?(((现在)?几点(了|啦|拉|呢)?(.+)?)|时间)", new AskTime());
        map.put("(.+)?星期几?.+", new AskWeek());
        map.put("(.+)?几号(.+)?", new AskDate());
        //行程备忘
        map.put("(.+)?下一个(约会|遇见|旅程|日程|安排|活动|议程)(.+)?", new CalEvent());
        map.put("(.+)?购物清单(.+)?",new CalEvent());
        map.put("(.+)?安排(.+(点(半|整)?|(秒钟?|分钟?|小时|天)后))?(.+)?", new CalEvent());
        map.put("(.+)?(记得|提醒我)(.+(点(半|整)?|(秒钟?|分钟?|小时|天)后))?(.+)?", new AddTasksEvents());
        //倒计时
        map.put("(\\d{1,12}|(一|壹|幺|二|两|三|四|五|六|七|八|九)?千?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?百?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?十?(一|壹|幺|二|两|三|四|五|六|七|八|九|十))(秒钟?|分钟?|小时|天|日)倒计时", new Timer());
        //电话
        map.put("(拨打|打?电话给?)(.+)的?电?话?", new callcontact());
        map.put("微信.+",new wechat());
//        map.put("搜索(.+)", new webSearch());
        map.put("搜索(.+)", new GoogleSearch());
        map.put("这?是?(什么|辨析)?(音乐|歌曲?)", new GoogleSearch(1));
        map.put("翻译", new AskTranslate());
        map.put("睡觉", null);
        map.put("开灯|关灯", new Light());
        //音乐
        map.put("^(播放|听)?音乐", new PlayMusic());
        map.put("下一首|换歌", new PlayMusic());
        map.put("^暂停", new PlayMusic());
        map.put("(上|前)一首", new PlayMusic());
        //开关飞行模式
        map.put("(打?开|关闭?)飞行模?式?",new ChangeAirplaneMode());
		//开关蓝牙
        map.put("(打?开|关闭?)蓝牙",new ChangeBlueTooth());
        map.put("(看看?一?下?蓝牙)|(蓝牙的?状态)|蓝牙已?经?(打?开|关闭?)了?没(打?开|关闭?)?",new ChangeBlueTooth(1));
        //开关wifi
        map.put("(打?开|关闭?)wifi",new ChangeWiFi());
        map.put("(看看?一?下?wifi)|(wifi的?状态)|wifi已?经?(打?开|关闭?)了?没(打?开|关闭?)?",new ChangeWiFi(1));
        //打开app
        map.put("(打?开|启动).+",new OpenApp());
        //打开网址
        //天气
        map.put("天气",new AskWeather());
        //空字符
        map.put("没有结果", null);


        if (result!=null){

            for(Map.Entry<String,baseTask> entry:map.entrySet()){
                if(result.matches(entry.getKey())){
                    Log.d(TAG, "justtext: "+entry.getKey());
                    entry.getValue().doSomething(result);
                    match=true;
                    break;
                }
            }
        }
        return match;
    }






}
