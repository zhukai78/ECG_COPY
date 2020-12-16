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
import com.hopetruly.ecg.sql.SqlManager;

public class SettingPedometerActivity extends BaseActivity {

    /* renamed from: a */
    EditText edt_pedometer_target;

    /* renamed from: c */
    ECGApplication settingECGApplication;

    /* renamed from: d */
    InputMethodManager mInputMethodManager;

    /* renamed from: a */
    private void m2545a() {
        this.edt_pedometer_target = (EditText) findViewById(R.id.pedometer_target);
        this.edt_pedometer_target.setText(String.valueOf(this.settingECGApplication.appPedometerConf.mo2672b()));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.settingECGApplication = (ECGApplication) getApplication();
        setContentView(R.layout.activity_setting_pedometer);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.mInputMethodManager = (InputMethodManager) getSystemService("input_method");
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
            String obj = this.edt_pedometer_target.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                this.settingECGApplication.appPedometerConf.setSTEP_TARGET(Long.valueOf(obj).longValue());
                SharedPreferences.Editor edit = this.settingECGApplication.spPedometer_onf.edit();
                edit.putLong("STEP_TARGET", Long.valueOf(obj).longValue());
                edit.commit();
                PedometerRecord q = this.settingECGApplication.appMainService.getSqlPedometerRecord();
                if (q != null) {
                    q.setTarget(this.settingECGApplication.appPedometerConf.mo2672b());
                    new SqlManager(getApplicationContext()).writeStepRec(q);
                }
                setResult(-1);
                if (getWindow().getAttributes().softInputMode == 0) {
                    this.mInputMethodManager.toggleSoftInput(0, 2);
                }
                finish();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
