package com.hopetruly.ecg.entity;

/* renamed from: com.hopetruly.ecg.entity.a */
public class ECGConf {

    /* renamed from: a */
    public static final String f2802a = null;

    /* renamed from: b */
    private int ecg_mesure_time = -1;

    /* renamed from: c */
    private int ecg_alarm_delay = 10;

    /* renamed from: d */
    private int ecg_auto_save = 1;

    /* renamed from: e */
    private int ecg_auto_upload = 0;

    /* renamed from: f */
    private int ecg_waveform_analysis = 0;

    /* renamed from: g */
    private int ecg_sms_alarm = 0;

    /* renamed from: h */
    private int ecg_enable_mark = 0;

    /* renamed from: i */
    private int ecg_realtime_upload = 0;

    /* renamed from: j */
    private boolean ecg_alarm_enable;

    /* renamed from: k */
    private int ecg_alarm_rate_max;

    /* renamed from: l */
    private int ecg_alarm_rate_min;

    /* renamed from: m */
    private int ecg_alarm_type;

    /* renamed from: n */
    private int ecg_marking_period;

    /* renamed from: a */
    public int getECG_MESURE_TIME() {
        return this.ecg_mesure_time;
    }

    /* renamed from: a */
    public void setECG_MESURE_TIME(int i) {
        this.ecg_mesure_time = i;
    }

    /* renamed from: a */
    public void setECG_ALARM_ENABLE(boolean z) {
        this.ecg_alarm_enable = z;
    }

    /* renamed from: b */
    public int getsetECG_AUTO_SAVE() {
        return this.ecg_auto_save;
    }

    /* renamed from: b */
    public void setECG_AUTO_SAVE(int i) {
        this.ecg_auto_save = i;
    }

    /* renamed from: c */
    public int getECG_AUTO_UPLOAD() {
        return this.ecg_auto_upload;
    }

    /* renamed from: c */
    public void setECG_AUTO_UPLOAD(int i) {
        this.ecg_auto_upload = i;
    }

    /* renamed from: d */
    public int getECG_REALTIME_UPLOAD() {
        return this.ecg_realtime_upload;
    }

    /* renamed from: d */
    public void setECG_REALTIME_UPLOAD(int i) {
        this.ecg_realtime_upload = i;
    }

    /* renamed from: e */
    public int getECG_ALARM_RATE_MAX() {
        return this.ecg_alarm_rate_max;
    }

    /* renamed from: e */
    public void setECG_ALARM_RATE_MAX(int i) {
        this.ecg_alarm_rate_max = i;
    }

    /* renamed from: f */
    public int getECG_ALARM_RATE_MIN() {
        return this.ecg_alarm_rate_min;
    }

    /* renamed from: f */
    public void setECG_ALARM_RATE_MIN(int i) {
        this.ecg_alarm_rate_min = i;
    }

    /* renamed from: g */
    public void setECG_ALARM_TYPE(int i) {
        this.ecg_alarm_type = i;
    }

    /* renamed from: g */
    public boolean getECG_ALARM_ENABLE() {
        return this.ecg_alarm_enable;
    }

    /* renamed from: h */
    public int getECG_ALARM_TYPE() {
        return this.ecg_alarm_type;
    }

    /* renamed from: h */
    public void setECG_WAVEFORM_ANALYSIS(int i) {
        this.ecg_waveform_analysis = i;
    }

    /* renamed from: i */
    public int getECG_WAVEFORM_ANALYSIS() {
        return this.ecg_waveform_analysis;
    }

    /* renamed from: i */
    public void setECG_SMS_ALARM(int i) {
        this.ecg_sms_alarm = i;
    }

    /* renamed from: j */
    public int getECG_SMS_ALARM() {
        return this.ecg_sms_alarm;
    }

    /* renamed from: j */
    public void setECG_ENABLE_MARK(int i) {
        this.ecg_enable_mark = i;
    }

    /* renamed from: k */
    public int getECG_ENABLE_MARK() {
        return this.ecg_enable_mark;
    }

    /* renamed from: k */
    public void setECG_ALARM_DELAY(int i) {
        this.ecg_alarm_delay = i;
    }

    /* renamed from: l */
    public int getECG_ALARM_DELAY() {
        return this.ecg_alarm_delay;
    }

    /* renamed from: l */
    public void setECG_MARKING_PERIOD(int i) {
        this.ecg_marking_period = i;
    }

    /* renamed from: m */
    public int getECG_MARKING_PERIOD() {
        return this.ecg_marking_period;
    }
}
