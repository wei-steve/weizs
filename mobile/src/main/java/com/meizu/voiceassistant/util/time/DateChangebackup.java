package com.meizu.voiceassistant.util.time;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by weichangtan on 16/9/17.
 */

public class DateChangebackup {
    private static final String TAG=DateChangebackup.class.getSimpleName();

    public static final String reg = "((?<mills>\\d{1,12}|(一|壹|幺|二|两|三|四|五|六|七|八|九)?千?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?百?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?十?(半|一|壹|幺|二|两|三|四|五|六|七|八|九|十))(?<unittime>秒钟?|分钟?|个?小时|天|日)后)" +"|("+
            "((?<year>(二?(零|壹|幺|一|二|三|四|五|六|七|八|九)?(零|壹|幺|一|二|三|四|五|六|七|八|九)?(零|壹|幺|一|二|两|三|四|五|六|七|八|九)|\\d{2,4}|(上一?年的上一?|去年的去|前|上一?|去|这一?|本|该|今|下一?|明|下一?年的下一?|明年的明|后)))(年|-))?" +
            "((?<month>((上一?个?月的上一?个?|上上个?|前个?|上一?个?|这一?个?|本|下一?个?|下一?个?月的下一?个?|下下个?月|后个?月)|\\d{1,2}|十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十)))(月|-))?" +
            "((?<day>(((大大前)|(大前)|(昨天的昨)|(上一)|昨|今|这|(下一)|明|(明天的明)|后|(大后)|(大大后))|\\d{1,2}|((二|三)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))))(号|日|天))?" +
            "((?<week>((?<which_week>第一|第二|第三|第四|上一?个?|这一?个?|下一?个?)?(星期|周)(?<day_of_week>一|二|三|四|五|六|七|天|日|\\d)?)))?" +
            "(?<ampm>((凌晨)|(早上?)|(早晨)|(上午)|(中午)|(下午)|(傍晚)|(晚上?)|(夜晚?)))?" +
            "((?<hour>(\\d{1,2}|(二?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))))(点|：|:))?" +
            "(?<minute>(半|整|(\\d{1,2}|(二|三|四|五)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))分?))?)";
    static final String KEY = "reg";
    static final String KEY2 = "test";
    EditText et1, et2, et3;
    Button bt1, bt2;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Calendar calendar;
    Calendar calendar_now;


