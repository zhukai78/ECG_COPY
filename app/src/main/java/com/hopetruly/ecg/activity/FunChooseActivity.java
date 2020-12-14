package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RadioGroup;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.C0771g;
import com.hopetruly.part.net.NetService;
import com.warick.p025a.C0801d;


public class FunChooseActivity extends C0721a {

    /* renamed from: a */
    MainService f2215a;

    /* renamed from: c */
    boolean f2216c = false;

    /* renamed from: d */
    boolean f2217d = false;

    /* renamed from: e */
    boolean f2218e = false;

    /* renamed from: f */
    boolean f2219f = false;

    /* renamed from: g */
    boolean f2220g = false;

    /* renamed from: h */
    ECGApplication f2221h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public NetService f2222i;

    /* renamed from: j */
    private RadioGroup f2223j = null;

    /* renamed from: k */
    private AlertDialog f2224k = null;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public AlertDialog f2225l = null;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public C0723b f2226m = new C0723b();
    /* access modifiers changed from: private */

    /* renamed from: n */
    public StepFragment f2227n = new StepFragment();
    /* access modifiers changed from: private */

    /* renamed from: o */
    public SettingFragment f2228o = new SettingFragment();
    /* access modifiers changed from: private */

    /* renamed from: p */
    public C0730c f2229p = new C0730c();

    /* renamed from: q */
    private ServiceConnection f2230q = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            FunChooseActivity.this.f2215a = ((MainService.C0762a) iBinder).mo2756a();
            FunChooseActivity.this.f2221h.f2094o = FunChooseActivity.this.f2215a;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            FunChooseActivity.this.f2215a = null;
        }
    };

    /* renamed from: r */
    private ServiceConnection f2231r = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NetService unused = FunChooseActivity.this.f2222i = ((NetService.C0786c) iBinder).mo2852a();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetService unused = FunChooseActivity.this.f2222i = null;
        }
    };

    /* renamed from: s */
    private BroadcastReceiver f2232s = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                C0771g.m2787d("FunChooseActivity", "wantExit:" + FunChooseActivity.this.f2217d + "  wantScan:" + FunChooseActivity.this.f2216c);
                if (!FunChooseActivity.this.f2217d && !FunChooseActivity.this.f2216c) {
                    FunChooseActivity.this.m2297g();
                }
                if (FunChooseActivity.this.f2216c) {
                    FunChooseActivity.this.f2216c = false;
                }
            } else if (action.equals("com.hopetruly.ecg.services.MainService.POWER_LOW")) {
                if (FunChooseActivity.this.f2225l != null) {
                    FunChooseActivity.this.f2225l.dismiss();
                }
                FunChooseActivity.this.mo2184d();
            }
        }
    };

    /* renamed from: t */
    private RadioGroup.OnCheckedChangeListener f2233t = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            Fragment fragment = new Fragment();
            FragmentTransaction beginTransaction = FunChooseActivity.this.getFragmentManager().beginTransaction();
            switch (i) {
                case R.id.nav_about_ecg /*2131165369*/:
                    fragment = f2229p;
                    break;
                case R.id.nav_home /*2131165370*/:
                    fragment = f2226m;
                    break;
                case R.id.nav_set /*2131165371*/:
                    fragment = f2228o;
                    break;
                case R.id.nav_step /*2131165372*/:
                    fragment = f2227n;
                    break;
                default:
                    beginTransaction.commit();
            }
            beginTransaction.replace(R.id.id_content, fragment);
            beginTransaction.commit();
        }
    };

    /* renamed from: f */
    private void m2296f() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exit_title));
        builder.setMessage(getString(R.string.exit_msg));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FunChooseActivity.this.f2217d = true;
                FunChooseActivity.this.finish();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FunChooseActivity.this.f2217d = false;
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m2297g() {
        if (this.f2218e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.Tip));
            builder.setMessage(getResources().getString(R.string.device_disconnected));
            builder.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            create.show();
        }
    }

    /* renamed from: a */
    public boolean mo2181a() {
        return this.f2215a != null && this.f2215a.mo2728b();
    }

    /* renamed from: b */
    public int mo2182b() {
        if (this.f2215a != null) {
            return this.f2215a.mo2750u();
        }
        return 0;
    }

    /* renamed from: c */
    public void mo2183c() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.ble_will_disconnected));
        builder.setPositiveButton(getResources().getString(R.string.l_disconnect), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FunChooseActivity.this.f2216c = true;
                FunChooseActivity.this.f2215a.mo2730d();
                dialogInterface.dismiss();
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

    /* renamed from: d */
    public void mo2184d() {
        String str;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        int batteryLevel = this.f2221h.f2080a.getBatteryLevel();
        if (batteryLevel == 0) {
            str = getResources().getString(R.string.no_power);
        } else if (batteryLevel <= 0 || batteryLevel >= 10) {
            str = getResources().getString(R.string.Power_low) + "20%";
        } else {
            str = getResources().getString(R.string.Power_low) + batteryLevel + "%";
        }
        builder.setMessage(str);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        this.f2225l = builder.create();
        this.f2225l.show();
    }

    /* renamed from: e */
    public void mo2185e() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.d_open_gps_title));
        builder.setMessage(getResources().getString(R.string.d_open_gps_prompt));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                if (intent.resolveActivity(FunChooseActivity.this.getPackageManager()) != null) {
                    FunChooseActivity.this.startActivityForResult(intent, 0);
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                C0801d.m2915d();
                FunChooseActivity.this.f2219f = true;
                if (FunChooseActivity.this.f2220g) {
                    C0801d.m2910a(FunChooseActivity.this.getApplicationContext());
                }
                dialogInterface.dismiss();
            }
        });
        this.f2224k = builder.create();
        this.f2224k.setCanceledOnTouchOutside(false);
        this.f2224k.setCancelable(false);
        this.f2224k.show();
    }

    public void onBackPressed() {
        m2296f();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_fun_choose);
        this.f2221h = (ECGApplication) getApplication();
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.id_content, this.f2226m);
        beginTransaction.commit();
        C0734d.m2582a(findViewById(R.id.nav_home), 0.6f);
        C0734d.m2582a(findViewById(R.id.nav_step), 0.6f);
        C0734d.m2582a(findViewById(R.id.nav_about_ecg), 0.6f);
        C0734d.m2582a(findViewById(R.id.nav_set), 0.6f);
        this.f2223j = (RadioGroup) findViewById(R.id.nav);
        this.f2223j.setOnCheckedChangeListener(this.f2233t);
        Intent intent = new Intent(this, MainService.class);
        startService(intent);
        bindService(intent, this.f2230q, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, NetService.class), this.f2231r, 1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.POWER_LOW");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2232s, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        C0771g.m2784a("FunChooseActivity", "onDestroy~~~~~");
        if (this.f2215a != null) {
            this.f2215a.mo2719a();
        }
        unbindService(this.f2230q);
        unbindService(this.f2231r);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2232s);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.f2218e = false;
        if (this.f2224k != null) {
            this.f2224k.dismiss();
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.f2218e = true;
        if (!this.f2219f) {
            if (!C0801d.m2910a(getApplicationContext())) {
                mo2185e();
            } else {
                this.f2220g = true;
            }
        }
        super.onResume();
    }
}
