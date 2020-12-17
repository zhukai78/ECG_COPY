package com.hopetruly.ecg.util;

/* renamed from: com.hopetruly.ecg.util.c */
public class DataParser {
    /* renamed from: a */
    public static byte IntToByte(int i) {
        return (byte) (i & 255);
    }

    /* renamed from: a */
    public static byte shortToByte(short s) {
        return (byte) (s & 255);
    }

    /* renamed from: a */
    public static short BytesToShort(byte b, byte b2) {
        return (short) ((b << 8) + (b2 & 255));
    }

    /* renamed from: b */
    public static byte m2759b(int i) {
        return (byte) ((i >> 8) & 255);
    }

    /* renamed from: b */
    public static byte m2760b(short s) {
        return (byte) (s >> 8);
    }

    /* renamed from: b */
    public static int parser(byte b, byte b2) {
        byte[] bArr = {b2, b, 0, 0};
        return ((bArr[2] & 255) << 16) | ((bArr[3] & 255) << 24) | ((bArr[1] & 255) << 8) | (bArr[0] & 255);
    }
}
