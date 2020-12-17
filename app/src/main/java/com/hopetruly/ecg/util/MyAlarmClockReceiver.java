package com.hopetruly.ecg.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;

public class MyAlarmClockReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        MyAlarmClock a = MyAlarmClock.getInstance(context);
        if (a.isClock) {
            a.clockCnt++;
        }
        if (a.misClock) {
            Intent intent2 = new Intent("com.hopetruly.ecg.util.MyAlarmClock.COUNT_SEC");
            intent2.putExtra("com.hopetruly.ecg.util.MyAlarmClock.COUNT_SEC", a.clockCnt);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
        }
        Calendar instance = Calendar.getInstance();
        if (a.mCalendar != null && a.mCalendar.get(5) != instance.get(5)) {
            a.mCalendar = instance;
            Intent intent3 = new Intent("com.hopetruly.ecg.util.MyAlarmClock.DATE_CHANGE");
            intent3.putExtra("com.hopetruly.ecg.util.MyAlarmClock.DATE_CHANGE", instance.getTimeInMillis());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent3);
        }
    }
}
