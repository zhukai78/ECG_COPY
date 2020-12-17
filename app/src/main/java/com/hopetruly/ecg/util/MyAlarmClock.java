package com.hopetruly.ecg.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

/* renamed from: com.hopetruly.ecg.util.i */
public class MyAlarmClock {

    /* renamed from: c */
    static MyAlarmClock myAlarmClock;

    /* renamed from: a */
    final String TAG = "MyAlarmClock";

    /* renamed from: b */
    Context mContext;

    /* renamed from: d */
    boolean misClock = false;

    /* renamed from: e */
    boolean isClock = false;

    /* renamed from: f */
    boolean f2919f = false;

    /* renamed from: g */
    long clockCnt;

    /* renamed from: h */
    Calendar mCalendar;

    private MyAlarmClock() {
    }

    private MyAlarmClock(Context context) {
        this.mContext = context;
    }

    /* renamed from: a */
    public static MyAlarmClock getInstance(Context context) {
        if (myAlarmClock == null) {
            myAlarmClock = new MyAlarmClock(context);
        }
        return myAlarmClock;
    }

    /* renamed from: a */
    public void setTimeDate() {
        ((AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE)).setRepeating(0, System.currentTimeMillis(), 1000, PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.mContext, MyAlarmClockReceiver.class), 0));
        this.mCalendar = Calendar.getInstance();
        this.mCalendar.setTime(new Date());
    }

    /* renamed from: a */
    public void setClock(boolean z) {
        this.misClock = z;
    }

    /* renamed from: b */
    public void cancelAlarmManager() {
        ((AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.mContext, MyAlarmClockReceiver.class), 0));
    }

    /* renamed from: b */
    public void setIsClock(boolean z) {
        this.isClock = z;
        setClock(z);
        this.clockCnt = 0;
    }
}
