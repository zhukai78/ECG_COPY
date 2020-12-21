package com.hopetruly.ecg.file;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.hopetruly.ecg.util.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/* renamed from: com.hopetruly.ecg.a.a */
public class MyFileListener implements FileListener {

    /* renamed from: a */
    private final int f2096a = 0;

    /* renamed from: b */
    private final int f2097b = 1;

    /* renamed from: c */
    private final int f2098c = 2;

    /* renamed from: d */
    private final int f2099d = 3;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public File cacheFile;

    /* renamed from: f */
    private Context f2101f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public boolean isCanWrite = false;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public Looper mFileLooper;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public Handler mFileHandler;

    /* renamed from: j */
    private int f2105j = 1024;

    /* renamed from: k */
    private int[] f2106k = new int[this.f2105j];

    /* renamed from: l */
    private int[] f2107l = new int[this.f2105j];
    /* access modifiers changed from: private */

    /* renamed from: m */
    public int f2108m = 0;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public int f2109n = 0;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public int f2110o = 0;
    /* access modifiers changed from: private */

    /* renamed from: p */
    public int saveCacheMake = 0;

    /* renamed from: q */
    private int f2112q = 512;

    /* renamed from: r */
    private Thread saveThread = new Thread() {
        public void run() {
            Looper.prepare();
            Looper unused = MyFileListener.this.mFileLooper = Looper.myLooper();
            Handler unused2 = MyFileListener.this.mFileHandler = new Handler(MyFileListener.this.mFileLooper) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            if (MyFileListener.this.cacheFile == null) {
                                LogUtils.logE("FileCache", "未有设置缓存文件的位置? SetCacheFile()");
                                return;
                            }
                            int unused = MyFileListener.this.f2108m = 0;
                            int unused2 = MyFileListener.this.f2109n = 0;
                            int unused3 = MyFileListener.this.f2110o = 0;
                            try {
                                if (!MyFileListener.this.cacheFile.exists()) {
                                    MyFileListener.this.cacheFile.createNewFile();
                                } else {
                                    FileWriter fileWriter = new FileWriter(MyFileListener.this.cacheFile, false);
                                    fileWriter.write("");
                                    fileWriter.close();
                                    LogUtils.logE("FileCache", "OPEN_CACHE_FILE >> clear file ");
                                }
                                boolean unused4 = MyFileListener.this.isCanWrite = true;
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        case 1:
                            if (MyFileListener.this.isCanWrite) {
                                MyFileListener.this.m2222e();
                                boolean unused5 = MyFileListener.this.isCanWrite = false;
                                LogUtils.logE("FileCache", "CLOSE_CACHE_FILE >> CanWrite = false; ");
                            } else {
                                Log.e("FileCache", "文件缓存还不能写入，检查是否OpenFileCache");
                            }
                            int unused6 = MyFileListener.this.saveCacheMake = 0;
                            return;
                        case 2:
                            if (MyFileListener.this.isCanWrite) {
                                MyFileListener.this.m2218c((int[]) message.obj);
                                return;
                            }
                            break;
                        case 3:
                            if (MyFileListener.this.isCanWrite) {
                                MyFileListener.this.m2216b((int[]) message.obj);
                                return;
                            }
                            break;
                        default:
                            return;
                    }
                    Log.e("FileCache", "文件缓存还不能写入，检查是否OpenFileCache");
                }
            };
            Looper.loop();
        }
    };

    public MyFileListener(Context context) {
        this.f2101f = context;
        this.saveThread.start();
    }

    /* renamed from: a */
    private void m2210a(int i) {
        this.f2106k[this.f2109n] = i;
        this.f2109n++;
        this.f2109n %= this.f2106k.length;
        this.saveCacheMake++;
        this.f2108m++;
        if (this.f2108m == this.f2112q) {
            int[] iArr = new int[this.f2108m];
            for (int i2 = 0; i2 < this.f2108m; i2++) {
                iArr[i2] = this.f2106k[(this.f2110o + i2) % this.f2106k.length];
            }
            Message obtain = Message.obtain(this.mFileHandler);
            obtain.what = 2;
            obtain.obj = iArr;
            this.mFileHandler.sendMessage(obtain);
            this.f2108m = 0;
            this.f2110o = this.f2109n;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2216b(int[] iArr) {
        for (int a : iArr) {
            m2210a(a);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2218c(int[] iArr) {
        if (this.cacheFile != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.cacheFile, true);
                for (int i = 0; i < iArr.length; i++) {
                    fileOutputStream.write((iArr[i]/1000 + " ").getBytes());
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m2222e() {
        if (this.f2108m > 0) {
            try {
                int[] iArr = new int[this.f2108m];
                for (int i = 0; i < this.f2108m; i++) {
                    iArr[i] = this.f2106k[(this.f2110o + i) % this.f2106k.length];
                }
                m2218c(iArr);
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: a */
    public String fileCacheStr() {
        if (this.isCanWrite) {
            return null;
        }
        return this.cacheFile.getAbsolutePath();
    }

    /* renamed from: a */
    public void shareCachePath(String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.cacheFile = new File(str);
            if (!this.cacheFile.getParentFile().exists()) {
                this.cacheFile.getParentFile().mkdirs();
            }
            if (!this.cacheFile.exists()) {
                try {
                    this.cacheFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            LogUtils.logE("FileCache", "没有sd卡！");
        }
    }

    /* renamed from: a */
    public void savemEcgData(int[] iArr) {
        if (this.mFileHandler != null) {
            Message obtain = Message.obtain(this.mFileHandler);
            obtain.what = 3;
            obtain.obj = iArr;
            this.mFileHandler.sendMessage(obtain);
        }
    }

    /* renamed from: b */
    public void startEcgEvent() {
        if (this.mFileHandler != null) {
            Message obtain = Message.obtain(this.mFileHandler);
            obtain.what = 0;
            this.mFileHandler.sendMessage(obtain);
        }
    }

    /* renamed from: c */
    public void stopEcgEvent() {
        if (this.mFileHandler != null) {
            Message obtain = Message.obtain(this.mFileHandler);
            obtain.what = 1;
            this.mFileHandler.sendMessage(obtain);
        }
    }

    /* renamed from: d */
    public int getSaveCacheMake() {
        return this.saveCacheMake;
    }
}
