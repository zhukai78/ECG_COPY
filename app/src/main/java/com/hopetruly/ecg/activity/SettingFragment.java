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
    RelativeLayout rv_setting_update_sw;

    /* renamed from: B */
    RelativeLayout rv_sos_setting;

    /* renamed from: C */
    RelativeLayout rv_login_out;

    /* renamed from: D */
    AlertDialog logout_titleDialog;

    /* renamed from: E */
    LinearLayout ll_set_ecg_alarm_div;

    /* renamed from: F */
    EditText edt_ecg_warn_rate_max;

    /* renamed from: G */
    EditText edt_ecg_warn_rate_min;

    /* renamed from: H */
    ECGApplication SetFragApp;

    /* renamed from: I */
    SharedPreferences.Editor SetFragSpEditor;

    /* renamed from: J */
    String mversionName;

    /* renamed from: K */
    private BroadcastReceiver mBatteryBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ") && intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(Sensor.BATTERY.getData().toString())) {
                int convertBAT = Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                if (SettingFragment.this.SetFragApp.appMachine != null && SettingFragment.this.ll_setting_device_info.getVisibility() == 0) {
                    SettingFragment.this.SetFragApp.appMachine.setBatteryLevel(convertBAT);
                    TextView textView = SettingFragment.this.tv_setting_machine_battery;
                    textView.setText(SettingFragment.this.SetFragApp.appMachine.getBatteryLevel() + " %");
                }
            }
        }
    };

    /* renamed from: L */
    private RadioGroup.OnCheckedChangeListener mRadioGroupOnCheckedListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            String str = "";
            int a = 0;
            ECGConf aVar3;
            int i4;
            SharedPreferences.Editor edit = SettingFragment.this.SetFragApp.spECG_conf.edit();
            int id = radioGroup.getId();
            if (id != R.id.setting_time) {
                switch (id) {
                    case R.id.setting_alarm_delay /*2131165429*/:
                        if (i == SettingFragment.this.rb_time_10s.getId()) {
                            SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_DELAY(10);
                        } else {
                            if (i == SettingFragment.this.rb_time_20s.getId()) {
                                SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_DELAY(20);
                            } else if (i == SettingFragment.this.rb_time_30s.getId()) {
                                SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_DELAY(30);
                            }
                        }
                        str = "ECG_ALARM_DELAY";
                        a = SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_DELAY();
                        break;
                    case R.id.setting_alarm_type /*2131165430*/:
                        if (i != SettingFragment.this.rb_setting_alarm_type_vibration.getId()) {
                            if (i == SettingFragment.this.rb_setting_alarm_type_sound.getId()) {
                                SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_TYPE(1);
                            } else if (i == SettingFragment.this.rb_setting_alarm_type_vibration_sound.getId()) {
                                aVar3 = SettingFragment.this.SetFragApp.appECGConf;
                                i4 = 2;
                            }
                            str = "ECG_ALARM_TYPE";
                            a = SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_TYPE();
                            break;
                        } else {
                            aVar3 = SettingFragment.this.SetFragApp.appECGConf;
                            i4 = 0;
                        }
                        aVar3.setECG_ALARM_TYPE(i4);
                        str = "ECG_ALARM_TYPE";
                        a = SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_TYPE();
                }
            } else {
                if (i == SettingFragment.this.rb_time_unlimit.getId()) {
                    SettingFragment.this.SetFragApp.appECGConf.setECG_MESURE_TIME(-1);
                } else {
                    if (i == SettingFragment.this.rb_time_1m.getId()) {
                        SettingFragment.this.SetFragApp.appECGConf.setECG_MESURE_TIME(1);
                    } else if (i == SettingFragment.this.rb_time_5m.getId()) {
                        SettingFragment.this.SetFragApp.appECGConf.setECG_MESURE_TIME(5);
                    } else if (i == SettingFragment.this.rb_time_10m.getId()) {
                        SettingFragment.this.SetFragApp.appECGConf.setECG_MESURE_TIME(10);
                    }
                    str = "ECG_MESURE_TIME";
                    a = SettingFragment.this.SetFragApp.appECGConf.getECG_MESURE_TIME();
                }
                str = "ECG_MESURE_TIME";
                a = SettingFragment.this.SetFragApp.appECGConf.getECG_MESURE_TIME();
            }
            edit.putInt(str, a);
            edit.commit();
        }
    };

    /* renamed from: M */
    private View.OnClickListener settingOnClickListener = new View.OnClickListener() {
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
                    SettingFragment.this.checkupdate();
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
    private CheckUpdataAsyncTask mCheckUpdataAsyncTask;

    /* renamed from: a */
    final String TAG = "SettingFragment";

    /* renamed from: b */
    RadioGroup rg_setting_time;

    /* renamed from: c */
    RadioGroup rg_setting_alarm_delay;

    /* renamed from: d */
    RadioGroup rg_setting_alarm_type;

    /* renamed from: e */
    RadioButton rb_time_unlimit;

    /* renamed from: f */
    RadioButton rb_time_1m;

    /* renamed from: g */
    RadioButton rb_time_5m;

    /* renamed from: h */
    RadioButton rb_time_10m;

    /* renamed from: i */
    RadioButton rb_setting_alarm_type_vibration;

    /* renamed from: j */
    RadioButton rb_setting_alarm_type_sound;

    /* renamed from: k */
    RadioButton rb_setting_alarm_type_vibration_sound;

    /* renamed from: l */
    RadioButton rb_time_10s;

    /* renamed from: m */
    RadioButton rb_time_20s;

    /* renamed from: n */
    RadioButton rb_time_30s;

    /* renamed from: o */
    CheckBox cb_setting_auto_record;

    /* renamed from: p */
    CheckBox cb_setting_auto_upload;

    /* renamed from: q */
    CheckBox cb_setting_realtime_upload;

    /* renamed from: r */
    CheckBox cb_set_ecg_alarm;

    /* renamed from: s */
    CheckBox cb_setting_ecg_waveform_analysis;

    /* renamed from: t */
    TextView tv_setting_machine_id;

    /* renamed from: u */
    TextView tv_setting_machine_name;

    /* renamed from: v */
    TextView tv_setting_machine_ver;

    /* renamed from: w */
    TextView tv_setting_machine_battery;

    /* renamed from: x */
    TextView tv_setting_ver_info;

    /* renamed from: y */
    LinearLayout ll_setting_device_info;

    /* renamed from: z */
    RelativeLayout rv_setting_check_update;

    /* renamed from: com.hopetruly.ecg.activity.SettingFragment$a */
    class CheckUpdataAsyncTask extends AsyncTask<String, Void, APPUploadFile> {
        CheckUpdataAsyncTask() {
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
                    Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getString(R.string.net_connection_fail),Toast.LENGTH_LONG).show();
                    return;
                }
                if (fVar.compareVersion(SettingFragment.this.mversionName)) {
                    SettingFragment.this.showNewVersionDialog(SettingFragment.this.mversionName, fVar.mo2694a(), fVar.mo2696b());
                } else {
                    Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getString(R.string.p_is_lastest),Toast.LENGTH_LONG).show();
                }
                super.onPostExecute(fVar);
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getString(R.string.p_check_update), Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }
    }


    /* renamed from: a */
    private void initView() throws PackageManager.NameNotFoundException {
        int l;
        RadioButton radioButton = null;
        RadioButton radioButton2 = null;
        RadioButton radioButton3 = null;
        this.rg_setting_time = (RadioGroup) getActivity().findViewById(R.id.setting_time);
        this.rg_setting_time.setOnCheckedChangeListener(this.mRadioGroupOnCheckedListener);
        this.rb_time_unlimit = (RadioButton) getActivity().findViewById(R.id.time_unlimit);
        this.rb_time_1m = (RadioButton) getActivity().findViewById(R.id.time_1m);
        this.rb_time_5m = (RadioButton) getActivity().findViewById(R.id.time_5m);
        this.rb_time_10m = (RadioButton) getActivity().findViewById(R.id.time_10m);
        int a = this.SetFragApp.appECGConf.getECG_MESURE_TIME();
        if (a == -1) {
            radioButton3 = this.rb_time_unlimit;
        } else if (a == 1) {
            radioButton3 = this.rb_time_1m;
        } else if (a != 5) {
            if (a == 10) {
                radioButton3 = this.rb_time_10m;
            }
            this.rg_setting_alarm_delay = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_delay);
            this.rg_setting_alarm_delay.setOnCheckedChangeListener(this.mRadioGroupOnCheckedListener);
            this.rb_time_10s = (RadioButton) getActivity().findViewById(R.id.time_10s);
            this.rb_time_20s = (RadioButton) getActivity().findViewById(R.id.time_20s);
            this.rb_time_30s = (RadioButton) getActivity().findViewById(R.id.time_30s);
            l = this.SetFragApp.appECGConf.getECG_ALARM_DELAY();
            if (l != 10) {
                radioButton2 = this.rb_time_10s;
            } else if (l != 20) {
                if (l == 30) {
                    radioButton2 = this.rb_time_30s;
                }
                this.cb_setting_auto_upload = (CheckBox) getActivity().findViewById(R.id.setting_auto_upload);
                this.cb_setting_auto_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ECGConf aVar;
                        int i;
                        if (z) {
                            aVar = SettingFragment.this.SetFragApp.appECGConf;
                            i = 1;
                        } else {
                            aVar = SettingFragment.this.SetFragApp.appECGConf;
                            i = 0;
                        }
                        aVar.setECG_AUTO_UPLOAD(i);
                        SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD());
                        SettingFragment.this.SetFragSpEditor.commit();
                    }
                });
                if (this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD() == 1) {
                    this.cb_setting_auto_upload.setChecked(true);
                } else {
                    this.cb_setting_auto_upload.setChecked(false);
                }
                this.cb_setting_auto_upload.findViewById(R.id.setting_auto_upload).setVisibility(View.GONE);
                this.cb_setting_auto_record = (CheckBox) getActivity().findViewById(R.id.setting_auto_record);
                this.cb_setting_auto_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z) {
                            SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_SAVE(1);
                            SettingFragment.this.cb_setting_auto_upload.setEnabled(true);
                        } else {
                            SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_SAVE(0);
                            SettingFragment.this.cb_setting_auto_upload.setEnabled(false);
                            SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_UPLOAD(0);
                            SettingFragment.this.cb_setting_auto_upload.setChecked(false);
                        }
                        SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD());
                        SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_SAVE", SettingFragment.this.SetFragApp.appECGConf.getsetECG_AUTO_SAVE());
                        SettingFragment.this.SetFragSpEditor.commit();
                    }
                });
                if (this.SetFragApp.appECGConf.getsetECG_AUTO_SAVE() == 1) {
                    this.cb_setting_auto_record.setChecked(true);
                    this.cb_setting_auto_upload.setEnabled(true);
                } else {
                    this.cb_setting_auto_record.setChecked(false);
                    this.cb_setting_auto_upload.setEnabled(false);
                    this.SetFragApp.appECGConf.setECG_AUTO_UPLOAD(0);
                    this.cb_setting_auto_upload.setChecked(false);
                }
                this.cb_setting_realtime_upload = (CheckBox) getActivity().findViewById(R.id.setting_realtime_upload);
                this.cb_setting_realtime_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ECGConf aVar;
                        int i;
                        if (z) {
                            aVar = SettingFragment.this.SetFragApp.appECGConf;
                            i = 1;
                        } else {
                            aVar = SettingFragment.this.SetFragApp.appECGConf;
                            i = 0;
                        }
                        aVar.setECG_REALTIME_UPLOAD(i);
                        SettingFragment.this.SetFragSpEditor.putInt("ECG_REALTIME_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_REALTIME_UPLOAD());
                        SettingFragment.this.SetFragSpEditor.commit();
                    }
                });
                if (this.SetFragApp.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
                    this.cb_setting_realtime_upload.setChecked(true);
                } else {
                    this.cb_setting_realtime_upload.setChecked(false);
                }
                this.cb_setting_ecg_waveform_analysis = (CheckBox) getActivity().findViewById(R.id.setting_ecg_waveform_analysis);
                this.cb_setting_ecg_waveform_analysis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        ECGConf aVar;
                        int i;
                        if (z) {
                            aVar = SettingFragment.this.SetFragApp.appECGConf;
                            i = 1;
                        } else {
                            aVar = SettingFragment.this.SetFragApp.appECGConf;
                            i = 0;
                        }
                        aVar.setECG_WAVEFORM_ANALYSIS(i);
                        SettingFragment.this.SetFragSpEditor.putInt("ECG_WAVEFORM_ANALYSIS", SettingFragment.this.SetFragApp.appECGConf.getECG_WAVEFORM_ANALYSIS());
                        SettingFragment.this.SetFragSpEditor.commit();
                    }
                });
                if (this.SetFragApp.appECGConf.getECG_WAVEFORM_ANALYSIS() == 1) {
                    this.cb_setting_ecg_waveform_analysis.setChecked(true);
                } else {
                    this.cb_setting_ecg_waveform_analysis.setChecked(false);
                }

                this.cb_setting_ecg_waveform_analysis.findViewById(R.id.setting_ecg_waveform_analysis).setVisibility(View.GONE);
                ((RelativeLayout) getActivity().findViewById(R.id.person_info_setting)).setOnClickListener(this.settingOnClickListener);
                ((RelativeLayout) getActivity().findViewById(R.id.pedometer_setting_rl)).setOnClickListener(this.settingOnClickListener);
                this.rv_sos_setting = (RelativeLayout) getActivity().findViewById(R.id.sos_setting);
                this.rv_sos_setting.setOnClickListener(this.settingOnClickListener);
                this.ll_setting_device_info = (LinearLayout) getActivity().findViewById(R.id.setting_device_info_ll);
                this.rv_login_out = (RelativeLayout) getActivity().findViewById(R.id.login_out);
                this.rv_login_out.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        SettingFragment.this.showlogout_titleDialog();
                    }
                });
                this.tv_setting_machine_name = (TextView) getActivity().findViewById(R.id.setting_machine_name);
                this.tv_setting_machine_id = (TextView) getActivity().findViewById(R.id.setting_machine_id);
                this.tv_setting_machine_ver = (TextView) getActivity().findViewById(R.id.setting_machine_ver);
                this.tv_setting_machine_battery = (TextView) getActivity().findViewById(R.id.setting_machine_battery);
                this.rv_setting_update_sw = (RelativeLayout) getActivity().findViewById(R.id.setting_update_sw);
                this.rv_setting_update_sw.setOnClickListener(this.settingOnClickListener);
                if (this.SetFragApp.appMachine != null) {
                    this.ll_setting_device_info.setVisibility(View.VISIBLE);
                    this.rv_setting_update_sw.setVisibility(View.VISIBLE);
                    if (!(this.SetFragApp.appMachine == null || this.SetFragApp.appMachine.getName() == null)) {
                        this.tv_setting_machine_name.setText(this.SetFragApp.appMachine.getName());
                    }
                    if (!(this.SetFragApp.appMachine == null || this.SetFragApp.appMachine.getId() == null)) {
                        this.tv_setting_machine_id.setText(this.SetFragApp.appMachine.getId());
                    }
                    if (!(this.SetFragApp.appMachine == null || this.SetFragApp.appMachine.getFwRev() == null)) {
                        this.tv_setting_machine_ver.setText(this.SetFragApp.appMachine.getFwRev());
                    }
                    TextView textView = this.tv_setting_machine_battery;
                    textView.setText(this.SetFragApp.appMachine.getBatteryLevel() + " %");
                }
                getActivity().invalidateOptionsMenu();
                this.rv_setting_check_update = (RelativeLayout) getActivity().findViewById(R.id.setting_check_update);
                this.rv_setting_check_update.setOnClickListener(this.settingOnClickListener);
                this.edt_ecg_warn_rate_max = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_max);
                this.edt_ecg_warn_rate_max.setText(String.valueOf(this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()));
                this.edt_ecg_warn_rate_max.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    /* renamed from: b */
                    private boolean isEditFocus = false;

                    public void onFocusChange(View view, boolean z) {
                        Context applicationContext;
                        Resources resources;
                        int i = 0;
                        if (z && !this.isEditFocus) {
                            this.isEditFocus = true;
                        }
                        if (!z && this.isEditFocus) {
                            EditText editText = (EditText) view;
                            if (editText.getText().toString().length() == 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_null;
                            } else {
                                Log.d("SettingFragment", editText.getText().toString());
                                int parseInt = Integer.parseInt(editText.getText().toString());
                                if (parseInt <= SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_less_min;
                                } else if (parseInt <= 0) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_zero;
                                } else {
                                    SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_RATE_MAX(parseInt);
                                }
                            }
                            Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getResources().getString(i), Toast.LENGTH_LONG).show();
                            editText.setText(String.valueOf(SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()));
                        }
                        SettingFragment.this.SetFragSpEditor.putInt("ECG_ALARM_RATE_MAX", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX());
                        SettingFragment.this.SetFragSpEditor.commit();
                    }
                });
                this.edt_ecg_warn_rate_min = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_min);
                this.edt_ecg_warn_rate_min.setText(String.valueOf(this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()));
                this.edt_ecg_warn_rate_min.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    /* renamed from: b */
                    private boolean isedt_ecg_warn_rate_minFocus = false;

                    public void onFocusChange(View view, boolean z) {
                        Context applicationContext;
                        Resources resources;
                        int i;
                        if (z && !this.isedt_ecg_warn_rate_minFocus) {
                            this.isedt_ecg_warn_rate_minFocus = true;
                        }
                        if (!z && this.isedt_ecg_warn_rate_minFocus) {
                            EditText editText = (EditText) view;
                            if (editText.getText().toString().length() == 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_null;
                            } else {
                                Log.d("SettingFragment", editText.getText().toString());
                                int parseInt = Integer.parseInt(editText.getText().toString());
                                if (parseInt >= SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_bigger_max;
                                } else if (parseInt <= 0) {
                                    applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                    resources = SettingFragment.this.getResources();
                                    i = R.string.p_ha_zero;
                                } else {
                                    SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_RATE_MIN(parseInt);
                                    SettingFragment.this.SetFragSpEditor.putInt("ECG_ALARM_RATE_MIN", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN());
                                    SettingFragment.this.SetFragSpEditor.commit();
                                    return;
                                }
                            }
                            Toast.makeText(applicationContext, resources.getString(i), Toast.LENGTH_LONG).show();
                            editText.setText(String.valueOf(SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()));
                        }
                    }
                });
                this.ll_set_ecg_alarm_div = (LinearLayout) getActivity().findViewById(R.id.set_ecg_alarm_div);
                this.cb_set_ecg_alarm = (CheckBox) getActivity().findViewById(R.id.set_ecg_alarm);
                this.cb_set_ecg_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        LinearLayout linearLayout;
                        int i;
                        if (z) {
                            linearLayout = SettingFragment.this.ll_set_ecg_alarm_div;
                            i = 0;
                        } else {
                            linearLayout = SettingFragment.this.ll_set_ecg_alarm_div;
                            i = 8;
                        }
                        linearLayout.setVisibility(i);
                        SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_ENABLE(z);
                        SettingFragment.this.SetFragSpEditor.putBoolean("ECG_ALARM_ENABLE", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_ENABLE());
                        SettingFragment.this.SetFragSpEditor.commit();
                    }
                });
                this.cb_set_ecg_alarm.setChecked(this.SetFragApp.appECGConf.getECG_ALARM_ENABLE());
                if (this.SetFragApp.appECGConf.getECG_ALARM_ENABLE()) {
                    this.ll_set_ecg_alarm_div.setVisibility(View.VISIBLE);
                } else {
                    this.ll_set_ecg_alarm_div.setVisibility(View.GONE);
                }
                this.rg_setting_alarm_type = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_type);
                this.rg_setting_alarm_type.setOnCheckedChangeListener(this.mRadioGroupOnCheckedListener);
                this.rb_setting_alarm_type_vibration = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration);
                this.rb_setting_alarm_type_sound = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_sound);
                this.rb_setting_alarm_type_vibration_sound = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration_sound);
                switch (this.SetFragApp.appECGConf.getECG_ALARM_TYPE()) {
                    case 0:
                        radioButton = this.rb_setting_alarm_type_vibration;
                        break;
                    case 1:
                        radioButton = this.rb_setting_alarm_type_sound;
                        break;
                    default:
                        radioButton = this.rb_setting_alarm_type_vibration_sound;
                        break;
                }
                radioButton.setChecked(true);
                this.tv_setting_ver_info = (TextView) getActivity().findViewById(R.id.setting_ver_info);
                this.mversionName = getActivity().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(getString(R.string.app_name));
                stringBuffer.append(" V");
                stringBuffer.append(this.mversionName);
                this.tv_setting_ver_info.setText(stringBuffer.toString());
            } else {
                radioButton2 = this.rb_time_20s;
            }
            radioButton2.setChecked(true);
            this.cb_setting_auto_upload = (CheckBox) getActivity().findViewById(R.id.setting_auto_upload);
            this.cb_setting_auto_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ECGConf aVar;
                    int i;
                    if (z) {
                        aVar = SettingFragment.this.SetFragApp.appECGConf;
                        i = 1;
                    } else {
                        aVar = SettingFragment.this.SetFragApp.appECGConf;
                        i = 0;
                    }
                    aVar.setECG_AUTO_UPLOAD(i);
                    SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD());
                    SettingFragment.this.SetFragSpEditor.commit();
                }
            });
            if (this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD() == 1) {
            }
            this.cb_setting_auto_upload.findViewById(R.id.setting_auto_upload).setVisibility(View.GONE);
            this.cb_setting_auto_record = (CheckBox) getActivity().findViewById(R.id.setting_auto_record);
            this.cb_setting_auto_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_SAVE(1);
                        SettingFragment.this.cb_setting_auto_upload.setEnabled(true);
                    } else {
                        SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_SAVE(0);
                        SettingFragment.this.cb_setting_auto_upload.setEnabled(false);
                        SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_UPLOAD(0);
                        SettingFragment.this.cb_setting_auto_upload.setChecked(false);
                    }
                    SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD());
                    SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_SAVE", SettingFragment.this.SetFragApp.appECGConf.getsetECG_AUTO_SAVE());
                    SettingFragment.this.SetFragSpEditor.commit();
                }
            });
            if (this.SetFragApp.appECGConf.getsetECG_AUTO_SAVE() == 1) {
            }
            this.cb_setting_realtime_upload = (CheckBox) getActivity().findViewById(R.id.setting_realtime_upload);
            this.cb_setting_realtime_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ECGConf aVar;
                    int i;
                    if (z) {
                        aVar = SettingFragment.this.SetFragApp.appECGConf;
                        i = 1;
                    } else {
                        aVar = SettingFragment.this.SetFragApp.appECGConf;
                        i = 0;
                    }
                    aVar.setECG_REALTIME_UPLOAD(i);
                    SettingFragment.this.SetFragSpEditor.putInt("ECG_REALTIME_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_REALTIME_UPLOAD());
                    SettingFragment.this.SetFragSpEditor.commit();
                }
            });
            if (this.SetFragApp.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
            }
            this.cb_setting_ecg_waveform_analysis = (CheckBox) getActivity().findViewById(R.id.setting_ecg_waveform_analysis);
            this.cb_setting_ecg_waveform_analysis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ECGConf aVar;
                    int i;
                    if (z) {
                        aVar = SettingFragment.this.SetFragApp.appECGConf;
                        i = 1;
                    } else {
                        aVar = SettingFragment.this.SetFragApp.appECGConf;
                        i = 0;
                    }
                    aVar.setECG_WAVEFORM_ANALYSIS(i);
                    SettingFragment.this.SetFragSpEditor.putInt("ECG_WAVEFORM_ANALYSIS", SettingFragment.this.SetFragApp.appECGConf.getECG_WAVEFORM_ANALYSIS());
                    SettingFragment.this.SetFragSpEditor.commit();
                }
            });
            if (this.SetFragApp.appECGConf.getECG_WAVEFORM_ANALYSIS() == 1) {
            }
            this.cb_setting_ecg_waveform_analysis.findViewById(R.id.setting_ecg_waveform_analysis).setVisibility(View.GONE);
            ((RelativeLayout) getActivity().findViewById(R.id.person_info_setting)).setOnClickListener(this.settingOnClickListener);
            ((RelativeLayout) getActivity().findViewById(R.id.pedometer_setting_rl)).setOnClickListener(this.settingOnClickListener);
            this.rv_sos_setting = (RelativeLayout) getActivity().findViewById(R.id.sos_setting);
            this.rv_sos_setting.setOnClickListener(this.settingOnClickListener);
            this.ll_setting_device_info = (LinearLayout) getActivity().findViewById(R.id.setting_device_info_ll);
            this.rv_login_out = (RelativeLayout) getActivity().findViewById(R.id.login_out);
            this.rv_login_out.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SettingFragment.this.showlogout_titleDialog();
                }
            });
            this.tv_setting_machine_name = (TextView) getActivity().findViewById(R.id.setting_machine_name);
            this.tv_setting_machine_id = (TextView) getActivity().findViewById(R.id.setting_machine_id);
            this.tv_setting_machine_ver = (TextView) getActivity().findViewById(R.id.setting_machine_ver);
            this.tv_setting_machine_battery = (TextView) getActivity().findViewById(R.id.setting_machine_battery);
            this.rv_setting_update_sw = (RelativeLayout) getActivity().findViewById(R.id.setting_update_sw);
            this.rv_setting_update_sw.setOnClickListener(this.settingOnClickListener);
            if (this.SetFragApp.appMachine != null) {
            }
            getActivity().invalidateOptionsMenu();
            this.rv_setting_check_update = (RelativeLayout) getActivity().findViewById(R.id.setting_check_update);
            this.rv_setting_check_update.setOnClickListener(this.settingOnClickListener);
            this.edt_ecg_warn_rate_max = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_max);
            this.edt_ecg_warn_rate_max.setText(String.valueOf(this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()));
            this.edt_ecg_warn_rate_max.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                /* renamed from: b */
                private boolean isEditFocus = false;

                public void onFocusChange(View view, boolean z) {
                    Context applicationContext;
                    Resources resources;
                    int i = 0;
                    if (z && !this.isEditFocus) {
                        this.isEditFocus = true;
                    }
                    if (!z && this.isEditFocus) {
                        EditText editText = (EditText) view;
                        if (editText.getText().toString().length() == 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_null;
                        } else {
                            Log.d("SettingFragment", editText.getText().toString());
                            int parseInt = Integer.parseInt(editText.getText().toString());
                            if (parseInt <= SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_less_min;
                            } else if (parseInt <= 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_zero;
                            } else {
                                SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_RATE_MAX(parseInt);
                            }
                        }
                        Toast.makeText(getActivity().getApplicationContext(), SettingFragment.this.getResources().getString(i), Toast.LENGTH_LONG).show();
                        editText.setText(String.valueOf(SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()));
                    }
                    SettingFragment.this.SetFragSpEditor.putInt("ECG_ALARM_RATE_MAX", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX());
                    SettingFragment.this.SetFragSpEditor.commit();
                }
            });
            this.edt_ecg_warn_rate_min = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_min);
            this.edt_ecg_warn_rate_min.setText(String.valueOf(this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()));
            this.edt_ecg_warn_rate_min.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                /* renamed from: b */
                private boolean isedt_ecg_warn_rate_minFocus = false;

                public void onFocusChange(View view, boolean z) {
                    Context applicationContext;
                    Resources resources;
                    int i;
                    if (z && !this.isedt_ecg_warn_rate_minFocus) {
                        this.isedt_ecg_warn_rate_minFocus = true;
                    }
                    if (!z && this.isedt_ecg_warn_rate_minFocus) {
                        EditText editText = (EditText) view;
                        if (editText.getText().toString().length() == 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_null;
                        } else {
                            Log.d("SettingFragment", editText.getText().toString());
                            int parseInt = Integer.parseInt(editText.getText().toString());
                            if (parseInt >= SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_bigger_max;
                            } else if (parseInt <= 0) {
                                applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                                resources = SettingFragment.this.getResources();
                                i = R.string.p_ha_zero;
                            } else {
                                SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_RATE_MIN(parseInt);
                                SettingFragment.this.SetFragSpEditor.putInt("ECG_ALARM_RATE_MIN", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN());
                                SettingFragment.this.SetFragSpEditor.commit();
                                return;
                            }
                        }
                        Toast.makeText(applicationContext, resources.getString(i), Toast.LENGTH_LONG).show();
                        editText.setText(String.valueOf(SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()));
                    }
                }
            });
            this.ll_set_ecg_alarm_div = (LinearLayout) getActivity().findViewById(R.id.set_ecg_alarm_div);
            this.cb_set_ecg_alarm = (CheckBox) getActivity().findViewById(R.id.set_ecg_alarm);
            this.cb_set_ecg_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    LinearLayout linearLayout;
                    int i;
                    if (z) {
                        linearLayout = SettingFragment.this.ll_set_ecg_alarm_div;
                        i = 0;
                    } else {
                        linearLayout = SettingFragment.this.ll_set_ecg_alarm_div;
                        i = 8;
                    }
                    linearLayout.setVisibility(i);
                    SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_ENABLE(z);
                    SettingFragment.this.SetFragSpEditor.putBoolean("ECG_ALARM_ENABLE", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_ENABLE());
                    SettingFragment.this.SetFragSpEditor.commit();
                }
            });
            this.cb_set_ecg_alarm.setChecked(this.SetFragApp.appECGConf.getECG_ALARM_ENABLE());
            if (this.SetFragApp.appECGConf.getECG_ALARM_ENABLE()) {
            }
            this.rg_setting_alarm_type = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_type);
            this.rg_setting_alarm_type.setOnCheckedChangeListener(this.mRadioGroupOnCheckedListener);
            this.rb_setting_alarm_type_vibration = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration);
            this.rb_setting_alarm_type_sound = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_sound);
            this.rb_setting_alarm_type_vibration_sound = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration_sound);
            switch (this.SetFragApp.appECGConf.getECG_ALARM_TYPE()) {
                case 0:
                    break;
                case 1:
                    break;
            }
            radioButton.setChecked(true);
            this.tv_setting_ver_info = (TextView) getActivity().findViewById(R.id.setting_ver_info);
            this.mversionName = getActivity().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(getString(R.string.app_name));
            stringBuffer2.append(" V");
            stringBuffer2.append(this.mversionName);
            this.tv_setting_ver_info.setText(stringBuffer2.toString());
        } else {
            radioButton3 = this.rb_time_5m;
        }
        radioButton3.setChecked(true);
        this.rg_setting_alarm_delay = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_delay);
        this.rg_setting_alarm_delay.setOnCheckedChangeListener(this.mRadioGroupOnCheckedListener);
        this.rb_time_10s = (RadioButton) getActivity().findViewById(R.id.time_10s);
        this.rb_time_20s = (RadioButton) getActivity().findViewById(R.id.time_20s);
        this.rb_time_30s = (RadioButton) getActivity().findViewById(R.id.time_30s);
        l = this.SetFragApp.appECGConf.getECG_ALARM_DELAY();
        if (l != 10) {
            radioButton2 = rb_time_20s;
        }
        if (radioButton2 != null) {
            radioButton2.setChecked(true);
        }
        this.cb_setting_auto_upload = (CheckBox) getActivity().findViewById(R.id.setting_auto_upload);
        this.cb_setting_auto_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ECGConf aVar;
                int i;
                if (z) {
                    aVar = SettingFragment.this.SetFragApp.appECGConf;
                    i = 1;
                } else {
                    aVar = SettingFragment.this.SetFragApp.appECGConf;
                    i = 0;
                }
                aVar.setECG_AUTO_UPLOAD(i);
                SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD());
                SettingFragment.this.SetFragSpEditor.commit();
            }
        });
        if (this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD() == 1) {
        }
        this.cb_setting_auto_upload.findViewById(R.id.setting_auto_upload).setVisibility(View.GONE);
        this.cb_setting_auto_record = (CheckBox) getActivity().findViewById(R.id.setting_auto_record);
        this.cb_setting_auto_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_SAVE(1);
                    SettingFragment.this.cb_setting_auto_upload.setEnabled(true);
                } else {
                    SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_SAVE(0);
                    SettingFragment.this.cb_setting_auto_upload.setEnabled(false);
                    SettingFragment.this.SetFragApp.appECGConf.setECG_AUTO_UPLOAD(0);
                    SettingFragment.this.cb_setting_auto_upload.setChecked(false);
                }
                SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_AUTO_UPLOAD());
                SettingFragment.this.SetFragSpEditor.putInt("ECG_AUTO_SAVE", SettingFragment.this.SetFragApp.appECGConf.getsetECG_AUTO_SAVE());
                SettingFragment.this.SetFragSpEditor.commit();
            }
        });
        if (this.SetFragApp.appECGConf.getsetECG_AUTO_SAVE() == 1) {
        }
        this.cb_setting_realtime_upload = (CheckBox) getActivity().findViewById(R.id.setting_realtime_upload);
        this.cb_setting_realtime_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ECGConf aVar;
                int i;
                if (z) {
                    aVar = SettingFragment.this.SetFragApp.appECGConf;
                    i = 1;
                } else {
                    aVar = SettingFragment.this.SetFragApp.appECGConf;
                    i = 0;
                }
                aVar.setECG_REALTIME_UPLOAD(i);
                SettingFragment.this.SetFragSpEditor.putInt("ECG_REALTIME_UPLOAD", SettingFragment.this.SetFragApp.appECGConf.getECG_REALTIME_UPLOAD());
                SettingFragment.this.SetFragSpEditor.commit();
            }
        });
        if (this.SetFragApp.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
        }
        this.cb_setting_ecg_waveform_analysis = (CheckBox) getActivity().findViewById(R.id.setting_ecg_waveform_analysis);
        this.cb_setting_ecg_waveform_analysis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ECGConf aVar;
                int i;
                if (z) {
                    aVar = SettingFragment.this.SetFragApp.appECGConf;
                    i = 1;
                } else {
                    aVar = SettingFragment.this.SetFragApp.appECGConf;
                    i = 0;
                }
                aVar.setECG_WAVEFORM_ANALYSIS(i);
                SettingFragment.this.SetFragSpEditor.putInt("ECG_WAVEFORM_ANALYSIS", SettingFragment.this.SetFragApp.appECGConf.getECG_WAVEFORM_ANALYSIS());
                SettingFragment.this.SetFragSpEditor.commit();
            }
        });
        if (this.SetFragApp.appECGConf.getECG_WAVEFORM_ANALYSIS() == 1) {
        }
        this.cb_setting_ecg_waveform_analysis.findViewById(R.id.setting_ecg_waveform_analysis).setVisibility(View.GONE);
        ((RelativeLayout) getActivity().findViewById(R.id.person_info_setting)).setOnClickListener(this.settingOnClickListener);
        ((RelativeLayout) getActivity().findViewById(R.id.pedometer_setting_rl)).setOnClickListener(this.settingOnClickListener);
        this.rv_sos_setting = (RelativeLayout) getActivity().findViewById(R.id.sos_setting);
        this.rv_sos_setting.setOnClickListener(this.settingOnClickListener);
        this.ll_setting_device_info = (LinearLayout) getActivity().findViewById(R.id.setting_device_info_ll);
        this.rv_login_out = (RelativeLayout) getActivity().findViewById(R.id.login_out);
        this.rv_login_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingFragment.this.showlogout_titleDialog();
            }
        });
        this.tv_setting_machine_name = (TextView) getActivity().findViewById(R.id.setting_machine_name);
        this.tv_setting_machine_id = (TextView) getActivity().findViewById(R.id.setting_machine_id);
        this.tv_setting_machine_ver = (TextView) getActivity().findViewById(R.id.setting_machine_ver);
        this.tv_setting_machine_battery = (TextView) getActivity().findViewById(R.id.setting_machine_battery);
        this.rv_setting_update_sw = (RelativeLayout) getActivity().findViewById(R.id.setting_update_sw);
        this.rv_setting_update_sw.setOnClickListener(this.settingOnClickListener);
        if (this.SetFragApp.appMachine != null) {
        }
        getActivity().invalidateOptionsMenu();
        this.rv_setting_check_update = (RelativeLayout) getActivity().findViewById(R.id.setting_check_update);
        this.rv_setting_check_update.setOnClickListener(this.settingOnClickListener);
        this.edt_ecg_warn_rate_max = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_max);
        this.edt_ecg_warn_rate_max.setText(String.valueOf(this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()));
        this.edt_ecg_warn_rate_max.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            /* renamed from: b */
            private boolean isEditFocus = false;

            public void onFocusChange(View view, boolean z) {
                Context applicationContext;
                Resources resources;
                int i = 0;
                if (z && !this.isEditFocus) {
                    this.isEditFocus = true;
                }
                if (!z && this.isEditFocus) {
                    EditText editText = (EditText) view;
                    if (editText.getText().toString().length() == 0) {
                        applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                        resources = SettingFragment.this.getResources();
                        i = R.string.p_ha_null;
                    } else {
                        Log.d("SettingFragment", editText.getText().toString());
                        int parseInt = Integer.parseInt(editText.getText().toString());
                        if (parseInt <= SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_less_min;
                        } else if (parseInt <= 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_zero;
                        } else {
                            SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_RATE_MAX(parseInt);
                        }
                    }
                    Toast.makeText(SettingFragment.this.getActivity().getApplicationContext(), SettingFragment.this.getResources().getString(i), Toast.LENGTH_LONG).show();
                    editText.setText(String.valueOf(SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()));
                }
                SettingFragment.this.SetFragSpEditor.putInt("ECG_ALARM_RATE_MAX", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX());
                SettingFragment.this.SetFragSpEditor.commit();
            }
        });
        this.edt_ecg_warn_rate_min = (EditText) getActivity().findViewById(R.id.ecg_warn_rate_min);
        this.edt_ecg_warn_rate_min.setText(String.valueOf(this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()));
        this.edt_ecg_warn_rate_min.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            /* renamed from: b */
            private boolean isedt_ecg_warn_rate_minFocus = false;

            public void onFocusChange(View view, boolean z) {
                Context applicationContext;
                Resources resources;
                int i;
                if (z && !this.isedt_ecg_warn_rate_minFocus) {
                    this.isedt_ecg_warn_rate_minFocus = true;
                }
                if (!z && this.isedt_ecg_warn_rate_minFocus) {
                    EditText editText = (EditText) view;
                    if (editText.getText().toString().length() == 0) {
                        applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                        resources = SettingFragment.this.getResources();
                        i = R.string.p_ha_null;
                    } else {
                        Log.d("SettingFragment", editText.getText().toString());
                        int parseInt = Integer.parseInt(editText.getText().toString());
                        if (parseInt >= SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MAX()) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_bigger_max;
                        } else if (parseInt <= 0) {
                            applicationContext = SettingFragment.this.getActivity().getApplicationContext();
                            resources = SettingFragment.this.getResources();
                            i = R.string.p_ha_zero;
                        } else {
                            SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_RATE_MIN(parseInt);
                            SettingFragment.this.SetFragSpEditor.putInt("ECG_ALARM_RATE_MIN", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN());
                            SettingFragment.this.SetFragSpEditor.commit();
                            return;
                        }
                    }
                    Toast.makeText(applicationContext, resources.getString(i), Toast.LENGTH_LONG).show();
                    editText.setText(String.valueOf(SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_RATE_MIN()));
                }
            }
        });
        this.ll_set_ecg_alarm_div = (LinearLayout) getActivity().findViewById(R.id.set_ecg_alarm_div);
        this.cb_set_ecg_alarm = (CheckBox) getActivity().findViewById(R.id.set_ecg_alarm);
        this.cb_set_ecg_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LinearLayout linearLayout;
                int i;
                if (z) {
                    linearLayout = SettingFragment.this.ll_set_ecg_alarm_div;
                    i = 0;
                } else {
                    linearLayout = SettingFragment.this.ll_set_ecg_alarm_div;
                    i = 8;
                }
                linearLayout.setVisibility(i);
                SettingFragment.this.SetFragApp.appECGConf.setECG_ALARM_ENABLE(z);
                SettingFragment.this.SetFragSpEditor.putBoolean("ECG_ALARM_ENABLE", SettingFragment.this.SetFragApp.appECGConf.getECG_ALARM_ENABLE());
                SettingFragment.this.SetFragSpEditor.commit();
            }
        });
        this.cb_set_ecg_alarm.setChecked(this.SetFragApp.appECGConf.getECG_ALARM_ENABLE());
        if (this.SetFragApp.appECGConf.getECG_ALARM_ENABLE()) {
        }
        this.rg_setting_alarm_type = (RadioGroup) getActivity().findViewById(R.id.setting_alarm_type);
        this.rg_setting_alarm_type.setOnCheckedChangeListener(this.mRadioGroupOnCheckedListener);
        this.rb_setting_alarm_type_vibration = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration);
        this.rb_setting_alarm_type_sound = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_sound);
        this.rb_setting_alarm_type_vibration_sound = (RadioButton) getActivity().findViewById(R.id.setting_alarm_type_vibration_sound);
        switch (this.SetFragApp.appECGConf.getECG_ALARM_TYPE()) {
            case 0:
                break;
            case 1:
                break;
        }
        if (radioButton != null) {
            radioButton.setChecked(true);
        }
        this.tv_setting_ver_info = (TextView) getActivity().findViewById(R.id.setting_ver_info);
        try {
            this.mversionName = getActivity().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer22 = new StringBuffer();
        stringBuffer22.append(getString(R.string.app_name));
        stringBuffer22.append(" V");
        stringBuffer22.append(this.mversionName);
        this.tv_setting_ver_info.setText(stringBuffer22.toString());
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void checkupdate() {
        if (this.mCheckUpdataAsyncTask == null || this.mCheckUpdataAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            this.mCheckUpdataAsyncTask = new CheckUpdataAsyncTask();
            this.mCheckUpdataAsyncTask.execute(new String[0]);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void showlogout_titleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.logout_title));
        builder.setMessage(getString(R.string.logout_msg));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                SettingFragment.this.SetFragApp.mSwConf.setSW_AUTO_LOGIN(0);
                SharedPreferences.Editor edit = SettingFragment.this.SetFragApp.spSw_conf.edit();
                edit.putInt("SW_AUTO_LOGIN", SettingFragment.this.SetFragApp.mSwConf.getAuto_login());
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
        this.logout_titleDialog = builder.create();
        this.logout_titleDialog.show();
    }

    /* renamed from: a */
    public void showNewVersionDialog(String str, String str2, final String str3) {
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
                    SettingFragment.this.SetFragApp.appMainService.mmainNetService.mo2822a(str3, stringBuffer.toString(), "ecg.apk");
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
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.mBatteryBroadcastReceiver, intentFilter);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
    }

    public void onDestroy() {
        LogUtils.logE("SettingFragment", "onDestroy~~~~");
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.mBatteryBroadcastReceiver);
        if (this.mCheckUpdataAsyncTask != null && this.mCheckUpdataAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.mCheckUpdataAsyncTask.cancel(true);
            this.mCheckUpdataAsyncTask = null;
        }
        if (this.logout_titleDialog != null) {
            this.logout_titleDialog.dismiss();
        }
        super.onDestroy();
    }

    public void onStart() {
        this.SetFragApp = (ECGApplication) getActivity().getApplication();
        this.SetFragSpEditor = this.SetFragApp.spECG_conf.edit();
        try {
            initView();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!(this.SetFragApp == null || this.SetFragApp.appMainService == null || !this.SetFragApp.appMainService.isMBleConn())) {
            this.SetFragApp.appMainService.GetDeviceBattery();
        }
        super.onStart();
    }
}
