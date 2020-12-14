package com.hopetruly.ecg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.entity.ECGConf;
import com.hopetruly.ecg.entity.APPUploadFile;
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.part.net.MyHttpHelper;

import java.io.File;

public class SettingFragment extends Fragment {

    /* renamed from: A */
    RelativeLayout f2585A;

    /* renamed from: B */
    RelativeLayout f2586B;

    /* renamed from: C */
    RelativeLayout f2587C;

    /* renamed from: D */
    AlertDialog f2588D;

    /* renamed from: E */
    LinearLayout f2589E;

    /* renamed from: F */
    EditText f2590F;

    /* renamed from: G */
    EditText f2591G;

    /* renamed from: H */
    ECGApplication f2592H;

    /* renamed from: I */
    SharedPreferences.Editor f2593I;

    /* renamed from: J */
    String f2594J;

    /* renamed from: K */
    private BroadcastReceiver f2595K = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ") && intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(Sensor.BATTERY.getData().toString())) {
                int convertBAT = Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                if (SettingFragment.this.f2592H.appMachine != null && SettingFragment.this.f2623y.getVisibility() == 0) {
                    SettingFragment.this.f2592H.appMachine.setBatteryLevel(convertBAT);
                    TextView textView = SettingFragment.this.f2621w;
                    textView.setText(SettingFragment.this.f2592H.appMachine.getBatteryLevel() + " %");
                }
            }
        }
    };

    /* renamed from: L */
    private RadioGroup.OnCheckedChangeListener f2596L = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            String str = "";
            int a = 0;
            ECGConf aVar3;
            int i4;
            SharedPreferences.Editor edit = SettingFragment.this.f2592H.spECG_conf.edit();
            int id = radioGroup.getId();
            if (id != R.id.setting_time) {
                switch (id) {
                    case R.id.setting_alarm_delay /*2131165429*/:
                        if (i == SettingFragment.this.f2610l.getId()) {
                            SettingFragment.this.f2592H.appECGConf.setECG_ALARM_DELAY(10);
                        } else {
                            if (i == SettingFragment.this.f2611m.getId()) {
                                SettingFragment.this.f2592H.appECGConf.setECG_ALARM_DELAY(20);
                            } else if (i == SettingFragment.this.f2612n.getId()) {
                                SettingFragment.this.f2592H.appECGConf.setECG_ALARM_DELAY(30);
                            }
                        }
                        str = "ECG_ALARM_DELAY";
                        a = SettingFragment.this.f2592H.appECGConf.mo2664l();
                        break;
                    case R.id.setting_alarm_type /*2131165430*/:
                        if (i != SettingFragment.this.f2607i.getId()) {
                            if (i == SettingFragment.this.f2608j.getId()) {
                                SettingFragment.this.f2592H.appECGConf.setECG_ALARM_TYPE(1);
                            } else if (i == SettingFragment.this.f2609k.getId()) {
                                aVar3 = SettingFragment.this.f2592H.appECGConf;
                                i4 = 2;
                            }
                            str = "ECG_ALARM_TYPE";
                            a = SettingFragment.this.f2592H.appECGConf.mo2656h();
                            break;
                        } else {
                            aVar3 = SettingFragment.this.f2592H.appECGConf;
                            i4 = 0;
                        }
                        aVar3.setECG_ALARM_TYPE(i4);
                        str = "ECG_ALARM_TYPE";
                        a = SettingFragment.this.f2592H.appECGConf.mo2656h();
                }
            } else {
                if (i == SettingFragment.this.f2603e.getId()) {
                    SettingFragment.this.f2592H.appECGConf.setECG_MESURE_TIME(-1);
                } else {
                    if (i == SettingFragment.this.f2604f.getId()) {
                        SettingFragment.this.f2592H.appECGConf.setECG_MESURE_TIME(1);
                    } else if (i == SettingFragment.this.f2605g.getId()) {
                        SettingFragment.this.f2592H.appECGConf.setECG_MESURE_TIME(5);
                    } else if (i == SettingFragment.this.f2606h.getId()) {
                        SettingFragment.this.f2592H.appECGConf.setECG_MESURE_TIME(10);
                    }
                    str = "ECG_MESURE_TIME";
                    a = SettingFragment.this.f2592H.appECGConf.mo2641a();
                }
                str = "ECG_MESURE_TIME";
                a = SettingFragment.this.f2592H.appECGConf.mo2641a();
            }
            edit.putInt(str, a);
            edit.commit();
        }
    };

    /* renamed from: M */
    private View.OnClickListener f2597M = new View.OnClickListener() {
        public void onClick(View view) {
            Activity activity = new Activity();
            Class cls = null;
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.pedometer_setting_rl /*2131165378*/:
                    activity = SettingFragment.this.getActivity();
                    cls = SettingPedometerActivity.class;
                    break;
                case R.id.person_info_setting /*2131165381*/:
                    activity = SettingFragment.this.getActivity();
                    cls = PersonInfoSettingActivity.class;
                    break;
                case R.id.setting_check_update /*2131165436*/:
                    SettingFragment.this.m2539b();
                    return;
                case R.id.setting_update_sw /*2131165451*/:
                    intent.setClass(SettingFragment.this.getActivity(), FwUpdateActivity.class);
                    intent.putExtra("update", "Manual");
                    break;
                case R.id.sos_setting /*2131165484*/:
                    activity = SettingFragment.this.getActivity();
                    cls = SOSSMSActivity.class;
                    break;
                default:
                    return;
            }
            intent.setClass(activity, cls);
            SettingFragment.this.startActivity(intent);
        }
    };

    /* renamed from: N */
    private C0710a f2598N;

    /* renamed from: a */
    final String f2599a = "SettingFragment";

    /* renamed from: b */
    RadioGroup f2600b;

    /* renamed from: c */
    RadioGroup f2601c;

    /* renamed from: d */
    RadioGroup f2602d;

    /* renamed from: e */
    RadioButton f2603e;

    /* renamed from: f */
    RadioButton f2604f;

    /* renamed from: g */
    RadioButton f2605g;

    /* renamed from: h */
    RadioButton f2606h;

    /* renamed from: i */
    RadioButton f2607i;

    /* renamed from: j */
    RadioButton f2608j;

    /* renamed from: k */
    RadioButton f2609k;

    /* renamed from: l */
    RadioButton f2610l;

    /* renamed from: m */
    RadioButton f2611m;

    /* renamed from: n */
    RadioButton f2612n;

    /* renamed from: o */
    CheckBox f2613o;

    /* renamed from: p */
    CheckBox f2614p;

    /* renamed from: q */
    CheckBox f2615q;

    /* renamed from: r */
    CheckBox f2616r;

    /* renamed from: s */
    CheckBox f2617s;

    /* renamed from: t */
    TextView f2618t;

    /* renamed from: u */
    TextView f2619u;

    /* renamed from: v */
    TextView f2620v;

    /* renamed from: w */
    TextView f2621w;

    /* renamed from: x */
    TextView f2622x;

    /* renamed from: y */
    LinearLayout f2623y;

    /* renamed from: z */
    RelativeLayout f2624z;

    /* renamed from: com.hopetruly.ecg.activity.SettingFragment$a */
    class C0710a extends AsyncTask<String, Void, APPUploadFile> {
        C0710a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public APPUploadFile doInBackground(String... strArr) {
            if (isCancelled()) {
                return null;
            }
            return MyHttpHelper.check_update();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(APPUploadFile fVar) {
            if (!isCancelled()) {
                if (fVar == null) {
                    Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getString(R.string.net_connection_fail), 0).show();
                    return;
                }
                if (fVar.compareVersion(SettingFragment.this.f2594J)) {
                    SettingFragment.this.mo2363a(SettingFragment.this.f2594J, fVar.mo2694a(), fVar.mo2696b());
                } else {
                    Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getString(R.string.p_is_lastest), 0).show();
                }
                super.onPostExecute(fVar);
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getString(R.string.p_check_update), 0).show();
            super.onPreExecute();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x013f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0178  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x01a6  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x01ac  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x03a3  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x03a9  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x03fc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0402  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0405  */
    /* renamed from: a */
    private void m2537a() throws PackageManager.NameNotFoundException {
        int l;
        RadioButton radioButton = null;
        RadioButton radioButton2 = null;
        RadioButton radioButton3 = null;
        this.f2600b = (RadioGroup) getActivity().findViewById(R.id.setting_time);
        this.f2600b.setOnCheckedChangeListener(this.f2596L);
        this.f2603e = (RadioButton) getActivity().findViewById(R.id.time_unlimit);
        this.f2604f = (RadioButton) getActivity().findViewById(R.id.time_1m);
        this.f2605g = (RadioButton) getActivity().findViewById(R.id.time_5m);
        this.f2606h = (RadioButton) getActivity().findViewById(R.id.time_10m);
        int a = this.f2592H.appECGConf.mo2641a();
        if (a == -1) {
            radioButton3 = this.f2603e;
        } else if (a == 1) {
            radioButton3 = this.f2604f;
        } else if (a != 5) {
            if (a == 10) {
                radioButton3 = this.f2606h;
            }
            this.f2601c = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_delay);
            this.f2601c.setOnCheckedChangeListener(this.f2596L);
            this.f2610l = (RadioButton) getActivity().findViewById(R.id.time_10s);
            this.f2611m = (RadioButton) getActivity().findViewById(R.id.time_20s);
            this.f2612n = (RadioButton) getActivity().findViewById(R.id.time_30s);
            l = this.f2592H.appECGConf.mo2664l();
            if (l != 10) {
                radioButton2 = this.f2610l;
            } else if (l != 20) {
                if (l == 30) {
                    radioButton2 = this.f2612n;
                }
                this.f2614p = (CheckBox) getActivity().findViewById(R.id.setting_auto_upload);
                this.f2614p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ECGConf aVar;
                        int i;
                        if (z) {
                            aVar = SettingFragment.this.f2592H.appECGConf;
                            i = 1;
                        } else {
                            aVar = SettingFragment.this.f2592H.appECGConf;
                            i = 0;
                        }
                        aVar.setECG_AUTO_UPLOAD(i);
                        SettingFragment.this.f2593I.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2646c());
                        SettingFragment.this.f2593I.commit();
                    }
                });
                if (this.f2592H.appECGConf.mo2646c() == 1) {
                    this.f2614p.setChecked(true);
                } else {
                    this.f2614p.setChecked(false);
                }
                this.f2614p.findViewById(R.id.setting_auto_upload).setVisibility(8);
                this.f2613o = (CheckBox) getActivity().findViewById(R.id.setting_auto_record);
                this.f2613o.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z) {
                            SettingFragment.this.f2592H.appECGConf.setECG_AUTO_SAVE(1);
                            SettingFragment.this.f2614p.setEnabled(true);
                        } else {
                            SettingFragment.this.f2592H.appECGConf.setECG_AUTO_SAVE(0);
                            SettingFragment.this.f2614p.setEnabled(false);
                            SettingFragment.this.f2592H.appECGConf.setECG_AUTO_UPLOAD(0);
                            SettingFragment.this.f2614p.setChecked(false);
                        }
                        SettingFragment.this.f2593I.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2646c());
                        SettingFragment.this.f2593I.putInt("ECG_AUTO_SAVE", SettingFragment.this.f2592H.appECGConf.mo2644b());
                        SettingFragment.this.f2593I.commit();
                    }
                });
                if (this.f2592H.appECGConf.mo2644b() == 1) {
                    this.f2613o.setChecked(true);
                    this.f2614p.setEnabled(true);
                } else {
                    this.f2613o.setChecked(false);
                    this.f2614p.setEnabled(false);
                    this.f2592H.appECGConf.setECG_AUTO_UPLOAD(0);
                    this.f2614p.setChecked(false);
                }
                this.f2615q = (CheckBox) getActivity().findViewById(R.id.setting_realtime_upload);
                this.f2615q.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ECGConf aVar;
                        int i;
                        if (z) {
                            aVar = SettingFragment.this.f2592H.appECGConf;
                            i = 1;
                        } else {
                            aVar = SettingFragment.this.f2592H.appECGConf;
                            i = 0;
                        }
                        aVar.setECG_REALTIME_UPLOAD(i);
                        SettingFragment.this.f2593I.putInt("ECG_REALTIME_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2648d());
                        SettingFragment.this.f2593I.commit();
                    }
                });
                if (this.f2592H.appECGConf.mo2648d() == 1) {
                    this.f2615q.setChecked(true);
                } else {
                    this.f2615q.setChecked(false);
                }
                this.f2617s = (CheckBox) getActivity().findViewById(R.id.setting_ecg_waveform_analysis);
                this.f2617s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ECGConf aVar;
                        int i;
                        if (z) {
                            aVar = SettingFragment.this.f2592H.appECGConf;
                            i = 1;
                        } else {
                            aVar = SettingFragment.this.f2592H.appECGConf;
                            i = 0;
                        }
                        aVar.setECG_WAVEFORM_ANALYSIS(i);
                        SettingFragment.this.f2593I.putInt("ECG_WAVEFORM_ANALYSIS", SettingFragment.this.f2592H.appECGConf.mo2658i());
                        SettingFragment.this.f2593I.commit();
                    }
                });
                if (this.f2592H.appECGConf.mo2658i() == 1) {
                    this.f2617s.setChecked(true);
                } else {
                    this.f2617s.setChecked(false);
                }
                this.f2617s.findViewById(R.id.setting_ecg_waveform_analysis).setVisibility(8);
                ((RelativeLayout) getActivity().findViewById(R.id.person_info_setting)).setOnClickListener(this.f2597M);
                ((RelativeLayout) getActivity().findViewById(R.id.pedometer_setting_rl)).setOnClickListener(this.f2597M);
                this.f2586B = (RelativeLayout) getActivity().findViewById(R.id.sos_setting);
                this.f2586B.setOnClickListener(this.f2597M);
                this.f2623y = (LinearLayout) getActivity().findViewById(R.id.setting_device_info_ll);
                this.f2587C = (RelativeLayout) getActivity().findViewById(R.id.login_out);
                this.f2587C.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        SettingFragment.this.m2541c();
                    }
                });
                this.f2619u = (TextView) getActivity().findViewById(R.id.setting_machine_name);
                this.f2618t = (TextView) getActivity().findViewById(R.id.setting_machine_id);
                this.f2620v = (TextView) getActivity().findViewById(R.id.setting_machine_ver);
                this.f2621w = (TextView) getActivity().findViewById(R.id.setting_machine_battery);
                this.f2585A = (RelativeLayout) getActivity().findViewById(R.id.setting_update_sw);
                this.f2585A.setOnClickListener(this.f2597M);
                if (this.f2592H.appMachine != null) {
                    this.f2623y.setVisibility(0);
                    this.f2585A.setVisibility(0);
                    if (!(this.f2592H.appMachine == null || this.f2592H.appMachine.getName() == null)) {
                        this.f2619u.setText(this.f2592H.appMachine.getName());
                    }
                    if (!(this.f2592H.appMachine == null || this.f2592H.appMachine.getId() == null)) {
                        this.f2618t.setText(this.f2592H.appMachine.getId());
                    }
                    if (!(this.f2592H.appMachine == null || this.f2592H.appMachine.getFwRev() == null)) {
                        this.f2620v.setText(this.f2592H.appMachine.getFwRev());
                    }
                    TextView textView = this.f2621w;
                    textView.setText(this.f2592H.appMachine.getBatteryLevel() + " %");
                }
                getActivity().invalidateOptionsMenu();
                this.f2624z = (RelativeLayout) getActivity().findViewById(R.id.setting_check_update);
                this.f2624z.setOnClickListener(this.f2597M);
                this.f2590F = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_max);
                this.f2590F.setText(String.valueOf(this.f2592H.appECGConf.mo2650e()));
                this.f2590F.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    /* renamed from: b */
                    private boolean f2629b = false;

                    public void onFocusChange(View view, boolean z) {
                        Context applicationContext;
                        Resources resources;
                        int i = 0;
                        if (z && !this.f2629b) {
                            this.f2629b = true;
                        }
                        if (!z && this.f2629b) {
                            EditText editText = (EditText) view;
                            if (editText.getText().toString().length() == 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_null;
                            } else {
                                Log.d("SettingFragment", editText.getText().toString());
                                int parseInt = Integer.parseInt(editText.getText().toString());
                                if (parseInt <= SettingFragment.this.f2592H.appECGConf.mo2652f()) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_less_min;
                                } else if (parseInt <= 0) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_zero;
                                } else {
                                    SettingFragment.this.f2592H.appECGConf.setECG_ALARM_RATE_MAX(parseInt);
                                }
                            }
                            Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getResources().getString(i), 0).show();
                            editText.setText(String.valueOf(SettingFragment.this.f2592H.appECGConf.mo2650e()));
                        }
                        SettingFragment.this.f2593I.putInt("ECG_ALARM_RATE_MAX", SettingFragment.this.f2592H.appECGConf.mo2650e());
                        SettingFragment.this.f2593I.commit();
                    }
                });
                this.f2591G = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_min);
                this.f2591G.setText(String.valueOf(this.f2592H.appECGConf.mo2652f()));
                this.f2591G.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    /* renamed from: b */
                    private boolean f2631b = false;

                    public void onFocusChange(View view, boolean z) {
                        Context applicationContext;
                        Resources resources;
                        int i;
                        if (z && !this.f2631b) {
                            this.f2631b = true;
                        }
                        if (!z && this.f2631b) {
                            EditText editText = (EditText) view;
                            if (editText.getText().toString().length() == 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_null;
                            } else {
                                Log.d("SettingFragment", editText.getText().toString());
                                int parseInt = Integer.parseInt(editText.getText().toString());
                                if (parseInt >= SettingFragment.this.f2592H.appECGConf.mo2650e()) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_bigger_max;
                                } else if (parseInt <= 0) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_zero;
                                } else {
                                    SettingFragment.this.f2592H.appECGConf.setECG_ALARM_RATE_MIN(parseInt);
                                    SettingFragment.this.f2593I.putInt("ECG_ALARM_RATE_MIN", SettingFragment.this.f2592H.appECGConf.mo2652f());
                                    SettingFragment.this.f2593I.commit();
                                    return;
                                }
                            }
                            Toast.makeText(applicationContext, resources.getString(i), 0).show();
                            editText.setText(String.valueOf(SettingFragment.this.f2592H.appECGConf.mo2652f()));
                        }
                    }
                });
                this.f2589E = (LinearLayout) getActivity().findViewById(R.id.set_ecg_alarm_div);
                this.f2616r = (CheckBox) getActivity().findViewById(R.id.set_ecg_alarm);
                this.f2616r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        LinearLayout linearLayout;
                        int i;
                        if (z) {
                            linearLayout = SettingFragment.this.f2589E;
                            i = 0;
                        } else {
                            linearLayout = SettingFragment.this.f2589E;
                            i = 8;
                        }
                        linearLayout.setVisibility(i);
                        SettingFragment.this.f2592H.appECGConf.setECG_ALARM_ENABLE(z);
                        SettingFragment.this.f2593I.putBoolean("ECG_ALARM_ENABLE", SettingFragment.this.f2592H.appECGConf.mo2655g());
                        SettingFragment.this.f2593I.commit();
                    }
                });
                this.f2616r.setChecked(this.f2592H.appECGConf.mo2655g());
                if (this.f2592H.appECGConf.mo2655g()) {
                    this.f2589E.setVisibility(0);
                } else {
                    this.f2589E.setVisibility(8);
                }
                this.f2602d = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_type);
                this.f2602d.setOnCheckedChangeListener(this.f2596L);
                this.f2607i = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration);
                this.f2608j = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_sound);
                this.f2609k = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration_sound);
                switch (this.f2592H.appECGConf.mo2656h()) {
                    case 0:
                        radioButton = this.f2607i;
                        break;
                    case 1:
                        radioButton = this.f2608j;
                        break;
                    default:
                        radioButton = this.f2609k;
                        break;
                }
                radioButton.setChecked(true);
                this.f2622x = (TextView) getActivity().findViewById(R.id.setting_ver_info);
                this.f2594J = getActivity().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(getString(R.string.app_name));
                stringBuffer.append(" V");
                stringBuffer.append(this.f2594J);
                this.f2622x.setText(stringBuffer.toString());
            } else {
                radioButton2 = this.f2611m;
            }
            radioButton2.setChecked(true);
            this.f2614p = (CheckBox) getActivity().findViewById(R.id.setting_auto_upload);
            this.f2614p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ECGConf aVar;
                    int i;
                    if (z) {
                        aVar = SettingFragment.this.f2592H.appECGConf;
                        i = 1;
                    } else {
                        aVar = SettingFragment.this.f2592H.appECGConf;
                        i = 0;
                    }
                    aVar.setECG_AUTO_UPLOAD(i);
                    SettingFragment.this.f2593I.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2646c());
                    SettingFragment.this.f2593I.commit();
                }
            });
            if (this.f2592H.appECGConf.mo2646c() == 1) {
            }
            this.f2614p.findViewById(R.id.setting_auto_upload).setVisibility(8);
            this.f2613o = (CheckBox) getActivity().findViewById(R.id.setting_auto_record);
            this.f2613o.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        SettingFragment.this.f2592H.appECGConf.setECG_AUTO_SAVE(1);
                        SettingFragment.this.f2614p.setEnabled(true);
                    } else {
                        SettingFragment.this.f2592H.appECGConf.setECG_AUTO_SAVE(0);
                        SettingFragment.this.f2614p.setEnabled(false);
                        SettingFragment.this.f2592H.appECGConf.setECG_AUTO_UPLOAD(0);
                        SettingFragment.this.f2614p.setChecked(false);
                    }
                    SettingFragment.this.f2593I.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2646c());
                    SettingFragment.this.f2593I.putInt("ECG_AUTO_SAVE", SettingFragment.this.f2592H.appECGConf.mo2644b());
                    SettingFragment.this.f2593I.commit();
                }
            });
            if (this.f2592H.appECGConf.mo2644b() == 1) {
            }
            this.f2615q = (CheckBox) getActivity().findViewById(R.id.setting_realtime_upload);
            this.f2615q.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ECGConf aVar;
                    int i;
                    if (z) {
                        aVar = SettingFragment.this.f2592H.appECGConf;
                        i = 1;
                    } else {
                        aVar = SettingFragment.this.f2592H.appECGConf;
                        i = 0;
                    }
                    aVar.setECG_REALTIME_UPLOAD(i);
                    SettingFragment.this.f2593I.putInt("ECG_REALTIME_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2648d());
                    SettingFragment.this.f2593I.commit();
                }
            });
            if (this.f2592H.appECGConf.mo2648d() == 1) {
            }
            this.f2617s = (CheckBox) getActivity().findViewById(R.id.setting_ecg_waveform_analysis);
            this.f2617s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ECGConf aVar;
                    int i;
                    if (z) {
                        aVar = SettingFragment.this.f2592H.appECGConf;
                        i = 1;
                    } else {
                        aVar = SettingFragment.this.f2592H.appECGConf;
                        i = 0;
                    }
                    aVar.setECG_WAVEFORM_ANALYSIS(i);
                    SettingFragment.this.f2593I.putInt("ECG_WAVEFORM_ANALYSIS", SettingFragment.this.f2592H.appECGConf.mo2658i());
                    SettingFragment.this.f2593I.commit();
                }
            });
            if (this.f2592H.appECGConf.mo2658i() == 1) {
            }
            this.f2617s.findViewById(R.id.setting_ecg_waveform_analysis).setVisibility(8);
            ((RelativeLayout) getActivity().findViewById(R.id.person_info_setting)).setOnClickListener(this.f2597M);
            ((RelativeLayout) getActivity().findViewById(R.id.pedometer_setting_rl)).setOnClickListener(this.f2597M);
            this.f2586B = (RelativeLayout) getActivity().findViewById(R.id.sos_setting);
            this.f2586B.setOnClickListener(this.f2597M);
            this.f2623y = (LinearLayout) getActivity().findViewById(R.id.setting_device_info_ll);
            this.f2587C = (RelativeLayout) getActivity().findViewById(R.id.login_out);
            this.f2587C.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SettingFragment.this.m2541c();
                }
            });
            this.f2619u = (TextView) getActivity().findViewById(R.id.setting_machine_name);
            this.f2618t = (TextView) getActivity().findViewById(R.id.setting_machine_id);
            this.f2620v = (TextView) getActivity().findViewById(R.id.setting_machine_ver);
            this.f2621w = (TextView) getActivity().findViewById(R.id.setting_machine_battery);
            this.f2585A = (RelativeLayout) getActivity().findViewById(R.id.setting_update_sw);
            this.f2585A.setOnClickListener(this.f2597M);
            if (this.f2592H.appMachine != null) {
            }
            getActivity().invalidateOptionsMenu();
            this.f2624z = (RelativeLayout) getActivity().findViewById(R.id.setting_check_update);
            this.f2624z.setOnClickListener(this.f2597M);
            this.f2590F = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_max);
            this.f2590F.setText(String.valueOf(this.f2592H.appECGConf.mo2650e()));
            this.f2590F.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                /* renamed from: b */
                private boolean f2629b = false;

                public void onFocusChange(View view, boolean z) {
                    Context applicationContext;
                    Resources resources;
                    int i = 0;
                    if (z && !this.f2629b) {
                        this.f2629b = true;
                    }
                    if (!z && this.f2629b) {
                        EditText editText = (EditText) view;
                        if (editText.getText().toString().length() == 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_null;
                        } else {
                            Log.d("SettingFragment", editText.getText().toString());
                            int parseInt = Integer.parseInt(editText.getText().toString());
                            if (parseInt <= SettingFragment.this.f2592H.appECGConf.mo2652f()) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_less_min;
                            } else if (parseInt <= 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_zero;
                            } else {
                                SettingFragment.this.f2592H.appECGConf.setECG_ALARM_RATE_MAX(parseInt);
                            }
                        }
                        Toast.makeText(getActivity().getApplicationContext(), SettingFragment.this.getResources().getString(i), 0).show();
                        editText.setText(String.valueOf(SettingFragment.this.f2592H.appECGConf.mo2650e()));
                    }
                    SettingFragment.this.f2593I.putInt("ECG_ALARM_RATE_MAX", SettingFragment.this.f2592H.appECGConf.mo2650e());
                    SettingFragment.this.f2593I.commit();
                }
            });
            this.f2591G = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_min);
            this.f2591G.setText(String.valueOf(this.f2592H.appECGConf.mo2652f()));
            this.f2591G.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                /* renamed from: b */
                private boolean f2631b = false;

                public void onFocusChange(View view, boolean z) {
                    Context applicationContext;
                    Resources resources;
                    int i;
                    if (z && !this.f2631b) {
                        this.f2631b = true;
                    }
                    if (!z && this.f2631b) {
                        EditText editText = (EditText) view;
                        if (editText.getText().toString().length() == 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_null;
                        } else {
                            Log.d("SettingFragment", editText.getText().toString());
                            int parseInt = Integer.parseInt(editText.getText().toString());
                            if (parseInt >= SettingFragment.this.f2592H.appECGConf.mo2650e()) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_bigger_max;
                            } else if (parseInt <= 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_zero;
                            } else {
                                SettingFragment.this.f2592H.appECGConf.setECG_ALARM_RATE_MIN(parseInt);
                                SettingFragment.this.f2593I.putInt("ECG_ALARM_RATE_MIN", SettingFragment.this.f2592H.appECGConf.mo2652f());
                                SettingFragment.this.f2593I.commit();
                                return;
                            }
                        }
                        Toast.makeText(applicationContext, resources.getString(i), 0).show();
                        editText.setText(String.valueOf(SettingFragment.this.f2592H.appECGConf.mo2652f()));
                    }
                }
            });
            this.f2589E = (LinearLayout) getActivity().findViewById(R.id.set_ecg_alarm_div);
            this.f2616r = (CheckBox) getActivity().findViewById(R.id.set_ecg_alarm);
            this.f2616r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    LinearLayout linearLayout;
                    int i;
                    if (z) {
                        linearLayout = SettingFragment.this.f2589E;
                        i = 0;
                    } else {
                        linearLayout = SettingFragment.this.f2589E;
                        i = 8;
                    }
                    linearLayout.setVisibility(i);
                    SettingFragment.this.f2592H.appECGConf.setECG_ALARM_ENABLE(z);
                    SettingFragment.this.f2593I.putBoolean("ECG_ALARM_ENABLE", SettingFragment.this.f2592H.appECGConf.mo2655g());
                    SettingFragment.this.f2593I.commit();
                }
            });
            this.f2616r.setChecked(this.f2592H.appECGConf.mo2655g());
            if (this.f2592H.appECGConf.mo2655g()) {
            }
            this.f2602d = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_type);
            this.f2602d.setOnCheckedChangeListener(this.f2596L);
            this.f2607i = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration);
            this.f2608j = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_sound);
            this.f2609k = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration_sound);
            switch (this.f2592H.appECGConf.mo2656h()) {
                case 0:
                    break;
                case 1:
                    break;
            }
            radioButton.setChecked(true);
            this.f2622x = (TextView) getActivity().findViewById(R.id.setting_ver_info);
            this.f2594J = getActivity().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(getString(R.string.app_name));
            stringBuffer2.append(" V");
            stringBuffer2.append(this.f2594J);
            this.f2622x.setText(stringBuffer2.toString());
        } else {
            radioButton3 = this.f2605g;
        }
        radioButton3.setChecked(true);
        this.f2601c = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_delay);
        this.f2601c.setOnCheckedChangeListener(this.f2596L);
        this.f2610l = (RadioButton) getActivity().findViewById(R.id.time_10s);
        this.f2611m = (RadioButton) getActivity().findViewById(R.id.time_20s);
        this.f2612n = (RadioButton) getActivity().findViewById(R.id.time_30s);
        l = this.f2592H.appECGConf.mo2664l();
        if (l != 10) {
            radioButton2 = f2611m;
        }
        radioButton2.setChecked(true);
        this.f2614p = (CheckBox) getActivity().findViewById(R.id.setting_auto_upload);
        this.f2614p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ECGConf aVar;
                int i;
                if (z) {
                    aVar = SettingFragment.this.f2592H.appECGConf;
                    i = 1;
                } else {
                    aVar = SettingFragment.this.f2592H.appECGConf;
                    i = 0;
                }
                aVar.setECG_AUTO_UPLOAD(i);
                SettingFragment.this.f2593I.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2646c());
                SettingFragment.this.f2593I.commit();
            }
        });
        if (this.f2592H.appECGConf.mo2646c() == 1) {
        }
        this.f2614p.findViewById(R.id.setting_auto_upload).setVisibility(8);
        this.f2613o = (CheckBox) getActivity().findViewById(R.id.setting_auto_record);
        this.f2613o.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    SettingFragment.this.f2592H.appECGConf.setECG_AUTO_SAVE(1);
                    SettingFragment.this.f2614p.setEnabled(true);
                } else {
                    SettingFragment.this.f2592H.appECGConf.setECG_AUTO_SAVE(0);
                    SettingFragment.this.f2614p.setEnabled(false);
                    SettingFragment.this.f2592H.appECGConf.setECG_AUTO_UPLOAD(0);
                    SettingFragment.this.f2614p.setChecked(false);
                }
                SettingFragment.this.f2593I.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2646c());
                SettingFragment.this.f2593I.putInt("ECG_AUTO_SAVE", SettingFragment.this.f2592H.appECGConf.mo2644b());
                SettingFragment.this.f2593I.commit();
            }
        });
        if (this.f2592H.appECGConf.mo2644b() == 1) {
        }
        this.f2615q = (CheckBox) getActivity().findViewById(R.id.setting_realtime_upload);
        this.f2615q.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ECGConf aVar;
                int i;
                if (z) {
                    aVar = SettingFragment.this.f2592H.appECGConf;
                    i = 1;
                } else {
                    aVar = SettingFragment.this.f2592H.appECGConf;
                    i = 0;
                }
                aVar.setECG_REALTIME_UPLOAD(i);
                SettingFragment.this.f2593I.putInt("ECG_REALTIME_UPLOAD", SettingFragment.this.f2592H.appECGConf.mo2648d());
                SettingFragment.this.f2593I.commit();
            }
        });
        if (this.f2592H.appECGConf.mo2648d() == 1) {
        }
        this.f2617s = (CheckBox) getActivity().findViewById(R.id.setting_ecg_waveform_analysis);
        this.f2617s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ECGConf aVar;
                int i;
                if (z) {
                    aVar = SettingFragment.this.f2592H.appECGConf;
                    i = 1;
                } else {
                    aVar = SettingFragment.this.f2592H.appECGConf;
                    i = 0;
                }
                aVar.setECG_WAVEFORM_ANALYSIS(i);
                SettingFragment.this.f2593I.putInt("ECG_WAVEFORM_ANALYSIS", SettingFragment.this.f2592H.appECGConf.mo2658i());
                SettingFragment.this.f2593I.commit();
            }
        });
        if (this.f2592H.appECGConf.mo2658i() == 1) {
        }
        this.f2617s.findViewById(R.id.setting_ecg_waveform_analysis).setVisibility(8);
        ((RelativeLayout) getActivity().findViewById(R.id.person_info_setting)).setOnClickListener(this.f2597M);
        ((RelativeLayout) getActivity().findViewById(R.id.pedometer_setting_rl)).setOnClickListener(this.f2597M);
        this.f2586B = (RelativeLayout) getActivity().findViewById(R.id.sos_setting);
        this.f2586B.setOnClickListener(this.f2597M);
        this.f2623y = (LinearLayout) getActivity().findViewById(R.id.setting_device_info_ll);
        this.f2587C = (RelativeLayout) getActivity().findViewById(R.id.login_out);
        this.f2587C.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingFragment.this.m2541c();
            }
        });
        this.f2619u = (TextView) getActivity().findViewById(R.id.setting_machine_name);
        this.f2618t = (TextView) getActivity().findViewById(R.id.setting_machine_id);
        this.f2620v = (TextView) getActivity().findViewById(R.id.setting_machine_ver);
        this.f2621w = (TextView) getActivity().findViewById(R.id.setting_machine_battery);
        this.f2585A = (RelativeLayout) getActivity().findViewById(R.id.setting_update_sw);
        this.f2585A.setOnClickListener(this.f2597M);
        if (this.f2592H.appMachine != null) {
        }
        getActivity().invalidateOptionsMenu();
        this.f2624z = (RelativeLayout) getActivity().findViewById(R.id.setting_check_update);
        this.f2624z.setOnClickListener(this.f2597M);
        this.f2590F = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_max);
        this.f2590F.setText(String.valueOf(this.f2592H.appECGConf.mo2650e()));
        this.f2590F.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            /* renamed from: b */
            private boolean f2629b = false;

            public void onFocusChange(View view, boolean z) {
                Context applicationContext;
                Resources resources;
                int i = 0;
                if (z && !this.f2629b) {
                    this.f2629b = true;
                }
                if (!z && this.f2629b) {
                    EditText editText = (EditText) view;
                    if (editText.getText().toString().length() == 0) {
                        applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                        resources = SettingFragment.this.getResources();
                        i = R.string.p_ha_null;
                    } else {
                        Log.d("SettingFragment", editText.getText().toString());
                        int parseInt = Integer.parseInt(editText.getText().toString());
                        if (parseInt <= SettingFragment.this.f2592H.appECGConf.mo2652f()) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_less_min;
                        } else if (parseInt <= 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_zero;
                        } else {
                            SettingFragment.this.f2592H.appECGConf.setECG_ALARM_RATE_MAX(parseInt);
                        }
                    }
                    Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getResources().getString(i), 0).show();
                    editText.setText(String.valueOf(SettingFragment.this.f2592H.appECGConf.mo2650e()));
                }
                SettingFragment.this.f2593I.putInt("ECG_ALARM_RATE_MAX", SettingFragment.this.f2592H.appECGConf.mo2650e());
                SettingFragment.this.f2593I.commit();
            }
        });
        this.f2591G = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_min);
        this.f2591G.setText(String.valueOf(this.f2592H.appECGConf.mo2652f()));
        this.f2591G.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            /* renamed from: b */
            private boolean f2631b = false;

            public void onFocusChange(View view, boolean z) {
                Context applicationContext;
                Resources resources;
                int i;
                if (z && !this.f2631b) {
                    this.f2631b = true;
                }
                if (!z && this.f2631b) {
                    EditText editText = (EditText) view;
                    if (editText.getText().toString().length() == 0) {
                        applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                        resources = SettingFragment.this.getResources();
                        i = R.string.p_ha_null;
                    } else {
                        Log.d("SettingFragment", editText.getText().toString());
                        int parseInt = Integer.parseInt(editText.getText().toString());
                        if (parseInt >= SettingFragment.this.f2592H.appECGConf.mo2650e()) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_bigger_max;
                        } else if (parseInt <= 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_zero;
                        } else {
                            SettingFragment.this.f2592H.appECGConf.setECG_ALARM_RATE_MIN(parseInt);
                            SettingFragment.this.f2593I.putInt("ECG_ALARM_RATE_MIN", SettingFragment.this.f2592H.appECGConf.mo2652f());
                            SettingFragment.this.f2593I.commit();
                            return;
                        }
                    }
                    Toast.makeText(applicationContext, resources.getString(i), 0).show();
                    editText.setText(String.valueOf(SettingFragment.this.f2592H.appECGConf.mo2652f()));
                }
            }
        });
        this.f2589E = (LinearLayout) getActivity().findViewById(R.id.set_ecg_alarm_div);
        this.f2616r = (CheckBox) getActivity().findViewById(R.id.set_ecg_alarm);
        this.f2616r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LinearLayout linearLayout;
                int i;
                if (z) {
                    linearLayout = SettingFragment.this.f2589E;
                    i = 0;
                } else {
                    linearLayout = SettingFragment.this.f2589E;
                    i = 8;
                }
                linearLayout.setVisibility(i);
                SettingFragment.this.f2592H.appECGConf.setECG_ALARM_ENABLE(z);
                SettingFragment.this.f2593I.putBoolean("ECG_ALARM_ENABLE", SettingFragment.this.f2592H.appECGConf.mo2655g());
                SettingFragment.this.f2593I.commit();
            }
        });
        this.f2616r.setChecked(this.f2592H.appECGConf.mo2655g());
        if (this.f2592H.appECGConf.mo2655g()) {
        }
        this.f2602d = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_type);
        this.f2602d.setOnCheckedChangeListener(this.f2596L);
        this.f2607i = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration);
        this.f2608j = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_sound);
        this.f2609k = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration_sound);
        switch (this.f2592H.appECGConf.mo2656h()) {
            case 0:
                break;
            case 1:
                break;
        }
        radioButton.setChecked(true);
        this.f2622x = (TextView) getActivity().findViewById(R.id.setting_ver_info);
        try {
            this.f2594J = getActivity().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer22 = new StringBuffer();
        stringBuffer22.append(getString(R.string.app_name));
        stringBuffer22.append(" V");
        stringBuffer22.append(this.f2594J);
        this.f2622x.setText(stringBuffer22.toString());
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2539b() {
        if (this.f2598N == null || this.f2598N.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2598N = new C0710a();
            this.f2598N.execute(new String[0]);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2541c() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.logout_title));
        builder.setMessage(getString(R.string.logout_msg));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SettingFragment.this.f2592H.mSwConf.setSW_AUTO_LOGIN(0);
                SharedPreferences.Editor edit = SettingFragment.this.f2592H.spSw_conf.edit();
                edit.putInt("SW_AUTO_LOGIN", SettingFragment.this.f2592H.mSwConf.getAuto_login());
                edit.commit();
                SettingFragment.this.getActivity().startActivity(new Intent(SettingFragment.this.getActivity(), LoginActivity.class));
                LocalBroadcastManager.getInstance(SettingFragment.this.getActivity().getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.activity.BaseActivity.LOGINOUT_ACTION"));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        this.f2588D = builder.create();
        this.f2588D.show();
    }

    /* renamed from: a */
    public void mo2363a(String str, String str2, final String str3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.new_version));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getString(R.string.find_new_ver));
        stringBuffer.append("\n");
        stringBuffer.append(getString(R.string.cur_version));
        stringBuffer.append(": ");
        stringBuffer.append(str);
        stringBuffer.append("\n");
        stringBuffer.append(getString(R.string.new_version));
        stringBuffer.append(": ");
        stringBuffer.append(str2);
        stringBuffer.append("\n");
        builder.setMessage(stringBuffer.toString());
        builder.setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    StringBuffer stringBuffer = new StringBuffer(Environment.getExternalStorageDirectory().getAbsolutePath());
                    stringBuffer.append(File.separator);
                    stringBuffer.append("hopetruly");
                    stringBuffer.append(File.separator);
                    stringBuffer.append("update");
                    SettingFragment.this.f2592H.appMainService.mmainNetService.mo2822a(str3, stringBuffer.toString(), "ecg.apk");
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    public void onCreate(Bundle bundle) {
        setHasOptionsMenu(true);
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ");
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.f2595K, intentFilter);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
    }

    public void onDestroy() {
        LogUtils.logE("SettingFragment", "onDestroy~~~~");
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.f2595K);
        if (this.f2598N != null && this.f2598N.getStatus() == AsyncTask.Status.RUNNING) {
            this.f2598N.cancel(true);
            this.f2598N = null;
        }
        if (this.f2588D != null) {
            this.f2588D.dismiss();
        }
        super.onDestroy();
    }

    public void onStart() {
        this.f2592H = (ECGApplication) getActivity().getApplication();
        this.f2593I = this.f2592H.spECG_conf.edit();
        try {
            m2537a();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!(this.f2592H == null || this.f2592H.appMainService == null || !this.f2592H.appMainService.isMBleConn())) {
            this.f2592H.appMainService.mo2732f();
        }
        super.onStart();
    }
}
