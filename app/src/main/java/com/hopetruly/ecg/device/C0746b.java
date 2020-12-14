package com.hopetruly.ecg.device;

/* renamed from: com.hopetruly.ecg.device.b */
public class C0746b {

    /* renamed from: a */
    public float[] f2794a;

    /* renamed from: b */
    public int[] f2795b;

    public C0746b(float[] fArr) {
        this.f2794a = fArr;
        this.f2795b = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            this.f2795b[i] = (int) (this.f2794a[i] * 1000.0f);
        }
    }
}
