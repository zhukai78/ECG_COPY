package com.warick.p025a;

import android.content.Context;

//import com.hexin.ecg_hexin_bio.baidu.location.BDLocation;
//import com.hexin.ecg_hexin_bio.baidu.location.C0435a1;


/* renamed from: com.warick.a.d */
public class GpsManagerHelper {

    /* renamed from: a */
    private static GpsManagerHelper mGpsManagerHelper;

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
    private GpsManager mGpsManager;

    /* renamed from: h */
//    private C0792a f3013h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public GpsBean[] mGpsBeans = {null, null, null, null};
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int[] arrs = {15, 10, 5, 0};

    /* renamed from: k */
    private OnGpsListener mOnGpsListener = null;

    /* renamed from: l */
    private GpsManager.OnLocationChange mOnLocationChange = new GpsManager.OnLocationChange() {
        /* renamed from: a */
        public void onLastKnownLocation(double d, double d2) {
            if (GpsManagerHelper.this.mGpsBeans[0] == null) {
                GpsManagerHelper.this.mGpsBeans[0] = new GpsBean();
            }
            GpsManagerHelper.this.mGpsBeans[0].mo2884a(100);
            GpsManagerHelper.this.mGpsBeans[0].mo2886b(d2);
            GpsManagerHelper.this.mGpsBeans[0].mo2883a(d);
            GpsManagerHelper.this.arrs[1] = 10;
            GpsManagerHelper.this.arrs[2] = 5;
            GpsManagerHelper.this.arrs[3] = 0;
            GpsManagerHelper.this.setGpsBeanData();
        }

        /* renamed from: b */
        public void onNowKnownLocation(double d, double d2) {
            if (GpsManagerHelper.this.mGpsBeans[1] == null) {
                GpsManagerHelper.this.mGpsBeans[1] = new GpsBean();
            }
//            C0801d.this.mGpsBeans[1].mo2884a((int) C0435a1.f1319m);
            GpsManagerHelper.this.mGpsBeans[1].mo2886b(d2);
            GpsManagerHelper.this.mGpsBeans[1].mo2883a(d);
            if (GpsManagerHelper.this.arrs[1] < 16) {
                int[] b = GpsManagerHelper.this.arrs;
                b[1] = b[1] + 1;
            }
            GpsManagerHelper.this.arrs[2] = 5;
            GpsManagerHelper.this.arrs[3] = 0;
            GpsManagerHelper.this.setGpsBeanData();
        }
    };

    /* renamed from: m */
//    private C0792a.C0794b f3018m = new C0792a.C0794b() {
//        /* renamed from: a */
//        public void mo2867a(BDLocation bDLocation) {
//            if (bDLocation.getLocType() == 61) {
//                if (C0801d.this.mGpsBeans[2] == null) {
//                    C0801d.this.mGpsBeans[2] = new GpsBean();
//                }
//                C0801d.this.mGpsBeans[2].mo2884a(200);
//                C0801d.this.mGpsBeans[2].mo2883a(bDLocation.getLongitude());
//                C0801d.this.mGpsBeans[2].mo2886b(bDLocation.getLatitude());
//                if (C0801d.this.arrs[2] < 17) {
//                    int[] b = C0801d.this.arrs;
//                    b[2] = b[2] + 1;
//                }
//                C0801d.this.arrs[3] = 0;
//            } else if (bDLocation.getLocType() == 161) {
//                if (C0801d.this.mGpsBeans[3] == null) {
//                    C0801d.this.mGpsBeans[3] = new GpsBean();
//                }
//                C0801d.this.mGpsBeans[3].mo2884a(210);
//                C0801d.this.mGpsBeans[3].mo2883a(bDLocation.getLongitude());
//                C0801d.this.mGpsBeans[3].mo2886b(bDLocation.getLatitude());
//                if (C0801d.this.arrs[3] < 18) {
//                    int[] b2 = C0801d.this.arrs;
//                    b2[3] = b2[3] + 1;
//                }
//            }
//            if (bDLocation.hasAddr()) {
//                String unused = C0801d.this.f3011f = bDLocation.getAddrStr();
//            }
//            C0801d.this.setGpsBeanData();
//        }
//    };

    /* renamed from: com.warick.a.d$a */
    public interface OnGpsListener {
        /* renamed from: a */
        void sendLocationBroad(double d, double d2, String str);
    }

    private GpsManagerHelper(Context context) {
        this.mGpsManager = new GpsManager(context);
//        this.f3013h = new C0792a(context);
        this.f3011f = "";
    }

    /* renamed from: a */
    public static boolean m2908a() {
        if (mGpsManagerHelper == null) {
            return false;
        }
        mGpsManagerHelper.mGpsManager.setOnLocationChange(mGpsManagerHelper.mOnLocationChange);
//        mGpsManagerHelper.f3013h.mo2865a(mGpsManagerHelper.f3018m);
//        mGpsManagerHelper.f3013h.mo2864a();
        mGpsManagerHelper.clearGpsData();
        return true;
    }

    /* renamed from: a */
    private boolean m2909a(int i) {
        if (i > 3 || i < 0) {
            return false;
        }
        boolean z = true;
        for (int i2 = 0; i2 < 4; i2++) {
            if (this.arrs[i] < this.arrs[i2]) {
                z = false;
            }
        }
        return z;
    }

    /* renamed from: a */
    public static boolean initGpsManagerHelper(Context context) {
        if (mGpsManagerHelper == null) {
            mGpsManagerHelper = new GpsManagerHelper(context);
        }
        return mGpsManagerHelper.mGpsManager.isProviderEnabled();
    }

    /* renamed from: b */
    public static void m2912b() {
        if (mGpsManagerHelper != null) {
//            mGpsManagerHelper.f3013h.mo2866b();
            mGpsManagerHelper.mGpsManager.removeGpsStatusUpdate();
        }
    }

    /* renamed from: d */
    public static void removegps() {
        m2912b();
        mGpsManagerHelper = null;
    }

    /* renamed from: e */
    public static GpsManagerHelper mGpsManagerHelper() {
        return mGpsManagerHelper;
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public void setGpsBeanData() {
        GpsBean g = getGpsBean();
        if (this.mOnGpsListener == null) {
            return;
        }
        if (g != null) {
            this.mOnGpsListener.sendLocationBroad(g.mo2882a(), g.mo2885b(), this.f3011f);
        } else {
            this.mOnGpsListener.sendLocationBroad(0.0d, 0.0d, (String) null);
        }
    }

    /* renamed from: a */
    public void setOnGpsListener(OnGpsListener aVar) {
        this.mOnGpsListener = aVar;
    }

    /* renamed from: c */
    public void clearGpsData() {
        this.f3011f = "";
        this.mGpsBeans[0] = null;
        this.mGpsBeans[1] = null;
        this.mGpsBeans[2] = null;
        this.mGpsBeans[3] = null;
    }

    /* renamed from: f */
    public String mo2889f() {
        return this.f3011f;
    }

    /* renamed from: g */
    public GpsBean getGpsBean() {
        if (this.mGpsBeans[0] != null && m2909a(0)) {
            return this.mGpsBeans[0];
        }
        if (this.mGpsBeans[1] != null && m2909a(1)) {
            return this.mGpsBeans[1];
        }
        if (this.mGpsBeans[2] != null && m2909a(2)) {
            return this.mGpsBeans[2];
        }
        if (this.mGpsBeans[3] != null) {
            return this.mGpsBeans[3];
        }
        return null;
    }
}
