package com.warick.drawable.drawlistener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.hopetruly.ecg.util.EcgParserUtils;
import com.warick.drawable.WarickSurfaceView;
import com.warick.jni.filter.Fir;


/* renamed from: com.warick.drawable.a.c */
public class EcgCovertDrawListener implements WarickSurfaceView.DrawListener {

    /* renamed from: b */
    private static final String f3046b = "c";

    /* renamed from: a */
    boolean isFiter = true;

    /* renamed from: c */
    private float[] historyEcgs = null;

    /* renamed from: d */
    private float[] mRealEcgs = null;

    /* renamed from: e */
    private int mWitdh = 0;

    /* renamed from: f */
    private int f3051f;

    /* renamed from: g */
    private int f3052g = 0;

    /* renamed from: h */
    private Path f3053h;

    /* renamed from: i */
    private Path f3054i;

    /* renamed from: j */
    private int recordProgress = 0;

    /* renamed from: k */
    private int reverseMark = 1;

    /* renamed from: l */
    private int realHisMode = 0;

    /* renamed from: m */
    private float covertXFloat = 1.3f;

    /* renamed from: n */
    private float f3059n = 0.13f;

    /* renamed from: o */
    private float mheight;

    /* renamed from: p */
    private double[] f3061p;

    /* renamed from: q */
    private Path f3062q;

    /* renamed from: r */
    private int[] f3063r;

    /* renamed from: s */
    private EcgParserUtils mEcgParserUtils;

    /* renamed from: t */
    private boolean IsCancelCut = true;
    
    private static String TAG = "EcgCovertDrawListener";

    /* renamed from: b */
    private void m2951b(Canvas canvas, Paint paint) {
        Log.d(TAG, "m2951b:-------- ");
        this.f3053h.reset();
        this.f3053h.moveTo(0.0f, this.mheight - ((this.mRealEcgs[0] * this.f3059n) * ((float) this.reverseMark)));
        for (int i = 1; i < this.f3052g; i++) {
            this.f3053h.lineTo(mo2914b(i), this.mheight - (this.mRealEcgs[i] * this.f3059n));
        }
        canvas.drawPath(this.f3053h, paint);
    }

