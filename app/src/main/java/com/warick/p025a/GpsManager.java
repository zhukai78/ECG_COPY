package com.warick.p025a;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;

/* renamed from: com.warick.a.b */
public class GpsManager {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final String f2994a = "b";
    /* access modifiers changed from: private */

    /* renamed from: b */
    public LocationManager f2995b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public OnLocationChange f2996c = null;

    /* renamed from: d */
    private GpsStatus.Listener f2997d = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int i) {
            String str;
            String str2;
            switch (i) {
                case 1:
                    str = GpsManager.f2994a;
                    str2 = "定位启动";
                    break;
                case 2:
                    str = GpsManager.f2994a;
                    str2 = "定位结束";
                    break;
                case 3:
                    str = GpsManager.f2994a;
                    str2 = "第一次定位";
                    break;
                case 4:
//                    Log.i(C0795b.f2994a, "卫星状态改变");
                    GpsStatus gpsStatus = GpsManager.this.f2995b.getGpsStatus((GpsStatus) null);
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();
                    int i2 = 0;
                    while (it.hasNext() && i2 <= maxSatellites) {
                        GpsSatellite next = it.next();
                        i2++;
                    }
                    str = GpsManager.f2994a;
                    str2 = "搜索到：" + i2 + "颗卫星";
                    break;
                default:
                    return;
            }
//            Log.i(str, str2);
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: e */
    public LocationListener f2998e = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (GpsManager.this.f2996c != null && location != null) {
                Log.i(GpsManager.f2994a, "位置变更");
                GpsManager.this.f2996c.onLastKnownLocation(location.getLongitude(), location.getLatitude());
            }
        }

        public void onProviderDisabled(String str) {
            Log.i(GpsManager.f2994a, "GPS禁用时触发");
        }

        public void onProviderEnabled(String str) {
            Log.i(GpsManager.f2994a, "GPS开启时触发");
            GpsManager.this.f2995b.requestLocationUpdates(str, 10000, 1.0f, GpsManager.this.f2998e);
            Location lastKnownLocation = GpsManager.this.f2995b.getLastKnownLocation(str);
            if (lastKnownLocation != null) {
                GpsManager.this.f2996c.onLastKnownLocation(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude());
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            String str2;
            String str3;
            switch (i) {
                case 0:
                    str3 = GpsManager.f2994a;
                    str2 = "当前GPS状态为服务区外状态";
                    break;
                case 1:
                    str3 = GpsManager.f2994a;
                    str2 = "当前GPS状态为暂停服务状态";
                    break;
                case 2:
                    str3 = GpsManager.f2994a;
                    str2 = "当前GPS状态为可见状态";
                    break;
                default:
                    return;
            }
            Log.i(str3, str2);
        }
    };

    /* renamed from: f */
    private LocationListener f2999f = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (GpsManager.this.f2996c != null && location != null) {
                Log.i(GpsManager.f2994a, "位置变更");
                GpsManager.this.f2996c.onNowKnownLocation(location.getLongitude(), location.getLatitude());
            }
        }

        public void onProviderDisabled(String str) {
            Log.i(GpsManager.f2994a, "GPS禁用时触发");
        }

        public void onProviderEnabled(String str) {
            Log.i(GpsManager.f2994a, "GPS开启时触发");
            Location lastKnownLocation = GpsManager.this.f2995b.getLastKnownLocation(str);
            if (lastKnownLocation != null) {
                GpsManager.this.f2996c.onLastKnownLocation(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude());
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            String str2;
            String str3;
            switch (i) {
                case 0:
                    str3 = GpsManager.f2994a;
                    str2 = "当前GPS状态为服务区外状态";
                    break;
                case 1:
                    str3 = GpsManager.f2994a;
                    str2 = "当前GPS状态为暂停服务状态";
                    break;
                case 2:
                    str3 = GpsManager.f2994a;
                    str2 = "当前GPS状态为可见状态";
                    break;
                default:
                    return;
            }
            Log.i(str3, str2);
        }
    };

    /* renamed from: com.warick.a.b$a */
    public interface OnLocationChange {
        /* renamed from: a */
        void onLastKnownLocation(double d, double d2);

        /* renamed from: b */
        void onNowKnownLocation(double d, double d2);
    }

    public GpsManager(Context context) {
        this.f2995b = (LocationManager) context.getSystemService("location");
    }

    /* renamed from: d */
    private Criteria m2896d() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(1);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(false);
        criteria.setPowerRequirement(1);
        return criteria;
    }

    /* renamed from: a */
    public void mo2868a() {
        this.f2995b.removeGpsStatusListener(this.f2997d);
        this.f2995b.removeUpdates(this.f2998e);
        this.f2995b.removeUpdates(this.f2999f);
    }

    /* renamed from: a */
    public void setOnLocationChange(OnLocationChange aVar) {
        if (this.f2996c == null && this.f2995b != null) {
            this.f2996c = aVar;
            this.f2995b.addGpsStatusListener(this.f2997d);
            if (this.f2995b.isProviderEnabled("network")) {
                this.f2995b.requestLocationUpdates("network", 10000, 1.0f, this.f2999f);
            }
            String bestProvider = this.f2995b.getBestProvider(m2896d(), true);
            Location lastKnownLocation = this.f2995b.getLastKnownLocation(bestProvider);
            if (!(lastKnownLocation == null || this.f2996c == null)) {
                this.f2996c.onLastKnownLocation(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude());
            }
            this.f2995b.requestLocationUpdates(bestProvider, 10000, 1.0f, this.f2998e);
        }
    }

    /* renamed from: b */
    public boolean mo2870b() {
        return this.f2995b.isProviderEnabled("gps");
    }
}
