package com.hopetruly.ecg.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.ECGEntity;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.p021a.MyFileListener;
import com.hopetruly.ecg.p021a.FileListener;
import com.hopetruly.ecg.util.C0770f;
import com.hopetruly.ecg.util.LogUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileService extends Service {

    /* renamed from: a */
    ECGApplication f2835a;

    /* renamed from: b */
    FileListener f2836b;

    /* renamed from: c */
    private final IBinder f2837c = new FileServiceBinder();

    /* renamed from: d */
    private C0756b f2838d;

    /* renamed from: e */
    private C0757c f2839e;

    /* renamed from: com.hopetruly.ecg.services.FileService$a */
    public class FileServiceBinder extends Binder {
        public FileServiceBinder() {
        }

        /* renamed from: a */
        public FileService getFileService() {
            return FileService.this;
        }
    }

    /* renamed from: com.hopetruly.ecg.services.FileService$b */
    private class C0756b extends AsyncTask<String, Void, String> {

        /* renamed from: b */
        private boolean f2842b;

        private C0756b() {
            this.f2842b = false;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            String str;
            String str2 = strArr[0];
            String str3 = null;
            if (str2 == null || (str = strArr[1]) == null) {
                return null;
            }
            try {
                String encode = URLEncoder.encode(URLDecoder.decode(str, "utf-8"), "utf-8");
                String str4 = strArr[2];
                if (str4 == null) {
                    return null;
                }
                try {
                    String encode2 = URLEncoder.encode(str4, "utf-8");
                    if ("mounted".equals(Environment.getExternalStorageState())) {
                        if (C0770f.m2782a(str2, encode, false, FileService.this.getApplicationContext(), encode2)) {
                            StringBuffer stringBuffer = new StringBuffer(Environment.getExternalStorageDirectory().getAbsolutePath());
                            stringBuffer.append(File.separator);
                            stringBuffer.append("hopetruly");
                            stringBuffer.append(File.separator);
                            stringBuffer.append("ECGdata");
                            stringBuffer.append(File.separator);
                            stringBuffer.append(encode2);
                            stringBuffer.append("_");
                            stringBuffer.append(encode);
                            str3 = stringBuffer.toString();
                        }
                        this.f2842b = true;
                    }
                    try {
                        Thread.sleep(1000);
                        return str3;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return str3;
                    }
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                    return null;
                }
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            if (isCancelled() || !this.f2842b) {
                Intent intent = new Intent();
                intent.setAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_FAIL");
                LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent);
                return;
            }
            Intent intent2 = new Intent();
            if (str != null) {
                intent2.setAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_SUCCESS");
                intent2.putExtra("com.holptruly.ecg.services.FileService.EXTRA_FILE", str);
            } else {
                intent2.setAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_FAIL");
            }
            LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent2);
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            Intent intent = new Intent();
            intent.setAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_START");
            LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent);
            super.onPreExecute();
        }
    }

    /* renamed from: com.hopetruly.ecg.services.FileService$c */
    private class C0757c extends AsyncTask<Object, Void, String> {

        /* renamed from: b */
        private boolean f2844b;

        private C0757c() {
            this.f2844b = false;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(Object... objArr) {
            ECGEntity ecgEntity;
            ECGRecord eCGRecord = (ECGRecord) objArr[0];
            String str = null;
            if (eCGRecord == null || (ecgEntity = eCGRecord.getEcgEntity()) == null) {
                return null;
            }
            ecgEntity.setLeadExten(eCGRecord.getLeadType() == 1 ? ECGEntity.LEAD_PART_CHEST : ECGEntity.LEAD_PART_HAND);
            float[] fArr = objArr[1] != null ? (float[]) objArr[1] : null;
            if ("mounted".equals(Environment.getExternalStorageState())) {
                try {
                    StringBuffer stringBuffer = new StringBuffer(Environment.getExternalStorageDirectory().getAbsolutePath());
                    stringBuffer.append(File.separator);
                    stringBuffer.append("hopetruly");
                    stringBuffer.append(File.separator);
                    stringBuffer.append("ECGdata");
                    stringBuffer.append(File.separator);
                    stringBuffer.append(URLEncoder.encode(eCGRecord.getUser().getName(), "utf-8") + "_" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date(System.currentTimeMillis())) + "_" + ((int) (Math.random() * 10000.0d)) + ".hl7");
                    String stringBuffer2 = stringBuffer.toString();
                    if (fArr != null) {
                        try {
                            C0770f.m2779a(FileService.this.getApplicationContext(), stringBuffer2, fArr, eCGRecord.getEcgEntity());
                        } catch (Exception e) {
                            e = e;
                            str = stringBuffer2;
                            e.printStackTrace();
                            this.f2844b = false;
                            Intent intent = new Intent();
                            intent.setAction("com.holptruly.ecg.services.FileService.FILE_SAVE_FAIL");
                            LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent);
                            Looper.prepare();
                            Toast.makeText(FileService.this.getApplicationContext(), FileService.this.getString(R.string.p_save_file_fail), Toast.LENGTH_LONG).show();
                            Looper.loop();
                            return str;
                        }
                    } else {
                        while (FileService.this.f2836b.mo2102a() == null) {
                            LogUtils.logE("FileService", "fileCache not complete wait");
                            Thread.sleep(500);
                        }
                        C0770f.m2778a(FileService.this.getApplicationContext(), stringBuffer2, FileService.this.f2836b.mo2102a(), eCGRecord.getEcgEntity());
                    }
                    this.f2844b = true;
                    return stringBuffer2;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    this.f2844b = false;
                    Intent intent2 = new Intent();
                    intent2.setAction("com.holptruly.ecg.services.FileService.FILE_SAVE_FAIL");
                    LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent2);
                    Looper.prepare();
                    Toast.makeText(FileService.this.getApplicationContext(), FileService.this.getString(R.string.p_save_file_fail), Toast.LENGTH_LONG).show();
                    Looper.loop();
                    return str;
                }
            }
            return str;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            Context applicationContext;
            FileService fileService;
            int i;
            if (!isCancelled() && this.f2844b) {
                Intent intent = new Intent();
                if (str != null) {
                    intent.setAction("com.holptruly.ecg.services.FileService.FILE_SAVE_SUCCESS");
                    intent.putExtra("com.holptruly.ecg.services.FileService.EXTRA_FILE", str);
                    LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent);
                    applicationContext = FileService.this.getApplicationContext();
                    fileService = FileService.this;
                    i = R.string.p_create_xml;
                } else {
                    intent.setAction("com.holptruly.ecg.services.FileService.FILE_SAVE_FAIL");
                    LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent);
                    applicationContext = FileService.this.getApplicationContext();
                    fileService = FileService.this;
                    i = R.string.p_no_sdcard;
                }
