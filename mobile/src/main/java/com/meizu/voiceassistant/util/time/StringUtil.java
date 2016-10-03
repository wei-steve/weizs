package com.meizu.voiceassistant.util.time;

/**
 * Created by weichangtan on 16/9/18.
 */

public class StringUtil {

    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }
}
