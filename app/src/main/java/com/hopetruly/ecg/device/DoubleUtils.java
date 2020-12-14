package com.hopetruly.ecg.device;

/* renamed from: com.hopetruly.ecg.device.c */
public class DoubleUtils {

    /* renamed from: a */
    public double f2796a;

    /* renamed from: b */
    public double f2797b;

    /* renamed from: c */
    public double f2798c;

    public DoubleUtils(double d, double d2, double d3) {
        this.f2796a = d;
        this.f2797b = d2;
        this.f2798c = d3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DoubleUtils cVar = (DoubleUtils) obj;
        return Double.doubleToLongBits(this.f2796a) == Double.doubleToLongBits(cVar.f2796a) && Double.doubleToLongBits(this.f2797b) == Double.doubleToLongBits(cVar.f2797b) && Double.doubleToLongBits(this.f2798c) == Double.doubleToLongBits(cVar.f2798c);
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.f2796a);
        long doubleToLongBits2 = Double.doubleToLongBits(this.f2797b);
        int i = ((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        long doubleToLongBits3 = Double.doubleToLongBits(this.f2798c);
        return (31 * i) + ((int) (doubleToLongBits3 ^ (doubleToLongBits3 >>> 32)));
    }
}
