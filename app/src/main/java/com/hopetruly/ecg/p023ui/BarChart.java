package com.hopetruly.ecg.p023ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hopetruly.ecg.util.DpUtil;
import com.hopetruly.ecg.util.LogUtils;

import java.text.DecimalFormat;

/* renamed from: com.hopetruly.ecg.ui.BarChart */
public class BarChart extends View {

    /* renamed from: a */
    final String f2880a;

    /* renamed from: b */
    Context f2881b;

    /* renamed from: c */
    DecimalFormat f2882c;

    /* renamed from: d */
    Paint f2883d;

    /* renamed from: e */
    Paint f2884e;

    /* renamed from: f */
    Paint f2885f;

    /* renamed from: g */
    Paint f2886g;

    /* renamed from: h */
    private int f2887h;

    /* renamed from: i */
    private int f2888i;

    /* renamed from: j */
    private int f2889j;

    /* renamed from: k */
    private int f2890k;

    /* renamed from: l */
    private String[] f2891l;

    /* renamed from: m */
    private int[] f2892m;

    /* renamed from: com.hopetruly.ecg.ui.BarChart$a */
    class C0763a {

        /* renamed from: a */
        int f2893a;

        /* renamed from: b */
        int f2894b;

        public C0763a(int i, int i2) {
            this.f2893a = i;
            this.f2894b = i2;
        }
    }

    public BarChart(Context context) {
        this(context, (AttributeSet) null);
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f2880a = "BarChart";
        this.f2881b = context;
        this.f2883d = new Paint(1);
        this.f2883d.setColor(-12303292);
        this.f2883d.setStrokeWidth(2.0f);
        this.f2883d.setStyle(Paint.Style.STROKE);
        this.f2884e = new Paint(1);
        this.f2884e.setStyle(Paint.Style.STROKE);
        this.f2884e.setColor(-12303292);
        this.f2884e.setStrokeWidth(2.0f);
        this.f2884e.setPathEffect(new DashPathEffect(new float[]{10.0f, 10.0f}, 1.0f));
        this.f2885f = new Paint(1);
        this.f2885f.setStyle(Paint.Style.FILL_AND_STROKE);
        this.f2885f.setColor(-16776961);
        this.f2886g = new Paint(1);
        this.f2886g.setColor(-16777216);
        this.f2886g.setTextSize((float) DpUtil.toDp(context, 14.0f));
        this.f2886g.setTextAlign(Paint.Align.RIGHT);
        mo2757a(0, 6211, 5);
        mo2758a(7, new String[]{"1", "2", "3", "4", "5", "6", "7"});
        mo2759a(new int[]{233, 400, 323, 1523, 456, 2000, 345});
        this.f2882c = new DecimalFormat("0.0");
    }

    /* renamed from: a */
    public void mo2757a(int i, int i2, int i3) {
        this.f2887h = i;
        this.f2888i = i2;
        this.f2889j = i3;
    }

    /* renamed from: a */
    public void mo2758a(int i, String[] strArr) {
        if (i != strArr.length) {
            throw new IllegalArgumentException("数据长度与设定的x轴数据长度不一致");
        }
        this.f2890k = i;
        this.f2891l = strArr;
    }

    /* renamed from: a */
    public void mo2759a(int[] iArr) {
        if (iArr.length > this.f2891l.length) {
            throw new IllegalArgumentException("数据长度与设定的x轴数据长度不一致");
        }
        this.f2892m = iArr;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        LogUtils.logE("BarChart", "height:" + height + "   width:" + width);
        Paint paint = this.f2886g;
        StringBuilder sb = new StringBuilder();
        sb.append(this.f2888i);
        sb.append("");
        int measureText = (int) paint.measureText(sb.toString());
        int i2 = measureText + 30;
        C0763a aVar = new C0763a(i2, height - DpUtil.toDp(this.f2881b, 50.0f));
        C0763a aVar2 = new C0763a(width - DpUtil.toDp(this.f2881b, 20.0f), height - DpUtil.toDp(this.f2881b, 50.0f));
        C0763a aVar3 = new C0763a(i2, DpUtil.toDp(this.f2881b, 20.0f));
        C0763a aVar4 = new C0763a(width - DpUtil.toDp(this.f2881b, 20.0f), DpUtil.toDp(this.f2881b, 20.0f));
        canvas2.drawLine((float) aVar.f2893a, (float) aVar.f2894b, (float) aVar2.f2893a, (float) aVar2.f2894b, this.f2883d);
        canvas2.drawLine((float) aVar.f2893a, (float) aVar.f2894b, (float) aVar3.f2893a, (float) aVar3.f2894b, this.f2883d);
        canvas2.drawLine((float) aVar3.f2893a, (float) aVar3.f2894b, (float) aVar4.f2893a, (float) aVar4.f2894b, this.f2883d);
        canvas2.drawLine((float) aVar2.f2893a, (float) aVar2.f2894b, (float) aVar4.f2893a, (float) aVar4.f2894b, this.f2883d);
        int abs = Math.abs(aVar.f2894b - aVar3.f2894b) / (this.f2889j - 1);
        int i3 = (this.f2888i - this.f2887h) / (this.f2889j - 1);
        int i4 = 0;
        while (i4 < this.f2889j) {
            if (i4 == 0 || i4 == this.f2889j - 1) {
                i = i4;
            } else {
                float f = (float) aVar.f2893a;
                int i5 = i4 * abs;
                float f2 = (float) (aVar.f2894b - i5);
                float f3 = (float) aVar2.f2893a;
                float f4 = (float) (aVar.f2894b - i5);
                float f5 = f2;
                float f6 = f3;
                float f7 = f4;
                i = i4;
                canvas2.drawLine(f, f5, f6, f7, this.f2884e);
            }
            canvas2.drawText(this.f2882c.format((double) (((float) (i * i3)) / 1000.0f)) + "k", (float) (measureText + 20), (float) ((aVar.f2894b - (i * abs)) + DpUtil.toDp(this.f2881b, 7.0f)), this.f2886g);
            i4 = i + 1;
        }
        int abs2 = Math.abs(aVar.f2893a - aVar2.f2893a) / this.f2890k;
        for (int i6 = 0; i6 < this.f2890k; i6++) {
            canvas2.drawText(this.f2891l[i6], (float) (aVar.f2893a + (abs2 / 2) + (i6 * abs2)), (float) (aVar.f2894b + DpUtil.toDp(this.f2881b, 20.0f)), this.f2886g);
        }
        for (int i7 = 0; i7 < this.f2892m.length; i7++) {
            int i8 = i7 * abs2;
            canvas2.drawRect(new Rect(aVar.f2893a + i8 + DpUtil.toDp(this.f2881b, 5.0f), aVar.f2894b - ((Math.abs(aVar.f2894b - aVar3.f2894b) * this.f2892m[i7]) / this.f2888i), ((aVar.f2893a + i8) + abs2) - DpUtil.toDp(this.f2881b, 5.0f), aVar.f2894b), this.f2885f);
        }
    }
}
