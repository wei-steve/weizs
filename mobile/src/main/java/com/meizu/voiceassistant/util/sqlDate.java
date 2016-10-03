package com.meizu.voiceassistant.util;

/**
 * Created by weichangtan on 16/5/21.
 */
public class sqlDate {
    private int id;
    private String title;
    private int SIBLING_ID;
    private int deleted;
    private int Completed;


    public sqlDate(int id, String title, int SIBLING_ID,int completed) {
        this.id = id;
        this.title = title;
        this.SIBLING_ID = SIBLING_ID;
        this.Completed=completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSIBLING_ID() {
        return SIBLING_ID;
    }

    public void setSIBLING_ID(int SIBLING_ID) {
        this.SIBLING_ID = SIBLING_ID;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getCompleted() {
        return Completed;
    }

    public void setCompleted(int completed) {
        Completed = completed;
    }
}
