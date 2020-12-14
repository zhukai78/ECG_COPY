package com.hopetruly.ecg.algorithm;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.warick.jni.filter.Fir;

/* renamed from: com.hopetruly.ecg.algorithm.c */
public class C0737c {

    /* renamed from: a */
    Context f2744a;

    /* renamed from: b */
    private int f2745b = 175;

    /* renamed from: c */
    private int f2746c = 60;

    /* renamed from: d */
    private int f2747d = 5;

    /* renamed from: e */
    private int f2748e = 0;

    /* renamed from: f */
    private long f2749f = -1;

    /* renamed from: g */
    private double f2750g = 0.0d;

    /* renamed from: h */
    private C0738d f2751h;

    /* renamed from: i */
    private double[] f2752i;

    /* renamed from: j */
    private long f2753j = 0;

    public C0737c(Context context, int i, int i2) {
        this.f2744a = context;
        if (i > 0) {
            this.f2745b = i;
        }
        if (i2 > 0) {
            this.f2746c = i2;
        }
        this.f2751h = new C0738d();
        this.f2752i = new double[Fir.getOrder(Fir.f3077g)];
        Fir.m2980a(Fir.f3077g, this.f2752i);
    }

    /* renamed from: c */
    private void m2599c() {
        if (this.f2749f == -1) {
            this.f2749f = SystemClock.elapsedRealtime();
            Log.e("StepCounter", "CalculateCal->CalStartTime>>>>" + this.f2749f);
        }
        this.f2748e++;
        if (this.f2748e == this.f2747d) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Log.e("StepCounter", "CalculateCal->TempTime>>>>" + elapsedRealtime);
            double d = (((double) (elapsedRealtime - this.f2749f)) / 1000.0d) / 60.0d;
            int i = (int) ((1.0d / d) * 5.0d);
            double d2 = ((((0.43d * ((double) this.f2745b)) + (0.57d * ((double) this.f2746c))) + (0.26d * ((double) i))) + (0.92d * d)) - 108.44d;
            Log.e("StepCounter", "CalculateCal->cal>>>>" + d2 + "----" + d + "----" + i);
            this.f2750g = this.f2750g + d2;
            Intent intent = new Intent("com.hopetruly.part.StepCounter.CAL");
            intent.putExtra("cal_value", this.f2750g);
//            C0140d.m485a(this.f2744a).mo390a(intent);
            LocalBroadcastManager.getInstance(f2744a).sendBroadcast(intent);
            this.f2748e = 0;
            this.f2749f = -1;
        }
    }

    /* renamed from: a */
    public void mo2460a() {
        this.f2750g = 0.0d;
        this.f2748e = 0;
        this.f2749f = -1;
    }

    /* renamed from: a */
    public void mo2461a(float f) {
        if (this.f2751h.mo2463a((float) Fir.RealtimeFir(f, Fir.f3077g, this.f2752i))) {
            this.f2753j++;
            Intent intent = new Intent("com.hopetruly.part.StepCounter.STEP");
            intent.putExtra("step_value", this.f2753j);
//            C0140d.m485a(this.f2744a).mo390a(intent);
            LocalBroadcastManager.getInstance(f2744a).sendBroadcast(intent);
            m2599c();
        }
    }

    /* renamed from: b */
    public void mo2462b() {
        this.f2753j = 0;
    }
}
