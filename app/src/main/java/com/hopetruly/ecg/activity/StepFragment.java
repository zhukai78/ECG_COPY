package com.hopetruly.ecg.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.device.Sensor;
import com.hopetruly.ecg.entity.PedometerRecord;
import com.hopetruly.ecg.p022b.C0740b;

import java.util.Calendar;

public class StepFragment extends Fragment {

    /* renamed from: a */
    final String f2647a = "StepFragment";

    /* renamed from: b */
    TextView f2648b;

    /* renamed from: c */
    TextView f2649c;

    /* renamed from: d */
    TextView f2650d;

    /* renamed from: e */
    TextView f2651e;

    /* renamed from: f */
    TextView f2652f;

    /* renamed from: g */
    ECGApplication f2653g;

    /* renamed from: h */
    PedometerRecord f2654h;

    /* renamed from: i */
    private RelativeLayout f2655i;

    /* renamed from: j */
    private RelativeLayout f2656j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public TextView f2657k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public boolean f2658l;

    /* renamed from: m */
    private boolean f2659m = true;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public boolean f2660n;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public FunChooseActivity f2661o;

    /* renamed from: p */
    private BroadcastReceiver f2662p = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY")) {
                if (intent.getStringExtra("com.hopetruly.ec.services.EXTRA_UUID").equals(Sensor.ACCELEROMETER.getData().toString())) {
                    StepFragment.this.m2548a(intent.getByteArrayExtra("com.hopetruly.ec.services.EXTRA_DATA"));
                }
            } else if (action.equals("com.hopetruly.ecg.services.MainService.REFRESH_STEP")) {
                if (StepFragment.this.f2653g != null || StepFragment.this.f2653g.f2094o != null) {
                    StepFragment.this.f2654h = StepFragment.this.f2653g.f2094o.mo2745p();
                    if (StepFragment.this.f2654h != null) {
                        StepFragment.this.f2648b.setText(String.valueOf(StepFragment.this.f2654h.getCurStep()));
                        StepFragment.this.f2652f.setText(String.valueOf(StepFragment.this.f2654h.getCount() + StepFragment.this.f2654h.getCurStep()));
                        if (StepFragment.this.f2654h.getTarget() > 0) {
                            StepFragment.this.f2650d.setText(String.valueOf(StepFragment.this.f2654h.getTarget()));
                            StepFragment.this.f2651e.setText(String.valueOf((int) ((StepFragment.this.f2654h.getCount() * 100) / StepFragment.this.f2654h.getTarget())));
                        }
                    }
                }
            } else if (action.equals("com.hopetruly.ecg.services.MainService.REFRESH_CAL")) {
                if (StepFragment.this.f2653g != null || StepFragment.this.f2653g.f2094o != null) {
                    StepFragment.this.f2654h = StepFragment.this.f2653g.f2094o.mo2745p();
                    if (StepFragment.this.f2654h != null) {
                        StepFragment.this.f2649c.setText(String.valueOf((int) StepFragment.this.f2654h.getCal()));
                    }
                }
            } else if (action.equals("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED")) {
                boolean unused = StepFragment.this.f2658l = false;
                boolean unused2 = StepFragment.this.f2660n = false;
                try {
                    StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_connect_dev));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0162  */
    /* renamed from: a */
    private void m2546a() {
        int i = 0;
        TextView textView;
        TextView textView2;
        long b = 0;
        this.f2661o = (FunChooseActivity) getActivity();
        Calendar instance = Calendar.getInstance();
        this.f2654h = new C0740b(getActivity().getApplicationContext()).mo2466a(this.f2653g.f2081b.getId(), instance.get(1), instance.get(2) + 1, instance.get(5));
        this.f2648b = (TextView) getView().findViewById(R.id.step_step);
        this.f2649c = (TextView) getView().findViewById(R.id.step_calories);
        this.f2651e = (TextView) getView().findViewById(R.id.step_complete);
        this.f2652f = (TextView) getView().findViewById(R.id.step_taday_total);
        this.f2650d = (TextView) getView().findViewById(R.id.step_target);
        if (this.f2654h != null) {
            this.f2652f.setText(String.valueOf(this.f2654h.getCount()));
            this.f2648b.setText(String.valueOf(this.f2654h.getCurStep()));
            this.f2649c.setText(String.valueOf(this.f2654h.getCal()));
            if (this.f2654h.getTarget() > 0) {
                this.f2651e.setText(String.valueOf((int) ((this.f2654h.getCount() * 100) / this.f2654h.getTarget())));
                textView2 = this.f2650d;
                b = this.f2654h.getTarget();
            }
            this.f2660n = this.f2653g.f2094o.mo2728b();
            this.f2658l = this.f2653g.f2094o.mo2741o();
            this.f2657k = (TextView) getView().findViewById(R.id.step_start_l);
            this.f2655i = (RelativeLayout) getView().findViewById(R.id.step_start);
            if (!this.f2660n) {
                textView = this.f2657k;
                i = R.string.l_connect_dev;
            } else if (this.f2658l) {
                textView = this.f2657k;
                i = R.string.l_step_stop;
            } else {
                textView = this.f2657k;
                i = R.string.l_step_start;
            }
            textView.setText(getString(i));
            this.f2655i.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (!StepFragment.this.f2660n) {
                        StepFragment.this.f2661o.startActivity(new Intent(StepFragment.this.f2661o, ScanActivity.class));
                    } else if (StepFragment.this.f2658l) {
                        if (StepFragment.this.f2653g.f2094o.mo2739m()) {
                            boolean unused = StepFragment.this.f2658l = false;
                            StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_step_start));
                            StepFragment.this.f2653g.f2087h.mo2673b(0);
                        }
                    } else if (StepFragment.this.f2653g.f2094o.mo2738l()) {
                        StepFragment.this.f2648b.setText("0");
                        boolean unused2 = StepFragment.this.f2658l = true;
                        StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_step_stop));
                    }
                }
            });
            this.f2656j = (RelativeLayout) getView().findViewById(R.id.step_view_history);
            this.f2656j.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    StepFragment.this.f2661o.startActivity(new Intent(StepFragment.this.f2661o, StepRECActivity.class));
                }
            });
        }
        this.f2652f.setText("0");
        this.f2648b.setText("0");
        this.f2651e.setText("0");
        this.f2649c.setText("0");
        if (this.f2653g.f2087h.mo2672b() > 0) {
            textView2 = this.f2650d;
            b = this.f2653g.f2087h.mo2672b();
        }
        this.f2660n = this.f2653g.f2094o.mo2728b();
        this.f2658l = this.f2653g.f2094o.mo2741o();
        this.f2657k = (TextView) getView().findViewById(R.id.step_start_l);
        this.f2655i = (RelativeLayout) getView().findViewById(R.id.step_start);
        if (!this.f2660n) {
            i = R.string.l_connect_dev;
        }
        f2657k.setText(getString(i));
        this.f2655i.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!StepFragment.this.f2660n) {
                    StepFragment.this.f2661o.startActivity(new Intent(StepFragment.this.f2661o, ScanActivity.class));
                } else if (StepFragment.this.f2658l) {
                    if (StepFragment.this.f2653g.f2094o.mo2739m()) {
                        boolean unused = StepFragment.this.f2658l = false;
                        StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_step_start));
                        StepFragment.this.f2653g.f2087h.mo2673b(0);
                    }
                } else if (StepFragment.this.f2653g.f2094o.mo2738l()) {
                    StepFragment.this.f2648b.setText("0");
                    boolean unused2 = StepFragment.this.f2658l = true;
                    StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_step_stop));
                }
            }
        });
        this.f2656j = (RelativeLayout) getView().findViewById(R.id.step_view_history);
        this.f2656j.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StepFragment.this.f2661o.startActivity(new Intent(StepFragment.this.f2661o, StepRECActivity.class));
            }
        });
        f2650d.setText(String.valueOf(b));
        this.f2660n = this.f2653g.f2094o.mo2728b();
        this.f2658l = this.f2653g.f2094o.mo2741o();
        this.f2657k = (TextView) getView().findViewById(R.id.step_start_l);
        this.f2655i = (RelativeLayout) getView().findViewById(R.id.step_start);
        if (!this.f2660n) {
            i = R.string.l_connect_dev;
        }
        f2657k.setText(getString(i));
        this.f2655i.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!StepFragment.this.f2660n) {
                    StepFragment.this.f2661o.startActivity(new Intent(StepFragment.this.f2661o, ScanActivity.class));
                } else if (StepFragment.this.f2658l) {
                    if (StepFragment.this.f2653g.f2094o.mo2739m()) {
                        boolean unused = StepFragment.this.f2658l = false;
                        StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_step_start));
                        StepFragment.this.f2653g.f2087h.mo2673b(0);
                    }
                } else if (StepFragment.this.f2653g.f2094o.mo2738l()) {
                    StepFragment.this.f2648b.setText("0");
                    boolean unused2 = StepFragment.this.f2658l = true;
                    StepFragment.this.f2657k.setText(StepFragment.this.getString(R.string.l_step_stop));
                }
            }
        });
        this.f2656j = (RelativeLayout) getView().findViewById(R.id.step_view_history);
        this.f2656j.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StepFragment.this.f2661o.startActivity(new Intent(StepFragment.this.f2661o, StepRECActivity.class));
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2548a(byte[] bArr) {
        float f = (((float) (((double) bArr[0]) / 16.0d)) * 1000.0f) / 5.0f;
        float f2 = (((float) (((double) bArr[1]) / 16.0d)) * 1000.0f) / 5.0f;
        float f3 = (((float) (((double) (-1 * bArr[2])) / 16.0d)) * 1000.0f) / 5.0f;
        Math.sqrt((double) ((f * f) + (f2 * f2) + (f3 * f3)));
    }

    public void onCreate(Bundle bundle) {
        setHasOptionsMenu(true);
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DISCONNECTED");
        intentFilter.addAction("com.hopetruly.ec.services.ACTION_GATT_DATA_NOTIFY");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.REFRESH_STEP");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.REFRESH_CAL");
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.f2662p, intentFilter);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        getActivity().getMenuInflater().inflate(R.menu.step, menu);
        menu.findItem(R.id.action_reset_step).setTitle("Reset");
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_step, viewGroup, false);
    }

    public void onDestroy() {
        if (!this.f2659m) {
            this.f2659m = true;
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.f2662p);
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_reset_step) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.f2654h == null) {
            return true;
        }
        this.f2654h.setCount(0);
        this.f2654h.setCal(0);
        this.f2648b.setText(String.valueOf(this.f2654h.getCount()));
        this.f2649c.setText(String.valueOf(this.f2654h.getCal()));
        return true;
    }

    public void onStart() {
        super.onStart();
        this.f2659m = false;
        this.f2653g = (ECGApplication) getActivity().getApplication();
        if (this.f2653g != null && this.f2653g.f2094o != null) {
            m2546a();
            if (this.f2653g.f2087h.mo2674c() == 1) {
                this.f2653g.f2087h.mo2673b(0);
                if (!this.f2658l && this.f2653g.f2094o.mo2738l()) {
                    this.f2648b.setText("0");
                    this.f2658l = true;
                    this.f2657k.setText(getString(R.string.l_step_stop));
                }
            }
        }
    }
}
