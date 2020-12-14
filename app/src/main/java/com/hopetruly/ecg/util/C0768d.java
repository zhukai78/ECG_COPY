package com.hopetruly.ecg.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.hopetruly.ecg.activity.ErrorActivity;
import com.hopetruly.ecg.entity.ErrorInfo;
import com.hopetruly.ecg.services.MainService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/* renamed from: com.hopetruly.ecg.util.d */
public class C0768d implements Thread.UncaughtExceptionHandler {

    /* renamed from: d */
    private static C0768d f2903d;

    /* renamed from: a */
    Context f2904a;

    /* renamed from: b */
    ArrayList<Activity> f2905b = new ArrayList<>();

    /* renamed from: c */
    ArrayList<Service> f2906c = new ArrayList<>();

    /* renamed from: e */
    private Thread.UncaughtExceptionHandler f2907e;

    /* renamed from: f */
    private ErrorInfo f2908f = new ErrorInfo();

    /* renamed from: g */
    private SimpleDateFormat f2909g = new SimpleDateFormat("yyyy-MM-dd-_HH:mm:ss", Locale.CHINA);

    /* renamed from: h */
    private String f2910h = null;

    private C0768d() {
    }

    /* renamed from: a */
    public static C0768d m2762a() {
        if (f2903d == null) {
            f2903d = new C0768d();
        }
        return f2903d;
    }

    /* renamed from: a */
    private boolean m2763a(Throwable th) {
        if (th == null) {
            return false;
        }
        mo2774b(this.f2904a);
        m2764b(th);
        return true;
    }

    /* renamed from: b */
    private String m2764b(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("time =" + this.f2908f.getTime() + "\n");
        stringBuffer.append("manufacturer =" + this.f2908f.getManufacturer() + "\n");
        stringBuffer.append("model =" + this.f2908f.getModel() + "\n");
        stringBuffer.append("CPU_ABI =" + this.f2908f.getCPU_ABI() + "\n");
        stringBuffer.append("Android Ver =" + this.f2908f.getAndroidVer() + "\n");
        stringBuffer.append("Androud Sdk =" + this.f2908f.getAndroudSdk() + "\n");
        stringBuffer.append("VersionName =" + this.f2908f.getVersionName() + "\n");
        stringBuffer.append("VersionCode =" + this.f2908f.getVersionCode() + "\n");
        stringBuffer.append("Firmware Ver =" + this.f2908f.getFirmwareVer() + "\n");
        stringBuffer.append("========================================================================\n");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        do {
            th.printStackTrace(printWriter);
            th = th.getCause();
        } while (th != null);
        printWriter.close();
        String obj = stringWriter.toString();
        this.f2908f.setErrorInfo(obj);
        stringBuffer.append(obj);
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String format = this.f2909g.format(new Date());
            File file = new File(this.f2910h, "crash-" + format + "-" + currentTimeMillis + ".log");
            if (file != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e("CrashHandler", "an error occured while writing file...", e);
            return null;
        }
    }

    /* renamed from: a */
    public void mo2769a(Activity activity) {
        this.f2905b.add(activity);
    }

    /* renamed from: a */
    public void mo2770a(Service service) {
        this.f2906c.add(service);
    }

    /* renamed from: a */
    public void mo2771a(Context context) {
        this.f2904a = context;
        this.f2907e = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.f2910h = Environment.getExternalStorageDirectory() + File.separator + "hopetruly" + File.separator + "carshLog";
            File file = new File(this.f2910h);
            if (!file.exists()) {
                file.mkdirs();
                return;
            }
            return;
        }
        Log.e("CrashHandler", "can not find sdcard");
    }

    /* renamed from: b */
    public void mo2772b() {
        Iterator<Activity> it = this.f2905b.iterator();
        while (it.hasNext()) {
            it.next().finish();
        }
    }

    /* renamed from: b */
    public void mo2773b(Activity activity) {
        this.f2905b.remove(activity);
    }

    /* renamed from: b */
    public void mo2774b(Context context) {
        this.f2908f.setTime(this.f2909g.format(new Date()));
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (packageInfo != null) {
                String str = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                this.f2908f.setVersionName(str);
                this.f2908f.setVersionCode(packageInfo.versionCode + "");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CrashHandler", "an error occured when collect package info", e);
        }
        try {
            this.f2908f.setManufacturer(Build.MANUFACTURER);
            this.f2908f.setModel(Build.MODEL);
            this.f2908f.setCPU_ABI(Build.CPU_ABI);
            this.f2908f.setAndroidVer(Build.VERSION.RELEASE);
            this.f2908f.setAndroudSdk(String.valueOf(Build.VERSION.SDK_INT));
        } catch (Exception e2) {
            Log.e("CrashHandler", "an error occured when collect crash info", e2);
        }
    }

    /* renamed from: c */
    public void mo2775c() {
        Iterator<Service> it = this.f2906c.iterator();
        while (it.hasNext()) {
            Service next = it.next();
            if (next instanceof MainService) {
                ((MainService) next).mo2719a();
            } else {
                next.stopSelf();
            }
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        if (m2763a(th) || this.f2907e == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Log.e("CrashHandler", "error : ", e);
            }
            mo2772b();
            mo2775c();
            Intent intent = new Intent(this.f2904a, ErrorActivity.class);
            intent.putExtra("error", this.f2908f);
            ((AlarmManager) this.f2904a.getSystemService(Context.ALARM_SERVICE)).set(1, System.currentTimeMillis() + 1000, PendingIntent.getActivity(this.f2904a.getApplicationContext(), -1, intent, 268435456));
            Log.e("error", "app error!");
            Process.killProcess(Process.myPid());
            return;
        }
        this.f2907e.uncaughtException(thread, th);
    }
}
