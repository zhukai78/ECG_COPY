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
import com.hopetruly.part.net.MyHttpHelper;
import com.hopetruly.part.net.NetService;
import org.json.JSONArray;
import org.json.JSONException;

public class EcgTypeSelectActivity extends BaseActivity implements View.OnClickListener {

    /* renamed from: a */
    ECGApplication mECGApplication;

    /* renamed from: c */
    private ImageView btn_ect_type_hand;

    /* renamed from: d */
    private ImageView btn_ect_type_heart;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public NetService mNetSerBinder;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public PopupWindow pupop_login;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public EditText edt_login_user_name;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public EditText edt_login_user_pwd;

    /* renamed from: i */
    private BroadcastReceiver f2191i = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL")) {
                EcgTypeSelectActivity.this.dismisscheck_loginDialog();
                EcgTypeSelectActivity.this.pupop_login.dismiss();
            } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_FAILE")) {
                EcgTypeSelectActivity.this.dismisscheck_loginDialog();
                Toast.makeText(EcgTypeSelectActivity.this.getApplicationContext(), EcgTypeSelectActivity.this.getString(R.string.p_login_fail), Toast.LENGTH_SHORT).show();
            }
        }
    };

    /* renamed from: j */
    private ServiceConnection NetSerConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NetService unused = EcgTypeSelectActivity.this.mNetSerBinder = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetService unused = EcgTypeSelectActivity.this.mNetSerBinder = null;
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: k */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                EcgTypeSelectActivity.this.showNeed_loginDialog();
            }
            if (message.what == 1) {
                EcgTypeSelectActivity.this.showcheck_networkDialog();
            }
            super.handleMessage(message);
        }
    };

    /* renamed from: l */
    private ProgressDialog messageProgressDialog;

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void showLoginDialog(String str) {
        if (this.messageProgressDialog == null) {
            this.messageProgressDialog = new ProgressDialog(this);
            this.messageProgressDialog.setCanceledOnTouchOutside(false);
        }
        this.messageProgressDialog.setMessage(str);
        if (!this.messageProgressDialog.isShowing()) {
            this.messageProgressDialog.show();
        }
    }

    /* renamed from: b */
    private void initView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
        this.pupop_login = new PopupWindow(inflate, -2, -2, true);
        this.pupop_login.setOutsideTouchable(false);
        this.pupop_login.setFocusable(true);
        this.edt_login_user_name = (EditText) inflate.findViewById(R.id.login_user_name);
        this.edt_login_user_name.setText(((ECGApplication) getApplication()).mUserInfo.getName());
        this.edt_login_user_pwd = (EditText) inflate.findViewById(R.id.login_user_pwd);
        this.edt_login_user_pwd.requestFocus();
        ((Button) inflate.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EcgTypeSelectActivity.this.mNetSerBinder.getEcgFilesAsyn(EcgTypeSelectActivity.this.edt_login_user_name.getText().toString(), EcgTypeSelectActivity.this.edt_login_user_pwd.getText().toString());
                EcgTypeSelectActivity.this.showLoginDialog(EcgTypeSelectActivity.this.getString(R.string.p_login_login));
            }
        });
        ((Button) inflate.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EcgTypeSelectActivity.this.pupop_login.dismiss();
            }
        });
    }

    /* renamed from: c */
    private void p_check_loginDialog() {
        showLoginDialog(getString(R.string.p_check_login));
        new Thread() {
            public void run() {
                String a = MyHttpHelper.checkLogin();
                if (a != null) {
                    Log.d("EcgTypeSelectActivity", a);
                    try {
                        JSONArray jSONArray = new JSONArray(a);
                        if (jSONArray.getInt(0) == 1 && jSONArray.getInt(2) == 998) {
                            EcgTypeSelectActivity.this.mHandler.sendEmptyMessage(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    EcgTypeSelectActivity.this.mHandler.sendEmptyMessage(1);
                }
                EcgTypeSelectActivity.this.dismisscheck_loginDialog();
                super.run();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void dismisscheck_loginDialog() {
        if (this.messageProgressDialog != null && this.messageProgressDialog.isShowing()) {
            this.messageProgressDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void showNeed_loginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.p_realtime_ecg_need_login));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                EcgTypeSelectActivity.this.pupop_login.showAtLocation(EcgTypeSelectActivity.this.findViewById(R.id.btn_ect_type_hand), 17, 0, 0);
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
    public void showcheck_networkDialog() {
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
        this.btn_ect_type_hand = (ImageView) findViewById(R.id.btn_ect_type_hand);
        this.btn_ect_type_heart = (ImageView) findViewById(R.id.btn_ect_type_heart);
        this.btn_ect_type_heart.setOnClickListener(this);
        this.btn_ect_type_hand.setOnClickListener(this);
        this.mECGApplication = (ECGApplication) getApplication();
        if (this.mECGApplication.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
            bindService(new Intent(this, NetService.class), this.NetSerConn, Context.BIND_AUTO_CREATE);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL");
            intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_FAILE");
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2191i, intentFilter);
            initView();
            p_check_loginDialog();
        }
        super.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.mECGApplication.appECGConf.getECG_REALTIME_UPLOAD() == 1) {
            unbindService(this.NetSerConn);
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
