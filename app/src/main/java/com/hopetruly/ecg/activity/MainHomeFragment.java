package com.hopetruly.ecg.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.p022b.SqlManager;


/* renamed from: com.hopetruly.ecg.activity.b */
public class MainHomeFragment extends Fragment {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final String TAG = "b";
    /* access modifiers changed from: private */

    /* renamed from: b */
    public Resources f2693b;

    /* renamed from: c */
    private RelativeLayout rl_ecg_rec_list_btn;

    /* renamed from: d */
    private RelativeLayout rl_dev_status;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public ImageView f2696e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public ImageView f2697f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public ImageView f2698g;

    /* renamed from: h */
    private TextView f2699h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public TextView tv_dev_bar_title;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public TextView tv_home_dev_status;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public ImageView iv_dev_icon_img;

    /* renamed from: l */
    private boolean f2703l = true;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public FunChooseActivity f2704m = null;

    /* renamed from: n */
    private BroadcastReceiver f2705n = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                MainHomeFragment.this.tv_dev_bar_title.setText(MainHomeFragment.this.f2693b.getString(R.string.l_scan_dev));
                MainHomeFragment.this.tv_home_dev_status.setText(MainHomeFragment.this.f2693b.getString(R.string.l_status_disconnect));
                MainHomeFragment.this.iv_dev_icon_img.setImageResource(R.drawable.dev_note);
                MainHomeFragment.this.f2698g.setVisibility(View.VISIBLE);
                MainHomeFragment.this.f2696e.setVisibility(View.GONE);
                MainHomeFragment.this.f2697f.setVisibility(View.GONE);
            } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY") && intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(Sensor.BATTERY.getData().toString())) {
                MainHomeFragment.this.m2567a(Sensor.BATTERY.convertBAT(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA")));
            }
        }
    };

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2567a(int i) {
        ImageView imageView;
        int i2;
        if (i > 80) {
            imageView = this.iv_dev_icon_img;
            i2 = R.drawable.icon_power_1;
        } else if (i > 60) {
            imageView = this.iv_dev_icon_img;
            i2 = R.drawable.icon_power_2;
        } else if (i > 40) {
            imageView = this.iv_dev_icon_img;
            i2 = R.drawable.icon_power_3;
        } else if (i > 20) {
            imageView = this.iv_dev_icon_img;
            i2 = R.drawable.icon_power_4;
        } else if (i > 0) {
            imageView = this.iv_dev_icon_img;
            i2 = R.drawable.icon_power_5;
        } else {
            imageView = this.iv_dev_icon_img;
            i2 = R.drawable.icon_power_6;
        }
        imageView.setImageResource(i2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_ecg_main, viewGroup, false);
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (!this.f2703l) {
            this.f2703l = true;
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.f2705n);
        }
        super.onDestroy();
    }

    public void onStart() {
        Log.d(TAG, "onStart");
        this.f2703l = false;
        this.f2704m = (FunChooseActivity) getActivity();
        int d = new SqlManager(getActivity()).rawQueryEcgRecord(((ECGApplication) getActivity().getApplication()).mUserInfo.getId());
        this.f2699h = (TextView) getView().findViewById(R.id.home_rec_num);
        this.f2699h.setText(String.valueOf(d));
        this.rl_ecg_rec_list_btn = (RelativeLayout) getView().findViewById(R.id.ecg_rec_list_btn);
        this.rl_ecg_rec_list_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainHomeFragment.this.f2704m.startActivity(new Intent(MainHomeFragment.this.f2704m, EcgRecListActivity.class));
            }
        });
        this.f2693b = getView().getResources();
        this.tv_dev_bar_title = (TextView) getView().findViewById(R.id.dev_bar_title);
        this.tv_home_dev_status = (TextView) getView().findViewById(R.id.home_dev_status);
        this.iv_dev_icon_img = (ImageView) getView().findViewById(R.id.dev_icon_img);
        if (this.f2704m.mo2181a()) {
            this.tv_dev_bar_title.setText(this.f2693b.getString(R.string.l_close_dev));
            this.tv_home_dev_status.setText(this.f2693b.getString(R.string.l_status_connected));
            m2567a(this.f2704m.mo2182b());
        } else {
            this.tv_dev_bar_title.setText(this.f2693b.getString(R.string.l_scan_dev));
            this.tv_home_dev_status.setText(this.f2693b.getString(R.string.l_status_disconnect));
            this.iv_dev_icon_img.setImageResource(R.drawable.dev_note);
        }
        this.rl_dev_status = (RelativeLayout) getView().findViewById(R.id.dev_status);
        this.rl_dev_status.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainHomeFragment.this.f2704m.mo2181a()) {
                    MainHomeFragment.this.f2704m.showdisconnectedBleDialog();
                    return;
                }
                MainHomeFragment.this.f2704m.startActivity(new Intent(MainHomeFragment.this.f2704m, ScanActivity.class));
            }
        });
        this.f2698g = (ImageView) getView().findViewById(R.id.ecg_conn_btn);
        this.f2698g.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!MainHomeFragment.this.f2704m.mo2181a()) {
                    MainHomeFragment.this.startActivity(new Intent(MainHomeFragment.this.f2704m, ScanActivity.class));
                }
            }
        });
        this.f2696e = (ImageView) getView().findViewById(R.id.ecg_start);
        this.f2696e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainHomeFragment.this.f2704m.mo2181a()) {
                    if (MainHomeFragment.this.f2704m.fcECGApplication != null && MainHomeFragment.this.f2704m.fcECGApplication.appMainService != null && MainHomeFragment.this.f2704m.fcECGApplication.appMainService.mo2741o() && !MainHomeFragment.this.f2704m.fcECGApplication.appMainService.mo2739m()) {
                        Log.e(MainHomeFragment.TAG, "stopStep failed..");
                    }
                    MainHomeFragment.this.f2704m.startActivity(new Intent(MainHomeFragment.this.f2704m, EcgTypeSelectActivity.class));
                }
            }
        });
        this.f2697f = (ImageView) getView().findViewById(R.id.step_start);
        this.f2697f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainHomeFragment.this.f2704m.mo2181a()) {
                    MainHomeFragment.this.f2704m.fcECGApplication.appPedometerConf.mo2673b(1);
                    ((RadioButton) MainHomeFragment.this.f2704m.findViewById(R.id.nav_step)).setChecked(true);
                }
            }
        });
        if (this.f2704m.mo2181a()) {
            this.f2698g.setVisibility(View.GONE);
            this.f2696e.setVisibility(View.VISIBLE);
            this.f2697f.setVisibility(View.VISIBLE);
        } else {
            this.f2698g.setVisibility(View.VISIBLE);
            this.f2696e.setVisibility(View.GONE);
            this.f2697f.setVisibility(View.GONE);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.f2705n, intentFilter);
        super.onStart();
    }
}
