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
public class BleHelper {

    /* renamed from: a */
    public static UUID DESC_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static final String TAG = "a";

    /* renamed from: b */
    Timer f2932b;

    /* renamed from: c */
    boolean f2933c = false;

    /* renamed from: d */
    Context mCtx;

    /* renamed from: f */
    private BluetoothManager mBluetoothManager;

    /* renamed from: g */
    private BluetoothAdapter mbluetoothAdapter;

    /* renamed from: h */
    private String f2937h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public BluetoothGatt mbluetoothGatt;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int f2939j = 0;

    /* renamed from: k */
    private boolean f2940k = false;

    /* renamed from: l */
    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            BleHelper.this.m2825a("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY", 0, bluetoothGattCharacteristic);
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0) {
                BleHelper.this.m2825a("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_READ", i, bluetoothGattCharacteristic);
            }
            super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0) {
                BleHelper.this.m2825a("com.hopetruly.ec.services.ACTION_GATT_CHARACTERISTIC_WRITE", i, bluetoothGattCharacteristic);
            }
            super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            String g = BleHelper.TAG;
            Log.i(g, "Status=" + i + "  newState=" + i2);
            if (i == 0 && i2 == 2) {
                int unused = BleHelper.this.f2939j = 2;
                BleHelper.this.sendBleConn("com.hopetruly.ec.services.ACTION_GATT_CONNECTED");
                Log.i(BleHelper.TAG, "Connected to GATT server.");
                String g2 = BleHelper.TAG;
                Log.i(g2, "Attempting to start service discovery:" + BleHelper.this.mbluetoothGatt.discoverServices());
            } else if (i2 == 0) {
                int unused2 = BleHelper.this.f2939j = 0;
                Log.i(BleHelper.TAG, "Disconnected from GATT server.");
                BleHelper.this.closeBluetoothGatt();
                BleHelper.this.sendBleConn("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
            }
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.i(BleHelper.TAG, "call onDescriptorRead");
            if (i == 0) {
                BleHelper.this.sendBleConn("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORREAD");
            }
            super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.i(BleHelper.TAG, "call onDescriptorWrite");
            if (i == 0) {
                BleHelper.this.sendBleConn("com.hopetruly.ec.services.ACTION_GATT_DESCRIPTORWRITE");
            }
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            if (i == 0) {
                Log.i(BleHelper.TAG, "onServicesDiscovered success");
                BleHelper.this.f2933c = true;
                BleHelper.this.sendBleConn("com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED");
                return;
            }
            String g = BleHelper.TAG;
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
            BleHelper.this.m2821a(intent);
        }
    };

    public BleHelper(Context context) {
        this.mCtx = context;
        this.f2940k = mo2798a();
        if (this.f2940k) {
            this.f2932b = new Timer();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2821a(Intent intent) {
//        C0140d.m485a(this.mCtx).mo390a(intent);
        LocalBroadcastManager.getInstance(mCtx).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2825a(String str, int i, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Intent intent = new Intent(str);
        byte[] value = bluetoothGattCharacteristic.getValue();
        intent.putExtra("com.hopetruly.ec.services.EXTRA_STATUS", i);
        intent.putExtra("com.hopetruly.ec.services.EXTRA_DATA", value);
        intent.putExtra("com.hopetruly.ec.services.EXTRA_UUID", bluetoothGattCharacteristic.getUuid().toString());
//        C0140d.m485a(this.mCtx).mo390a(intent);
        LocalBroadcastManager.getInstance(mCtx).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void sendBleConn(String str) {
//        C0140d.m485a(this.mCtx).mo390a(new Intent(str));   //其实就是发个广播
        LocalBroadcastManager.getInstance(mCtx).sendBroadcast(new Intent(str));
    }

    /* renamed from: a */
    public BluetoothGattService mo2796a(UUID uuid) {
        if (this.mbluetoothGatt != null) {
            return this.mbluetoothGatt.getService(uuid);
        }
        return null;
    }

    /* renamed from: a */
    public void startScan(int i) {
        if (this.mbluetoothAdapter == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return;
        }
        Log.i(TAG, "start Scan LeDevice");
        if (!this.f2942m) {
            this.f2932b.schedule(new TimerTask() {
                public void run() {
                    BleHelper.this.mo2808d();
                }
            }, (long) i);
            mbluetoothAdapter.startLeScan(this.f2943n);
            this.f2942m = true;
            m2821a(new Intent("com.hopetruly.ec.services.ACTION_BLE_START_SCAN"));
        }
    }

    /* renamed from: a */
    public boolean mo2798a() {
        if (this.mCtx.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            sendBleConn("com.hopetruly.ec.services.ACTION_BLE_SUPPORTED");
            if (this.mBluetoothManager == null) {
                this.mBluetoothManager = (BluetoothManager) this.mCtx.getSystemService(Context.BLUETOOTH_SERVICE);
                if (this.mBluetoothManager == null) {
                    Log.e(TAG, "Unable to initialize BluetoothManager.");
                    return false;
                }
            }
            this.mbluetoothAdapter = this.mBluetoothManager.getAdapter();
            if (this.mbluetoothAdapter == null) {
                Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
                return false;
            }
            sendBleConn(!this.mbluetoothAdapter.isEnabled() ? "com.hopetruly.ec.services.ACTION_BLE_NOT_OPEN" : "com.hopetruly.ec.services.ACTION_BLE_OPEN");
            return true;
        }
        sendBleConn("com.hopetruly.ec.services.ACTION_BLE_NOT_SUPPORTED");
        return false;
    }

    /* renamed from: a */
    public boolean writeCharacteristicBle(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.mbluetoothAdapter == null || this.mbluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        } else if (this.f2933c) {
            return this.mbluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
        } else {
            Log.e(TAG, "Service is not ready");
            return false;
        }
    }

    /* renamed from: a */
    public boolean eNABLE_NOTIFICATION_VALUE(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        if (this.mbluetoothAdapter == null || this.mbluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        this.mbluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z);
        BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(DESC_UUID);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        return this.mbluetoothGatt.writeDescriptor(descriptor);
    }

    /* renamed from: a */
    public boolean connectbLE(String str) {
        String str2;
        String str3;
        if (this.mbluetoothAdapter == null || str == null) {
            str2 = TAG;
            str3 = "BluetoothAdapter not initialized or unspecified address.";
        } else {
            BluetoothDevice remoteDevice = this.mbluetoothAdapter.getRemoteDevice(str);
            if (remoteDevice == null) {
                str2 = TAG;
                str3 = "Device not found.  Unable to connect.";
            } else {
                this.mbluetoothGatt = remoteDevice.connectGatt(this.mCtx, false, this.mBluetoothGattCallback);
                Log.d(TAG, "Trying to create a new connection.");
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
    public boolean checkUUID(UUID uuid, UUID uuid2) {
        String str;
        String str2;
        BluetoothGattCharacteristic characteristic;
        if (this.mbluetoothAdapter == null || this.mbluetoothGatt == null) {
            str = TAG;
            str2 = "BluetoothAdapter not initialized";
        } else if (!this.f2933c) {
            str = TAG;
            str2 = "GATT Service not ready";
        } else {
            BluetoothGattService service = this.mbluetoothGatt.getService(uuid2);
            characteristic = service.getCharacteristic(uuid);
            return (service == null || characteristic == null || !this.mbluetoothGatt.readCharacteristic(characteristic)) ? false : true;
        }
        Log.w(str, str2);
        return false;
    }

    /* renamed from: a */
    public boolean writeDescriptorVar(UUID uuid, UUID uuid2, boolean z) {
        String str;
        String str2;
        if (this.mbluetoothAdapter == null || this.mbluetoothGatt == null) {
            str = TAG;
            str2 = "BluetoothAdapter not initialized";
        } else if (!this.f2933c) {
            str = TAG;
            str2 = "GATT Service not ready";
        } else {
            try {
                BluetoothGattCharacteristic characteristic = this.mbluetoothGatt.getService(uuid).getCharacteristic(uuid2);
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(DESC_UUID);
                descriptor.setValue(z ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                if (!this.mbluetoothGatt.setCharacteristicNotification(characteristic, z) || !this.mbluetoothGatt.writeDescriptor(descriptor)) {
                    return false;
                }
                Log.i(TAG, "the setCharacteristicNotification was set successfully");
                Log.i(TAG, "the writeDescriptor was initiated successfully");
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
    public boolean writeCharacteristicBytes(byte[] bArr, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.mbluetoothAdapter == null || this.mbluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        } else if (!this.f2933c) {
            Log.e(TAG, "Service is not ready");
            return false;
        } else {
            bluetoothGattCharacteristic.setValue(bArr);
            return this.mbluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
        }
    }

    /* renamed from: b */
    public void disconnectBle() {
        if (this.mbluetoothAdapter == null || this.mbluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        Log.w(TAG, "Disconnect connection");
        this.mbluetoothGatt.disconnect();
        this.f2937h = null;
    }

    /* renamed from: b */
    public boolean sleep10(int i) {
        try {
            Thread.sleep(10);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return true;
        }
    }

    /* renamed from: c */
    public void closeBluetoothGatt() {
        if (this.mbluetoothGatt != null) {
            this.mbluetoothGatt.close();
            this.mbluetoothGatt = null;
        }
    }

    /* renamed from: d */
    public void mo2808d() {
        if (this.mbluetoothAdapter == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
        } else if (this.f2942m) {
            Log.i(TAG, "stop Scan LeDevice");
            this.f2942m = false;
            this.mbluetoothAdapter.stopLeScan(this.f2943n);
            m2821a(new Intent("com.hopetruly.ec.services.ACTION_BLE_STOP_SCAN"));
        }
    }

    /* renamed from: e */
    public List<BluetoothGattService> getBluetoothGattServices() {
        if (this.mbluetoothGatt == null) {
            return null;
        }
        return this.mbluetoothGatt.getServices();
    }

    /* renamed from: f */
    public BluetoothGatt getBluetoothGatt() {
        if (this.mbluetoothGatt == null) {
            return null;
        }
        return this.mbluetoothGatt;
    }
}
