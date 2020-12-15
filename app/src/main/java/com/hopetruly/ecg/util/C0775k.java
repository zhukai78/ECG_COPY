package com.hopetruly.ecg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.TextUtils;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.ECGRecord;
import com.warick.jni.filter.Fir;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

/* renamed from: com.hopetruly.ecg.util.k */
public class C0775k {

    /* renamed from: a */
    Context mctx;

    /* renamed from: b */
    private float f2924b = 10.0f;

    /* renamed from: c */
    private float f2925c = 10.0f;

    /* renamed from: d */
    private boolean f2926d = true;

    /* renamed from: e */
    private int f2927e = 1;

    /* renamed from: f */
    private int f2928f = 1;

    /* renamed from: g */
    private boolean f2929g = true;

    public C0775k(Context context) {
        this.mctx = context;
    }

    /* renamed from: a */
    private int m2805a(String str) {
        int i = 1;
        if (!TextUtils.isEmpty(str) && !this.f2929g) {
            int length = str.length() / 20;
            if (str.length() % 20 == 0) {
                i = 0;
            }
            this.f2928f = length + i;
        } else {
            this.f2928f = 1;
        }
        return this.f2928f;
    }

    /* renamed from: a */
    private String bitmapToJpg(Bitmap bitmap) {
        StringBuffer stringBuffer = new StringBuffer(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuffer.append(File.separator);
        stringBuffer.append("hopetruly");
        stringBuffer.append(File.separator);
        stringBuffer.append("ScreenShot");
        stringBuffer.append(File.separator);
        stringBuffer.append("ecgScreenShot");
        stringBuffer.append(".jpg");
        String stringBuffer2 = stringBuffer.toString();
        try {
            File file = new File(stringBuffer2);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 35, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return stringBuffer2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    private void m2807a(Canvas canvas) {
        canvas.getWidth();
        canvas.getHeight();
        new Paint().setColor(Color.rgb(85, 235, 243));
        canvas.drawColor(-1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00d8  */
    /* renamed from: a */
    private void m2808a(Canvas canvas, Paint paint, ECGRecord eCGRecord, String str) throws IOException, SAXException, ParserConfigurationException {
        int heartRate;
        String str2 = "";
        int i = 0;
        float f = this.f2925c * 65.0f;
        float f2 = this.f2925c * 4.0f;
        float f3 = this.f2925c * 5.0f;
        ECGApplication eCGApplication = (ECGApplication) this.mctx.getApplicationContext();
        canvas.drawText((TextUtils.isEmpty(eCGApplication.mUserInfo.getLastName()) ? "" : eCGApplication.mUserInfo.getLastName()) + (TextUtils.isEmpty(eCGApplication.mUserInfo.getFirstName()) ? "" : eCGApplication.mUserInfo.getFirstName()), f, f2, paint);
        m2809a(canvas, ECGRecordUtils.annotatedECG(new File(eCGRecord.getFilePath()), "text"), f, f2 + f3, f3, paint);
        switch (eCGRecord.getLeadType()) {
            case 0:
                i = R.string.l_with_hand;
                break;
            case 1:
                i = R.string.l_with_chest;
                break;
            default:
                canvas.drawText(str, this.f2925c * 105.0f, (this.f2925c * 9.0f) + (((float) this.f2928f) * f3), paint);
                heartRate = eCGRecord.getHeartRate();
                if (heartRate != 0) {
                    str2 = eCGApplication.getString(R.string.l_ecg_rec_avg) + "：NaN Bpm";
                } else {
                    str2 = eCGApplication.getString(R.string.l_ecg_rec_avg) + "：" + heartRate + " Bpm";
                }
                canvas.drawText(str2, this.f2925c * 105.0f, (((float) (this.f2928f + 2)) * f3) + f2, paint);
                canvas.drawText(eCGRecord.getTime(), f, f2 + (f3 * ((float) (this.f2928f + 2))), paint);
        }
        canvas.drawText(eCGApplication.getString(i), f, (((float) (this.f2928f + 1)) * f3) + f2, paint);
        canvas.drawText(str, this.f2925c * 105.0f, (this.f2925c * 9.0f) + (((float) this.f2928f) * f3), paint);
        heartRate = eCGRecord.getHeartRate();
        if (heartRate != 0) {
        }
        canvas.drawText(str2, this.f2925c * 105.0f, (((float) (this.f2928f + 2)) * f3) + f2, paint);
        canvas.drawText(eCGRecord.getTime(), f, f2 + (f3 * ((float) (this.f2928f + 2))), paint);
    }

    /* renamed from: a */
    private void m2809a(Canvas canvas, String str, float f, float f2, float f3, Paint paint) {
        float f4 = f2;
        if (!TextUtils.isEmpty(str)) {
            int i = 1;
            int i2 = 0;
            if (this.f2929g) {
                String substring = str.length() > 20 ? str.substring(0, 20) : str;
                this.f2928f = 1;
                canvas.drawText(substring, f, f4, paint);
                return;
            }
            Canvas canvas2 = canvas;
            String str2 = str;
            float f5 = f;
            Paint paint2 = paint;
            int length = str.length() / 20;
            if (str.length() % 20 == 0) {
                i = 0;
            }
            this.f2928f = length + i;
            int i3 = 0;
            while (i2 < this.f2928f) {
                int i4 = i3 + 20;
                canvas2.drawText(str2, i3, i4, f5, f4 + (((float) i2) * f3), paint2);
                i2++;
                i3 = i4;
            }
        }
    }

    /* renamed from: a */
    private void m2810a(int[] iArr, float[] fArr, Canvas canvas, Paint paint) {
        int i;
        float[] fArr2 = fArr;
        int i2 = (int) ((((float) this.f2928f) * this.f2925c * 5.0f) + 300.0f);
        int i3 = (int) (300.0f + (((float) this.f2928f) * this.f2925c * 5.0f));
        Path path = new Path();
        path.reset();
        int i4 = iArr[0];
        path.moveTo(this.f2924b * 8.0f, ((float) i2) - (fArr2[i4] / this.f2924b));
        int i5 = iArr[0] + 1;
        int i6 = i3;
        int i7 = i2;
        int i8 = 1;
        while (i8 < canvas.getWidth() && i8 < iArr[1] && (i = i4 + i8) < fArr2.length) {
            if (i7 == i6) {
                path.lineTo(((float) i8) + (this.f2924b * 8.0f), ((float) i7) - (fArr2[i] / this.f2924b));
            } else if (i5 < fArr2.length) {
                path.lineTo((float) i8, ((float) i7) - (fArr2[i5] / this.f2924b));
            }
            i5++;
            if (i8 == canvas.getWidth() - 1) {
                i7 += 300;
                if (i7 > canvas.getHeight()) {
                    break;
                }
                path.moveTo(0.0f, ((float) i7) - (fArr2[i5] / this.f2924b));
                i6 = 300;
                i8 = 1;
            }
            i8++;
        }
        canvas.drawPath(path, paint);
    }

    /* renamed from: a */
    private float[] m2811a(float[] fArr) {
        double[] dArr = new double[Fir.getOrder(Fir.f3076f)];
        Fir.m2980a(Fir.getOrder(Fir.f3076f), dArr);
        float[] fArr2 = new float[fArr.length];
        int i = 0;
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (this.f2926d) {
                if (i2 > Fir.getOrder(Fir.f3076f) / 2) {
                    i++;
                }
                fArr2[i] = ((float) Fir.RealtimeFir(fArr[i2], Fir.f3076f, dArr)) * ((float) this.f2927e);
            } else {
                fArr2[i2] = fArr[i2] * ((float) this.f2927e);
            }
        }
        if (this.f2926d) {
            for (int i3 = 0; i3 < Fir.getOrder(Fir.f3076f) / 2; i3++) {
                i++;
                fArr2[i] = ((float) Fir.RealtimeFir(0, Fir.f3076f, dArr)) * ((float) this.f2927e);
            }
        }
        return fArr2;
    }

    /* renamed from: b */
    private void m2812b(Canvas canvas) {
        float f;
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.rgb(85, 235, 243));
        float f2 = this.f2924b;
        int i = (int) (150.0f + (((float) this.f2928f) * this.f2925c * 5.0f));
        int i2 = 0;
        int i3 = i;
        int i4 = 0;
        while (true) {
            f = 3.0f;
            if (i3 >= height) {
                break;
            }
            if (((float) i4) % 5.0f == 0.0f) {
                paint.setStrokeWidth(3.0f);
            } else {
                paint.setStrokeWidth(1.0f);
            }
            float f3 = (float) i3;
            canvas.drawLine(0.0f, f3, (float) width, f3, paint);
            i4++;
            i3 = (int) (f3 + f2);
        }
        int i5 = 0;
        while (i2 < width) {
            if (((float) i5) % 5.0f == 0.0f) {
                paint.setStrokeWidth(f);
            } else {
                paint.setStrokeWidth(1.0f);
            }
            float f4 = (float) i2;
            canvas.drawLine(f4, (float) i, f4, (float) height, paint);
            i5++;
            i2 = (int) (f4 + f2);
            f = f;
        }
    }

    /* renamed from: a */
    public String mo2791a(ECGRecord eCGRecord, float[] fArr, int i, String str, int i2, int i3) throws IOException, SAXException, ParserConfigurationException {
        int i4;
        Paint paint;
        float f;
        Paint paint2;
        Canvas canvas;
        float f2;
        ECGRecord eCGRecord2 = eCGRecord;
        float[] fArr2 = fArr;
        String str2 = str;
        int i5 = i2;
        ECGApplication eCGApplication = (ECGApplication) this.mctx.getApplicationContext();
        if (eCGRecord2 == null) {
            return null;
        }
        m2805a(ECGRecordUtils.annotatedECG(new File(eCGRecord.getFilePath()), "text"));
        int[] a = mo2795a(fArr2, i);
        if (a == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i5, (int) (((float) (((int) ((((float) (a[1] - a[0])) / 1500.0f) + 0.99999f)) * 300)) + (((float) this.f2928f) * this.f2925c * 5.0f) + 150.0f), Bitmap.Config.RGB_565);
        Canvas canvas2 = new Canvas(createBitmap);
        Paint paint3 = new Paint();
        paint3.setColor(-16777216);
        paint3.setStrokeWidth(3.0f);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setAntiAlias(true);
        paint3.setPathEffect(new CornerPathEffect(5.0f));
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        m2807a(canvas2);
        paint3.setStrokeWidth(2.0f);
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        paint3.setTextSize(35.0f);
        paint3.setColor(-12303292);
        paint3.setTypeface(Typeface.DEFAULT_BOLD);
        float f3 = this.f2925c * 5.0f;
        float f4 = this.f2925c * 4.0f;
        canvas2.drawText("病人编号：" + eCGRecord.getUser().getId(), 10.0f, (2.0f * f3) + f4, paint3);
        canvas2.drawText("主治医生（签名）：", 10.0f, f4 + (f3 * 3.0f), paint3);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(eCGApplication.getResources(), R.drawable.ecg_bg, options);
        int i6 = options.outWidth;
        if (options.outHeight > 150 || i6 > 150) {
            float f5 = (float) 150;
            i4 = Math.round(((float) i3) / f5);
            int round = Math.round(((float) i5) / f5);
            if (i4 >= round) {
                i4 = round;
            }
        } else {
            i4 = 1;
        }
        options.inSampleSize = i4;
        options.inJustDecodeBounds = false;
        Bitmap decodeResource = BitmapFactory.decodeResource(this.mctx.getResources(), R.drawable.ecg_bg, options);
        canvas2.save();
        canvas2.translate(0.0f, 0.0f);
        canvas2.drawBitmap(decodeResource, 0.0f, 0.0f, new Paint());
        canvas2.restore();
        m2808a(canvas2, paint3, eCGRecord2, str2);
        m2812b(canvas2);
        paint3.setColor(-16777216);
        paint3.setStrokeWidth(3.0f);
        paint3.setStyle(Paint.Style.STROKE);
        int i7 = (int) (300.0f + (((float) this.f2928f) * f3));
        if (str2.equals(eCGApplication.getString(R.string.l_ecg_scale_50))) {
            f = (float) i7;
            Canvas canvas3 = canvas2;
            paint = paint3;
            float f6 = f;
            paint2 = paint;
            canvas3.drawLine(0.0f, f6, 3.0f, f, paint2);
            canvas3.drawLine(3.0f, f6, 3.0f, (f - (this.f2924b * 20.0f)) - 1.0f, paint2);
            canvas = canvas2;
            canvas.drawLine(3.0f, f - (this.f2924b * 20.0f), (this.f2924b * 5.0f) + 1.0f, f - (this.f2924b * 20.0f), paint2);
            canvas.drawLine(this.f2924b * 5.0f, f - (this.f2924b * 20.0f), this.f2924b * 5.0f, (float) (i7 + 1), paint2);
            f2 = this.f2924b * 5.0f;
        } else {
            paint = paint3;
            f = (float) i7;
            Canvas canvas4 = canvas2;
            float f7 = f;
            paint2 = paint;
            canvas4.drawLine(0.0f, f7, 3.0f, f, paint2);
            canvas4.drawLine(3.0f, f7, 3.0f, (f - (this.f2924b * 10.0f)) - 1.0f, paint2);
            canvas = canvas2;
            canvas.drawLine(3.0f, f - (this.f2924b * 10.0f), (this.f2924b * 5.0f) + 1.0f, f - (this.f2924b * 10.0f), paint2);
            canvas.drawLine(this.f2924b * 5.0f, f - (this.f2924b * 10.0f), this.f2924b * 5.0f, (float) (i7 + 1), paint2);
            f2 = this.f2924b * 5.0f;
        }
        canvas.drawLine(f2, f, this.f2924b * 6.0f, f, paint2);
        m2810a(a, m2811a(fArr2), canvas2, paint);
        return bitmapToJpg(createBitmap);
    }

    /* renamed from: a */
    public void mo2792a(float f) {
        this.f2924b /= f;
    }

    /* renamed from: a */
    public void mo2793a(int i) {
        this.f2927e = i;
    }

    /* renamed from: a */
    public void mo2794a(boolean z) {
        this.f2926d = z;
    }

    /* renamed from: a */
    public int[] mo2795a(float[] fArr, int i) {
        int i2;
        int i3;
        int[] iArr = new int[2];
        int i4 = i - 7500;
        int i5 = i + 7500;
        if (i4 < 0) {
            i5 -= i4;
            i4 = 0;
        }
        if (i5 > fArr.length - 1) {
            i2 = i4 - (i5 - (fArr.length - 1));
            i3 = fArr.length - 1;
            if (i2 < 0) {
                i2 = 0;
            }
        } else {
            i3 = i5;
            i2 = i4;
        }
        iArr[0] = i2;
        iArr[1] = i3;
        return iArr;
    }
}
