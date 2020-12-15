package com.hopetruly.ecg.util;

import android.content.Context;

/* renamed from: com.hopetruly.ecg.util.e */
public class DpUtil {
    /* renamed from: a */
    public static int toDp(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
