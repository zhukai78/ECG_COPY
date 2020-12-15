package com.hopetruly.ecg.util;

import android.content.Context;
import android.os.Vibrator;

/* renamed from: com.hopetruly.ecg.util.b */
public class VibratorUtils {

    /* renamed from: a */
    private static Vibrator mVibrator;

    /* renamed from: a */
    public static void canvelVibrator() {
        if (mVibrator != null) {
            mVibrator.cancel();
        }
    }

    /* renamed from: a */
    public static void goVibrator(Context context, int i, int i2) {
        mVibrator = (Vibrator) context.getApplicationContext().getSystemService("vibrator");
        switch (i) {
            case 1:
                mVibrator.vibrate(new long[]{(long) i2, 1000}, 0);
                return;
            case 2:
                mVibrator.vibrate((long) i2);
                return;
            default:
                return;
        }
    }
}
