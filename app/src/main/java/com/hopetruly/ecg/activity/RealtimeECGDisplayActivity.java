package com.hopetruly.ecg.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.algorithm.MaybeAlertHelper;
import com.hopetruly.ecg.algorithm.HeartRateCounter3;
import com.hopetruly.ecg.device.ConvertECG;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.p022b.SqlManager;
import com.hopetruly.ecg.p023ui.C0764a;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.ECGRecordUtils;
import com.warick.drawable.WarickSurfaceView;
import com.warick.drawable.p028a.C0813b;
import com.warick.drawable.p028a.C0814c;

import java.io.File;
import java.util.Arrays;

public class RealtimeECGDisplayActivity extends BaseActivity {

    /* renamed from: z */
    private static final String TAG = "RealtimeECGDisplayActivity";
    /* access modifiers changed from: private */

    /* renamed from: A */
    public TextView tv_ecg_filter_status;
    /* access modifiers changed from: private */

    /* renamed from: B */
    public TextView tv_ecg_alarm_status;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public Button btn_ecg_bt_disable_alarm;

    /* renamed from: D */
    private Switch switch_filter;
    /* access modifiers changed from: private */

    /* renamed from: E */
    public EditText edit_ecg_content;
    /* access modifiers changed from: private */

    /* renamed from: F */
    public boolean isShowSaveAlert = false;

    /* renamed from: G */
    private PopupWindow popwindow_ecg_tool;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public PopupWindow ecg_comment_pupopwindow;
    /* access modifiers changed from: private */

    /* renamed from: I */
    public ProgressDialog progress_saving_edit = null;

