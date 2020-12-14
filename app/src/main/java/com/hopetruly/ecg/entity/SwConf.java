package com.hopetruly.ecg.entity;

/* renamed from: com.hopetruly.ecg.entity.e */
public class SwConf {

    /* renamed from: a */
    public static final String f2826a = null;

    /* renamed from: b */
    private int auto_login = 0;

    /* renamed from: c */
    private int f2828c = 0;

    /* renamed from: d */
    private int agree_declare = 1;

    /* renamed from: e */
    private int agree_flag = 0;

    /* renamed from: f */
    private int device_id_upload = 0;

    /* renamed from: g */
    private String f2832g;

    /* renamed from: a */
    public int getAuto_login() {
        return this.auto_login;
    }

    /* renamed from: a */
    public void setSW_AUTO_LOGIN(int i) {
        this.auto_login = i;
    }

    /* renamed from: a */
    public void mo2683a(String str) {
        this.f2832g = str;
    }

    /* renamed from: b */
    public int mo2684b() {
        return this.f2828c;
    }

    /* renamed from: b */
    public void setSW_SAVE_ACCOUNT_AND_PASSWORD(int i) {
        this.f2828c = i;
    }

    /* renamed from: c */
    public int getAgree_declare() {
        return this.agree_declare;
    }

    /* renamed from: c */
    public void setAgree_declare(int i) {
        this.agree_declare = i;
    }

    /* renamed from: d */
    public int getAgree_flag() {
        return this.agree_flag;
    }

    /* renamed from: d */
    public void setAgree_flag(int i) {
        this.agree_flag = i;
    }

    /* renamed from: e */
    public String mo2690e() {
        return this.f2832g;
    }

    /* renamed from: e */
    public void setDevice_id_upload(int i) {
        this.device_id_upload = i;
    }

    /* renamed from: f */
    public int getDevice_id_upload() {
        return this.device_id_upload;
    }

    /* renamed from: g */
    public String mo2693g() {
        return "MUIRHEAD.A3.2.2\u0000";
    }
}
