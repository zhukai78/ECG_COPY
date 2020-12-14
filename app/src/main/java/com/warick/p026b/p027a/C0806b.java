package com.warick.p026b.p027a;

import com.hopetruly.ecg.ECGApplication;
import com.warick.p025a.GpsBean;
import com.warick.p025a.GpsManagerHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.warick.b.a.b */
public class C0806b {
    /* renamed from: a */
    public static void m2927a(ECGApplication eCGApplication) {
        String str;
        if (GpsManagerHelper.mGpsManagerHelper() != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(eCGApplication.appSosConf.mo2680c());
            stringBuffer.append(" [ECG Air info:");
            GpsBean g = GpsManagerHelper.mGpsManagerHelper().getGpsBean();
            stringBuffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis())));
            if (g == null) {
                str = "(No GPS location information)]";
            } else {
                stringBuffer.append("(longitude:");
                stringBuffer.append(g.mo2882a());
                stringBuffer.append(", latitude:");
                stringBuffer.append(g.mo2885b());
                stringBuffer.append(", address:");
                stringBuffer.append(GpsManagerHelper.mGpsManagerHelper().mo2889f());
                str = ")]";
            }
            stringBuffer.append(str);
            boolean[] b = eCGApplication.appSosConf.mo2679b();
            String[] a = eCGApplication.appSosConf.mo2678a();
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
