package com.meizu.voiceassistant.util;

import android.graphics.Bitmap;

/**
 * Created by weichangtan on 16/1/7.
 */
public class ListData {

    public static final int voicecommand=1;
    public static final int answer=2;
    public static final int english=3;
    public static final int stock=4;
    public static final int search=5;
    public static final int call=6;


    private String content;
    private int flag;
    private Bitmap bm;

    public ListData(String content,int flag,Bitmap bm){
        setContent(content);
        setFlag(flag);
        setBm(bm);
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm() {
        return bm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
