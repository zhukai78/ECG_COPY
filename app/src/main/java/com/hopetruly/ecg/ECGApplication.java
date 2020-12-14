package com.hopetruly.ecg;

import android.app.Application;
import android.content.SharedPreferences;

import com.hopetruly.ecg.entity.C0748a;
import com.hopetruly.ecg.entity.C0749b;
import com.hopetruly.ecg.entity.C0750c;
import com.hopetruly.ecg.entity.C0751d;
import com.hopetruly.ecg.entity.C0752e;
import com.hopetruly.ecg.entity.Machine;
import com.hopetruly.ecg.entity.UserInfo;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.C0768d;


public class ECGApplication extends Application {

    public Machine f2080a;

    public UserInfo f2081b = new UserInfo();

    public SharedPreferences f2082c;

    public C0752e f2083d = new C0752e();

    public SharedPreferences f2084e;

    public C0748a f2085f = new C0748a();

    public SharedPreferences f2086g;

    public C0750c f2087h = new C0750c();

    /* renamed from: i */
    public SharedPreferences f2088i;

    /* renamed from: j */
    public C0749b f2089j = new C0749b();

    /* renamed from: k */
    public SharedPreferences f2090k;

    /* renamed from: l */
    public C0751d f2091l = new C0751d();

    /* renamed from: m */
    public SharedPreferences f2092m;

    /* renamed from: n */
    public int f2093n = -1;

    /* renamed from: o */
    public MainService f2094o;

    /* renamed from: p */
    public C0768d f2095p;

    public void onCreate() {
        this.f2082c = getSharedPreferences("person_info", 0);
        this.f2084e = getSharedPreferences("sw_conf", 0);
        this.f2086g = getSharedPreferences("ECG_conf", 0);
        this.f2088i = getSharedPreferences("pedometer_onf", 0);
        this.f2090k = getSharedPreferences("fall_conf", 0);
        this.f2092m = getSharedPreferences("sos_sms_conf", 0);
        this.f2081b.setId(this.f2082c.getString("userId", UserInfo.EMPTY));
        this.f2081b.setName(this.f2082c.getString("userName", UserInfo.EMPTY));
        this.f2081b.setPassword(this.f2082c.getString("userPassword", UserInfo.EMPTY));
        this.f2081b.setBirthday(this.f2082c.getString("birthday", UserInfo.EMPTY));
        this.f2081b.setSex(this.f2082c.getString("sex", "men"));
        this.f2081b.setAge(this.f2082c.getInt("age", 0));
        this.f2081b.setHeight(this.f2082c.getString("height", UserInfo.EMPTY));
        this.f2081b.setWeight(this.f2082c.getString("weight", UserInfo.EMPTY));
        this.f2081b.setProfession(this.f2082c.getString("profession", UserInfo.EMPTY));
        this.f2081b.setEmail(this.f2082c.getString("email", UserInfo.EMPTY));
        this.f2081b.setPhone(this.f2082c.getString("phone", UserInfo.EMPTY));
        this.f2081b.setAddress(this.f2082c.getString("address", UserInfo.EMPTY));
        this.f2081b.setFirstName(this.f2082c.getString("firstName", UserInfo.EMPTY));
        this.f2081b.setLastName(this.f2082c.getString("lastName", UserInfo.EMPTY));
        this.f2081b.setEmePhone(this.f2082c.getString("emergencyPhone", UserInfo.EMPTY));
        this.f2081b.setMedications(this.f2082c.getString("medications", UserInfo.EMPTY));
        this.f2081b.setSmoker(this.f2082c.getString("smoker", "no"));
        this.f2083d.mo2682a(this.f2084e.getInt("SW_AUTO_LOGIN", 0));
        this.f2083d.mo2685b(this.f2084e.getInt("SW_SAVE_ACCOUNT_AND_PASSWORD", 0));
        this.f2085f.mo2642a(this.f2086g.getInt("ECG_MESURE_TIME", -1));
        this.f2085f.mo2645b(this.f2086g.getInt("ECG_AUTO_SAVE", 0));
        this.f2085f.mo2647c(this.f2086g.getInt("ECG_AUTO_UPLOAD", 0));
        this.f2085f.mo2649d(this.f2086g.getInt("ECG_REALTIME_UPLOAD", 0));
        this.f2085f.mo2657h(this.f2086g.getInt("ECG_WAVEFORM_ANALYSIS", 0));
        this.f2085f.mo2659i(this.f2086g.getInt("ECG_SMS_ALARM", 0));
        this.f2085f.mo2661j(this.f2086g.getInt("ECG_ENABLE_MARK", 0));
        this.f2085f.mo2643a(this.f2086g.getBoolean("ECG_ALARM_ENABLE", false));
        this.f2085f.mo2651e(this.f2086g.getInt("ECG_ALARM_RATE_MAX", 100));
        this.f2085f.mo2653f(this.f2086g.getInt("ECG_ALARM_RATE_MIN", 50));
        this.f2085f.mo2654g(this.f2086g.getInt("ECG_ALARM_TYPE", 2));
        this.f2085f.mo2663k(this.f2086g.getInt("ECG_ALARM_DELAY", 10));
        this.f2085f.mo2665l(this.f2086g.getInt("ECG_MARKING_PERIOD", 15));
        this.f2087h.mo2670a(this.f2088i.getInt("STEP_ENABLE_STEP", 0));
        this.f2087h.mo2671a(this.f2088i.getLong("STEP_TARGET", 0));
        this.f2089j.mo2668a(this.f2090k.getInt("FALL_ENABLE_FALL", 0));
        this.f2091l.mo2675a(this.f2092m.getString("SOS_CUSTOM_CONTENT", ""));
        this.f2091l.mo2676a(this.f2092m.getString("SOS_PHONE0", ""), 0);
        this.f2091l.mo2676a(this.f2092m.getString("SOS_PHONE1", ""), 1);
        this.f2091l.mo2676a(this.f2092m.getString("SOS_PHONE2", ""), 2);
        this.f2091l.mo2676a(this.f2092m.getString("SOS_PHONE3", ""), 3);
        this.f2091l.mo2677a(this.f2092m.getBoolean("SOS_PHONE0_ENABLE", false), 0);
        this.f2091l.mo2677a(this.f2092m.getBoolean("SOS_PHONE1_ENABLE", false), 1);
        this.f2091l.mo2677a(this.f2092m.getBoolean("SOS_PHONE2_ENABLE", false), 2);
        this.f2091l.mo2677a(this.f2092m.getBoolean("SOS_PHONE3_ENABLE", false), 3);
        this.f2095p = C0768d.m2762a();
        this.f2095p.mo2771a(getApplicationContext());
        super.onCreate();
    }

}
