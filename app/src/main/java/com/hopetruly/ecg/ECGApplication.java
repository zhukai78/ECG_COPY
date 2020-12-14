package com.hopetruly.ecg;

import android.app.Application;
import android.content.SharedPreferences;

import com.hopetruly.ecg.entity.ECGConf;
import com.hopetruly.ecg.entity.FallConf;
import com.hopetruly.ecg.entity.PedometerConf;
import com.hopetruly.ecg.entity.SosConf;
import com.hopetruly.ecg.entity.SwConf;
import com.hopetruly.ecg.entity.Machine;
import com.hopetruly.ecg.entity.UserInfo;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.EcgUncaughtExceptionHandler;


public class ECGApplication extends Application {

    public Machine appMachine;

    public UserInfo mUserInfo = new UserInfo();

    public SharedPreferences spPerson_info;

    public SwConf mSwConf = new SwConf();

    public SharedPreferences spSw_conf;

    public ECGConf appECGConf = new ECGConf();

    public SharedPreferences spECG_conf;

    public PedometerConf appPedometerConf = new PedometerConf();

    /* renamed from: i */
    public SharedPreferences spPedometer_onf;

    /* renamed from: j */
    public FallConf appFallConf = new FallConf();

    /* renamed from: k */
    public SharedPreferences spFall_conf;

    /* renamed from: l */
    public SosConf appSosConf = new SosConf();

    /* renamed from: m */
    public SharedPreferences spSos_sms_conf;

    /* renamed from: n */
    public int f2093n = -1;

    /* renamed from: o */
    public MainService appMainService;

    /* renamed from: p */
    public EcgUncaughtExceptionHandler appEcgUncaughtExceptionHandler;

