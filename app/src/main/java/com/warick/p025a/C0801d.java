package com.warick.p025a;

import android.content.Context;

//import com.hexin.ecg_hexin_bio.baidu.location.BDLocation;
//import com.hexin.ecg_hexin_bio.baidu.location.C0435a1;


/* renamed from: com.warick.a.d */
public class C0801d {

    /* renamed from: a */
    private static C0801d f3006a;

    /* renamed from: b */
    private final int f3007b = 15;

    /* renamed from: c */
    private final int f3008c = 10;

    /* renamed from: d */
    private final int f3009d = 5;

    /* renamed from: e */
    private final int f3010e = 0;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public String f3011f;

    /* renamed from: g */
    private C0795b f3012g;

    /* renamed from: h */
//    private C0792a f3013h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public C0800c[] f3014i = {null, null, null, null};
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int[] f3015j = {15, 10, 5, 0};

    /* renamed from: k */
    private C0804a f3016k = null;

    /* renamed from: l */
    private C0795b.C0799a f3017l = new C0795b.C0799a() {
        /* renamed from: a */
        public void mo2880a(double d, double d2) {
            if (C0801d.this.f3014i[0] == null) {
                C0801d.this.f3014i[0] = new C0800c();
            }
            C0801d.this.f3014i[0].mo2884a(100);
            C0801d.this.f3014i[0].mo2886b(d2);
            C0801d.this.f3014i[0].mo2883a(d);
            C0801d.this.f3015j[1] = 10;
            C0801d.this.f3015j[2] = 5;
            C0801d.this.f3015j[3] = 0;
            C0801d.this.m2917h();
        }

        /* renamed from: b */
        public void mo2881b(double d, double d2) {
            if (C0801d.this.f3014i[1] == null) {
                C0801d.this.f3014i[1] = new C0800c();
            }
//            C0801d.this.f3014i[1].mo2884a((int) C0435a1.f1319m);
            C0801d.this.f3014i[1].mo2886b(d2);
            C0801d.this.f3014i[1].mo2883a(d);
            if (C0801d.this.f3015j[1] < 16) {
                int[] b = C0801d.this.f3015j;
                b[1] = b[1] + 1;
            }
            C0801d.this.f3015j[2] = 5;
            C0801d.this.f3015j[3] = 0;
            C0801d.this.m2917h();
        }
    };

    /* renamed from: m */
//    private C0792a.C0794b f3018m = new C0792a.C0794b() {
//        /* renamed from: a */
//        public void mo2867a(BDLocation bDLocation) {
//            if (bDLocation.getLocType() == 61) {
//                if (C0801d.this.f3014i[2] == null) {
//                    C0801d.this.f3014i[2] = new C0800c();
//                }
//                C0801d.this.f3014i[2].mo2884a(200);
//                C0801d.this.f3014i[2].mo2883a(bDLocation.getLongitude());
//                C0801d.this.f3014i[2].mo2886b(bDLocation.getLatitude());
//                if (C0801d.this.f3015j[2] < 17) {
//                    int[] b = C0801d.this.f3015j;
//                    b[2] = b[2] + 1;
//                }
//                C0801d.this.f3015j[3] = 0;
//            } else if (bDLocation.getLocType() == 161) {
//                if (C0801d.this.f3014i[3] == null) {
//                    C0801d.this.f3014i[3] = new C0800c();
//                }
//                C0801d.this.f3014i[3].mo2884a(210);
//                C0801d.this.f3014i[3].mo2883a(bDLocation.getLongitude());
//                C0801d.this.f3014i[3].mo2886b(bDLocation.getLatitude());
//                if (C0801d.this.f3015j[3] < 18) {
//                    int[] b2 = C0801d.this.f3015j;
//                    b2[3] = b2[3] + 1;
//                }
//            }
//            if (bDLocation.hasAddr()) {
//                String unused = C0801d.this.f3011f = bDLocation.getAddrStr();
//            }
//            C0801d.this.m2917h();
//        }
//    };

    /* renamed from: com.warick.a.d$a */
    public interface C0804a {
        /* renamed from: a */
        void mo2755a(double d, double d2, String str);
    }

    private C0801d(Context context) {
        this.f3012g = new C0795b(context);
//        this.f3013h = new C0792a(context);
        this.f3011f = "";
    }

    /* renamed from: a */
    public static boolean m2908a() {
        if (f3006a == null) {
            return false;
        }
        f3006a.f3012g.mo2869a(f3006a.f3017l);
//        f3006a.f3013h.mo2865a(f3006a.f3018m);
//        f3006a.f3013h.mo2864a();
        f3006a.mo2888c();
        return true;
    }

    /* renamed from: a */
    private boolean m2909a(int i) {
        if (i > 3 || i < 0) {
            return false;
        }
        boolean z = true;
        for (int i2 = 0; i2 < 4; i2++) {
            if (this.f3015j[i] < this.f3015j[i2]) {
                z = false;
            }
        }
        return z;
    }

    /* renamed from: a */
    public static boolean m2910a(Context context) {
        if (f3006a == null) {
            f3006a = new C0801d(context);
        }
        return f3006a.f3012g.mo2870b();
    }

    /* renamed from: b */
    public static void m2912b() {
        if (f3006a != null) {
//            f3006a.f3013h.mo2866b();
            f3006a.f3012g.mo2868a();
        }
    }

    /* renamed from: d */
    public static void m2915d() {
        m2912b();
        f3006a = null;
    }

    /* renamed from: e */
    public static C0801d m2916e() {
        return f3006a;
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public void m2917h() {
        C0800c g = mo2890g();
        if (this.f3016k == null) {
            return;
        }
        if (g != null) {
            this.f3016k.mo2755a(g.mo2882a(), g.mo2885b(), this.f3011f);
        } else {
            this.f3016k.mo2755a(0.0d, 0.0d, (String) null);
        }
    }

    /* renamed from: a */
    public void mo2887a(C0804a aVar) {
        this.f3016k = aVar;
    }

    /* renamed from: c */
    public void mo2888c() {
        this.f3011f = "";
        this.f3014i[0] = null;
        this.f3014i[1] = null;
        this.f3014i[2] = null;
        this.f3014i[3] = null;
    }

    /* renamed from: f */
    public String mo2889f() {
        return this.f3011f;
    }

    /* renamed from: g */
    public C0800c mo2890g() {
        if (this.f3014i[0] != null && m2909a(0)) {
            return this.f3014i[0];
        }
        if (this.f3014i[1] != null && m2909a(1)) {
            return this.f3014i[1];
        }
        if (this.f3014i[2] != null && m2909a(2)) {
            return this.f3014i[2];
        }
        if (this.f3014i[3] != null) {
            return this.f3014i[3];
        }
        return null;
    }
}
