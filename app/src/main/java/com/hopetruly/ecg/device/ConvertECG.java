package com.hopetruly.ecg.device;

/* renamed from: com.hopetruly.ecg.device.b */
public class ConvertECG {

    /* renamed from: a */
    public float[] fArr;

    /* renamed from: b */
    public int[] ecgArr;

    public ConvertECG(float[] fArr) {
        this.fArr = fArr;
        this.ecgArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            this.ecgArr[i] = (int) (this.fArr[i] * 1000.0f);
        }
    }
}
