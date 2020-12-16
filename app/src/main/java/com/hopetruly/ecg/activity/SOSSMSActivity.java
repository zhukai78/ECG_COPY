package com.hopetruly.ecg.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.warick.gps.GpsManagerHelper;

public class SOSSMSActivity extends BaseActivity {

    /* renamed from: a */
    ECGApplication mECGApplication;

    /* renamed from: c */
    SharedPreferences.Editor SosEditor;

    /* renamed from: d */
    private final int f2527d = 4;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public TextView[] tv_sos_phone1 = new TextView[4];

    /* renamed from: f */
    private CheckBox[] cb_sos_phone0_enable = new CheckBox[4];

    /* renamed from: g */
    private CheckBox cb_setting_sms_alarm;

    /* renamed from: h */
    private CheckBox cb_setting_risk_marker;

    /* renamed from: i */
    private TextView tv_sos_custom_content;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public LinearLayout ll_set_mark_time_div;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public EditText edt_marking_period;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public boolean isCanAlarm = false;

    /* renamed from: m */
    private CompoundButton.OnCheckedChangeListener f2536m = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            switch (compoundButton.getId()) {
                case R.id.sos_sel_phone1 /*2131165480*/:
                    if (!z || SOSSMSActivity.this.tv_sos_phone1[0].getText().length() != 0) {
                        return;
                    }
                case R.id.sos_sel_phone2 /*2131165481*/:
                    if (!z || SOSSMSActivity.this.tv_sos_phone1[1].getText().length() != 0) {
                        return;
                    }
                case R.id.sos_sel_phone3 /*2131165482*/:
                    if (!z || SOSSMSActivity.this.tv_sos_phone1[2].getText().length() != 0) {
                        return;
                    }
                case R.id.sos_sel_phone4 /*2131165483*/:
                    if (!z || SOSSMSActivity.this.tv_sos_phone1[3].getText().length() != 0) {
                        return;
                    }
                default:

            }
            compoundButton.setChecked(false);
        }
    };

    /* renamed from: a */
    private void initView() {
        this.tv_sos_phone1[0] = (TextView) findViewById(R.id.sos_phone1);
        this.tv_sos_phone1[1] = (TextView) findViewById(R.id.sos_phone2);
        this.tv_sos_phone1[2] = (TextView) findViewById(R.id.sos_phone3);
        this.tv_sos_phone1[3] = (TextView) findViewById(R.id.sos_phone4);
        String[] a = this.mECGApplication.appSosConf.mo2678a();
        this.tv_sos_phone1[0].setText(a[0]);
        this.tv_sos_phone1[1].setText(a[1]);
        this.tv_sos_phone1[2].setText(a[2]);
        this.tv_sos_phone1[3].setText(a[3]);
        this.cb_sos_phone0_enable[0] = (CheckBox) findViewById(R.id.sos_sel_phone1);
        this.cb_sos_phone0_enable[1] = (CheckBox) findViewById(R.id.sos_sel_phone2);
        this.cb_sos_phone0_enable[2] = (CheckBox) findViewById(R.id.sos_sel_phone3);
        this.cb_sos_phone0_enable[3] = (CheckBox) findViewById(R.id.sos_sel_phone4);
        boolean[] b = this.mECGApplication.appSosConf.mo2679b();
        this.cb_sos_phone0_enable[0].setChecked(b[0]);
        this.cb_sos_phone0_enable[1].setChecked(b[1]);
        this.cb_sos_phone0_enable[2].setChecked(b[2]);
        this.cb_sos_phone0_enable[3].setChecked(b[3]);
        this.cb_sos_phone0_enable[0].setOnCheckedChangeListener(this.f2536m);
        this.cb_sos_phone0_enable[1].setOnCheckedChangeListener(this.f2536m);
        this.cb_sos_phone0_enable[2].setOnCheckedChangeListener(this.f2536m);
        this.cb_sos_phone0_enable[3].setOnCheckedChangeListener(this.f2536m);
        this.tv_sos_custom_content = (TextView) findViewById(R.id.sos_custom_content);
        this.tv_sos_custom_content.setText(this.mECGApplication.appSosConf.getSOS_CUSTOM_CONTENT());
        this.cb_setting_sms_alarm = (CheckBox) findViewById(R.id.setting_sms_alarm);
        this.cb_setting_sms_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    boolean unused = SOSSMSActivity.this.isCanAlarm = true;
                    SOSSMSActivity.this.allEnable();
                    SOSSMSActivity.this.mECGApplication.appECGConf.setECG_SMS_ALARM(1);
                    GpsManagerHelper.initGpsManagerHelper(SOSSMSActivity.this.getApplicationContext());
                    GpsManagerHelper.m2908a();
                } else {
                    boolean unused2 = SOSSMSActivity.this.isCanAlarm = false;
                    SOSSMSActivity.this.allDisable();
                    SOSSMSActivity.this.mECGApplication.appECGConf.setECG_SMS_ALARM(0);
                    GpsManagerHelper.removegps();
                }
                SOSSMSActivity.this.SosEditor.putInt("ECG_SMS_ALARM", SOSSMSActivity.this.mECGApplication.appECGConf.getECG_SMS_ALARM());
                SOSSMSActivity.this.SosEditor.commit();
            }
        });
        if (this.mECGApplication.appECGConf.getECG_SMS_ALARM() == 1) {
            this.cb_setting_sms_alarm.setChecked(true);
        } else {
            this.cb_setting_sms_alarm.setChecked(false);
        }
        this.edt_marking_period = (EditText) findViewById(R.id.marking_period);
        this.ll_set_mark_time_div = (LinearLayout) findViewById(R.id.set_mark_time_div);
        this.cb_setting_risk_marker = (CheckBox) findViewById(R.id.setting_risk_marker);
        this.cb_setting_risk_marker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    SOSSMSActivity.this.mECGApplication.appECGConf.setECG_ENABLE_MARK(1);
                    SOSSMSActivity.this.ll_set_mark_time_div.setVisibility(View.VISIBLE);
                    SOSSMSActivity.this.edt_marking_period.setEnabled(true);
                    SOSSMSActivity.this.edt_marking_period.setText(String.valueOf(SOSSMSActivity.this.mECGApplication.appECGConf.getECG_MARKING_PERIOD()));
                } else {
                    SOSSMSActivity.this.mECGApplication.appECGConf.setECG_ENABLE_MARK(0);
                    SOSSMSActivity.this.edt_marking_period.setEnabled(false);
                    SOSSMSActivity.this.ll_set_mark_time_div.setVisibility(View.GONE);
                }
                SOSSMSActivity.this.SosEditor.putInt("ECG_ENABLE_MARK", SOSSMSActivity.this.mECGApplication.appECGConf.getECG_ENABLE_MARK());
                SOSSMSActivity.this.SosEditor.commit();
            }
        });
        if (this.mECGApplication.appECGConf.getECG_ENABLE_MARK() == 1) {
            this.cb_setting_risk_marker.setChecked(true);
            this.ll_set_mark_time_div.setVisibility(View.VISIBLE);
            return;
        }
        this.cb_setting_risk_marker.setChecked(false);
        this.ll_set_mark_time_div.setVisibility(View.GONE);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void allEnable() {
        this.tv_sos_phone1[0].setEnabled(true);
        this.tv_sos_phone1[1].setEnabled(true);
        this.tv_sos_phone1[2].setEnabled(true);
        this.tv_sos_phone1[3].setEnabled(true);
        this.cb_sos_phone0_enable[0].setEnabled(true);
        this.cb_sos_phone0_enable[1].setEnabled(true);
        this.cb_sos_phone0_enable[2].setEnabled(true);
        this.cb_sos_phone0_enable[3].setEnabled(true);
        this.tv_sos_custom_content.setEnabled(true);
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void allDisable() {
        this.tv_sos_phone1[0].setEnabled(false);
        this.tv_sos_phone1[1].setEnabled(false);
        this.tv_sos_phone1[2].setEnabled(false);
        this.tv_sos_phone1[3].setEnabled(false);
        this.cb_sos_phone0_enable[0].setEnabled(false);
        this.cb_sos_phone0_enable[1].setEnabled(false);
        this.cb_sos_phone0_enable[2].setEnabled(false);
        this.cb_sos_phone0_enable[3].setEnabled(false);
        this.tv_sos_custom_content.setEnabled(false);
    }

    /* renamed from: d */
    private void saveInfo() {
        int i = 0;
        if (this.isCanAlarm) {
            this.mECGApplication.appSosConf.setPhones(this.tv_sos_phone1[0].getText().toString(), 0);
            this.mECGApplication.appSosConf.setPhones(this.tv_sos_phone1[1].getText().toString(), 1);
            this.mECGApplication.appSosConf.setPhones(this.tv_sos_phone1[2].getText().toString(), 2);
            this.mECGApplication.appSosConf.setPhones(this.tv_sos_phone1[3].getText().toString(), 3);
            if (this.tv_sos_phone1[0].getText().toString().length() == 0) {
                this.cb_sos_phone0_enable[0].setChecked(false);
            }
            if (this.tv_sos_phone1[1].getText().toString().length() == 0) {
                this.cb_sos_phone0_enable[1].setChecked(false);
            }
            if (this.tv_sos_phone1[2].getText().toString().length() == 0) {
                this.cb_sos_phone0_enable[2].setChecked(false);
            }
            if (this.tv_sos_phone1[3].getText().toString().length() == 0) {
                this.cb_sos_phone0_enable[3].setChecked(false);
            }
            this.mECGApplication.appSosConf.setPhonesEnable(this.cb_sos_phone0_enable[0].isChecked(), 0);
            this.mECGApplication.appSosConf.setPhonesEnable(this.cb_sos_phone0_enable[1].isChecked(), 1);
            this.mECGApplication.appSosConf.setPhonesEnable(this.cb_sos_phone0_enable[2].isChecked(), 2);
            this.mECGApplication.appSosConf.setPhonesEnable(this.cb_sos_phone0_enable[3].isChecked(), 3);
            this.mECGApplication.appSosConf.setSOS_CUSTOM_CONTENT(this.tv_sos_custom_content.getText().toString());
            SharedPreferences.Editor edit = this.mECGApplication.spSos_sms_conf.edit();
            edit.putString("SOS_PHONE0", this.tv_sos_phone1[0].getText().toString());
            edit.putString("SOS_PHONE1", this.tv_sos_phone1[1].getText().toString());
            edit.putString("SOS_PHONE2", this.tv_sos_phone1[2].getText().toString());
            edit.putString("SOS_PHONE3", this.tv_sos_phone1[3].getText().toString());
            edit.putString("SOS_CUSTOM_CONTENT", this.tv_sos_custom_content.getText().toString());
            edit.putBoolean("SOS_PHONE0_ENABLE", this.cb_sos_phone0_enable[0].isChecked());
            edit.putBoolean("SOS_PHONE1_ENABLE", this.cb_sos_phone0_enable[1].isChecked());
            edit.putBoolean("SOS_PHONE2_ENABLE", this.cb_sos_phone0_enable[2].isChecked());
            edit.putBoolean("SOS_PHONE3_ENABLE", this.cb_sos_phone0_enable[3].isChecked());
            edit.commit();
        }
        if (this.cb_setting_risk_marker.isChecked()) {
            if (this.edt_marking_period.getText().toString().length() == 0) {
                i = R.string.p_ha_null;
            } else {
                int parseInt = Integer.parseInt(this.edt_marking_period.getText().toString());
                if (parseInt <= 0) {
                    i = R.string.p_ha_zero;
                } else {
                    if (parseInt > 300) {
                        Toast.makeText(this, getString(R.string.p_mark_large), Toast.LENGTH_LONG).show();
                        this.mECGApplication.appECGConf.setECG_MARKING_PERIOD(300);
                    } else {
                        this.mECGApplication.appECGConf.setECG_MARKING_PERIOD(parseInt);
                    }
                    this.SosEditor.putInt("ECG_MARKING_PERIOD", this.mECGApplication.appECGConf.getECG_MARKING_PERIOD());
                    this.SosEditor.commit();
                }
            }
            Toast.makeText(this, getString(i), Toast.LENGTH_LONG).show();
            this.SosEditor.putInt("ECG_MARKING_PERIOD", this.mECGApplication.appECGConf.getECG_MARKING_PERIOD());
            this.SosEditor.commit();
        }
    }

    /* renamed from: e */
    private void hideSoftInput() {
        View peekDecorView = getWindow().peekDecorView();
        if (peekDecorView != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        setContentView(R.layout.activity_sos_seting);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(bundle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_pedometer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            hideSoftInput();
            onBackPressed();
        } else if (itemId == R.id.action_save) {
            saveInfo();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.p_save), Toast.LENGTH_LONG).show();
            setResult(-1);
            hideSoftInput();
            finish();
        }
        return super.onMenuItemSelected(i, menuItem);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        this.mECGApplication = (ECGApplication) getApplication();
        this.SosEditor = this.mECGApplication.spECG_conf.edit();
        initView();
        if (this.isCanAlarm) {
            allEnable();
        } else {
            allDisable();
        }
        super.onStart();
    }
}
