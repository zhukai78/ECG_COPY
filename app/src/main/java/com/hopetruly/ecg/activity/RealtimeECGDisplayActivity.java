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
import com.hopetruly.ecg.algorithm.C0736b;
import com.hopetruly.ecg.algorithm.HeartRateCounter3;
import com.hopetruly.ecg.device.C0746b;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.p022b.C0740b;
import com.hopetruly.ecg.p023ui.C0764a;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.C0770f;
import com.warick.drawable.WarickSurfaceView;
import com.warick.drawable.p028a.C0813b;
import com.warick.drawable.p028a.C0814c;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class RealtimeECGDisplayActivity extends C0721a {

    /* renamed from: z */
    private static final String f2461z = "RealtimeECGDisplayActivity";
    /* access modifiers changed from: private */

    /* renamed from: A */
    public TextView f2462A;
    /* access modifiers changed from: private */

    /* renamed from: B */
    public TextView f2463B;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public Button f2464C;

    /* renamed from: D */
    private Switch f2465D;
    /* access modifiers changed from: private */

    /* renamed from: E */
    public EditText f2466E;
    /* access modifiers changed from: private */

    /* renamed from: F */
    public boolean f2467F = false;

    /* renamed from: G */
    private PopupWindow f2468G;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public PopupWindow f2469H;
    /* access modifiers changed from: private */

    /* renamed from: I */
    public ProgressDialog f2470I = null;

    /* renamed from: J */
    private ServiceConnection f2471J = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RealtimeECGDisplayActivity.this.f2492u = ((MainService.C0762a) iBinder).mo2756a();
            if (RealtimeECGDisplayActivity.this.f2492u.mo2728b()) {
                RealtimeECGDisplayActivity.this.f2482k.setText(RealtimeECGDisplayActivity.this.getResources().getString(R.string.BLE_state));
                RealtimeECGDisplayActivity.this.f2492u.mo2737k();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        RealtimeECGDisplayActivity.this.m2475e();
                        RealtimeECGDisplayActivity.this.f2487p.setVisible(false);
                        RealtimeECGDisplayActivity.this.f2488q.setVisible(true);
                    }
                }, 1000);
                return;
            }
            RealtimeECGDisplayActivity.this.f2482k.setText(RealtimeECGDisplayActivity.this.getResources().getString(R.string.BLE_state1));
        }

        public void onServiceDisconnected(ComponentName componentName) {
            RealtimeECGDisplayActivity.this.f2492u = null;
        }
    };

    /* renamed from: K */
    private BroadcastReceiver f2472K = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            TextView a = new TextView(context);
            String string;
            String stringExtra;
            TextView textView;
            RealtimeECGDisplayActivity realtimeECGDisplayActivity;
            String action = intent.getAction();
            try {
                if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY")) {
                    if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(Sensor.ECG.getData().toString()) && RealtimeECGDisplayActivity.this.f2492u.mo2736j()) {
                        RealtimeECGDisplayActivity.this.m2466a(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                    }
                } else if (action.equals("com.hopetruly.ecg.services.MainService.REFRESH_TIMER")) {
                    RealtimeECGDisplayActivity.this.m2461a(intent.getLongExtra("com.hopetruly.ecg.services.MainService.REFRESH_TIMER", 0));
                } else if (action.equals("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME")) {
                    RealtimeECGDisplayActivity.this.m2465a(intent.getStringExtra("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME"));
                } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                    RealtimeECGDisplayActivity.this.m2476f();
                    RealtimeECGDisplayActivity.this.f2482k.setText(RealtimeECGDisplayActivity.this.getResources().getString(R.string.BLE_state1));
                    RealtimeECGDisplayActivity.this.m2478g();
                } else if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_START")) {
                    RealtimeECGDisplayActivity.this.m2480h();
                } else {
                    if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS")) {
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    } else if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL")) {
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    } else {
                        if (action.equals("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_SUCCESS")) {
                            stringExtra = intent.getStringExtra("Address");
                            textView = RealtimeECGDisplayActivity.this.f2481j;
                        } else {
                            if (action.equals("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_FAIL")) {
                                RealtimeECGDisplayActivity.this.f2481j.setText("Failed to locate ！");
                            } else if (action.equals("com.hopetruly.ecg.services.MainService.MARK_TIME_START")) {
                                Toast.makeText(RealtimeECGDisplayActivity.this, RealtimeECGDisplayActivity.this.getString(R.string.marking_finish), 0).show();
                                return;
                            } else if (action.equals("com.holptruly.ecg.services.NetService.NET_CHANGE")) {
                                RealtimeECGDisplayActivity.this.m2473d();
                                return;
                            } else if (action.equals("com.hopetruly.ecg.util.ACTION_HEARTRATE")) {
                                stringExtra = intent.getStringExtra("heart_rate");
                                if (!stringExtra.equals("NaN")) {
                                    RealtimeECGDisplayActivity.this.f2493v.mo2450a(Integer.parseInt(stringExtra));
                                } else {
                                    RealtimeECGDisplayActivity.this.f2493v.mo2449a();
                                }
                                if (RealtimeECGDisplayActivity.this.f2493v.mo2456g()) {
                                    RealtimeECGDisplayActivity.this.f2464C.setVisibility(0);
                                    C0764a.m2746a(RealtimeECGDisplayActivity.this.f2464C);
                                } else {
                                    C0764a.m2747b(RealtimeECGDisplayActivity.this.f2464C);
                                    RealtimeECGDisplayActivity.this.f2464C.setVisibility(8);
                                }
                                textView = RealtimeECGDisplayActivity.this.f2480i;
                            } else if (action.equals("com.hopetruly.ecg.util.MyAlarmClock.COUNT_SEC")) {
                                RealtimeECGDisplayActivity.this.f2493v.mo2454e();
                                if (RealtimeECGDisplayActivity.this.f2493v.mo2451b() && !RealtimeECGDisplayActivity.this.f2493v.mo2455f()) {
                                    RealtimeECGDisplayActivity.this.f2463B.setText(R.string.ecg_alarm_status_on);
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
                    realtimeECGDisplayActivity.m2482i();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /* renamed from: a */
    WarickSurfaceView f2473a;

    /* renamed from: c */
    C0814c f2474c;

    /* renamed from: d */
    C0813b f2475d;

    /* renamed from: e */
    TextView f2476e;

    /* renamed from: f */
    TextView f2477f;

    /* renamed from: g */
    TextView f2478g;

    /* renamed from: h */
    TextView f2479h;

    /* renamed from: i */
    TextView f2480i;

    /* renamed from: j */
    TextView f2481j;

    /* renamed from: k */
    TextView f2482k;

    /* renamed from: l */
    TextView f2483l;

    /* renamed from: m */
    TextView f2484m;

    /* renamed from: n */
    TextView f2485n;

    /* renamed from: o */
    TextView f2486o;

    /* renamed from: p */
    MenuItem f2487p;

    /* renamed from: q */
    MenuItem f2488q;

    /* renamed from: r */
    MenuItem f2489r;

    /* renamed from: s */
    MenuItem f2490s;

    /* renamed from: t */
    ECGRecord f2491t;

    /* renamed from: u */
    MainService f2492u;

    /* renamed from: v */
    C0736b f2493v;

    /* renamed from: w */
    ECGApplication f2494w;

    /* renamed from: x */
    HeartRateCounter3 f2495x;

    /* renamed from: y */
    ProgressDialog f2496y;

    /* renamed from: a */
    private void m2459a() {
        TextView textView;
        int i;
        TextView textView2;
        int i2;
        bindService(new Intent(this, MainService.class), this.f2471J, 1);
        this.f2493v = new C0736b(this);
        this.f2495x = new HeartRateCounter3();
        if (getIntent().getIntExtra("Lead", 0) == 0) {
            this.f2491t.setLeadType(0);
            this.f2485n.setText(getString(R.string.l_with_hand));
            if (this.f2474c != null) {
                this.f2474c.mo2911a(true);
            }
            textView = this.f2462A;
            i = R.string.ecg_filter_status_on;
        } else {
            this.f2491t.setLeadType(1);
            this.f2485n.setText(getString(R.string.l_with_chest));
            if (this.f2474c != null) {
                this.f2474c.mo2911a(false);
            }
            textView = this.f2462A;
            i = R.string.ecg_filter_status_off;
        }
        textView.setText(getString(i));
        if (this.f2493v.mo2451b()) {
            textView2 = this.f2463B;
            i2 = R.string.ecg_alarm_status_on;
        } else {
            textView2 = this.f2463B;
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
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2472K, intentFilter);
    }

    /* renamed from: a */
    private void m2460a(int i) {
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
    public void m2461a(long j) {
        long j2 = j % 3600;
        TextView textView = this.f2478g;
        textView.setText("" + String.format("%02d", new Object[]{Integer.valueOf((int) (j / 3600))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((int) (j2 / 60))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((int) (j2 % 60))}));
        if (this.f2494w.f2085f.mo2641a() > 0 && j2 >= ((long) (this.f2494w.f2085f.mo2641a() * 60))) {
            m2476f();
            m2460a(this.f2494w.f2085f.mo2641a());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2465a(String str) {
        this.f2479h.setVisibility(0);
        this.f2485n.setVisibility(0);
        this.f2479h.setText(str);
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
    public void m2466a(byte[] bArr) {

        if (s.length() < 138) {
            bytesNum--;
        }
        if (bytesNum < 6) {
            int length = s.length();
            s += bytesToHex(bArr, 0, bArr.length, false);
            Log.d(f2461z, "s'length: " + length + "," + bytesNum);
//            Log.d(f2461z, "s'origin value: " + s);
            System.arraycopy(bArr, 0, gattValue, length/2, bArr.length);
//            Log.d(f2461z, "gattValue' origin value: " + bytesToHex(gattValue, 0, gattValue.length, false));
            bytesNum++;
            return;
        }

        int index = s.indexOf("56A1D");
        s = s.substring(index, s.length());
        Log.d(f2461z, "56a1d'index: " + index);
        System.arraycopy(gattValue, index/2, gattValue, 0, gattValue.length - index/2);
//        byte[] bRex = Arrays.copyOfRange(gattValue, index/2, (s.length() + index)/2);
//        Log.d(f2461z, "g'long sub: " + index/2 + ","  + bytesToHex(gattValue, 0, gattValue.length, false));
        String sub = s.substring(0, 7);
        if (sub.contains("56A1D0")) {
            realGattValue = Arrays.copyOfRange(gattValue, 6, 16);
            s = s.substring(42, s.length());
            int length = s.length();
            s += bytesToHex(bArr, 0, bArr.length, false);
//            Log.d(f2461z, "s'new sub: " + index + "," + s);
            System.arraycopy(gattValue, 21, gattValue, 0, length/2);
            System.arraycopy(bArr, 0, gattValue, length/2, bArr.length);
//            Log.d(f2461z, "g'new sub: " + index/2 + ","  + bytesToHex(gattValue, 0, gattValue.length, false));

        }else if (sub.contains("56A1D1")) {
            //todo
            s = s.substring(34, s.length());
            int length = s.length();
            s += bytesToHex(bArr, 0, bArr.length, false);
            System.arraycopy(gattValue, 17, gattValue, 0, length/2);
            System.arraycopy(bArr, 0, gattValue, length/2, bArr.length);
        }

//        Log.d(f2461z, "getGattValue: " + s + "-----" + index);
//        Log.d(f2461z, "getRealValue: " + bytesToHex(realGattValue, 0, realGattValue.length, false));
        String str;
        TextView textView;
        if (this.f2474c != null) {
            C0746b convertECG = Sensor.ECG.convertECG(realGattValue);
            f2492u.f2850b.mo2703a(convertECG.f2795b);
            for (int i = 0; i < convertECG.f2795b.length; i++) {
                this.f2474c.mo2918c((float) convertECG.f2795b[i]);
                this.f2495x.mo2439a((float) (convertECG.f2795b[i] * this.f2474c.mo2925h()));
                if (this.f2495x.getHrStatus() != 2 || this.f2495x.getHeartRate() <= 0) {
                    textView = this.f2480i;
                    str = "NaN";
                } else {
                    if (!String.valueOf(this.f2495x.getHeartRate()).equals("NaN")) {
                        this.f2493v.mo2450a(this.f2495x.getHeartRate());
                    } else {
                        this.f2493v.mo2449a();
                    }
                    if (this.f2493v.mo2456g()) {
                        this.f2464C.setVisibility(0);
                        C0764a.m2746a(this.f2464C);
                    } else {
                        C0764a.m2747b(this.f2464C);
                        this.f2464C.setVisibility(8);
                    }
                    textView = this.f2480i;
                    str = String.valueOf(this.f2495x.getHeartRate());
                }
                textView.setText(str);
            }
        }
    }

    /* renamed from: b */
    private void m2469b() {
        this.f2473a = (WarickSurfaceView) findViewById(R.id.heart_rate_sv);
        this.f2475d = new C0813b();
        this.f2473a.mo2893a((WarickSurfaceView.C0809a) this.f2475d);
        this.f2474c = new C0814c();
        this.f2474c.mo2908a(0);
        this.f2473a.mo2893a((WarickSurfaceView.C0809a) this.f2474c);
        this.f2473a.mo2892a(25);
        this.f2476e = (TextView) findViewById(R.id.ecg_values);
        this.f2478g = (TextView) findViewById(R.id.main_timer);
        this.f2479h = (TextView) findViewById(R.id.main_datatime);
        this.f2479h.setVisibility(8);
        this.f2485n = (TextView) findViewById(R.id.main_ecg_description);
        this.f2462A = (TextView) findViewById(R.id.main_ecg_filter_status);
        this.f2463B = (TextView) findViewById(R.id.main_ecg_alarm_status);
        this.f2464C = (Button) findViewById(R.id.ecg_bt_disable_alarm);
        this.f2464C.setVisibility(8);
        this.f2464C.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                C0764a.m2747b(view);
                ((Button) view).setVisibility(8);
                RealtimeECGDisplayActivity.this.f2493v.mo2452c();
                RealtimeECGDisplayActivity.this.f2463B.setText(RealtimeECGDisplayActivity.this.getString(R.string.ecg_alarm_hand_off));
            }
        });
        this.f2480i = (TextView) findViewById(R.id.main_heartrate);
        this.f2481j = (TextView) findViewById(R.id.main_location);
        this.f2482k = (TextView) findViewById(R.id.ecg_blr_status);
        this.f2482k.setText(getResources().getString(R.string.BLE_state));
        this.f2483l = (TextView) findViewById(R.id.ecg_auto_rec_status);
        this.f2484m = (TextView) findViewById(R.id.ecg_realtime_upload_status);
        this.f2486o = (TextView) findViewById(R.id.ecg_net_status);
        this.f2477f = (TextView) findViewById(R.id.ecg_scale_info);
    }

    /* renamed from: c */
    private void m2471c() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_tool, (ViewGroup) null);
        this.f2468G = new PopupWindow(inflate, -2, -2, false);
        this.f2468G.setBackgroundDrawable(new BitmapDrawable());
        this.f2468G.setOutsideTouchable(true);
        this.f2468G.setFocusable(true);
        this.f2465D = (Switch) inflate.findViewById(R.id.filter_switch);
        this.f2465D.setChecked(this.f2474c.mo2923f());
        this.f2465D.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                TextView b;
                RealtimeECGDisplayActivity realtimeECGDisplayActivity;
                int i;
                RealtimeECGDisplayActivity.this.f2474c.mo2911a(z);
                RealtimeECGDisplayActivity.this.f2489r.setVisible(z);
                RealtimeECGDisplayActivity.this.f2490s.setVisible(!z);
                if (RealtimeECGDisplayActivity.this.f2474c.mo2923f()) {
                    b = RealtimeECGDisplayActivity.this.f2462A;
                    realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                    i = R.string.ecg_filter_status_on;
                } else {
                    b = RealtimeECGDisplayActivity.this.f2462A;
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
                        textView = RealtimeECGDisplayActivity.this.f2477f;
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_12_5;
                        break;
                    case R.id.ecg_scale_type_25 /*2131165298*/:
                        RealtimeECGDisplayActivity.this.f2474c.mo2907a(1.0f);
                        textView = RealtimeECGDisplayActivity.this.f2477f;
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_25;
                        break;
                    case R.id.ecg_scale_type_50 /*2131165299*/:
                        RealtimeECGDisplayActivity.this.f2474c.mo2907a(2.0f);
                        textView = RealtimeECGDisplayActivity.this.f2477f;
                        realtimeECGDisplayActivity = RealtimeECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_50;
                        break;
                    default:
                        return;
                }
                textView.setText(realtimeECGDisplayActivity.getString(i2));
            }
        });
        this.f2477f.setText(getString(R.string.l_ecg_scale_25));
        inflate.findViewById(R.id.btn_previous).setVisibility(8);
        inflate.findViewById(R.id.btn_next).setVisibility(8);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_comment, (ViewGroup) null);
        this.f2469H = new PopupWindow(inflate2, -2, -2, true);
        this.f2469H.setBackgroundDrawable(new BitmapDrawable());
        this.f2469H.setOutsideTouchable(false);
        this.f2469H.setFocusable(true);
        this.f2466E = (EditText) inflate2.findViewById(R.id.ecg_comment_content);
        ((Button) inflate2.findViewById(R.id.ecg_comment_ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProgressDialog unused = RealtimeECGDisplayActivity.this.f2470I = ProgressDialog.show(RealtimeECGDisplayActivity.this, (CharSequence) null, RealtimeECGDisplayActivity.this.getString(R.string.saving_edit), true, true, (DialogInterface.OnCancelListener) null);
                new Thread(new Runnable() {
                    public void run() {
                        String obj = RealtimeECGDisplayActivity.this.f2466E.getText().toString();
                        if (obj != null) {
                            try {
                                File file = new File(RealtimeECGDisplayActivity.this.f2491t.getFilePath());
                                C0770f.m2780a(file, "text", obj);
                                RealtimeECGDisplayActivity.this.f2491t.setDescription(obj);
                                C0740b bVar = new C0740b(RealtimeECGDisplayActivity.this.getApplicationContext());
                                for (ECGRecord next : bVar.mo2467a(RealtimeECGDisplayActivity.this.f2494w.f2081b.getId())) {
                                    if (next.getFileName().equals(file.getName())) {
                                        RealtimeECGDisplayActivity.this.f2491t.setId(next.getId());
                                    }
                                }
                                bVar.mo2471b(RealtimeECGDisplayActivity.this.f2491t);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (RealtimeECGDisplayActivity.this.f2470I != null) {
                            RealtimeECGDisplayActivity.this.f2470I.dismiss();
                        }
                        ProgressDialog unused = RealtimeECGDisplayActivity.this.f2470I = null;
                    }
                }).start();
                RealtimeECGDisplayActivity.this.f2469H.dismiss();
                if (!RealtimeECGDisplayActivity.this.f2492u.mo2728b()) {
                    RealtimeECGDisplayActivity.this.onBackPressed();
                }
            }
        });
        ((Button) inflate2.findViewById(R.id.ecg_comment_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RealtimeECGDisplayActivity.this.f2469H.dismiss();
                if (!RealtimeECGDisplayActivity.this.f2492u.mo2728b()) {
                    RealtimeECGDisplayActivity.this.onBackPressed();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m2473d() {
        TextView textView;
        Resources resources;
        int i;
        TextView textView2;
        Resources resources2;
        int i2;
        int i3;
        Resources resources3;
        TextView textView3;
        if (this.f2494w.f2085f.mo2644b() == 1) {
            textView = this.f2483l;
            resources = getResources();
            i = R.string.auto_save;
        } else {
            textView = this.f2483l;
            resources = getResources();
            i = R.string.auto_save1;
        }
        textView.setText(resources.getString(i));
        if (this.f2494w.f2085f.mo2648d() == 1) {
            textView2 = this.f2484m;
            resources2 = getResources();
            i2 = R.string.Real_time_upload;
        } else {
            textView2 = this.f2484m;
            resources2 = getResources();
            i2 = R.string.Real_time_upload1;
        }
        textView2.setText(resources2.getString(i2));
        switch (this.f2494w.f2093n) {
            case -1:
                textView3 = this.f2486o;
                resources3 = getResources();
                i3 = R.string.network_state;
                break;
            case 0:
                textView3 = this.f2486o;
                resources3 = getResources();
                i3 = R.string.network_state_mobile;
                break;
            case 1:
                textView3 = this.f2486o;
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
    public void m2475e() {
        if (!this.f2492u.mo2736j()) {
            if (!this.f2492u.mo2728b()) {
                Toast.makeText(getApplicationContext(), getString(R.string.ble_not_connect), 0).show();
                return;
            }
            this.f2474c.mo2926i();
            this.f2492u.mo2721a(this.f2491t);
            this.f2495x.init();
            this.f2493v.mo2453d();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m2476f() {
        if (this.f2492u.mo2736j()) {
            this.f2491t.setHeartRate(this.f2495x.getAvgHr());
            this.f2492u.mo2734h();
            this.f2479h.setVisibility(8);
            this.f2485n.setVisibility(8);
            if (this.f2494w.f2085f.mo2644b() == 0) {
                m2484j();
                return;
            }
            this.f2492u.mo2735i();
            this.f2469H.showAtLocation(findViewById(R.id.heart_rate_sv), 17, 0, 0);
            this.f2466E.setText("");
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
                if (!RealtimeECGDisplayActivity.this.f2467F) {
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
    public void m2480h() {
        this.f2496y = new ProgressDialog(this);
        this.f2496y.setMessage(getResources().getString(R.string.Saving));
        this.f2496y.setCancelable(false);
        this.f2496y.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void m2482i() {
        this.f2496y.dismiss();
    }

    /* renamed from: j */
    private void m2484j() {
        this.f2467F = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Save));
        builder.setMessage(getResources().getString(R.string.Save_to_file));
        builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean unused = RealtimeECGDisplayActivity.this.f2467F = false;
                RealtimeECGDisplayActivity.this.f2492u.mo2735i();
                RealtimeECGDisplayActivity.this.f2469H.showAtLocation(RealtimeECGDisplayActivity.this.findViewById(R.id.heart_rate_sv), 17, 0, 0);
                RealtimeECGDisplayActivity.this.f2466E.setText("");
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean unused = RealtimeECGDisplayActivity.this.f2467F = false;
                dialogInterface.dismiss();
                if (!RealtimeECGDisplayActivity.this.f2492u.mo2728b()) {
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
        if (this.f2492u.mo2736j()) {
            m2486k();
        } else {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint("LongLogTag")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(f2461z, "oncreat~~~~");
        this.f2494w = (ECGApplication) getApplication();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.f2491t = new ECGRecord();
        getWindow().setFlags(128, 128);
        setContentView(R.layout.activity_realtime_ecg_display);
        m2469b();
        m2459a();
        m2471c();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ecg_realtime_display_menu, menu);
        this.f2487p = menu.findItem(R.id.action_ecg_start);
        this.f2488q = menu.findItem(R.id.action_ecg_stop);
        this.f2487p.setVisible(true);
        this.f2489r = menu.findItem(R.id.action_ecg_filter_on);
        this.f2490s = menu.findItem(R.id.action_ecg_filter_off);
        this.f2489r.setVisible(this.f2474c.mo2923f());
        this.f2490s.setVisible(!this.f2474c.mo2923f());
        return true;
    }

    /* access modifiers changed from: protected */
    @SuppressLint("LongLogTag")
    public void onDestroy() {
        Log.i(f2461z, "onDestroy~~~~");
        if (this.f2492u != null) {
            this.f2492u.mo2747r();
        }
        unbindService(this.f2471J);
        this.f2493v.mo2459j();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2472K);
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
                    this.f2465D.setChecked(true);
                    this.f2489r.setVisible(true);
                    this.f2490s.setVisible(false);
                    textView = this.f2462A;
                    i = R.string.ecg_filter_status_on;
                    break;
                case R.id.action_ecg_filter_on /*2131165207*/:
                    this.f2474c.mo2911a(false);
                    this.f2465D.setChecked(false);
                    this.f2489r.setVisible(false);
                    this.f2490s.setVisible(true);
                    textView = this.f2462A;
                    i = R.string.ecg_filter_status_off;
                    break;
                case R.id.action_ecg_options /*2131165208*/:
                    try {
                        this.f2468G.showAsDropDown(findViewById(menuItem.getItemId()));
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                case R.id.action_ecg_reverse /*2131165209*/:
                    this.f2474c.mo2924g();
                    return true;
                case R.id.action_ecg_start /*2131165210*/:
                    m2475e();
                    this.f2488q.setVisible(true);
                    this.f2487p.setVisible(false);
                    return true;
                case R.id.action_ecg_stop /*2131165211*/:
                    m2476f();
                    this.f2493v.mo2452c();
                    C0764a.m2747b(this.f2464C);
                    this.f2464C.setVisibility(8);
                    this.f2488q.setVisible(false);
                    this.f2487p.setVisible(true);
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
        Log.i(f2461z, "onResume~~~~");
        m2473d();
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.f2468G.dismiss();
        super.onStop();
    }
}
