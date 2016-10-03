package com.meizu.voiceassistant.commandType;


import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.NotificationService.Notify;
import com.meizu.voiceassistant.util.TTS;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by weichangtan on 16/1/9.
 */
public class AskTime extends baseTask {

    @Override
    public void doSomething(String text) {
        saynowTime(text);
    }

    public static void saynowTime(String result) {

        Date mdate = new Date();
        Calendar calendar = null;
        String saylocation = null;
        String say_am_pm = null;
        int say_hour = 0;
        String location = null;

        Matcher timematcher = Pattern.compile("(?<location>.+)?(((现在)?几点(了|啦|拉|呢)?(.+)?)|时间)").matcher(result);
        if (timematcher.find()) {
            location = timematcher.group("location");


            if (location != null) {

                while (true) {
                    while (true) {   //中国
                        if (location.contains("中国")) {
                            saylocation = "中国";
                            break;
                        }
                        if (location.contains("河北")) {
                            saylocation = "河北";
                            break;
                        }
                        if (location.contains("辽宁")) {
                            saylocation = "辽宁";
                            break;
                        }
                        if (location.contains("黑龙江")) {
                            saylocation = "黑龙江";
                            break;
                        }
                        if (location.contains("江苏")) {
                            saylocation = "江苏";
                            break;
                        }
                        if (location.contains("浙江")) {
                            saylocation = "浙江";
                            break;
                        }
                        if (location.contains("安徽")) {
                            saylocation = "安徽";
                            break;
                        }
                        if (location.contains("福建")) {
                            saylocation = "福建";
                            break;
                        }
                        if (location.contains("江西")) {
                            saylocation = "江西";
                            break;
                        }
                        if (location.contains("山东")) {
                            saylocation = "山东";
                            break;
                        }
                        if (location.contains("河南")) {
                            saylocation = "河南";
                            break;
                        }
                        if (location.contains("湖北")) {
                            saylocation = "湖北";
                            break;
                        }
                        if (location.contains("湖南")) {
                            saylocation = "湖南";
                            break;
                        }
                        if (location.contains("广东")) {
                            saylocation = "广东";
                            break;
                        }
                        if (location.contains("海南")) {
                            saylocation = "海南";
                            break;
                        }
                        if (location.contains("四川")) {
                            saylocation = "四川";
                            break;
                        }
                        if (location.contains("贵州")) {
                            saylocation = "贵州";
                            break;
                        }
                        if (location.contains("云南")) {
                            saylocation = "云南";
                            break;
                        }
                        if (location.contains("陕西")) {
                            saylocation = "陕西";
                            break;
                        }
                        if (location.contains("甘肃")) {
                            saylocation = "甘肃";
                            break;
                        }
                        if (location.contains("青海")) {
                            saylocation = "青海";
                            break;
                        }
                        if (location.contains("台湾")) {
                            saylocation = "台湾";
                            break;
                        }
                        if (location.contains("天津")) {
                            saylocation = "天津";
                            break;
                        }
                        if (location.contains("上海")) {
                            saylocation = "上海";
                            break;
                        }
                        if (location.contains("重庆")) {
                            saylocation = "重庆";
                            break;
                        }
                        if (location.contains("广西")) {
                            saylocation = "广西";
                            break;
                        }
                        if (location.contains("内蒙")) {
                            saylocation = "内蒙";
                            break;
                        }
                        if (location.contains("西藏")) {
                            saylocation = "西藏";
                            break;
                        }
                        if (location.contains("宁夏")) {
                            saylocation = "宁夏";
                            break;
                        }
                        if (location.contains("新疆")) {
                            saylocation = "新疆";
                            break;
                        }
                        if (location.contains("香港")) {
                            saylocation = "香港";
                            break;
                        }
                        if (location.contains("澳门")) {
                            saylocation = "澳门";
                            break;
                        }
                        if (location.contains("东莞")) {
                            saylocation = "东莞";
                            break;
                        }
                        if (location.contains("北京")) {
                            saylocation = "北京";
                            break;
                        }
                        System.out.println("if(东莞)");
                        break;
                    }
                    if (saylocation != null) {
                        saylocation = saylocation + "时间";
                        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                        break;
                    }


                    while (true) {       //加拿大
                        if (location.contains("温哥华")) {
                            saylocation = "温哥华";
                            break;
                        }
                        break;
                    }
                    if (saylocation != null) {
                        saylocation = saylocation + "时间";
                        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
                        break;
                    }


                    saylocation = "所在地时间:";
                    calendar = Calendar.getInstance();
                    break;
                }
            } else {
                saylocation = "";
                calendar = Calendar.getInstance();
            }


            calendar.setTime(mdate);

            while (true) {
                if (calendar.get(Calendar.HOUR_OF_DAY) == 0) {
                    say_am_pm = "半夜";
                    say_hour = 12;
                    break;
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) > 0 && calendar.get(Calendar.HOUR_OF_DAY) < 6) {
                    say_am_pm = "凌晨";
                    say_hour = calendar.get(Calendar.HOUR);
                    break;
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) > 5 && calendar.get(Calendar.HOUR_OF_DAY) < 12) {
                    say_am_pm = "上午";
                    say_hour = calendar.get(Calendar.HOUR);
                    break;
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) == 12) {
                    say_am_pm = "中午";
                    say_hour = 12;
                    break;
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) > 12 && calendar.get(Calendar.HOUR_OF_DAY) < 18) {
                    say_am_pm = "下午";
                    say_hour = calendar.get(Calendar.HOUR);
                    break;
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) > 17 && calendar.get(Calendar.HOUR_OF_DAY) < 20) {
                    say_am_pm = "傍晚";
                    say_hour = calendar.get(Calendar.HOUR);
                    break;
                }
                if (calendar.get(Calendar.HOUR_OF_DAY) > 19) {
                    say_am_pm = "晚上";
                    say_hour = calendar.get(Calendar.HOUR);
                    break;
                }
                break;
            }

            String date = saylocation + say_am_pm + say_hour + "点" + calendar.get(Calendar.MINUTE) + "分";

//            Bundle bundle = new Bundle();
//            bundle.putString("time",date);
//            Message msg = new Message();
//            msg.what = 4;
//            msg.setData(bundle);
//            RecognizeIntentService.handler.sendMessage(msg);

//            ((SpeechApp) SpeechApp.getContext().getApplicationContext()).setVoiceType(Type_asktime);

//            ShowResult.show(date, ListData.answer);
            Notify.justtext(date);
            TTS.start(date, TTS.zh);
        }
    }


}
