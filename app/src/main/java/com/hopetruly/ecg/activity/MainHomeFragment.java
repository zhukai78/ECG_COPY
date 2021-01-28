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
import com.hopetruly.ecg.sql.SqlManager;


/* renamed from: com.hopetruly.ecg.activity.b */
public class MainHomeFragment extends Fragment {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final String TAG = "b";
    /* access modifiers changed from: private */

    /* renamed from: b */
    public Resources str_res;

    /* renamed from: c */
    private RelativeLayout rl_ecg_rec_list_btn;

    /* renamed from: d */
    private RelativeLayout rl_dev_status;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public ImageView iv_ecg_start;
    /* access modifiers changed from: private */

    /* renamed from: f */
//    public ImageView iv_step_start;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public ImageView iv_ecg_conn_btn;

    /* renamed from: h */
    private TextView tv_home_rec_num;
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
    private boolean isdestroy = true;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public FunChooseActivity mFunChooseActivity = null;

    /* renamed from: n */
    private BroadcastReceiver updataUiBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                MainHomeFragment.this.tv_dev_bar_title.setText(MainHomeFragment.this.str_res.getString(R.string.l_scan_dev));
                MainHomeFragment.this.tv_home_dev_status.setText(MainHomeFragment.this.str_res.getString(R.string.l_status_disconnect));
                MainHomeFragment.this.iv_dev_icon_img.setImageResource(R.drawable.dev_note);
                MainHomeFragment.this.iv_ecg_conn_btn.setVisibility(View.VISIBLE);
                MainHomeFragment.this.iv_ecg_start.setVisibility(View.GONE);
                //                MainHomeFragment.this.iv_step_start.setVisibility(View.GONE);
            } else if (action.equals("com.hopetruly.ec.services.BATTERY")) {
                MainHomeFragment.this.assertBattery(intent.getIntExtra("com.hopetruly.ec.services.BATTERY",0));
            }
        }
    };

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void assertBattery(int i) {
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
        if (!this.isdestroy) {
            this.isdestroy = true;
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.updataUiBroadcastReceiver);
        }
        super.onDestroy();
    }

    public void onStart() {
        Log.d(TAG, "onStart");
        this.isdestroy = false;
        this.mFunChooseActivity = (FunChooseActivity) getActivity();
        int d = new SqlManager(getActivity()).rawQueryEcgRecord(((ECGApplication) getActivity().getApplication()).mUserInfo.getId());
        this.tv_home_rec_num = (TextView) getView().findViewById(R.id.home_rec_num);
        this.tv_home_rec_num.setText(String.valueOf(d));
        this.rl_ecg_rec_list_btn = (RelativeLayout) getView().findViewById(R.id.ecg_rec_list_btn);
        this.rl_ecg_rec_list_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainHomeFragment.this.mFunChooseActivity.startActivity(new Intent(MainHomeFragment.this.mFunChooseActivity, EcgRecListActivity.class));
            }
        });
        this.str_res = getView().getResources();
        this.tv_dev_bar_title = (TextView) getView().findViewById(R.id.dev_bar_title);
        this.tv_home_dev_status = (TextView) getView().findViewById(R.id.home_dev_status);
        this.iv_dev_icon_img = (ImageView) getView().findViewById(R.id.dev_icon_img);
        if (this.mFunChooseActivity.isBleConn()) {
            this.tv_dev_bar_title.setText(this.str_res.getString(R.string.l_close_dev));
            this.tv_home_dev_status.setText(this.str_res.getString(R.string.l_status_connected));
            assertBattery(this.mFunChooseActivity.getgetbatterylevel());
        } else {
            this.tv_dev_bar_title.setText(this.str_res.getString(R.string.l_scan_dev));
            this.tv_home_dev_status.setText(this.str_res.getString(R.string.l_status_disconnect));
            this.iv_dev_icon_img.setImageResource(R.drawable.dev_note);
        }
        this.rl_dev_status = (RelativeLayout) getView().findViewById(R.id.dev_status);
        this.rl_dev_status.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainHomeFragment.this.mFunChooseActivity.isBleConn()) {
                    MainHomeFragment.this.mFunChooseActivity.showdisconnectedBleDialog();
                    return;
                }
                MainHomeFragment.this.mFunChooseActivity.startActivity(new Intent(MainHomeFragment.this.mFunChooseActivity, ScanActivity.class));
            }
        });
        this.iv_ecg_conn_btn = (ImageView) getView().findViewById(R.id.ecg_conn_btn);
        this.iv_ecg_conn_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!MainHomeFragment.this.mFunChooseActivity.isBleConn()) {
                    MainHomeFragment.this.startActivity(new Intent(MainHomeFragment.this.mFunChooseActivity, ScanActivity.class));
                }
            }
        });
        this.iv_ecg_start = (ImageView) getView().findViewById(R.id.ecg_start);
        this.iv_ecg_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainHomeFragment.this.mFunChooseActivity.isBleConn()) {
                    if (MainHomeFragment.this.mFunChooseActivity.fcECGApplication != null && MainHomeFragment.this.mFunChooseActivity.fcECGApplication.appMainService != null && MainHomeFragment.this.mFunChooseActivity.fcECGApplication.appMainService.getisGattStop() && !mFunChooseActivity.fcECGApplication.appMainService.stopPedometerRecord()) {
                        Log.e(MainHomeFragment.TAG, "stopStep failed..");
                    }
                    MainHomeFragment.this.mFunChooseActivity.startActivity(new Intent(MainHomeFragment.this.mFunChooseActivity, EcgTypeSelectActivity.class));
                }
            }
        });
//        this.iv_step_start = (ImageView) getView().findViewById(R.id.step_start);
//        this.iv_step_start.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (MainHomeFragment.this.mFunChooseActivity.isBleConn()) {
//                    MainHomeFragment.this.mFunChooseActivity.fcECGApplication.appPedometerConf.mo2673b(1);
////                    ((RadioButton) MainHomeFragment.this.mFunChooseActivity.findViewById(R.id.nav_step)).setChecked(true);
//                }
//            }
//        });
        if (this.mFunChooseActivity.isBleConn()) {
            this.iv_ecg_conn_btn.setVisibility(View.GONE);
            this.iv_ecg_start.setVisibility(View.VISIBLE);
//            this.iv_step_start.setVisibility(View.VISIBLE);
        } else {
            this.iv_ecg_conn_btn.setVisibility(View.VISIBLE);
            this.iv_ecg_start.setVisibility(View.GONE);
//            this.iv_step_start.setVisibility(View.GONE);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        intentFilter.addAction("com.hopetruly.ec.services.BATTERY");
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.updataUiBroadcastReceiver, intentFilter);
        super.onStart();
    }
}
