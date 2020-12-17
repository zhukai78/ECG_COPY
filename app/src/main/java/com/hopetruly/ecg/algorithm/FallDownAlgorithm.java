package com.hopetruly.ecg.algorithm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/* renamed from: com.hopetruly.ecg.algorithm.a */
public class FallDownAlgorithm {

    /* renamed from: a */
    int f2721a = 0;

    /* renamed from: b */
    int f2722b = 0;

    /* renamed from: c */
    int f2723c = 0;

    /* renamed from: d */
    int f2724d = 0;

    /* renamed from: e */
    Context mCtx;

    /* renamed from: f */
    private long freefallTimeMill = 0;

    /* renamed from: g */
    private long impactTimeMill = 0;

    /* renamed from: h */
    private long checkTimeMill = 0;

    /* renamed from: i */
    private boolean firstCheckBool = false;

    /* renamed from: j */
    private boolean impactBool = false;

    /* renamed from: k */
    private boolean f2731k = false;

    public FallDownAlgorithm(Context context) {
        this.mCtx = context;
    }

    /* renamed from: b */
    private void m2585b(int i, int i2, int i3, int i4) {
        this.checkTimeMill = System.currentTimeMillis();
        if (!this.firstCheckBool && i4 < 50) {
            Log.e("#####################", "free fall !!!!!!!!!!!");
            this.firstCheckBool = true;
            this.freefallTimeMill = this.checkTimeMill;
        }
        if (!this.impactBool && this.firstCheckBool) {
            int i5 = (int) (this.checkTimeMill - this.freefallTimeMill);
            if (i5 >= 400) {
                Log.e("#####################", "impact timeout !!!!!!!!!!!" + i5 + ":" + i4);
                this.firstCheckBool = false;
                this.impactBool = false;
            } else if (i4 > 500) {
                Log.e("#####################", "impact !!!!!!!!!!!" + i5 + ":" + i4);
                this.impactBool = true;
                this.impactTimeMill = this.checkTimeMill;
                this.f2722b = i4;
                this.f2723c = i4;
            } else {
                Log.e("#####################", "impact < limit!!!!!!!!!!!" + i5 + ":" + i4);
            }
        }
        if (this.firstCheckBool && this.impactBool) {
            int i6 = (int) (this.checkTimeMill - this.impactTimeMill);
            if (i6 < 3000) {
                if (Math.abs(this.f2723c - i4) < 25) {
                    Log.e("#####################", "stable!!!!!!!!!!!" + i6 + ">>" + this.f2723c + ":" + i4);
                    this.f2724d = this.f2724d + 1;
                } else {
                    Log.e("#####################", "not  stable!!!!!!!!!!!" + i6 + ">>" + this.f2723c + ":" + i4);
                    this.f2724d = 0;
                }
                this.f2723c = i4;
                if (this.f2724d > 3) {
                    this.f2731k = true;
//                    C0140d.m485a(this.mCtx).mo390a(new Intent("com.holptruly.part.FallDetection.FALLDOWN"));
                    LocalBroadcastManager.getInstance(mCtx).sendBroadcast(new Intent("com.holptruly.part.FallDetection.FALLDOWN"));
                    Log.e("#####################", "fall down !!!!!!!!!!!");
                    this.firstCheckBool = false;
                    this.impactBool = false;
                    this.f2731k = false;
                    this.f2724d = 0;
                    return;
                }
                return;
            }
            this.firstCheckBool = false;
            this.impactBool = false;
            this.f2731k = false;
            this.f2724d = 0;
            Log.e("#####################", "timeout!!!!!!!!!!!" + i6 + ":" + i4);
        }
    }

    /* renamed from: a */
    public void checkFall(int i, int i2, int i3, int i4) {
        m2585b(i, i2, i3, i4);
    }
}
