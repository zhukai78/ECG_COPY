package com.warick.drawable.drawlistener;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.warick.drawable.WarickSurfaceView;


/* renamed from: com.warick.drawable.a.d */
public class HistoryRecordDrawListener implements WarickSurfaceView.DrawListener {

    /* renamed from: a */
    private static final String f3066a = "d";

    /* renamed from: b */
    private float[] cutFloats = {0.0f, 0.0f};

    /* renamed from: c */
    private boolean[] cutBool = {false, false};

    /* renamed from: d */
    private String[] drawAText = {"", ""};

    /* renamed from: e */
    private Paint recordDrawPaint = new Paint();

    public HistoryRecordDrawListener() {
        this.recordDrawPaint.setColor(-16711936);
        this.recordDrawPaint.setTextSize(20.0f);
        this.recordDrawPaint.setTextAlign(Paint.Align.CENTER);
        this.recordDrawPaint.setStrokeWidth(3.0f);
        this.recordDrawPaint.setStyle(Paint.Style.STROKE);
    }

    /* renamed from: a */
    public void mo2927a(int i) {
        this.recordDrawPaint.setColor(i);
    }

    /* renamed from: a */
    public void setCutFloats(int i, float f) {
        this.cutFloats[i] = f;
    }

    /* renamed from: a */
    public void setDrawAText(int i, String str) {
        this.drawAText[i] = str;
    }

    /* renamed from: a */
    public void setCutsBool(int i, boolean z) {
        this.cutBool[i] = z;
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        if (this.cutBool[0]) {
            canvas.drawText(this.drawAText[0], this.cutFloats[0], 30.0f, this.recordDrawPaint);
            canvas.drawLine(this.cutFloats[0], 40.0f, this.cutFloats[0], (float) (canvas.getHeight() - 20), this.recordDrawPaint);
        }
        if (this.cutBool[1]) {
            canvas.drawText(this.drawAText[1], this.cutFloats[1], 30.0f, this.recordDrawPaint);
            canvas.drawLine(this.cutFloats[1], 40.0f, this.cutFloats[1], (float) (canvas.getHeight() - 20), this.recordDrawPaint);
        }
    }
}
