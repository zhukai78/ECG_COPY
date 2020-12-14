package com.hopetruly.part.p024a;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/* renamed from: com.hopetruly.part.a.a */
public class C0777a {

    /* renamed from: a */
    public static UUID f2930a = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static final String f2931e = "a";

    /* renamed from: b */
    Timer f2932b;

    /* renamed from: c */
    boolean f2933c = false;

    /* renamed from: d */
    Context f2934d;

    /* renamed from: f */
    private BluetoothManager f2935f;

    /* renamed from: g */
    private BluetoothAdapter f2936g;

    /* renamed from: h */
    private String f2937h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public BluetoothGatt f2938i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int f2939j = 0;

    /* renamed from: k */
    private boolean f2940k = false;

    /* renamed from: l */
    private final BluetoothGattCallback f2941l = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            C0777a.this.m2825a("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY", 0, bluetoothGattCharacteristic);
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0) {
                C0777a.this.m2825a("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ", i, bluetoothGattCharacteristic);
            }
            super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0) {
                C0777a.this.m2825a("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE", i, bluetoothGattCharacteristic);
            }
            super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            String g = C0777a.f2931e;
            Log.i(g, "Status=" + i + "  newState=" + i2);
            if (i == 0 && i2 == 2) {
                int unused = C0777a.this.f2939j = 2;
                C0777a.this.m2826b("com.hopetruly.ec.services.ACTION_GATT_CONNECTED");
                Log.i(C0777a.f2931e, "Connected to GATT server.");
                String g2 = C0777a.f2931e;
                Log.i(g2, "Attempting to start service discovery:" + C0777a.this.f2938i.discoverServices());
            } else if (i2 == 0) {
                int unused2 = C0777a.this.f2939j = 0;
                Log.i(C0777a.f2931e, "Disconnected from GATT server.");
                C0777a.this.mo2807c();
                C0777a.this.m2826b("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
            }
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.i(C0777a.f2931e, "call onDescriptorRead");
            if (i == 0) {
                C0777a.this.m2826b("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORREAD");
            }
            super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.i(C0777a.f2931e, "call onDescriptorWrite");
            if (i == 0) {
                C0777a.this.m2826b("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE");
            }
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            if (i == 0) {
                Log.i(C0777a.f2931e, "onServicesDiscovered success");
                C0777a.this.f2933c = true;
                C0777a.this.m2826b("com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED");
                return;
            }
            String g = C0777a.f2931e;
            Log.w(g, "onServicesDiscovered false , received: " + i);
        }
    };

    /* renamed from: m */
    private boolean f2942m = false;

    /* renamed from: n */
    private BluetoothAdapter.LeScanCallback f2943n = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            Intent intent = new Intent("com.hopetruly.ec.services.ACTION_CATCH_DEVICE");
            intent.putExtra("device", bluetoothDevice);
            intent.putExtra("scanRecord", bArr);
            intent.putExtra("deviceRSSI", i);
            C0777a.this.m2821a(intent);
        }
    };

    public C0777a(Context context) {
        this.f2934d = context;
        this.f2940k = mo2798a();
        if (this.f2940k) {
            this.f2932b = new Timer();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2821a(Intent intent) {
//        C0140d.m485a(this.f2934d).mo390a(intent);
        LocalBroadcastManager.getInstance(f2934d).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2825a(String str, int i, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intent intent = new Intent(str);
        byte[] value = bluetoothGattCharacteristic.getValue();
        intent.putExtra("com.hopetruly.ec.services.EXTRA_STATUS", i);
        intent.putExtra("com.hopetruly.ec.services.EXTRA_DATA", value);
        intent.putExtra("com.hopetruly.ec.services.EXTRA_UUID", bluetoothGattCharacteristic.getUuid().toString());
//        C0140d.m485a(this.f2934d).mo390a(intent);
        LocalBroadcastManager.getInstance(f2934d).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2826b(String str) {
//        C0140d.m485a(this.f2934d).mo390a(new Intent(str));   //其实就是发个广播
        LocalBroadcastManager.getInstance(f2934d).sendBroadcast(new Intent(str));
    }

    /* renamed from: a */
    public BluetoothGattService mo2796a(UUID uuid) {
        if (this.f2938i != null) {
            return this.f2938i.getService(uuid);
        }
        return null;
    }

    /* renamed from: a */
    public void mo2797a(int i) {
        if (this.f2936g == null) {
            Log.w(f2931e, "BluetoothAdapter not initialized or unspecified address.");
            return;
        }
        Log.i(f2931e, "start Scan LeDevice");
        if (!this.f2942m) {
            this.f2932b.schedule(new TimerTask() {
                public void run() {
                    C0777a.this.mo2808d();
                }
            }, (long) i);
            f2936g.startLeScan(this.f2943n);
            this.f2942m = true;
            m2821a(new Intent("com.hopetruly.ec.services.ACTION_BLE_START_SCAN"));
        }
    }

    /* renamed from: a */
    public boolean mo2798a() {
        if (this.f2934d.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            m2826b("com.hopetruly.ec.services.ACTION_BLE_SUPPORTED");
            if (this.f2935f == null) {
                this.f2935f = (BluetoothManager) this.f2934d.getSystemService(Context.BLUETOOTH_SERVICE);
                if (this.f2935f == null) {
                    Log.e(f2931e, "Unable to initialize BluetoothManager.");
                    return false;
                }
            }
            this.f2936g = this.f2935f.getAdapter();
            if (this.f2936g == null) {
                Log.e(f2931e, "Unable to obtain a BluetoothAdapter.");
                return false;
            }
            m2826b(!this.f2936g.isEnabled() ? "com.hopetruly.ec.services.ACTION_BLE_NOT_OPEN" : "com.hopetruly.ec.services.ACTION_BLE_OPEN");
            return true;
        }
        m2826b("com.hopetruly.ec.services.ACTION_BLE_NOT_SUPPORTED");
        return false;
    }

    /* renamed from: a */
    public boolean mo2799a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.f2936g == null || this.f2938i == null) {
            Log.w(f2931e, "BluetoothAdapter not initialized");
            return false;
        } else if (this.f2933c) {
            return this.f2938i.writeCharacteristic(bluetoothGattCharacteristic);
        } else {
            Log.e(f2931e, "Service is not ready");
            return false;
        }
    }

    /* renamed from: a */
    public boolean mo2800a(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        if (this.f2936g == null || this.f2938i == null) {
            Log.w(f2931e, "BluetoothAdapter not initialized");
            return false;
        }
        this.f2938i.setCharacteristicNotification(bluetoothGattCharacteristic, z);
        BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(f2930a);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        return this.f2938i.writeDescriptor(descriptor);
    }

    /* renamed from: a */
    public boolean mo2801a(String str) {
        String str2;
        String str3;
        if (this.f2936g == null || str == null) {
            str2 = f2931e;
            str3 = "BluetoothAdapter not initialized or unspecified address.";
        } else {
            BluetoothDevice remoteDevice = this.f2936g.getRemoteDevice(str);
            if (remoteDevice == null) {
                str2 = f2931e;
                str3 = "Device not found.  Unable to connect.";
            } else {
                this.f2938i = remoteDevice.connectGatt(this.f2934d, false, this.f2941l);
                Log.d(f2931e, "Trying to create a new connection.");
                this.f2937h = str;
                this.f2939j = 1;
                return true;
            }
        }
        Log.w(str2, str3);
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r3 = r4.getCharacteristic(r3);
     */
    /* renamed from: a */
    public boolean mo2802a(UUID uuid, UUID uuid2) {
        String str;
        String str2;
        BluetoothGattCharacteristic characteristic;
        if (this.f2936g == null || this.f2938i == null) {
            str = f2931e;
            str2 = "BluetoothAdapter not initialized";
        } else if (!this.f2933c) {
            str = f2931e;
            str2 = "GATT Service not ready";
        } else {
            BluetoothGattService service = this.f2938i.getService(uuid2);
            characteristic = service.getCharacteristic(uuid);
            return (service == null || characteristic == null || !this.f2938i.readCharacteristic(characteristic)) ? false : true;
        }
        Log.w(str, str2);
        return false;
    }

    /* renamed from: a */
    public boolean mo2803a(UUID uuid, UUID uuid2, boolean z) {
        String str;
        String str2;
        if (this.f2936g == null || this.f2938i == null) {
            str = f2931e;
            str2 = "BluetoothAdapter not initialized";
        } else if (!this.f2933c) {
            str = f2931e;
            str2 = "GATT Service not ready";
        } else {
            try {
                BluetoothGattCharacteristic characteristic = this.f2938i.getService(uuid).getCharacteristic(uuid2);
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(f2930a);
                descriptor.setValue(z ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                if (!this.f2938i.setCharacteristicNotification(characteristic, z) || !this.f2938i.writeDescriptor(descriptor)) {
                    return false;
                }
                Log.i(f2931e, "the setCharacteristicNotification was set successfully");
                Log.i(f2931e, "the writeDescriptor was initiated successfully");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        Log.w(str, str2);
        return false;
    }

    /* renamed from: a */
    public boolean mo2804a(byte[] bArr, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.f2936g == null || this.f2938i == null) {
            Log.w(f2931e, "BluetoothAdapter not initialized");
            return false;
        } else if (!this.f2933c) {
            Log.e(f2931e, "Service is not ready");
            return false;
        } else {
            bluetoothGattCharacteristic.setValue(bArr);
            return this.f2938i.writeCharacteristic(bluetoothGattCharacteristic);
        }
    }

    /* renamed from: b */
    public void mo2805b() {
        if (this.f2936g == null || this.f2938i == null) {
            Log.w(f2931e, "BluetoothAdapter not initialized");
            return;
        }
        Log.w(f2931e, "Disconnect connection");
        this.f2938i.disconnect();
        this.f2937h = null;
    }

    /* renamed from: b */
    public boolean mo2806b(int i) {
        try {
            Thread.sleep(10);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return true;
        }
    }

    /* renamed from: c */
    public void mo2807c() {
        if (this.f2938i != null) {
            this.f2938i.close();
            this.f2938i = null;
        }
    }

    /* renamed from: d */
    public void mo2808d() {
        if (this.f2936g == null) {
            Log.w(f2931e, "BluetoothAdapter not initialized or unspecified address.");
        } else if (this.f2942m) {
            Log.i(f2931e, "stop Scan LeDevice");
            this.f2942m = false;
            this.f2936g.stopLeScan(this.f2943n);
            m2821a(new Intent("com.hopetruly.ec.services.ACTION_BLE_STOP_SCAN"));
        }
    }

    /* renamed from: e */
    public List<BluetoothGattService> mo2809e() {
        if (this.f2938i == null) {
            return null;
        }
        return this.f2938i.getServices();
    }

    /* renamed from: f */
    public BluetoothGatt mo2810f() {
        if (this.f2938i == null) {
            return null;
        }
        return this.f2938i;
    }
}
