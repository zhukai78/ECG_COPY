package com.warick.drawable.drawlistener;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.warick.drawable.WarickSurfaceView;


/* renamed from: com.warick.drawable.a.a */
public class HisToryEcgProgressDrawListener implements WarickSurfaceView.DrawListener {

    /* renamed from: a */
    private static Paint progressDrawPaint;

    /* renamed from: b */
    private static float halfHeight;

    /* renamed from: c */
    private String f3041c;

    /* renamed from: d */
    private float f3042d;

    /* renamed from: e */
    private float f3043e;

    /* renamed from: f */
    private boolean isCanDrawEcgProgress = false;

    /* renamed from: a */
    private static void initDrawPaint(float f) {
        progressDrawPaint = new Paint();
        progressDrawPaint.setTextSize(40.0f);
        progressDrawPaint.setColor(-16711936);
        progressDrawPaint.setStrokeWidth(8.0f);
        progressDrawPaint.setTextAlign(Paint.Align.LEFT);
        halfHeight = f / 2.0f;
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        if (this.isCanDrawEcgProgress) {
            if (progressDrawPaint == null) {
                initDrawPaint((float) canvas.getHeight());
            }
            if (this.f3041c != null) {
                if (this.f3042d > ((float) ((canvas.getWidth() * 2) / 3))) {
                    progressDrawPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(this.f3041c, this.f3042d - 15.0f, (halfHeight - this.f3043e) - 15.0f, progressDrawPaint);
                    progressDrawPaint.setTextAlign(Paint.Align.LEFT);
                } else {
                    canvas.drawText(this.f3041c, this.f3042d + 15.0f, (halfHeight - this.f3043e) - 15.0f, progressDrawPaint);
                }
            }
            canvas.drawCircle(this.f3042d, halfHeight - this.f3043e, 8.0f, progressDrawPaint);
        }
    }

    /* renamed from: a */
    public void setDrawDatas(String str, float f, float f2) {
        this.f3041c = str;
        this.f3042d = f;
        this.f3043e = f2;
    }

    /* renamed from: a */
    public void setIsCanDrawEcgProgress(boolean z) {
        this.isCanDrawEcgProgress = z;
    }
}
