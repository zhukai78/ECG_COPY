package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.UserInfo;
import com.hopetruly.ecg.util.C0771g;
import com.hopetruly.part.net.C0791b;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends C0721a {

    /* renamed from: a */
    final String f2370a = "LoginActivity";

    /* renamed from: c */
    C0648b f2371c;

    /* renamed from: d */
    C0647a f2372d;

    /* renamed from: e */
    ProgressDialog f2373e;

    /* renamed from: f */
    private final int f2374f = 1;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public EditText f2375g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public EditText f2376h;

    /* renamed from: i */
    private TextView f2377i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public RelativeLayout f2378j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public ECGApplication f2379k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public String f2380l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public String f2381m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public SharedPreferences.Editor f2382n;

    /* renamed from: o */
    private View.OnClickListener f2383o = new View.OnClickListener() {
        public void onClick(View view) {
            Context applicationContext;
            LoginActivity loginActivity;
            int i;
            int id = view.getId();
            if (id == R.id.login_btn_div) {
                String unused = LoginActivity.this.f2380l = LoginActivity.this.f2375g.getText().toString();
                String unused2 = LoginActivity.this.f2381m = LoginActivity.this.f2376h.getText().toString();
                String unused3 = LoginActivity.this.f2380l = LoginActivity.this.f2380l.trim();
                String unused4 = LoginActivity.this.f2381m = LoginActivity.this.f2381m.trim();
                boolean z = true;
                if ((LoginActivity.this.f2380l == null) || (LoginActivity.this.f2380l.length() < 1)) {
                    applicationContext = LoginActivity.this.getApplicationContext();
                    loginActivity = LoginActivity.this;
                    i = R.string.p_no_username;
                } else {
                    boolean z2 = LoginActivity.this.f2381m == null;
                    if (LoginActivity.this.f2381m.length() >= 1) {
                        z = false;
                    }
                    if (z2 || z) {
                        applicationContext = LoginActivity.this.getApplicationContext();
                        loginActivity = LoginActivity.this;
                        i = R.string.p_no_pwd;
                    } else {
                        String string = LoginActivity.this.f2379k.f2082c.getString("userName", (String) null);
                        String string2 = LoginActivity.this.f2379k.f2082c.getString("userPassword", (String) null);
                        if (string == null || string2 == null || ((string.equals(LoginActivity.this.f2380l) && string2.equals(LoginActivity.this.f2381m)) || LoginActivity.this.f2379k.f2083d.mo2688d() != 0)) {
                            LoginActivity.this.m2404a(LoginActivity.this.f2380l, LoginActivity.this.f2381m);
                            return;
                        } else {
                            LoginActivity.this.m2411d();
                            return;
                        }
                    }
                }
                Toast.makeText(applicationContext, loginActivity.getString(i), 0).show();
            } else if (id == R.id.register) {
                LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 80001);
            }
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.LoginActivity$a */
    class C0647a extends AsyncTask<Void, Void, String> {
        C0647a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(Void... voidArr) {
            if (isCancelled()) {
                return null;
            }
            return C0791b.m2879b();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            if (!isCancelled()) {
                LoginActivity.this.m2415f();
                if (str != null) {
                    C0771g.m2784a("LoginActivity", str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        if (jSONArray.getInt(0) == 0) {
                            JSONObject jSONObject = jSONArray.getJSONObject(2);
                            LoginActivity.this.f2379k.f2081b.setId(jSONObject.getString("user_id"));
                            LoginActivity.this.f2379k.f2081b.setFirstName(jSONObject.getString("user_first_name"));
                            LoginActivity.this.f2379k.f2081b.setLastName(jSONObject.getString("user_last_name"));
                            LoginActivity.this.f2379k.f2081b.setAge(jSONObject.getInt("user_age"));
                            LoginActivity.this.f2379k.f2081b.setSex(jSONObject.getString("user_sex"));
                            LoginActivity.this.f2379k.f2081b.setBirthday(jSONObject.getString("user_birthday"));
                            LoginActivity.this.f2379k.f2081b.setHeight(jSONObject.getString("user_height"));
                            LoginActivity.this.f2379k.f2081b.setWeight(jSONObject.getString("user_weight"));
                            LoginActivity.this.f2379k.f2081b.setProfession(jSONObject.getString("user_profession"));
                            LoginActivity.this.f2379k.f2081b.setEmail(jSONObject.getString("user_email"));
                            LoginActivity.this.f2379k.f2081b.setPhone(jSONObject.getString("user_phone"));
                            LoginActivity.this.f2379k.f2081b.setAddress(jSONObject.getString("user_addr"));
                            SharedPreferences.Editor edit = LoginActivity.this.f2379k.f2082c.edit();
                            edit.putString("userId", LoginActivity.this.f2379k.f2081b.getId());
                            edit.putString("birthday", LoginActivity.this.f2379k.f2081b.getBirthday());
                            edit.putInt("age", LoginActivity.this.f2379k.f2081b.getAge());
                            edit.putString("height", LoginActivity.this.f2379k.f2081b.getHeight());
                            edit.putString("weight", LoginActivity.this.f2379k.f2081b.getWeight());
                            edit.putString("medications", LoginActivity.this.f2379k.f2081b.getMedications());
                            edit.putString("sex", LoginActivity.this.f2379k.f2081b.getSex());
                            edit.putString("smoker", LoginActivity.this.f2379k.f2081b.getSmoker());
                            edit.putString("profession", LoginActivity.this.f2379k.f2081b.getProfession());
                            edit.putString("email", LoginActivity.this.f2379k.f2081b.getEmail());
                            edit.putString("phone", LoginActivity.this.f2379k.f2081b.getPhone());
                            edit.putString("address", LoginActivity.this.f2379k.f2081b.getAddress());
                            edit.putString("firstName", LoginActivity.this.f2379k.f2081b.getFirstName());
                            edit.putString("lastName", LoginActivity.this.f2379k.f2081b.getLastName());
                            edit.commit();
                            LoginActivity.this.m2416g();
                            return;
                        }
                        int i = jSONArray.getInt(2);
                        if (i != 2) {
                            Context applicationContext = LoginActivity.this.getApplicationContext();
                            Toast.makeText(applicationContext, LoginActivity.this.getString(R.string.p_err_code) + i, 0).show();
                            return;
                        }
                        Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_no_user_info), 0).show();
                        LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this, PersonInfoRegisterActivity.class), 80002);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getResources().getString(R.string.net_error), 0).show();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            LoginActivity.this.f2373e.setMessage(LoginActivity.this.getResources().getString(R.string.get_user_info));
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.LoginActivity$b */
    class C0648b extends AsyncTask<String, Void, String> {
        C0648b() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            if (isCancelled()) {
                return null;
            }
            return C0791b.m2874a(strArr[0], strArr[1]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            Toast makeText;
            if (!isCancelled()) {
                LoginActivity.this.f2373e.setMessage(LoginActivity.this.getResources().getString(R.string.login_authe_success));
                if (str != null) {
                    Log.i("LoginActivity", "result>>" + str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        int i = jSONArray.getInt(0);
                        if (i == 0) {
                            Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_login_success), 0).show();
                            if (LoginActivity.this.f2379k.f2083d.mo2688d() == 1) {
                                LoginActivity.this.f2379k.f2083d.mo2689d(0);
                                LoginActivity.this.f2382n.putInt("SW_AGREE_FLAG", LoginActivity.this.f2379k.f2083d.mo2688d());
                                LoginActivity.this.f2382n.commit();
                            }
                            String string = jSONArray.getString(2);
                            if (string != null) {
                                Log.e("LoginActivity", "Id:" + string);
                                LoginActivity.this.f2379k.f2081b.setId(string);
                            }
                            SharedPreferences.Editor edit = LoginActivity.this.f2379k.f2082c.edit();
                            edit.putString("userId", LoginActivity.this.f2379k.f2081b.getId());
                            edit.putString("userName", LoginActivity.this.f2380l);
                            edit.putString("userPassword", LoginActivity.this.f2381m);
                            edit.commit();
                            LoginActivity.this.f2379k.f2083d.mo2685b(1);
                            LoginActivity.this.f2379k.f2083d.mo2682a(1);
                            LoginActivity.this.f2382n.putInt("SW_SAVE_ACCOUNT_AND_PASSWORD", LoginActivity.this.f2379k.f2083d.mo2684b());
                            LoginActivity.this.f2382n.putInt("SW_AUTO_LOGIN", LoginActivity.this.f2379k.f2083d.mo2681a());
                            LoginActivity.this.f2382n.commit();
                            LoginActivity.this.m2409c();
                            LoginActivity.this.f2379k.f2081b.setUserName(LoginActivity.this.f2380l);
                            LoginActivity.this.f2379k.f2081b.setName(LoginActivity.this.f2380l);
                            return;
                        }
                        int i2 = jSONArray.getInt(2);
                        if (i2 == 1) {
                            makeText = Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_username_err), 0);
                        } else if (i2 != 999) {
                            Context applicationContext = LoginActivity.this.getApplicationContext();
                            makeText = Toast.makeText(applicationContext, LoginActivity.this.getString(R.string.p_err_code) + i, 0);
                        } else {
                            makeText = Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_pwd_err), 0);
                        }
                        makeText.show();
                        LoginActivity.this.m2415f();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LoginActivity.this.f2379k.f2083d.mo2689d(1);
                    Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getResources().getString(R.string.net_error), 0).show();
                    LoginActivity.this.m2415f();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            LoginActivity.this.m2413e();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2404a(String str, String str2) {
        if (str != null && str2 != null) {
            if (this.f2371c == null || this.f2371c.getStatus() != AsyncTask.Status.RUNNING) {
                this.f2371c = new C0648b();
                this.f2371c.execute(new String[]{str, str2});
                return;
            }
            Toast.makeText(getApplicationContext(), getString(R.string.p_in_login), 0).show();
        }
    }

    /* renamed from: b */
    private void m2407b() {
        this.f2375g = (EditText) findViewById(R.id.user_name);
        this.f2376h = (EditText) findViewById(R.id.user_pwd);
        this.f2376h.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 66) {
                    return LoginActivity.this.f2378j.performClick();
                }
                return false;
            }
        });
        this.f2377i = (TextView) findViewById(R.id.register);
        this.f2377i.setOnClickListener(this.f2383o);
        this.f2378j = (RelativeLayout) findViewById(R.id.login_btn_div);
        this.f2378j.setOnClickListener(this.f2383o);
        if (this.f2379k.f2083d.mo2681a() == 1) {
            this.f2380l = this.f2379k.f2081b.getName();
            this.f2381m = this.f2379k.f2081b.getPassword();
            this.f2375g.setText(this.f2380l);
            this.f2376h.setText(this.f2381m);
            m2404a(this.f2380l, this.f2381m);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2409c() {
        if (this.f2372d == null || this.f2372d.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2372d = new C0647a();
            this.f2372d.execute(new Void[0]);
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.p_wait), 0).show();
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m2411d() {
        startActivityForResult(new Intent(this, DeclareActivity.class), 1);
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m2413e() {
        if (this.f2373e == null) {
            this.f2373e = new ProgressDialog(this);
        }
        this.f2373e.setMessage(getResources().getString(R.string.p_login_login));
        if (!this.f2373e.isShowing()) {
            this.f2373e.show();
        }
        this.f2373e.setCanceledOnTouchOutside(false);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m2415f() {
        if (this.f2373e != null && this.f2373e.isShowing()) {
            this.f2373e.dismiss();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m2416g() {
        startActivity(new Intent(this, FunChooseActivity.class));
        finish();
    }

    /* renamed from: a */
    public void mo2253a() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.p_login_tip));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 80001) {
            if (i2 == 1000) {
                String stringExtra = intent.getStringExtra("name");
                String stringExtra2 = intent.getStringExtra("pwd");
                this.f2375g.setText(stringExtra);
                this.f2376h.setText(stringExtra2);
            } else if (i2 == 1001) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.p_login_no_reg), 0).show();
            }
        } else if (i == 80002) {
            if (i2 == -1) {
                m2416g();
            }
        } else if (i != 1) {
        } else {
            if (i2 == -1) {
                m2404a(this.f2380l, this.f2381m);
            } else if (i2 == 0) {
                this.f2379k.f2083d.mo2682a(0);
                this.f2382n.putInt("SW_AUTO_LOGIN", this.f2379k.f2083d.mo2681a());
                this.f2379k.f2083d.mo2689d(0);
                this.f2382n.putInt("SW_AGREE_FLAG", this.f2379k.f2083d.mo2688d());
                this.f2379k.f2083d.mo2687c(0);
                this.f2382n.putInt("SW_AGREE_DECLARE", this.f2379k.f2083d.mo2686c());
                this.f2382n.commit();
                finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        this.f2379k = (ECGApplication) getApplication();
        this.f2382n = this.f2379k.f2084e.edit();
        m2407b();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        m2415f();
        if (this.f2371c != null && this.f2371c.getStatus() == AsyncTask.Status.RUNNING) {
            this.f2371c.cancel(true);
            this.f2371c = null;
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Log.d("LoginActivity", "OnStart " + this.f2379k.f2081b.getName());
        if (this.f2379k.f2081b.getName() == UserInfo.EMPTY) {
            mo2253a();
        }
        super.onStart();
    }
}
