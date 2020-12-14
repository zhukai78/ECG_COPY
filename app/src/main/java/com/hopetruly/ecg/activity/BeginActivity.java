package com.hopetruly.ecg.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.UserInfo;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BeginActivity extends Activity {

    /* renamed from: a */
    final String TAG = "BeginActivity";

    /* renamed from: b */
    ECGApplication mApplication;

    /* renamed from: c */
    TextView f2124c;

    /* renamed from: d */
    private final int f2125d = 1;

    /* renamed from: e */
    private boolean f2126e = true;

    /* renamed from: f */
    private SharedPreferences.Editor f2127f;

    /* renamed from: b */
    private void m2236b() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                Looper.prepare();
                BeginActivity.this.m2238d();
                Looper.loop();
            }
        }, 1000);
    }

    /* renamed from: c */
    private void m2237c() {
        if (mApplication.f2081b.getName() == UserInfo.EMPTY || mApplication.f2081b.getId() == UserInfo.EMPTY) {
            this.f2126e = false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m2238d() {
        if (!this.f2126e || mApplication.f2083d.mo2686c() != 1) {
            m2241g();
        } else {
            m2240f();
        }
    }

    /* renamed from: e */
    private void m2239e() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /* renamed from: f */
    private void m2240f() {
        startActivity(new Intent(this, FunChooseActivity.class));
        finish();
    }

    /* renamed from: g */
    private void m2241g() {
        startActivityForResult(new Intent(this, DeclareActivity.class), 1);
    }

    /* renamed from: a */
    public void mo2115a() {
        try {
            mo2116a(getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES).signatures[0].toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void mo2116a(byte[] bArr) {
        try {
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
            String obj = x509Certificate.getPublicKey().toString();
            String bigInteger = x509Certificate.getSerialNumber().toString();
            String sigAlgName = x509Certificate.getSigAlgName();
            String sigAlgOID = x509Certificate.getSigAlgOID();
            Log.e("test", obj + "\n" + bigInteger + "\n" + sigAlgName + "\n" + sigAlgOID);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1) {
            return;
        }
        if (i2 == -1) {
            mApplication.f2083d.mo2689d(1);
            this.f2127f.putInt("SW_AGREE_FLAG", mApplication.f2083d.mo2688d());
            mApplication.f2083d.mo2687c(1);
            this.f2127f.putInt("SW_AGREE_DECLARE", mApplication.f2083d.mo2686c());
            this.f2127f.commit();
            m2239e();
        } else if (i2 == 0) {
            mApplication.f2083d.mo2687c(0);
            this.f2127f.putInt("SW_AGREE_DECLARE", mApplication.f2083d.mo2686c());
            this.f2127f.commit();
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_begin);
        this.f2124c = (TextView) findViewById(R.id.begin_status);
        mApplication = (ECGApplication) getApplication();
        f2127f = mApplication.f2084e.edit();
        mo2115a();
        m2237c();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            for (int i2 : iArr) {
                if (i2 != 0) {
                    Toast.makeText(this, "拒绝权限将无法使用该应用！", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
            m2236b();
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onResume() {
        Log.d("BeginActivity", "onResume");
        ArrayList arrayList = new ArrayList();
        for (String str : new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SEND_SMS"}) {
            if (PermissionChecker.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 1);
        } else {
            m2236b();
        }
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        Log.d("BeginActivity", "OnStop");
        super.onStop();
    }
}
