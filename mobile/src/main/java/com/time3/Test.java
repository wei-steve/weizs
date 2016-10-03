/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.time3;


import com.time3.nlp.TimeNormalizer;
import com.time3.nlp.TimeUnit;
import com.time3.util.DateUtil;

/**
 *
 * @author Administrator
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        test();
    }
    
    public static void test() {
        /*Lunar2Solar l = new Lunar2Solar();
        System.out.println("20140928："+ l.lunarToSolar(2014, 9, 28, true)[1] + "-" + l.lunarToSolar(2014, 9, 28, true)[2]);
        FeastToSolar f = new FeastToSolar();
        String ff = "青年节";
	System.out.println(ff+"："+ f.getFeast(ff)[0]+"-"+ f.getFeast(ff)[1]);*/
        //String path = TimeNormalizer.class.getResource("").getPath();

        //String classPath = path.substring(0, path.indexOf("build"));
        //System.out.println(classPath + ": classpath");
        TimeNormalizer normalizer = new TimeNormalizer();

        //		normalizer.parse("Hi，all。下周一下午三点开会");// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        //		System.out.println("Hi，all。下周一下午三点开会");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("早上六点起床");// 注意此处识别到6天在今天已经过去，自动识别为明早六点（未来倾向，可通过开关关闭：new TimeNormalizer(classPath+"/TimeExp.m", false)）
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("早上六点起床");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("周一开会");// 如果本周已经是周二，识别为下周周一。同理处理各级时间。（未来倾向）
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("周一开会");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("下下周一开会");//对于上/下的识别
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("下下周一开会");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("6:30 起床");// 严格时间格式的识别
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("6:30 起床");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("6-3 春游");// 严格时间格式的识别
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("6-3 春游");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("6月3  春游");// 残缺时间的识别 （打字输入时可便捷用户）
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("6月3  春游");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("明天早上跑步");// 模糊时间范围识别（可在RangeTimeEnum中修改
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("明天早上跑步");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //
        //		normalizer.parse("本周日到下周日出差");// 多时间识别
        //		unit = normalizer.getTimeUnit();
        //		System.out.println("本周日到下周日出差");
        //		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        //		System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());

        String o = "圣诞下午三点到五点开会";

        System.out.println("待处理：" + o);
        normalizer.parse(o);// 多时间识别，注意第二个时间点用了第一个时间的上文
        unit = normalizer.getTimeUnit();
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());
//        System.out.println("rests: " + arrayToString(normalizer.getRest()));
//        System.out.println("times origin: " + arrayToString(normalizer.getTimesOrigin()));

        /*
         * 输出：
           待处理：圣诞下午三点到五点开会
            2016-09-20 13:57:12 //这行不知道哪里输出来的，不用管，只在终端输出
            2016-12-25 15:00:00-false //第一个时间，非全天
            2016-12-25 17:00:00-false //第二个时间，非全天
            rests: {, 到, 开会} //第一个是空的，是最前面的部分，如果你用“提醒我圣诞”，这个就有值了
            times origin: {圣诞下午3点, 5点} //time的原值，大写被转成小写了，以后再考虑下要不要溯回原值
        */
    }
    
}
