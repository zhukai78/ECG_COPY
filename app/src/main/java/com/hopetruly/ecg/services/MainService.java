package com.hopetruly.ecg.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.algorithm.C0735a;
import com.hopetruly.ecg.algorithm.C0737c;
import com.hopetruly.ecg.device.C0745a;
import com.hopetruly.ecg.device.C0746b;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.device.SimpleKeysStatus;
import com.hopetruly.ecg.entity.ECGEntity;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.entity.PedometerRecord;
import com.hopetruly.ecg.p022b.C0740b;
import com.hopetruly.ecg.util.C0771g;
import com.hopetruly.ecg.util.C0772h;
import com.hopetruly.ecg.util.C0773i;
import com.hopetruly.ecg.util.C0774j;
import com.hopetruly.part.net.C0790a;
import com.hopetruly.part.net.NetService;
import com.hopetruly.part.p024a.C0777a;
import com.warick.p025a.C0801d;
import com.warick.p026b.p027a.C0806b;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainService extends Service {

    public PedometerRecord f2845A = null;

    public boolean f2846B = false;

    public boolean f2847C = false;

    private C0801d.C0804a f2848D = new C0801d.C0804a() {
        /* renamed from: a */
        public void mo2755a(double d, double d2, String str) {
            Intent intent = new Intent();
            if (str == null) {
                intent.setAction("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_FAIL");
            } else {
                intent.setAction("com.hopetruly.ecg.services.MainService.RECEIVE_LOCATION_SUCCESS");
                intent.putExtra("Address", str);
            }
//            C0140d.m485a(MainService.this.getApplicationContext()).mo390a(intent);
            LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(intent);

        }
    };

    String f2849a = "MainService";

    /* renamed from: b */
    public FileService f2850b;

    /* renamed from: c */
    public NetService f2851c;

    /* renamed from: d */
    ECGApplication f2852d;

    /* renamed from: e */
    C0737c f2853e;

    /* renamed from: f */
    C0735a f2854f;

    /* renamed from: g */
    ArrayList<Sensor> f2855g = new ArrayList<>();

    /* renamed from: h */
    C0773i f2856h;

    /* renamed from: i */
    long f2857i = 0;

    /* renamed from: j */
    boolean f2858j = false;

    /* renamed from: k */
    Timer f2859k = null;

    /* renamed from: l */
    TimerTask f2860l = null;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public C0777a f2861m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public boolean f2862n = false;

    /* renamed from: o */
    private String f2863o;
    /* access modifiers changed from: private */

    /* renamed from: p */
    public C0740b f2864p = null;

    /* renamed from: q */
    private C0774j f2865q;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public int f2866r = 0;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public long f2867s = 5;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public boolean f2868t = false;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public C0772h f2869u;

    /* renamed from: v */
    private final IBinder f2870v = new C0762a();

    private ServiceConnection f2871w = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof NetService.C0786c) {
                MainService.this.f2851c = ((NetService.C0786c) iBinder).mo2852a();
                if (MainService.this.f2852d.f2085f.mo2648d() == 1 && !MainService.this.f2851c.mo2831e()) {
                    MainService.this.f2851c.mo2828c();
                }
            } else if (iBinder instanceof FileService.C0755a) {
                MainService.this.f2850b = ((FileService.C0755a) iBinder).mo2708a();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainService.this.f2850b = null;
            MainService.this.f2851c = null;
        }
    };

    private BroadcastReceiver f2872x = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager a = null;
            Intent intent2 = null;
            Intent intent3 = null;
            LocalBroadcastManager a2 = null;
            int c;
            ArrayList<Sensor> arrayList;
            Sensor sensor;
            boolean z;
            C0777a d = null;
            UUID data = null;
            UUID service = null;
            ArrayList<Sensor> arrayList2;
            Sensor sensor2 = null;
            try {
                String action = intent.getAction();
                if ("com.hopetruly.ec.services.ACTION_GATT_CONNECTED".equals(action)) {
                    boolean unused = MainService.this.f2862n = true;
                    MainService.this.startForeground(101, MainService.this.m2709w());
                    return;
                }
                int i = 0;
                if ("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED".equals(action)) {
                    boolean unused2 = MainService.this.f2862n = false;
                    MainService.this.stopForeground(true);
                    if (MainService.this.f2846B) {
                        MainService.this.mo2740n();
                    }
                    MainService.this.f2852d.f2080a = null;
                } else if (!"com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED".equals(action)) {
                    if (action.equals("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ")) {
                        String stringExtra = intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID");
                        if (stringExtra.equals(C0745a.f2769b.toString())) {
                            byte[] byteArrayExtra = intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA");
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i2 = 0; i2 < byteArrayExtra.length; i2++) {
                                stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(byteArrayExtra[i2])}));
                            }
                            MainService.this.f2852d.f2080a.setId(stringBuffer.toString());
                            if (MainService.this.f2851c.mo2826b() != -1 && MainService.this.f2852d.f2084e.getInt("DEVICE_ID_UPLOAD", 0) == 1) {
                                MainService.this.f2851c.mo2825a(MainService.this.f2852d.f2080a.getId(), MainService.this.f2852d.f2081b.getName());
                            }
                            d = MainService.this.f2861m;
                            data = C0745a.f2772e;
                            service = C0745a.f2768a;
                        } else if (stringExtra.equals(C0745a.f2772e.toString())) {
                            MainService.this.f2852d.f2080a.setFwRev(new String(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA")));
                            d = MainService.this.f2861m;
                            data = C0745a.f2774g;
                            service = C0745a.f2768a;
                        } else if (stringExtra.equals(C0745a.f2774g.toString())) {
                            MainService.this.f2852d.f2080a.setManufacturerName(new String(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA")));
                            List<BluetoothGattService> e = MainService.this.f2861m.mo2809e();
                            if (e != null) {
                                z = false;
                                while (i < e.size()) {
                                    if (e.get(i).getUuid().compareTo(Sensor.BATTERY.getService()) == 0) {
                                        z = true;
                                    }
                                    i++;
                                }
                            } else {
                                z = false;
                            }
                            if (z) {
                                d = MainService.this.f2861m;
                                data = Sensor.BATTERY.getData();
                                service = Sensor.BATTERY.getService();
                            } else {
                                a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                intent2 = new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END");
                            }
                        } else if (stringExtra.equals(Sensor.BATTERY.getData().toString())) {
                            int unused3 = MainService.this.f2866r = Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                            MainService.this.f2852d.f2080a.setBatteryLevel(MainService.this.f2866r);
                            if (MainService.this.f2866r < 20) {
                                LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.POWER_LOW"));
                            }
                            if (MainService.this.f2866r < 10) {
                                new C0774j(MainService.this.getApplicationContext()).mo2790a("ECG Air low power", "Power " + MainService.this.f2866r + " %");
                            }
                            a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            intent2 = new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END");
                        } else {
                            return;
                        }
                        d.mo2802a(data, service);
                        return;
                    } else if ("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE".equals(action)) {
                        if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(C0745a.f2780m.toString())) {
                            C0771g.m2784a(MainService.this.f2849a, "begin  notify sensors !");
                            a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            intent2 = new Intent("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE");
                        } else {
                            return;
                        }
                    } else if (!action.equals("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORREAD")) {
                        if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE")) {
                            C0771g.m2787d(MainService.this.f2849a, "sensors.size():" + MainService.this.f2855g.size());
                            if (MainService.this.f2855g.size() > 0) {
                                if (MainService.this.f2855g.contains(Sensor.ACCELEROMETER)) {
                                    MainService.this.f2861m.mo2803a(Sensor.ACCELEROMETER.getService(), Sensor.ACCELEROMETER.getData(), true);
                                    arrayList = MainService.this.f2855g;
                                    sensor = Sensor.ACCELEROMETER;
                                } else if (MainService.this.f2855g.contains(Sensor.ECG)) {
                                    MainService.this.f2861m.mo2803a(Sensor.ECG.getService(), Sensor.ECG.getData(), true);
                                    arrayList = MainService.this.f2855g;
                                    sensor = Sensor.ECG;
                                } else if (MainService.this.f2855g.contains(Sensor.SIMPLE_KEYS)) {
                                    MainService.this.f2861m.mo2803a(Sensor.SIMPLE_KEYS.getService(), Sensor.SIMPLE_KEYS.getData(), true);
                                    arrayList = MainService.this.f2855g;
                                    sensor = Sensor.SIMPLE_KEYS;
                                } else if (MainService.this.f2855g.contains(Sensor.BATTERY)) {
                                    MainService.this.f2861m.mo2803a(Sensor.BATTERY.getService(), Sensor.BATTERY.getData(), true);
                                    arrayList = MainService.this.f2855g;
                                    sensor = Sensor.BATTERY;
                                } else {
//                                    C0771g.m2787d(MainService.this.f2849a, "sensor notify error.sensorlist have unknow sensor!");
                                    MainService.this.f2852d.f2080a.setId("7CEC793F53F5");
                                    MainService.this.f2852d.f2080a.setFwRev("MUIRHEAD.A3.2.2\u0000");
                                    MainService.this.f2852d.f2080a.setManufacturerName("Warick Medical");
                                    LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END"));
                                    return;
                                }
                                arrayList.remove(sensor);
                                return;
                            }
                            MainService.this.mo2731e();
                        } else if ("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY".equals(action)) {
                            String stringExtra2 = intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID");
                            if (stringExtra2.equals(Sensor.ACCELEROMETER.getData().toString())) {
                                byte[] byteArrayExtra2 = intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA");
                                float f = (((float) (((double) byteArrayExtra2[0]) / 16.0d)) * 1000.0f) / 5.0f;
                                float f2 = (((float) (((double) byteArrayExtra2[1]) / 16.0d)) * 1000.0f) / 5.0f;
                                float f3 = (((float) (((double) (-1 * byteArrayExtra2[2])) / 16.0d)) * 1000.0f) / 5.0f;
                                double sqrt = Math.sqrt((double) ((f * f) + (f2 * f2) + (f3 * f3)));
                                if (MainService.this.f2846B) {
                                    MainService.this.f2853e.mo2461a((float) sqrt);
                                }
                                if (MainService.this.f2847C) {
                                    MainService.this.f2854f.mo2448a((int) f, (int) f2, (int) f3, (int) sqrt);
                                    return;
                                }
                                return;
                            } else if (stringExtra2.equals(Sensor.ECG.getData().toString()) && MainService.this.f2874z) {
                                C0746b convertECG = Sensor.ECG.convertECG(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                                MainService.this.f2850b.mo2703a(convertECG.f2795b);
                                if (MainService.this.f2851c.mo2831e()) {
                                    MainService.this.f2851c.mo2823a(convertECG.f2795b, 75);
                                    return;
                                }
                                return;
                            } else if (stringExtra2.equals(Sensor.SIMPLE_KEYS.getData().toString())) {
                                SimpleKeysStatus convertKeys = Sensor.SIMPLE_KEYS.convertKeys(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                                C0771g.m2784a(MainService.this.f2849a, "simple key status : " + convertKeys.toString());
                                if (convertKeys.equals(SimpleKeysStatus.ALARM_BUTTON)) {
                                    C0774j jVar = new C0774j(MainService.this.getApplicationContext());
                                    if (MainService.this.f2852d.f2085f.mo2660j() == 1) {
                                        C0806b.m2927a(MainService.this.f2852d);
                                        jVar.mo2790a("SOS", "Send SMS");
                                    }
                                    if (MainService.this.f2852d.f2085f.mo2662k() == 1 && MainService.this.f2874z && (c = MainService.this.f2850b.mo2705c()) > 0 && MainService.this.f2869u.mo2779a(c, MainService.this.f2852d.f2085f.mo2666m())) {
                                        Log.e(MainService.this.f2849a, "Mark time..");
                                        a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                        intent2 = new Intent("com.hopetruly.ecg.services.MainService.MARK_TIME_START");
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else if (stringExtra2.equals(Sensor.BATTERY.getData().toString())) {
                                int unused4 = MainService.this.f2866r = Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                                MainService.this.f2852d.f2080a.setBatteryLevel(MainService.this.f2866r);
                                C0771g.m2784a(MainService.this.f2849a, "battery level:" + MainService.this.f2866r);
                                if (MainService.this.f2866r < 20) {
                                    LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.POWER_LOW"));
                                }
                                if (MainService.this.f2866r < 10) {
                                    new C0774j(MainService.this.getApplicationContext()).mo2790a("ECG Air low power", "Power " + MainService.this.f2866r + " %");
                                    return;
                                }
                                return;
                            } else {
                                return;
                            }
                        } else {
                            if ("com.holptruly.ecg.services.FileService.FILE_SAVE_SUCCESS".equals(action)) {
                                String stringExtra3 = intent.getStringExtra("com.holptruly.ecg.services.FileService.EXTRA_FILE");
                                MainService.this.f2873y.setFilePath(stringExtra3);
                                MainService.this.f2873y.setFileName(new File(stringExtra3).getName());
                                MainService.this.f2873y.setMark_time(MainService.this.f2873y.getEcgEntity().getMark_time());
                                MainService.this.f2864p.mo2469a(MainService.this.f2873y);
                                MainService.this.f2864p.mo2468a();
                                if (MainService.this.f2852d.f2085f.mo2646c() == 1) {
                                    MainService.this.f2851c.mo2820a(MainService.this.f2873y);
                                }
                                intent3 = new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS");
                                a2 = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            } else if (action.equals("com.holptruly.ecg.services.FileService.FILE_SAVE_FAIL")) {
                                intent3 = new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL");
                                a2 = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            } else if (action.equals("com.hopetruly.part.StepCounter.STEP")) {
                                if (MainService.this.f2845A != null) {
                                    long longExtra = intent.getLongExtra("step_value", MainService.this.f2845A.getCurStep());
                                    if (longExtra > MainService.this.f2867s) {
                                        MainService.this.f2845A.setCurStep(longExtra - MainService.this.f2867s);
                                        boolean unused5 = MainService.this.f2868t = true;
                                    }
                                }
                                a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                intent2 = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_STEP");
                            } else if (action.equals("com.hopetruly.part.StepCounter.CAL")) {
                                if (MainService.this.f2845A != null) {
                                    double doubleExtra = intent.getDoubleExtra("cal_value", 0.0d);
                                    if (MainService.this.f2868t) {
                                        MainService.this.f2845A.setCal(((long) doubleExtra) + MainService.this.f2845A.getCal());
                                    }
                                }
                                a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                intent2 = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_CAL");
                            } else if (action.equals("com.hopetruly.ecg.util.MyAlarmClock.DATE_CHANGE")) {
                                if (MainService.this.f2845A != null) {
                                    if (MainService.this.f2845A.getTarget() == 0 && MainService.this.f2845A.getCurStep() == 0 && MainService.this.f2845A.getCount() == 0) {
                                        MainService.this.f2864p.mo2472b(MainService.this.f2845A);
                                    } else {
                                        MainService.this.f2864p.mo2474c(MainService.this.f2845A);
                                    }
                                }
                                if (MainService.this.f2846B) {
                                    MainService.this.m2710x();
                                    MainService.this.m2711y();
                                    return;
                                }
                                return;
                            } else if (!action.equals("com.holptruly.part.FallDetection.FALLDOWN") && "com.holptruly.ecg.services.NetService.UPLOAD_ID_SUCCESS".equals(action)) {
                                SharedPreferences.Editor edit = MainService.this.f2852d.f2084e.edit();
                                MainService.this.f2852d.f2083d.mo2691e(0);
                                edit.putInt("DEVICE_ID_UPLOAD", MainService.this.f2852d.f2083d.mo2692f());
                                edit.commit();
                                return;
                            } else {
                                return;
                            }
                            a2.sendBroadcast(intent3);
                            return;
                        }
                    } else {
                        return;
                    }
                    a.sendBroadcast(intent2);
                } else if (MainService.this.f2862n) {
                    MainService.this.f2855g.clear();
                    List<BluetoothGattService> e2 = MainService.this.f2861m.mo2809e();
                    if (e2 != null) {
                        while (i < e2.size()) {
                            UUID uuid = e2.get(i).getUuid();
                            if (uuid.compareTo(Sensor.ACCELEROMETER.getService()) == 0) {
                                arrayList2 = MainService.this.f2855g;
                                sensor2 = Sensor.ACCELEROMETER;
                            } else if (uuid.compareTo(Sensor.ECG.getService()) == 0) {
                                arrayList2 = MainService.this.f2855g;
                                sensor2 = Sensor.ECG;
                            } else if (uuid.compareTo(Sensor.SIMPLE_KEYS.getService()) == 0) {
                                arrayList2 = MainService.this.f2855g;
                                sensor2 = Sensor.SIMPLE_KEYS;
                            } else if (uuid.compareTo(Sensor.BATTERY.getService()) == 0) {
                                arrayList2 = MainService.this.f2855g;
                                sensor2 = Sensor.BATTERY;
                            } else {
                                i++;
                            }
                            MainService.this.f2855g.add(sensor2);
                            i++;
                        }
                    }
                    MainService.this.mo2726b(10);
                }
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            }
        }
    };

    public ECGRecord f2873y = null;
    /* access modifiers changed from: private */

    /* renamed from: z */
    public boolean f2874z = false;

    /* renamed from: com.hopetruly.ecg.services.MainService$a */
    public class C0762a extends Binder {
        public C0762a() {
        }

        /* renamed from: a */
        public MainService mo2756a() {
            return MainService.this;
        }
    }

    /* renamed from: v */
    private static IntentFilter m2708v() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_CONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORREAD");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE");
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_SAVE_SUCCESS");
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_SAVE_FAIL");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.CONNECT_REMOTE_HOST_FAIL");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.UPLOAD_ID_SUCCESS");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("com.hopetruly.part.StepCounter.STEP");
        intentFilter.addAction("com.hopetruly.part.StepCounter.CAL");
        intentFilter.addAction("com.holptruly.part.FallDetection.FALLDOWN");
        intentFilter.addAction("com.hopetruly.ecg.util.MyAlarmClock.DATE_CHANGE");
        return intentFilter;
    }

    public Notification m2709w() {
//        C0097x.C0102d b = new C0097x.C0102d(this).mo351a((int) R.drawable.ic_launcher).mo353a((CharSequence) "ECG").mo356b((CharSequence) "已连接到蓝牙设备！");
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this).setContentText("ECG").setContentText("已连接到蓝牙设备！");
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(new ComponentName(getPackageName(), "com.hopetruly.ecg.activity.BeginActivity"));
//        intent.setFlags(270532608);
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//        b.mo352a(PendingIntent.getActivity(this, 0, intent, 134217728));
        notificationCompat.setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
        return notificationCompat.getNotification();
    }

    public void m2710x() {
        this.f2853e.mo2462b();
        this.f2853e.mo2460a();
        this.f2868t = false;
    }

    /* access modifiers changed from: private */
    /* renamed from: y */
    public void m2711y() {
        Calendar instance = Calendar.getInstance();
        mo2746q();
        if (this.f2845A == null) {
            this.f2845A = new PedometerRecord();
            this.f2845A.setUserId(this.f2852d.f2081b.getId());
            this.f2845A.setTarget(this.f2852d.f2087h.mo2672b());
            this.f2845A.setYear(instance.get(1));
            this.f2845A.setMonth(instance.get(2) + 1);
            this.f2845A.setDay(instance.get(5));
            this.f2864p.mo2470a(this.f2845A);
        }
    }

    /* renamed from: a */
    public void mo2719a() {
        C0771g.m2784a(this.f2849a, "ExitService~~");
        mo2734h();
        mo2737k();
        this.f2856h.mo2788b();
        C0790a.m2869a().mo2863b();
        stopSelf();
    }

    /* renamed from: a */
    public void mo2720a(int i) {
        if (this.f2861m != null) {
            this.f2861m.mo2797a(i);
        } else {
            C0771g.m2786c(this.f2849a, "StartScanBLE>BLEservice = null");
        }
    }

    /* renamed from: a */
    public void mo2721a(ECGRecord eCGRecord) {
        C0771g.m2784a(this.f2849a, "StartECG~~~~~");
        if (this.f2862n) {
            this.f2874z = true;
            this.f2857i = 0;
            mo2748s();
            this.f2873y = eCGRecord;
            this.f2873y.setUser(this.f2852d.f2081b);
            this.f2873y.setMachine(this.f2852d.f2080a);
            Date date = new Date(System.currentTimeMillis());
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
            this.f2873y.setTime(format);
            ECGEntity eCGEntity = new ECGEntity();
            eCGEntity.setStartTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(date));
            eCGEntity.setLead(ECGEntity.LEAD_I);
            eCGEntity.setLeadExten(eCGRecord.getLeadType() == 1 ? ECGEntity.LEAD_PART_CHEST : ECGEntity.LEAD_PART_HAND);
            this.f2873y.setEcgEntity(eCGEntity);
            this.f2850b.mo2699a();
            if (this.f2852d.f2085f.mo2648d() == 1) {
                this.f2851c.mo2828c();
            }
            Intent intent = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME");
            intent.putExtra("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME", format);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            mo2724a(Sensor.ECG, true);
            return;
        }
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.ble_not_connect), Toast.LENGTH_SHORT).show();
        C0771g.m2786c(this.f2849a, "StartECG>蓝牙未连接错误！");
    }

    /* renamed from: a */
    public void mo2722a(ECGRecord eCGRecord, float[] fArr) {
        mo2727b(eCGRecord);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_START"));
        this.f2850b.mo2701a(eCGRecord, fArr);
    }

    /* renamed from: a */
    public void mo2723a(String str) {
        if (this.f2861m != null) {
            this.f2863o = str;
            this.f2861m.mo2808d();
            this.f2861m.mo2801a(str);
            return;
        }
        C0771g.m2786c(this.f2849a, "ConnectBLE>BLEservice = null");
    }

    /* renamed from: a */
    public boolean mo2724a(Sensor sensor, boolean z) {
        BluetoothGattService a;
        BluetoothGattCharacteristic characteristic;
        if (this.f2861m == null || (a = this.f2861m.mo2796a(sensor.getService())) == null || (characteristic = a.getCharacteristic(sensor.getConfig())) == null) {
            return false;
        }
        characteristic.setValue(new byte[]{z ? (byte) 1 : 0});
        return this.f2861m.mo2799a(characteristic);
    }

    /* renamed from: a */
    public boolean mo2725a(UUID uuid, UUID uuid2, int i) {
        BluetoothGattService a;
        BluetoothGattCharacteristic characteristic;
        if (this.f2861m == null || (a = this.f2861m.mo2796a(uuid)) == null || (characteristic = a.getCharacteristic(uuid2)) == null) {
            return false;
        }
        characteristic.setValue(new byte[]{(byte) i});
        return this.f2861m.mo2799a(characteristic);
    }

    /* renamed from: b */
    public void mo2726b(int i) {
        if (this.f2861m == null || !this.f2862n) {
            C0771g.m2786c(this.f2849a, "SetStepSpeed>mBLEHelper = null");
            return;
        }
        String str = this.f2849a;
        C0771g.m2787d(str, "set Step peroid:" + i);
        mo2725a(C0745a.f2777j, C0745a.f2780m, i);
    }

    /* renamed from: b */
    public void mo2727b(ECGRecord eCGRecord) {
        this.f2873y = eCGRecord;
    }

    /* renamed from: b */
    public boolean mo2728b() {
        return this.f2862n;
    }

    /* renamed from: c */
    public void mo2729c() {
        if (this.f2861m != null) {
            this.f2861m.mo2808d();
        } else {
            C0771g.m2786c(this.f2849a, "StopScanBLE>BLEservice = null");
        }
    }

    /* renamed from: d */
    public void mo2730d() {
        if (this.f2861m != null) {
            C0771g.m2786c(this.f2849a, "DisconnectBLE");
            this.f2861m.mo2805b();
            return;
        }
        C0771g.m2786c(this.f2849a, "DisconnectBLE>BLEservice = null");
    }

    /* renamed from: e */
    public void mo2731e() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_BEGIN"));
        if (this.f2861m != null) {
            String str = this.f2849a;
            C0771g.m2784a(str, "read deciev info ->" + this.f2861m.mo2802a(C0745a.f2769b, C0745a.f2768a));
            return;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END"));
        C0771g.m2786c(this.f2849a, "StopScanBLE>BLEservice = null");
    }

    /* renamed from: f */
    public void mo2732f() {
        if (this.f2861m != null) {
            List<BluetoothGattService> e = this.f2861m.mo2809e();
            int i = 0;
            if (e != null) {
                int i2 = 0;
                while (i < e.size()) {
                    if (e.get(i).getUuid().compareTo(Sensor.BATTERY.getService()) == 0) {
                        i2 = 1;
                    }
                    i++;
                }
                i = i2;
            }
            if (i != 0) {
                this.f2861m.mo2802a(Sensor.BATTERY.getData(), Sensor.BATTERY.getService());
                return;
            }
            return;
        }
        C0771g.m2786c(this.f2849a, "StopGetDeviceBattery>BLEservice is null");
    }

    /* renamed from: g */
    public C0777a mo2733g() {
        return this.f2861m;
    }

    /* renamed from: h */
    public void mo2734h() {
        C0771g.m2784a(this.f2849a, "StopECG~~~~~");
        if (this.f2874z) {
            this.f2874z = false;
            if (this.f2862n) {
                mo2724a(Sensor.ECG, false);
            } else {
                C0771g.m2786c(this.f2849a, "StartECG>蓝牙未连接错误！");
            }
            mo2749t();
            this.f2869u.mo2777a(this.f2850b.mo2705c());
            this.f2850b.mo2704b();
            this.f2851c.mo2830d();
            String str = this.f2849a;
            C0771g.m2784a(str, "SaveECG>timeSec:" + this.f2857i + "");
            this.f2857i = this.f2857i % 3600;
            int i = (int) (this.f2857i % 60);
            ECGRecord eCGRecord = this.f2873y;
            eCGRecord.setPeriod(String.format("%02d", new Object[]{Integer.valueOf((int) (this.f2857i / 3600))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((int) (this.f2857i / 60))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(i)}));
            this.f2873y.getEcgEntity().setEndTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date(System.currentTimeMillis())));
            this.f2873y.getEcgEntity().setMark_time(this.f2869u.mo2785c());
            int[] iArr = null;
            if (this.f2873y.getEcgEntity().getMark_time() != null) {
                iArr = this.f2869u.mo2780a();
            }
            this.f2873y.getEcgEntity().setMark_period(iArr);
            this.f2869u.mo2782b();
        }
    }

    /* renamed from: i */
    public void mo2735i() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_START"));
        this.f2850b.mo2700a(this.f2873y);
    }

    /* renamed from: j */
    public boolean mo2736j() {
        return this.f2874z;
    }

    /* renamed from: k */
    public boolean mo2737k() {
        if (!this.f2862n) {
            return false;
        }
        return mo2724a(Sensor.ACCELEROMETER, false);
    }

    /* renamed from: l */
    public boolean mo2738l() {
        this.f2852d.f2087h.mo2670a(1);
        if (!mo2747r()) {
            return false;
        }
        m2711y();
        this.f2845A.setCurStep(0);
        this.f2846B = true;
        return true;
    }

    /* renamed from: m */
    public boolean mo2739m() {
        this.f2852d.f2087h.mo2670a(0);
        if (!mo2737k()) {
            return false;
        }
        this.f2846B = false;
        if (this.f2845A == null) {
            return true;
        }
        this.f2845A.setCount(this.f2845A.getCount() + this.f2845A.getCurStep());
        if (this.f2845A.getTarget() == 0 && this.f2845A.getCurStep() == 0 && this.f2845A.getCount() == 0) {
            this.f2864p.mo2472b(this.f2845A);
        } else {
            this.f2864p.mo2474c(this.f2845A);
        }
        m2710x();
        return true;
    }

    /* renamed from: n */
    public boolean mo2740n() {
        this.f2852d.f2087h.mo2670a(0);
        if (this.f2862n) {
            return false;
        }
        this.f2846B = false;
        if (this.f2845A == null) {
            return true;
        }
        this.f2845A.setCount(this.f2845A.getCount() + this.f2845A.getCurStep());
        if (this.f2845A.getTarget() == 0 && this.f2845A.getCurStep() == 0 && this.f2845A.getCount() == 0) {
            this.f2864p.mo2472b(this.f2845A);
        } else {
            this.f2864p.mo2474c(this.f2845A);
        }
        m2710x();
        return true;
    }

    /* renamed from: o */
    public boolean mo2741o() {
        return this.f2846B;
    }

    public IBinder onBind(Intent intent) {
        return this.f2870v;
    }

    public void onCreate() {
        super.onCreate();
        C0771g.m2784a(this.f2849a, "onCreate ....");
        this.f2852d = (ECGApplication) getApplication();
        this.f2852d.f2095p.mo2770a((Service) this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2872x, m2708v());
        this.f2864p = new C0740b(getApplicationContext());
        Intent intent = new Intent();
        intent.setClass(this, FileService.class);
        bindService(intent, this.f2871w, Context.BIND_AUTO_CREATE);
        intent.setClass(this, NetService.class);
        bindService(intent, this.f2871w, Context.BIND_AUTO_CREATE);
        this.f2861m = new C0777a(this);
        this.f2865q = new C0774j(getApplicationContext());
        C0801d.m2908a();
        if (C0801d.m2916e() != null) {
            C0801d.m2916e().mo2887a(this.f2848D);
        }
        this.f2869u = new C0772h();
        this.f2853e = new C0737c(this, 0, 0);
        this.f2854f = new C0735a(this);
        this.f2856h = C0773i.m2798a((Context) this);
        this.f2856h.mo2786a();
        this.f2856h.mo2789b(true);
    }

    public void onDestroy() {
        C0771g.m2784a(this.f2849a, "onDestroy ....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2872x);
        this.f2852d.f2094o = null;
        if (!(this.f2861m == null && this.f2850b == null && this.f2851c == null)) {
            unbindService(this.f2871w);
        }
        C0801d.m2912b();
        if (this.f2862n) {
            mo2730d();
        }
        super.onDestroy();
    }

    /* renamed from: p */
    public PedometerRecord mo2745p() {
        return this.f2845A;
    }

    /* renamed from: q */
    public PedometerRecord mo2746q() {
        Calendar instance = Calendar.getInstance();
        this.f2845A = this.f2864p.mo2466a(this.f2852d.f2081b.getId(), instance.get(1), instance.get(2) + 1, instance.get(5));
        return this.f2845A;
    }

    /* renamed from: r */
    public boolean mo2747r() {
        if (!this.f2862n) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ble_not_connect), Toast.LENGTH_SHORT).show();
            C0771g.m2786c(this.f2849a, "ACC 加速度 >蓝牙未连接错误！");
            return false;
        }
        if ((this.f2852d.f2087h.mo2669a() == 1) || (this.f2852d.f2089j.mo2667a() == 1)) {
            return mo2724a(Sensor.ACCELEROMETER, true);
        }
        return false;
    }

    /* renamed from: s */
    public void mo2748s() {
        C0771g.m2784a(this.f2849a, "Timer>start!~~~");
        if (!this.f2858j) {
            if (this.f2859k == null) {
                this.f2859k = new Timer();
            }
            this.f2859k.schedule(new TimerTask() {
                public void run() {
                    MainService.this.f2857i++;
                    Intent intent = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_TIMER");
                    intent.putExtra("com.hopetruly.ecg.services.MainService.REFRESH_TIMER", MainService.this.f2857i);
                    LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(intent);
                }
            }, 1000, 1000);
            this.f2858j = true;
        }
    }

    /* renamed from: t */
    public void mo2749t() {
        C0771g.m2784a(this.f2849a, "Timer>stop!~~~");
        if (this.f2858j) {
            if (this.f2859k != null) {
                this.f2859k.purge();
                this.f2859k.cancel();
                this.f2859k = null;
            }
            this.f2858j = false;
            Intent intent = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_TIMER");
            intent.putExtra("com.hopetruly.ecg.services.MainService.REFRESH_TIMER", 0);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }

    /* renamed from: u */
    public int mo2750u() {
        return this.f2866r;
    }

}
