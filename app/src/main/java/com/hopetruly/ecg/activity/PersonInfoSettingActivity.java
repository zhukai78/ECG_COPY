package com.hopetruly.ecg.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.UserInfo;
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.part.net.MyHttpHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

public class PersonInfoSettingActivity extends BaseActivity {

    /* renamed from: A */
    private boolean f2422A = false;
    /* access modifiers changed from: private */

    /* renamed from: B */
    public PopupWindow f2423B;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public EditText f2424C;
    /* access modifiers changed from: private */

    /* renamed from: D */
    public EditText f2425D;

    /* renamed from: E */
    private View.OnTouchListener f2426E = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            Log.i("PersonInfoSettingActivity", "datePickerDialog create!~~~");
            new DatePickerDialog(PersonInfoSettingActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                    PersonInfoSettingActivity.this.f2449v = i;
                    PersonInfoSettingActivity.this.f2450w = i2;
                    PersonInfoSettingActivity.this.f2451x = i3;
                    String str = i + "-" + (i2 + 1) + "-" + PersonInfoSettingActivity.this.f2451x;
                    PersonInfoSettingActivity.this.f2448u.mUserInfo.setAge(Calendar.getInstance().get(1) - i);
                    PersonInfoSettingActivity.this.f2448u.mUserInfo.setBirthday(str);
                    PersonInfoSettingActivity.this.f2432e.setText(str + "(" + PersonInfoSettingActivity.this.f2448u.mUserInfo.getAge() + " age)");
                    Toast.makeText(PersonInfoSettingActivity.this, str, 1).show();
                }
            }, PersonInfoSettingActivity.this.f2449v, PersonInfoSettingActivity.this.f2450w, PersonInfoSettingActivity.this.f2451x).show();
            return true;
        }
    };

    /* renamed from: F */
    private RadioGroup.OnCheckedChangeListener f2427F = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            UserInfo userInfo;
            String str;
            UserInfo userInfo2;
            String str2;
            switch (radioGroup.getId()) {
                case R.id.setting_sex /*2131165447*/:
                    if (i == PersonInfoSettingActivity.this.f2444q.getId()) {
                        userInfo = PersonInfoSettingActivity.this.f2448u.mUserInfo;
                        str = "M";
                    } else if (i == PersonInfoSettingActivity.this.f2445r.getId()) {
                        userInfo = PersonInfoSettingActivity.this.f2448u.mUserInfo;
                        str = "G";
                    } else {
                        return;
                    }
                    userInfo.setSex(str);
                    return;
                case R.id.setting_smoker /*2131165448*/:
                    if (i == PersonInfoSettingActivity.this.f2446s.getId()) {
                        userInfo2 = PersonInfoSettingActivity.this.f2448u.mUserInfo;
                        str2 = "yes";
                    } else if (i == PersonInfoSettingActivity.this.f2447t.getId()) {
                        userInfo2 = PersonInfoSettingActivity.this.f2448u.mUserInfo;
                        str2 = "no";
                    } else {
                        return;
                    }
                    userInfo2.setSmoker(str2);
                    return;
                default:
                    return;
            }
        }
    };

    /* renamed from: G */
    private C0661b f2428G;

    /* renamed from: a */
    final String f2429a = "PersonInfoSettingActivity";

    /* renamed from: c */
    EditText f2430c;

    /* renamed from: d */
    EditText f2431d;

    /* renamed from: e */
    EditText f2432e;

    /* renamed from: f */
    EditText f2433f;

    /* renamed from: g */
    EditText f2434g;

    /* renamed from: h */
    EditText f2435h;

    /* renamed from: i */
    EditText f2436i;

    /* renamed from: j */
    EditText f2437j;

    /* renamed from: k */
    EditText f2438k;

    /* renamed from: l */
    EditText f2439l;

    /* renamed from: m */
    EditText f2440m;

    /* renamed from: n */
    TextView f2441n;

    /* renamed from: o */
    RadioGroup f2442o;

    /* renamed from: p */
    RadioGroup f2443p;

    /* renamed from: q */
    RadioButton f2444q;

    /* renamed from: r */
    RadioButton f2445r;

    /* renamed from: s */
    RadioButton f2446s;

    /* renamed from: t */
    RadioButton f2447t;

    /* renamed from: u */
    ECGApplication f2448u;

    /* renamed from: v */
    int f2449v;

    /* renamed from: w */
    int f2450w;

    /* renamed from: x */
    int f2451x;

    /* renamed from: y */
    SharedPreferences.Editor f2452y;

    /* renamed from: z */
    ProgressDialog f2453z;

    /* renamed from: com.hopetruly.ecg.activity.PersonInfoSettingActivity$a */
    class C0660a extends AsyncTask<String, Void, String> {
        C0660a() {
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
            if (str != null) {
                Log.i("PersonInfoSettingActivity", "result>>" + str);
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    int i = jSONArray.getInt(0);
                    if (i == 0) {
                        PersonInfoSettingActivity.this.f2423B.dismiss();
                        PersonInfoSettingActivity.this.m2443a(PersonInfoSettingActivity.this.f2448u.mUserInfo);
                    } else if (i == 1) {
                        int i2 = jSONArray.getInt(2);
                        if (i2 == 1) {
                            makeText = Toast.makeText(PersonInfoSettingActivity.this, PersonInfoSettingActivity.this.getString(R.string.p_username_err), 0);
                        } else if (i2 != 999) {
                            PersonInfoSettingActivity personInfoSettingActivity = PersonInfoSettingActivity.this;
                            makeText = Toast.makeText(personInfoSettingActivity, PersonInfoSettingActivity.this.getString(R.string.p_err_code) + i2, 0);
                        } else {
                            makeText = Toast.makeText(PersonInfoSettingActivity.this, PersonInfoSettingActivity.this.getString(R.string.p_pwd_err), 0);
                        }
                        makeText.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(PersonInfoSettingActivity.this, PersonInfoSettingActivity.this.getString(R.string.net_error), 0).show();
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.PersonInfoSettingActivity$b */
    class C0661b extends AsyncTask<UserInfo, Void, String> {
        C0661b() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(UserInfo... userInfoArr) {
            if (isCancelled()) {
                return null;
            }
            return MyHttpHelper.get_user_info(userInfoArr[0]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            Toast makeText;
            if (!isCancelled()) {
                PersonInfoSettingActivity.this.m2452f();
                if (str != null) {
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        int i = jSONArray.getInt(0);
                        if (i == 0) {
                            PersonInfoSettingActivity.this.f2452y.putString("birthday", PersonInfoSettingActivity.this.f2448u.mUserInfo.getBirthday());
                            PersonInfoSettingActivity.this.f2452y.putInt("age", PersonInfoSettingActivity.this.f2448u.mUserInfo.getAge());
                            PersonInfoSettingActivity.this.f2452y.putString("height", PersonInfoSettingActivity.this.f2448u.mUserInfo.getHeight());
                            PersonInfoSettingActivity.this.f2452y.putString("weight", PersonInfoSettingActivity.this.f2448u.mUserInfo.getWeight());
                            PersonInfoSettingActivity.this.f2452y.putString("medications", PersonInfoSettingActivity.this.f2448u.mUserInfo.getMedications());
                            PersonInfoSettingActivity.this.f2452y.putString("sex", PersonInfoSettingActivity.this.f2448u.mUserInfo.getSex());
                            PersonInfoSettingActivity.this.f2452y.putString("smoker", PersonInfoSettingActivity.this.f2448u.mUserInfo.getSmoker());
                            PersonInfoSettingActivity.this.f2452y.putString("profession", PersonInfoSettingActivity.this.f2448u.mUserInfo.getProfession());
                            PersonInfoSettingActivity.this.f2452y.putString("email", PersonInfoSettingActivity.this.f2448u.mUserInfo.getEmail());
                            PersonInfoSettingActivity.this.f2452y.putString("phone", PersonInfoSettingActivity.this.f2448u.mUserInfo.getPhone());
                            PersonInfoSettingActivity.this.f2452y.putString("address", PersonInfoSettingActivity.this.f2448u.mUserInfo.getAddress());
                            PersonInfoSettingActivity.this.f2452y.putString("firstName", PersonInfoSettingActivity.this.f2448u.mUserInfo.getFirstName());
                            PersonInfoSettingActivity.this.f2452y.putString("lastName", PersonInfoSettingActivity.this.f2448u.mUserInfo.getLastName());
                            PersonInfoSettingActivity.this.f2452y.commit();
                            makeText = Toast.makeText(PersonInfoSettingActivity.this, PersonInfoSettingActivity.this.getString(R.string.p_save_ok), 0);
                        } else if (i == 1 && jSONArray.getInt(2) == 998) {
                            PersonInfoSettingActivity.this.f2423B.showAtLocation(PersonInfoSettingActivity.this.findViewById(R.id.setting_user_firstname), 17, 0, 0);
                            return;
                        } else {
                            makeText = Toast.makeText(PersonInfoSettingActivity.this, PersonInfoSettingActivity.this.getString(R.string.p_save_fail), 0);
                        }
                        makeText.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            PersonInfoSettingActivity.this.m2444a(PersonInfoSettingActivity.this.getResources().getString(R.string.uploading));
        }
    }

    /* renamed from: a */
    private void m2440a() {
        this.f2430c = (EditText) findViewById(R.id.setting_user_firstname);
        if (this.f2448u.mUserInfo.getFirstName() == UserInfo.EMPTY) {
            this.f2430c.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2430c.setText(this.f2448u.mUserInfo.getFirstName());
        }
        this.f2431d = (EditText) findViewById(R.id.setting_user_lastname);
        if (this.f2448u.mUserInfo.getLastName() == UserInfo.EMPTY) {
            this.f2431d.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2431d.setText(this.f2448u.mUserInfo.getLastName());
        }
        this.f2441n = (TextView) findViewById(R.id.setting_user_name);
        if (this.f2448u.mUserInfo.getId() == UserInfo.EMPTY) {
            this.f2441n.setText(R.string.unknow);
        } else {
            this.f2441n.setText(this.f2448u.mUserInfo.getName());
        }
        this.f2432e = (EditText) findViewById(R.id.setting_user_date_of_brith);
        if (this.f2448u.mUserInfo.getBirthday() == UserInfo.EMPTY) {
            this.f2432e.setHint(getResources().getString(R.string.unknow));
            Calendar instance = Calendar.getInstance();
            this.f2449v = instance.get(1);
            this.f2450w = instance.get(2);
            this.f2451x = instance.get(5);
        } else {
            EditText editText = this.f2432e;
            editText.setText(this.f2448u.mUserInfo.getBirthday() + "(" + this.f2448u.mUserInfo.getAge() + " age)");
            String[] split = this.f2448u.mUserInfo.getBirthday().split("-");
            if (split.length == 3) {
                try {
                    this.f2449v = Integer.parseInt(split[0]);
                    this.f2450w = Integer.parseInt(split[1]) - 1;
                    this.f2451x = Integer.parseInt(split[2]);
                } catch (Exception unused) {
                }
            }
        }
        this.f2432e.setOnTouchListener(this.f2426E);
        this.f2438k = (EditText) findViewById(R.id.setting_user_profession);
        if (this.f2448u.mUserInfo.getProfession() == UserInfo.EMPTY) {
            this.f2438k.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2438k.setText(this.f2448u.mUserInfo.getProfession());
        }
        this.f2436i = (EditText) findViewById(R.id.setting_user_phone);
        if (this.f2448u.mUserInfo.getPhone() == UserInfo.EMPTY) {
            this.f2436i.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2436i.setText(this.f2448u.mUserInfo.getPhone());
        }
        this.f2437j = (EditText) findViewById(R.id.setting_user_email);
        if (this.f2448u.mUserInfo.getEmail() == UserInfo.EMPTY) {
            this.f2437j.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2437j.setText(this.f2448u.mUserInfo.getEmail());
        }
        this.f2439l = (EditText) findViewById(R.id.setting_user_addr);
        if (this.f2448u.mUserInfo.getAddress() == UserInfo.EMPTY) {
            this.f2439l.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2439l.setText(this.f2448u.mUserInfo.getAddress());
        }
        this.f2433f = (EditText) findViewById(R.id.setting_user_h);
        if (this.f2448u.mUserInfo.getHeight() == UserInfo.EMPTY) {
            this.f2433f.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2433f.setText(this.f2448u.mUserInfo.getHeight());
        }
        this.f2434g = (EditText) findViewById(R.id.setting_user_w);
        if (this.f2448u.mUserInfo.getWeight() == UserInfo.EMPTY) {
            this.f2434g.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2434g.setText(this.f2448u.mUserInfo.getWeight());
        }
        this.f2435h = (EditText) findViewById(R.id.setting_user_medications);
        if (this.f2448u.mUserInfo.getMedications() == UserInfo.EMPTY) {
            this.f2435h.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2435h.setText(this.f2448u.mUserInfo.getMedications());
        }
        this.f2440m = (EditText) findViewById(R.id.setting_user_emergency_phoneNum);
        if (this.f2448u.mUserInfo.getEmePhone() == UserInfo.EMPTY) {
            this.f2440m.setHint(getResources().getString(R.string.unknow));
        } else {
            this.f2440m.setText(this.f2448u.mUserInfo.getEmePhone());
        }
        this.f2442o = (RadioGroup) findViewById(R.id.setting_sex);
        this.f2442o.setOnCheckedChangeListener(this.f2427F);
        this.f2444q = (RadioButton) findViewById(R.id.sex_men);
        this.f2445r = (RadioButton) findViewById(R.id.sex_women);
        (this.f2448u.mUserInfo.getSex().equals("M") ? this.f2444q : this.f2445r).setChecked(true);
        this.f2443p = (RadioGroup) findViewById(R.id.setting_smoker);
        this.f2443p.setOnCheckedChangeListener(this.f2427F);
        this.f2446s = (RadioButton) findViewById(R.id.smoker_yes);
        this.f2447t = (RadioButton) findViewById(R.id.smoker_no);
        (this.f2448u.mUserInfo.getSmoker().equals("yes") ? this.f2446s : this.f2447t).setChecked(true);
        m2446b();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2443a(UserInfo userInfo) {
        if (this.f2428G == null || this.f2428G.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2428G = new C0661b();
            this.f2428G.execute(new UserInfo[]{userInfo});
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2444a(String str) {
        if (this.f2453z == null) {
            this.f2453z = new ProgressDialog(this);
        }
        this.f2453z.setMessage(str);
        this.f2453z.setCanceledOnTouchOutside(false);
        this.f2453z.show();
    }

    /* renamed from: b */
    private void m2446b() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
        this.f2423B = new PopupWindow(inflate, -2, -2, true);
        this.f2423B.setOutsideTouchable(false);
        this.f2423B.setFocusable(true);
        this.f2424C = (EditText) inflate.findViewById(R.id.login_user_name);
        this.f2424C.setText(this.f2448u.mUserInfo.getName());
        this.f2425D = (EditText) inflate.findViewById(R.id.login_user_pwd);
        this.f2425D.requestFocus();
        ((Button) inflate.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = PersonInfoSettingActivity.this.f2424C.getText().toString();
                String obj2 = PersonInfoSettingActivity.this.f2425D.getText().toString();
                new C0660a().execute(new String[]{obj, obj2});
                PersonInfoSettingActivity.this.m2444a(PersonInfoSettingActivity.this.getString(R.string.p_login_login));
            }
        });
        ((Button) inflate.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersonInfoSettingActivity.this.f2423B.dismiss();
            }
        });
    }

    /* renamed from: c */
    private void m2448c() {
        String obj = this.f2430c.getText().toString();
        if (obj == null || obj.length() <= 0) {
            Toast.makeText(this, getResources().getString(R.string.not_enter_f_name), 0).show();
            return;
        }
        this.f2448u.mUserInfo.setFirstName(obj);
        String obj2 = this.f2431d.getText().toString();
        if (obj2 == null || obj2.length() <= 0) {
            Toast.makeText(this, getResources().getString(R.string.not_enter_l_name), 0).show();
            return;
        }
        this.f2448u.mUserInfo.setLastName(obj2);
        if (this.f2448u.mUserInfo.getBirthday() == null) {
            Toast.makeText(this, getResources().getString(R.string.not_select_date), 0).show();
        } else if (this.f2448u.mUserInfo.getSex() == null) {
            Toast.makeText(this, getResources().getString(R.string.not_select_sex), 0).show();
        } else {
            String obj3 = this.f2436i.getText().toString();
            if (obj3 == null || obj3.length() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.not_enter_phone), 0).show();
                return;
            }
            this.f2448u.mUserInfo.setPhone(obj3);
            String obj4 = this.f2438k.getText().toString();
            if (obj4 == null || obj4.length() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.not_enter_profression), 0).show();
                return;
            }
            this.f2448u.mUserInfo.setProfession(obj4);
            String obj5 = this.f2437j.getText().toString();
            if (obj5 == null || obj5.length() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.not_enter_email), 0).show();
                return;
            }
            this.f2448u.mUserInfo.setEmail(obj5);
            String obj6 = this.f2439l.getText().toString();
            if (obj6 == null || obj6.length() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.not_enter_address), 0).show();
                return;
            }
            this.f2448u.mUserInfo.setAddress(obj6);
            String obj7 = this.f2433f.getText().toString();
            if (obj7 == null || obj7.length() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.not_enter_height), 0).show();
                return;
            }
            this.f2448u.mUserInfo.setHeight(obj7);
            String obj8 = this.f2434g.getText().toString();
            if (obj8 == null || obj8.length() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.not_enter_weight), 0).show();
                return;
            }
            this.f2448u.mUserInfo.setWeight(obj8);
            String obj9 = this.f2440m.getText().toString();
            if (obj9 != null && obj9.length() > 0) {
                this.f2448u.mUserInfo.setEmePhone(obj9);
            }
            m2443a(this.f2448u.mUserInfo);
        }
    }

    /* renamed from: d */
    private void m2449d() {
        this.f2430c.setEnabled(true);
        this.f2431d.setEnabled(true);
        this.f2432e.setEnabled(true);
        this.f2433f.setEnabled(true);
        this.f2434g.setEnabled(true);
        this.f2435h.setEnabled(true);
        this.f2436i.setEnabled(true);
        this.f2437j.setEnabled(true);
        this.f2438k.setEnabled(true);
        this.f2439l.setEnabled(true);
        this.f2440m.setEnabled(true);
        this.f2444q.setEnabled(true);
        this.f2445r.setEnabled(true);
        this.f2446s.setEnabled(true);
        this.f2447t.setEnabled(true);
    }

    /* renamed from: e */
    private void m2451e() {
        this.f2430c.setEnabled(false);
        this.f2431d.setEnabled(false);
        this.f2432e.setEnabled(false);
        this.f2433f.setEnabled(false);
        this.f2434g.setEnabled(false);
        this.f2435h.setEnabled(false);
        this.f2436i.setEnabled(false);
        this.f2437j.setEnabled(false);
        this.f2438k.setEnabled(false);
        this.f2439l.setEnabled(false);
        this.f2440m.setEnabled(false);
        this.f2444q.setEnabled(false);
        this.f2445r.setEnabled(false);
        this.f2446s.setEnabled(false);
        this.f2447t.setEnabled(false);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m2452f() {
        if (this.f2453z != null && this.f2453z.isShowing()) {
            this.f2453z.dismiss();
        }
    }

    public void onCreate(Bundle bundle) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(bundle);
        setContentView(R.layout.activity_person_info_setting);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem findItem;
        int i;
        getMenuInflater().inflate(R.menu.person_info, menu);
        if (this.f2422A) {
            findItem = menu.findItem(R.id.action_edit);
            i = R.string.Save;
        } else {
            findItem = menu.findItem(R.id.action_edit);
            i = R.string.Edit;
        }
        findItem.setTitle(getString(i));
        return true;
    }

    public void onDestroy() {
        LogUtils.logE("PersonInfoSettingActivity", "ondestory~~~~");
        m2452f();
        super.onDestroy();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onMenuItemSelected(i, menuItem);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_edit) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (!this.f2422A) {
            m2449d();
            this.f2422A = true;
            invalidateOptionsMenu();
            return true;
        }
        m2448c();
        this.f2422A = false;
        invalidateOptionsMenu();
        m2451e();
        return true;
    }

    public void onStart() {
        this.f2448u = (ECGApplication) getApplication();
        this.f2452y = this.f2448u.spPerson_info.edit();
        m2440a();
        if (this.f2422A) {
            m2449d();
        } else {
            m2451e();
        }
        super.onStart();
    }
}
