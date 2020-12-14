package com.hopetruly.ecg.algorithm;

import android.util.Log;

/* renamed from: com.hopetruly.ecg.algorithm.d */
public class C0738d {

    /* renamed from: a */
    public int f2754a = 0;

    /* renamed from: b */
    private float[] f2755b = {0.0f, 0.0f, 0.0f};

    /* renamed from: c */
    private int f2756c = 2;

    /* renamed from: d */
    private int f2757d = 3;

    /* renamed from: e */
    private float f2758e;

    /* renamed from: f */
    private float f2759f;

    /* renamed from: g */
    private boolean f2760g = false;

    /* renamed from: h */
    private boolean f2761h = false;

    /* renamed from: i */
    private float f2762i = 0.0f;

    /* renamed from: j */
    private int f2763j = 0;

    /* renamed from: a */
    private boolean m2603a() {
        if (this.f2760g) {
            m2605c();
            if (this.f2761h) {
                this.f2760g = false;
                this.f2761h = false;
                this.f2763j++;
                this.f2762i += (((this.f2759f + this.f2758e) / 2.0f) - this.f2762i) / ((float) this.f2763j);
                Log.d("StepCounter", "Max:" + this.f2758e + ", Min:" + this.f2759f + ", Avg:" + this.f2762i);
                return this.f2758e - this.f2759f >= 10.0f;
            }
        } else {
            m2604b();
        }
        return false;
    }

    /* renamed from: b */
    private void m2604b() {
        int i = ((this.f2756c + 3) - 2) % 3;
        int i2 = ((this.f2756c + 3) - 1) % 3;
        if (this.f2755b[((this.f2756c + 3) - 3) % 3] <= this.f2755b[i] && this.f2755b[i] > this.f2755b[i2] && this.f2755b[i] >= this.f2762i) {
            if (this.f2754a <= 1 || this.f2754a >= 5) {
                if (!this.f2760g) {
                    this.f2758e = this.f2755b[i];
                    this.f2760g = true;
                } else if (this.f2758e < this.f2755b[i]) {
                    this.f2758e = this.f2755b[i];
                } else {
                    return;
                }
                this.f2754a = 1;
            }
        }
    }

    /* renamed from: c */
    private void m2605c() {
        int i = ((this.f2756c + 3) - 2) % 3;
        int i2 = ((this.f2756c + 3) - 1) % 3;
        if (this.f2755b[((this.f2756c + 3) - 3) % 3] >= this.f2755b[i] && this.f2755b[i] < this.f2755b[i2]) {
            if (!this.f2761h) {
                this.f2759f = this.f2755b[i];
                this.f2761h = true;
            } else if (this.f2759f > this.f2755b[i]) {
                this.f2759f = this.f2755b[i];
            }
        }
    }

    /* renamed from: a */
    public boolean mo2463a(float f) {
        if (this.f2757d > 1) {
            this.f2755b[3 - this.f2757d] = f;
            this.f2757d--;
            return false;
        }
        this.f2755b[this.f2756c] = f;
        this.f2756c = (this.f2756c + 1) % 3;
        if (this.f2754a > 0) {
            this.f2754a++;
        }
        return m2603a();
    }
}
