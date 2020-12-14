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
import com.hopetruly.part.net.C0791b;

import org.json.JSONArray;
import org.json.JSONException;

//import com.hexin.ecg_hexin_bio.baidu.location.BDLocationStatusCodes;

public class RegisterActivity extends C0721a {

    /* renamed from: a */
    final String f2512a = "RegisterActivity";

    /* renamed from: c */
    EditText f2513c;

    /* renamed from: d */
    EditText f2514d;

    /* renamed from: e */
    EditText f2515e;

    /* renamed from: f */
    Button f2516f;

    /* renamed from: g */
    ECGApplication f2517g;

    /* renamed from: h */
    String f2518h;

    /* renamed from: i */
    String f2519i;

    /* renamed from: j */
    C0678a f2520j;

    /* renamed from: k */
    ProgressDialog f2521k;

    /* renamed from: l */
    private View.OnClickListener f2522l = new View.OnClickListener() {
        public void onClick(View view) {
            Context context;
            int i;
            Resources resources;
            if (view.getId() == R.id.register_button) {
                RegisterActivity.this.f2518h = RegisterActivity.this.f2513c.getText().toString();
                RegisterActivity.this.f2519i = RegisterActivity.this.f2514d.getText().toString();
                String obj = RegisterActivity.this.f2515e.getText().toString();
                if (RegisterActivity.this.f2518h == null || RegisterActivity.this.f2518h.length() <= 0 || RegisterActivity.this.f2519i == null || RegisterActivity.this.f2519i.length() <= 0 || obj == null || obj.length() <= 0) {
                    context = RegisterActivity.this.getApplicationContext();
                    resources = RegisterActivity.this.getResources();
                    i = R.string.Not_completed;
                } else {
                    Log.i("RegisterActivity", RegisterActivity.this.f2519i + "???" + obj);
                    if (RegisterActivity.this.f2519i.equals(obj)) {
                        RegisterActivity.this.m2495a(RegisterActivity.this.f2518h, RegisterActivity.this.f2519i);
                        return;
                    }
                    context = RegisterActivity.this.getApplicationContext();
                    resources = RegisterActivity.this.getResources();
                    i = R.string.diff_pwd;
                }
                Toast.makeText(context, resources.getString(i), 0).show();
            }
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.RegisterActivity$a */
    class C0678a extends AsyncTask<String, Void, String> {
        C0678a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            if (isCancelled()) {
                return null;
            }
            return C0791b.m2880b(strArr[0], strArr[1]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            Toast makeText;
            if (!isCancelled()) {
                RegisterActivity.this.m2496b();
                if (str != null) {
                    Log.i("RegisterActivity", "result>>" + str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        if (jSONArray.getInt(0) == 0) {
                            Intent intent = new Intent();
                            intent.putExtra("name", RegisterActivity.this.f2518h);
                            intent.putExtra("pwd", RegisterActivity.this.f2519i);
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
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), RegisterActivity.this.getResources().getString(R.string.net_error), 0).show();
                }
                super.onPostExecute(str);
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            RegisterActivity.this.m2494a(RegisterActivity.this.getResources().getString(R.string.registering));
            super.onPreExecute();
        }
    }

    /* renamed from: a */
    private void m2490a() {
        this.f2513c = (EditText) findViewById(R.id.regiter_username);
        this.f2514d = (EditText) findViewById(R.id.register_password);
        this.f2515e = (EditText) findViewById(R.id.register_password_confirm);
        this.f2516f = (Button) findViewById(R.id.register_button);
        this.f2516f.setOnClickListener(this.f2522l);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2494a(String str) {
        this.f2521k = new ProgressDialog(this);
        this.f2521k.setMessage(str);
        this.f2521k.show();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2495a(String str, String str2) {
        if (this.f2520j == null || this.f2520j.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2520j = new C0678a();
            this.f2520j.execute(new String[]{this.f2518h, this.f2519i});
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.registering), 0).show();
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2496b() {
        this.f2521k.dismiss();
    }

    public void onBackPressed() {
//        setResult(BDLocationStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES, (Intent) null);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        this.f2517g = (ECGApplication) getApplication();
        m2490a();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.f2520j != null && this.f2520j.getStatus() == AsyncTask.Status.RUNNING) {
            this.f2520j.cancel(true);
            this.f2520j = null;
        }
        super.onDestroy();
    }
}
