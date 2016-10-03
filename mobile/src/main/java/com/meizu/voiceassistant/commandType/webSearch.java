package com.meizu.voiceassistant.commandType;


import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.util.ListData;
import com.meizu.voiceassistant.util.ShowResult;

/**
 * Created by weichangtan on 16/9/7.
 */
public class webSearch extends baseTask{

    @Override
    public void doSomething(String text) {
        start(text);
    }

    public static void start(String result){
        String location;

        Matcher searchmatcher = Pattern.compile("搜索(?<location>.+)").matcher(result);
        if (searchmatcher.find()) {
            location = searchmatcher.group("location");
            ShowResult.show(location, ListData.search);

        }
    }
}
