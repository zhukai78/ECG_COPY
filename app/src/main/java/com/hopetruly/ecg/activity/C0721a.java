package com.hopetruly.ecg.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.util.C0771g;


/* renamed from: com.hopetruly.ecg.activity.a */
public class C0721a extends Activity {

    /* renamed from: a */
    private BroadcastReceiver f2689a = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.hopetruly.ecg.activity.BaseActivity.LOGINOUT_ACTION")) {
                C0771g.m2784a("base", "recever:LOGINOUT_ACTION");
                C0721a.this.finish();
            }
        }
    };

    /* renamed from: b */
    ECGApplication f2690b;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f2690b = (ECGApplication) getApplication();
        this.f2690b.f2095p.mo2769a((Activity) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ecg.activity.BaseActivity.LOGINOUT_ACTION");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2689a, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2689a);
        this.f2690b.f2095p.mo2773b((Activity) this);
    }
}
