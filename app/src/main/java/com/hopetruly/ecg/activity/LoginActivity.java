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
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.part.net.MyHttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    /* renamed from: a */
    final String TAG = "LoginActivity";

    /* renamed from: c */
    LoginmAsyncTask mLoginmAsyncTask;

    /* renamed from: d */
    Get_q_user_infoAsyntask mGet_q_user_infoAsyntask;

    /* renamed from: e */
    ProgressDialog login_loginDialog;

    /* renamed from: f */
    private final int f2374f = 1;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public EditText edt_user_name;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public EditText edt_user_pwd;

    /* renamed from: i */
    private TextView tv_register;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public RelativeLayout rv_login_btn_div;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public ECGApplication loginEcgApp;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public String muserName;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public String muserPassword;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public SharedPreferences.Editor loginEditor;

    /* renamed from: o */
    private View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            Context applicationContext;
            LoginActivity loginActivity;
            int i;
            int id = view.getId();
            if (id == R.id.login_btn_div) {
                String unused = LoginActivity.this.muserName = LoginActivity.this.edt_user_name.getText().toString();
                String unused2 = LoginActivity.this.muserPassword = LoginActivity.this.edt_user_pwd.getText().toString();
                String unused3 = LoginActivity.this.muserName = LoginActivity.this.muserName.trim();
                String unused4 = LoginActivity.this.muserPassword = LoginActivity.this.muserPassword.trim();
                boolean z = true;
                if ((LoginActivity.this.muserName == null) || (LoginActivity.this.muserName.length() < 1)) {
                    applicationContext = LoginActivity.this.getApplicationContext();
                    loginActivity = LoginActivity.this;
                    i = R.string.p_no_username;
                } else {
                    boolean z2 = LoginActivity.this.muserPassword == null;
                    if (LoginActivity.this.muserPassword.length() >= 1) {
                        z = false;
                    }
                    if (z2 || z) {
                        applicationContext = LoginActivity.this.getApplicationContext();
                        loginActivity = LoginActivity.this;
                        i = R.string.p_no_pwd;
                    } else {
                        String string = LoginActivity.this.loginEcgApp.spPerson_info.getString("userName", (String) null);
                        String string2 = LoginActivity.this.loginEcgApp.spPerson_info.getString("userPassword", (String) null);
                        if (string == null || string2 == null || ((string.equals(LoginActivity.this.muserName) && string2.equals(LoginActivity.this.muserPassword)) || LoginActivity.this.loginEcgApp.mSwConf.getAgree_flag() != 0)) {
                            LoginActivity.this.put_in_login(LoginActivity.this.muserName, LoginActivity.this.muserPassword);
                            return;
                        } else {
                            LoginActivity.this.startDeclareActivity();
                            return;
                        }
                    }
                }
                Toast.makeText(applicationContext, loginActivity.getString(i), Toast.LENGTH_LONG).show();
            } else if (id == R.id.register) {
                LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 80001);
            }
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.LoginActivity$a */
    class Get_q_user_infoAsyntask extends AsyncTask<Void, Void, String> {
        Get_q_user_infoAsyntask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(Void... voidArr) {
            if (isCancelled()) {
                return null;
            }
            return MyHttpHelper.get_q_user_info();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            if (!isCancelled()) {
                LoginActivity.this.dismissLogin_loginDialog();
                if (str != null) {
                    LogUtils.logI("LoginActivity", str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        if (jSONArray.getInt(0) == 0) {
                            JSONObject jSONObject = jSONArray.getJSONObject(2);
                            LoginActivity.this.loginEcgApp.mUserInfo.setId(jSONObject.getString("user_id"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setFirstName(jSONObject.getString("user_first_name"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setLastName(jSONObject.getString("user_last_name"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setAge(jSONObject.getInt("user_age"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setSex(jSONObject.getString("user_sex"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setBirthday(jSONObject.getString("user_birthday"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setHeight(jSONObject.getString("user_height"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setWeight(jSONObject.getString("user_weight"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setProfession(jSONObject.getString("user_profession"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setEmail(jSONObject.getString("user_email"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setPhone(jSONObject.getString("user_phone"));
                            LoginActivity.this.loginEcgApp.mUserInfo.setAddress(jSONObject.getString("user_addr"));
                            SharedPreferences.Editor edit = LoginActivity.this.loginEcgApp.spPerson_info.edit();
                            edit.putString("userId", LoginActivity.this.loginEcgApp.mUserInfo.getId());
                            edit.putString("birthday", LoginActivity.this.loginEcgApp.mUserInfo.getBirthday());
                            edit.putInt("age", LoginActivity.this.loginEcgApp.mUserInfo.getAge());
                            edit.putString("height", LoginActivity.this.loginEcgApp.mUserInfo.getHeight());
                            edit.putString("weight", LoginActivity.this.loginEcgApp.mUserInfo.getWeight());
                            edit.putString("medications", LoginActivity.this.loginEcgApp.mUserInfo.getMedications());
                            edit.putString("sex", LoginActivity.this.loginEcgApp.mUserInfo.getSex());
                            edit.putString("smoker", LoginActivity.this.loginEcgApp.mUserInfo.getSmoker());
                            edit.putString("profession", LoginActivity.this.loginEcgApp.mUserInfo.getProfession());
                            edit.putString("email", LoginActivity.this.loginEcgApp.mUserInfo.getEmail());
                            edit.putString("phone", LoginActivity.this.loginEcgApp.mUserInfo.getPhone());
                            edit.putString("address", LoginActivity.this.loginEcgApp.mUserInfo.getAddress());
                            edit.putString("firstName", LoginActivity.this.loginEcgApp.mUserInfo.getFirstName());
                            edit.putString("lastName", LoginActivity.this.loginEcgApp.mUserInfo.getLastName());
                            edit.commit();
                            LoginActivity.this.startToMain();
                            return;
                        }
                        int i = jSONArray.getInt(2);
                        if (i != 2) {
                            Context applicationContext = LoginActivity.this.getApplicationContext();
                            Toast.makeText(applicationContext, LoginActivity.this.getString(R.string.p_err_code) + i, Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_no_user_info), Toast.LENGTH_LONG).show();
                        LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this, PersonInfoRegisterActivity.class), 80002);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            LoginActivity.this.login_loginDialog.setMessage(LoginActivity.this.getResources().getString(R.string.get_user_info));
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.LoginActivity$b */
    class LoginmAsyncTask extends AsyncTask<String, Void, String> {
        LoginmAsyncTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            if (isCancelled()) {
                return null;
            }
            return MyHttpHelper.get_ecg_file_list(strArr[0], strArr[1]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            Toast makeText;
            if (!isCancelled()) {
                LoginActivity.this.login_loginDialog.setMessage(LoginActivity.this.getResources().getString(R.string.login_authe_success));
                if (str != null) {
                    Log.i("LoginActivity", "result>>" + str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        int i = jSONArray.getInt(0);
                        if (i == 0) {
                            Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_login_success), Toast.LENGTH_LONG).show();
                            if (LoginActivity.this.loginEcgApp.mSwConf.getAgree_flag() == 1) {
                                LoginActivity.this.loginEcgApp.mSwConf.setAgree_flag(0);
                                LoginActivity.this.loginEditor.putInt("SW_AGREE_FLAG", LoginActivity.this.loginEcgApp.mSwConf.getAgree_flag());
                                LoginActivity.this.loginEditor.commit();
                            }
                            String string = jSONArray.getString(2);
                            if (string != null) {
                                Log.e("LoginActivity", "Id:" + string);
                                LoginActivity.this.loginEcgApp.mUserInfo.setId(string);
                            }
                            SharedPreferences.Editor edit = LoginActivity.this.loginEcgApp.spPerson_info.edit();
                            edit.putString("userId", LoginActivity.this.loginEcgApp.mUserInfo.getId());
                            edit.putString("userName", LoginActivity.this.muserName);
                            edit.putString("userPassword", LoginActivity.this.muserPassword);
                            edit.commit();
                            LoginActivity.this.loginEcgApp.mSwConf.setSW_SAVE_ACCOUNT_AND_PASSWORD(1);
                            LoginActivity.this.loginEcgApp.mSwConf.setSW_AUTO_LOGIN(1);
                            LoginActivity.this.loginEditor.putInt("SW_SAVE_ACCOUNT_AND_PASSWORD", LoginActivity.this.loginEcgApp.mSwConf.mo2684b());
                            LoginActivity.this.loginEditor.putInt("SW_AUTO_LOGIN", LoginActivity.this.loginEcgApp.mSwConf.getAuto_login());
                            LoginActivity.this.loginEditor.commit();
                            LoginActivity.this.startmGet_q_user_info();
                            LoginActivity.this.loginEcgApp.mUserInfo.setUserName(LoginActivity.this.muserName);
                            LoginActivity.this.loginEcgApp.mUserInfo.setName(LoginActivity.this.muserName);
                            return;
                        }
                        int i2 = jSONArray.getInt(2);
                        if (i2 == 1) {
                            makeText = Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_username_err), Toast.LENGTH_LONG);
                        } else if (i2 != 999) {
                            Context applicationContext = LoginActivity.this.getApplicationContext();
                                makeText = Toast.makeText(applicationContext, LoginActivity.this.getString(R.string.p_err_code) + i, Toast.LENGTH_LONG);
                        } else {
                            makeText = Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getString(R.string.p_pwd_err), Toast.LENGTH_LONG);
                        }
                        makeText.show();
                        LoginActivity.this.dismissLogin_loginDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LoginActivity.this.loginEcgApp.mSwConf.setAgree_flag(1);
                    Toast.makeText(LoginActivity.this.getApplicationContext(), LoginActivity.this.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                    LoginActivity.this.dismissLogin_loginDialog();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            LoginActivity.this.showLogin_loginDialog();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void put_in_login(String str, String str2) {
        if (str != null && str2 != null) {
            if (this.mLoginmAsyncTask == null || this.mLoginmAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                this.mLoginmAsyncTask = new LoginmAsyncTask();
                this.mLoginmAsyncTask.execute(new String[]{str, str2});
                return;
            }
            Toast.makeText(getApplicationContext(), getString(R.string.p_in_login), Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: b */
    private void initView() {
        this.edt_user_name = (EditText) findViewById(R.id.user_name);
        this.edt_user_pwd = (EditText) findViewById(R.id.user_pwd);
        this.edt_user_pwd.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 66) {
                    return LoginActivity.this.rv_login_btn_div.performClick();
                }
                return false;
            }
        });
        this.tv_register = (TextView) findViewById(R.id.register);
        this.tv_register.setOnClickListener(this.loginOnClickListener);
        this.rv_login_btn_div = (RelativeLayout) findViewById(R.id.login_btn_div);
        this.rv_login_btn_div.setOnClickListener(this.loginOnClickListener);
        if (this.loginEcgApp.mSwConf.getAuto_login() == 1) {
            this.muserName = this.loginEcgApp.mUserInfo.getName();
            this.muserPassword = this.loginEcgApp.mUserInfo.getPassword();
            this.edt_user_name.setText(this.muserName);
            this.edt_user_pwd.setText(this.muserPassword);
            put_in_login(this.muserName, this.muserPassword);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void startmGet_q_user_info() {
        if (this.mGet_q_user_infoAsyntask == null || this.mGet_q_user_infoAsyntask.getStatus() != AsyncTask.Status.RUNNING) {
            this.mGet_q_user_infoAsyntask = new Get_q_user_infoAsyntask();
            this.mGet_q_user_infoAsyntask.execute(new Void[0]);
            return;
        }
        Toast.makeText(getApplicationContext(), getString(R.string.p_wait), Toast.LENGTH_LONG).show();
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void startDeclareActivity() {
        startActivityForResult(new Intent(this, DeclareActivity.class), 1);
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void showLogin_loginDialog() {
        if (this.login_loginDialog == null) {
            this.login_loginDialog = new ProgressDialog(this);
        }
        this.login_loginDialog.setMessage(getResources().getString(R.string.p_login_login));
        if (!this.login_loginDialog.isShowing()) {
            this.login_loginDialog.show();
        }
        this.login_loginDialog.setCanceledOnTouchOutside(false);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void dismissLogin_loginDialog() {
        if (this.login_loginDialog != null && this.login_loginDialog.isShowing()) {
            this.login_loginDialog.dismiss();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void startToMain() {
        startActivity(new Intent(this, FunChooseActivity.class));
        finish();
    }

    /* renamed from: a */
    public void showLogin_tipDialog() {
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
                this.edt_user_name.setText(stringExtra);
                this.edt_user_pwd.setText(stringExtra2);
            } else if (i2 == 1001) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.p_login_no_reg), Toast.LENGTH_LONG).show();
            }
        } else if (i == 80002) {
            if (i2 == -1) {
                startToMain();
            }
        } else if (i != 1) {
        } else {
            if (i2 == -1) {
                put_in_login(this.muserName, this.muserPassword);
            } else if (i2 == 0) {
                this.loginEcgApp.mSwConf.setSW_AUTO_LOGIN(0);
                this.loginEditor.putInt("SW_AUTO_LOGIN", this.loginEcgApp.mSwConf.getAuto_login());
                this.loginEcgApp.mSwConf.setAgree_flag(0);
                this.loginEditor.putInt("SW_AGREE_FLAG", this.loginEcgApp.mSwConf.getAgree_flag());
                this.loginEcgApp.mSwConf.setAgree_declare(0);
                this.loginEditor.putInt("SW_AGREE_DECLARE", this.loginEcgApp.mSwConf.getAgree_declare());
                this.loginEditor.commit();
                finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        this.loginEcgApp = (ECGApplication) getApplication();
        this.loginEditor = this.loginEcgApp.spSw_conf.edit();
        initView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        dismissLogin_loginDialog();
        if (this.mLoginmAsyncTask != null && this.mLoginmAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.mLoginmAsyncTask.cancel(true);
            this.mLoginmAsyncTask = null;
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Log.d("LoginActivity", "OnStart " + this.loginEcgApp.mUserInfo.getName());
        if (this.loginEcgApp.mUserInfo.getName() == UserInfo.EMPTY) {
            showLogin_tipDialog();
        }
        super.onStart();
    }
}
