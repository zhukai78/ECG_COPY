package com.hopetruly.ecg.util;

import java.util.Arrays;

/* renamed from: com.hopetruly.ecg.util.h */
public class EcgParserUtils {

    /* renamed from: a */
    private int f2911a;

    /* renamed from: b */
    private int f2912b;

    /* renamed from: c */
    private int[] mEcgMakes;

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
        if (this.mEcgMakes != null) {
            this.f2911a = (int) (((double) i) / 0.25d);
            for (int i2 = 0; i2 < this.mEcgMakes.length; i2 += 2) {
                int i3 = i2 + 1;
                if (this.mEcgMakes[i3] > this.f2911a) {
                    this.mEcgMakes[i3] = this.f2911a;
                }
            }
        }
    }

    /* renamed from: a */
    public void addEcgMakes(int[] iArr) {
        this.mEcgMakes = iArr;
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
        this.mEcgMakes = this.mEcgMakes == null ? new int[2] : Arrays.copyOf(this.mEcgMakes, this.mEcgMakes.length + 2);
        this.mEcgMakes[this.mEcgMakes.length - 2] = i4;
        this.mEcgMakes[this.mEcgMakes.length - 1] = i6;
        return true;
    }

    /* renamed from: a */
    public int[] getmEcgMakes() {
        return this.mEcgMakes;
    }

    /* renamed from: b */
    public int getHalfLen(int i) {
        for (int length = this.mEcgMakes.length - 2; length > 0; length -= 2) {
            if (((double) i) >= ((double) this.mEcgMakes[length]) * 0.25d) {
                return length;
            }
        }
        return -2;
    }

    /* renamed from: b */
    public void clearData() {
        this.f2911a = 0;
        this.f2912b = 0;
        this.mEcgMakes = null;
    }

    /* renamed from: b */
    public int[] mo2783b(int i, int i2) {
        if (this.mEcgMakes == null) {
            return null;
        }
        int[] iArr = null;
        for (int i3 = 0; i3 < this.mEcgMakes.length; i3 += 2) {
            int i4 = (int) (((float) this.mEcgMakes[i3]) * 0.25f);
            int i5 = (int) (((float) this.mEcgMakes[i3 + 1]) * 0.25f);
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
        for (int i2 = 0; i2 < this.mEcgMakes.length - 2; i2 += 2) {
            if (((double) i) < ((double) this.mEcgMakes[i2]) * 0.25d) {
                return i2 - 2;
            }
        }
        return this.mEcgMakes.length - 4;
    }

    /* renamed from: c */
    public String creatMakeTimeStrBuff() {
        if (this.mEcgMakes == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < this.mEcgMakes.length) {
            int i2 = i + 2;
            if (i2 != this.mEcgMakes.length) {
                stringBuffer.append(this.mEcgMakes[i]);
                stringBuffer.append(",");
                stringBuffer.append(this.mEcgMakes[i + 1]);
                stringBuffer.append("|");
            } else if (i2 == this.mEcgMakes.length) {
                stringBuffer.append(this.mEcgMakes[i]);
                stringBuffer.append(",");
                stringBuffer.append(this.mEcgMakes[i + 1]);
            }
            i = i2;
        }
        return stringBuffer.toString();
    }
}
