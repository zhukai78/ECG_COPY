package com.warick.drawable.p028a;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.warick.drawable.WarickSurfaceView;


/* renamed from: com.warick.drawable.a.d */
public class HistoryRecordDrawListener implements WarickSurfaceView.DrawListener {

    /* renamed from: a */
    private static final String f3066a = "d";

    /* renamed from: b */
    private float[] f3067b = {0.0f, 0.0f};

    /* renamed from: c */
    private boolean[] f3068c = {false, false};

    /* renamed from: d */
    private String[] f3069d = {"", ""};

    /* renamed from: e */
    private Paint f3070e = new Paint();

    public HistoryRecordDrawListener() {
        this.f3070e.setColor(-16711936);
        this.f3070e.setTextSize(20.0f);
        this.f3070e.setTextAlign(Paint.Align.CENTER);
        this.f3070e.setStrokeWidth(3.0f);
        this.f3070e.setStyle(Paint.Style.STROKE);
    }

    /* renamed from: a */
    public void mo2927a(int i) {
        this.f3070e.setColor(i);
    }

    /* renamed from: a */
    public void mo2928a(int i, float f) {
        this.f3067b[i] = f;
    }

    /* renamed from: a */
    public void mo2929a(int i, String str) {
        this.f3069d[i] = str;
    }

    /* renamed from: a */
    public void mo2930a(int i, boolean z) {
        this.f3068c[i] = z;
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        if (this.f3068c[0]) {
            canvas.drawText(this.f3069d[0], this.f3067b[0], 30.0f, this.f3070e);
            canvas.drawLine(this.f3067b[0], 40.0f, this.f3067b[0], (float) (canvas.getHeight() - 20), this.f3070e);
        }
        if (this.f3068c[1]) {
            canvas.drawText(this.f3069d[1], this.f3067b[1], 30.0f, this.f3070e);
            canvas.drawLine(this.f3067b[1], 40.0f, this.f3067b[1], (float) (canvas.getHeight() - 20), this.f3070e);
        }
    }
}