    /* renamed from: j */
    private void covertEcgLib() {
        Log.d(TAG, "covertEcgLib:------- ");
        if (this.mRealEcgs != null && this.historyEcgs != null) {
            int i = 0;
            int i2 = 0;
            while (i < this.f3051f && this.recordProgress + i < this.historyEcgs.length) {
                if (this.isFiter) {
                    if (i > Fir.getOrder(Fir.Fir_5) / 2) {
                        i2++;
                    }
                    this.mRealEcgs[i2] = ((float) Fir.RealtimeFir(this.historyEcgs[this.recordProgress + i], Fir.Fir_5, this.f3061p)) * ((float) this.reverseMark);
                } else {
                    this.mRealEcgs[i] = this.historyEcgs[this.recordProgress + i] * ((float) this.reverseMark);
                }
                i++;
            }
            if (this.isFiter) {
                int i3 = i2;
                for (int i4 = 0; i4 < Fir.getOrder(Fir.Fir_5) / 2; i4++) {
                    i3++;
                    this.mRealEcgs[i3] = ((float) Fir.RealtimeFir(0, Fir.Fir_5, this.f3061p)) * ((float) this.reverseMark);
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
    public void setEcg_scale_type(float f) {
        Log.d(TAG, "setEcg_scale_type: -----");
        if (this.mRealEcgs != null) {
            this.covertXFloat = 1.3f * f;
            this.f3059n = f * 0.13f;
            this.f3051f = ((int) (((float) this.mWitdh) / this.covertXFloat)) + 1;
            if (this.mRealEcgs != null && this.historyEcgs != null) {
                covertEcgLib();
            }
        }
    }

    public void setEcg_scale_type_special(float f) {
        if (this.mRealEcgs != null) {
            this.covertXFloat = 1.3f * f;
            this.f3059n = f * 0.13f * 0.5f;
            this.f3051f = ((int) (((float) this.mWitdh) / this.covertXFloat)) + 1;
            if (this.mRealEcgs != null && this.historyEcgs != null) {
                covertEcgLib();
            }
        }
    }

    /* renamed from: a */
    public void setRealHisMode(int i) {
        this.realHisMode = i;
    }

    /* renamed from: a */
    public void initdraw(int width, int high) {
        Log.d(TAG, "initdraw: -------");
        this.mheight = ((float) high) / 2.0f;
        this.mWitdh = width;
        this.mRealEcgs = new float[this.mWitdh];
        this.f3051f = ((int) (((float) this.mWitdh) / 1.3f)) + 1;
        resetEcgDatas();
        this.f3061p = new double[Fir.getOrder(Fir.Fir_5)];
        Fir.setDoubles_0(Fir.getOrder(Fir.Fir_5), this.f3061p);
        switch (this.realHisMode) {
            case 0:
                this.f3054i = new Path();
                break;
            case 1:
                break;
            default:
                return;
        }
        if (this.historyEcgs != null) {
            covertEcgLib();
        }
        this.f3053h = new Path();
        this.f3062q = new Path();
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        int i;
        if (canvas != null) {
            if (this.mRealEcgs == null) {
                initdraw(canvas.getWidth(), canvas.getHeight());
            } else if (this.realHisMode == 0) {
                this.f3053h.reset();
                this.f3054i.reset();
                this.f3053h.moveTo(0.0f, this.mheight - ((this.mRealEcgs[0] * this.f3059n) * ((float) this.reverseMark)));
//                Log.d(TAG, "onMyDraw: mheight's value: " + mheight);
                for (int i2 = 1; i2 < this.f3052g - 1; i2++) {
                    this.f3053h.lineTo(mo2914b(i2), mo2913b(this.mRealEcgs[i2]));
                    Log.d(TAG, "onMyDraw: f3049[i2]= " + mRealEcgs[i2]);
                    Log.d(TAG, "onMyDraw: mo2913b(mRealEcgs[i2])= " + mo2913b(mRealEcgs[i2]));
                }
                int i3 = this.f3052g + 20;
                this.f3054i.moveTo(((float) i3) * this.covertXFloat, this.mheight - ((this.mRealEcgs[i3] * this.f3059n) * ((float) this.reverseMark)));
                for (int i4 = i3 + 1; i4 < this.f3051f - 1; i4++) {
                    this.f3054i.lineTo(mo2914b(i4), mo2913b(this.mRealEcgs[i4]));
//                    Log.d(TAG, "onMyDraw: f3049[i4]= " + mRealEcgs[i4]);
                }
                canvas.drawPath(this.f3054i, paint);
                canvas.drawPath(this.f3053h, paint);
            } else {
//                Log.d(TAG, "onMyDraw: （3）");
                if (this.mEcgParserUtils != null && this.IsCancelCut) {
                    this.f3063r = this.mEcgParserUtils.mo2783b(this.recordProgress, this.recordProgress + this.f3051f);
                    if (this.f3063r != null) {
                        this.f3053h.reset();
                        this.f3062q.reset();
                        this.f3053h.moveTo(0.0f, this.mheight - (this.mRealEcgs[0] * this.f3059n));
                        for (int i5 = 1; i5 <= this.f3063r[0]; i5++) {
                            this.f3053h.lineTo(mo2914b(i5), this.mheight - (this.mRealEcgs[i5] * this.f3059n));
                        }
                        for (int i6 = 0; i6 < this.f3063r.length; i6 += 2) {
                            this.f3062q.moveTo(mo2914b(this.f3063r[i6]), this.mheight - (this.mRealEcgs[this.f3063r[i6]] * this.f3059n));
                            int i7 = this.f3063r[i6];
                            while (true) {
                                i = i6 + 1;
                                if (i7 >= this.f3063r[i]) {
                                    break;
                                }
                                this.f3062q.lineTo(mo2914b(i7), this.mheight - (this.mRealEcgs[i7] * this.f3059n));
                                i7++;
                            }
                            if (i6 < this.f3063r.length - 2) {
                                this.f3053h.moveTo(mo2914b(this.f3063r[i] - 1), this.mheight - (this.mRealEcgs[this.f3063r[i] - 1] * this.f3059n));
                                for (int i8 = this.f3063r[i] - 1; i8 <= this.f3063r[i6 + 2]; i8++) {
                                    this.f3053h.lineTo(mo2914b(i8), this.mheight - (this.mRealEcgs[i8] * this.f3059n));
                                }
                            }
                        }
                        this.f3053h.moveTo(mo2914b(this.f3063r[this.f3063r.length - 1] - 1), this.mheight - (this.mRealEcgs[this.f3063r[this.f3063r.length - 1] - 1] * this.f3059n));
                        for (int i9 = this.f3063r[this.f3063r.length - 1] - 1; i9 < this.f3052g; i9++) {
                            this.f3053h.lineTo(mo2914b(i9), this.mheight - (this.mRealEcgs[i9] * this.f3059n));
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
    public void setEcgParserUtils(EcgParserUtils hVar) {
        this.mEcgParserUtils = hVar;
    }

    /* renamed from: a */
    public void offEcg_filter(boolean z) {
        Log.d(TAG, "offEcg_filter: ------");
        this.isFiter = z;
        if (this.mRealEcgs != null && this.historyEcgs != null) {
            covertEcgLib();
        }
    }

    /* renamed from: a */
    public void setHistoryEcgs(float[] fArr) {
        this.historyEcgs = fArr;
    }

    /* renamed from: b */
    public float mo2913b(float f) {
//        Log.d(TAG, "mo2913b: mheight's value is:" + mheight);
//        Log.d(TAG, "mo2913b: ecg's value is:" + f);
//        Log.d(TAG, "mo2913b: f3059n's value is:" + f3059n);
//        Log.d(TAG, "mo2913b: reverseMark's value is:" + reverseMark);
        return this.mheight - ((f * this.f3059n) * ((float) this.reverseMark));
    }

    /* renamed from: b */
    public float mo2914b(int i) {
        return ((float) i) * this.covertXFloat;
    }

    /* renamed from: b */
    public int mo2915b() {
        return this.f3051f;
    }

    /* renamed from: c */
    public int mo2916c(int i) {
        if (this.historyEcgs == null) {
            return 0;
        }
        int i2 = this.recordProgress + ((int) (((float) i) / this.covertXFloat));
        return i2 > this.historyEcgs.length + -1 ? this.historyEcgs.length - 1 : i2;
    }

    /* renamed from: c */
    public void actionCancelCut() {
        this.IsCancelCut = true;
    }

    /* renamed from: c */
    public void mo2918c(float f) {
        Log.d(TAG, "mo2918c: =======");
        if (this.mRealEcgs != null) {
            this.f3052g++;
            this.f3052g %= this.f3051f;
            Log.d(TAG, "mo2918c: f3051f's value is " + f3051f);
//            if (this.isFiter) {
//                this.mRealEcgs[this.f3052g] = (float) Fir.RealtimeFir(f, Fir.Fir_5, this.f3061p);
//                Log.d("EcgCovertDrawListener", "what is the realtimeFir return? " + mRealEcgs[f3052g]);
//            } else {
//                this.mRealEcgs[this.f3052g] = f;
//            }
            mRealEcgs[f3052g] = (float) (f / 1000.0);
//            Log.d(TAG, "mo2918c: the ecg's value is：" + mRealEcgs[f3052g]);
        }else {
            Log.d(TAG, "mo2918c: mRealEcgs is null");
        }
    }

    /* renamed from: d */
    public float mo2919d(int i) {
        Log.d(TAG, "mo2919d: -------");
        if (i < this.recordProgress || this.mRealEcgs == null) {
            return 0.0f;
        }
        return this.mRealEcgs[i - this.recordProgress];
    }

    /* renamed from: d */
    public void mo2920d() {
        this.IsCancelCut = false;
        mo2921e();
    }

    /* renamed from: e */
    public void mo2921e() {
        this.f3063r = null;
    }

    /* renamed from: e */
    public void setRecordProgress(int i) {
        this.recordProgress = i;
        covertEcgLib();
    }

    /* renamed from: f */
    public boolean isFiterOpen() {
        return this.isFiter;
    }

    /* renamed from: g */
    public void reverseEcg() {
        this.reverseMark *= -1;
        if (this.mRealEcgs != null && this.historyEcgs != null) {
            covertEcgLib();
        }
    }

    /* renamed from: h */
    public int getReverseMark() {
        return this.reverseMark;
    }

    /* renamed from: i */
    public void resetEcgDatas() {
        Log.d(TAG, "resetEcgDatas: ---------");
        if (this.mRealEcgs != null) {
            for (int i = 0; i < this.mRealEcgs.length; i++) {
                this.mRealEcgs[i] = 5000.0f;
            }
            this.f3052g = 0;
        }
    }
}
