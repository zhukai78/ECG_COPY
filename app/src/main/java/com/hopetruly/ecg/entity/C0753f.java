package com.hopetruly.ecg.entity;


import com.hopetruly.ecg.util.C0771g;

/* renamed from: com.hopetruly.ecg.entity.f */
public class C0753f {

    /* renamed from: a */
    private String f2833a;

    /* renamed from: b */
    private String f2834b;

    /* renamed from: a */
    public String mo2694a() {
        return this.f2833a;
    }

    /* renamed from: a */
    public void mo2695a(String str) {
        this.f2833a = str;
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
    public boolean mo2698c(String str) {
        if (this.f2833a == null) {
            return false;
        }
        C0771g.m2787d("compareVersion", "ver:" + this.f2833a + "curVer:" + str);
        String[] split = this.f2833a.split("\\.");
        String[] split2 = str.split("\\.");
        for (int i = 0; i < split.length; i++) {
            int parseInt = Integer.parseInt(split[i]);
            C0771g.m2787d("compareVersion", "tmp1:" + parseInt);
            if (i >= split2.length) {
                return true;
            }
            int parseInt2 = Integer.parseInt(split2[i]);
            C0771g.m2787d("compareVersion", "tmp1:" + parseInt + "tmp2:" + parseInt2);
            if (parseInt > parseInt2) {
                return true;
            }
        }
        return false;
    }
}
