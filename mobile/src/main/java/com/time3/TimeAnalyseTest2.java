/**
 * Copyright (c) 2016 21CN.COM . All rights reserved.<br>
 *
 * Description: TimeNLP<br>
 *
 * Modified log:<br>
 * ------------------------------------------------------<br>
 * Ver.	Date	Author	Description<br>
 * ------------------------------------------------------<br>
 * 1.0	2016年5月4日	kexm	created.<br>
 */
package com.time3;

import java.util.regex.Pattern;

//import org.junit.Test;
//import com.time.nlp.TimeNormalizer;
//import com.time.nlp.TimeUnit;
//import com.time.util.DateUtil;
import com.time.nlp.TimeNormalizer;
import com.time.nlp.TimeUnit;
import com.time.util.DateUtil;
import com.time.util.Lunar2Solar;
import com.time.util.FeastToSolar;
import com.time.util.LunarSolarConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * 测试类
 * <p>
 * @author <a href="mailto:kexm@corp.21cn.com">kexm</a>
 * @version 1.0
 * @since 2016年5月4日
 *
 */
public class TimeAnalyseTest2 {
    private static String[] fest = new String[]{"0101|元旦节", "0108|周恩来逝世纪念日", "0110|中国公安110宣传日", "0121|列宁逝世纪念日", "0126|国际海关日", "0202|世界湿地日", "0204|世界抗癌日", "0207|京汉铁路罢工纪念", "0207|国际声援南非日", "0210|国际气象节", "0214|情人节", "0219|邓小平逝世纪念日", "0221|反对殖民制度斗争日", "0221|国际母语日", "0224|第三世界青年日", "0301|国际海豹日", "0303|全国爱耳日", "0303|世界野生动植物日", "0305|周恩来诞辰纪念日", "0305|“向雷锋同志学习”纪念日", "0305|中国青年志愿者服务日", "0306|世界青光眼日", "0308|妇女节|国际劳动妇女节", "0312|孙中山逝世纪念日", "0312|中国植树节", "0314|马克思逝世纪念日", "0314|白色情人节", "0315|国际消费者权益日", "0316|手拉手情系贫困小伙伴全国统一行动日", "0317|国际航海日", "0317|中国国医节", "0318|全国科技人才活动日", "0321|世界林业节", "0321|消除种族歧视国际日", "0321|世界儿歌日世界诗歌日", "0321|世界睡眠日", "03月22日至4月25日之间|复活节", "0322|世界水日", "0323|世界气象日", "0324|世界防治结核病日", "0330|巴勒斯坦国土日", "0401|愚人节|国际愚人节", "0402|国际儿童图书日", "0402|世界自闭症日", "0405|巴勒斯坦儿童日", "0407|世界卫生日", "0407|世界高血压日", "0410|非洲环境保护日", "0411|世界帕金森日", "0415|非洲自由日", "04月16日至18日：全球青年服务日", "0417|世界血友病日", "0418|国际古迹遗址日", "0421|全国企业家活动日", "0422|列宁诞辰纪念日", "0422|世界地球日", "0422|世界法律日", "0423|世界图书和版权日", "0424|世界青年反对殖民主义日", "0424|亚非新闻工作者日", "0425|全国儿童预防接种宣传日", "0426|世界知识产权日", "0427|联谊城日", "0428|世界安全生产与健康日", "0430|全国交通安全反思日", "0501|(国际)?劳动节", "0503|世界新闻自由日", "0504|青年节|中国青年节", "0504|五四运动纪念日", "0505|马克思诞辰纪念日", "0508|世界红十字日", "0508|世界微笑日", "0511|世界肥胖日", "0512|国际护士节", "0515|全国碘缺乏病防治日", "0515|国际家庭日", "0517|世界电信日", "0518|国际博物馆日", "0520|全国母乳喂养宣传日", "0520|中国学生营养日", "0520|世界计量日", "0522|生物多样性国际日", "0525|非洲解放日", "0526|世界向人体条件挑战日", "0527|上海解放日", "0529|国际维和人员日", "0530|“五卅”反对帝国主义运动纪念日", "0531|世界无烟日", "0601|(国际)?儿童节", "0601|国际牛奶日", "0604|受侵略戕害的无辜儿童国际日", "0605|世界环境日", "0606|全国爱眼日", "0608|世界海洋日", "0611|中国人口日", "0612|世界无童工日", "0614|世界献血日", "0617|世界防止荒漠化和干旱日", "0620|世界难民日", "0622|中国儿童慈善活动日", "0623|国际奥林匹克日", "0623|世界手球日", "0625|全国土地日", "0626|国际禁毒日", "0626|禁止药物滥用和非法贩运国际日", "0626|国际宪章日", "0626|支援酷刑受害者国际日", "0701|中国共产党诞生日", "0701|香港回归纪念日", "0701|亚洲30亿人口日", "0702|国际体育记者日", "0708|世界过敏性疾病日", "0707|中国人民抗日战争纪念日", "0711|世界人口日", "0711|中国航海节", "0726|世界语创立日", "0730|非洲妇女日", "0801|中国人民解放军建军节", "0805|恩格斯逝世纪念日", "0806|国际电影节", "0808|全民健身日", "0812|国际青年日", "0813|国际左撇子日", "0815|日本正式宣布无条件投降日", "0822|邓小平诞辰纪念日", "0823|贩卖黑奴及其废除的国际纪念日", "0826|全国律师咨询日", "0901|全国中小学开学日", "0903|中国抗日战争胜利纪念日", "0908|国际新闻工作者日", "0908|世界扫盲日", "0909|毛泽东逝世纪念日", "0910|中国教师节", "0910|世界预防自杀日", "0914|世界清洁地球日", "0916|国际臭氧层保护日", "0918|“九·一八”事变纪念日", "0920|全国爱牙日", "0921|国际和平日", "0922|世界无车日", "0921|世界老年性痴呆宣传日", "0927|世界旅游日", "1001|国庆节", "1001|国际音乐日", "1001|国际老年人日", "1002|国际和平与民主自由斗争日", "1004|世界动物日", "1005|世界教师日", "1008|全国高血压日", "1009|世界邮政日", "1010|辛亥革命纪念日", "1010|世界精神卫生日", "1011|声援南非政治犯日", "1011|世界镇痛日", "1012|世界60亿人口日", "1013|中国少年先锋队诞辰日", "1013|世界保健日", "1014|世界标准日", "1015|国际盲人节", "1016|世界粮食日", "1017|世界消除贫困日", "1022|世界传统医药日", "1024|联合国日", "1024|世界发展宣传日", "1025|抗美援朝纪念日", "1028|关注男性生殖健康日", "1031|世界勤俭日", "1031|万圣节前夕", "1106|防止战争和武装冲突糟蹋环境国际日", "1107|苏联十月革命纪念日", "1107|世界美发日", "1107|世界美容日", "1108|中国记者节", "1109|中国消防宣传日", "1110|世界青年节", "1112|刘少奇逝世纪念日", "1112|孙中山诞辰纪念日", "1114|世界糖尿病日", "1116|国际容忍日", "1117|国际大学生节", "1120|非洲工业化日", "1120|国际儿童日", "1121|世界电视日", "1121|世界问候日", "1124|刘少奇诞辰纪念日", "1125|消除对妇女的暴力行为国际日", "1125|国际素食日", "1128|恩格斯诞辰纪念日", "1129|声援巴勒斯坦人民国际日", "1201|世界艾滋病日", "1202|废除奴隶制国际日", "1203|国际残疾人日", "1204|全国法制宣传日", "1205|促进经济和社会发展自愿人员国际日", "1205|世界弱能人士日", "1207|国际民航日", "1209|“一二·九”运动纪念日", "1209|世界足球日", "1209|国际反腐败日", "1210|世界人权日", "1211|国际山岳日", "1212|西安事变纪念日", "1213|南京大屠杀纪念日", "1215|世界强化免疫日", "1218|国际移徙者日", "1219|联合国南南合作日", "1220|澳门回归纪念日", "1220|国际人类团结日", "1221|国际篮球日", "1224|平安夜", "1225|圣诞节", "1226|毛泽东诞辰纪念日", "1226|节礼日"};
    private static String[] festc = new String[]{"01010|黑人日", "01021|日本成人节", "04040|世界儿童日", "05026|世界高血压日", "05020|母亲节", "05030|全国助残日", "06030|父亲节", "07016|国际合作节", "09036|全民国防教育日", "09040|国际聋人节", "10011|国际住房日", "10011|国际建筑日", "10024|世界视觉日", "10023|减少自然灾害国际日", "11044|美国感恩节", "12020|国际儿童电视广播日"};
    private static String[] festl = new String[]{"0101|春节", "0115|元宵节?|上元节|灯节", "0202|龙抬头节?", "0505|端午节?", "0707|七夕(情人)?节?", "0715|中元节|鬼节", "0815|中秋节?", "0909|重阳节?", "1208|腊八节?", "1230|除夕日?"};
    //fest 公历节日(4位)，0-2是月份，2-4是日
	//festc 某月第几个星期几(5位)，如05020母亲节，05月第02个星期日(从星期日对应0开始)
	//festl 农历节日(4位)，参照公历，其中二十四节气内置计算，不可传入
	//各个节日的日期规则和pattern要用“|”隔开
	
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        TimeNormalizer normalizer = new TimeNormalizer();
		
