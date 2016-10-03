package com.meizu.voiceassistant.util;

import java.util.Calendar;

/**
 * Created by weichangtan on 16/5/13.
 */
public class SayDate {
    public String saydate(long tergetdate) {
        String say_date = null;

        if (tergetdate == 0) {
            return null;
        }
        Calendar terget = Calendar.getInstance();
        terget.setTimeInMillis(tergetdate);
        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.YEAR) == terget.get(Calendar.YEAR)) {
            //同一年的时候
            int day_of_year = terget.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR);
            int week_of_year = terget.get(Calendar.WEEK_OF_YEAR) - now.get(Calendar.WEEK_OF_YEAR);
            int month = terget.get(Calendar.MONTH) - now.get(Calendar.MONTH);
            if (day_of_year < 3 && day_of_year > -3) {
                //是今天的前后两天
                switch (day_of_year) {
                    case -2:
                        say_date = "前天";
                        break;
                    case -1:
                        say_date = "昨天";
                        break;
                    case 0:
                        say_date = "今天";
                        break;
                    case 1:
                        say_date = "明天";
                        break;
                    case 2:
                        say_date = "后天";
                        break;
                    default:
                        break;
                }
            } else if (week_of_year > -2 && week_of_year < 2) {
                switch (week_of_year) {
                    case -1:
                        say_date = "上星期";
                        break;
                    case 0:
                        say_date = "这星期";
                        break;
                    case 1:
                        say_date = "下星期";
                        break;
                    default:
                        break;
                }
                String day_of_week = null;
                switch (terget.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        day_of_week = "天";
                        break;
                    case 2:
                        day_of_week = "一";
                        break;
                    case 3:
                        day_of_week = "二";
                        break;
                    case 4:
                        day_of_week = "三";
                        break;
                    case 5:
                        day_of_week = "四";
                        break;
                    case 6:
                        day_of_week = "五";
                        break;
                    case 7:
                        day_of_week = "六";
                        break;

                }
                say_date = say_date + day_of_week;
            } else if (month > -2 && month < 2) {
                switch (month) {
                    case -1:
                        say_date = "上月";
                        break;
                    case 0:
                        say_date = "本月";
                        break;
                    case 1:
                        say_date = "下月";
                        break;
                    default:
                        break;
                }
                say_date = say_date + terget.get(Calendar.DAY_OF_MONTH) + "号";
            } else {
                int saymonth = terget.get(Calendar.MONTH) + 1;
                say_date = "今年" + saymonth + "月" + terget.get(Calendar.DAY_OF_MONTH) + "日";
            }


        } else {
            int saymonth = terget.get(Calendar.MONTH) + 1;
            String day_of_week = null;
            switch (terget.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    day_of_week = "天";
                    break;
                case 2:
                    day_of_week = "一";
                    break;
                case 3:
                    day_of_week = "二";
                    break;
                case 4:
                    day_of_week = "三";
                    break;
                case 5:
                    day_of_week = "四";
                    break;
                case 6:
                    day_of_week = "五";
                    break;
                case 7:
                    day_of_week = "六";
                    break;

            }
            say_date = terget.get(Calendar.YEAR) + "年" + saymonth + "月" + terget.get(Calendar.DAY_OF_MONTH) + "日" +
                    "星期" + day_of_week;

        }


        String say_day_ampm = null;
        if (terget.get(Calendar.AM_PM) == Calendar.AM) {
            say_day_ampm = "上午";
        } else if (terget.get(Calendar.AM_PM) == Calendar.PM) {
            say_day_ampm = "下午";
        }

        String say_minute = null;
        if (terget.get(Calendar.MINUTE) != 0) {
            say_minute = terget.get(Calendar.MINUTE) + "分";

        } else if (terget.get(Calendar.MINUTE) == 30) {
            say_minute = "半";
        } else {
            say_minute = "";
        }

        say_date = say_date + "，" + say_day_ampm+ terget.get(Calendar.HOUR) + "点" + say_minute+"，";
        return say_date;
    }

}
