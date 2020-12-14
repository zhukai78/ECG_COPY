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
    Context f2725e;

    /* renamed from: f */
    private long f2726f = 0;

    /* renamed from: g */
    private long f2727g = 0;

    /* renamed from: h */
    private long f2728h = 0;

    /* renamed from: i */
    private boolean f2729i = false;

    /* renamed from: j */
    private boolean f2730j = false;

    /* renamed from: k */
    private boolean f2731k = false;

    public FallDownAlgorithm(Context context) {
        this.f2725e = context;
    }

    /* renamed from: b */
    private void m2585b(int i, int i2, int i3, int i4) {
        this.f2728h = System.currentTimeMillis();
        if (!this.f2729i && i4 < 50) {
            Log.e("#####################", "free fall !!!!!!!!!!!");
            this.f2729i = true;
            this.f2726f = this.f2728h;
        }
        if (!this.f2730j && this.f2729i) {
            int i5 = (int) (this.f2728h - this.f2726f);
            if (i5 >= 400) {
                Log.e("#####################", "impact timeout !!!!!!!!!!!" + i5 + ":" + i4);
                this.f2729i = false;
                this.f2730j = false;
            } else if (i4 > 500) {
                Log.e("#####################", "impact !!!!!!!!!!!" + i5 + ":" + i4);
                this.f2730j = true;
                this.f2727g = this.f2728h;
                this.f2722b = i4;
                this.f2723c = i4;
            } else {
                Log.e("#####################", "impact < limit!!!!!!!!!!!" + i5 + ":" + i4);
            }
        }
        if (this.f2729i && this.f2730j) {
            int i6 = (int) (this.f2728h - this.f2727g);
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
//                    C0140d.m485a(this.f2725e).mo390a(new Intent("com.holptruly.part.FallDetection.FALLDOWN"));
                    LocalBroadcastManager.getInstance(f2725e).sendBroadcast(new Intent("com.holptruly.part.FallDetection.FALLDOWN"));
                    Log.e("#####################", "fall down !!!!!!!!!!!");
                    this.f2729i = false;
                    this.f2730j = false;
                    this.f2731k = false;
                    this.f2724d = 0;
                    return;
                }
                return;
            }
            this.f2729i = false;
            this.f2730j = false;
            this.f2731k = false;
            this.f2724d = 0;
            Log.e("#####################", "timeout!!!!!!!!!!!" + i6 + ":" + i4);
        }
    }

    /* renamed from: a */
    public void mo2448a(int i, int i2, int i3, int i4) {
        m2585b(i, i2, i3, i4);
    }
}
