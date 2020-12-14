package com.hopetruly.ecg.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.hopetruly.ecg.R;


/* renamed from: com.hopetruly.ecg.util.j */
public class C0774j {

    /* renamed from: a */
    Context f2922a;

    public C0774j(Context context) {
        this.f2922a = context;
    }

    /* renamed from: b */
    private Notification m2803b(String str, String str2) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this.f2922a).setSmallIcon((int) R.drawable.ic_launcher).setContentTitle((CharSequence) str).setContentText((CharSequence) str2);
        b.setAutoCancel(true);
        b.setSortKey(str);
        b.setDefaults(-1);
        return b.getNotification();
    }

    /* renamed from: a */
    public void mo2790a(String str, String str2) {
        ((NotificationManager) this.f2922a.getSystemService(Context.NOTIFICATION_SERVICE)).notify(100, m2803b(str, str2));
    }
}
