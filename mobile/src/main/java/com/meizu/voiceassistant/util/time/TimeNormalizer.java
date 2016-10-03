package com.meizu.voiceassistant.util.time;

/**
 * Created by weichangtan on 16/9/18.
 */

public class TimeNormalizer {

    private String time;
    private String baseTime;

    public void parse(String time){
        this.time = time;
    }
    public void parse(String time,String baseTime){
        this.time = time;
        this.baseTime = baseTime;

    }
}
