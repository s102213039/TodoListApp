package com.example.yychiu.todolistapp;

/**
 * Created by yychiu on 2017/11/30.
 */

public class Todostuff {
    String date;
    String info;
    boolean done;

    public Todostuff(){}

    public Todostuff(String date, String info, boolean done) {
        this.date = date;
        this.info = info;
        this.done = done;
    }

    public String getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    public boolean isDone() {
        return done;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
