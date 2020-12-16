package com.warick.drawable.p028a;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.warick.drawable.WarickSurfaceView;


/* renamed from: com.warick.drawable.a.a */
public class C0812a implements WarickSurfaceView.DrawListener {

    /* renamed from: a */
    private static Paint f3039a;

    /* renamed from: b */
    private static float f3040b;

    /* renamed from: c */
    private String f3041c;

    /* renamed from: d */
    private float f3042d;

    /* renamed from: e */
    private float f3043e;

    /* renamed from: f */
    private boolean f3044f = false;

    /* renamed from: a */
    private static void m2944a(float f) {
        f3039a = new Paint();
        f3039a.setTextSize(40.0f);
        f3039a.setColor(-16711936);
        f3039a.setStrokeWidth(8.0f);
        f3039a.setTextAlign(Paint.Align.LEFT);
        f3040b = f / 2.0f;
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        if (this.f3044f) {
            if (f3039a == null) {
                m2944a((float) canvas.getHeight());
            }
            if (this.f3041c != null) {
                if (this.f3042d > ((float) ((canvas.getWidth() * 2) / 3))) {
                    f3039a.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(this.f3041c, this.f3042d - 15.0f, (f3040b - this.f3043e) - 15.0f, f3039a);
                    f3039a.setTextAlign(Paint.Align.LEFT);
                } else {
                    canvas.drawText(this.f3041c, this.f3042d + 15.0f, (f3040b - this.f3043e) - 15.0f, f3039a);
                }
            }
            canvas.drawCircle(this.f3042d, f3040b - this.f3043e, 8.0f, f3039a);
        }
    }

    /* renamed from: a */
    public void mo2903a(String str, float f, float f2) {
        this.f3041c = str;
        this.f3042d = f;
        this.f3043e = f2;
    }

    /* renamed from: a */
    public void mo2904a(boolean z) {
        this.f3044f = z;
    }
}
