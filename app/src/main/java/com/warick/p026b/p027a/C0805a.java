package com.warick.p026b.p027a;

import android.telephony.SmsManager;

import java.util.ArrayList;

/* renamed from: com.warick.b.a.a */
public class C0805a {
    /* renamed from: a */
    public static void m2926a(String str, String[] strArr) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> divideMessage = smsManager.divideMessage(str);
        for (String sendMultipartTextMessage : strArr) {
            smsManager.sendMultipartTextMessage(sendMultipartTextMessage, (String) null, divideMessage, (ArrayList) null, (ArrayList) null);
        }
    }
}
