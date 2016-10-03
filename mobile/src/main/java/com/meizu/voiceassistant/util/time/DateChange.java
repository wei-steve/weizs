package com.meizu.voiceassistant.util.time;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.time3.nlp.stringPreHandlingModule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateChange {
    private static final String TAG = DateChange.class.getSimpleName();
    static final String KEY = "reg";
    static final String KEY2 = "test";
    private EditText et1, et2, et3;
    private Button bt1, bt2;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Calendar calendar;
    private Calendar calendar_now;
    private long TimeInMillis;

    public DateChange() {
    }

    public long parse(String time) {

        //简化 数字
        time = stringPreHandlingModule.numberTranslator(time);

        String mills, unittime, year, month, day, week, which_week, day_of_week, ampm, hour, minute;


        Matcher matcher = Pattern.compile(dateRegex.timeregex).matcher(time);
        matcher.find();
        mills = matcher.group("mills");
        unittime = matcher.group("unittime");
        year = matcher.group("year");
        month = matcher.group("month");
        day = matcher.group("day");
        week = matcher.group("week");
        which_week = matcher.group("which_week");
        day_of_week = matcher.group("day_of_week");
        ampm = matcher.group("ampm");
        hour = matcher.group("hour");
        minute = matcher.group("minute");



        calendar = Calendar.getInstance();
        calendar_now = Calendar.getInstance();
//

        if (year == null && month == null && day == null && week == null && hour == null && mills == null) {
            return 0;
        }

        //先判断格式 是 "年月日" 还是 "多少时间后"
        if (mills != null) {
            Log.d(TAG, "识别了x分钟后");
            Log.d(TAG, "mills:"+mills+"unittime:"+unittime);
            double banmillls = 0;
            if (mills.matches("\\d{1,12}")){
                banmillls=Double.valueOf(mills);
            }else if (mills.matches("半")) {
                banmillls = 0.5;
            }

            Matcher mills_matcher = Pattern.compile("\\d{1,12}|半").matcher(mills);
            if (mills_matcher.find()) {

                double ms = 0;
                if (unittime.matches("秒钟?")) {
                    ms = 1;
                }
                if (unittime.matches("分钟?")) {
                    ms = 60;
                }
                if (unittime.matches("个?小时")) {
                    ms = 3600;
                }
                if (unittime.matches("天|日")) {
                    ms = 86400;
                }
                double timer = banmillls * ms * 1000;

                Log.d("ms", "ms:" + ms);
                Log.d("timer", "timer:" + timer);

                calendar.add(Calendar.MILLISECOND, (int) timer);

            }


        } else {
            //年
            if (year == null || year.equals("")) {
                //按现在的年
            } else {
                //年是口述文字时
                if (year.matches("上一?年的上一?") || year.matches("去年的去") || year.matches("前")) {
                    calendar.add(Calendar.YEAR, -2);
                } else if (year.matches("上一?") || year.matches("去")) {
                    calendar.add(Calendar.YEAR, -1);
                } else if (year.matches("这一?") || year.matches("本") || year.matches("该") || year.matches("今")) {
                    calendar.add(Calendar.YEAR, 0);
                } else if (year.matches("下一?") || year.matches("明")) {
                    calendar.add(Calendar.YEAR, 1);
                } else if (year.matches("下一?年的下一?") || year.matches("明年的明") || year.matches("后")) {
                    calendar.add(Calendar.YEAR, 2);
                } else {
                    if (year.matches("\\d{4}")) {
                        calendar.set(Calendar.YEAR, Integer.parseInt(year));
                    } else {
                        calendar.set(Calendar.YEAR, Integer.parseInt(year) + 2000);
                    }
                }
            }

            if (month == null || month.equals("")) {
                if (year != null && !year.equals("")) {
                    calendar.set(Calendar.MONTH, Calendar.JANUARY);
//                强行起始1月
                } else {
//                按现在月份    10月
                }
            } else {
                Log.d("regex", "执行了 今月 明月");
                //月是口述文字时
                if (month.matches("上一?个?月的上一?个?") || month.matches("上上个?") || month.matches("前个?")) {
                    calendar.add(Calendar.MONTH, -2);//calender的month,在add时需要+1
                } else if (month.matches("上一?个?")) {
                    calendar.add(Calendar.MONTH, -1);//calender的month,在add时需要+1
                } else if (month.matches("这一?个?") || month.matches("本")) {
                    calendar.add(Calendar.MONTH, 0);//calender的month,在add时需要+1
                } else if (month.matches("下一?个?")) {
                    calendar.add(Calendar.MONTH, 1);//calender的month,在add时需要+1
                } else if (month.matches("下一?个?月的下一?个?") || month.matches("下下个?") || month.matches("后个?")) {
                    calendar.add(Calendar.MONTH, 2);//calender的month,在add时需要+1
                } else {
                    if (month.matches("\\d{1,2}"))
                        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                }
            }

            if (day == null || day.equals("")) {
                if ((year != null && !year.equals("")) ||( month != null && !month.equals(""))) {
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                } else {
//                    按现在日期 8日
                }
            } else {
                if (week == null || week.equals("")) {
                    if (day.matches("大大前")) {
                        calendar.add(Calendar.DAY_OF_MONTH, -4);
                    } else if (day.matches("大前")) {
                        calendar.add(Calendar.DAY_OF_MONTH, -3);
                    } else if (day.matches("昨天的昨") || day.matches("前")) {
                        calendar.add(Calendar.DAY_OF_MONTH, -2);
                    } else if (day.matches("上一") || day.matches("昨")) {
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                    } else if (day.matches("今") || day.matches("这")) {
                        calendar.add(Calendar.DAY_OF_MONTH, 0);
                    } else if (day.matches("下一") || day.matches("明")) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    } else if (day.matches("明天的明") || day.matches("后")) {
                        calendar.add(Calendar.DAY_OF_MONTH, 2);
                    } else if (day.matches("大后")) {
                        calendar.add(Calendar.DAY_OF_MONTH, 3);
                    } else if (day.matches("大大后")) {
                        calendar.add(Calendar.DAY_OF_MONTH, 4);
                    } else {
                        if (day.matches("\\d{1,2}")) {
                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
                        }
                    }
                }
            }

            //星期
            if (week == null || week.equals("")) {

            } else {
                //哪个星期
                if (which_week == null || which_week.equals("")) {
                    //// TODO: 16/9/20  这里是否有东西漏了
                } else {
                    if (which_week.matches("上")) {
                        calendar.add(Calendar.WEEK_OF_MONTH, -1);
                    } else if (which_week.matches("这")) {
                        calendar.add(Calendar.WEEK_OF_MONTH, 0);
                    } else if (which_week.matches("下")) {
                        calendar.add(Calendar.WEEK_OF_MONTH, 1);
                    }
                }

                //星期几
                if (day_of_week == null || day_of_week.equals("")) {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else {
                    if (day_of_week.matches("[1-7]")){
                        //// TODO: 16/9/20 写到这里
                        calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(day_of_week) + 1);
                    }
                }

            }




            //ampm
            if (ampm == null || ampm.equals("")) {
                if ((year != null && !year.equals("") )||( month != null && !month.equals("") )||( day != null && !day.equals(""))||( week != null && !week.equals(""))) {
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                } else {
//                    按现在pm
                }
            } else {
                if (ampm != null && ampm.matches("凌晨|早上?|早晨|上午|中午")) {
                    calendar.set(Calendar.AM_PM, Calendar.AM);
                } else if (ampm != null && ampm.matches("下午|傍晚|晚上?|夜晚?")) {
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                }
            }

            //时
            if (hour == null || hour.equals("")) {
                if (ampm != null && !ampm.equals("")) {
                    if (ampm.matches("凌晨")) {
                        calendar.set(Calendar.HOUR, 4);
                    } else if (ampm.matches("早上?")) {
                        calendar.set(Calendar.HOUR, 6);
                    } else if (ampm.matches("早晨")) {
                        calendar.set(Calendar.HOUR, 6);
                    } else if (ampm.matches("上午")) {
                        calendar.set(Calendar.HOUR, 6);
                    } else if (ampm.matches("中午")) {
                        calendar.set(Calendar.HOUR, 11);
                    } else if (ampm.matches("下午")) {
                        calendar.set(Calendar.HOUR, 1);
                    } else if (ampm.matches("傍晚")) {
                        calendar.set(Calendar.HOUR, 5);
                    } else if (ampm.matches("晚上?")) {
                        calendar.set(Calendar.HOUR, 6);
                    } else if (ampm.matches("夜晚")) {
                        calendar.set(Calendar.HOUR, 6);
                    }
                } else {
                    calendar.set(Calendar.HOUR, 9);
                }
            } else {
                calendar.set(Calendar.HOUR, Integer.parseInt(hour));
//                1-24点要改变
            }

            //分
            if (minute == null || minute.equals("")) {
                calendar.set(Calendar.MINUTE, 0);
            } else {
                if (minute.matches("半")) {
                    calendar.set(Calendar.MINUTE, 30);
                } else if (minute.matches("整")) {
                    calendar.set(Calendar.MINUTE, 0);
                } else {
                    if (minute.matches("\\d{1,2}")) {
                        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
                    }
                }
            }

//            if (which_week == null && year == null && month == null && calendar.getTimeInMillis() < calendar_now.getTimeInMillis()) {
//                Log.d("流程", "add week of month 1");
//
//                calendar.add(Calendar.WEEK_OF_MONTH, 1);
//            }
//            if (year == null && month == null && day == null && week == null) {
//                Log.d("流程", "year,month,day,week 都是null");
//                if (calendar.getTimeInMillis() < calendar_now.getTimeInMillis()) {
//                    if (ampm != null) {
//                        calendar.add(Calendar.HOUR_OF_DAY, 24);
//                        Log.d("流程", "ampm!=null    加24");
//                    } else {
//                        calendar.add(Calendar.HOUR_OF_DAY, 12);
//                        if (calendar.get(Calendar.HOUR_OF_DAY) < 7) {
//                            calendar.add(Calendar.HOUR_OF_DAY, 12);
//                            Log.d("流程", "ampm!=null    加12");
//
//                        }
//                    }
//                }
//            }


//        if (!ampm.matches("凌晨|早上?|早晨|上午|中午")&&calendar.get(Calendar.HOUR_OF_DAY)<7){
//            Log.d("流程", "ampm＝null&&hour<7    退出循环");
//            calendar.add(Calendar.HOUR_OF_DAY,12);
//        }

            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);


        }


        int saymonth = calendar.get(Calendar.MONTH) + 1;
        int say_day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String say_day_ampm = null;
        if (calendar.get(Calendar.AM_PM) != 1) {
            say_day_ampm = "上午";
        } else {
            say_day_ampm = "下午";
        }
        String text_date = "年=" + year + "         year=" + calendar.get(Calendar.YEAR) + "\n" +
                "月=" + month + "            month=" + saymonth + "\n" +
                "日=" + day + "              day=" + calendar.get(Calendar.DAY_OF_MONTH) + "\n" +
                "星期" + week + "              week=" + say_day_of_week + "\n" +
                "ap=" + ampm + "                  ap=" + say_day_ampm + "\n" +
                "时=" + hour + "                  hour=" + calendar.get(Calendar.HOUR_OF_DAY) + "\n" +
                "分=" + minute + "                minute=" + calendar.get(Calendar.MINUTE) + "\n" +
                "星期＝";

        String text_say_date = calendar.get(Calendar.YEAR) + "年" + saymonth + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" +
                "星期" + say_day_of_week + say_day_ampm + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分";

        long long_say_date = calendar.getTimeInMillis();
        return long_say_date;

    }

    public long getTimeInMillis() {
        return calendar.getTimeInMillis();
    }

    public String getTime(){
        return calendar.getTime().toString();
    }

    public String getTimeZH(){
        return null;
    }
