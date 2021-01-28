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
    private int covertXWitdh;

    /* renamed from: g */
    private int lastEcgCnt = 0;

    /* renamed from: h */
    private Path linePath;

    /* renamed from: i */
    private Path linePath2;

    /* renamed from: j */
    private int recordProgress = 0;

    /* renamed from: k */
    private int reverseMark = 1;

    /* renamed from: l */
    private int realHisMode = 0;

    /* renamed from: m */
    private float covertXFloat = 1.3f;

    /* renamed from: n */
    private float covertYFloat = 0.10f;

    /* renamed from: o */
    private float mheight;

    /* renamed from: p */
    private double[] realtimeFirDoubles;

    /* renamed from: q */
    private Path f3062q;

    /* renamed from: r */
    private int[] historyInts;

    /* renamed from: s */
    private EcgParserUtils mEcgParserUtils;

    /* renamed from: t */
    private boolean IsCancelCut = true;
    
    private static String TAG = "EcgCovertDrawListener";

    /* renamed from: b */
    private void drawMyPath(Canvas canvas, Paint paint) {
        Log.d(TAG, "drawMyPath:-------- ");
        this.linePath.reset();
        this.linePath.moveTo(0.0f, this.mheight - ((this.mRealEcgs[0] * this.covertYFloat) * ((float) this.reverseMark)));
        for (int i = 1; i < this.lastEcgCnt; i++) {
            this.linePath.lineTo(reverseXData(i), this.mheight - (this.mRealEcgs[i] * this.covertYFloat));
        }
        canvas.drawPath(this.linePath, paint);
    }

    /* renamed from: j */
    private void covertEcgLib() {
        Log.d(TAG, "covertEcgLib:------- ");
        if (this.mRealEcgs != null && this.historyEcgs != null) {
            int i = 0;
            int i2 = 0;
            while (i < this.covertXWitdh && this.recordProgress + i < this.historyEcgs.length) {
                if (this.isFiter) {
                    if (i > Fir.getOrder(Fir.Fir_5) / 2) {
                        i2++;
                    }
                    this.mRealEcgs[i2] = ((float) Fir.RealtimeFir(this.historyEcgs[this.recordProgress + i], Fir.Fir_5, this.realtimeFirDoubles)) * ((float) this.reverseMark);
                } else {
                    this.mRealEcgs[i] = this.historyEcgs[this.recordProgress + i] * ((float) this.reverseMark);
                }
                i++;
            }
            if (this.isFiter) {
                int i3 = i2;
                for (int i4 = 0; i4 < Fir.getOrder(Fir.Fir_5) / 2; i4++) {
                    i3++;
                    this.mRealEcgs[i3] = ((float) Fir.RealtimeFir(0, Fir.Fir_5, this.realtimeFirDoubles)) * ((float) this.reverseMark);
                }
            }
            this.lastEcgCnt = i;
        }
    }

    /* renamed from: a */
    public  float getCovertYFloat() {
        return this.covertYFloat;
    }

    /* renamed from: a */
    public void setEcg_scale_type(float f) {
        Log.d(TAG, "setEcg_scale_type: -----");
        if (this.mRealEcgs != null) {
            this.covertXFloat = 1.3f * f;
            this.covertYFloat = f * 0.13f;
            this.covertXWitdh = ((int) (((float) this.mWitdh) / this.covertXFloat)) + 1;
            if (this.mRealEcgs != null && this.historyEcgs != null) {
                covertEcgLib();
            }
        }
    }

    public void setEcg_scale_type_special(float f) {
        if (this.mRealEcgs != null) {
            this.covertXFloat = 1.3f * f;
            this.covertYFloat = f * 0.13f * 0.5f;
            this.covertXWitdh = ((int) (((float) this.mWitdh) / this.covertXFloat)) + 1;
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
        this.covertXWitdh = ((int) (((float) this.mWitdh) / 1.3f)) + 1;
        resetEcgDatas();
        this.realtimeFirDoubles = new double[Fir.getOrder(Fir.Fir_5)];
        Fir.setDoubles_0(Fir.getOrder(Fir.Fir_5), this.realtimeFirDoubles);
        switch (this.realHisMode) {
            case 0:
                this.linePath2 = new Path();
                break;
            case 1:
                break;
            default:
                return;
        }
        if (this.historyEcgs != null) {
            covertEcgLib();
        }
        this.linePath = new Path();
        this.f3062q = new Path();
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        int i;
        if (canvas != null) {
            if (this.mRealEcgs == null) {
                initdraw(canvas.getWidth(), canvas.getHeight());
            } else if (this.realHisMode == 0) {
                //实时模式
                this.linePath.reset();
                this.linePath2.reset();
                this.linePath.moveTo(0.0f, this.mheight - ((this.mRealEcgs[0] * this.covertYFloat) * ((float) this.reverseMark)));
//                Log.d(TAG, "onMyDraw: mheight's value: " + mheight);
                for (int i2 = 1; i2 < this.lastEcgCnt - 1; i2++) {
                    this.linePath.lineTo(reverseXData(i2), reverseYData(this.mRealEcgs[i2]));
                    Log.d(TAG, "onMyDraw: f3049[i2]= " + mRealEcgs[i2]);
                    Log.d(TAG, "onMyDraw: reverseYData(mRealEcgs[i2])= " + reverseYData(mRealEcgs[i2]));
                }
                int i3 = this.lastEcgCnt + 20;
                this.linePath2.moveTo(((float) i3) * this.covertXFloat, this.mheight - ((this.mRealEcgs[i3] * this.covertYFloat) * ((float) this.reverseMark)));
                for (int i4 = i3 + 1; i4 < this.covertXWitdh - 1; i4++) {
                    this.linePath2.lineTo(reverseXData(i4), reverseYData(this.mRealEcgs[i4]));
//                    Log.d(TAG, "onMyDraw: f3049[i4]= " + mRealEcgs[i4]);
                }
                canvas.drawPath(this.linePath2, paint);
                canvas.drawPath(this.linePath, paint);
            } else {
                //历史模式
//                Log.d(TAG, "onMyDraw: （3）");
                if (this.mEcgParserUtils != null && this.IsCancelCut) {
                    this.historyInts = this.mEcgParserUtils.mo2783b(this.recordProgress, this.recordProgress + this.covertXWitdh);
                    if (this.historyInts != null) {
                        this.linePath.reset();
                        this.f3062q.reset();
                        this.linePath.moveTo(0.0f, this.mheight - (this.mRealEcgs[0] * this.covertYFloat));
                        for (int i5 = 1; i5 <= this.historyInts[0]; i5++) {
                            this.linePath.lineTo(reverseXData(i5), this.mheight - (this.mRealEcgs[i5] * this.covertYFloat));
                        }
                        for (int i6 = 0; i6 < this.historyInts.length; i6 += 2) {
                            this.f3062q.moveTo(reverseXData(this.historyInts[i6]), this.mheight - (this.mRealEcgs[this.historyInts[i6]] * this.covertYFloat));
                            int i7 = this.historyInts[i6];
                            while (true) {
                                i = i6 + 1;
                                if (i7 >= this.historyInts[i]) {
                                    break;
                                }
                                this.f3062q.lineTo(reverseXData(i7), this.mheight - (this.mRealEcgs[i7] * this.covertYFloat));
                                i7++;
                            }
                            if (i6 < this.historyInts.length - 2) {
                                this.linePath.moveTo(reverseXData(this.historyInts[i] - 1), this.mheight - (this.mRealEcgs[this.historyInts[i] - 1] * this.covertYFloat));
                                for (int i8 = this.historyInts[i] - 1; i8 <= this.historyInts[i6 + 2]; i8++) {
                                    this.linePath.lineTo(reverseXData(i8), this.mheight - (this.mRealEcgs[i8] * this.covertYFloat));
                                }
                            }
                        }
                        this.linePath.moveTo(reverseXData(this.historyInts[this.historyInts.length - 1] - 1), this.mheight - (this.mRealEcgs[this.historyInts[this.historyInts.length - 1] - 1] * this.covertYFloat));
                        for (int i9 = this.historyInts[this.historyInts.length - 1] - 1; i9 < this.lastEcgCnt; i9++) {
                            this.linePath.lineTo(reverseXData(i9), this.mheight - (this.mRealEcgs[i9] * this.covertYFloat));
                        }
                        Paint paint2 = new Paint();
                        paint2.set(paint);
                        paint2.setColor(Color.rgb(255, 165, 0));
                        canvas.drawPath(this.linePath, paint);
                        canvas.drawPath(this.f3062q, paint2);
                        return;
                    }
                }
                drawMyPath(canvas, paint);
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
    public float reverseYData(float f) {
//        Log.d(TAG, "reverseYData: mheight's value is:" + mheight);
//        Log.d(TAG, "reverseYData: ecg's value is:" + f);
//        Log.d(TAG, "reverseYData: covertYFloat's value is:" + covertYFloat);
//        Log.d(TAG, "reverseYData: reverseMark's value is:" + reverseMark);
        return this.mheight - ((f * this.covertYFloat) * ((float) this.reverseMark));
    }

    /* renamed from: b */
    public float reverseXData(int i) {
        return ((float) i) * this.covertXFloat;
    }

    /* renamed from: b */
    public int getCovertXWitdh() {
        return this.covertXWitdh;
    }

    /* renamed from: c */
    public int getEcgIndexMs(int i) {
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
    public void putRealEcgData(float f) {
        Log.d(TAG, "putRealEcgData: =======");
        if (this.mRealEcgs != null) {
            this.lastEcgCnt++;
            this.lastEcgCnt %= this.covertXWitdh;
            Log.d(TAG, "putRealEcgData: covertXWitdh's value is " + covertXWitdh);
//            if (this.isFiter) {
//                this.mRealEcgs[this.lastEcgCnt] = (float) Fir.RealtimeFir(f, Fir.Fir_5, this.realtimeFirDoubles);
//                Log.d("EcgCovertDrawListener", "what is the realtimeFir return? " + mRealEcgs[lastEcgCnt]);
//            } else {
//                this.mRealEcgs[this.lastEcgCnt] = f;
//            }
            mRealEcgs[lastEcgCnt] = (float) (f / 1000.0);
//            Log.d(TAG, "putRealEcgData: the ecg's value is：" + mRealEcgs[lastEcgCnt]);
        }else {
            Log.d(TAG, "putRealEcgData: mRealEcgs is null");
        }
    }

    /* renamed from: d */
    public float getEcgMv(int i) {
        Log.d(TAG, "getEcgMv: -------");
        if (i < this.recordProgress || this.mRealEcgs == null) {
            return 0.0f;
        }
        return this.mRealEcgs[i - this.recordProgress];
    }

    /* renamed from: d */
    public void setRevModel() {
        this.IsCancelCut = false;
        clearHistoryInts();
    }

    /* renamed from: e */
    public void clearHistoryInts() {
        this.historyInts = null;
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
            this.lastEcgCnt = 0;
        }
    }
}
