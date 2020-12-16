package com.warick.drawable.p028a;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.warick.drawable.WarickSurfaceView;


/* renamed from: com.warick.drawable.a.b */
public class GrideDrawListener implements WarickSurfaceView.DrawListener {

    /* renamed from: a */
    private Bitmap mdrawBitMap = null;

    /* renamed from: a */
    private void drawGride(Canvas canvas) {
        float f;
        float f2;
        float f3;
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int i = height / 2;
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint2.setColor(Color.rgb(85, 235, 243));
        this.mdrawBitMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas2 = new Canvas(this.mdrawBitMap);
        canvas2.drawColor(-1);
        int i2 = 0;
        float f4 = (float) i;
        int i3 = 0;
        while (true) {
            f = 1.0f;
            f2 = 3.0f;
            f3 = 0.0f;
            if (i >= height) {
                break;
            }
            if (((float) i3) % 5.0f == 0.0f) {
                paint2.setStrokeWidth(3.0f);
            } else {
                paint2.setStrokeWidth(1.0f);
            }
            float f5 = (float) i;
            float f6 = (float) width;
            Canvas canvas3 = canvas2;
            float f7 = f6;
            Paint paint3 = paint2;
            canvas3.drawLine(0.0f, f5, f6, f5, paint3);
            canvas3.drawLine(0.0f, f4, f7, f4, paint3);
            f4 -= 13.0f;
            i3++;
            i = (int) (f5 + 13.0f);
        }
        int i4 = 0;
        while (i2 < width) {
            if (((float) i4) % 5.0f == f3) {
                paint2.setStrokeWidth(f2);
            } else {
                paint2.setStrokeWidth(f);
            }
            float f8 = (float) i2;
            canvas2.drawLine(f8, 0.0f, f8, (float) height, paint2);
            i4++;
            i2 = (int) (f8 + 13.0f);
            f = f;
            f3 = f3;
            f2 = f2;
        }
        float f9 = f3;
        canvas.drawBitmap(this.mdrawBitMap, f9, f9, paint);
    }

    /* renamed from: a */
    public void recycleDrawBitMap() {
        if (this.mdrawBitMap != null && !this.mdrawBitMap.isRecycled()) {
            this.mdrawBitMap.recycle();
            this.mdrawBitMap = null;
        }
    }

    /* renamed from: a */
    public void onMyDraw(Canvas canvas, Paint paint) {
        if (canvas != null) {
            if (this.mdrawBitMap == null) {
                drawGride(canvas);
            }
            canvas.drawBitmap(this.mdrawBitMap, 0.0f, 0.0f, paint);
        }
    }
}
