package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.R;
import com.hopetruly.ecg.device.ECGUUIDS;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.DataParser;
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.part.net.NetService;
import com.hopetruly.part.p024a.BleHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FwUpdateActivity extends BaseActivity {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static String TAG = "FwUpdateActivity";



    /* renamed from: A */
    public FwInfo notifyFwInfo = new FwInfo();

    /* renamed from: B */
    private Timer mFwTimer = null;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public C0622b f2250C = new C0622b();

    /* renamed from: D */
    private TimerTask mFwTimerTask = null;
    /* access modifiers changed from: private */

    /* renamed from: E */
    public boolean isDeviceSupportFw = false;
    /* access modifiers changed from: private */

    /* renamed from: F */
    public boolean isStartFw = false;
    /* access modifiers changed from: private */

    /* renamed from: G */
    public boolean isAuto = false;

    /* renamed from: H */
    private int fwSecond = 0;

    /* renamed from: I */
    private IntentFilter mIntentFilter;

    /* renamed from: J */
    private ServiceConnection netBinderSerConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NetService unused = FwUpdateActivity.this.NetiBinder = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetService unused = FwUpdateActivity.this.NetiBinder = null;
        }
    };

    /* renamed from: K */
    private ServiceConnection MainBinderSerConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainService unused = FwUpdateActivity.this.mMainService = ((MainService.MainBinder) iBinder).getMainBinder();
            if (!FwUpdateActivity.this.mMainService.isMBleConn()) {
                FwUpdateActivity.this.showDisconnect_and_checkDialog();
                return;
            }
            BleHelper unused2 = FwUpdateActivity.this.mBleHelper = FwUpdateActivity.this.mMainService.getMainbleHelper();
            BluetoothGattService unused3 = FwUpdateActivity.this.mBluetoothGattService = FwUpdateActivity.this.mBleHelper.getServicebyUUID(ECGUUIDS.f2787t);
            BluetoothGattService unused4 = FwUpdateActivity.this.mBluetoothGattService1 = FwUpdateActivity.this.mBleHelper.getServicebyUUID(ECGUUIDS.f2790w);
            if ((FwUpdateActivity.this.mBluetoothGattService == null) || (FwUpdateActivity.this.mBluetoothGattService1 == null)) {
                FwUpdateActivity.this.showNOT_support_firmware_updataDialog();
                boolean unused5 = FwUpdateActivity.this.isDeviceSupportFw = false;
                return;
            }
            List unused6 = FwUpdateActivity.this.f2272p = FwUpdateActivity.this.mBluetoothGattService.getCharacteristics();
            List unused7 = FwUpdateActivity.this.f2273q = FwUpdateActivity.this.mBluetoothGattService1.getCharacteristics();
            boolean unused8 = FwUpdateActivity.this.isDeviceSupportFw = FwUpdateActivity.this.f2272p.size() == 2 && FwUpdateActivity.this.f2273q.size() >= 3;
            if (FwUpdateActivity.this.isDeviceSupportFw) {
                BluetoothGattCharacteristic unused9 = FwUpdateActivity.this.f2274r = (BluetoothGattCharacteristic) FwUpdateActivity.this.f2272p.get(0);
                BluetoothGattCharacteristic unused10 = FwUpdateActivity.this.f2275s = (BluetoothGattCharacteristic) FwUpdateActivity.this.f2272p.get(1);
                BluetoothGattCharacteristic unused11 = FwUpdateActivity.this.f2276t = (BluetoothGattCharacteristic) FwUpdateActivity.this.f2273q.get(1);
            }
            FwUpdateActivity.this.btn_load_a.setEnabled(FwUpdateActivity.this.isDeviceSupportFw);
            FwUpdateActivity.this.btn_load_b.setEnabled(FwUpdateActivity.this.isDeviceSupportFw);
            FwUpdateActivity.this.btn_load_c.setEnabled(FwUpdateActivity.this.isDeviceSupportFw);
            if (FwUpdateActivity.this.isDeviceSupportFw) {
                FwUpdateActivity.this.m2336j();
                FwUpdateActivity.this.m2338k();
                return;
            }
            FwUpdateActivity.this.showNOT_support_firmware_updataDialog();
            Toast.makeText(FwUpdateActivity.this.getApplicationContext(), FwUpdateActivity.this.getString(R.string.p_oad_init_fail), Toast.LENGTH_LONG).show();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainService unused = FwUpdateActivity.this.mMainService = null;
        }
    };

    /* renamed from: L */
    private final BroadcastReceiver fwBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String d = FwUpdateActivity.TAG;
            Log.d(d, "action: " + action);
            if ("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY".equals(action)) {
                byte[] byteArrayExtra = intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA");
                if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(FwUpdateActivity.this.f2274r.getUuid().toString())) {
                    FwUpdateActivity.this.notifyFwInfo.fwVersion = DataParser.BytesToShort(byteArrayExtra[1], byteArrayExtra[0]);
                    FwUpdateActivity.this.notifyFwInfo.fwType = Character.valueOf((FwUpdateActivity.this.notifyFwInfo.fwVersion & 1) == 1 ? 'B' : 'A');
                    FwUpdateActivity.this.notifyFwInfo.fwSize = DataParser.parser(byteArrayExtra[3], byteArrayExtra[2]);
                    FwUpdateActivity.this.showFwInfo(FwUpdateActivity.this.tv_tw_target, FwUpdateActivity.this.notifyFwInfo);
                    try {
                        FwUpdateActivity.this.startFwUpdate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (FwUpdateActivity.this.isAuto) {
                        SharedPreferences.Editor edit = FwUpdateActivity.this.ecgApplication.spSw_conf.edit();
                        FwUpdateActivity.this.ecgApplication.mSwConf.mo2683a(FwUpdateActivity.this.ecgApplication.appMachine.getId());
                        edit.putString("DEVICE_ID", FwUpdateActivity.this.ecgApplication.mSwConf.mo2690e());
                        edit.commit();
                        edit.putString("MacAddress", FwUpdateActivity.this.ecgApplication.appMachine.getMacAddress());
                        edit.commit();
                    }
                }
            } else if ("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE".equals(action)) {
                int intExtra = intent.getIntExtra("com.hopetruly.ec.services.EXTRA_STATUS", 0);
                if (intExtra != 0) {
                    String d2 = FwUpdateActivity.TAG;
                    Log.e(d2, "Write failed: " + intExtra);
                    Toast.makeText(context, "GATT error: status=" + intExtra, Toast.LENGTH_SHORT).show();
                }
            } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                boolean unused = FwUpdateActivity.this.isAuto = false;
                FwUpdateActivity.this.showDisconnect_and_checkDialog();
            }
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: d */
    public TextView tv_tw_target;

    /* renamed from: e */
    private TextView tv_tw_file;

    /* renamed from: f */
    private TextView tv_tw_info;

    /* renamed from: g */
    private TextView tv_tw_log;

    /* renamed from: h */
    private ProgressBar pb_progress;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public Button btn_load_a;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public Button btn_load_b;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public Button btn_load_c;

    /* renamed from: l */
    private Button btn_start;

    /* renamed from: m */
    private LinearLayout ll_file_LinearLayout;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public BluetoothGattService mBluetoothGattService;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public BluetoothGattService mBluetoothGattService1;
    /* access modifiers changed from: private */

    /* renamed from: p */
    public List<BluetoothGattCharacteristic> f2272p;
    /* access modifiers changed from: private */

    /* renamed from: q */
    public List<BluetoothGattCharacteristic> f2273q;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public BluetoothGattCharacteristic f2274r = null;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public BluetoothGattCharacteristic f2275s = null;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public BluetoothGattCharacteristic f2276t = null;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public BleHelper mBleHelper;
    /* access modifiers changed from: private */

    /* renamed from: v */
    public MainService mMainService;
    /* access modifiers changed from: private */

    /* renamed from: w */
    public NetService NetiBinder;

    /* renamed from: x */
    private final byte[] f2280x = new byte[262144];

    /* renamed from: y */
    private final byte[] f2281y = new byte[18];
    /* access modifiers changed from: private */

    /* renamed from: z */
    public FwInfo mFwInfo = new FwInfo();

    /* renamed from: com.hopetruly.ecg.activity.FwUpdateActivity$a */
    private class FwInfo {

        /* renamed from: a */
        short fwVersion;

        /* renamed from: b */
        int fwSize;

        /* renamed from: c */
        Character fwType;

        /* renamed from: d */
        byte[] mFwInfobuf;

        private FwInfo() {
            this.mFwInfobuf = new byte[4];
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.FwUpdateActivity$b */
    private class C0622b {

        /* renamed from: a */
        int f2295a;

        /* renamed from: b */
        short allSize;

        /* renamed from: c */
        short completeSize;

        /* renamed from: d */
        int f2298d;

        /* renamed from: e */
        int f2299e;

        private C0622b() {
            this.f2295a = 0;
            this.allSize = 0;
            this.completeSize = 0;
            this.f2298d = 0;
            this.f2299e = 0;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void mo2223a() {
            this.f2295a = 0;
            this.allSize = 0;
            this.f2298d = 0;
            this.f2299e = 0;
            this.completeSize = (short) (FwUpdateActivity.this.mFwInfo.fwSize / 4);
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.FwUpdateActivity$c */
    private class FwTimerTask extends TimerTask {
        private FwTimerTask() {
        }

        public void run() {
            FwUpdateActivity.this.f2250C.f2299e++;
            if (FwUpdateActivity.this.isStartFw) {
                FwUpdateActivity.this.m2340l();
                if (FwUpdateActivity.this.f2250C.f2299e % 20 == 0) {
                    FwUpdateActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            FwUpdateActivity.this.updateTimeProgress();
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void showFwInfo(TextView textView, FwInfo aVar) {
        String str = TAG;
        LogUtils.logE(str, "h.len>>" + aVar.fwSize);
        textView.setText(Html.fromHtml(String.format("%s %c %s %d %s %d", new Object[]{getString(R.string.l_type), aVar.fwType, getString(R.string.l_version), Integer.valueOf(aVar.fwVersion >> 1), getString(R.string.l_size), Integer.valueOf(aVar.fwSize * 4)})));
    }

    /* renamed from: a */
    private boolean writeMyCharacteristicByte(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte b) {
        boolean a = this.mBleHelper.writeCharacteristicBytes(new byte[]{b}, bluetoothGattCharacteristic);
        return a ? this.mBleHelper.sleep10(100) : a;
    }

    /* renamed from: a */
    private boolean enableNotification_value(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        boolean a = this.mBleHelper.eNABLE_NOTIFICATION_VALUE(bluetoothGattCharacteristic, z);
        return a ? this.mBleHelper.sleep10(100) : a;
    }

    /* renamed from: a */
    private boolean m2315a(String str, boolean z) throws IOException {
        InputStream inputStream;
        if (z) {
            try {
                inputStream = getAssets().open(str);
            } catch (IOException unused) {
                this.tv_tw_log.setText(getString(R.string.l_open_file_fail) + " " + str + "\n");
                return false;
            }
        } else {
            inputStream = new FileInputStream(new File(str));
        }
        inputStream.read(this.f2280x, 0, this.f2280x.length);
        inputStream.close();
        this.mFwInfo.fwVersion = DataParser.BytesToShort(this.f2280x[5], this.f2280x[4]);
        this.mFwInfo.fwSize = DataParser.parser(this.f2280x[7], this.f2280x[6]);
        boolean z2 = true;
        this.mFwInfo.fwType = Character.valueOf((this.mFwInfo.fwVersion & 1) == 1 ? 'B' : 'A');
        System.arraycopy(this.f2280x, 8, this.mFwInfo.mFwInfobuf, 0, 4);
        showFwInfo(this.tv_tw_file, this.mFwInfo);
        if (this.mFwInfo.fwType == this.notifyFwInfo.fwType) {
            z2 = false;
        }
        this.tv_tw_file.setTextColor(z2 ? -16777216 : Color.rgb(238, 92, 66));
        this.btn_start.setEnabled(z2);
        this.fwSecond = (((20 * this.mFwInfo.fwSize) * 4) / 16) / 1000;
        updateTimeProgress();
        this.tv_tw_log.setText(getString(R.string.l_img) + " " + this.mFwInfo.fwType + " " + getString(R.string.l_sel) + ".\n");
        this.tv_tw_log.append(getString(z2 ? R.string.l_fw_ready : R.string.l_fw_incorrect_img));
        m2332h();
        return false;
    }

    /* renamed from: e */
    private void addIntentFilter() {
        this.mIntentFilter = new IntentFilter();
        this.mIntentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        this.mIntentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE");
        this.mIntentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        this.mIntentFilter.addAction("com.holptruly.ecg.services.NetService.NET_CHANGE");
    }

    /* renamed from: f */
    private void startFw_update() {
        this.tv_tw_log.append(getString(R.string.l_fw_update_start));
        this.isStartFw = true;
        m2332h();
        byte[] bArr = new byte[12];
        bArr[0] = DataParser.shortToByte(this.mFwInfo.fwVersion);
        bArr[1] = DataParser.m2760b(this.mFwInfo.fwVersion);
        bArr[2] = DataParser.IntToByte(this.mFwInfo.fwSize);
        bArr[3] = DataParser.m2759b(this.mFwInfo.fwSize);
        System.arraycopy(this.mFwInfo.mFwInfobuf, 0, bArr, 4, 4);
        this.f2274r.setValue(bArr);
        this.mBleHelper.writeCharacteristicBle(this.f2274r);
        this.f2250C.mo2223a();
        this.mFwTimer = null;
        this.mFwTimer = new Timer();
        this.mFwTimerTask = new FwTimerTask();
        this.mFwTimer.scheduleAtFixedRate(this.mFwTimerTask, 0, 20);
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m2330g() {
        this.mFwTimer.cancel();
        this.mFwTimer.purge();
        if (this.mFwTimerTask != null) {
            this.mFwTimerTask.cancel();
        }
        this.mFwTimerTask = null;
        this.isStartFw = false;
        this.tv_tw_info.setText("");
        this.pb_progress.setProgress(0);
        m2332h();
        if (this.f2250C.allSize == this.f2250C.completeSize) {
            this.tv_tw_log.setText(getString(R.string.l_fw_update_ok));
            if (this.isAuto && getMyNetInfoType().booleanValue()) {
                SharedPreferences.Editor edit = this.ecgApplication.spSw_conf.edit();
                this.ecgApplication.mSwConf.setDevice_id_upload(1);
                edit.putInt("DEVICE_ID_UPLOAD", this.ecgApplication.mSwConf.getDevice_id_upload());
                edit.commit();
            }
            this.isAuto = false;
            restartBle();
            return;
        }
        this.tv_tw_log.append(getString(R.string.l_fw_update_cancel));
    }

    /* renamed from: h */
    private void m2332h() {
        if (this.isStartFw) {
            this.btn_start.setText(getResources().getString(R.string.cancle));
            this.btn_load_a.setEnabled(false);
        } else {
            this.pb_progress.setProgress(0);
            this.btn_start.setText(getResources().getString(R.string.start_programming));
            if (this.mFwInfo.fwType.charValue() == 'A') {
                this.btn_load_a.setEnabled(false);
                this.btn_load_b.setEnabled(true);
                return;
            } else if (this.mFwInfo.fwType.charValue() == 'B') {
                this.btn_load_a.setEnabled(true);
            } else {
                return;
            }
        }
        this.btn_load_b.setEnabled(false);
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void updateTimeProgress() {
        int i = this.f2250C.f2298d / 1000;
        int i2 = i > 0 ? this.f2250C.f2295a / i : 0;
        String format = String.format("%s %d / %d %s", new Object[]{getString(R.string.l_time), Integer.valueOf(i), Integer.valueOf(this.fwSecond), getString(R.string.l_sec)});
        this.tv_tw_info.setText(format + String.format("    %s %d (%d/%s)", new Object[]{getString(R.string.l_byte), Integer.valueOf(this.f2250C.f2295a), Integer.valueOf(i2), getString(R.string.l_sec)}));
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public void m2336j() {
        boolean a = enableNotification_value(this.f2274r, true);
        if (a) {
            a = writeMyCharacteristicByte(this.f2274r, (byte) 0);
        }
        if (a) {
            a = writeMyCharacteristicByte(this.f2274r, (byte) 1);
        }
        if (!a) {
            Toast.makeText(this, getString(R.string.p_get_target_fail), Toast.LENGTH_LONG).show();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public void m2338k() {
        this.f2276t.setValue(new byte[]{DataParser.shortToByte((short) 10), DataParser.m2760b((short) 10), DataParser.shortToByte((short) 10), DataParser.m2760b((short) 10), 0, 0, DataParser.shortToByte((short) 100), DataParser.m2760b((short) 100)});
        if (this.mBleHelper.writeCharacteristicBle(this.f2276t)) {
            this.mBleHelper.sleep10(100);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x006b, code lost:
        if (r7.mBleHelper.getBluetoothGatt() == null) goto L_0x006d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* renamed from: l */
    public void m2340l() {
        if (this.f2250C.allSize < this.f2250C.completeSize) {
            this.isStartFw = true;
            this.f2281y[0] = DataParser.shortToByte(this.f2250C.allSize);
            this.f2281y[1] = DataParser.m2760b(this.f2250C.allSize);
            System.arraycopy(this.f2280x, this.f2250C.f2295a, this.f2281y, 2, 16);
            this.f2275s.setValue(this.f2281y);
            if (this.mBleHelper.writeCharacteristicBle(this.f2275s)) {
                C0622b bVar = this.f2250C;
                bVar.allSize = (short) (bVar.allSize + 1);
                this.f2250C.f2295a += 16;
                this.pb_progress.setProgress((this.f2250C.allSize * 100) / this.f2250C.completeSize);
            }
            this.f2250C.f2298d += 20;
            if (this.isStartFw) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        FwUpdateActivity.this.updateTimeProgress();
                        FwUpdateActivity.this.m2330g();
                    }
                });
                return;
            }
            return;
        }
        this.isStartFw = false;
        this.f2250C.f2298d += 20;
        if (this.isStartFw) {
        }
    }

    /* renamed from: m */
    private void restartBle() {
        BluetoothAdapter adapter = ((BluetoothManager) getSystemService(BLUETOOTH_SERVICE)).getAdapter();
        if (adapter.isEnabled()) {
            adapter.disable();
        } else {
            adapter.enable();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public void showNOT_support_firmware_updataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.NOT_support_firmware_updata));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FwUpdateActivity.this.onBackPressed();
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public void showDisconnect_and_checkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.Disconnect_and_check));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FwUpdateActivity.this.onBackPressed();
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* renamed from: p */
    private void showFw_update_tipsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.fw_update_tips));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Boolean getMyNetInfoType() {
        return Boolean.valueOf(this.NetiBinder.getNetInfoType() != -1);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void startFwUpdate() throws IOException {
        showFw_update_tipsDialog();
        selectFwImg();
    }

    /* renamed from: c */
    public void selectFwImg() throws IOException {
        m2315a(this.notifyFwInfo.fwType.charValue() == 'A' ? "Muirhead_ImgB.bin" : "Muirhead_ImgA.bin", true);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 3001 && i2 == -1) {
            String stringExtra = intent.getStringExtra("file_path");
            String str = TAG;
            Log.d(str, "return: " + stringExtra);
            try {
                m2315a(stringExtra, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (this.isStartFw) {
            Toast.makeText(this, getResources().getString(R.string.Device_is_programming), Toast.LENGTH_LONG).show();
        } else if (!this.isAuto) {
            super.onBackPressed();
        } else if (this.mMainService.isMBleConn()) {
            this.mMainService.disconnectMainBLE();
        }
    }

    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_fwupdate);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String stringExtra = getIntent().getStringExtra("update");
        if (stringExtra.equals("Auto")) {
            Log.i(TAG, "Auto");
            this.isAuto = true;
        } else if (stringExtra.equals("Manual")) {
            Log.i(TAG, "Manual");
            this.isAuto = false;
        }
        ((ImageView) findViewById(16908332)).setPadding(10, 0, 20, 10);
        setTitle(getString(R.string.Firmware_Updata));
        this.tv_tw_info = (TextView) findViewById(R.id.tw_info);
        this.tv_tw_target = (TextView) findViewById(R.id.tw_target);
        this.tv_tw_file = (TextView) findViewById(R.id.tw_file);
        this.tv_tw_log = (TextView) findViewById(R.id.tw_log);
        this.pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        this.btn_start = (Button) findViewById(R.id.btn_start);
        this.btn_start.setEnabled(false);
        this.btn_load_a = (Button) findViewById(R.id.btn_load_a);
        this.btn_load_b = (Button) findViewById(R.id.btn_load_b);
        this.btn_load_c = (Button) findViewById(R.id.btn_load_c);
        this.ll_file_LinearLayout = (LinearLayout) findViewById(R.id.file_LinearLayout);
        this.ll_file_LinearLayout.setVisibility(View.INVISIBLE);
        this.btn_load_a.setVisibility(View.INVISIBLE);
        this.btn_load_b.setVisibility(View.INVISIBLE);
        this.btn_load_c.setVisibility(View.INVISIBLE);
        addIntentFilter();
        bindService(new Intent(this, MainService.class), this.MainBinderSerConn, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, NetService.class), this.netBinderSerConn, Context.BIND_AUTO_CREATE);
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        unbindService(this.MainBinderSerConn);
        unbindService(this.netBinderSerConn);
        if (this.mFwTimerTask != null) {
            this.mFwTimerTask.cancel();
        }
        this.mFwTimer = null;
    }

    public void onLoad(View view) throws IOException {
        m2315a(view.getId() == R.id.btn_load_a ? "Muirhead_ImgA.bin" : "Muirhead_ImgB.bin", true);
        m2332h();
    }

    public void onLoadCustom(View view) {
        startActivityForResult(new Intent(this, FileExploreActivity.class), 3001);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.fwBroadcastReceiver);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.fwBroadcastReceiver, this.mIntentFilter);
    }

    public void onStart(View view) {
        if (this.isStartFw) {
            m2330g();
        } else {
            startFw_update();
        }
    }
}