    public long chang(String time) {

        String regex = "((?<mills>\\d{1,12}|(一|壹|幺|二|两|三|四|五|六|七|八|九)?千?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?百?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?十?(半|一|壹|幺|二|两|三|四|五|六|七|八|九|十))(?<unittime>秒钟?|分钟?|个?小时|天|日)后)" +"|("+
                "((?<year>(二?(零|壹|幺|一|二|三|四|五|六|七|八|九)?(零|壹|幺|一|二|三|四|五|六|七|八|九)?(零|壹|幺|一|二|两|三|四|五|六|七|八|九)|\\d{2,4}|(上一?年的上一?|去年的去|前|上一?|去|这一?|本|该|今|下一?|明|下一?年的下一?|明年的明|后)))(年|-))?" +
                "((?<month>((上一?个?月的上一?个?|上上个?|前个?|上一?个?|这一?个?|本|下一?个?|下一?个?月的下一?个?|下下个?月|后个?月)|\\d{1,2}|十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十)))(月|-))?" +
                "((?<day>(((大大前)|(大前)|(昨天的昨)|(上一)|昨|今|这|(下一)|明|(明天的明)|后|(大后)|(大大后))|\\d{1,2}|((二|三)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))))(号|日|天))?" +
                "((?<week>((?<which_week>第一|第二|第三|第四|上一?个?|这一?个?|下一?个?)?(星期|周)(?<day_of_week>一|二|三|四|五|六|七|天|日|\\d)?)))?" +
                "(?<ampm>((凌晨)|(早上?)|(早晨)|(上午)|(中午)|(下午)|(傍晚)|(晚上?)|(夜晚?)))?" +
                "((?<hour>(\\d{1,2}|(二?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))))(点|：|:))?" +
                "(?<minute>(半|整|(\\d{1,2}|(二|三|四|五)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))分?))?)";



        Matcher matcher = Pattern.compile(regex).matcher(time);
        matcher.find();
        String mills = matcher.group("mills");
        String unittime = matcher.group("unittime");
        String year = matcher.group("year");
        String month = matcher.group("month");
        String day = matcher.group("day");
        String week = matcher.group("week");
        String which_week = matcher.group("which_week");
        String day_of_week = matcher.group("day_of_week");
        String ampm = matcher.group("ampm");
        String hour = matcher.group("hour");
        String minute = matcher.group("minute");


        Log.d("regex", "x后=" + mills);
        Log.d("regex", "x分钟后=" + unittime);

        Log.d("regex", "年=" + year);
        Log.d("regex", "月=" + month);
        Log.d("regex", "日=" + day);
        Log.d("regex", "星期=" + week);
        Log.d("regex", "which_week=" + which_week);
        Log.d("regex", "day_of_week=" + day_of_week);
        Log.d("regex", "ap=" + ampm);
        Log.d("regex", "时=" + hour);
        Log.d("regex", "分=" + minute);


        calendar = Calendar.getInstance();
        calendar_now = Calendar.getInstance();
//

        if(year==null&&month==null&&day==null&&week==null&&hour==null&&mills==null){
            return 0;
        }

        if (mills!=null){
            Log.d("ms", "识别了x分钟后");
            double banmillls=0;
            if (mills.matches("半")){
                banmillls = 0.5;
            }
            if (mills.matches("(一|壹|幺|二|两|三|四|五|六|七|八|九)?千?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?百?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?十?(一|壹|幺|二|两|三|四|五|六|七|八|九|十)")) {
                mills = String.valueOf(new DateChangebackup().chn2digit(mills));
                banmillls= Double.parseDouble(mills);
            }

            Matcher mills_matcher = Pattern.compile("\\d{1,12}|半").matcher(mills);
            if (mills_matcher.find()) {

                double ms=0;
                if (unittime.matches("秒钟?")){
                    ms=1;}
                if (unittime.matches("分钟?")){
                    ms=60;}
                if (unittime.matches("个?小时")){
                    ms=3600;}
                if (unittime.matches("天|日")){
                    ms=86400;}
                double timer = banmillls*ms*1000;

                Log.d("ms", "ms:"+ms);
                Log.d("timer", "timer:"+timer);

                calendar.add(Calendar.MILLISECOND, (int) timer);

            }



        }else {
            //年
            while (true) {


                int year_change = 0;
                //年是空值时
                if (year == null || year.equals("")) {
                    break;

                }
                while (true) {

                    //年是口述文字时
                    if (year.matches("上一?年的上一?") || year.matches("去年的去") || year.matches("前")) {
                        year_change = -2;
                    } else if (year.matches("上一?") || year.matches("去")) {
                        year_change = -1;
                    } else if (year.matches("这一?") || year.matches("本") || year.matches("该") || year.matches("今")) {
                        year_change = 0;
                    } else if (year.matches("下一?") || year.matches("明")) {
                        year_change = 1;
                    } else if (year.matches("下一?年的下一?") || year.matches("明年的明") || year.matches("后")) {
                        year_change = 2;
                    } else {
                        break;
                    }

                    calendar.add(Calendar.YEAR, year_change);
                    break;
                }


//            年是阿拉伯数字时
                Matcher matcher_year = Pattern.compile("\\d{2,4}").matcher(year);
                if (matcher_year.find()) {

                    if (year.matches("\\d{4}")) {
                        year_change = Integer.parseInt(matcher_year.group());
                    } else {
                        year_change = Integer.parseInt(matcher_year.group()) + 2000;
                    }
                    calendar.set(Calendar.YEAR, year_change);
                    Log.d("regex", matcher_year.group());
                    break;
                }
                break;
            }


            //月
            while (true) {
                int month_change = 0;
                //月是空值时
                if (month == null || month.equals("")) {
                    Log.d("流程", "month=null");
                    if (year != null) {
                        calendar.set(Calendar.MONTH, 0);
                        Log.d("流程", "year!=null");
                        break;
                    }
                    break;
                }


                while (true) {
                    Log.d("regex", "执行了 今月 明月");
                    //月是口述文字时
                    if (month.matches("上一?个?月的上一?个?") || month.matches("上上个?") || month.matches("前个?")) {
                        month_change = -2;
                    } else if (month.matches("上一?个?")) {
                        month_change = -1;
                    } else if (month.matches("这一?个?") || month.matches("本")) {
                        month_change = 0;
                    } else if (month.matches("下一?个?")) {
                        month_change = 1;
                    } else if (month.matches("下一?个?月的下一?个?") || month.matches("下下个?") || month.matches("后个?")) {
                        month_change = 2;
                    } else {
                        break;
                    }
                    Log.d("流程", "add calender.month");
                    calendar.add(Calendar.MONTH, month_change);//calender的month,在add时需要+1
                    break;
                }

                if (month.matches("十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十)")) {
                    month = String.valueOf(chn2digit(month));
                }
                //月是阿拉伯数字时
                Pattern pattern_month = Pattern.compile("\\d{1,2}");
                Log.d("regex", "执行了月的\\d{1,2}");
                Matcher matcher_month = pattern_month.matcher(month);
                if (matcher_month.find()) {
                    Log.d("流程", "set calender.month \\d");
                    calendar.set(Calendar.MONTH, Integer.parseInt(matcher_month.group()) - 1);
                    break;
                }

                break;
            }


            //日
            while (true) {
                int day_change = 0;
                if (day == null || day.equals("")) {
                    Log.d("流程", "day=null");
                    day_change = 0;
                    if (year != null || month != null) {
                        Log.d("流程", "set calender.DAY OF MONTH 1");
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                    }
                    break;

                }
                while (true) {
                    if (week == null || week.equals("")) {
                        if (day.matches("大大前")) {
                            day_change = -4;
                        } else if (day.matches("大前")) {
                            day_change = -3;
                        } else if (day.matches("昨天的昨") || day.matches("前")) {
                            day_change = -2;
                        } else if (day.matches("上一") || day.matches("昨")) {
                            day_change = -1;
                        } else if (day.matches("今") || day.matches("这")) {
                            day_change = 0;
                        } else if (day.matches("下一") || day.matches("明")) {
                            day_change = 1;
                        } else if (day.matches("明天的明") || day.matches("后")) {
                            day_change = 2;
                        } else if (day.matches("大后")) {
                            day_change = 3;
                        } else if (day.matches("大大后")) {
                            day_change = 4;
                        } else {
                            break;
                        }
                        Log.d("流程", "add calender.day of month");
                        calendar.add(Calendar.DAY_OF_MONTH, day_change);
                    }
                    break;
                }

                if (day.matches("(二|三)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十)")) {
                    day = String.valueOf(chn2digit(day));
                }
                Matcher matcher_day = Pattern.compile("\\d{1,2}").matcher(day);
                if (matcher_day.find()) {
                    Log.d("流程", "set calender.day of month \\d");
                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(matcher_day.group()));
                    Log.d("regex", matcher_day.group());
                    break;
                }
                break;
            }


            //星期
            while (true) {
                if (week == null || week.equals("")) {
                    break;
                }
                if (year != null || month != null) {
                    break;
                }

                int which_week_chang = 0;
                //如果星期＝null and 年 ＝ null and 月＝null
                if (which_week != null) {
                    if (which_week.matches("上")) {
                        which_week_chang = -1;
                    } else if (which_week.matches("这")) {
                        which_week_chang = 0;
                    } else if (which_week.matches("下")) {
                        which_week_chang = 1;
                    }
                    Log.d("流程", "add calender.week of month");
                    calendar.add(Calendar.WEEK_OF_MONTH, which_week_chang);
                }


                if (day_of_week != null) {
                    Log.d("流程", "day_of_week !=null");
                    if (day_of_week.matches("一|二|三|四|五|六|七|天|日")) {
                        Log.d("流程", "day_of_week 汉字转数字");
                        day_of_week = String.valueOf(chn2digit(day_of_week));
                    }
                    Matcher matcher_day_of_week = Pattern.compile("\\d").matcher(day_of_week);
                    if (matcher_day_of_week.find()) {
                        if (day_of_week.matches("\\d")) {
                            Log.d("流程", "set calender.day of week \\d");
                            calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(matcher_day_of_week.group()) + 1);
                        }
                    }
                } else {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                }

                Log.d("regex", "which_week: " + which_week + "||||day_of_week:" + day_of_week);
                break;
            }


