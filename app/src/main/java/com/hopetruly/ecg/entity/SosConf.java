package com.hopetruly.ecg.entity;

/* renamed from: com.hopetruly.ecg.entity.d */
public class SosConf {

    /* renamed from: a */
    private final int f2822a = 4;

    /* renamed from: b */
    private String[] phoneStrs = new String[4];

    /* renamed from: c */
    private boolean[] phoneEnables = new boolean[4];

    /* renamed from: d */
    private String sos_custom_content;

    /* renamed from: a */
    public void setSOS_CUSTOM_CONTENT(String str) {
        this.sos_custom_content = str;
    }

    /* renamed from: a */
    public void setPhones(String str, int i) {
        if (i <= 3 && i >= 0) {
            this.phoneStrs[i] = str;
        }
    }

    /* renamed from: a */
    public void setPhonesEnable(boolean z, int i) {
        this.phoneEnables[i] = z;
    }

    /* renamed from: a */
    public String[] getPhoneStrs() {
        return this.phoneStrs;
    }

    /* renamed from: b */
    public boolean[] getPhoneEnables() {
        return this.phoneEnables;
    }

    /* renamed from: c */
    public String getSOS_CUSTOM_CONTENT() {
        return this.sos_custom_content;
    }
}
