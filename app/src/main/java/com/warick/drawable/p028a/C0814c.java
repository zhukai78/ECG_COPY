package com.warick.drawable.p028a;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.hopetruly.ecg.util.EcgParserUtils;
import com.warick.drawable.WarickSurfaceView;
import com.warick.jni.filter.Fir;


/* renamed from: com.warick.drawable.a.c */
public class C0814c implements WarickSurfaceView.C0809a {

    /* renamed from: b */
    private static final String f3046b = "c";

    /* renamed from: a */
    boolean f3047a = true;

    /* renamed from: c */
    private float[] f3048c = null;

    /* renamed from: d */
    private float[] f3049d = null;

    /* renamed from: e */
    private int f3050e = 0;

    /* renamed from: f */
    private int f3051f;

    /* renamed from: g */
    private int f3052g = 0;

    /* renamed from: h */
    private Path f3053h;

    /* renamed from: i */
    private Path f3054i;

    /* renamed from: j */
    private int f3055j = 0;

    /* renamed from: k */
    private int f3056k = 1;

    /* renamed from: l */
    private int f3057l = 0;

    /* renamed from: m */
    private float f3058m = 1.3f;

    /* renamed from: n */
    private float f3059n = 0.13f;

    /* renamed from: o */
    private float f3060o;

    /* renamed from: p */
    private double[] f3061p;

    /* renamed from: q */
    private Path f3062q;

    /* renamed from: r */
    private int[] f3063r;

    /* renamed from: s */
    private EcgParserUtils f3064s;

    /* renamed from: t */
    private boolean f3065t = true;
    
    private static String TAG = "C0814C";

    /* renamed from: b */
    private void m2951b(Canvas canvas, Paint paint) {
        Log.d(TAG, "m2951b:-------- ");
        this.f3053h.reset();
        this.f3053h.moveTo(0.0f, this.f3060o - ((this.f3049d[0] * this.f3059n) * ((float) this.f3056k)));
        for (int i = 1; i < this.f3052g; i++) {
            this.f3053h.lineTo(mo2914b(i), this.f3060o - (this.f3049d[i] * this.f3059n));
        }
        canvas.drawPath(this.f3053h, paint);
    }

    /* renamed from: j */
    private void m2952j() {
        Log.d(TAG, "m2952j:------- ");
        if (this.f3049d != null && this.f3048c != null) {
            int i = 0;
            int i2 = 0;
            while (i < this.f3051f && this.f3055j + i < this.f3048c.length) {
                if (this.f3047a) {
                    if (i > Fir.getOrder(Fir.f3076f) / 2) {
                        i2++;
                    }
                    this.f3049d[i2] = ((float) Fir.RealtimeFir(this.f3048c[this.f3055j + i], Fir.f3076f, this.f3061p)) * ((float) this.f3056k);
                } else {
                    this.f3049d[i] = this.f3048c[this.f3055j + i] * ((float) this.f3056k);
                }
                i++;
            }
            if (this.f3047a) {
                int i3 = i2;
                for (int i4 = 0; i4 < Fir.getOrder(Fir.f3076f) / 2; i4++) {
                    i3++;
                    this.f3049d[i3] = ((float) Fir.RealtimeFir(0, Fir.f3076f, this.f3061p)) * ((float) this.f3056k);
                }
            }
            this.f3052g = i;
        }
    }

    /* renamed from: a */
    public float mo2906a() {
        return this.f3059n;
    }

    /* renamed from: a */
    public void mo2907a(float f) {
        Log.d(TAG, "mo2907a: -----");
        if (this.f3049d != null) {
            this.f3058m = 1.3f * f;
            this.f3059n = f * 0.13f;
            this.f3051f = ((int) (((float) this.f3050e) / this.f3058m)) + 1;
            if (this.f3049d != null && this.f3048c != null) {
                m2952j();
            }
        }
    }

    public void specialMo2907a(float f) {
        if (this.f3049d != null) {
            this.f3058m = 1.3f * f;
            this.f3059n = f * 0.13f * 0.5f;
            this.f3051f = ((int) (((float) this.f3050e) / this.f3058m)) + 1;
            if (this.f3049d != null && this.f3048c != null) {
                m2952j();
            }
        }
    }

    /* renamed from: a */
    public void mo2908a(int i) {
        this.f3057l = i;
    }

