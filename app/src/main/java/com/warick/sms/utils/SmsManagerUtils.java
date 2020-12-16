package com.warick.sms.utils;

import android.telephony.SmsManager;

import java.util.ArrayList;

/* renamed from: com.warick.b.a.a */
public class SmsManagerUtils {
    /* renamed from: a */
    public static void sendMultipartTextMessageSms(String str, String[] strArr) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> divideMessage = smsManager.divideMessage(str);
        for (String sendMultipartTextMessage : strArr) {
            smsManager.sendMultipartTextMessage(sendMultipartTextMessage, (String) null, divideMessage, (ArrayList) null, (ArrayList) null);
        }
    }
}