    /* renamed from: J */
    private ServiceConnection realMainServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RealtimeECGDisplayActivity.this.realtimeMainService = ((MainService.MainBinder) iBinder).getMainBinder();
            if (RealtimeECGDisplayActivity.this.realtimeMainService.isMBleConn()) {
                tv_ecg_blr_status.setText(RealtimeECGDisplayActivity.this.getResources().getString(R.string.BLE_state));
                RealtimeECGDisplayActivity.this.realtimeMainService.mo2737k();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        RealtimeECGDisplayActivity.this.beginEcg();
                        menu_ecg_start.setVisible(false);
                        menu_ecg_stop.setVisible(true);
                    }
                }, 1000);
                return;
            }
            RealtimeECGDisplayActivity.this.tv_ecg_blr_status.setText(RealtimeECGDisplayActivity.this.getResources().getString(R.string.BLE_state1));
        }

        public void onServiceDisconnected(ComponentName componentName) {
            RealtimeECGDisplayActivity.this.realtimeMainService = null;
        }
    };

    /* renamed from: K */
    private BroadcastReceiver ecgBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            TextView a = new TextView(context);
            String string;
            String stringExtra;
            TextView textView;
            RealtimeECGDisplayActivity realtimeECGDisplayActivity;
            String action = intent.getAction();
            try {
                if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY")) {
                    if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(Sensor.ECG.getData().toString()) && RealtimeECGDisplayActivity.this.realtimeMainService.mo2736j()) {
                        RealtimeECGDisplayActivity.this.covertECGData(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                    }
                } else if (action.equals("com.hopetruly.ecg.services.MainService.REFRESH_TIMER")) {
                    RealtimeECGDisplayActivity.this.refreshTimer(intent.getLongExtra("com.hopetruly.ecg.services.MainService.REFRESH_TIMER", 0));
                } else if (action.equals("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME")) {
                    RealtimeECGDisplayActivity.this.refreshDatetime(intent.getStringExtra("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME"));
                } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                    RealtimeECGDisplayActivity.this.m2476f();
                    RealtimeECGDisplayActivity.this.tv_ecg_blr_status.setText(RealtimeECGDisplayActivity.this.getResources().getString(R.string.BLE_state1));
                    RealtimeECGDisplayActivity.this.m2478g();
                } else if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_START")) {
                    RealtimeECGDisplayActivity.this.showSaveingprogressDialog();
                } else {
                    if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS")) {
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    } else if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL")) {
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    } else {
                        if (action.equals("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_SUCCESS")) {
                            stringExtra = intent.getStringExtra("Address");
                            textView = RealtimeECGDisplayActivity.this.tv_main_location;
                        } else {
                            if (action.equals("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_FAIL")) {
                                RealtimeECGDisplayActivity.this.tv_main_location.setText("Failed to locate ！");
                            } else if (action.equals("com.hopetruly.ecg.services.MainService.MARK_TIME_START")) {
                                Toast.makeText(RealtimeECGDisplayActivity.this, RealtimeECGDisplayActivity.this.getString(R.string.marking_finish), Toast.LENGTH_LONG).show();
                                return;
                            } else if (action.equals("com.holptruly.ecg.services.NetService.NET_CHANGE")) {
                                RealtimeECGDisplayActivity.this.updataStatus();
                                return;
                            } else if (action.equals("com.hopetruly.ecg.util.ACTION_HEARTRATE")) {
                                stringExtra = intent.getStringExtra("heart_rate");
                                if (!stringExtra.equals("NaN")) {
                                    RealtimeECGDisplayActivity.this.f2493v.mo2450a(Integer.parseInt(stringExtra));
                                } else {
                                    RealtimeECGDisplayActivity.this.f2493v.mo2449a();
                                }
                                if (RealtimeECGDisplayActivity.this.f2493v.mo2456g()) {
                                    RealtimeECGDisplayActivity.this.btn_ecg_bt_disable_alarm.setVisibility(View.VISIBLE);
                                    C0764a.m2746a(RealtimeECGDisplayActivity.this.btn_ecg_bt_disable_alarm);
                                } else {
                                    C0764a.m2747b(RealtimeECGDisplayActivity.this.btn_ecg_bt_disable_alarm);
                                    RealtimeECGDisplayActivity.this.btn_ecg_bt_disable_alarm.setVisibility(View.GONE);
                                }
                                textView = RealtimeECGDisplayActivity.this.tv_heartrate;
                            } else if (action.equals("com.hopetruly.ecg.util.MyAlarmClock.COUNT_SEC")) {
                                RealtimeECGDisplayActivity.this.f2493v.mo2454e();
                                if (RealtimeECGDisplayActivity.this.f2493v.mo2451b() && !RealtimeECGDisplayActivity.this.f2493v.mo2455f()) {
                                    RealtimeECGDisplayActivity.this.tv_ecg_alarm_status.setText(R.string.ecg_alarm_status_on);
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                            return;
                        }
                        textView.setText(stringExtra);
                        return;
                    }
                    realtimeECGDisplayActivity.dissmissSaveingprogressDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /* renamed from: a */
    WarickSurfaceView sv_heart_rate;

    /* renamed from: c */
    C0814c f2474c;

    /* renamed from: d */
    C0813b f2475d;

    /* renamed from: e */
    TextView tv_ecg_values;

    /* renamed from: f */
    TextView tv_ecg_scale_info;

    /* renamed from: g */
    TextView tv_main_timer;

    /* renamed from: h */
    TextView tv_main_datatime;

    /* renamed from: i */
    TextView tv_heartrate;

    /* renamed from: j */
    TextView tv_main_location;

    /* renamed from: k */
    TextView tv_ecg_blr_status;

    /* renamed from: l */
    TextView tv_ecg_auto_rec;

    /* renamed from: m */
    TextView tv_ecg_realtime_upload_status;

    /* renamed from: n */
    TextView tv_main_ecg_description;

    /* renamed from: o */
    TextView tv_ecg_net_status;

    /* renamed from: p */
    MenuItem menu_ecg_start;

    /* renamed from: q */
    MenuItem menu_ecg_stop;

    /* renamed from: r */
    MenuItem menu_ecg_filter_on;

    /* renamed from: s */
    MenuItem menu_ecg_filter_off;

    /* renamed from: t */
    ECGRecord realtimeECGRecord;

    /* renamed from: u */
    MainService realtimeMainService;

    /* renamed from: v */
    MaybeAlertHelper f2493v;

    /* renamed from: w */
    ECGApplication realtimeApplication;

    /* renamed from: x */
    HeartRateCounter3 mHeartRateCounter3;

    /* renamed from: y */
    ProgressDialog saveprogressDialog;

    /* renamed from: a */
    private void checkEcgSet() {
        TextView textView;
        int i;
        TextView textView2;
        int i2;
        bindService(new Intent(this, MainService.class), this.realMainServiceConn, 1);
        f2493v = new MaybeAlertHelper(this);
        mHeartRateCounter3 = new HeartRateCounter3();
        if (getIntent().getIntExtra("Lead", 0) == 0) {
            this.realtimeECGRecord.setLeadType(0);
            this.tv_main_ecg_description.setText(getString(R.string.l_with_hand));
            if (this.f2474c != null) {
                this.f2474c.mo2911a(true);
            }
            textView = this.tv_ecg_filter_status;
            i = R.string.ecg_filter_status_on;
        } else {
            this.realtimeECGRecord.setLeadType(1);
            this.tv_main_ecg_description.setText(getString(R.string.l_with_chest));
            if (this.f2474c != null) {
                this.f2474c.mo2911a(false);
            }
            textView = this.tv_ecg_filter_status;
            i = R.string.ecg_filter_status_off;
        }
        textView.setText(getString(i));
        if (this.f2493v.mo2451b()) {
            textView2 = this.tv_ecg_alarm_status;
            i2 = R.string.ecg_alarm_status_on;
        } else {
            textView2 = this.tv_ecg_alarm_status;
            i2 = R.string.ecg_alarm_status_off;
        }
        textView2.setText(getString(i2));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.REFRESH_TIMER");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.FILE_SAVE_START");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_SUCCESS");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_FAIL");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.MARK_TIME_START");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.NET_CHANGE");
        intentFilter.addAction("com.hopetruly.ecg.util.MyAlarmClock.COUNT_SEC");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.ecgBroadcastReceiver, intentFilter);
    }

    /* renamed from: a */
    private void showTime_is_reached_dialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(i + getResources().getString(R.string.set_time_is_reached));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void refreshTimer(long j) {
        long j2 = j % 3600;
        TextView textView = this.tv_main_timer;
        textView.setText("" + String.format("%02d", new Object[]{Integer.valueOf((int) (j / 3600))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((int) (j2 / 60))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((int) (j2 % 60))}));
        if (this.realtimeApplication.appECGConf.getECG_MESURE_TIME() > 0 && j2 >= ((long) (this.realtimeApplication.appECGConf.getECG_MESURE_TIME() * 60))) {
            m2476f();
            showTime_is_reached_dialog(this.realtimeApplication.appECGConf.getECG_MESURE_TIME());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void refreshDatetime(String str) {
        this.tv_main_datatime.setVisibility(View.VISIBLE);
        this.tv_main_ecg_description.setVisibility(View.VISIBLE);
        this.tv_main_datatime.setText(str);
    }

    /*
     * 十六进制位数的表示数组
     */
    private static final char[] HEX_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private String s = "";

    private byte[] gattValue = new byte[1000];

    private byte[] realGattValue;

    private int bytesNum = 0;

    /*
     *   byte数组转成十六进制
     */
    public static String bytesToHex(byte[] bytes, int start, int length, boolean add0x) {
        if (bytes == null || bytes.length <= start || length <= 0) {
            return "";
        }
        int maxLength = Math.min(length, bytes.length - start);
        char[] hexChars = new char[(maxLength * 2)];
        for (int j = 0; j < maxLength; j++) {
            int v = bytes[start + j] & 255;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[(j * 2) + 1] = HEX_ARRAY[v & 15];
        }
        if (!add0x) {
            return new String(hexChars);
        }
        return "0x" + new String(hexChars);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    @SuppressLint("LongLogTag")
    public void covertECGData(byte[] bArr) {

        if (s.length() < 138) {
            bytesNum--;
        }
        if (bytesNum < 6) {
            int length = s.length();
            s += bytesToHex(bArr, 0, bArr.length, false);
            Log.d(TAG, "s'length: " + length + "," + bytesNum);
//            Log.d(TAG, "s'origin value: " + s);
            System.arraycopy(bArr, 0, gattValue, length / 2, bArr.length);
//            Log.d(TAG, "gattValue' origin value: " + bytesToHex(gattValue, 0, gattValue.length, false));
            bytesNum++;
            return;
        }

        int index = s.indexOf("56A1D");
        s = s.substring(index, s.length());
        Log.d(TAG, "56a1d'index: " + index);
        System.arraycopy(gattValue, index / 2, gattValue, 0, gattValue.length - index / 2);
//        byte[] bRex = Arrays.copyOfRange(gattValue, index/2, (s.length() + index)/2);
//        Log.d(TAG, "g'long sub: " + index/2 + ","  + bytesToHex(gattValue, 0, gattValue.length, false));
        String sub = s.substring(0, 7);
        if (sub.contains("56A1D0")) {
            realGattValue = Arrays.copyOfRange(gattValue, 6, 16);
            s = s.substring(42, s.length());
            int length = s.length();
            s += bytesToHex(bArr, 0, bArr.length, false);
//            Log.d(TAG, "s'new sub: " + index + "," + s);
            System.arraycopy(gattValue, 21, gattValue, 0, length / 2);
            System.arraycopy(bArr, 0, gattValue, length / 2, bArr.length);
//            Log.d(TAG, "g'new sub: " + index/2 + ","  + bytesToHex(gattValue, 0, gattValue.length, false));

        } else if (sub.contains("56A1D1")) {
            //todo
            s = s.substring(34, s.length());
            int length = s.length();
            s += bytesToHex(bArr, 0, bArr.length, false);
            System.arraycopy(gattValue, 17, gattValue, 0, length / 2);
            System.arraycopy(bArr, 0, gattValue, length / 2, bArr.length);
        }

//        Log.d(TAG, "getGattValue: " + s + "-----" + index);
//        Log.d(TAG, "getRealValue: " + bytesToHex(realGattValue, 0, realGattValue.length, false));
        String str;
        TextView textView;
        if (this.f2474c != null) {
            ConvertECG convertECG = Sensor.ECG.convertECG(realGattValue);
            realtimeMainService.mmainFileService.mo2703a(convertECG.ecgArr);
            for (int i = 0; i < convertECG.ecgArr.length; i++) {
                this.f2474c.mo2918c((float) convertECG.ecgArr[i]);
                this.mHeartRateCounter3.mo2439a((float) (convertECG.ecgArr[i] * this.f2474c.mo2925h()));
                if (this.mHeartRateCounter3.getHrStatus() != 2 || this.mHeartRateCounter3.getHeartRate() <= 0) {
                    textView = this.tv_heartrate;
                    str = "NaN";
                } else {
                    if (!String.valueOf(this.mHeartRateCounter3.getHeartRate()).equals("NaN")) {
                        this.f2493v.mo2450a(this.mHeartRateCounter3.getHeartRate());
                    } else {
                        this.f2493v.mo2449a();
                    }
                    if (this.f2493v.mo2456g()) {
                        this.btn_ecg_bt_disable_alarm.setVisibility(View.VISIBLE);
                        C0764a.m2746a(this.btn_ecg_bt_disable_alarm);
                    } else {
                        C0764a.m2747b(this.btn_ecg_bt_disable_alarm);
                        this.btn_ecg_bt_disable_alarm.setVisibility(View.GONE);
                    }
                    textView = this.tv_heartrate;
                    str = String.valueOf(this.mHeartRateCounter3.getHeartRate());
                }
                textView.setText(str);
            }
        }
    }

    /* renamed from: b */
    private void initView() {
        this.sv_heart_rate = (WarickSurfaceView) findViewById(R.id.heart_rate_sv);
        this.f2475d = new C0813b();
        this.sv_heart_rate.mo2893a((WarickSurfaceView.C0809a) this.f2475d);
        this.f2474c = new C0814c();
        this.f2474c.mo2908a(0);
        this.sv_heart_rate.mo2893a((WarickSurfaceView.C0809a) this.f2474c);
        this.sv_heart_rate.mo2892a(25);
        this.tv_ecg_values = (TextView) findViewById(R.id.ecg_values);
        this.tv_main_timer = (TextView) findViewById(R.id.main_timer);
        this.tv_main_datatime = (TextView) findViewById(R.id.main_datatime);
        this.tv_main_datatime.setVisibility(View.GONE);
        this.tv_main_ecg_description = (TextView) findViewById(R.id.main_ecg_description);
        this.tv_ecg_filter_status = (TextView) findViewById(R.id.main_ecg_filter_status);
        this.tv_ecg_alarm_status = (TextView) findViewById(R.id.main_ecg_alarm_status);
        this.btn_ecg_bt_disable_alarm = (Button) findViewById(R.id.ecg_bt_disable_alarm);
        this.btn_ecg_bt_disable_alarm.setVisibility(View.GONE);
        this.btn_ecg_bt_disable_alarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                C0764a.m2747b(view);
                ((Button) view).setVisibility(View.GONE);
                RealtimeECGDisplayActivity.this.f2493v.mo2452c();
                RealtimeECGDisplayActivity.this.tv_ecg_alarm_status.setText(RealtimeECGDisplayActivity.this.getString(R.string.ecg_alarm_hand_off));
            }
        });
        tv_heartrate = (TextView) findViewById(R.id.main_heartrate);
        tv_main_location = (TextView) findViewById(R.id.main_location);
        tv_ecg_blr_status = (TextView) findViewById(R.id.ecg_blr_status);
        tv_ecg_blr_status.setText(getResources().getString(R.string.BLE_state));
        tv_ecg_auto_rec = (TextView) findViewById(R.id.ecg_auto_rec_status);
        tv_ecg_realtime_upload_status = (TextView) findViewById(R.id.ecg_realtime_upload_status);
        tv_ecg_net_status = (TextView) findViewById(R.id.ecg_net_status);
        tv_ecg_scale_info = (TextView) findViewById(R.id.ecg_scale_info);
    }

    /* renamed from: c */
    private void showPupop_ecg_tool() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_tool, (ViewGroup) null);
        this.popwindow_ecg_tool = new PopupWindow(inflate, -2, -2, false);
        this.popwindow_ecg_tool.setBackgroundDrawable(new BitmapDrawable());
        this.popwindow_ecg_tool.setOutsideTouchable(true);
        this.popwindow_ecg_tool.setFocusable(true);
        this.switch_filter = (Switch) inflate.findViewById(R.id.filter_switch);
        this.switch_filter.setChecked(this.f2474c.mo2923f());
        this.switch_filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                TextView b;
                RealtimeECGDisplayActivity realtimeECGDisplayActivity;
                int i;
                RealtimeECGDisplayActivity.this.f2474c.mo2911a(z);
                RealtimeECGDisplayActivity.this.menu_ecg_filter_on.setVisible(z);
                RealtimeECGDisplayActivity.this.menu_ecg_filter_off.setVisible(!z);
                if (RealtimeECGDisplayActivity.this.f2474c.mo2923f()) {
                    b = RealtimeECGDisplayActivity.this.tv_ecg_filter_status;
                    realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    i = R.string.ecg_filter_status_on;
                } else {
                    b = RealtimeECGDisplayActivity.this.tv_ecg_filter_status;
                    realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    i = R.string.ecg_filter_status_off;
                }
                b.setText(realtimeECGDisplayActivity.getString(i));
            }
        });
        ((Button) inflate.findViewById(R.id.btn_reverse)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RealtimeECGDisplayActivity.this.f2474c.mo2924g();
            }
        });
        ((RadioGroup) inflate.findViewById(R.id.ecg_scale_type)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RealtimeECGDisplayActivity realtimeECGDisplayActivity;
                TextView textView;
                int i2;
                switch (i) {
                    case R.id.ecg_scale_type_12_5:
                        RealtimeECGDisplayActivity.this.f2474c.specialMo2907a(1.0f);
                        textView = RealtimeECGDisplayActivity.this.tv_ecg_scale_info;
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_12_5;
                        break;
                    case R.id.ecg_scale_type_25 /*2131165298*/:
                        RealtimeECGDisplayActivity.this.f2474c.mo2907a(1.0f);
                        textView = RealtimeECGDisplayActivity.this.tv_ecg_scale_info;
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_25;
                        break;
                    case R.id.ecg_scale_type_50 /*2131165299*/:
                        RealtimeECGDisplayActivity.this.f2474c.mo2907a(2.0f);
                        textView = RealtimeECGDisplayActivity.this.tv_ecg_scale_info;
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_50;
                        break;
                    default:
                        return;
                }
                textView.setText(realtimeECGDisplayActivity.getString(i2));
            }
        });
        this.tv_ecg_scale_info.setText(getString(R.string.l_ecg_scale_25));
        inflate.findViewById(R.id.btn_previous).setVisibility(View.GONE);
        inflate.findViewById(R.id.btn_next).setVisibility(View.GONE);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_comment, (ViewGroup) null);
        this.ecg_comment_pupopwindow = new PopupWindow(inflate2, -2, -2, true);
        this.ecg_comment_pupopwindow.setBackgroundDrawable(new BitmapDrawable());
        this.ecg_comment_pupopwindow.setOutsideTouchable(false);
        this.ecg_comment_pupopwindow.setFocusable(true);
        this.edit_ecg_content = (EditText) inflate2.findViewById(R.id.ecg_comment_content);
        ((Button) inflate2.findViewById(R.id.ecg_comment_ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProgressDialog unused = RealtimeECGDisplayActivity.this.progress_saving_edit = ProgressDialog.show(RealtimeECGDisplayActivity.this, (CharSequence) null, RealtimeECGDisplayActivity.this.getString(R.string.saving_edit), true, true, (DialogInterface.OnCancelListener) null);
                new Thread(new Runnable() {
                    public void run() {
                        String obj = RealtimeECGDisplayActivity.this.edit_ecg_content.getText().toString();
                        if (obj != null) {
                            try {
                                File file = new File(RealtimeECGDisplayActivity.this.realtimeECGRecord.getFilePath());
                                ECGRecordUtils.getDescriptionFile(file, "text", obj);
                                RealtimeECGDisplayActivity.this.realtimeECGRecord.setDescription(obj);
                                SqlManager bVar = new SqlManager(RealtimeECGDisplayActivity.this.getApplicationContext());
                                for (ECGRecord next : bVar.getECGRecord(RealtimeECGDisplayActivity.this.realtimeApplication.mUserInfo.getId())) {
                                    if (next.getFileName().equals(file.getName())) {
                                        RealtimeECGDisplayActivity.this.realtimeECGRecord.setId(next.getId());
                                    }
                                }
                                bVar.updateEcgRecord(RealtimeECGDisplayActivity.this.realtimeECGRecord);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (RealtimeECGDisplayActivity.this.progress_saving_edit != null) {
                            RealtimeECGDisplayActivity.this.progress_saving_edit.dismiss();
                        }
                        ProgressDialog unused = RealtimeECGDisplayActivity.this.progress_saving_edit = null;
                    }
                }).start();
                RealtimeECGDisplayActivity.this.ecg_comment_pupopwindow.dismiss();
                if (!RealtimeECGDisplayActivity.this.realtimeMainService.isMBleConn()) {
                    RealtimeECGDisplayActivity.this.onBackPressed();
                }
            }
        });
        ((Button) inflate2.findViewById(R.id.ecg_comment_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RealtimeECGDisplayActivity.this.ecg_comment_pupopwindow.dismiss();
                if (!RealtimeECGDisplayActivity.this.realtimeMainService.isMBleConn()) {
                    RealtimeECGDisplayActivity.this.onBackPressed();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void updataStatus() {
        TextView textView;
        Resources resources;
        int i;
        TextView textView2;
        Resources resources2;
        int i2;
        int i3;
        Resources resources3;
        TextView textView3;
        if (this.realtimeApplication.appECGConf.getsetECG_AUTO_SAVE() == 1) {
            textView = this.tv_ecg_auto_rec;
            resources = getResources();
            i = R.string.auto_save;
        } else {
            textView = this.tv_ecg_auto_rec;
            resources = getResources();
            i = R.string.auto_save1;
        }
        textView.setText(resources.getString(i));
        if (this.realtimeApplication.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
            textView2 = this.tv_ecg_realtime_upload_status;
            resources2 = getResources();
            i2 = R.string.Real_time_upload;
        } else {
            textView2 = this.tv_ecg_realtime_upload_status;
            resources2 = getResources();
            i2 = R.string.Real_time_upload1;
        }
        textView2.setText(resources2.getString(i2));
        switch (this.realtimeApplication.f2093n) {
            case -1:
                textView3 = this.tv_ecg_net_status;
                resources3 = getResources();
                i3 = R.string.network_state;
                break;
            case 0:
                textView3 = this.tv_ecg_net_status;
                resources3 = getResources();
                i3 = R.string.network_state_mobile;
                break;
            case 1:
                textView3 = this.tv_ecg_net_status;
                resources3 = getResources();
                i3 = R.string.network_state_wifi;
                break;
            default:
                return;
        }
        textView3.setText(resources3.getString(i3));
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void beginEcg() {
        if (!this.realtimeMainService.mo2736j()) {
            if (!this.realtimeMainService.isMBleConn()) {
                Toast.makeText(getApplicationContext(), getString(R.string.ble_not_connect), Toast.LENGTH_LONG).show();
                return;
            }
            this.f2474c.mo2926i();
            this.realtimeMainService.startMyECG(this.realtimeECGRecord);
            this.mHeartRateCounter3.init();
            this.f2493v.mo2453d();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m2476f() {
        if (this.realtimeMainService.mo2736j()) {
            this.realtimeECGRecord.setHeartRate(this.mHeartRateCounter3.getAvgHr());
            this.realtimeMainService.stopECG();
            this.tv_main_datatime.setVisibility(View.GONE);
            this.tv_main_ecg_description.setVisibility(View.GONE);
            if (this.realtimeApplication.appECGConf.getsetECG_AUTO_SAVE() == 0) {
                showSaveAlertDialog();
                return;
            }
            this.realtimeMainService.mo2735i();
            this.ecg_comment_pupopwindow.showAtLocation(findViewById(R.id.heart_rate_sv), 17, 0, 0);
            this.edit_ecg_content.setText("");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m2478g() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.Disconnect_and_check));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (!RealtimeECGDisplayActivity.this.isShowSaveAlert) {
                    RealtimeECGDisplayActivity.this.onBackPressed();
                }
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public void showSaveingprogressDialog() {
        this.saveprogressDialog = new ProgressDialog(this);
        this.saveprogressDialog.setMessage(getResources().getString(R.string.Saving));
        this.saveprogressDialog.setCancelable(false);
        this.saveprogressDialog.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void dissmissSaveingprogressDialog() {
        this.saveprogressDialog.dismiss();
    }

    /* renamed from: j */
    private void showSaveAlertDialog() {
        this.isShowSaveAlert = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Save));
        builder.setMessage(getResources().getString(R.string.Save_to_file));
        builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean unused = RealtimeECGDisplayActivity.this.isShowSaveAlert = false;
                RealtimeECGDisplayActivity.this.realtimeMainService.mo2735i();
                RealtimeECGDisplayActivity.this.ecg_comment_pupopwindow.showAtLocation(RealtimeECGDisplayActivity.this.findViewById(R.id.heart_rate_sv), 17, 0, 0);
                RealtimeECGDisplayActivity.this.edit_ecg_content.setText("");
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean unused = RealtimeECGDisplayActivity.this.isShowSaveAlert = false;
                dialogInterface.dismiss();
                if (!RealtimeECGDisplayActivity.this.realtimeMainService.isMBleConn()) {
                    RealtimeECGDisplayActivity.this.onBackPressed();
                }
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* renamed from: k */
    private void m2486k() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getString(R.string.device_is_running));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    public void onBackPressed() {
        if (this.realtimeMainService.mo2736j()) {
            m2486k();
        } else {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint("LongLogTag")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(TAG, "oncreat~~~~");
        realtimeApplication = (ECGApplication) getApplication();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.realtimeECGRecord = new ECGRecord();
        getWindow().setFlags(128, 128);
        setContentView(R.layout.activity_realtime_ecg_display);
        initView();
        checkEcgSet();
        showPupop_ecg_tool();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ecg_realtime_display_menu, menu);
        this.menu_ecg_start = menu.findItem(R.id.action_ecg_start);
        this.menu_ecg_stop = menu.findItem(R.id.action_ecg_stop);
        this.menu_ecg_start.setVisible(true);
        this.menu_ecg_filter_on = menu.findItem(R.id.action_ecg_filter_on);
        this.menu_ecg_filter_off = menu.findItem(R.id.action_ecg_filter_off);
        this.menu_ecg_filter_on.setVisible(this.f2474c.mo2923f());
        this.menu_ecg_filter_off.setVisible(!this.f2474c.mo2923f());
        return true;
    }

    /* access modifiers changed from: protected */
    @SuppressLint("LongLogTag")
    public void onDestroy() {
        Log.i(TAG, "onDestroy~~~~");
        if (this.realtimeMainService != null) {
            this.realtimeMainService.initACCELEROMETER();
        }
        unbindService(this.realMainServiceConn);
        this.f2493v.mo2459j();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.ecgBroadcastReceiver);
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        TextView textView;
        int i;
        int itemId = menuItem.getItemId();
        if (itemId == 16908332 || itemId == R.id.action_exit) {
            onBackPressed();
            return true;
        } else if (itemId != R.id.action_help) {
            switch (itemId) {
                case R.id.action_ecg_filter_off /*2131165206*/:
                    this.f2474c.mo2911a(true);
                    this.switch_filter.setChecked(true);
                    this.menu_ecg_filter_on.setVisible(true);
                    this.menu_ecg_filter_off.setVisible(false);
                    textView = this.tv_ecg_filter_status;
                    i = R.string.ecg_filter_status_on;
                    break;
                case R.id.action_ecg_filter_on /*2131165207*/:
                    this.f2474c.mo2911a(false);
                    this.switch_filter.setChecked(false);
                    this.menu_ecg_filter_on.setVisible(false);
                    this.menu_ecg_filter_off.setVisible(true);
                    textView = this.tv_ecg_filter_status;
                    i = R.string.ecg_filter_status_off;
                    break;
                case R.id.action_ecg_options /*2131165208*/:
                    try {
                        this.popwindow_ecg_tool.showAsDropDown(findViewById(menuItem.getItemId()));
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                case R.id.action_ecg_reverse /*2131165209*/:
                    this.f2474c.mo2924g();
                    return true;
                case R.id.action_ecg_start /*2131165210*/:
                    beginEcg();
                    this.menu_ecg_stop.setVisible(true);
                    this.menu_ecg_start.setVisible(false);
                    return true;
                case R.id.action_ecg_stop /*2131165211*/:
                    m2476f();
                    this.f2493v.mo2452c();
                    C0764a.m2747b(this.btn_ecg_bt_disable_alarm);
                    this.btn_ecg_bt_disable_alarm.setVisibility(View.GONE);
                    this.menu_ecg_stop.setVisible(false);
                    this.menu_ecg_start.setVisible(true);
                    return true;
                default:
                    return super.onOptionsItemSelected(menuItem);
            }
            textView.setText(getString(i));
            return true;
        } else {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint("LongLogTag")
    public void onResume() {
        Log.i(TAG, "onResume~~~~");
        updataStatus();
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.popwindow_ecg_tool.dismiss();
        super.onStop();
    }
}
