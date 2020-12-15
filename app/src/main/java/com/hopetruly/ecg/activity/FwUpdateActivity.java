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
import android.os.Environment;
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
    public static String f2246a = "FwUpdateActivity";

    /* renamed from: c */
    private static final String f2247c = Environment.DIRECTORY_DOWNLOADS;
    /* access modifiers changed from: private */

    /* renamed from: A */
    public C0621a f2248A = new C0621a();

    /* renamed from: B */
    private Timer f2249B = null;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public C0622b f2250C = new C0622b();

    /* renamed from: D */
    private TimerTask f2251D = null;
    /* access modifiers changed from: private */

    /* renamed from: E */
    public boolean f2252E = false;
    /* access modifiers changed from: private */

    /* renamed from: F */
    public boolean f2253F = false;
    /* access modifiers changed from: private */

    /* renamed from: G */
    public boolean f2254G = false;

    /* renamed from: H */
    private int f2255H = 0;

    /* renamed from: I */
    private IntentFilter f2256I;

    /* renamed from: J */
    private ServiceConnection f2257J = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NetService unused = FwUpdateActivity.this.f2279w = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetService unused = FwUpdateActivity.this.f2279w = null;
        }
    };

    /* renamed from: K */
    private ServiceConnection f2258K = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainService unused = FwUpdateActivity.this.f2278v = ((MainService.MainBinder) iBinder).getMainBinder();
            if (!FwUpdateActivity.this.f2278v.isMBleConn()) {
                FwUpdateActivity.this.m2346o();
                return;
            }
            BleHelper unused2 = FwUpdateActivity.this.f2277u = FwUpdateActivity.this.f2278v.mo2733g();
            BluetoothGattService unused3 = FwUpdateActivity.this.f2270n = FwUpdateActivity.this.f2277u.mo2796a(ECGUUIDS.f2787t);
            BluetoothGattService unused4 = FwUpdateActivity.this.f2271o = FwUpdateActivity.this.f2277u.mo2796a(ECGUUIDS.f2790w);
            if ((FwUpdateActivity.this.f2270n == null) || (FwUpdateActivity.this.f2271o == null)) {
                FwUpdateActivity.this.m2343n();
                boolean unused5 = FwUpdateActivity.this.f2252E = false;
                return;
            }
            List unused6 = FwUpdateActivity.this.f2272p = FwUpdateActivity.this.f2270n.getCharacteristics();
            List unused7 = FwUpdateActivity.this.f2273q = FwUpdateActivity.this.f2271o.getCharacteristics();
            boolean unused8 = FwUpdateActivity.this.f2252E = FwUpdateActivity.this.f2272p.size() == 2 && FwUpdateActivity.this.f2273q.size() >= 3;
            if (FwUpdateActivity.this.f2252E) {
                BluetoothGattCharacteristic unused9 = FwUpdateActivity.this.f2274r = (BluetoothGattCharacteristic) FwUpdateActivity.this.f2272p.get(0);
                BluetoothGattCharacteristic unused10 = FwUpdateActivity.this.f2275s = (BluetoothGattCharacteristic) FwUpdateActivity.this.f2272p.get(1);
                BluetoothGattCharacteristic unused11 = FwUpdateActivity.this.f2276t = (BluetoothGattCharacteristic) FwUpdateActivity.this.f2273q.get(1);
            }
            FwUpdateActivity.this.f2265i.setEnabled(FwUpdateActivity.this.f2252E);
            FwUpdateActivity.this.f2266j.setEnabled(FwUpdateActivity.this.f2252E);
            FwUpdateActivity.this.f2267k.setEnabled(FwUpdateActivity.this.f2252E);
            if (FwUpdateActivity.this.f2252E) {
                FwUpdateActivity.this.m2336j();
                FwUpdateActivity.this.m2338k();
                return;
            }
            FwUpdateActivity.this.m2343n();
            Toast.makeText(FwUpdateActivity.this.getApplicationContext(), FwUpdateActivity.this.getString(R.string.p_oad_init_fail), Toast.LENGTH_LONG).show();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainService unused = FwUpdateActivity.this.f2278v = null;
        }
    };

    /* renamed from: L */
    private final BroadcastReceiver f2259L = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String d = FwUpdateActivity.f2246a;
            Log.d(d, "action: " + action);
            if ("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY".equals(action)) {
                byte[] byteArrayExtra = intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA");
                if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(FwUpdateActivity.this.f2274r.getUuid().toString())) {
                    FwUpdateActivity.this.f2248A.f2290a = DataParser.m2758a(byteArrayExtra[1], byteArrayExtra[0]);
                    FwUpdateActivity.this.f2248A.f2292c = Character.valueOf((FwUpdateActivity.this.f2248A.f2290a & 1) == 1 ? 'B' : 'A');
                    FwUpdateActivity.this.f2248A.f2291b = DataParser.parser(byteArrayExtra[3], byteArrayExtra[2]);
                    FwUpdateActivity.this.m2310a(FwUpdateActivity.this.f2260d, FwUpdateActivity.this.f2248A);
                    try {
                        FwUpdateActivity.this.mo2204b();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (FwUpdateActivity.this.f2254G) {
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
                    String d2 = FwUpdateActivity.f2246a;
                    Log.e(d2, "Write failed: " + intExtra);
                    Toast.makeText(context, "GATT error: status=" + intExtra, Toast.LENGTH_SHORT).show();
                }
            } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                boolean unused = FwUpdateActivity.this.f2254G = false;
                FwUpdateActivity.this.m2346o();
            }
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: d */
    public TextView f2260d;

    /* renamed from: e */
    private TextView f2261e;

    /* renamed from: f */
    private TextView f2262f;

    /* renamed from: g */
    private TextView f2263g;

    /* renamed from: h */
    private ProgressBar f2264h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public Button f2265i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public Button f2266j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public Button f2267k;

    /* renamed from: l */
    private Button f2268l;

    /* renamed from: m */
    private LinearLayout f2269m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public BluetoothGattService f2270n;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public BluetoothGattService f2271o;
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
    public BleHelper f2277u;
    /* access modifiers changed from: private */

    /* renamed from: v */
    public MainService f2278v;
    /* access modifiers changed from: private */

    /* renamed from: w */
    public NetService f2279w;

    /* renamed from: x */
    private final byte[] f2280x = new byte[262144];

    /* renamed from: y */
    private final byte[] f2281y = new byte[18];
    /* access modifiers changed from: private */

    /* renamed from: z */
    public C0621a f2282z = new C0621a();

    /* renamed from: com.hopetruly.ecg.activity.FwUpdateActivity$a */
    private class C0621a {

        /* renamed from: a */
        short f2290a;

        /* renamed from: b */
        int f2291b;

        /* renamed from: c */
        Character f2292c;

        /* renamed from: d */
        byte[] f2293d;

        private C0621a() {
            this.f2293d = new byte[4];
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.FwUpdateActivity$b */
    private class C0622b {

        /* renamed from: a */
        int f2295a;

        /* renamed from: b */
        short f2296b;

        /* renamed from: c */
        short f2297c;

        /* renamed from: d */
        int f2298d;

        /* renamed from: e */
        int f2299e;

        private C0622b() {
            this.f2295a = 0;
            this.f2296b = 0;
            this.f2297c = 0;
            this.f2298d = 0;
            this.f2299e = 0;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void mo2223a() {
            this.f2295a = 0;
            this.f2296b = 0;
            this.f2298d = 0;
            this.f2299e = 0;
            this.f2297c = (short) (FwUpdateActivity.this.f2282z.f2291b / 4);
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.FwUpdateActivity$c */
    private class C0623c extends TimerTask {
        private C0623c() {
        }

        public void run() {
            FwUpdateActivity.this.f2250C.f2299e++;
            if (FwUpdateActivity.this.f2253F) {
                FwUpdateActivity.this.m2340l();
                if (FwUpdateActivity.this.f2250C.f2299e % 20 == 0) {
                    FwUpdateActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            FwUpdateActivity.this.m2333i();
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2310a(TextView textView, C0621a aVar) {
        String str = f2246a;
        LogUtils.logE(str, "h.len>>" + aVar.f2291b);
        textView.setText(Html.fromHtml(String.format("%s %c %s %d %s %d", new Object[]{getString(R.string.l_type), aVar.f2292c, getString(R.string.l_version), Integer.valueOf(aVar.f2290a >> 1), getString(R.string.l_size), Integer.valueOf(aVar.f2291b * 4)})));
    }

    /* renamed from: a */
    private boolean m2312a(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte b) {
        boolean a = this.f2277u.writeCharacteristicBytes(new byte[]{b}, bluetoothGattCharacteristic);
        return a ? this.f2277u.sleep10(100) : a;
    }

    /* renamed from: a */
    private boolean m2313a(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        boolean a = this.f2277u.eNABLE_NOTIFICATION_VALUE(bluetoothGattCharacteristic, z);
        return a ? this.f2277u.sleep10(100) : a;
    }

    /* renamed from: a */
    private boolean m2315a(String str, boolean z) throws IOException {
        InputStream inputStream;
        if (z) {
            try {
                inputStream = getAssets().open(str);
            } catch (IOException unused) {
                this.f2263g.setText(getString(R.string.l_open_file_fail) + " " + str + "\n");
                return false;
            }
        } else {
            inputStream = new FileInputStream(new File(str));
        }
        inputStream.read(this.f2280x, 0, this.f2280x.length);
        inputStream.close();
        this.f2282z.f2290a = DataParser.m2758a(this.f2280x[5], this.f2280x[4]);
        this.f2282z.f2291b = DataParser.parser(this.f2280x[7], this.f2280x[6]);
        boolean z2 = true;
        this.f2282z.f2292c = Character.valueOf((this.f2282z.f2290a & 1) == 1 ? 'B' : 'A');
        System.arraycopy(this.f2280x, 8, this.f2282z.f2293d, 0, 4);
        m2310a(this.f2261e, this.f2282z);
        if (this.f2282z.f2292c == this.f2248A.f2292c) {
            z2 = false;
        }
        this.f2261e.setTextColor(z2 ? -16777216 : Color.rgb(238, 92, 66));
        this.f2268l.setEnabled(z2);
        this.f2255H = (((20 * this.f2282z.f2291b) * 4) / 16) / 1000;
        m2333i();
        this.f2263g.setText(getString(R.string.l_img) + " " + this.f2282z.f2292c + " " + getString(R.string.l_sel) + ".\n");
        this.f2263g.append(getString(z2 ? R.string.l_fw_ready : R.string.l_fw_incorrect_img));
        m2332h();
        return false;
    }

    /* renamed from: e */
    private void m2326e() {
        this.f2256I = new IntentFilter();
        this.f2256I.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        this.f2256I.addAction("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE");
        this.f2256I.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        this.f2256I.addAction("com.holptruly.ecg.services.NetService.NET_CHANGE");
    }

    /* renamed from: f */
    private void m2327f() {
        this.f2263g.append(getString(R.string.l_fw_update_start));
        this.f2253F = true;
        m2332h();
        byte[] bArr = new byte[12];
        bArr[0] = DataParser.m2757a(this.f2282z.f2290a);
        bArr[1] = DataParser.m2760b(this.f2282z.f2290a);
        bArr[2] = DataParser.m2756a(this.f2282z.f2291b);
        bArr[3] = DataParser.m2759b(this.f2282z.f2291b);
        System.arraycopy(this.f2282z.f2293d, 0, bArr, 4, 4);
        this.f2274r.setValue(bArr);
        this.f2277u.writeCharacteristicBle(this.f2274r);
        this.f2250C.mo2223a();
        this.f2249B = null;
        this.f2249B = new Timer();
        this.f2251D = new C0623c();
        this.f2249B.scheduleAtFixedRate(this.f2251D, 0, 20);
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m2330g() {
        this.f2249B.cancel();
        this.f2249B.purge();
        if (this.f2251D != null) {
            this.f2251D.cancel();
        }
        this.f2251D = null;
        this.f2253F = false;
        this.f2262f.setText("");
        this.f2264h.setProgress(0);
        m2332h();
        if (this.f2250C.f2296b == this.f2250C.f2297c) {
            this.f2263g.setText(getString(R.string.l_fw_update_ok));
            if (this.f2254G && mo2203a().booleanValue()) {
                SharedPreferences.Editor edit = this.ecgApplication.spSw_conf.edit();
                this.ecgApplication.mSwConf.setDevice_id_upload(1);
                edit.putInt("DEVICE_ID_UPLOAD", this.ecgApplication.mSwConf.getDevice_id_upload());
                edit.commit();
            }
            this.f2254G = false;
            m2341m();
            return;
        }
        this.f2263g.append(getString(R.string.l_fw_update_cancel));
    }

    /* renamed from: h */
    private void m2332h() {
        if (this.f2253F) {
            this.f2268l.setText(getResources().getString(R.string.cancle));
            this.f2265i.setEnabled(false);
        } else {
            this.f2264h.setProgress(0);
            this.f2268l.setText(getResources().getString(R.string.start_programming));
            if (this.f2282z.f2292c.charValue() == 'A') {
                this.f2265i.setEnabled(false);
                this.f2266j.setEnabled(true);
                return;
            } else if (this.f2282z.f2292c.charValue() == 'B') {
                this.f2265i.setEnabled(true);
            } else {
                return;
            }
        }
        this.f2266j.setEnabled(false);
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void m2333i() {
        int i = this.f2250C.f2298d / 1000;
        int i2 = i > 0 ? this.f2250C.f2295a / i : 0;
        String format = String.format("%s %d / %d %s", new Object[]{getString(R.string.l_time), Integer.valueOf(i), Integer.valueOf(this.f2255H), getString(R.string.l_sec)});
        this.f2262f.setText(format + String.format("    %s %d (%d/%s)", new Object[]{getString(R.string.l_byte), Integer.valueOf(this.f2250C.f2295a), Integer.valueOf(i2), getString(R.string.l_sec)}));
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public void m2336j() {
        boolean a = m2313a(this.f2274r, true);
        if (a) {
            a = m2312a(this.f2274r, (byte) 0);
        }
        if (a) {
            a = m2312a(this.f2274r, (byte) 1);
        }
        if (!a) {
            Toast.makeText(this, getString(R.string.p_get_target_fail), Toast.LENGTH_LONG).show();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public void m2338k() {
        this.f2276t.setValue(new byte[]{DataParser.m2757a((short) 10), DataParser.m2760b((short) 10), DataParser.m2757a((short) 10), DataParser.m2760b((short) 10), 0, 0, DataParser.m2757a((short) 100), DataParser.m2760b((short) 100)});
        if (this.f2277u.writeCharacteristicBle(this.f2276t)) {
            this.f2277u.sleep10(100);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x006b, code lost:
        if (r7.f2277u.getBluetoothGatt() == null) goto L_0x006d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* renamed from: l */
    public void m2340l() {
        if (this.f2250C.f2296b < this.f2250C.f2297c) {
            this.f2253F = true;
            this.f2281y[0] = DataParser.m2757a(this.f2250C.f2296b);
            this.f2281y[1] = DataParser.m2760b(this.f2250C.f2296b);
            System.arraycopy(this.f2280x, this.f2250C.f2295a, this.f2281y, 2, 16);
            this.f2275s.setValue(this.f2281y);
            if (this.f2277u.writeCharacteristicBle(this.f2275s)) {
                C0622b bVar = this.f2250C;
                bVar.f2296b = (short) (bVar.f2296b + 1);
                this.f2250C.f2295a += 16;
                this.f2264h.setProgress((this.f2250C.f2296b * 100) / this.f2250C.f2297c);
            }
            this.f2250C.f2298d += 20;
            if (this.f2253F) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        FwUpdateActivity.this.m2333i();
                        FwUpdateActivity.this.m2330g();
                    }
                });
                return;
            }
            return;
        }
        this.f2253F = false;
        this.f2250C.f2298d += 20;
        if (this.f2253F) {
        }
    }

    /* renamed from: m */
    private void m2341m() {
        BluetoothAdapter adapter = ((BluetoothManager) getSystemService(BLUETOOTH_SERVICE)).getAdapter();
        if (adapter.isEnabled()) {
            adapter.disable();
        } else {
            adapter.enable();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public void m2343n() {
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
    public void m2346o() {
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
    private void m2348p() {
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
    public Boolean mo2203a() {
        return Boolean.valueOf(this.f2279w.getNetInfoType() != -1);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo2204b() throws IOException {
        m2348p();
        mo2205c();
    }

    /* renamed from: c */
    public void mo2205c() throws IOException {
        m2315a(this.f2248A.f2292c.charValue() == 'A' ? "Muirhead_ImgB.bin" : "Muirhead_ImgA.bin", true);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 3001 && i2 == -1) {
            String stringExtra = intent.getStringExtra("file_path");
            String str = f2246a;
            Log.d(str, "return: " + stringExtra);
            try {
                m2315a(stringExtra, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {
        Log.d(f2246a, "onBackPressed");
        if (this.f2253F) {
            Toast.makeText(this, getResources().getString(R.string.Device_is_programming), Toast.LENGTH_LONG).show();
        } else if (!this.f2254G) {
            super.onBackPressed();
        } else if (this.f2278v.isMBleConn()) {
            this.f2278v.disconnectMainBLE();
        }
    }

    public void onCreate(Bundle bundle) {
        Log.d(f2246a, "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_fwupdate);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String stringExtra = getIntent().getStringExtra("update");
        if (stringExtra.equals("Auto")) {
            Log.i(f2246a, "Auto");
            this.f2254G = true;
        } else if (stringExtra.equals("Manual")) {
            Log.i(f2246a, "Manual");
            this.f2254G = false;
        }
        ((ImageView) findViewById(16908332)).setPadding(10, 0, 20, 10);
        setTitle(getString(R.string.Firmware_Updata));
        this.f2262f = (TextView) findViewById(R.id.tw_info);
        this.f2260d = (TextView) findViewById(R.id.tw_target);
        this.f2261e = (TextView) findViewById(R.id.tw_file);
        this.f2263g = (TextView) findViewById(R.id.tw_log);
        this.f2264h = (ProgressBar) findViewById(R.id.pb_progress);
        this.f2268l = (Button) findViewById(R.id.btn_start);
        this.f2268l.setEnabled(false);
        this.f2265i = (Button) findViewById(R.id.btn_load_a);
        this.f2266j = (Button) findViewById(R.id.btn_load_b);
        this.f2267k = (Button) findViewById(R.id.btn_load_c);
        this.f2269m = (LinearLayout) findViewById(R.id.file_LinearLayout);
        this.f2269m.setVisibility(View.INVISIBLE);
        this.f2265i.setVisibility(View.INVISIBLE);
        this.f2266j.setVisibility(View.INVISIBLE);
        this.f2267k.setVisibility(View.INVISIBLE);
        m2326e();
        bindService(new Intent(this, MainService.class), this.f2258K, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, NetService.class), this.f2257J, Context.BIND_AUTO_CREATE);
    }

    public void onDestroy() {
        Log.d(f2246a, "onDestroy");
        super.onDestroy();
        unbindService(this.f2258K);
        unbindService(this.f2257J);
        if (this.f2251D != null) {
            this.f2251D.cancel();
        }
        this.f2249B = null;
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
        Log.d(f2246a, "onPause");
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2259L);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.d(f2246a, "onResume");
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2259L, this.f2256I);
    }

    public void onStart(View view) {
        if (this.f2253F) {
            m2330g();
        } else {
            m2327f();
        }
    }
}
