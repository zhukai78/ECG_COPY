package com.hopetruly.ecg.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

/* renamed from: com.hopetruly.ecg.util.i */
public class C0773i {

    /* renamed from: c */
    static C0773i f2914c;

    /* renamed from: a */
    final String f2915a = "MyAlarmClock";

    /* renamed from: b */
    Context f2916b;

    /* renamed from: d */
    boolean f2917d = false;

    /* renamed from: e */
    boolean f2918e = false;

    /* renamed from: f */
    boolean f2919f = false;

    /* renamed from: g */
    long f2920g;

    /* renamed from: h */
    Calendar f2921h;

    private C0773i() {
    }

    private C0773i(Context context) {
        this.f2916b = context;
    }

    /* renamed from: a */
    public static C0773i m2798a(Context context) {
        if (f2914c == null) {
            f2914c = new C0773i(context);
        }
        return f2914c;
    }

    /* renamed from: a */
    public void mo2786a() {
        ((AlarmManager) this.f2916b.getSystemService(Context.ALARM_SERVICE)).setRepeating(0, System.currentTimeMillis(), 1000, PendingIntent.getBroadcast(this.f2916b, 0, new Intent(this.f2916b, MyAlarmClockReceiver.class), 0));
        this.f2921h = Calendar.getInstance();
        this.f2921h.setTime(new Date());
    }

    /* renamed from: a */
    public void mo2787a(boolean z) {
        this.f2917d = z;
    }

    /* renamed from: b */
    public void mo2788b() {
        ((AlarmManager) this.f2916b.getSystemService(Context.ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this.f2916b, 0, new Intent(this.f2916b, MyAlarmClockReceiver.class), 0));
    }

    /* renamed from: b */
    public void mo2789b(boolean z) {
        this.f2918e = z;
        mo2787a(z);
        this.f2920g = 0;
    }
}
