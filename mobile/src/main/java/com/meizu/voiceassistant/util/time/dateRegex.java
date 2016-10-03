package com.meizu.voiceassistant.util.time;


import android.util.Log;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.time3.nlp.stringPreHandlingModule;

/**
 * Created by weichangtan on 16/9/17.
 */

public class dateRegex {
    private static final String TAG=dateRegex.class.getSimpleName();



    private String other="(?<other>[^明今]*)";
    private static String numYear="(二?(零|壹|幺|一|二|三|四|五|六|七|八|九)?(零|壹|幺|一|二|三|四|五|六|七|八|九)?(零|壹|幺|一|二|两|三|四|五|六|七|八|九))";
    private static String numMonth="(十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))";
    private static String numday="((二|三)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))";
    private static String numhour="(二?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))";
    private static String numMinute="(\\d{1,2}|(二|三|四|五)?十?(零|壹|幺|一|二|两|三|四|五|六|七|八|九|十))";


    public static String year = "((?<year>(上一?年的上一?)|(去年的去)|前|(上一?)|去|(这一?)|本|该|今|(下一?)|明|(下一?年的下一?)|(明年的明)|后|\\d{2,4})(年|-))?";
    public static String month= "((?<month>(上一?个?月的上一?个?)|(上上个?)|(前个?)|(上一?个?)|(这一?个?)|本|(下一?个?)|(下一?个?月的下一?个?)|(下下个?月)|(后个?月)|\\d{1,2})(月|-))?";
    public static String day = "(?<day>(大大前)|(大前)|(昨天的昨)|(上一)|昨|今|这|(下一)|明|(明天的明)|后|(大后)|(大大后)|\\d{1,2})(号|日|天)";
    public static String week = "(?<week>(?<which_week>(第一)|(第二)|(第三)|(第四)|(上一?个?)|(这一?个?)|(下一?个?))?(星期|周)(?<day_of_week>一|二|三|四|五|六|七|天|日|1-7)?)";
    public static String ampm = "(?<ampm>(凌晨)|(早上?)|(早晨)|(上午)|(中午)|(下午)|(傍晚)|(晚上?)|(夜晚?))?";
    public static String hour = "("+  "(?<hour>\\d{1,2})"+"(点|：|:)" + "(?<minute>半|整|\\d{1,2})?"+  ")?";

	private static String minute = "(?<minute2>"+numMinute+"分)?";

    public static String totle = year + month + "((" + day + ")|" + week + ")?" + ampm + hour;


    private static String mills =  "(?<mills>\\d{1,12}|(一|壹|幺|二|两|三|四|五|六|七|八|九)?千?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?百?(零|一|壹|幺|二|两|三|四|五|六|七|八|九)?十?(半|一|壹|幺|二|两|三|四|五|六|七|八|九|十))(?<unittime>秒钟?|分钟?|个?小时|天|日)后";





    public static String timeregex="((" + mills + ")" + "|" + "(" +totle + "))";

	

    public static String test(String time) {
        String str=null;

        time = stringPreHandlingModule.numberTranslator(time);
        Matcher matcher = Pattern.compile( dateRegex.timeregex).matcher(time);
        DateChange dateChange = new DateChange();
        dateChange.parse(time);
        long timeInMillis=dateChange.getTimeInMillis();
        String Time = dateChange.getTime();

//        while (matcher.find()) {
//            System.out.println("regular:"  + " justtext:"
//                    + matcher.justtext() + " end:" + matcher.end());
//        }

        while (matcher.find()) {
            Log.d(TAG, "group数量:" +matcher.groupCount() + " justtext:" + matcher.start() + " end:" + matcher.end());
            String first=matcher.group(0);;
            String second=matcher.group(1);;
//            String other = matcher.group("other");
            String mills1 = matcher.group("mills");
            String unittime1 = matcher.group("unittime");
            String year1 =  matcher.group("year");
            String month1 = matcher.group("month");
            String day1 = matcher.group("day");
            String week1 = matcher.group("week");
            String which_week1 = matcher.group("which_week");
            String day_of_week1 = matcher.group("day_of_week");
            String ampm1 = matcher.group("ampm");
            String hour1 = matcher.group("hour");
            String minute1 = matcher.group("minute");

            if (matcher.group().equals("")){
                Log.d(TAG, "##############################################################################");
            }else {
                str = "我的工具-结果:" + "\n" +
                        "matcher.group():       " + matcher.group() + "\n" +
                        "first:       " + first + "\n" +
                        "second:       " + second + "\n" +
                        "\n" +
                        "DateChange:timeInMillis:   " + timeInMillis + "\n" +
                        "DateChange:Time:   " + Time + "\n" +
//                    "other:       " + other + "\n" +
                        "mills:       " + mills1 + "\n" +
                        "unittime:    " + unittime1 + "\n" +
                        "年:        " + year1 + "\n" +
                        "月:       " + month1 + "\n" +
                        "日:         " + day1 + "\n" +
                        "周:        " + week1 + "\n" +
                        "哪周:  " + which_week1 + "\n" +
                        "周几: " + day_of_week1 + "\n" +
                        "ampm:        " + ampm1 + "\n" +
                        "时间:        " + hour1 + "\n" +
                        "分:      " + minute1 + "\n";

                Log.d(TAG, str);
            }
        }
        return str;
    }
}
