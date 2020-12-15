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
import com.hopetruly.part.net.MyHttpHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

public class PersonInfoRegisterActivity extends BaseActivity {

    /* renamed from: A */
    private RadioGroup.OnCheckedChangeListener rgOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            UserInfo userInfo;
            String str;
            UserInfo userInfo2;
            String str2;
            switch (radioGroup.getId()) {
                case R.id.setting_sex /*2131165447*/:
                    if (i == PersonInfoRegisterActivity.this.rb_sex_men.getId()) {
                        userInfo = PersonInfoRegisterActivity.this.mECGApplication.mUserInfo;
                        str = "M";
                    } else if (i == PersonInfoRegisterActivity.this.rb_sex_women.getId()) {
                        userInfo = PersonInfoRegisterActivity.this.mECGApplication.mUserInfo;
                        str = "G";
                    } else {
                        return;
                    }
                    userInfo.setSex(str);
                    return;
                case R.id.setting_smoker /*2131165448*/:
                    if (i == PersonInfoRegisterActivity.this.rb_smoker_yes.getId()) {
                        userInfo2 = PersonInfoRegisterActivity.this.mECGApplication.mUserInfo;
                        str2 = "yes";
                    } else if (i == PersonInfoRegisterActivity.this.rb_smoker_no.getId()) {
                        userInfo2 = PersonInfoRegisterActivity.this.mECGApplication.mUserInfo;
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
    private Get_net_user_infoAsynTask myupdatainfoAsynTask;

    /* renamed from: a */
    final String f2391a = "PersonInfoRegisterActivity";

    /* renamed from: c */
    EditText edt_setting_user_first_name;

    /* renamed from: d */
    EditText edt_setting_user_last_name;

    /* renamed from: e */
    EditText edt_setting_user_date_of_brith;

    /* renamed from: f */
    EditText edt_setting_user_h;

    /* renamed from: g */
    EditText edt_setting_user_w;

    /* renamed from: h */
    EditText edt_setting_user_medications;

    /* renamed from: i */
    EditText edt_setting_user_phone;

    /* renamed from: j */
    EditText edt_setting_user_email;

    /* renamed from: k */
    EditText edt_setting_user_profession;

    /* renamed from: l */
    EditText edt_setting_user_addr;

    /* renamed from: m */
    EditText edt_setting_user_emergency_phoneNum;

    /* renamed from: n */
    RadioGroup rg_setting_sex;

    /* renamed from: o */
    RadioGroup rg_setting_smoker;

    /* renamed from: p */
    RadioButton rb_sex_men;

    /* renamed from: q */
    RadioButton rb_sex_women;

    /* renamed from: r */
    RadioButton rb_smoker_yes;

    /* renamed from: s */
    RadioButton rb_smoker_no;

    /* renamed from: t */
    Button btn_register_person_info_commit;

    /* renamed from: u */
    int f2410u;

    /* renamed from: v */
    int f2411v;

    /* renamed from: w */
    int f2412w;

    /* renamed from: x */
    ECGApplication mECGApplication;

    /* renamed from: y */
    ProgressDialog uploadProgressDialog;

    /* renamed from: z */
    private View.OnTouchListener viewOnTouchListener = new View.OnTouchListener() {
        @SuppressLint("LongLogTag")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            Log.i("PersonInfoRegisterActivity", "datePickerDialog create!~~~");
            new DatePickerDialog(PersonInfoRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                    String str = i + "-" + i2 + "-" + i3;
                    PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.setAge(PersonInfoRegisterActivity.this.f2410u - i);
                    PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.setBirthday(str);
                    PersonInfoRegisterActivity.this.edt_setting_user_date_of_brith.setText(str + "(" + PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getAge() + " age)");
                    Toast.makeText(PersonInfoRegisterActivity.this, str, 1).show();
                }
            }, PersonInfoRegisterActivity.this.f2410u, PersonInfoRegisterActivity.this.f2411v, PersonInfoRegisterActivity.this.f2412w).show();
            return true;
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.PersonInfoRegisterActivity$a */
    class Get_net_user_infoAsynTask extends AsyncTask<UserInfo, Void, String> {
        Get_net_user_infoAsynTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(UserInfo... userInfoArr) {
            if (isCancelled()) {
                return null;
            }
            try {
                String a = MyHttpHelper.get_user_info(userInfoArr[0]);
                if (a == null || new JSONArray(a).getInt(0) != 0) {
                    return null;
                }
                return MyHttpHelper.get_q_user_info();
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
                PersonInfoRegisterActivity.this.dismissuploadProgressDialog();
                if (str != null) {
                    Log.i("PersonInfoRegisterActivity", str);
                    try {
                        JSONArray jSONArray = new JSONArray(str);
                        int i = jSONArray.getInt(0);
                        if (i == 0) {
                            SharedPreferences.Editor edit = PersonInfoRegisterActivity.this.getApplicationContext().getSharedPreferences("user_conf", 0).edit();
                            edit.putString("userId", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getId());
                            edit.putString("birthday", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getBirthday());
                            edit.putInt("age", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getAge());
                            edit.putString("height", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getHeight());
                            edit.putString("weight", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getWeight());
                            edit.putString("medications", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getMedications());
                            edit.putString("sex", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getSex());
                            edit.putString("smoker", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getSmoker());
                            edit.putString("profession", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getProfession());
                            edit.putString("email", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getEmail());
                            edit.putString("phone", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getPhone());
                            edit.putString("address", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getAddress());
                            edit.putString("firstName", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getFirstName());
                            edit.putString("lastName", PersonInfoRegisterActivity.this.mECGApplication.mUserInfo.getLastName());
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
                    Toast.makeText(PersonInfoRegisterActivity.this.getApplicationContext(), PersonInfoRegisterActivity.this.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            PersonInfoRegisterActivity.this.showUploadProgressDialog(PersonInfoRegisterActivity.this.getResources().getString(R.string.uploading));
        }
    }

    /* renamed from: a */
    private void updatainfoAsynTask(UserInfo userInfo) {
        if (this.myupdatainfoAsynTask == null || this.myupdatainfoAsynTask.getStatus() != AsyncTask.Status.RUNNING) {
            this.myupdatainfoAsynTask = new Get_net_user_infoAsynTask();
            this.myupdatainfoAsynTask.execute(new UserInfo[]{userInfo});
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void showUploadProgressDialog(String str) {
        this.uploadProgressDialog = new ProgressDialog(this);
        this.uploadProgressDialog.setMessage(str);
        this.uploadProgressDialog.show();
    }

    /* renamed from: b */
    private void initView() {
        this.edt_setting_user_first_name = (EditText) findViewById(R.id.setting_user_first_name);
        this.edt_setting_user_first_name.setHint(getResources().getString(R.string.f_name));
        this.edt_setting_user_last_name = (EditText) findViewById(R.id.setting_user_last_name);
        this.edt_setting_user_last_name.setHint(getResources().getString(R.string.l_name));
        this.edt_setting_user_date_of_brith = (EditText) findViewById(R.id.setting_user_date_of_brith);
        this.edt_setting_user_date_of_brith.setHint(getResources().getString(R.string.click_to_select_date));
        Calendar instance = Calendar.getInstance();
        this.f2410u = instance.get(1);
        this.f2411v = instance.get(2);
        this.f2412w = instance.get(5);
        this.edt_setting_user_date_of_brith.setOnTouchListener(this.viewOnTouchListener);
        this.edt_setting_user_profession = (EditText) findViewById(R.id.setting_user_profession);
        this.edt_setting_user_profession.setHint(getResources().getString(R.string.your_profression));
        this.edt_setting_user_phone = (EditText) findViewById(R.id.setting_user_phone);
        this.edt_setting_user_phone.setHint(getResources().getString(R.string.your_phone));
        this.edt_setting_user_email = (EditText) findViewById(R.id.setting_user_email);
        this.edt_setting_user_email.setHint(getString(R.string.your_Email));
        this.edt_setting_user_addr = (EditText) findViewById(R.id.setting_user_addr);
        this.edt_setting_user_addr.setHint(getResources().getString(R.string.your_address));
        this.edt_setting_user_h = (EditText) findViewById(R.id.setting_user_h);
        this.edt_setting_user_h.setHint(getResources().getString(R.string.your_height));
        this.edt_setting_user_w = (EditText) findViewById(R.id.setting_user_w);
        this.edt_setting_user_w.setHint(getResources().getString(R.string.your_weight));
        this.edt_setting_user_medications = (EditText) findViewById(R.id.setting_user_medications);
        this.edt_setting_user_medications.setHint(getResources().getString(R.string.medicine_tips));
        this.edt_setting_user_emergency_phoneNum = (EditText) findViewById(R.id.setting_user_emergency_phoneNum);
        this.rg_setting_sex = (RadioGroup) findViewById(R.id.setting_sex);
        this.rg_setting_sex.setOnCheckedChangeListener(this.rgOnCheckedChangeListener);
        this.rb_sex_men = (RadioButton) findViewById(R.id.sex_men);
        this.rb_sex_women = (RadioButton) findViewById(R.id.sex_women);
        (this.mECGApplication.mUserInfo.getSex().equals("M") ? this.rb_sex_men : this.rb_sex_women).setChecked(true);
        this.rg_setting_smoker = (RadioGroup) findViewById(R.id.setting_smoker);
        this.rg_setting_smoker.setOnCheckedChangeListener(this.rgOnCheckedChangeListener);
        this.rb_smoker_yes = (RadioButton) findViewById(R.id.smoker_yes);
        this.rb_smoker_no = (RadioButton) findViewById(R.id.smoker_no);
        this.rb_smoker_no.setChecked(true);
        this.mECGApplication.mUserInfo.setSmoker("no");
        this.btn_register_person_info_commit = (Button) findViewById(R.id.register_person_info_commit);
        this.btn_register_person_info_commit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersonInfoRegisterActivity.this.checkEdit();
            }
        });
    }

    /* renamed from: c */
    private void saveNomalInfo() {
        this.mECGApplication.mUserInfo.setFirstName("");
        this.mECGApplication.mUserInfo.setLastName("");
        this.mECGApplication.mUserInfo.setAge(0);
        this.mECGApplication.mUserInfo.setSex("");
        this.mECGApplication.mUserInfo.setBirthday("");
        this.mECGApplication.mUserInfo.setHeight("");
        this.mECGApplication.mUserInfo.setWeight("");
        this.mECGApplication.mUserInfo.setProfession("");
        this.mECGApplication.mUserInfo.setEmail("");
        this.mECGApplication.mUserInfo.setPhone("");
        this.mECGApplication.mUserInfo.setAddress("");
        SharedPreferences.Editor edit = this.mECGApplication.spPerson_info.edit();
        edit.putString("birthday", this.mECGApplication.mUserInfo.getBirthday());
        edit.putInt("age", this.mECGApplication.mUserInfo.getAge());
        edit.putString("height", this.mECGApplication.mUserInfo.getHeight());
        edit.putString("weight", this.mECGApplication.mUserInfo.getWeight());
        edit.putString("medications", this.mECGApplication.mUserInfo.getMedications());
        edit.putString("sex", this.mECGApplication.mUserInfo.getSex());
        edit.putString("smoker", this.mECGApplication.mUserInfo.getSmoker());
        edit.putString("profession", this.mECGApplication.mUserInfo.getProfession());
        edit.putString("email", this.mECGApplication.mUserInfo.getEmail());
        edit.putString("phone", this.mECGApplication.mUserInfo.getPhone());
        edit.putString("address", this.mECGApplication.mUserInfo.getAddress());
        edit.putString("firstName", this.mECGApplication.mUserInfo.getFirstName());
        edit.putString("lastName", this.mECGApplication.mUserInfo.getLastName());
        edit.commit();
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void dismissuploadProgressDialog() {
        this.uploadProgressDialog.dismiss();
    }

    /* renamed from: e */
    private void showTipInfoDialog() {
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
    public void checkEdit() {
        String obj = this.edt_setting_user_first_name.getText().toString();
        if (obj == null || obj.length() <= 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_f_name), Toast.LENGTH_LONG).show();
            return;
        }
        this.mECGApplication.mUserInfo.setFirstName(obj);
        String obj2 = this.edt_setting_user_last_name.getText().toString();
        if (obj2 == null || obj2.length() <= 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_l_name), Toast.LENGTH_LONG).show();
            return;
        }
        this.mECGApplication.mUserInfo.setLastName(obj2);
        if (this.mECGApplication.mUserInfo.getBirthday() == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_select_date), Toast.LENGTH_LONG).show();
        } else if (this.mECGApplication.mUserInfo.getSex() == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_select_sex), Toast.LENGTH_LONG).show();
        } else {
            String obj3 = this.edt_setting_user_phone.getText().toString();
            if (obj3 == null || obj3.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_phone), Toast.LENGTH_LONG).show();
                return;
            }
            this.mECGApplication.mUserInfo.setPhone(obj3);
            String obj4 = this.edt_setting_user_email.getText().toString();
            if (obj4 == null || obj4.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_email), Toast.LENGTH_LONG).show();
                return;
            }
            this.mECGApplication.mUserInfo.setEmail(obj4);
            String obj5 = this.edt_setting_user_profession.getText().toString();
            if (obj5 == null || obj5.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_profression), Toast.LENGTH_LONG).show();
                return;
            }
            this.mECGApplication.mUserInfo.setProfession(obj5);
            String obj6 = this.edt_setting_user_addr.getText().toString();
            if (obj6 == null || obj6.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_address), Toast.LENGTH_LONG).show();
                return;
            }
            this.mECGApplication.mUserInfo.setAddress(obj6);
            String obj7 = this.edt_setting_user_h.getText().toString();
            if (obj7 == null || obj7.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_height), Toast.LENGTH_LONG).show();
                return;
            }
            this.mECGApplication.mUserInfo.setHeight(obj7);
            String obj8 = this.edt_setting_user_w.getText().toString();
            if (obj8 == null || obj8.length() <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enter_weight), Toast.LENGTH_LONG).show();
                return;
            }
            this.mECGApplication.mUserInfo.setWeight(obj8);
            String obj9 = this.edt_setting_user_emergency_phoneNum.getText().toString();
            if (obj9 != null && obj9.length() > 0) {
                this.mECGApplication.mUserInfo.setEmePhone(obj9);
            }
            updatainfoAsynTask(this.mECGApplication.mUserInfo);
        }
    }

    public void onBackPressed() {
        showTipInfoDialog();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_person_info_register);
        this.mECGApplication = (ECGApplication) getApplication();
        initView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_info_register, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.myupdatainfoAsynTask != null && this.myupdatainfoAsynTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.myupdatainfoAsynTask.cancel(true);
            this.myupdatainfoAsynTask = null;
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_skip) {
            return super.onOptionsItemSelected(menuItem);
        }
        saveNomalInfo();
        startActivity(new Intent(this, FunChooseActivity.class));
        finish();
        return true;
    }
}
