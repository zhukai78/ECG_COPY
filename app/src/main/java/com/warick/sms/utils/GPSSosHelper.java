package com.warick.sms.utils;

import com.hopetruly.ecg.ECGApplication;
import com.warick.gps.GpsBean;
import com.warick.gps.GpsManagerHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.warick.b.a.b */
public class GPSSosHelper {
    /* renamed from: a */
    public static void sendMultipartTextMessageSmsGPS(ECGApplication eCGApplication) {
        String str;
        if (GpsManagerHelper.mGpsManagerHelper() != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(eCGApplication.appSosConf.getSOS_CUSTOM_CONTENT());
            stringBuffer.append(" [ECG Air info:");
            GpsBean g = GpsManagerHelper.mGpsManagerHelper().getGpsBean();
            stringBuffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis())));
            if (g == null) {
                str = "(No GPS location information)]";
            } else {
                stringBuffer.append("(longitude:");
                stringBuffer.append(g.getLongitude());
                stringBuffer.append(", latitude:");
                stringBuffer.append(g.getLatitude());
                stringBuffer.append(", address:");
                stringBuffer.append(GpsManagerHelper.mGpsManagerHelper().getTip());
                str = ")]";
            }
            stringBuffer.append(str);
            boolean[] b = eCGApplication.appSosConf.getPhoneEnables();
            String[] a = eCGApplication.appSosConf.getPhoneStrs();
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
            SmsManagerUtils.sendMultipartTextMessageSms(stringBuffer.toString(), strArr);
        }
    }
}