            //ampm
            while (true) {
                if (ampm == null && hour == null) {
                    calendar.set(Calendar.AM_PM, 0);
                    break;
                }
                if (ampm != null && ampm.matches("凌晨|早上?|早晨|上午|中午")) {
                    calendar.set(Calendar.AM_PM, 0);
                    if (hour == null || hour.equals("")) {
                        if (ampm.matches("凌晨")) {
                            calendar.set(Calendar.HOUR, 4);
                            break;
                        }
                        if (ampm.matches("早上?")) {
                            calendar.set(Calendar.HOUR, 6);
                            break;
                        }
                        if (ampm.matches("早晨")) {
                            calendar.set(Calendar.HOUR, 6);
                            break;
                        }
                        if (ampm.matches("上午")) {
                            calendar.set(Calendar.HOUR, 6);
                            break;
                        }
                        if (ampm.matches("中午")) {
                            calendar.set(Calendar.HOUR, 11);
                            break;
                        }

                        break;
                    }
                } else if (ampm != null && ampm.matches("下午|傍晚|晚上?|夜晚?")) {
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                    if (hour == null || hour.equals("")) {
                        if (ampm.matches("下午")) {
                            calendar.set(Calendar.HOUR, 1);
                            break;
                        }
                        if (ampm.matches("傍晚")) {
                            calendar.set(Calendar.HOUR, 5);
                            break;
                        }
                        if (ampm.matches("晚上?")) {
                            calendar.set(Calendar.HOUR, 6);
                            break;
                        }
                        if (ampm.matches("夜晚")) {
                            calendar.set(Calendar.HOUR, 6);
                            break;
                        }
                        break;
                    }
                }


                break;
            }


