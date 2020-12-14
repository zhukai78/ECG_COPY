package com.hopetruly.ecg.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.UserInfo;
import com.hopetruly.part.net.C0791b;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

public class PersonInfoRegisterActivity extends C0721a {

    /* renamed from: A */
    private RadioGroup.OnCheckedChangeListener f2389A = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            UserInfo userInfo;
            String str;
            UserInfo userInfo2;
            String str2;
            switch (radioGroup.getId()) {
                case R.id.setting_sex /*2131165447*/:
                    if (i == PersonInfoRegisterActivity.this.f2405p.getId()) {
                        userInfo = PersonInfoRegisterActivity.this.f2413x.f2081b;
                        str = "M";
                    } else if (i == PersonInfoRegisterActivity.this.f2406q.getId()) {
                        userInfo = PersonInfoRegisterActivity.this.f2413x.f2081b;
                        str = "G";
                    } else {
                        return;
                    }
                    userInfo.setSex(str);
                    return;
                case R.id.setting_smoker /*2131165448*/:
                    if (i == PersonInfoRegisterActivity.this.f2407r.getId()) {
                        userInfo2 = PersonInfoRegisterActivity.this.f2413x.f2081b;
                        str2 = "yes";
                    } else if (i == PersonInfoRegisterActivity.this.f2408s.getId()) {
                        userInfo2 = PersonInfoRegisterActivity.this.f2413x.f2081b;
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

    /* renamed from: B */
    private C0654a f2390B;

    /* renamed from: a */
    final String f2391a = "PersonInfoRegisterActivity";

    /* renamed from: c */
    EditText f2392c;

    /* renamed from: d */
    EditText f2393d;

    /* renamed from: e */
    EditText f2394e;

    /* renamed from: f */
    EditText f2395f;

    /* renamed from: g */
    EditText f2396g;

    /* renamed from: h */
    EditText f2397h;

    /* renamed from: i */
    EditText f2398i;

    /* renamed from: j */
    EditText f2399j;

    /* renamed from: k */
    EditText f2400k;

    /* renamed from: l */
    EditText f2401l;

    /* renamed from: m */
    EditText f2402m;

    /* renamed from: n */
    RadioGroup f2403n;

    /* renamed from: o */
    RadioGroup f2404o;

    /* renamed from: p */
    RadioButton f2405p;

    /* renamed from: q */
    RadioButton f2406q;

    /* renamed from: r */
    RadioButton f2407r;

    /* renamed from: s */
    RadioButton f2408s;

    /* renamed from: t */
    Button f2409t;

    /* renamed from: u */
    int f2410u;

    /* renamed from: v */
    int f2411v;

    /* renamed from: w */
    int f2412w;

    /* renamed from: x */
    ECGApplication f2413x;

    /* renamed from: y */
    ProgressDialog f2414y;

    /* renamed from: z */
    private View.OnTouchListener f2415z = new View.OnTouchListener() {
        @SuppressLint("LongLogTag")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            Log.i("PersonInfoRegisterActivity", "datePickerDialog create!~~~");
            new DatePickerDialog(PersonInfoRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                    String str = i + "-" + i2 + "-" + i3;
                    PersonInfoRegisterActivity.this.f2413x.f2081b.setAge(PersonInfoRegisterActivity.this.f2410u - i);
                    PersonInfoRegisterActivity.this.f2413x.f2081b.setBirthday(str);
                    PersonInfoRegisterActivity.this.f2394e.setText(str + "(" + PersonInfoRegisterActivity.this.f2413x.f2081b.getAge() + " age)");
                    Toast.makeText(PersonInfoRegisterActivity.this, str, 1).show();
                }
            }, PersonInfoRegisterActivity.this.f2410u, PersonInfoRegisterActivity.this.f2411v, PersonInfoRegisterActivity.this.f2412w).show();
            return true;
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.PersonInfoRegisterActivity$a */
    class C0654a extends AsyncTask<UserInfo, Void, String> {
        C0654a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(UserInfo... userInfoArr) {
            if (isCancelled()) {
                return null;
            }
            try {
                String a = C0791b.m2872a(userInfoArr[0]);
                if (a == null || new JSONArray(a).getInt(0) != 0) {
                    return null;
                }
                return C0791b.m2879b();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        @SuppressLint("LongLogTag")
        public void onPostExecute(String str) {
            Toast makeText;
            if (!isCancelled()) {
                PersonInfoRegisterActivity.this.m2434d();
                if (str != null) {
                    Log.i("PersonInfoRegisterActivity", str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        int i = jSONArray.getInt(0);
                        if (i == 0) {
                            SharedPreferences.Editor edit = PersonInfoRegisterActivity.this.getApplicationContext().getSharedPreferences("user_conf", 0).edit();
                            edit.putString("userId", PersonInfoRegisterActivity.this.f2413x.f2081b.getId());
                            edit.putString("birthday", PersonInfoRegisterActivity.this.f2413x.f2081b.getBirthday());
                            edit.putInt("age", PersonInfoRegisterActivity.this.f2413x.f2081b.getAge());
                            edit.putString("height", PersonInfoRegisterActivity.this.f2413x.f2081b.getHeight());
                            edit.putString("weight", PersonInfoRegisterActivity.this.f2413x.f2081b.getWeight());
                            edit.putString("medications", PersonInfoRegisterActivity.this.f2413x.f2081b.getMedications());
                            edit.putString("sex", PersonInfoRegisterActivity.this.f2413x.f2081b.getSex());
                            edit.putString("smoker", PersonInfoRegisterActivity.this.f2413x.f2081b.getSmoker());
                            edit.putString("profession", PersonInfoRegisterActivity.this.f2413x.f2081b.getProfession());
                            edit.putString("email", PersonInfoRegisterActivity.this.f2413x.f2081b.getEmail());
                            edit.putString("phone", PersonInfoRegisterActivity.this.f2413x.f2081b.getPhone());
                            edit.putString("address", PersonInfoRegisterActivity.this.f2413x.f2081b.getAddress());
                            edit.putString("firstName", PersonInfoRegisterActivity.this.f2413x.f2081b.getFirstName());
                            edit.putString("lastName", PersonInfoRegisterActivity.this.f2413x.f2081b.getLastName());
                            edit.commit();
                            PersonInfoRegisterActivity.this.setResult(-1, new Intent());
                            PersonInfoRegisterActivity.this.finish();
                            return;
                        }
                        if (jSONArray.getInt(2) != 1) {
                            Context applicationContext = PersonInfoRegisterActivity.this.getApplicationContext();
                            makeText = Toast.makeText(applicationContext, PersonInfoRegisterActivity.this.getString(R.string.p_err_code) + i, 0);
                        } else {
                            makeText = Toast.makeText(PersonInfoRegisterActivity.this.getApplicationContext(), PersonInfoRegisterActivity.this.getString(R.string.p_not_reg), 0);
                        }
                        makeText.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(PersonInfoRegisterActivity.this.getApplicationContext(), PersonInfoRegisterActivity.this.getResources().getString(R.string.net_error), 0).show();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            PersonInfoRegisterActivity.this.m2431a(PersonInfoRegisterActivity.this.getResources().getString(R.string.uploading));
        }
    }

    /* renamed from: a */
    private void m2430a(UserInfo userInfo) {
        if (this.f2390B == null || this.f2390B.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2390B = new C0654a();
            this.f2390B.execute(new UserInfo[]{userInfo});
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2431a(String str) {
        this.f2414y = new ProgressDialog(this);
        this.f2414y.setMessage(str);
        this.f2414y.show();
    }

    /* renamed from: b */
    private void m2432b() {
        this.f2392c = (EditText) findViewById(R.id.setting_user_first_name);
        this.f2392c.setHint(getResources().getString(R.string.f_name));
        this.f2393d = (EditText) findViewById(R.id.setting_user_last_name);
        this.f2393d.setHint(getResources().getString(R.string.l_name));
        this.f2394e = (EditText) findViewById(R.id.setting_user_date_of_brith);
        this.f2394e.setHint(getResources().getString(R.string.click_to_select_date));
        Calendar instance = Calendar.getInstance();
        this.f2410u = instance.get(1);
        this.f2411v = instance.get(2);
        this.f2412w = instance.get(5);
        this.f2394e.setOnTouchListener(this.f2415z);
        this.f2400k = (EditText) findViewById(R.id.setting_user_profession);
        this.f2400k.setHint(getResources().getString(R.string.your_profression));
        this.f2398i = (EditText) findViewById(R.id.setting_user_phone);
        this.f2398i.setHint(getResources().getString(R.string.your_phone));
        this.f2399j = (EditText) findViewById(R.id.setting_user_email);
        this.f2399j.setHint(getString(R.string.your_Email));
        this.f2401l = (EditText) findViewById(R.id.setting_user_addr);
        this.f2401l.setHint(getResources().getString(R.string.your_address));
        this.f2395f = (EditText) findViewById(R.id.setting_user_h);
        this.f2395f.setHint(getResources().getString(R.string.your_height));
        this.f2396g = (EditText) findViewById(R.id.setting_user_w);
        this.f2396g.setHint(getResources().getString(R.string.your_weight));
        this.f2397h = (EditText) findViewById(R.id.setting_user_medications);
        this.f2397h.setHint(getResources().getString(R.string.medicine_tips));
        this.f2402m = (EditText) findViewById(R.id.setting_user_emergency_phoneNum);
        this.f2403n = (RadioGroup) findViewById(R.id.setting_sex);
        this.f2403n.setOnCheckedChangeListener(this.f2389A);
        this.f2405p = (RadioButton) findViewById(R.id.sex_men);
        this.f2406q = (RadioButton) findViewById(R.id.sex_women);
        (this.f2413x.f2081b.getSex().equals("M") ? this.f2405p : this.f2406q).setChecked(true);
        this.f2404o = (RadioGroup) findViewById(R.id.setting_smoker);
        this.f2404o.setOnCheckedChangeListener(this.f2389A);
        this.f2407r = (RadioButton) findViewById(R.id.smoker_yes);
        this.f2408s = (RadioButton) findViewById(R.id.smoker_no);
        this.f2408s.setChecked(true);
        this.f2413x.f2081b.setSmoker("no");
        this.f2409t = (Button) findViewById(R.id.register_person_info_commit);
        this.f2409t.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersonInfoRegisterActivity.this.mo2270a();
            }
        });
    }

    /* renamed from: c */
    private void m2433c() {
        this.f2413x.f2081b.setFirstName("");
        this.f2413x.f2081b.setLastName("");
        this.f2413x.f2081b.setAge(0);
        this.f2413x.f2081b.setSex("");
        this.f2413x.f2081b.setBirthday("");
        this.f2413x.f2081b.setHeight("");
        this.f2413x.f2081b.setWeight("");
        this.f2413x.f2081b.setProfession("");
        this.f2413x.f2081b.setEmail("");
        this.f2413x.f2081b.setPhone("");
        this.f2413x.f2081b.setAddress("");
        SharedPreferences.Editor edit = this.f2413x.f2082c.edit();
        edit.putString("birthday", this.f2413x.f2081b.getBirthday());
        edit.putInt("age", this.f2413x.f2081b.getAge());
        edit.putString("height", this.f2413x.f2081b.getHeight());
        edit.putString("weight", this.f2413x.f2081b.getWeight());
        edit.putString("medications", this.f2413x.f2081b.getMedications());
        edit.putString("sex", this.f2413x.f2081b.getSex());
        edit.putString("smoker", this.f2413x.f2081b.getSmoker());
        edit.putString("profession", this.f2413x.f2081b.getProfession());
        edit.putString("email", this.f2413x.f2081b.getEmail());
        edit.putString("phone", this.f2413x.f2081b.getPhone());
        edit.putString("address", this.f2413x.f2081b.getAddress());
        edit.putString("firstName", this.f2413x.f2081b.getFirstName());
        edit.putString("lastName", this.f2413x.f2081b.getLastName());
        edit.commit();
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m2434d() {
        this.f2414y.dismiss();
    }

    /* renamed from: e */
    private void m2435e() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.info_important));
        builder.setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo2270a() {
        String obj = this.f2392c.getText().toString();
        if (obj == null || obj.length() <= 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_f_name), 0).show();
            return;
        }
        this.f2413x.f2081b.setFirstName(obj);
        String obj2 = this.f2393d.getText().toString();
        if (obj2 == null || obj2.length() <= 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_l_name), 0).show();
            return;
        }
        this.f2413x.f2081b.setLastName(obj2);
        if (this.f2413x.f2081b.getBirthday() == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_select_date), 0).show();
        } else if (this.f2413x.f2081b.getSex() == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_select_sex), 0).show();
        } else {
            String obj3 = this.f2398i.getText().toString();
            if (obj3 == null || obj3.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_phone), 0).show();
                return;
            }
            this.f2413x.f2081b.setPhone(obj3);
            String obj4 = this.f2399j.getText().toString();
            if (obj4 == null || obj4.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_email), 0).show();
                return;
            }
            this.f2413x.f2081b.setEmail(obj4);
            String obj5 = this.f2400k.getText().toString();
            if (obj5 == null || obj5.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_profression), 0).show();
                return;
            }
            this.f2413x.f2081b.setProfession(obj5);
            String obj6 = this.f2401l.getText().toString();
            if (obj6 == null || obj6.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_address), 0).show();
                return;
            }
            this.f2413x.f2081b.setAddress(obj6);
            String obj7 = this.f2395f.getText().toString();
            if (obj7 == null || obj7.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_height), 0).show();
                return;
            }
            this.f2413x.f2081b.setHeight(obj7);
            String obj8 = this.f2396g.getText().toString();
            if (obj8 == null || obj8.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_weight), 0).show();
                return;
            }
            this.f2413x.f2081b.setWeight(obj8);
            String obj9 = this.f2402m.getText().toString();
            if (obj9 != null && obj9.length() > 0) {
                this.f2413x.f2081b.setEmePhone(obj9);
            }
            m2430a(this.f2413x.f2081b);
        }
    }

    public void onBackPressed() {
        m2435e();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_person_info_register);
        this.f2413x = (ECGApplication) getApplication();
        m2432b();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_info_register, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.f2390B != null && this.f2390B.getStatus() == AsyncTask.Status.RUNNING) {
            this.f2390B.cancel(true);
            this.f2390B = null;
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_skip) {
            return super.onOptionsItemSelected(menuItem);
        }
        m2433c();
        startActivity(new Intent(this, FunChooseActivity.class));
        finish();
        return true;
    }
}
