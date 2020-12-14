package com.hopetruly.ecg.entity;


import com.hopetruly.ecg.util.LogUtils;

/* renamed from: com.hopetruly.ecg.entity.f */
public class APPUploadFile {

    /* renamed from: a */
    private String ver;

    /* renamed from: b */
    private String f2834b;

    /* renamed from: a */
    public String mo2694a() {
        return this.ver;
    }

    /* renamed from: a */
    public void mo2695a(String str) {
        this.ver = str;
    }

    /* renamed from: b */
    public String mo2696b() {
        return this.f2834b;
    }

    /* renamed from: b */
    public void mo2697b(String str) {
        this.f2834b = str;
    }

    /* renamed from: c */
    public boolean compareVersion(String str) {
        if (this.ver == null) {
            return false;
        }
        LogUtils.logE("compareVersion", "ver:" + this.ver + "curVer:" + str);
        String[] split = this.ver.split("\\.");
        String[] split2 = str.split("\\.");
        for (int i = 0; i < split.length; i++) {
            int parseInt = Integer.parseInt(split[i]);
            LogUtils.logE("compareVersion", "tmp1:" + parseInt);
            if (i >= split2.length) {
                return true;
            }
            int parseInt2 = Integer.parseInt(split2[i]);
            LogUtils.logE("compareVersion", "tmp1:" + parseInt + "tmp2:" + parseInt2);
            if (parseInt > parseInt2) {
                return true;
            }
        }
        return false;
    }
}