		// 恢复初始，防止重复parse时出问题
//		normalizer.clear();
		
		//    外接接口，可手动设置pattern，
		//    该pattern只用于第一步分词，在parse前有效
		//    识别规则以外的字词将被抛弃
		//String pattern = normalizer.getPattern(); //获取匹配字符串
		//normalizer.setPattern(pattern); //设置匹配字符串
		
		// setSolarFeastPattern 设置公历节日
		// setLunarFeastPattern 设置农历节日
		// setCalFeastPattern   设置如“母亲节”要计算的节日
        normalizer.setSolarFeastPattern(fest);
        normalizer.setLunarFeastPattern(festc);
        normalizer.setCalFeastPattern(festl);
		
		// setReplaceModal 设置是否替换语气词["的"]，默认fasle
		normalizer.setReplaceModal(false);
		
		// 设置最多获取多少组时间，默认无限制
//		normalizer.setMaxTime(2);
		
		TimeUnit[] unit;
		String[] rests, timeOrigin;

        normalizer.parse("Hi，all。下周一下午三点开会"); //分词
        unit = normalizer.getTimeUnit();
		rests = normalizer.getRest();
		timeOrigin = normalizer.getTimesOrigin();
        /*
		 * getTimeUnit()获取时间对象数组，每个对象包含 getTime()-时间，和 getIsAllDayTime()-是否整天
		 * getRest()获取从分词后时间以外的字符窜数组，位置按顺序排序
		 * getTimeOrigin()获取getTimeUnit()的字符串值，一一对应
		 */
		System.out.println("Hi，all。下周一下午三点开会");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
		System.out.println("rests: " + arrayToString(normalizer.getRest()));
        System.out.println("times origin: " + arrayToString(normalizer.getTimesOrigin()));
		/*
		 * Hi，all。下周一下午三点开会
		 2016-09-26 15:00:00-false
		 rests: {Hi，all。, 开会}
		 times origin: {下周1下午3点}
		 */
        
