package com.hopetruly.ecg.entity;

/* renamed from: com.hopetruly.ecg.entity.d */
public class C0751d {

    /* renamed from: a */
    private final int f2822a = 4;

    /* renamed from: b */
    private String[] f2823b = new String[4];

    /* renamed from: c */
    private boolean[] f2824c = new boolean[4];

    /* renamed from: d */
    private String f2825d;

    /* renamed from: a */
    public void mo2675a(String str) {
        this.f2825d = str;
    }

    /* renamed from: a */
    public void mo2676a(String str, int i) {
        if (i <= 3 && i >= 0) {
            this.f2823b[i] = str;
        }
    }

    /* renamed from: a */
    public void mo2677a(boolean z, int i) {
        this.f2824c[i] = z;
    }

    /* renamed from: a */
    public String[] mo2678a() {
        return this.f2823b;
    }

    /* renamed from: b */
    public boolean[] mo2679b() {
        return this.f2824c;
    }

    /* renamed from: c */
    public String mo2680c() {
        return this.f2825d;
    }
}