//    public long chn2digit(String chnStr) {
//        //init map
//        java.util.Map<String, Integer> unitMap = new java.util.HashMap<String, Integer>();
//        unitMap.put("十", 10);
//        unitMap.put("百", w100);
//        unitMap.put("千", 1000);
//        unitMap.put("万", 10000);
//        unitMap.put("亿", 100000000);
//
//        java.util.Map<String, Integer> numMap = new java.util.HashMap<String, Integer>();
////        numMap.put("半", (int) 0.5);
//        numMap.put("零", 0);
//        numMap.put("一", 1);
//        numMap.put("壹", 1);
//        numMap.put("幺", 1);
//        numMap.put("二", 2);
//        numMap.put("两", 2);
//        numMap.put("三", 3);
//        numMap.put("四", 4);
//        numMap.put("五", 5);
//        numMap.put("六", 6);
//        numMap.put("七", 7);
//        numMap.put("天", 7);
//        numMap.put("日", 7);
//        numMap.put("八", 8);
//        numMap.put("九", 9);
//
//        //队列
//        List<Long> queue = new ArrayList<Long>();
//        long tempNum = 0;
//        for (int i = 0; i < chnStr.length(); i++) {
//            char bit = chnStr.charAt(i);
//            System.out.print(bit);
//            //数字
//            if (numMap.containsKey(bit + "")) {
//
//                tempNum = tempNum + numMap.get(bit + "");
//
//                //一位数、末位数、亿或万的前一位进队列
//                if (chnStr.length() == 1
//                        | i == chnStr.length() - 1
//                        | (i + 1 < chnStr.length() && (chnStr.charAt(i + 1) == '亿' | chnStr
//                        .charAt(i + 1) == '万'))) {
//                    queue.add(tempNum);
//                }
//            }
//            //单位
//            else if (unitMap.containsKey(bit + "")) {
//
//                //遇到十 转换为一十、临时变量进队列
//                if (bit == '十') {
//                    if (tempNum != 0) {
//                        tempNum = tempNum * unitMap.get(bit + "");
//                    } else {
//                        tempNum = 1 * unitMap.get(bit + "");
//                    }
//                    queue.add(tempNum);
//                    tempNum = 0;
//                }
//
//                //遇到千、百 临时变量进队列
//                if (bit == '千' | bit == '百') {
//                    if (tempNum != 0) {
//                        tempNum = tempNum * unitMap.get(bit + "");
//                    }
//                    queue.add(tempNum);
//                    tempNum = 0;
//                }
//
//                //遇到亿、万 队列中各元素依次累加*单位值、清空队列、新结果值进队列
//                if (bit == '亿' | bit == '万') {
//                    long tempSum = 0;
//                    if (queue.size() != 0) {
//                        for (int j = 0; j < queue.size(); j++) {
//                            tempSum += queue.get(j);
//                        }
//                    } else {
//                        tempSum = 1;
//                    }
//                    tempNum = tempSum * unitMap.get(bit + "");
//                    queue.clear();//清空队列
//                    queue.add(tempNum);//新结果值进队列
//                    tempNum = 0;
//                }
//            }
//        }
//
//        //output
//        System.out.println();
//        long sum = 0;
//        for (Long i : queue) {
//            sum += i;
//        }
//        System.out.println(sum);
//        return sum;
//    }

}