		////////////////////////////////////////////////////////////////////////////////////////////////////
//		normalizer.clear();
//		normalizer.setMaxTime(2);//获取两组后不再向下匹配
		
        String o = "母亲节下午三点到五点提醒我儿童节要回家";

        System.out.println("\n待处理：" + o);
        normalizer.parse(o);// 多时间识别，注意第二个时间点用了第一个时间的上文
        unit = normalizer.getTimeUnit();
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());
		//System.out.println(DateUtil.formatDateDefault(unit[2].getTime()) + "-" + unit[2].getIsAllDayTime());
        System.out.println("rests: " + arrayToString(normalizer.getRest()));
        System.out.println("times origin: " + arrayToString(normalizer.getTimesOrigin()));
        /*
         * 输出：
		 待处理：母亲节下午三点到五点提醒我儿童节要回家
		 2016-09-22 09:16:27
		 2017-05-27 15:00:00-false
		 2017-05-27 17:00:00-false
		 rests: {, 到, 提醒我儿童节要回家}
		 times origin: {母亲节下午3点, 5点}
        */
    }

    public static String arrayToString(String[] arr) {
        StringBuilder sb = new StringBuilder(arr.length * 3); // StringBuilder(arr.length*3)性能比StringBuilder()高
        sb.append("{");
        int offset = arr.length - 1;
        for (int i = 0; i < offset; i++) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[offset]).append("}");

        return sb.toString();
    }
    
    public static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder(arr.length * 3); // StringBuilder(arr.length*3)性能比StringBuilder()高
        sb.append("{");
        int offset = arr.length - 1;
        for (int i = 0; i < offset; i++) {
            sb.append(arr[i]+"").append(", ");
        }
        sb.append(arr[offset]).append("}");

        return sb.toString();
    }

}