            //时
            while (true) {
                int hour_change = 0;
                if ((hour == null || hour.equals(""))) {

                    if (ampm == null || ampm.equals("")) {
                        calendar.set(Calendar.HOUR, 9);
                    }
                    break;
                }
                if (hour == null || hour.equals("")) {

                    break;
                }

                if (hour != null && hour.matches("二?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十)")) {
                    hour = String.valueOf(chn2digit(hour));
                }
                Matcher matcher_hour = Pattern.compile("\\d{1,2}").matcher(hour);
                if (matcher_hour.find()) {
                    calendar.set(Calendar.HOUR, Integer.parseInt(matcher_hour.group()));

                    break;
                }


                break;
            }


            //分
            while (true) {
                int minute_change = 0;
                //分 如果“时”是空，而“分”有值。要不要把“分”归零？
                if (minute == null || minute.equals("")) {
                    calendar.set(Calendar.MINUTE, 0);
                    break;
                } else if (minute.matches("半")) {
                    calendar.set(Calendar.MINUTE, 30);
                    break;
                } else if (minute.matches("整")) {
                    calendar.set(Calendar.MINUTE, 0);
                    break;
                }
                if (minute.matches("(二|三|四|五)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十)")) {
                    minute = String.valueOf(chn2digit(minute));
                }
                Matcher matcher_minute = Pattern.compile("\\d{1,2}").matcher(minute);
                if (matcher_minute.find()) {
                    calendar.set(Calendar.MINUTE, Integer.parseInt(matcher_minute.group()));
                    break;
                }
                break;
            }

