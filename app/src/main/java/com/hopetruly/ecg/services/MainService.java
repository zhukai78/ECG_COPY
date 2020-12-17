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
import com.hopetruly.ecg.algorithm.FallDownAlgorithm;
import com.hopetruly.ecg.algorithm.StepCounter;
import com.hopetruly.ecg.device.ECGUUIDS;
import com.hopetruly.ecg.device.ConvertECG;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.device.SimpleKeysStatus;
import com.hopetruly.ecg.entity.ECGEntity;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.entity.PedometerRecord;
import com.hopetruly.ecg.sql.SqlManager;
import com.hopetruly.ecg.util.EcgParserUtils;
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.ecg.util.MyAlarmClock;
import com.hopetruly.ecg.util.NotificationUtils;
import com.hopetruly.part.net.MyHttpClient;
import com.hopetruly.part.net.NetService;
import com.hopetruly.part.p024a.BleHelper;
import com.warick.gps.GpsManagerHelper;
import com.warick.sms.utils.GPSSosHelper;
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

    public PedometerRecord mainPedometerRecord = null;

    public boolean isGattStop = false;

    public boolean assertFallDown = false;

    private GpsManagerHelper.OnGpsListener monGpsListener = new GpsManagerHelper.OnGpsListener() {
        /* renamed from: a */
        public void sendLocationBroad(double d, double d2, String str) {
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

    String TAG = "MainService";

    /* renamed from: b */
    public FileService mmainFileService;

    /* renamed from: c */
    public NetService mmainNetService;

    /* renamed from: d */
    ECGApplication mmainecggApp;

    /* renamed from: e */
    StepCounter mainmStepCounter;

    /* renamed from: f */
    FallDownAlgorithm mFallDownAlgorithm;

    /* renamed from: g */
    ArrayList<Sensor> mSensors = new ArrayList<>();

    /* renamed from: h */
    MyAlarmClock myAlarmClock;

    /* renamed from: i */
    long saveTimeSec = 0;

    /* renamed from: j */
    boolean isStartTimer = false;

    /* renamed from: k */
    Timer refreshTIMER = null;


    /* renamed from: m */
    public BleHelper mainbleHelper;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public boolean isConn = false;

    /* renamed from: o */
    private String mMacAdrr;
    /* access modifiers changed from: private */

    /* renamed from: p */
    public SqlManager mSqlManager = null;

    /* renamed from: q */
    private NotificationUtils mNotificationUtils;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public int batterylevel = 0;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public long INT_5 = 5;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public boolean isBeginStep = false;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public EcgParserUtils mEcgParserUtils;

    /* renamed from: v */
    private final IBinder mMainBinder = new MainBinder();

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof NetService.NetSerBinder) {
                MainService.this.mmainNetService = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
                if (MainService.this.mmainecggApp.appECGConf.getECG_REALTIME_UPLOAD() == 1 && !MainService.this.mmainNetService.isNetRun()) {
                    MainService.this.mmainNetService.startNetServiceTheard();
                }
            } else if (iBinder instanceof FileService.FileServiceBinder) {
                MainService.this.mmainFileService = ((FileService.FileServiceBinder) iBinder).getFileService();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainService.this.mmainFileService = null;
            MainService.this.mmainNetService = null;
        }
    };

    private BroadcastReceiver mainLocalbroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager a = null;
            Intent intent2 = null;
            Intent intent3 = null;
            LocalBroadcastManager a2 = null;
            int c;
            ArrayList<Sensor> arrayList;
            Sensor sensor;
            boolean z;
            BleHelper d = null;
            UUID data = null;
            UUID service = null;
            ArrayList<Sensor> arrayList2;
            Sensor sensor2 = null;
            try {
                String action = intent.getAction();
                if ("com.hopetruly.ec.services.ACTION_GATT_CONNECTED".equals(action)) {
                    boolean unused = MainService.this.isConn = true;
//                    MainService.this.startForeground(101, MainService.this.getMainNotification());
                    return;
                }
                int i = 0;
                if ("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED".equals(action)) {
                    boolean unused2 = MainService.this.isConn = false;
                    MainService.this.stopForeground(true);
                    if (MainService.this.isGattStop) {
                        MainService.this.isStopStepRecord();
                    }
                    MainService.this.mmainecggApp.appMachine = null;
                } else if (!"com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED".equals(action)) {
                    if (action.equals("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ")) {
                        String stringExtra = intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID");
                        if (stringExtra.equals(ECGUUIDS.SERV_UUID.toString())) {
                            byte[] byteArrayExtra = intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA");
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i2 = 0; i2 < byteArrayExtra.length; i2++) {
                                stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(byteArrayExtra[i2])}));
                            }
                            MainService.this.mmainecggApp.appMachine.setMac(stringBuffer.toString());
                            if (MainService.this.mmainNetService.getNetInfoType() != -1 && MainService.this.mmainecggApp.spSw_conf.getInt("DEVICE_ID_UPLOAD", 0) == 1) {
                                MainService.this.mmainNetService.mo2825a(MainService.this.mmainecggApp.appMachine.getId(), MainService.this.mmainecggApp.mUserInfo.getName());
                            }
                            d = MainService.this.mainbleHelper;
                            data = ECGUUIDS.f2772e;
                            service = ECGUUIDS.CHAR_DEVICE_INFO_UUID;
                        } else if (stringExtra.equals(ECGUUIDS.f2772e.toString())) {
                            MainService.this.mmainecggApp.appMachine.setFwRev(new String(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA")));
                            d = MainService.this.mainbleHelper;
                            data = ECGUUIDS.f2774g;
                            service = ECGUUIDS.CHAR_DEVICE_INFO_UUID;
                        } else if (stringExtra.equals(ECGUUIDS.f2774g.toString())) {
                            MainService.this.mmainecggApp.appMachine.setManufacturerName(new String(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA")));
                            List<BluetoothGattService> e = MainService.this.mainbleHelper.getBluetoothGattServices();
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
                                d = MainService.this.mainbleHelper;
                                data = Sensor.BATTERY.getData();
                                service = Sensor.BATTERY.getService();
                            } else {
                                a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                intent2 = new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END");
                            }
                        } else if (stringExtra.equals(Sensor.BATTERY.getData().toString())) {
                            int unused3 = MainService.this.batterylevel = Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                            MainService.this.mmainecggApp.appMachine.setBatteryLevel(MainService.this.batterylevel);
                            if (MainService.this.batterylevel < 20) {
                                LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.POWER_LOW"));
                            }
                            if (MainService.this.batterylevel < 10) {
                                new NotificationUtils(MainService.this.getApplicationContext()).toBatteryNotify("ECG Air low power", "Power " + MainService.this.batterylevel + " %");
                            }
                            a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            intent2 = new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END");
                        } else {
                            return;
                        }
                        d.checkUUID(data, service);
                        return;
                    } else if ("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE".equals(action)) {
                        if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(ECGUUIDS.f2780m.toString())) {
                            LogUtils.logI(MainService.this.TAG, "begin  notify sensors !");
                            a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            intent2 = new Intent("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE");
                        } else {
                            return;
                        }
                    } else if (!action.equals("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORREAD")) {
                        if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE")) {
                            LogUtils.logE(MainService.this.TAG, "sensors.size():" + MainService.this.mSensors.size());
                            if (MainService.this.mSensors.size() > 0) {
                                if (MainService.this.mSensors.contains(Sensor.ACCELEROMETER)) {
                                    MainService.this.mainbleHelper.writeDescriptorVar(Sensor.ACCELEROMETER.getService(), Sensor.ACCELEROMETER.getData(), true);
                                    arrayList = MainService.this.mSensors;
                                    sensor = Sensor.ACCELEROMETER;
                                } else if (MainService.this.mSensors.contains(Sensor.ECG)) {
                                    MainService.this.mainbleHelper.writeDescriptorVar(Sensor.ECG.getService(), Sensor.ECG.getData(), true);
                                    arrayList = MainService.this.mSensors;
                                    sensor = Sensor.ECG;
                                } else if (MainService.this.mSensors.contains(Sensor.SIMPLE_KEYS)) {
                                    MainService.this.mainbleHelper.writeDescriptorVar(Sensor.SIMPLE_KEYS.getService(), Sensor.SIMPLE_KEYS.getData(), true);
                                    arrayList = MainService.this.mSensors;
                                    sensor = Sensor.SIMPLE_KEYS;
                                } else if (MainService.this.mSensors.contains(Sensor.BATTERY)) {
                                    MainService.this.mainbleHelper.writeDescriptorVar(Sensor.BATTERY.getService(), Sensor.BATTERY.getData(), true);
                                    arrayList = MainService.this.mSensors;
                                    sensor = Sensor.BATTERY;
                                } else {
//                                    C0771g.logE(MainService.this.TAG, "sensor notify error.sensorlist have unknow sensor!");
                                    MainService.this.mmainecggApp.appMachine.setMac("7CEC793F53F5");
                                    MainService.this.mmainecggApp.appMachine.setFwRev("MUIRHEAD.A3.2.2\u0000");
                                    MainService.this.mmainecggApp.appMachine.setManufacturerName("Warick Medical");
                                    LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END"));
                                    return;
                                }
                                arrayList.remove(sensor);
                                return;
                            }
                            MainService.this.readdeviceInfo();
                        } else if ("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY".equals(action)) {
                            String stringExtra2 = intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID");
                            if (stringExtra2.equals(Sensor.ACCELEROMETER.getData().toString())) {
                                byte[] byteArrayExtra2 = intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA");
                                float f = (((float) (((double) byteArrayExtra2[0]) / 16.0d)) * 1000.0f) / 5.0f;
                                float f2 = (((float) (((double) byteArrayExtra2[1]) / 16.0d)) * 1000.0f) / 5.0f;
                                float f3 = (((float) (((double) (-1 * byteArrayExtra2[2])) / 16.0d)) * 1000.0f) / 5.0f;
                                double sqrt = Math.sqrt((double) ((f * f) + (f2 * f2) + (f3 * f3)));
                                if (MainService.this.isGattStop) {
                                    MainService.this.mainmStepCounter.mo2461a((float) sqrt);
                                }
                                if (MainService.this.assertFallDown) {
                                    MainService.this.mFallDownAlgorithm.mo2448a((int) f, (int) f2, (int) f3, (int) sqrt);
                                    return;
                                }
                                return;
                            } else if (stringExtra2.equals(Sensor.ECG.getData().toString()) && MainService.this.isStartEcg) {
                                ConvertECG convertECG = Sensor.ECG.convertECG(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                                MainService.this.mmainFileService.savemRealEcgData(convertECG.ecgArr);
                                if (MainService.this.mmainNetService.isNetRun()) {
                                    MainService.this.mmainNetService.mo2823a(convertECG.ecgArr, 75);
                                    return;
                                }
                                return;
                            } else if (stringExtra2.equals(Sensor.SIMPLE_KEYS.getData().toString())) {
                                SimpleKeysStatus convertKeys = Sensor.SIMPLE_KEYS.convertKeys(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                                LogUtils.logI(MainService.this.TAG, "simple key status : " + convertKeys.toString());
                                if (convertKeys.equals(SimpleKeysStatus.ALARM_BUTTON)) {
                                    NotificationUtils jVar = new NotificationUtils(MainService.this.getApplicationContext());
                                    if (MainService.this.mmainecggApp.appECGConf.getECG_SMS_ALARM() == 1) {
                                        GPSSosHelper.sendMultipartTextMessageSmsGPS(MainService.this.mmainecggApp);
                                        jVar.toBatteryNotify("SOS", "Send SMS");
                                    }
                                    if (MainService.this.mmainecggApp.appECGConf.getECG_ENABLE_MARK() == 1 && MainService.this.isStartEcg && (c = MainService.this.mmainFileService.mo2705c()) > 0 && MainService.this.mEcgParserUtils.mo2779a(c, MainService.this.mmainecggApp.appECGConf.getECG_MARKING_PERIOD())) {
                                        Log.e(MainService.this.TAG, "Mark time..");
                                        a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                        intent2 = new Intent("com.hopetruly.ecg.services.MainService.MARK_TIME_START");
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else if (stringExtra2.equals(Sensor.BATTERY.getData().toString())) {
                                int unused4 = MainService.this.batterylevel = Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                                MainService.this.mmainecggApp.appMachine.setBatteryLevel(MainService.this.batterylevel);
                                LogUtils.logI(MainService.this.TAG, "battery level:" + MainService.this.batterylevel);
                                if (MainService.this.batterylevel < 20) {
                                    LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.POWER_LOW"));
                                }
                                if (MainService.this.batterylevel < 10) {
                                    new NotificationUtils(MainService.this.getApplicationContext()).toBatteryNotify("ECG Air low power", "Power " + MainService.this.batterylevel + " %");
                                    return;
                                }
                                return;
                            } else {
                                return;
                            }
                        } else {
                            if ("com.holptruly.ecg.services.FileService.FILE_SAVE_SUCCESS".equals(action)) {
                                String stringExtra3 = intent.getStringExtra("com.holptruly.ecg.services.FileService.EXTRA_FILE");
                                MainService.this.mSaveECGRecord.setFilePath(stringExtra3);
                                MainService.this.mSaveECGRecord.setFileName(new File(stringExtra3).getName());
                                MainService.this.mSaveECGRecord.setMark_time(MainService.this.mSaveECGRecord.getEcgEntity().getMark_time());
                                MainService.this.mSqlManager.insertEcgRecord(MainService.this.mSaveECGRecord);
                                MainService.this.mSqlManager.closeDatabase();
                                if (MainService.this.mmainecggApp.appECGConf.getECG_AUTO_UPLOAD() == 1) {
                                    MainService.this.mmainNetService.uploadingRecord(MainService.this.mSaveECGRecord);
                                }
                                intent3 = new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS");
                                a2 = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            } else if (action.equals("com.holptruly.ecg.services.FileService.FILE_SAVE_FAIL")) {
                                intent3 = new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL");
                                a2 = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                            } else if (action.equals("com.hopetruly.part.StepCounter.STEP")) {
                                if (MainService.this.mainPedometerRecord != null) {
                                    long longExtra = intent.getLongExtra("step_value", MainService.this.mainPedometerRecord.getCurStep());
                                    if (longExtra > MainService.this.INT_5) {
                                        MainService.this.mainPedometerRecord.setCurStep(longExtra - MainService.this.INT_5);
                                        boolean unused5 = MainService.this.isBeginStep = true;
                                    }
                                }
                                a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                intent2 = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_STEP");
                            } else if (action.equals("com.hopetruly.part.StepCounter.CAL")) {
                                if (MainService.this.mainPedometerRecord != null) {
                                    double doubleExtra = intent.getDoubleExtra("cal_value", 0.0d);
                                    if (MainService.this.isBeginStep) {
                                        MainService.this.mainPedometerRecord.setCal(((long) doubleExtra) + MainService.this.mainPedometerRecord.getCal());
                                    }
                                }
                                a = LocalBroadcastManager.getInstance(MainService.this.getApplicationContext());
                                intent2 = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_CAL");
                            } else if (action.equals("com.hopetruly.ecg.util.MyAlarmClock.DATE_CHANGE")) {
                                if (MainService.this.mainPedometerRecord != null) {
                                    if (MainService.this.mainPedometerRecord.getTarget() == 0 && MainService.this.mainPedometerRecord.getCurStep() == 0 && MainService.this.mainPedometerRecord.getCount() == 0) {
                                        MainService.this.mSqlManager.deleteStepRecord(MainService.this.mainPedometerRecord);
                                    } else {
                                        MainService.this.mSqlManager.writeStepRec(MainService.this.mainPedometerRecord);
                                    }
                                }
                                if (MainService.this.isGattStop) {
                                    MainService.this.stopStep();
                                    MainService.this.insertStepRecord();
                                    return;
                                }
                                return;
                            } else if (!action.equals("com.holptruly.part.FallDetection.FALLDOWN") && "com.holptruly.ecg.services.NetService.UPLOAD_ID_SUCCESS".equals(action)) {
                                SharedPreferences.Editor edit = MainService.this.mmainecggApp.spSw_conf.edit();
                                MainService.this.mmainecggApp.mSwConf.setDevice_id_upload(0);
                                edit.putInt("DEVICE_ID_UPLOAD", MainService.this.mmainecggApp.mSwConf.getDevice_id_upload());
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
                } else if (MainService.this.isConn) {
                    MainService.this.mSensors.clear();
                    List<BluetoothGattService> e2 = MainService.this.mainbleHelper.getBluetoothGattServices();
                    if (e2 != null) {
                        while (i < e2.size()) {
                            UUID uuid = e2.get(i).getUuid();
                            if (uuid.compareTo(Sensor.ACCELEROMETER.getService()) == 0) {
                                arrayList2 = MainService.this.mSensors;
                                sensor2 = Sensor.ACCELEROMETER;
                            } else if (uuid.compareTo(Sensor.ECG.getService()) == 0) {
                                arrayList2 = MainService.this.mSensors;
                                sensor2 = Sensor.ECG;
                            } else if (uuid.compareTo(Sensor.SIMPLE_KEYS.getService()) == 0) {
                                arrayList2 = MainService.this.mSensors;
                                sensor2 = Sensor.SIMPLE_KEYS;
                            } else if (uuid.compareTo(Sensor.BATTERY.getService()) == 0) {
                                arrayList2 = MainService.this.mSensors;
                                sensor2 = Sensor.BATTERY;
                            } else {
                                i++;
                            }
                            MainService.this.mSensors.add(sensor2);
                            i++;
                        }
                    }
                    MainService.this.setStepSpeed(10);
                }
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            }
        }
    };

    public ECGRecord mSaveECGRecord = null;
    /* access modifiers changed from: private */

    /* renamed from: z */
    public boolean isStartEcg = false;

    /* renamed from: com.hopetruly.ecg.services.MainService$a */
    public class MainBinder extends Binder {
        public MainBinder() {
        }

        /* renamed from: a */
        public MainService getMainBinder() {
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

    public Notification getMainNotification() {
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

    public void stopStep() {
        this.mainmStepCounter.clearStep_value();
        this.mainmStepCounter.clearStep();
        this.isBeginStep = false;
    }

    /* access modifiers changed from: private */
    /* renamed from: y */
    public void insertStepRecord() {
        Calendar instance = Calendar.getInstance();
        getSqlPedometerRecord();
        if (this.mainPedometerRecord == null) {
            this.mainPedometerRecord = new PedometerRecord();
            this.mainPedometerRecord.setUserId(this.mmainecggApp.mUserInfo.getId());
            this.mainPedometerRecord.setTarget(this.mmainecggApp.appPedometerConf.getSTEP_TARGET());
            this.mainPedometerRecord.setYear(instance.get(1));
            this.mainPedometerRecord.setMonth(instance.get(2) + 1);
            this.mainPedometerRecord.setDay(instance.get(5));
            this.mSqlManager.insertStepRecord(this.mainPedometerRecord);
        }
    }

    /* renamed from: a */
    public void exitService() {
        LogUtils.logI(this.TAG, "ExitService~~");
        stopECG();
        closeACCELEROMETER();
        this.myAlarmClock.cancelAlarmManager();
        MyHttpClient.initMyHttpClient().closeHttpClient();
        stopSelf();
    }

    /* renamed from: a */
    public void startScanBLE(int i) {
        if (this.mainbleHelper != null) {
            this.mainbleHelper.startScan(i);
        } else {
            LogUtils.logW(this.TAG, "StartScanBLE>BLEservice = null");
        }
    }

    /* renamed from: a */
    public void startMyECG(ECGRecord eCGRecord) {
        LogUtils.logI(this.TAG, "StartECG~~~~~");
        if (this.isConn) {
            this.isStartEcg = true;
            this.saveTimeSec = 0;
            startTimer();
            this.mSaveECGRecord = eCGRecord;
            this.mSaveECGRecord.setUser(this.mmainecggApp.mUserInfo);
            this.mSaveECGRecord.setMachine(this.mmainecggApp.appMachine);
            Date date = new Date(System.currentTimeMillis());
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
            this.mSaveECGRecord.setTime(format);
            ECGEntity eCGEntity = new ECGEntity();
            eCGEntity.setStartTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(date));
            eCGEntity.setLead(ECGEntity.LEAD_I);
            eCGEntity.setLeadExten(eCGRecord.getLeadType() == 1 ? ECGEntity.LEAD_PART_CHEST : ECGEntity.LEAD_PART_HAND);
            this.mSaveECGRecord.setEcgEntity(eCGEntity);
            this.mmainFileService.mo2699a();
            if (this.mmainecggApp.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
                this.mmainNetService.startNetServiceTheard();
            }
            Intent intent = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME");
            intent.putExtra("com.hopetruly.ecg.services.MainService.REFRESH_DATETIME", format);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            writeCharacteristicBoolean(Sensor.ECG, true);
            return;
        }
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.ble_not_connect), Toast.LENGTH_SHORT).show();
        LogUtils.logW(this.TAG, "StartECG>蓝牙未连接错误！");
    }

    /* renamed from: a */
    public void saveEcgFile(ECGRecord eCGRecord, float[] fArr) {
        setmSaveECGRecord(eCGRecord);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_START"));
        this.mmainFileService.startSaveEcgFileAsyncTask(eCGRecord, fArr);
    }

    /* renamed from: a */
    public void connectBLE(String str) {
        if (this.mainbleHelper != null) {
            this.mMacAdrr = str;
            this.mainbleHelper.stopScan();
            this.mainbleHelper.connectbLE(str);
            return;
        }
        LogUtils.logW(this.TAG, "ConnectBLE>BLEservice = null");
    }

    /* renamed from: a */
    public boolean writeCharacteristicBoolean(Sensor sensor, boolean z) {
        BluetoothGattService a;
        BluetoothGattCharacteristic characteristic;
        if (this.mainbleHelper == null || (a = this.mainbleHelper.getServicebyUUID(sensor.getService())) == null || (characteristic = a.getCharacteristic(sensor.getConfig())) == null) {
            return false;
        }
        characteristic.setValue(new byte[]{z ? (byte) 1 : 0});
        return this.mainbleHelper.writeCharacteristicBle(characteristic);
    }

    /* renamed from: a */
    public boolean writeCharacteristic(UUID uuid, UUID uuid2, int i) {
        BluetoothGattService a;
        BluetoothGattCharacteristic characteristic;
        if (this.mainbleHelper == null || (a = this.mainbleHelper.getServicebyUUID(uuid)) == null || (characteristic = a.getCharacteristic(uuid2)) == null) {
            return false;
        }
        characteristic.setValue(new byte[]{(byte) i});
        return this.mainbleHelper.writeCharacteristicBle(characteristic);
    }

    /* renamed from: b */
    public void setStepSpeed(int i) {
        if (this.mainbleHelper == null || !this.isConn) {
            LogUtils.logW(this.TAG, "SetStepSpeed>mBLEHelper = null");
            return;
        }
        String str = this.TAG;
        LogUtils.logE(str, "set Step peroid:" + i);
        writeCharacteristic(ECGUUIDS.SER_ACCELEROMETER_UUID, ECGUUIDS.f2780m, i);
    }

    /* renamed from: b */
    public void setmSaveECGRecord(ECGRecord eCGRecord) {
        this.mSaveECGRecord = eCGRecord;
    }

    /* renamed from: b */
    public boolean isMBleConn() {
        return this.isConn;
    }

    /* renamed from: c */
    public void stopScanBLE() {
        if (this.mainbleHelper != null) {
            this.mainbleHelper.stopScan();
        } else {
            LogUtils.logW(this.TAG, "StopScanBLE>BLEservice = null");
        }
    }

    /* renamed from: d */
    public void disconnectMainBLE() {
        if (this.mainbleHelper != null) {
            LogUtils.logW(this.TAG, "DisconnectBLE");
            this.mainbleHelper.disconnectBle();
            return;
        }
        LogUtils.logW(this.TAG, "DisconnectBLE>BLEservice = null");
    }

    /* renamed from: e */
    public void readdeviceInfo() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_BEGIN"));
        if (this.mainbleHelper != null) {
            String str = this.TAG;
            LogUtils.logI(str, "read deciev info ->" + this.mainbleHelper.checkUUID(ECGUUIDS.SERV_UUID, ECGUUIDS.CHAR_DEVICE_INFO_UUID));
            return;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END"));
        LogUtils.logW(this.TAG, "StopScanBLE>BLEservice = null");
    }

    /* renamed from: f */
    public void GetDeviceBattery() {
        if (this.mainbleHelper != null) {
            List<BluetoothGattService> e = this.mainbleHelper.getBluetoothGattServices();
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
                this.mainbleHelper.checkUUID(Sensor.BATTERY.getData(), Sensor.BATTERY.getService());
                return;
            }
            return;
        }
        LogUtils.logW(this.TAG, "StopGetDeviceBattery>BLEservice is null");
    }

    /* renamed from: g */
    public BleHelper getMainbleHelper() {
        return this.mainbleHelper;
    }

    /* renamed from: h */
    public void stopECG() {
        LogUtils.logI(this.TAG, "StopECG~~~~~");
        if (this.isStartEcg) {
            this.isStartEcg = false;
            if (this.isConn) {
                writeCharacteristicBoolean(Sensor.ECG, false);
            } else {
                LogUtils.logW(this.TAG, "StartECG>蓝牙未连接错误！");
            }
            stopTimer();
            this.mEcgParserUtils.mo2777a(this.mmainFileService.mo2705c());
            this.mmainFileService.mo2704b();
            this.mmainNetService.mo2830d();
            String str = this.TAG;
            LogUtils.logI(str, "SaveECG>timeSec:" + this.saveTimeSec + "");
            this.saveTimeSec = this.saveTimeSec % 3600;
            int i = (int) (this.saveTimeSec % 60);
            ECGRecord eCGRecord = this.mSaveECGRecord;
            eCGRecord.setPeriod(String.format("%02d", new Object[]{Integer.valueOf((int) (this.saveTimeSec / 3600))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((int) (this.saveTimeSec / 60))}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(i)}));
            this.mSaveECGRecord.getEcgEntity().setEndTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date(System.currentTimeMillis())));
            this.mSaveECGRecord.getEcgEntity().setMark_time(this.mEcgParserUtils.mo2785c());
            int[] iArr = null;
            if (this.mSaveECGRecord.getEcgEntity().getMark_time() != null) {
                iArr = this.mEcgParserUtils.mo2780a();
            }
            this.mSaveECGRecord.getEcgEntity().setMark_period(iArr);
            this.mEcgParserUtils.mo2782b();
        }
    }

    /* renamed from: i */
    public void fileSaveStart() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.hopetruly.ecg.services.MainService.FILE_SAVE_START"));
        this.mmainFileService.startSaveEcgFileAsyncTask(this.mSaveECGRecord);
    }

    /* renamed from: j */
    public boolean getIsStartEcg() {
        return this.isStartEcg;
    }

    /* renamed from: k */
    public boolean closeACCELEROMETER() {
        if (!this.isConn) {
            return false;
        }
        return writeCharacteristicBoolean(Sensor.ACCELEROMETER, false);
    }

    /* renamed from: l */
    public boolean mo2738l() {
        this.mmainecggApp.appPedometerConf.setSTEP_ENABLE_STEP(1);
        if (!initACCELEROMETER()) {
            return false;
        }
        insertStepRecord();
        this.mainPedometerRecord.setCurStep(0);
        this.isGattStop = true;
        return true;
    }

    /* renamed from: m */
    public boolean mo2739m() {
        this.mmainecggApp.appPedometerConf.setSTEP_ENABLE_STEP(0);
        if (!closeACCELEROMETER()) {
            return false;
        }
        this.isGattStop = false;
        if (this.mainPedometerRecord == null) {
            return true;
        }
        this.mainPedometerRecord.setCount(this.mainPedometerRecord.getCount() + this.mainPedometerRecord.getCurStep());
        if (this.mainPedometerRecord.getTarget() == 0 && this.mainPedometerRecord.getCurStep() == 0 && this.mainPedometerRecord.getCount() == 0) {
            this.mSqlManager.deleteStepRecord(this.mainPedometerRecord);
        } else {
            this.mSqlManager.writeStepRec(this.mainPedometerRecord);
        }
        stopStep();
        return true;
    }

    /* renamed from: n */
    public boolean isStopStepRecord() {
        this.mmainecggApp.appPedometerConf.setSTEP_ENABLE_STEP(0);
        if (this.isConn) {
            return false;
        }
        this.isGattStop = false;
        if (this.mainPedometerRecord == null) {
            return true;
        }
        this.mainPedometerRecord.setCount(this.mainPedometerRecord.getCount() + this.mainPedometerRecord.getCurStep());
        if (this.mainPedometerRecord.getTarget() == 0 && this.mainPedometerRecord.getCurStep() == 0 && this.mainPedometerRecord.getCount() == 0) {
            this.mSqlManager.deleteStepRecord(this.mainPedometerRecord);
        } else {
            this.mSqlManager.writeStepRec(this.mainPedometerRecord);
        }
        stopStep();
        return true;
    }

    /* renamed from: o */
    public boolean getisGattStop() {
        return this.isGattStop;
    }

    public IBinder onBind(Intent intent) {
        return this.mMainBinder;
    }

    public void onCreate() {
        super.onCreate();
        LogUtils.logI(this.TAG, "onCreate ....");
        this.mmainecggApp = (ECGApplication) getApplication();
        this.mmainecggApp.appEcgUncaughtExceptionHandler.mo2770a((Service) this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mainLocalbroadcastReceiver, m2708v());
        this.mSqlManager = new SqlManager(getApplicationContext());
        Intent intent = new Intent();
        intent.setClass(this, FileService.class);
        bindService(intent, this.mServiceConnection, Context.BIND_AUTO_CREATE);
        intent.setClass(this, NetService.class);
        bindService(intent, this.mServiceConnection, Context.BIND_AUTO_CREATE);
        this.mainbleHelper = new BleHelper(this);
        this.mNotificationUtils = new NotificationUtils(getApplicationContext());
        GpsManagerHelper.isEnableGpsManager();
        if (GpsManagerHelper.mGpsManagerHelper() != null) {
            GpsManagerHelper.mGpsManagerHelper().setOnGpsListener(this.monGpsListener);
        }
        this.mEcgParserUtils = new EcgParserUtils();
        this.mainmStepCounter = new StepCounter(this, 0, 0);
        this.mFallDownAlgorithm = new FallDownAlgorithm(this);
        this.myAlarmClock = MyAlarmClock.getInstance((Context) this);
        this.myAlarmClock.setTimeDate();
        this.myAlarmClock.setIsClock(true);
    }

    public void onDestroy() {
        LogUtils.logI(this.TAG, "onDestroy ....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mainLocalbroadcastReceiver);
        this.mmainecggApp.appMainService = null;
        if (!(this.mainbleHelper == null && this.mmainFileService == null && this.mmainNetService == null)) {
            unbindService(this.mServiceConnection);
        }
        GpsManagerHelper.removeGps();
        if (this.isConn) {
            disconnectMainBLE();
        }
        super.onDestroy();
    }

    /* renamed from: p */
    public PedometerRecord getmainPedometerRecord() {
        return this.mainPedometerRecord;
    }

    /* renamed from: q */
    public PedometerRecord getSqlPedometerRecord() {
        Calendar instance = Calendar.getInstance();
        this.mainPedometerRecord = this.mSqlManager.getPedometerRecord(this.mmainecggApp.mUserInfo.getId(), instance.get(1), instance.get(2) + 1, instance.get(5));
        return this.mainPedometerRecord;
    }

    /* renamed from: r */
    public boolean initACCELEROMETER() {
        if (!this.isConn) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ble_not_connect), Toast.LENGTH_SHORT).show();
            LogUtils.logW(this.TAG, "ACC 加速度 >蓝牙未连接错误！");
            return false;
        }
        if ((this.mmainecggApp.appPedometerConf.mo2669a() == 1) || (this.mmainecggApp.appFallConf.mo2667a() == 1)) {
            return writeCharacteristicBoolean(Sensor.ACCELEROMETER, true);
        }
        return false;
    }

    /* renamed from: s */
    public void startTimer() {
        LogUtils.logI(this.TAG, "Timer>start!~~~");
        if (!this.isStartTimer) {
            if (this.refreshTIMER == null) {
                this.refreshTIMER = new Timer();
            }
            this.refreshTIMER.schedule(new TimerTask() {
                public void run() {
                    MainService.this.saveTimeSec++;
                    Intent intent = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_TIMER");
                    intent.putExtra("com.hopetruly.ecg.services.MainService.REFRESH_TIMER", MainService.this.saveTimeSec);
                    LocalBroadcastManager.getInstance(MainService.this.getApplicationContext()).sendBroadcast(intent);
                }
            }, 1000, 1000);
            this.isStartTimer = true;
        }
    }

    /* renamed from: t */
    public void stopTimer() {
        LogUtils.logI(this.TAG, "Timer>stop!~~~");
        if (this.isStartTimer) {
            if (this.refreshTIMER != null) {
                this.refreshTIMER.purge();
                this.refreshTIMER.cancel();
                this.refreshTIMER = null;
            }
            this.isStartTimer = false;
            Intent intent = new Intent("com.hopetruly.ecg.services.MainService.REFRESH_TIMER");
            intent.putExtra("com.hopetruly.ecg.services.MainService.REFRESH_TIMER", 0);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }

    /* renamed from: u */
    public int getbatterylevel() {
        return this.batterylevel;
    }

}