    /* renamed from: a */
    public void mo2909a(int width, int high) {
        Log.d(TAG, "mo2909a: -------");
        this.f3060o = ((float) high) / 2.0f;
        this.f3050e = width;
        this.f3049d = new float[this.f3050e];
        this.f3051f = ((int) (((float) this.f3050e) / 1.3f)) + 1;
        mo2926i();
        this.f3061p = new double[Fir.getOrder(Fir.f3076f)];
        Fir.m2980a(Fir.getOrder(Fir.f3076f), this.f3061p);
        switch (this.f3057l) {
            case 0:
                this.f3054i = new Path();
                break;
            case 1:
                break;
            default:
                return;
        }
        if (this.f3048c != null) {
            m2952j();
        }
        this.f3053h = new Path();
        this.f3062q = new Path();
    }

    /* renamed from: a */
    public void mo2900a(Canvas canvas, Paint paint) {
        int i;
        if (canvas != null) {
            if (this.f3049d == null) {
                mo2909a(canvas.getWidth(), canvas.getHeight());
            } else if (this.f3057l == 0) {
                this.f3053h.reset();
                this.f3054i.reset();
                this.f3053h.moveTo(0.0f, this.f3060o - ((this.f3049d[0] * this.f3059n) * ((float) this.f3056k)));
//                Log.d(TAG, "mo2900a: f3060o's value: " + f3060o);
                for (int i2 = 1; i2 < this.f3052g - 1; i2++) {
                    this.f3053h.lineTo(mo2914b(i2), mo2913b(this.f3049d[i2]));
                    Log.d(TAG, "mo2900a: f3049[i2]= " + f3049d[i2]);
                    Log.d(TAG, "mo2900a: mo2913b(f3049d[i2])= " + mo2913b(f3049d[i2]));
                }
                int i3 = this.f3052g + 20;
                this.f3054i.moveTo(((float) i3) * this.f3058m, this.f3060o - ((this.f3049d[i3] * this.f3059n) * ((float) this.f3056k)));
                for (int i4 = i3 + 1; i4 < this.f3051f - 1; i4++) {
                    this.f3054i.lineTo(mo2914b(i4), mo2913b(this.f3049d[i4]));
//                    Log.d(TAG, "mo2900a: f3049[i4]= " + f3049d[i4]);
                }
                canvas.drawPath(this.f3054i, paint);
                canvas.drawPath(this.f3053h, paint);
            } else {
//                Log.d(TAG, "mo2900a: （3）");
                if (this.f3064s != null && this.f3065t) {
                    this.f3063r = this.f3064s.mo2783b(this.f3055j, this.f3055j + this.f3051f);
                    if (this.f3063r != null) {
                        this.f3053h.reset();
                        this.f3062q.reset();
                        this.f3053h.moveTo(0.0f, this.f3060o - (this.f3049d[0] * this.f3059n));
                        for (int i5 = 1; i5 <= this.f3063r[0]; i5++) {
                            this.f3053h.lineTo(mo2914b(i5), this.f3060o - (this.f3049d[i5] * this.f3059n));
                        }
                        for (int i6 = 0; i6 < this.f3063r.length; i6 += 2) {
                            this.f3062q.moveTo(mo2914b(this.f3063r[i6]), this.f3060o - (this.f3049d[this.f3063r[i6]] * this.f3059n));
                            int i7 = this.f3063r[i6];
                            while (true) {
                                i = i6 + 1;
                                if (i7 >= this.f3063r[i]) {
                                    break;
                                }
                                this.f3062q.lineTo(mo2914b(i7), this.f3060o - (this.f3049d[i7] * this.f3059n));
                                i7++;
                            }
                            if (i6 < this.f3063r.length - 2) {
                                this.f3053h.moveTo(mo2914b(this.f3063r[i] - 1), this.f3060o - (this.f3049d[this.f3063r[i] - 1] * this.f3059n));
                                for (int i8 = this.f3063r[i] - 1; i8 <= this.f3063r[i6 + 2]; i8++) {
                                    this.f3053h.lineTo(mo2914b(i8), this.f3060o - (this.f3049d[i8] * this.f3059n));
                                }
                            }
                        }
                        this.f3053h.moveTo(mo2914b(this.f3063r[this.f3063r.length - 1] - 1), this.f3060o - (this.f3049d[this.f3063r[this.f3063r.length - 1] - 1] * this.f3059n));
                        for (int i9 = this.f3063r[this.f3063r.length - 1] - 1; i9 < this.f3052g; i9++) {
                            this.f3053h.lineTo(mo2914b(i9), this.f3060o - (this.f3049d[i9] * this.f3059n));
                        }
                        Paint paint2 = new Paint();
                        paint2.set(paint);
                        paint2.setColor(Color.rgb(255, 165, 0));
                        canvas.drawPath(this.f3053h, paint);
                        canvas.drawPath(this.f3062q, paint2);
                        return;
                    }
                }
                m2951b(canvas, paint);
            }
        }
    }

