package com.hopetruly.ecg.algorithm;


import com.warick.jni.filter.Fir;

public class HeartRateCounter3 {

    /* renamed from: a */
    private double[] f2720a = new double[Fir.getOrder(Fir.Fir_4)];

    static {
        System.loadLibrary("com_hopetruly_ecg_algorithm_HeartRateCounter3");
    }

    public HeartRateCounter3() {
        Fir.setDoubles_0(Fir.getOrder(Fir.Fir_4), this.f2720a);
    }

    /* renamed from: a */
    public int mo2438a() {
        return Fir.getOrder(Fir.Fir_4);
    }

    /* renamed from: a */
    public void mo2439a(float f) {
        setECG((float) Fir.RealtimeFir(f, Fir.Fir_4, this.f2720a));
    }

    public native int getAvgHr();

    public native int getHeartRate();

    public native byte getHrStatus();

    public native int getRRWidth();

    public native int getRWaveOffset();

    public native int getSWaveOffset();

    public native void init();

    public native void setECG(float f);
}
