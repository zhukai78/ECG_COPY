package com.hopetruly.ecg.util;

import java.util.Arrays;

/* renamed from: com.hopetruly.ecg.util.h */
public class C0772h {

    /* renamed from: a */
    private int f2911a;

    /* renamed from: b */
    private int f2912b;

    /* renamed from: c */
    private int[] f2913c;

    /* renamed from: b */
    private int[] m2788b(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int i = 0;
        while (i < iArr.length - 2) {
            int i2 = i + 1;
            i += 2;
            if (iArr[i2] > iArr[i]) {
                iArr[i2] = iArr[i] + 10;
            }
        }
        return iArr;
    }

    /* renamed from: a */
    public void mo2777a(int i) {
        if (this.f2913c != null) {
            this.f2911a = (int) (((double) i) / 0.25d);
            for (int i2 = 0; i2 < this.f2913c.length; i2 += 2) {
                int i3 = i2 + 1;
                if (this.f2913c[i3] > this.f2911a) {
                    this.f2913c[i3] = this.f2911a;
                }
            }
        }
    }

    /* renamed from: a */
    public void mo2778a(int[] iArr) {
        this.f2913c = iArr;
    }

    /* renamed from: a */
    public boolean mo2779a(int i, int i2) {
        int i3 = (int) (((double) i) / 0.25d);
        int i4 = 0;
        if (i3 - this.f2912b <= 1000) {
            return false;
        }
        this.f2912b = i3;
        int i5 = i2 * 1000;
        if (this.f2912b > i5) {
            i4 = this.f2912b - i5;
        }
        int i6 = this.f2912b + i5;
        this.f2913c = this.f2913c == null ? new int[2] : Arrays.copyOf(this.f2913c, this.f2913c.length + 2);
        this.f2913c[this.f2913c.length - 2] = i4;
        this.f2913c[this.f2913c.length - 1] = i6;
        return true;
    }

    /* renamed from: a */
    public int[] mo2780a() {
        return this.f2913c;
    }

    /* renamed from: b */
    public int mo2781b(int i) {
        for (int length = this.f2913c.length - 2; length > 0; length -= 2) {
            if (((double) i) >= ((double) this.f2913c[length]) * 0.25d) {
                return length;
            }
        }
        return -2;
    }

    /* renamed from: b */
    public void mo2782b() {
        this.f2911a = 0;
        this.f2912b = 0;
        this.f2913c = null;
    }

    /* renamed from: b */
    public int[] mo2783b(int i, int i2) {
        if (this.f2913c == null) {
            return null;
        }
        int[] iArr = null;
        for (int i3 = 0; i3 < this.f2913c.length; i3 += 2) {
            int i4 = (int) (((float) this.f2913c[i3]) * 0.25f);
            int i5 = (int) (((float) this.f2913c[i3 + 1]) * 0.25f);
            if (i5 > i && i4 < i2) {
                iArr = iArr == null ? new int[2] : Arrays.copyOf(iArr, iArr.length + 2);
                if (i >= i4) {
                    iArr[iArr.length - 2] = 0;
                } else {
                    iArr[iArr.length - 2] = i4 - i;
                }
                if (i2 <= i5) {
                    iArr[iArr.length - 1] = i2 - i;
                } else {
                    iArr[iArr.length - 1] = i5 - i;
                }
            }
        }
        return m2788b(iArr);
    }

    /* renamed from: c */
    public int mo2784c(int i) {
        for (int i2 = 0; i2 < this.f2913c.length - 2; i2 += 2) {
            if (((double) i) < ((double) this.f2913c[i2]) * 0.25d) {
                return i2 - 2;
            }
        }
        return this.f2913c.length - 4;
    }

    /* renamed from: c */
    public String mo2785c() {
        if (this.f2913c == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < this.f2913c.length) {
            int i2 = i + 2;
            if (i2 != this.f2913c.length) {
                stringBuffer.append(this.f2913c[i]);
                stringBuffer.append(",");
                stringBuffer.append(this.f2913c[i + 1]);
                stringBuffer.append("|");
            } else if (i2 == this.f2913c.length) {
                stringBuffer.append(this.f2913c[i]);
                stringBuffer.append(",");
                stringBuffer.append(this.f2913c[i + 1]);
            }
            i = i2;
        }
        return stringBuffer.toString();
    }
}
