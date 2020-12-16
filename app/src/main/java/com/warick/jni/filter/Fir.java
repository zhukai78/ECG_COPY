package com.warick.jni.filter;

public class Fir {

    /* renamed from: a */
    public static int Fir_0 = 0;

    /* renamed from: b */
    public static int f3072b = 1;

    /* renamed from: c */
    public static int f3073c = 2;

    /* renamed from: d */
    public static int f3074d = 3;

    /* renamed from: e */
    public static int Fir_4 = 4;

    /* renamed from: f */
    public static int Fir_5 = 5;

    /* renamed from: g */
    public static int Fir_6 = 6;

    static {
        System.loadLibrary("com_warick_jni_filter_Fir");
    }

    public static native double RealtimeFir(float f, int i, double[] dArr);

    public static native double RealtimeFir(int i, int i2, double[] dArr);

    /* renamed from: a */
    public static void setDoubles_0(int i, double[] dArr) {
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = 0.0d;
        }
    }

    public static native int getOrder(int i);
}
