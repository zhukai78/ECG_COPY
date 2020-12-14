package com.hopetruly.ecg.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.PedometerRecord;
import com.hopetruly.ecg.p022b.SqlManager;

public class SettingPedometerActivity extends BaseActivity {

    /* renamed from: a */
    EditText f2644a;

    /* renamed from: c */
    ECGApplication f2645c;

    /* renamed from: d */
    InputMethodManager f2646d;

    /* renamed from: a */
    private void m2545a() {
        this.f2644a = (EditText) findViewById(R.id.pedometer_target);
        this.f2644a.setText(String.valueOf(this.f2645c.appPedometerConf.mo2672b()));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f2645c = (ECGApplication) getApplication();
        setContentView(R.layout.activity_setting_pedometer);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.f2646d = (InputMethodManager) getSystemService("input_method");
        m2545a();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_pedometer, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.action_save) {
            String obj = this.f2644a.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                this.f2645c.appPedometerConf.setSTEP_TARGET(Long.valueOf(obj).longValue());
                SharedPreferences.Editor edit = this.f2645c.spPedometer_onf.edit();
                edit.putLong("STEP_TARGET", Long.valueOf(obj).longValue());
                edit.commit();
                PedometerRecord q = this.f2645c.appMainService.mo2746q();
                if (q != null) {
                    q.setTarget(this.f2645c.appPedometerConf.mo2672b());
                    new SqlManager(getApplicationContext()).mo2474c(q);
                }
                setResult(-1);
                if (getWindow().getAttributes().softInputMode == 0) {
                    this.f2646d.toggleSoftInput(0, 2);
                }
                finish();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
