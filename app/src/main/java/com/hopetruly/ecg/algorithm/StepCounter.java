package com.hopetruly.ecg.algorithm;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.warick.jni.filter.Fir;

/* renamed from: com.hopetruly.ecg.algorithm.c */
public class StepCounter {

    /* renamed from: a */
    Context mContext;

    /* renamed from: b */
    private int defaultHeight = 175;

    /* renamed from: c */
    private int defaultWeight = 60;

    /* renamed from: d */
    private int f2747d = 5;

    /* renamed from: e */
    private int f2748e = 0;

    /* renamed from: f */
    private long f2749f = -1;

    /* renamed from: g */
    private double cal_value = 0.0d;

    /* renamed from: h */
    private MathUtil mMathUtil;

    /* renamed from: i */
    private double[] f2752i;

    /* renamed from: j */
    private long step_value = 0;

    public StepCounter(Context context, int i, int i2) {
        this.mContext = context;
        if (i > 0) {
            this.defaultHeight = i;
        }
        if (i2 > 0) {
            this.defaultWeight = i2;
        }
        this.mMathUtil = new MathUtil();
        this.f2752i = new double[Fir.getOrder(Fir.Fir_6)];
        Fir.setDoubles_0(Fir.Fir_6, this.f2752i);
    }

    /* renamed from: c */
    private void calculateStep() {
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
            double d2 = ((((0.43d * ((double) this.defaultHeight)) + (0.57d * ((double) this.defaultWeight))) + (0.26d * ((double) i))) + (0.92d * d)) - 108.44d;
            Log.e("StepCounter", "CalculateCal->cal>>>>" + d2 + "----" + d + "----" + i);
            this.cal_value = this.cal_value + d2;
            Intent intent = new Intent("com.hopetruly.part.StepCounter.CAL");
            intent.putExtra("cal_value", this.cal_value);
//            C0140d.m485a(this.mContext).mo390a(intent);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            this.f2748e = 0;
            this.f2749f = -1;
        }
    }

    /* renamed from: a */
    public void clearStep() {
        this.cal_value = 0.0d;
        this.f2748e = 0;
        this.f2749f = -1;
    }

    /* renamed from: a */
    public void sendStep(float f) {
        if (this.mMathUtil.mo2463a((float) Fir.RealtimeFir(f, Fir.Fir_6, this.f2752i))) {
            this.step_value++;
            Intent intent = new Intent("com.hopetruly.part.StepCounter.STEP");
            intent.putExtra("step_value", this.step_value);
//            C0140d.m485a(this.mContext).mo390a(intent);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            calculateStep();
        }
    }

    /* renamed from: b */
    public void clearStep_value() {
        this.step_value = 0;
    }
}
