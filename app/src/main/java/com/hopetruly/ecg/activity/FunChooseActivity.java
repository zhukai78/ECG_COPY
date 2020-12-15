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
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.part.net.NetService;
import com.warick.p025a.GpsManagerHelper;


public class FunChooseActivity extends BaseActivity {

    /* renamed from: a */
    MainService fcMainService;

    /* renamed from: c */
    boolean isdisconnectedBle = false;

    /* renamed from: d */
    boolean isexitApp = false;

    /* renamed from: e */
    boolean ispause = false;

    /* renamed from: f */
    boolean isGpsOpen = false;

    /* renamed from: g */
    boolean isResumeGps = false;

    /* renamed from: h */
    ECGApplication fcECGApplication;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public NetService fcNetService;

    /* renamed from: j */
    private RadioGroup rg_nav = null;

    /* renamed from: k */
    private AlertDialog gpsAlertDialog = null;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public AlertDialog powerlowDialog = null;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public MainHomeFragment mainHomeFragment = new MainHomeFragment();
    /* access modifiers changed from: private */

    /* renamed from: n */
    public StepFragment stepFragment = new StepFragment();
    /* access modifiers changed from: private */

    /* renamed from: o */
    public SettingFragment settingFragment = new SettingFragment();
    /* access modifiers changed from: private */

    /* renamed from: p */
    public AboutEcgFragment aboutEcgFragment = new AboutEcgFragment();

    /* renamed from: q */
    private ServiceConnection fcMainServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            FunChooseActivity.this.fcMainService = ((MainService.MainBinder) iBinder).getMainBinder();
            FunChooseActivity.this.fcECGApplication.appMainService = FunChooseActivity.this.fcMainService;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            FunChooseActivity.this.fcMainService = null;
        }
    };

    /* renamed from: r */
    private ServiceConnection fcNetServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NetService unused = FunChooseActivity.this.fcNetService = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetService unused = FunChooseActivity.this.fcNetService = null;
        }
    };

    /* renamed from: s */
    private BroadcastReceiver fcBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                LogUtils.logE("FunChooseActivity", "wantExit:" + FunChooseActivity.this.isexitApp + "  wantScan:" + FunChooseActivity.this.isdisconnectedBle);
                if (!FunChooseActivity.this.isexitApp && !FunChooseActivity.this.isdisconnectedBle) {
                    FunChooseActivity.this.showDevice_disconnectedDialog();
                }
                if (FunChooseActivity.this.isdisconnectedBle) {
                    FunChooseActivity.this.isdisconnectedBle = false;
                }
            } else if (action.equals("com.hopetruly.ecg.services.MainService.POWER_LOW")) {
                if (FunChooseActivity.this.powerlowDialog != null) {
                    FunChooseActivity.this.powerlowDialog.dismiss();
                }
                FunChooseActivity.this.showPowerlowDialog();
            }
        }
    };

    /* renamed from: t */
    private RadioGroup.OnCheckedChangeListener rgCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            Fragment fragment = new Fragment();
            FragmentTransaction beginTransaction = FunChooseActivity.this.getFragmentManager().beginTransaction();
            switch (i) {
                case R.id.nav_about_ecg /*2131165369*/:
                    fragment = aboutEcgFragment;
                    break;
                case R.id.nav_home /*2131165370*/:
                    fragment = mainHomeFragment;
                    break;
                case R.id.nav_set /*2131165371*/:
                    fragment = settingFragment;
                    break;
                case R.id.nav_step /*2131165372*/:
                    fragment = stepFragment;
                    break;
                default:
                    beginTransaction.commit();
            }
            beginTransaction.replace(R.id.id_content, fragment);
            beginTransaction.commit();
        }
    };

    /* renamed from: f */
    private void showExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exit_title));
        builder.setMessage(getString(R.string.exit_msg));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FunChooseActivity.this.isexitApp = true;
                FunChooseActivity.this.finish();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FunChooseActivity.this.isexitApp = false;
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void showDevice_disconnectedDialog() {
        if (this.ispause) {
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
    public boolean isBleConn() {
        return this.fcMainService != null && this.fcMainService.isMBleConn();
    }

    /* renamed from: b */
    public int getgetbatterylevel() {
        if (this.fcMainService != null) {
            return this.fcMainService.getbatterylevel();
        }
        return 0;
    }

    /* renamed from: c */
    public void showdisconnectedBleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.ble_will_disconnected));
        builder.setPositiveButton(getResources().getString(R.string.l_disconnect), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                FunChooseActivity.this.isdisconnectedBle = true;
                FunChooseActivity.this.fcMainService.disconnectMainBLE();
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
    public void showPowerlowDialog() {
        String str;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        int batteryLevel = this.fcECGApplication.appMachine.getBatteryLevel();
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
        powerlowDialog = builder.create();
        powerlowDialog.show();
    }

    /* renamed from: e */
    public void showGpsAlertDialog() {
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
                GpsManagerHelper.removegps();
                FunChooseActivity.this.isGpsOpen = true;
                if (FunChooseActivity.this.isResumeGps) {
                    GpsManagerHelper.initGpsManagerHelper(FunChooseActivity.this.getApplicationContext());
                }
                dialogInterface.dismiss();
            }
        });
        this.gpsAlertDialog = builder.create();
        this.gpsAlertDialog.setCanceledOnTouchOutside(false);
        this.gpsAlertDialog.setCancelable(false);
        this.gpsAlertDialog.show();
    }

    public void onBackPressed() {
        showExitAlertDialog();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_fun_choose);
        this.fcECGApplication = (ECGApplication) getApplication();
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.id_content, this.mainHomeFragment);
        beginTransaction.commit();
        TabChoice.selectRadioButton(findViewById(R.id.nav_home), 0.6f);
        TabChoice.selectRadioButton(findViewById(R.id.nav_step), 0.6f);
        TabChoice.selectRadioButton(findViewById(R.id.nav_about_ecg), 0.6f);
        TabChoice.selectRadioButton(findViewById(R.id.nav_set), 0.6f);
        this.rg_nav = (RadioGroup) findViewById(R.id.nav);
        this.rg_nav.setOnCheckedChangeListener(rgCheckedChangeListener);
        Intent intent = new Intent(this, MainService.class);
        startService(intent);
        bindService(intent, this.fcMainServiceConn, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, NetService.class), this.fcNetServiceConn, 1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.POWER_LOW");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.fcBroadcastReceiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LogUtils.logI("FunChooseActivity", "onDestroy~~~~~");
        if (this.fcMainService != null) {
            this.fcMainService.exitService();
        }
        unbindService(this.fcMainServiceConn);
        unbindService(this.fcNetServiceConn);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.fcBroadcastReceiver);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.ispause = false;
        if (this.gpsAlertDialog != null) {
            this.gpsAlertDialog.dismiss();
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        this.ispause = true;
        if (!this.isGpsOpen) {
            if (!GpsManagerHelper.initGpsManagerHelper(getApplicationContext())) {
                showGpsAlertDialog();
            } else {
                this.isResumeGps = true;
            }
        }
        super.onResume();
    }
}