            if (which_week == null && year == null && month == null && calendar.getTimeInMillis() < calendar_now.getTimeInMillis()) {
                Log.d("流程", "add week of month 1");

                calendar.add(Calendar.WEEK_OF_MONTH, 1);
            }
            if (year == null && month == null && day == null && week == null) {
                Log.d("流程", "year,month,day,week 都是null");
                if (calendar.getTimeInMillis() < calendar_now.getTimeInMillis()) {
                    if (ampm != null) {
                        calendar.add(Calendar.HOUR_OF_DAY, 24);
                        Log.d("流程", "ampm!=null    加24");
                    } else {
                        calendar.add(Calendar.HOUR_OF_DAY, 12);
                        if (calendar.get(Calendar.HOUR_OF_DAY) < 7) {
                            calendar.add(Calendar.HOUR_OF_DAY, 12);
                            Log.d("流程", "ampm!=null    加12");

                        }
                    }
                }
            }


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

    public long chn2digit(String chnStr) {
        //init map
        java.util.Map<String, Integer> unitMap = new java.util.HashMap<String, Integer>();
        unitMap.put("十", 10);
        unitMap.put("百", 100);
        unitMap.put("千", 1000);
        unitMap.put("万", 10000);
        unitMap.put("亿", 100000000);

        java.util.Map<String, Integer> numMap = new java.util.HashMap<String, Integer>();
//        numMap.put("半", (int) 0.5);
        numMap.put("零", 0);
        numMap.put("一", 1);
        numMap.put("壹", 1);
        numMap.put("幺", 1);
        numMap.put("二", 2);
        numMap.put("两", 2);
        numMap.put("三", 3);
        numMap.put("四", 4);
        numMap.put("五", 5);
        numMap.put("六", 6);
        numMap.put("七", 7);
        numMap.put("天", 7);
        numMap.put("日", 7);
        numMap.put("八", 8);
        numMap.put("九", 9);

        //队列
        List<Long> queue = new ArrayList<Long>();
        long tempNum = 0;
        for (int i = 0; i < chnStr.length(); i++) {
            char bit = chnStr.charAt(i);
            System.out.print(bit);
            //数字
            if (numMap.containsKey(bit + "")) {

                tempNum = tempNum + numMap.get(bit + "");

                //一位数、末位数、亿或万的前一位进队列
                if (chnStr.length() == 1
                        | i == chnStr.length() - 1
                        | (i + 1 < chnStr.length() && (chnStr.charAt(i + 1) == '亿' | chnStr
                        .charAt(i + 1) == '万'))) {
                    queue.add(tempNum);
                }
            }
            //单位
            else if (unitMap.containsKey(bit + "")) {

                //遇到十 转换为一十、临时变量进队列
                if (bit == '十') {
                    if (tempNum != 0) {
                        tempNum = tempNum * unitMap.get(bit + "");
                    } else {
                        tempNum = 1 * unitMap.get(bit + "");
                    }
                    queue.add(tempNum);
                    tempNum = 0;
                }

                //遇到千、百 临时变量进队列
                if (bit == '千' | bit == '百') {
                    if (tempNum != 0) {
                        tempNum = tempNum * unitMap.get(bit + "");
                    }
                    queue.add(tempNum);
                    tempNum = 0;
                }

                //遇到亿、万 队列中各元素依次累加*单位值、清空队列、新结果值进队列
                if (bit == '亿' | bit == '万') {
                    long tempSum = 0;
                    if (queue.size() != 0) {
                        for (int j = 0; j < queue.size(); j++) {
                            tempSum += queue.get(j);
                        }
                    } else {
                        tempSum = 1;
                    }
                    tempNum = tempSum * unitMap.get(bit + "");
                    queue.clear();//清空队列
                    queue.add(tempNum);//新结果值进队列
                    tempNum = 0;
                }
            }
        }

        //output
        System.out.println();
        long sum = 0;
        for (Long i : queue) {
            sum += i;
        }
        System.out.println(sum);
        return sum;
    }
}