    public void onCreate() {
        this.spPerson_info = getSharedPreferences("person_info", 0);
        this.spSw_conf = getSharedPreferences("sw_conf", 0);
        this.spECG_conf = getSharedPreferences("ECG_conf", 0);
        this.spPedometer_onf = getSharedPreferences("pedometer_onf", 0);
        this.spFall_conf = getSharedPreferences("fall_conf", 0);
        this.spSos_sms_conf = getSharedPreferences("sos_sms_conf", 0);
        this.mUserInfo.setId(this.spPerson_info.getString("userId", UserInfo.EMPTY));
        this.mUserInfo.setName(this.spPerson_info.getString("userName", UserInfo.EMPTY));
        this.mUserInfo.setPassword(this.spPerson_info.getString("userPassword", UserInfo.EMPTY));
        this.mUserInfo.setBirthday(this.spPerson_info.getString("birthday", UserInfo.EMPTY));
        this.mUserInfo.setSex(this.spPerson_info.getString("sex", "men"));
        this.mUserInfo.setAge(this.spPerson_info.getInt("age", 0));
        this.mUserInfo.setHeight(this.spPerson_info.getString("height", UserInfo.EMPTY));
        this.mUserInfo.setWeight(this.spPerson_info.getString("weight", UserInfo.EMPTY));
        this.mUserInfo.setProfession(this.spPerson_info.getString("profession", UserInfo.EMPTY));
        this.mUserInfo.setEmail(this.spPerson_info.getString("email", UserInfo.EMPTY));
        this.mUserInfo.setPhone(this.spPerson_info.getString("phone", UserInfo.EMPTY));
        this.mUserInfo.setAddress(this.spPerson_info.getString("address", UserInfo.EMPTY));
        this.mUserInfo.setFirstName(this.spPerson_info.getString("firstName", UserInfo.EMPTY));
        this.mUserInfo.setLastName(this.spPerson_info.getString("lastName", UserInfo.EMPTY));
        this.mUserInfo.setEmePhone(this.spPerson_info.getString("emergencyPhone", UserInfo.EMPTY));
        this.mUserInfo.setMedications(this.spPerson_info.getString("medications", UserInfo.EMPTY));
        this.mUserInfo.setSmoker(this.spPerson_info.getString("smoker", "no"));
        this.mSwConf.setSW_AUTO_LOGIN(this.spSw_conf.getInt("SW_AUTO_LOGIN", 0));
        this.mSwConf.setSW_SAVE_ACCOUNT_AND_PASSWORD(this.spSw_conf.getInt("SW_SAVE_ACCOUNT_AND_PASSWORD", 0));
        this.appECGConf.setECG_MESURE_TIME(this.spECG_conf.getInt("ECG_MESURE_TIME", -1));
        this.appECGConf.setECG_AUTO_SAVE(this.spECG_conf.getInt("ECG_AUTO_SAVE", 0));
        this.appECGConf.setECG_AUTO_UPLOAD(this.spECG_conf.getInt("ECG_AUTO_UPLOAD", 0));
        this.appECGConf.setECG_REALTIME_UPLOAD(this.spECG_conf.getInt("ECG_REALTIME_UPLOAD", 0));
        this.appECGConf.setECG_WAVEFORM_ANALYSIS(this.spECG_conf.getInt("ECG_WAVEFORM_ANALYSIS", 0));
        this.appECGConf.setECG_SMS_ALARM(this.spECG_conf.getInt("ECG_SMS_ALARM", 0));
        this.appECGConf.setECG_ENABLE_MARK(this.spECG_conf.getInt("ECG_ENABLE_MARK", 0));
        this.appECGConf.setECG_ALARM_ENABLE(this.spECG_conf.getBoolean("ECG_ALARM_ENABLE", false));
        this.appECGConf.setECG_ALARM_RATE_MAX(this.spECG_conf.getInt("ECG_ALARM_RATE_MAX", 100));
        this.appECGConf.setECG_ALARM_RATE_MIN(this.spECG_conf.getInt("ECG_ALARM_RATE_MIN", 50));
        this.appECGConf.setECG_ALARM_TYPE(this.spECG_conf.getInt("ECG_ALARM_TYPE", 2));
        this.appECGConf.setECG_ALARM_DELAY(this.spECG_conf.getInt("ECG_ALARM_DELAY", 10));
        this.appECGConf.setECG_MARKING_PERIOD(this.spECG_conf.getInt("ECG_MARKING_PERIOD", 15));
        this.appPedometerConf.setSTEP_ENABLE_STEP(this.spPedometer_onf.getInt("STEP_ENABLE_STEP", 0));
        this.appPedometerConf.setSTEP_TARGET(this.spPedometer_onf.getLong("STEP_TARGET", 0));
        this.appFallConf.setFALL_ENABLE_FALL(this.spFall_conf.getInt("FALL_ENABLE_FALL", 0));
        this.appSosConf.setSOS_CUSTOM_CONTENT(this.spSos_sms_conf.getString("SOS_CUSTOM_CONTENT", ""));
        this.appSosConf.setPhones(this.spSos_sms_conf.getString("SOS_PHONE0", ""), 0);
        this.appSosConf.setPhones(this.spSos_sms_conf.getString("SOS_PHONE1", ""), 1);
        this.appSosConf.setPhones(this.spSos_sms_conf.getString("SOS_PHONE2", ""), 2);
        this.appSosConf.setPhones(this.spSos_sms_conf.getString("SOS_PHONE3", ""), 3);
        this.appSosConf.setPhonesEnable(this.spSos_sms_conf.getBoolean("SOS_PHONE0_ENABLE", false), 0);
        this.appSosConf.setPhonesEnable(this.spSos_sms_conf.getBoolean("SOS_PHONE1_ENABLE", false), 1);
        this.appSosConf.setPhonesEnable(this.spSos_sms_conf.getBoolean("SOS_PHONE2_ENABLE", false), 2);
        this.appSosConf.setPhonesEnable(this.spSos_sms_conf.getBoolean("SOS_PHONE3_ENABLE", false), 3);
        this.appEcgUncaughtExceptionHandler = EcgUncaughtExceptionHandler.init();
        this.appEcgUncaughtExceptionHandler.ready(getApplicationContext());
        super.onCreate();
    }

}
