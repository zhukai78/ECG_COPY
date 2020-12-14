package com.hopetruly.ecg.entity;

import java.io.Serializable;

public class PedometerRecord implements Serializable {
    private static final long serialVersionUID = 1;
    private long cal = 0;
    private long count = 0;
    private long cur_step = 0;
    private int day;
    private String desc;
    private int month;
    private long target = 0;
    private String userId;
    private int year;

    public long getCal() {
        return this.cal;
    }

    public long getCount() {
        return this.count;
    }

    public long getCurStep() {
        return this.cur_step;
    }

    public int getDay() {
        return this.day;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getMonth() {
        return this.month;
    }

    public long getTarget() {
        return this.target;
    }

    public String getUserId() {
        return this.userId;
    }

    public int getYear() {
        return this.year;
    }

    public void setCal(long j) {
        this.cal = j;
    }

    public void setCount(long j) {
        this.count = j;
    }

    public void setCurStep(long j) {
        this.cur_step = j;
    }

    public void setDay(int i) {
        this.day = i;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setMonth(int i) {
        this.month = i;
    }

    public void setTarget(long j) {
        this.target = j;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setYear(int i) {
        this.year = i;
    }
}
