package com.meizu.voiceassistant.commandType;

import android.content.Intent;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import com.meizu.voiceassistant.SpeechApp;


/**
 * Created by weichangtan on 16/9/21.
 */

public class GoogleSearch extends baseTask {
    private static final String TAG= GoogleSearch.class.getSimpleName();
    private int state;

    public GoogleSearch() {
    }

    public GoogleSearch(int state) {
        this.state = state;
    }

    @Override
    public void doSomething(String text) {
        if (state==1){
            musicSearch();
        }else {
            search(text);
        }
    }

    public void search(String text){
        Matcher matcher=Pattern.compile("搜索(?<query>.+)").matcher(text);
        while (matcher.find()){
            if (!matcher.group("query").equals("")){
                Intent intent = new Intent();
                intent.setClassName("com.google.android.googlequicksearchbox","com.google.android.googlequicksearchbox.SearchActivity");
                intent.setAction("com.google.android.googlequicksearchbox.GOOGLE_SEARCH");
                intent.putExtra("query",matcher.group("query"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SpeechApp.getContext().startActivity(intent);
                return;
            }

        }
    }

    private void musicSearch(){
        Intent intent = new Intent();
        intent.setClassName("com.google.android.googlequicksearchbox","com.google.android.googlequicksearchbox.SearchActivity");
        intent.setAction("com.google.android.googlequicksearchbox.MUSIC_SEARCH");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SpeechApp.getContext().startActivity(intent);
    }
}
