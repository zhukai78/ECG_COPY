package com.hopetruly.ecg.algorithm;

public class ECGAnaysis {
    static {
        System.loadLibrary("com_hopetruly_ecg_algorithm_ECGAnaysis");
    }

    public native void ECGAnaysisInit();

    public native void dealRR(int i, int i2, int i3);

    public native int getPeaks();

    public native void setECGData(float f);
}
