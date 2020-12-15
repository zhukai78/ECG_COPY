package com.hopetruly.ecg.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.part.net.MyHttpHelper;

import org.json.JSONArray;
import org.json.JSONException;

//import com.hexin.ecg_hexin_bio.baidu.location.BDLocationStatusCodes;

public class RegisterActivity extends BaseActivity {

    /* renamed from: a */
    final String TAG = "RegisterActivity";

    /* renamed from: c */
    EditText edt_regiter_username;

    /* renamed from: d */
    EditText edt_register_password;

    /* renamed from: e */
    EditText edt_register_password_confirm;

    /* renamed from: f */
    Button btn_register_button;

    /* renamed from: g */
    ECGApplication mECGApplication;

    /* renamed from: h */
    String username;

    /* renamed from: i */
    String userpwd;

    /* renamed from: j */
    Register_ajaxAsynTask register_ajaxAsynTask;

    /* renamed from: k */
    ProgressDialog loadProgressDialog;

    /* renamed from: l */
    private View.OnClickListener f2522l = new View.OnClickListener() {
        public void onClick(View view) {
            Context context;
            int i;
            Resources resources;
            if (view.getId() == R.id.register_button) {
                RegisterActivity.this.username = RegisterActivity.this.edt_regiter_username.getText().toString();
                RegisterActivity.this.userpwd = RegisterActivity.this.edt_register_password.getText().toString();
                String obj = RegisterActivity.this.edt_register_password_confirm.getText().toString();
                if (RegisterActivity.this.username == null || RegisterActivity.this.username.length() <= 0 || RegisterActivity.this.userpwd == null || RegisterActivity.this.userpwd.length() <= 0 || obj == null || obj.length() <= 0) {
                    context = RegisterActivity.this.getApplicationContext();
                    resources = RegisterActivity.this.getResources();
                    i = R.string.Not_completed;
                } else {
                    Log.i("RegisterActivity", RegisterActivity.this.userpwd + "???" + obj);
                    if (RegisterActivity.this.userpwd.equals(obj)) {
                        RegisterActivity.this.toRegister(RegisterActivity.this.username, RegisterActivity.this.userpwd);
                        return;
                    }
                    context = RegisterActivity.this.getApplicationContext();
                    resources = RegisterActivity.this.getResources();
                    i = R.string.diff_pwd;
                }
                Toast.makeText(context, resources.getString(i), Toast.LENGTH_LONG).show();
            }
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.RegisterActivity$a */
    class Register_ajaxAsynTask extends AsyncTask<String, Void, String> {
        Register_ajaxAsynTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            if (isCancelled()) {
                return null;
            }
            return MyHttpHelper.register_ajax(strArr[0], strArr[1]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            Toast makeText;
            if (!isCancelled()) {
                RegisterActivity.this.dismissLoadProgressDialog();
                if (str != null) {
                    Log.i("RegisterActivity", "result>>" + str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        if (jSONArray.getInt(0) == 0) {
                            Intent intent = new Intent();
                            intent.putExtra("name", RegisterActivity.this.username);
                            intent.putExtra("pwd", RegisterActivity.this.userpwd);
                            RegisterActivity.this.setResult(1000, intent);
                            RegisterActivity.this.finish();
                        } else {
                            int i = jSONArray.getInt(2);
                            if (i != 5) {
                                Context applicationContext = RegisterActivity.this.getApplicationContext();
                                makeText = Toast.makeText(applicationContext, RegisterActivity.this.getString(R.string.p_err_code) + i, 0);
                            } else {
                                makeText = Toast.makeText(RegisterActivity.this.getApplicationContext(), RegisterActivity.this.getResources().getString(R.string.user_exists), 1);
                            }
                            makeText.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), RegisterActivity.this.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                }
                super.onPostExecute(str);
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            RegisterActivity.this.showMessageProgress(RegisterActivity.this.getResources().getString(R.string.registering));
            super.onPreExecute();
        }
    }

    /* renamed from: a */
    private void initView() {
        this.edt_regiter_username = (EditText) findViewById(R.id.regiter_username);
        this.edt_register_password = (EditText) findViewById(R.id.register_password);
        this.edt_register_password_confirm = (EditText) findViewById(R.id.register_password_confirm);
        this.btn_register_button = (Button) findViewById(R.id.register_button);
        this.btn_register_button.setOnClickListener(this.f2522l);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void showMessageProgress(String str) {
        this.loadProgressDialog = new ProgressDialog(this);
        this.loadProgressDialog.setMessage(str);
        this.loadProgressDialog.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void toRegister(String str, String str2) {
        if (this.register_ajaxAsynTask == null || this.register_ajaxAsynTask.getStatus() != AsyncTask.Status.RUNNING) {
            this.register_ajaxAsynTask = new Register_ajaxAsynTask();
            this.register_ajaxAsynTask.execute(new String[]{this.username, this.userpwd});
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.registering), Toast.LENGTH_LONG).show();
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void dismissLoadProgressDialog() {
        this.loadProgressDialog.dismiss();
    }

    public void onBackPressed() {
//        setResult(BDLocationStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES, (Intent) null);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        this.mECGApplication = (ECGApplication) getApplication();
        initView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.register_ajaxAsynTask != null && this.register_ajaxAsynTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.register_ajaxAsynTask.cancel(true);
            this.register_ajaxAsynTask = null;
        }
        super.onDestroy();
    }
}
