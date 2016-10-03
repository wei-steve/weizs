package com.meizu.voiceassistant.commandType;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.NotificationService.Notify;
import com.meizu.voiceassistant.util.TTS;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by weichangtan on 16/1/11.
 */
public class AskDate extends baseTask{

    @Override
    public void doSomething(String text) {
        saynowDate(text);
    }

    public static void saynowDate(String result){

        Date mdate=new Date();
        Calendar calendar = null;
        String saylocation=null;
        String location=null;



        Matcher datematcher = Pattern.compile("(?<location>.+)?几号(.+)?").matcher(result);
        if (datematcher.find()) {
            location = datematcher.group("location");


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
                        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
                        break;
                    }


                    if (saylocation == null) {
                        saylocation = "当地";
                        calendar = Calendar.getInstance();
                    }
                    break;
                }
            } else {
                saylocation = ".";
                calendar = Calendar.getInstance();
            }//以上设置时区

            int witchday;
            String saywitchday;
            while (true) {
                if (location == null) {
                    witchday = 0;
                    saywitchday = "今天";
                    break;
                }
                if (location.contains("昨天")) {
                    witchday = -1;
                    saywitchday = "昨天";
                    break;
                }
                if (location.contains("明天")) {
                    witchday = 1;
                    saywitchday = "明天";
                    break;
                }
                if (location.contains("大后天")) {
                    witchday = 3;
                    saywitchday = "大后天";
                    break;
                }
                if (location.contains("后天")) {
                    witchday = 2;
                    saywitchday = "后天";
                    break;
                }
                witchday = 0;
                saywitchday = "今天";
                break;
            }

            calendar.setTime(mdate);
            calendar.add(Calendar.DATE, witchday);

            int saymonth;
            saymonth = calendar.get(Calendar.MONTH) + 1;


            System.out.println("DATE" + calendar.get(Calendar.DATE) + "\n" +
                    "AM_PM" + calendar.get(Calendar.AM_PM) + "\n" +
                    "ERA" + calendar.get(Calendar.ERA) + "\n" +
                    "ZONE_OFFSET" + calendar.get(Calendar.ZONE_OFFSET) + "\n" +
                    "DST_OFFSET" + calendar.get(Calendar.DST_OFFSET) + "\n" +
                    "DAY_OF_WEEK" + calendar.get(Calendar.DAY_OF_WEEK) + "\n"
            );

            String date = saylocation + saywitchday + ":" + saymonth + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";

//            ShowResult.show(date, ListData.answer);
            Notify.justtext(date);
            TTS.start(date, TTS.zh);
        }
    }
    public static void abcc(){
       DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,Locale.CHINA);
        System.out.println(df.format(new Date()));

    }


}
