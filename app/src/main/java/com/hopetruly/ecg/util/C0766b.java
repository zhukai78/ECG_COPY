package com.hopetruly.ecg.util;

import android.content.Context;
import android.os.Vibrator;

/* renamed from: com.hopetruly.ecg.util.b */
public class C0766b {

    /* renamed from: a */
    private static Vibrator f2902a;

    /* renamed from: a */
    public static void m2754a() {
        if (f2902a != null) {
            f2902a.cancel();
        }
    }

    /* renamed from: a */
    public static void m2755a(Context context, int i, int i2) {
        f2902a = (Vibrator) context.getApplicationContext().getSystemService("vibrator");
        switch (i) {
            case 1:
                f2902a.vibrate(new long[]{(long) i2, 1000}, 0);
                return;
            case 2:
                f2902a.vibrate((long) i2);
                return;
            default:
                return;
        }
    }
}
