package com.hopetruly.ecg.algorithm;

import android.content.Context;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.util.C0765a;
import com.hopetruly.ecg.util.C0766b;

import java.sql.Date;

/* renamed from: com.hopetruly.ecg.algorithm.b */
public class C0736b {

    /* renamed from: c */
    private static int f2732c = 10;

    /* renamed from: d */
    private static int f2733d = 60;

    /* renamed from: a */
    ECGApplication f2734a;

    /* renamed from: b */
    C0765a f2735b;

    /* renamed from: e */
    private int f2736e = f2733d;

    /* renamed from: f */
    private Context f2737f;

    /* renamed from: g */
    private int f2738g = 0;

    /* renamed from: h */
    private int[] f2739h = {70, 70, 70, 70, 70, 70, 70, 70, 70, 70};

    /* renamed from: i */
    private long f2740i = 0;

    /* renamed from: j */
    private long f2741j = 0;

    /* renamed from: k */
    private boolean f2742k;

    /* renamed from: l */
    private boolean f2743l;

    public C0736b(Context context) {
        this.f2737f = context;
        this.f2734a = (ECGApplication) this.f2737f.getApplicationContext();
        this.f2735b = new C0765a(this.f2737f);
        this.f2742k = false;
        this.f2743l = false;
    }

    /* renamed from: k */
    private void m2587k() {
        if (!this.f2742k) {
            switch (this.f2734a.f2085f.mo2656h()) {
                case 0:
                    C0766b.m2755a(this.f2737f, 1, 100);
                    break;
                case 1:
                    break;
                case 2:
                    C0766b.m2755a(this.f2737f, 1, 100);
                    break;
            }
            if (this.f2735b.mo2768e()) {
                this.f2735b.mo2767d();
            } else {
                this.f2735b.mo2764a(-1);
            }
            this.f2742k = true;
        }
    }

    /* renamed from: a */
    public void mo2449a() {
        if (this.f2742k) {
            switch (this.f2734a.f2085f.mo2656h()) {
                case 0:
                    C0766b.m2754a();
                    break;
                case 1:
                    break;
                case 2:
                    C0766b.m2754a();
                    break;
            }
            this.f2735b.mo2766c();
            this.f2742k = false;
        }
    }

    /* renamed from: a */
    public void mo2450a(int i) {
        if (i == 0) {
            mo2449a();
            return;
        }
        this.f2739h[mo2458i()] = i;
        if (mo2457h()) {
            Date date = new Date(System.currentTimeMillis());
            if (this.f2740i == 0) {
                this.f2740i = date.getTime();
            } else {
                this.f2741j = date.getTime();
            }
            if (this.f2740i != 0 && this.f2741j != 0 && this.f2741j - this.f2740i >= ((long) (this.f2734a.f2085f.mo2664l() * 1000))) {
                this.f2740i = 0;
                this.f2741j = 0;
                m2587k();
                return;
            }
            return;
        }
        this.f2740i = 0;
        this.f2741j = 0;
        mo2449a();
    }

    /* renamed from: b */
    public boolean mo2451b() {
        return this.f2734a.f2085f.mo2655g();
    }

    /* renamed from: c */
    public void mo2452c() {
        mo2449a();
        this.f2743l = true;
        this.f2740i = 0;
        this.f2741j = 0;
    }

    /* renamed from: d */
    public void mo2453d() {
        this.f2743l = false;
    }

    /* renamed from: e */
    public void mo2454e() {
        if (this.f2743l) {
            this.f2736e--;
            if (this.f2736e == 0) {
                this.f2743l = false;
                this.f2736e = f2733d;
            }
        }
    }

    /* renamed from: f */
    public boolean mo2455f() {
        return this.f2743l;
    }

    /* renamed from: g */
    public boolean mo2456g() {
        return this.f2742k;
    }

    /* renamed from: h */
    public boolean mo2457h() {
        if (!this.f2734a.f2085f.mo2655g() || this.f2743l) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < f2732c; i3++) {
            if (this.f2739h[i3] > this.f2734a.f2085f.mo2650e()) {
                i++;
            }
            if (this.f2739h[i3] < this.f2734a.f2085f.mo2652f()) {
                i2++;
            }
        }
        return ((double) i) > ((double) f2732c) * 0.7d || ((double) i2) > ((double) f2732c) * 0.7d;
    }

    /* renamed from: i */
    public int mo2458i() {
        if (this.f2738g >= f2732c) {
            this.f2738g = 0;
            return 0;
        }
        int i = this.f2738g;
        this.f2738g = i + 1;
        return i;
    }

    /* renamed from: j */
    public void mo2459j() {
        mo2449a();
        this.f2735b.mo2765b();
    }
}