    /* renamed from: a */
    public void mo2910a(EcgParserUtils hVar) {
        this.f3064s = hVar;
    }

    /* renamed from: a */
    public void mo2911a(boolean z) {
        Log.d(TAG, "mo2911a: ------");
        this.f3047a = z;
        if (this.f3049d != null && this.f3048c != null) {
            m2952j();
        }
    }

    /* renamed from: a */
    public void mo2912a(float[] fArr) {
        this.f3048c = fArr;
    }

    /* renamed from: b */
    public float mo2913b(float f) {
//        Log.d(TAG, "mo2913b: f3060o's value is:" + f3060o);
//        Log.d(TAG, "mo2913b: ecg's value is:" + f);
//        Log.d(TAG, "mo2913b: f3059n's value is:" + f3059n);
//        Log.d(TAG, "mo2913b: f3056k's value is:" + f3056k);
        return this.f3060o - ((f * this.f3059n) * ((float) this.f3056k));
    }

    /* renamed from: b */
    public float mo2914b(int i) {
        return ((float) i) * this.f3058m;
    }

    /* renamed from: b */
    public int mo2915b() {
        return this.f3051f;
    }

    /* renamed from: c */
    public int mo2916c(int i) {
        if (this.f3048c == null) {
            return 0;
        }
        int i2 = this.f3055j + ((int) (((float) i) / this.f3058m));
        return i2 > this.f3048c.length + -1 ? this.f3048c.length - 1 : i2;
    }

    /* renamed from: c */
    public void mo2917c() {
        this.f3065t = true;
    }

    /* renamed from: c */
    public void mo2918c(float f) {
        Log.d(TAG, "mo2918c: =======");
        if (this.f3049d != null) {
            this.f3052g++;
            this.f3052g %= this.f3051f;
            Log.d(TAG, "mo2918c: f3051f's value is " + f3051f);
//            if (this.f3047a) {
//                this.f3049d[this.f3052g] = (float) Fir.RealtimeFir(f, Fir.f3076f, this.f3061p);
//                Log.d("C0814C", "what is the realtimeFir return? " + f3049d[f3052g]);
//            } else {
//                this.f3049d[this.f3052g] = f;
//            }
            f3049d[f3052g] = (float) (f / 1000.0);
//            Log.d(TAG, "mo2918c: the ecg's value is：" + f3049d[f3052g]);
        }else {
            Log.d(TAG, "mo2918c: f3049d is null");
        }
    }

    /* renamed from: d */
    public float mo2919d(int i) {
        Log.d(TAG, "mo2919d: -------");
        if (i < this.f3055j || this.f3049d == null) {
            return 0.0f;
        }
        return this.f3049d[i - this.f3055j];
    }

    /* renamed from: d */
    public void mo2920d() {
        this.f3065t = false;
        mo2921e();
    }

    /* renamed from: e */
    public void mo2921e() {
        this.f3063r = null;
    }

    /* renamed from: e */
    public void mo2922e(int i) {
        this.f3055j = i;
        m2952j();
    }

    /* renamed from: f */
    public boolean mo2923f() {
        return this.f3047a;
    }

    /* renamed from: g */
    public void mo2924g() {
        this.f3056k *= -1;
        if (this.f3049d != null && this.f3048c != null) {
            m2952j();
        }
    }

    /* renamed from: h */
    public int mo2925h() {
        return this.f3056k;
    }

    /* renamed from: i */
    public void mo2926i() {
        Log.d(TAG, "mo2926i: ---------");
        if (this.f3049d != null) {
            for (int i = 0; i < this.f3049d.length; i++) {
                this.f3049d[i] = 5000.0f;
            }
            this.f3052g = 0;
        }
    }
}
