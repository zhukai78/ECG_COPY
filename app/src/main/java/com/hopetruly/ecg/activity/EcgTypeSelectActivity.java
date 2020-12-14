package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.part.net.C0791b;
import com.hopetruly.part.net.NetService;
import org.json.JSONArray;
import org.json.JSONException;

public class EcgTypeSelectActivity extends C0721a implements View.OnClickListener {

    /* renamed from: a */
    ECGApplication f2184a;

    /* renamed from: c */
    private ImageView f2185c;

    /* renamed from: d */
    private ImageView f2186d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public NetService f2187e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public PopupWindow f2188f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public EditText f2189g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public EditText f2190h;

    /* renamed from: i */
    private BroadcastReceiver f2191i = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL")) {
                EcgTypeSelectActivity.this.m2277d();
                EcgTypeSelectActivity.this.f2188f.dismiss();
            } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_FAILE")) {
                EcgTypeSelectActivity.this.m2277d();
                Toast.makeText(EcgTypeSelectActivity.this.getApplicationContext(), EcgTypeSelectActivity.this.getString(R.string.p_login_fail), Toast.LENGTH_SHORT).show();
            }
        }
    };

    /* renamed from: j */
    private ServiceConnection f2192j = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NetService unused = EcgTypeSelectActivity.this.f2187e = ((NetService.C0786c) iBinder).mo2852a();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetService unused = EcgTypeSelectActivity.this.f2187e = null;
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: k */
    public Handler f2193k = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                EcgTypeSelectActivity.this.m2278e();
            }
            if (message.what == 1) {
                EcgTypeSelectActivity.this.mo2160a();
            }
            super.handleMessage(message);
        }
    };

    /* renamed from: l */
    private ProgressDialog f2194l;

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2271a(String str) {
        if (this.f2194l == null) {
            this.f2194l = new ProgressDialog(this);
            this.f2194l.setCanceledOnTouchOutside(false);
        }
        this.f2194l.setMessage(str);
        if (!this.f2194l.isShowing()) {
            this.f2194l.show();
        }
    }

    /* renamed from: b */
    private void m2273b() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
        this.f2188f = new PopupWindow(inflate, -2, -2, true);
        this.f2188f.setOutsideTouchable(false);
        this.f2188f.setFocusable(true);
        this.f2189g = (EditText) inflate.findViewById(R.id.login_user_name);
        this.f2189g.setText(((ECGApplication) getApplication()).f2081b.getName());
        this.f2190h = (EditText) inflate.findViewById(R.id.login_user_pwd);
        this.f2190h.requestFocus();
        ((Button) inflate.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EcgTypeSelectActivity.this.f2187e.mo2829c(EcgTypeSelectActivity.this.f2189g.getText().toString(), EcgTypeSelectActivity.this.f2190h.getText().toString());
                EcgTypeSelectActivity.this.m2271a(EcgTypeSelectActivity.this.getString(R.string.p_login_login));
            }
        });
        ((Button) inflate.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EcgTypeSelectActivity.this.f2188f.dismiss();
            }
        });
    }

    /* renamed from: c */
    private void m2275c() {
        m2271a(getString(R.string.p_check_login));
        new Thread() {
            public void run() {
                String a = C0791b.m2871a();
                if (a != null) {
                    Log.d("EcgTypeSelectActivity", a);
                    try {
                        JSONArray jSONArray = new JSONArray(a);
                        if (jSONArray.getInt(0) == 1 && jSONArray.getInt(2) == 998) {
                            EcgTypeSelectActivity.this.f2193k.sendEmptyMessage(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    EcgTypeSelectActivity.this.f2193k.sendEmptyMessage(1);
                }
                EcgTypeSelectActivity.this.m2277d();
                super.run();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m2277d() {
        if (this.f2194l != null && this.f2194l.isShowing()) {
            this.f2194l.dismiss();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m2278e() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.p_realtime_ecg_need_login));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                EcgTypeSelectActivity.this.f2188f.showAtLocation(EcgTypeSelectActivity.this.findViewById(R.id.btn_ect_type_hand), 17, 0, 0);
            }
        });
        builder.setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo2160a() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.p_realtime_ecg_check_network));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void onClick(View view) {
        String str = "";
        int i = -1;
        Intent intent = new Intent(this, RealtimeECGDisplayActivity.class);
        switch (view.getId()) {
            case R.id.btn_ect_type_hand /*2131165240*/:
                str = "Lead";
                i = 0;
                break;
            case R.id.btn_ect_type_heart /*2131165241*/:
                str = "Lead";
                i = 1;
                break;
            default:
                startActivity(intent);
                finish();
        }
        intent.putExtra(str, i);
        startActivity(intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        setContentView(R.layout.activity_ecg_type_select);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.f2185c = (ImageView) findViewById(R.id.btn_ect_type_hand);
        this.f2186d = (ImageView) findViewById(R.id.btn_ect_type_heart);
        this.f2186d.setOnClickListener(this);
        this.f2185c.setOnClickListener(this);
        this.f2184a = (ECGApplication) getApplication();
        if (this.f2184a.f2085f.mo2648d() == 1) {
            bindService(new Intent(this, NetService.class), this.f2192j, Context.BIND_AUTO_CREATE);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL");
            intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_FAILE");
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2191i, intentFilter);
            m2273b();
            m2275c();
        }
        super.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.f2184a.f2085f.mo2648d() == 1) {
            unbindService(this.f2192j);
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2191i);
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