//                Looper.prepare();
                Toast.makeText(applicationContext, fileService.getString(i), Toast.LENGTH_LONG).show();
//                Looper.loop();
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            Intent intent = new Intent();
            intent.setAction("com.holptruly.ecg.services.FileService.FILE_SAVE_START");
            LocalBroadcastManager.getInstance(FileService.this.getApplicationContext()).sendBroadcast(intent);
        }
    }

    /* renamed from: a */
    public void mo2699a() {
        this.f2836b.mo2105b();
    }

    /* renamed from: a */
    public void mo2700a(ECGRecord eCGRecord) {
        if (this.f2839e == null || this.f2839e.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2839e = new C0757c();
            this.f2839e.execute(new Object[]{eCGRecord, null});
        }
    }

    /* renamed from: a */
    public void mo2701a(ECGRecord eCGRecord, float[] fArr) {
        if (this.f2839e == null || this.f2839e.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2839e = new C0757c();
            this.f2839e.execute(new Object[]{eCGRecord, fArr});
        }
    }

    /* renamed from: a */
    public void mo2702a(String str, String str2, String str3) {
        if (this.f2838d == null || this.f2838d.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2838d = new C0756b();
            this.f2838d.execute(new String[]{str, str2, str3});
        }
    }

    /* renamed from: a */
    public void mo2703a(int[] iArr) {
        this.f2836b.savemEcgData(iArr);
    }

    /* renamed from: b */
    public void mo2704b() {
        this.f2836b.mo2106c();
    }

    /* renamed from: c */
    public int mo2705c() {
        return this.f2836b.mo2107d();
    }

    public IBinder onBind(Intent intent) {
        return this.f2837c;
    }

    public void onCreate() {
        super.onCreate();
        this.f2835a = (ECGApplication) getApplication();
        this.f2836b = new MyFileListener(this);
        StringBuffer stringBuffer = new StringBuffer(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuffer.append(File.separator);
        stringBuffer.append("hopetruly");
        stringBuffer.append(File.separator);
        stringBuffer.append("cache");
        stringBuffer.append(File.separator);
        stringBuffer.append("cache.txt");
        this.f2836b.mo2103a(stringBuffer.toString());
    }
}
