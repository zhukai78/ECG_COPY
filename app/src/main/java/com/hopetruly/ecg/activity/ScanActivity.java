package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.Machine;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.C0771g;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ScanActivity extends C0721a implements AdapterView.OnItemClickListener {

    /* renamed from: a */
    String f2540a = "ScanActivity";

    /* renamed from: c */
    ArrayList<HashMap<String, String>> f2541c = new ArrayList<>();

    /* renamed from: d */
    ProgressBar f2542d;

    /* renamed from: e */
    boolean f2543e = false;

    /* renamed from: f */
    boolean f2544f = false;

    /* renamed from: g */
    boolean f2545g = false;

    /* renamed from: h */
    boolean f2546h = false;

    /* renamed from: i */
    MainService f2547i;

    /* renamed from: j */
    ECGApplication f2548j;

    /* renamed from: k */
    Machine f2549k;

    /* renamed from: l */
    int f2550l = 0;

    /* renamed from: m */
    boolean f2551m = false;

    /* renamed from: n */
    Timer f2552n;

    /* renamed from: o */
    TimerTask f2553o;

    /* renamed from: p */
    ProgressDialog f2554p;
    /* access modifiers changed from: private */

    /* renamed from: q */
    public boolean f2555q = false;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public boolean f2556r = false;

    /* renamed from: s */
    private SharedPreferences.Editor f2557s;

    /* renamed from: t */
    private int f2558t = 10000;

    /* renamed from: u */
    private int f2559u = 30000;
    /* access modifiers changed from: private */

    /* renamed from: v */
    public AlertDialog f2560v;

    /* renamed from: w */
    private ServiceConnection f2561w = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ScanActivity.this.f2547i = ((MainService.C0762a) iBinder).mo2756a();
            ScanActivity.this.f2546h = true;
            boolean unused = ScanActivity.this.m2511a();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ScanActivity.this.f2547i = null;
            ScanActivity.this.f2546h = false;
        }
    };

    /* renamed from: x */
    private BroadcastReceiver f2562x = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            ProgressDialog progressDialog;
            Resources resources;
            int i;
            String action = intent.getAction();
            if (action.equals("com.hopetruly.ec.services.ACTION_CATCH_DEVICE")) {
                ScanActivity.this.f2542d.setVisibility(8);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("device");
                String str = ScanActivity.this.f2540a;
                Log.d(str, "Found device [" + bluetoothDevice.getName() + "]");
                if (!ScanActivity.this.m2515a(bluetoothDevice.getAddress())) {
                    String str2 = ScanActivity.this.f2540a;
                    Log.d(str2, "Device [" + bluetoothDevice.getName() + "] add list");
                    HashMap hashMap = new HashMap();
                    hashMap.put("name", bluetoothDevice.getName());
                    hashMap.put("address", bluetoothDevice.getAddress());
                    hashMap.put("rssi", intent.getIntExtra("deviceRSSI", 0) + "db");
                    ScanActivity.this.f2541c.add(hashMap);
                } else {
                    HashMap b = ScanActivity.this.m2518b(bluetoothDevice.getAddress());
                    b.put("rssi", intent.getIntExtra("deviceRSSI", 0) + "db");
                }
                ScanActivity.this.f2563y.notifyDataSetChanged();
                return;
            }
            if (action.equals("com.hopetruly.ec.services.ACTION_BLE_STOP_SCAN")) {
                ScanActivity.this.f2545g = false;
            } else if (action.equals("com.hopetruly.ec.services.ACTION_BLE_START_SCAN")) {
                ScanActivity.this.f2545g = true;
            } else {
                if (action.equals("com.hopetruly.ec.services.ACTION_GATT_CONNECTED")) {
                    if (ScanActivity.this.f2554p != null) {
                        progressDialog = ScanActivity.this.f2554p;
                        resources = ScanActivity.this.getResources();
                        i = R.string.find_service;
                    } else {
                        return;
                    }
                } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                    ScanActivity.this.m2525e();
                    boolean unused = ScanActivity.this.f2556r = false;
                    if (ScanActivity.this.f2560v != null) {
                        ScanActivity.this.f2560v.dismiss();
                    }
                    if (!ScanActivity.this.f2551m) {
                        if (ScanActivity.this.f2552n != null) {
                            ScanActivity.this.f2552n.cancel();
                            ScanActivity.this.f2552n.purge();
                            ScanActivity.this.f2552n = null;
                        }
                        ScanActivity.this.m2531h();
                        boolean unused2 = ScanActivity.this.f2555q = false;
                        return;
                    }
                    return;
                } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED")) {
                    if (ScanActivity.this.f2554p != null) {
                        progressDialog = ScanActivity.this.f2554p;
                        resources = ScanActivity.this.getResources();
                        i = R.string.find_service_finish;
                    } else {
                        return;
                    }
                } else if (action.equals("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_BEGIN")) {
                    if (ScanActivity.this.f2554p != null) {
                        progressDialog = ScanActivity.this.f2554p;
                        resources = ScanActivity.this.getResources();
                        i = R.string.get_device_info;
                    } else {
                        return;
                    }
                } else if (action.equals("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END")) {
                    ScanActivity.this.f2551m = false;
                    ScanActivity.this.m2525e();
                    f2555q = false;
                    Log.d("ScanActivity", "onReceive: f2548j.f2080a.getFwRev(): " + f2548j.f2080a.getFwRev());
                    Log.d("ScanActivity", "onReceive: f2548j.f2083d.mo2693g(): " + f2548j.f2083d.mo2693g());
                    if (f2548j.f2080a.getFwRev().equalsIgnoreCase(f2548j.f2083d.mo2693g())) {
                        ScanActivity.this.finish();
                        return;
                    }
                    f2556r = true;
                    ScanActivity.this.m2532i();
                    return;
                } else if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                    String str3 = ScanActivity.this.f2540a;
                    C0771g.m2787d(str3, "state:" + intExtra);
                    if (intExtra == 13) {
                        ScanActivity.this.f2543e = false;
                        return;
                    } else if (intExtra == 10) {
                        ScanActivity.this.f2543e = false;
                        if (ScanActivity.this.f2549k != null) {
                            ScanActivity.this.f2549k = null;
                            Toast.makeText(ScanActivity.this.getApplicationContext(), ScanActivity.this.getString(R.string.Connecting_bluetooth_outtime), 0).show();
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
                progressDialog.setMessage(resources.getString(i));
                return;
            }
            ScanActivity.this.invalidateOptionsMenu();
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: y */
    public BaseAdapter f2563y = new BaseAdapter() {
        public int getCount() {
            return ScanActivity.this.f2541c.size();
        }

        public Object getItem(int i) {
            return ScanActivity.this.f2541c.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            HashMap hashMap = ScanActivity.this.f2541c.get(i);
            if (view == null) {
                view = LayoutInflater.from(ScanActivity.this.getApplicationContext()).inflate(R.layout.device_lv_item, (ViewGroup) null);
            }
            ((TextView) view.findViewById(R.id.device_name_tv)).setText((CharSequence) hashMap.get("name"));
            ((TextView) view.findViewById(R.id.device_address_tv)).setText((CharSequence) hashMap.get("address"));
            ((TextView) view.findViewById(R.id.device_rssi)).setText((CharSequence) hashMap.get("rssi"));
            return view;
        }
    };

    /* renamed from: z */
    private DialogInterface.OnCancelListener f2564z = new DialogInterface.OnCancelListener() {
        public void onCancel(DialogInterface dialogInterface) {
            if (ScanActivity.this.f2552n != null) {
                ScanActivity.this.f2552n.cancel();
            }
            ScanActivity.this.f2547i.mo2730d();
            boolean unused = ScanActivity.this.f2555q = false;
        }
    };

    /* renamed from: a */
    private void m2510a(int i) {
        this.f2541c.clear();
        this.f2563y.notifyDataSetChanged();
        if (this.f2547i != null) {
            this.f2547i.mo2720a(i);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m2511a() {
        if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            this.f2544f = true;
            BluetoothManager bluetoothManager = (BluetoothManager) getSystemService("bluetooth");
            if (bluetoothManager == null) {
                Log.e(this.f2540a, "Unable to initialize BluetoothManager.");
            }
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            if (adapter == null) {
                Log.e(this.f2540a, "Unable to obtain a BluetoothAdapter.");
            }
            if (!adapter.isEnabled()) {
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 10001);
                return false;
            }
            this.f2543e = true;
            m2510a(this.f2558t);
            return true;
        }
        this.f2544f = false;
        m2527f();
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m2515a(String str) {
        for (int i = 0; i < this.f2541c.size(); i++) {
            if (((String) this.f2541c.get(i).get("address")).equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public HashMap<String, String> m2518b(String str) {
        for (int i = 0; i < this.f2541c.size(); i++) {
            if (((String) this.f2541c.get(i).get("address")).equals(str)) {
                return this.f2541c.get(i);
            }
        }
        return null;
    }

    /* renamed from: b */
    private void m2519b() {
        if (this.f2547i != null) {
            this.f2547i.mo2729c();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2521c() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService("bluetooth");
        if (bluetoothManager == null) {
            Log.e(this.f2540a, "Unable to initialize BluetoothManager.");
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (adapter == null) {
            Log.e(this.f2540a, "Unable to obtain a BluetoothAdapter.");
        }
        if (adapter.isEnabled()) {
            adapter.disable();
        } else {
            adapter.enable();
        }
    }

    /* renamed from: d */
    private void m2524d() {
        if (this.f2554p == null) {
            this.f2554p = new ProgressDialog(this);
        }
        this.f2554p.setMessage(getResources().getString(R.string.Connecting_bluetooth));
        this.f2554p.setCanceledOnTouchOutside(false);
        this.f2554p.setCancelable(true);
        this.f2554p.setOnCancelListener(this.f2564z);
        this.f2554p.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m2525e() {
        if (this.f2554p != null && this.f2554p.isShowing()) {
            this.f2554p.dismiss();
        }
    }

    /* renamed from: f */
    private void m2527f() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.device_unsupport));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ScanActivity.this.finish();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* renamed from: g */
    private void m2529g() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.Need_open_bluetooth));
        builder.setPositiveButton(getResources().getString(R.string.open), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ScanActivity.this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 10001);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public void m2531h() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.Disconnect_and_check));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void m2532i() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.fw_need_update));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ScanActivity.this.finish();
                Intent intent = new Intent();
                intent.putExtra("update", "Auto");
                intent.setClass(ScanActivity.this, FwUpdateActivity.class);
                ScanActivity.this.startActivity(intent);
            }
        });
        builder.setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ScanActivity.this.finish();
            }
        });
        this.f2560v = builder.create();
        this.f2560v.setCanceledOnTouchOutside(false);
        this.f2560v.show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i == 10001) {
                this.f2543e = true;
                m2510a(this.f2558t);
            }
        } else if (i2 == 0 && i == 10001) {
            m2529g();
        }
        super.onActivityResult(i, i2, intent);
    }

    public void onBackPressed() {
        Log.d(this.f2540a, "onBackPressed");
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_scan);
        this.f2548j = (ECGApplication) getApplication();
        this.f2557s = this.f2548j.f2084e.edit();
        ListView listView = (ListView) findViewById(R.id.scan_device_lv);
        this.f2542d = (ProgressBar) findViewById(R.id.search_device_pb);
        listView.setAdapter(this.f2563y);
        listView.setOnItemClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_CATCH_DEVICE");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_BLE_START_SCAN");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_BLE_STOP_SCAN");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_CONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_SERVICES_DISCOVERED");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_BEGIN");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.GET_DEVICE_INFO_END");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2562x, intentFilter);
        bindService(new Intent(this, MainService.class), this.f2561w, Context.BIND_AUTO_CREATE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem findItem;
        Resources resources;
        int i;
        getMenuInflater().inflate(R.menu.scan, menu);
        if (this.f2545g) {
            menu.findItem(R.id.action_pb).setVisible(true);
            findItem = menu.findItem(R.id.action_onoff);
            resources = getResources();
            i = R.string.Stop;
        } else {
            menu.findItem(R.id.action_pb).setVisible(false);
            findItem = menu.findItem(R.id.action_onoff);
            resources = getResources();
            i = R.string.Scan;
        }
        findItem.setTitle(resources.getString(i));
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        m2519b();
        if (this.f2553o != null) {
            this.f2553o.cancel();
            this.f2553o = null;
        }
        if (this.f2552n != null) {
            this.f2552n.cancel();
            this.f2552n.purge();
            this.f2552n = null;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2562x);
        if (this.f2546h) {
            unbindService(this.f2561w);
        }
        super.onDestroy();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.f2547i != null && !this.f2555q) {
            this.f2555q = true;
            HashMap hashMap = this.f2541c.get(i);
            this.f2563y.notifyDataSetChanged();
            this.f2549k = new Machine();
            this.f2549k.setName((String) hashMap.get("name"));
            this.f2549k.setMacAddress((String) hashMap.get("address"));
            this.f2548j.f2080a = this.f2549k;
            this.f2547i.mo2723a((String) hashMap.get("address"));
            this.f2550l = 0;
            m2524d();
            this.f2552n = new Timer();
            this.f2553o = new TimerTask() {
                public void run() {
                    ScanActivity.this.f2551m = true;
                    ScanActivity.this.f2548j.f2080a = null;
                    ScanActivity.this.m2525e();
                    boolean unused = ScanActivity.this.f2555q = false;
                    ScanActivity.this.m2521c();
                }
            };
            this.f2552n.schedule(this.f2553o, (long) this.f2559u);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_ble_onoff) {
            return true;
        } else {
            if (itemId != R.id.action_onoff) {
                return super.onOptionsItemSelected(menuItem);
            }
            if (!this.f2544f) {
                m2527f();
                return true;
            } else if (!this.f2543e) {
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 10001);
                return true;
            } else if (this.f2545g) {
                m2519b();
                return true;
            } else {
                m2510a(this.f2558t);
                return true;
            }
        }
    }
}
