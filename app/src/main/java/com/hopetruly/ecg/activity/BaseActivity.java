package com.hopetruly.ecg.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.util.LogUtils;


/* renamed from: com.hopetruly.ecg.activity.a */
public class BaseActivity extends Activity {

    /* renamed from: a */
    private BroadcastReceiver loginBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.hopetruly.ecg.activity.BaseActivity.LOGINOUT_ACTION")) {
                LogUtils.logI("base", "recever:LOGINOUT_ACTION");
                BaseActivity.this.finish();
            }
        }
    };

    /* renamed from: b */
    ECGApplication ecgApplication;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.ecgApplication = (ECGApplication) getApplication();
        this.ecgApplication.appEcgUncaughtExceptionHandler.addActivity((Activity) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ecg.activity.BaseActivity.LOGINOUT_ACTION");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.loginBroadcastReceiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.loginBroadcastReceiver);
        this.ecgApplication.appEcgUncaughtExceptionHandler.removeActivity((Activity) this);
    }
}
