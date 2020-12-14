package com.warick.p026b.p027a;

import com.hopetruly.ecg.ECGApplication;
import com.warick.p025a.C0800c;
import com.warick.p025a.C0801d;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.warick.b.a.b */
public class C0806b {
    /* renamed from: a */
    public static void m2927a(ECGApplication eCGApplication) {
        String str;
        if (C0801d.m2916e() != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(eCGApplication.f2091l.mo2680c());
            stringBuffer.append(" [ECG Air info:");
            C0800c g = C0801d.m2916e().mo2890g();
            stringBuffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis())));
            if (g == null) {
                str = "(No GPS location information)]";
            } else {
                stringBuffer.append("(longitude:");
                stringBuffer.append(g.mo2882a());
                stringBuffer.append(", latitude:");
                stringBuffer.append(g.mo2885b());
                stringBuffer.append(", address:");
                stringBuffer.append(C0801d.m2916e().mo2889f());
                str = ")]";
            }
            stringBuffer.append(str);
            boolean[] b = eCGApplication.f2091l.mo2679b();
            String[] a = eCGApplication.f2091l.mo2678a();
            int i = 0;
            for (boolean z : b) {
                if (z) {
                    i++;
                }
            }
            String[] strArr = new String[i];
            int i2 = 0;
            for (int i3 = 0; i3 < a.length; i3++) {
                if (b[i3]) {
                    strArr[i2] = a[i3];
                    i2++;
                }
            }
            C0805a.m2926a(stringBuffer.toString(), strArr);
        }
    }
}
