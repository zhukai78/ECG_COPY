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
import com.warick.p025a.GpsManagerHelper;

public class SOSSMSActivity extends BaseActivity {

    /* renamed from: a */
    ECGApplication f2525a;

    /* renamed from: c */
    SharedPreferences.Editor f2526c;

    /* renamed from: d */
    private final int f2527d = 4;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public TextView[] f2528e = new TextView[4];

    /* renamed from: f */
    private CheckBox[] f2529f = new CheckBox[4];

    /* renamed from: g */
    private CheckBox f2530g;

    /* renamed from: h */
    private CheckBox f2531h;

    /* renamed from: i */
    private TextView f2532i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public LinearLayout f2533j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public EditText f2534k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public boolean f2535l = false;

    /* renamed from: m */
    private CompoundButton.OnCheckedChangeListener f2536m = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            switch (compoundButton.getId()) {
                case R.id.sos_sel_phone1 /*2131165480*/:
                    if (!z || SOSSMSActivity.this.f2528e[0].getText().length() != 0) {
                        return;
                    }
                case R.id.sos_sel_phone2 /*2131165481*/:
                    if (!z || SOSSMSActivity.this.f2528e[1].getText().length() != 0) {
                        return;
                    }
                case R.id.sos_sel_phone3 /*2131165482*/:
                    if (!z || SOSSMSActivity.this.f2528e[2].getText().length() != 0) {
                        return;
                    }
                case R.id.sos_sel_phone4 /*2131165483*/:
                    if (!z || SOSSMSActivity.this.f2528e[3].getText().length() != 0) {
                        return;
                    }
                default:

            }
            compoundButton.setChecked(false);
        }
    };

    /* renamed from: a */
    private void m2499a() {
        this.f2528e[0] = (TextView) findViewById(R.id.sos_phone1);
        this.f2528e[1] = (TextView) findViewById(R.id.sos_phone2);
        this.f2528e[2] = (TextView) findViewById(R.id.sos_phone3);
        this.f2528e[3] = (TextView) findViewById(R.id.sos_phone4);
        String[] a = this.f2525a.appSosConf.mo2678a();
        this.f2528e[0].setText(a[0]);
        this.f2528e[1].setText(a[1]);
        this.f2528e[2].setText(a[2]);
        this.f2528e[3].setText(a[3]);
        this.f2529f[0] = (CheckBox) findViewById(R.id.sos_sel_phone1);
        this.f2529f[1] = (CheckBox) findViewById(R.id.sos_sel_phone2);
        this.f2529f[2] = (CheckBox) findViewById(R.id.sos_sel_phone3);
        this.f2529f[3] = (CheckBox) findViewById(R.id.sos_sel_phone4);
        boolean[] b = this.f2525a.appSosConf.mo2679b();
        this.f2529f[0].setChecked(b[0]);
        this.f2529f[1].setChecked(b[1]);
        this.f2529f[2].setChecked(b[2]);
        this.f2529f[3].setChecked(b[3]);
        this.f2529f[0].setOnCheckedChangeListener(this.f2536m);
        this.f2529f[1].setOnCheckedChangeListener(this.f2536m);
        this.f2529f[2].setOnCheckedChangeListener(this.f2536m);
        this.f2529f[3].setOnCheckedChangeListener(this.f2536m);
        this.f2532i = (TextView) findViewById(R.id.sos_custom_content);
        this.f2532i.setText(this.f2525a.appSosConf.mo2680c());
        this.f2530g = (CheckBox) findViewById(R.id.setting_sms_alarm);
        this.f2530g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    boolean unused = SOSSMSActivity.this.f2535l = true;
                    SOSSMSActivity.this.m2502b();
                    SOSSMSActivity.this.f2525a.appECGConf.setECG_SMS_ALARM(1);
                    GpsManagerHelper.initGpsManagerHelper(SOSSMSActivity.this.getApplicationContext());
                    GpsManagerHelper.m2908a();
                } else {
                    boolean unused2 = SOSSMSActivity.this.f2535l = false;
                    SOSSMSActivity.this.m2505c();
                    SOSSMSActivity.this.f2525a.appECGConf.setECG_SMS_ALARM(0);
                    GpsManagerHelper.removegps();
                }
                SOSSMSActivity.this.f2526c.putInt("ECG_SMS_ALARM", SOSSMSActivity.this.f2525a.appECGConf.getECG_SMS_ALARM());
                SOSSMSActivity.this.f2526c.commit();
            }
        });
        if (this.f2525a.appECGConf.getECG_SMS_ALARM() == 1) {
            this.f2530g.setChecked(true);
        } else {
            this.f2530g.setChecked(false);
        }
        this.f2534k = (EditText) findViewById(R.id.marking_period);
        this.f2533j = (LinearLayout) findViewById(R.id.set_mark_time_div);
        this.f2531h = (CheckBox) findViewById(R.id.setting_risk_marker);
        this.f2531h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    SOSSMSActivity.this.f2525a.appECGConf.setECG_ENABLE_MARK(1);
                    SOSSMSActivity.this.f2533j.setVisibility(View.VISIBLE);
                    SOSSMSActivity.this.f2534k.setEnabled(true);
                    SOSSMSActivity.this.f2534k.setText(String.valueOf(SOSSMSActivity.this.f2525a.appECGConf.getECG_MARKING_PERIOD()));
                } else {
                    SOSSMSActivity.this.f2525a.appECGConf.setECG_ENABLE_MARK(0);
                    SOSSMSActivity.this.f2534k.setEnabled(false);
                    SOSSMSActivity.this.f2533j.setVisibility(View.GONE);
                }
                SOSSMSActivity.this.f2526c.putInt("ECG_ENABLE_MARK", SOSSMSActivity.this.f2525a.appECGConf.getECG_ENABLE_MARK());
                SOSSMSActivity.this.f2526c.commit();
            }
        });
        if (this.f2525a.appECGConf.getECG_ENABLE_MARK() == 1) {
            this.f2531h.setChecked(true);
            this.f2533j.setVisibility(View.VISIBLE);
            return;
        }
        this.f2531h.setChecked(false);
        this.f2533j.setVisibility(View.GONE);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2502b() {
        this.f2528e[0].setEnabled(true);
        this.f2528e[1].setEnabled(true);
        this.f2528e[2].setEnabled(true);
        this.f2528e[3].setEnabled(true);
        this.f2529f[0].setEnabled(true);
        this.f2529f[1].setEnabled(true);
        this.f2529f[2].setEnabled(true);
        this.f2529f[3].setEnabled(true);
        this.f2532i.setEnabled(true);
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2505c() {
        this.f2528e[0].setEnabled(false);
        this.f2528e[1].setEnabled(false);
        this.f2528e[2].setEnabled(false);
        this.f2528e[3].setEnabled(false);
        this.f2529f[0].setEnabled(false);
        this.f2529f[1].setEnabled(false);
        this.f2529f[2].setEnabled(false);
        this.f2529f[3].setEnabled(false);
        this.f2532i.setEnabled(false);
    }

    /* renamed from: d */
    private void m2507d() {
        int i = 0;
        if (this.f2535l) {
            this.f2525a.appSosConf.setPhones(this.f2528e[0].getText().toString(), 0);
            this.f2525a.appSosConf.setPhones(this.f2528e[1].getText().toString(), 1);
            this.f2525a.appSosConf.setPhones(this.f2528e[2].getText().toString(), 2);
            this.f2525a.appSosConf.setPhones(this.f2528e[3].getText().toString(), 3);
            if (this.f2528e[0].getText().toString().length() == 0) {
                this.f2529f[0].setChecked(false);
            }
            if (this.f2528e[1].getText().toString().length() == 0) {
                this.f2529f[1].setChecked(false);
            }
            if (this.f2528e[2].getText().toString().length() == 0) {
                this.f2529f[2].setChecked(false);
            }
            if (this.f2528e[3].getText().toString().length() == 0) {
                this.f2529f[3].setChecked(false);
            }
            this.f2525a.appSosConf.setPhonesEnable(this.f2529f[0].isChecked(), 0);
            this.f2525a.appSosConf.setPhonesEnable(this.f2529f[1].isChecked(), 1);
            this.f2525a.appSosConf.setPhonesEnable(this.f2529f[2].isChecked(), 2);
            this.f2525a.appSosConf.setPhonesEnable(this.f2529f[3].isChecked(), 3);
            this.f2525a.appSosConf.setSOS_CUSTOM_CONTENT(this.f2532i.getText().toString());
            SharedPreferences.Editor edit = this.f2525a.spSos_sms_conf.edit();
            edit.putString("SOS_PHONE0", this.f2528e[0].getText().toString());
            edit.putString("SOS_PHONE1", this.f2528e[1].getText().toString());
            edit.putString("SOS_PHONE2", this.f2528e[2].getText().toString());
            edit.putString("SOS_PHONE3", this.f2528e[3].getText().toString());
            edit.putString("SOS_CUSTOM_CONTENT", this.f2532i.getText().toString());
            edit.putBoolean("SOS_PHONE0_ENABLE", this.f2529f[0].isChecked());
            edit.putBoolean("SOS_PHONE1_ENABLE", this.f2529f[1].isChecked());
            edit.putBoolean("SOS_PHONE2_ENABLE", this.f2529f[2].isChecked());
            edit.putBoolean("SOS_PHONE3_ENABLE", this.f2529f[3].isChecked());
            edit.commit();
        }
        if (this.f2531h.isChecked()) {
            if (this.f2534k.getText().toString().length() == 0) {
                i = R.string.p_ha_null;
            } else {
                int parseInt = Integer.parseInt(this.f2534k.getText().toString());
                if (parseInt <= 0) {
                    i = R.string.p_ha_zero;
                } else {
                    if (parseInt > 300) {
                        Toast.makeText(this, getString(R.string.p_mark_large), Toast.LENGTH_LONG).show();
                        this.f2525a.appECGConf.setECG_MARKING_PERIOD(300);
                    } else {
                        this.f2525a.appECGConf.setECG_MARKING_PERIOD(parseInt);
                    }
                    this.f2526c.putInt("ECG_MARKING_PERIOD", this.f2525a.appECGConf.getECG_MARKING_PERIOD());
                    this.f2526c.commit();
                }
            }
            Toast.makeText(this, getString(i), Toast.LENGTH_LONG).show();
            this.f2526c.putInt("ECG_MARKING_PERIOD", this.f2525a.appECGConf.getECG_MARKING_PERIOD());
            this.f2526c.commit();
        }
    }

    /* renamed from: e */
    private void m2508e() {
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
            m2508e();
            onBackPressed();
        } else if (itemId == R.id.action_save) {
            m2507d();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.p_save), Toast.LENGTH_LONG).show();
            setResult(-1);
            m2508e();
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
        this.f2525a = (ECGApplication) getApplication();
        this.f2526c = this.f2525a.spECG_conf.edit();
        m2499a();
        if (this.f2535l) {
            m2502b();
        } else {
            m2505c();
        }
        super.onStart();
    }
}
