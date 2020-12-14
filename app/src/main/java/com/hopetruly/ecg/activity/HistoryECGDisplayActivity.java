package com.hopetruly.ecg.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.algorithm.ECGAnaysis;
import com.hopetruly.ecg.algorithm.HeartRateCounter3;
import com.hopetruly.ecg.entity.ECGEntity;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.p022b.C0740b;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.C0770f;
import com.hopetruly.ecg.util.C0771g;
import com.hopetruly.ecg.util.C0772h;
import com.hopetruly.ecg.util.C0775k;
import com.hopetruly.part.net.NetService;
import com.warick.drawable.WarickSurfaceView;
import com.warick.drawable.p028a.C0812a;
import com.warick.drawable.p028a.C0813b;
import com.warick.drawable.p028a.C0814c;
import com.warick.drawable.p028a.C0815d;
import com.warick.jni.filter.Fir;

import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

public class HistoryECGDisplayActivity extends C0721a {
    /* access modifiers changed from: private */

    /* renamed from: A */
    public static final String f2303A = "HistoryECGDisplayActivity";
    /* access modifiers changed from: private */

    /* renamed from: B */
    public EditText f2304B;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public EditText f2305C;
    /* access modifiers changed from: private */

    /* renamed from: D */
    public EditText f2306D;
    /* access modifiers changed from: private */

    /* renamed from: E */
    public TextView f2307E;

    /* renamed from: F */
    private PopupWindow f2308F;
    /* access modifiers changed from: private */

    /* renamed from: G */
    public PopupWindow f2309G;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public PopupWindow f2310H;
    /* access modifiers changed from: private */

    /* renamed from: I */
    public PopupWindow f2311I;
    /* access modifiers changed from: private */

    /* renamed from: J */
    public ProgressDialog f2312J = null;

    /* renamed from: K */
    private double[] f2313K = new double[Fir.getOrder(Fir.f3076f)];
    /* access modifiers changed from: private */

    /* renamed from: L */
    public boolean f2314L = true;
    /* access modifiers changed from: private */

    /* renamed from: M */
    public Object f2315M;
    /* access modifiers changed from: private */

    /* renamed from: N */
    public int f2316N = 0;
    /* access modifiers changed from: private */

    /* renamed from: O */
    public int f2317O = 0;
    /* access modifiers changed from: private */

    /* renamed from: P */
    public boolean f2318P = false;
    /* access modifiers changed from: private */

    /* renamed from: Q */
    public boolean f2319Q = false;
    /* access modifiers changed from: private */

    /* renamed from: R */
    public int f2320R = 0;
    /* access modifiers changed from: private */

    /* renamed from: S */
    public int f2321S = 0;

    /* renamed from: T */
    private View.OnTouchListener f2322T = new View.OnTouchListener() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0178, code lost:
            r0.setText(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x01fd, code lost:
            r14.setText(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x03cb, code lost:
            r14 = com.hopetruly.ecg.activity.HistoryECGDisplayActivity.m2397n(r12.f2366a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x03d1, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            com.hopetruly.ecg.activity.HistoryECGDisplayActivity.m2397n(r12.f2366a).wait(50);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x03de, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r0.printStackTrace();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x043d, code lost:
            r0.mo2928a(1, (float) r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0041, code lost:
            android.util.Log.i(r14, r0);
         */
        @SuppressLint("LongLogTag")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int x;
            C0815d dVar;
            Object n;
            TextView textView;
            String str;
            String a;
            String str2;
            TextView textView2;
            String str3;
            try {
                DecimalFormat decimalFormat = new DecimalFormat("0.0000");
                if (HistoryECGDisplayActivity.this.f2317O == 0) {
                    switch (motionEvent.getAction() & 255) {
                        case 0:
                            HistoryECGDisplayActivity.m2394k(HistoryECGDisplayActivity.this);
                            Log.i(HistoryECGDisplayActivity.f2303A, "onTouchEvent>ACTION_DOWN>mode:" + HistoryECGDisplayActivity.this.f2316N);
                            int x2 = (int) motionEvent.getX();
                            HistoryECGDisplayActivity.this.f2337m.mo2930a(0, true);
                            HistoryECGDisplayActivity.this.f2337m.mo2930a(1, false);
                            HistoryECGDisplayActivity.this.f2337m.mo2929a(0, "A1");
                            float f = (float) x2;
                            HistoryECGDisplayActivity.this.f2337m.mo2928a(0, f);
                            float d = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x2));
                            HistoryECGDisplayActivity.this.f2338n[0].mo2904a(true);
                            HistoryECGDisplayActivity.this.f2338n[1].mo2904a(false);
                            HistoryECGDisplayActivity.this.f2338n[0].mo2903a((String) null, f, HistoryECGDisplayActivity.this.f2335k.mo2906a() * d);
                            HistoryECGDisplayActivity.this.f2326a.setText("A1：" + decimalFormat.format((double) (d / 1000.0f)) + " mV");
                            HistoryECGDisplayActivity.this.f2327c.setText("A2：-- mV");
                            textView = HistoryECGDisplayActivity.this.f2328d;
                            str = "A1-A2:-- ms";
                            break;
                        case 1:
                            HistoryECGDisplayActivity.m2396m(HistoryECGDisplayActivity.this);
                            a = HistoryECGDisplayActivity.f2303A;
                            str2 = "onTouchEvent>ACTION_UP>mode:" + HistoryECGDisplayActivity.this.f2316N;
                            break;
                        case 2:
                            if (HistoryECGDisplayActivity.this.f2316N != 1) {
                                int x3 = (int) motionEvent.getX(0);
                                int x4 = (int) motionEvent.getX(1);
                                HistoryECGDisplayActivity.this.f2337m.mo2930a(0, true);
                                float f2 = (float) x3;
                                HistoryECGDisplayActivity.this.f2337m.mo2928a(0, f2);
                                HistoryECGDisplayActivity.this.f2337m.mo2930a(1, true);
                                float f3 = (float) x4;
                                HistoryECGDisplayActivity.this.f2337m.mo2928a(1, f3);
                                float d2 = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x3));
                                HistoryECGDisplayActivity.this.f2338n[0].mo2904a(true);
                                HistoryECGDisplayActivity.this.f2338n[0].mo2903a((String) null, f2, HistoryECGDisplayActivity.this.f2335k.mo2906a() * d2);
                                float d3 = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x4));
                                HistoryECGDisplayActivity.this.f2338n[1].mo2904a(true);
                                HistoryECGDisplayActivity.this.f2338n[1].mo2903a((String) null, f3, HistoryECGDisplayActivity.this.f2335k.mo2906a() * d3);
                                HistoryECGDisplayActivity.this.f2326a.setText("A1：" + decimalFormat.format((double) (d2 / 1000.0f)) + " mV");
                                HistoryECGDisplayActivity.this.f2327c.setText("A2：" + decimalFormat.format((double) (d3 / 1000.0f)) + " mV");
                                textView2 = HistoryECGDisplayActivity.this.f2328d;
                                str3 = "A1-A2:" + ((float) (Math.abs(HistoryECGDisplayActivity.this.f2335k.mo2916c(x4) - HistoryECGDisplayActivity.this.f2335k.mo2916c(x3)) * 4)) + " ms";
                                break;
                            } else {
                                int x5 = (int) motionEvent.getX();
                                HistoryECGDisplayActivity.this.f2337m.mo2930a(0, true);
                                HistoryECGDisplayActivity.this.f2337m.mo2930a(1, false);
                                float f4 = (float) x5;
                                HistoryECGDisplayActivity.this.f2337m.mo2928a(0, f4);
                                float d4 = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x5));
                                HistoryECGDisplayActivity.this.f2338n[0].mo2903a((String) null, f4, HistoryECGDisplayActivity.this.f2335k.mo2906a() * d4);
                                HistoryECGDisplayActivity.this.f2338n[1].mo2904a(false);
                                HistoryECGDisplayActivity.this.f2326a.setText("A1：" + decimalFormat.format((double) (d4 / 1000.0f)) + " mV");
                                HistoryECGDisplayActivity.this.f2327c.setText("A2：-- mV");
                                textView = HistoryECGDisplayActivity.this.f2328d;
                                str = "A1-A2:-- ms";
                                break;
                            }
                        case 5:
                            HistoryECGDisplayActivity.m2394k(HistoryECGDisplayActivity.this);
                            Log.i(HistoryECGDisplayActivity.f2303A, "onTouchEvent>ACTION_POINTER_DOWN>mode:" + HistoryECGDisplayActivity.this.f2316N);
                            int x6 = (int) motionEvent.getX(0);
                            int x7 = (int) motionEvent.getX(1);
                            HistoryECGDisplayActivity.this.f2337m.mo2930a(0, true);
                            float f5 = (float) x6;
                            HistoryECGDisplayActivity.this.f2337m.mo2928a(0, f5);
                            HistoryECGDisplayActivity.this.f2337m.mo2930a(1, true);
                            HistoryECGDisplayActivity.this.f2337m.mo2929a(1, "A2");
                            float f6 = (float) x7;
                            HistoryECGDisplayActivity.this.f2337m.mo2928a(1, f6);
                            float d5 = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x6));
                            HistoryECGDisplayActivity.this.f2338n[0].mo2904a(true);
                            HistoryECGDisplayActivity.this.f2338n[0].mo2903a((String) null, f5, d5 * HistoryECGDisplayActivity.this.f2335k.mo2906a());
                            float d6 = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x7));
                            HistoryECGDisplayActivity.this.f2338n[1].mo2904a(true);
                            HistoryECGDisplayActivity.this.f2338n[1].mo2903a((String) null, f6, HistoryECGDisplayActivity.this.f2335k.mo2906a() * d6);
                            float d7 = HistoryECGDisplayActivity.this.f2335k.mo2919d(HistoryECGDisplayActivity.this.f2335k.mo2916c(x6));
                            HistoryECGDisplayActivity.this.f2326a.setText("A1：" + decimalFormat.format((double) (d7 / 1000.0f)) + " mV");
                            HistoryECGDisplayActivity.this.f2327c.setText("A2：" + decimalFormat.format((double) (d6 / 1000.0f)) + " mV");
                            textView2 = HistoryECGDisplayActivity.this.f2328d;
                            str3 = "A1-A2:" + ((float) (Math.abs(HistoryECGDisplayActivity.this.f2335k.mo2916c(x7) - HistoryECGDisplayActivity.this.f2335k.mo2916c(x6)) * 4)) + " ms";
                            break;
                        case 6:
                            HistoryECGDisplayActivity.m2396m(HistoryECGDisplayActivity.this);
                            a = HistoryECGDisplayActivity.f2303A;
                            str2 = "onTouchEvent>ACTION_POINTER_UP>mode:" + HistoryECGDisplayActivity.this.f2316N;
                            break;
                    }
                } else if (HistoryECGDisplayActivity.this.f2317O != 1) {
                    return false;
                } else {
                    switch (motionEvent.getAction() & 255) {
                        case 0:
                            if (HistoryECGDisplayActivity.this.f2318P || HistoryECGDisplayActivity.this.f2319Q) {
                                x = (int) motionEvent.getX();
                                if (HistoryECGDisplayActivity.this.f2318P) {
                                    HistoryECGDisplayActivity.this.f2337m.mo2930a(0, true);
                                    HistoryECGDisplayActivity.this.f2337m.mo2929a(0, HistoryECGDisplayActivity.this.getString(R.string.start));
                                    HistoryECGDisplayActivity.this.f2337m.mo2928a(0, (float) x);
                                }
                                if (HistoryECGDisplayActivity.this.f2319Q) {
                                    HistoryECGDisplayActivity.this.f2337m.mo2930a(1, true);
                                    HistoryECGDisplayActivity.this.f2337m.mo2929a(1, HistoryECGDisplayActivity.this.getString(R.string.end));
                                    dVar = HistoryECGDisplayActivity.this.f2337m;
                                }
                            }
                            break;
                        case 1:
                            int x8 = (int) motionEvent.getX();
                            if (!HistoryECGDisplayActivity.this.f2318P) {
                                if (HistoryECGDisplayActivity.this.f2319Q) {
                                    int c = HistoryECGDisplayActivity.this.f2335k.mo2916c(x8);
                                    HistoryECGDisplayActivity.this.f2327c.setText(HistoryECGDisplayActivity.this.getString(R.string.end) + "：" + (c * 4) + "ms");
                                    int unused = HistoryECGDisplayActivity.this.f2321S = c;
                                    break;
                                }
                            } else {
                                int c2 = HistoryECGDisplayActivity.this.f2335k.mo2916c(x8);
                                HistoryECGDisplayActivity.this.f2326a.setText(HistoryECGDisplayActivity.this.getString(R.string.start) + "：" + (c2 * 4) + "ms");
                                int unused2 = HistoryECGDisplayActivity.this.f2320R = c2;
                                break;
                            }
                            break;
                        case 2:
                            if (HistoryECGDisplayActivity.this.f2318P || HistoryECGDisplayActivity.this.f2319Q) {
                                x = (int) motionEvent.getX();
                                if (HistoryECGDisplayActivity.this.f2318P) {
                                    HistoryECGDisplayActivity.this.f2337m.mo2930a(0, true);
                                    HistoryECGDisplayActivity.this.f2337m.mo2928a(0, (float) x);
                                }
                                if (HistoryECGDisplayActivity.this.f2319Q) {
                                    HistoryECGDisplayActivity.this.f2337m.mo2930a(1, true);
                                    dVar = HistoryECGDisplayActivity.this.f2337m;
                                }
                            }
                            break;
                    }
                    synchronized (HistoryECGDisplayActivity.this.f2315M) {
                        try {
                            HistoryECGDisplayActivity.this.f2315M.wait(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                return true;
            } catch (NullPointerException e2) {
                e2.printStackTrace();
                return false;
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
                return false;
            }
        }
    };

    /* renamed from: U */
    private ServiceConnection f2323U = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof NetService.C0786c) {
                HistoryECGDisplayActivity.this.f2340p = ((NetService.C0786c) iBinder).mo2852a();
            } else if (iBinder instanceof MainService.C0762a) {
                HistoryECGDisplayActivity.this.f2341q = ((MainService.C0762a) iBinder).mo2756a();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            HistoryECGDisplayActivity.this.f2340p = null;
            HistoryECGDisplayActivity.this.f2341q = null;
        }
    };

    /* renamed from: V */
    private BroadcastReceiver f2324V = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Context applicationContext;
            HistoryECGDisplayActivity historyECGDisplayActivity;
            int i;
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.NetService.BEGIN_UPLOAD_ACTION")) {
                HistoryECGDisplayActivity.this.m2383c(HistoryECGDisplayActivity.this.getResources().getString(R.string.uploading));
                return;
            }
            if (action.equals("com.holptruly.ecg.services.NetService.END_SUCCESS_UPLOAD_ACTION")) {
                HistoryECGDisplayActivity.this.m2389f();
                applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                i = R.string.p_upload_success;
            } else if (action.equals("com.holptruly.ecg.services.NetService.END_FAIL_UPLOAD_ACTION")) {
                HistoryECGDisplayActivity.this.m2389f();
                applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                i = R.string.p_upload_fail;
            } else if (action.equals("com.holptruly.ecg.services.NetService.END_FAIL_UPLOAD_EXIST_ACTION")) {
                HistoryECGDisplayActivity.this.m2389f();
                applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                i = R.string.p_exist_fail;
            } else {
                if ("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS".equals(action)) {
                    HistoryECGDisplayActivity.this.f2346v = null;
                    HistoryECGDisplayActivity.this.f2347w = HistoryECGDisplayActivity.this.f2348x;
                    HistoryECGDisplayActivity.this.f2343s = HistoryECGDisplayActivity.this.m2374a(HistoryECGDisplayActivity.this.f2347w.getFilePath());
                    HistoryECGDisplayActivity.this.f2335k.mo2912a(HistoryECGDisplayActivity.this.f2343s);
                    HistoryECGDisplayActivity.this.f2335k.mo2922e(0);
                    HistoryECGDisplayActivity.this.f2330f.setProgress(0);
                } else if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL")) {
                    Toast.makeText(HistoryECGDisplayActivity.this.getApplicationContext(), HistoryECGDisplayActivity.this.getString(R.string.p_save_fail), 1).show();
                } else if (action.equals("com.holptruly.ecg.services.NetService.NEED_LOGIN")) {
                    HistoryECGDisplayActivity.this.m2389f();
                    HistoryECGDisplayActivity.this.f2311I.showAtLocation(HistoryECGDisplayActivity.this.findViewById(R.id.record_heart_rate_sv), 17, 0, 0);
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL")) {
                    HistoryECGDisplayActivity.this.m2389f();
                    HistoryECGDisplayActivity.this.f2311I.dismiss();
                    HistoryECGDisplayActivity.this.m2371a(HistoryECGDisplayActivity.this.f2347w);
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_FAILE")) {
                    HistoryECGDisplayActivity.this.m2389f();
                    applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                    historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                    i = R.string.p_login_fail;
                } else {
                    return;
                }
                HistoryECGDisplayActivity.this.m2389f();
                return;
            }
            Toast.makeText(applicationContext, historyECGDisplayActivity.getString(i), 0).show();
        }
    };

    /* renamed from: W */
    private ProgressDialog f2325W;

    /* renamed from: a */
    TextView f2326a;

    /* renamed from: c */
    TextView f2327c;

    /* renamed from: d */
    TextView f2328d;

    /* renamed from: e */
    TextView f2329e;

    /* renamed from: f */
    SeekBar f2330f;

    /* renamed from: g */
    Switch f2331g;

    /* renamed from: h */
    MenuItem f2332h;

    /* renamed from: i */
    MenuItem f2333i;

    /* renamed from: j */
    WarickSurfaceView f2334j;

    /* renamed from: k */
    C0814c f2335k;

    /* renamed from: l */
    C0813b f2336l;

    /* renamed from: m */
    C0815d f2337m;

    /* renamed from: n */
    C0812a[] f2338n = null;

    /* renamed from: o */
    ECGApplication f2339o;

    /* renamed from: p */
    NetService f2340p;

    /* renamed from: q */
    MainService f2341q;

    /* renamed from: r */
    C0740b f2342r;

    /* renamed from: s */
    float[] f2343s;

    /* renamed from: t */
    float[] f2344t;

    /* renamed from: u */
    C0772h f2345u;

    /* renamed from: v */
    int[] f2346v;

    /* renamed from: w */
    ECGRecord f2347w;

    /* renamed from: x */
    ECGRecord f2348x;

    /* renamed from: y */
    HeartRateCounter3 f2349y;

    /* renamed from: z */
    ECGAnaysis f2350z;

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0073  */
    /* renamed from: a */
    public void m2365a(Context context) throws ParserConfigurationException, SAXException, IOException {
        String a;
        float f;
        C0775k kVar = new C0775k(context);
        if (this.f2329e.getText().toString().equals(getString(R.string.l_ecg_scale_25))) {
            f = 1.0f;
        } else {
            if (this.f2329e.getText().toString().equals(getString(R.string.l_ecg_scale_50))) {
                f = 2.0f;
            }
            kVar.mo2794a(this.f2335k.mo2923f());
            kVar.mo2793a(this.f2335k.mo2925h());
            a = kVar.mo2791a(this.f2347w, this.f2343s, this.f2330f.getProgress(), this.f2329e.getText().toString(), 1500, 3100);
            Intent intent = new Intent("android.intent.action.SEND");
            if (a == null) {
                File file = new File(a);
                if (file.exists()) {
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    intent.setType("image/png");
                    context.startActivity(Intent.createChooser(intent, "Please select a sending software"));
                } else {
                    m2387e();
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        HistoryECGDisplayActivity.this.m2389f();
                    }
                }, 1000);
                return;
            }
            return;
        }
        kVar.mo2792a(f);
        kVar.mo2794a(this.f2335k.mo2923f());
        kVar.mo2793a(this.f2335k.mo2925h());
        a = kVar.mo2791a(this.f2347w, this.f2343s, this.f2330f.getProgress(), this.f2329e.getText().toString(), 1500, 3100);
        Intent intent2 = new Intent("android.intent.action.SEND");
        if (a == null) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2366a(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                intent.setType("application/octet-stream");
                context.startActivity(Intent.createChooser(intent, "Please select a sending software"));
                return;
            }
            m2387e();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2371a(ECGRecord eCGRecord) {
        this.f2340p.mo2820a(eCGRecord);
    }

    /* renamed from: a */
    private boolean m2373a(File file) {
        try {
            C0770f.m2775a(file, "AnnotatedECG");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public float[] m2374a(String str) {
        try {
            String[] split = C0770f.m2775a(new File(str), "digits").split(" ");
            float[] fArr = new float[split.length];
            for (int i = 0; i < fArr.length; i++) {
                if (!(split[i] == "" || split[i] == " ")) {
                    fArr[i] = Float.valueOf(split[i]).floatValue();
                }
            }
            return fArr;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    private float[] m2375a(float[] fArr, int i, int i2) {
        String str = f2303A;
        C0771g.m2787d(str, getString(R.string.start) + ":" + i + getString(R.string.end) + ":" + i2);
        if (i2 > i) {
            int i3 = i2;
            i2 = i;
            i = i3;
        }
        int i4 = i - i2;
        float[] fArr2 = new float[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            fArr2[i5] = fArr[i2 + i5];
        }
        return fArr2;
    }

    /* renamed from: b */
    private ECGRecord m2377b(String str) {
        String string;
        File file = new File(str);
        if (file.isDirectory()) {
            string = getResources().getString(R.string.p_file_not_exist);
        } else if (file.isFile() && m2373a(file)) {
            return C0770f.m2774a((Context) this, str);
        } else {
            string = getResources().getString(R.string.p_not_valid_ecg_rec_file);
        }
        Toast.makeText(this, string, 0).show();
        return null;
    }

    /* renamed from: b */
    private void m2378b() {
        TextView textView;
        String str;
        ((TextView) findViewById(R.id.record_usename)).setText(this.f2347w.getUser().getName());
        ((TextView) findViewById(R.id.record_useage)).setText(this.f2347w.getUser().getAge() + "");
        ((TextView) findViewById(R.id.record_datatime)).setText(this.f2347w.getTime());
        ((TextView) findViewById(R.id.record_period)).setText(getString(R.string.l_ecg_rec_long) + "：" + this.f2347w.getPeriod());
        TextView textView2 = (TextView) findViewById(R.id.record_ecg_description);
        switch (this.f2347w.getLeadType()) {
            case 0:
                textView2.setText(getString(R.string.l_with_hand));
                this.f2314L = true;
                break;
            case 1:
                textView2.setText(getString(R.string.l_with_chest));
                this.f2314L = false;
                break;
        }
        ((TextView) findViewById(R.id.record_heartrate)).setText(String.valueOf(this.f2347w.getHeartRate()));
        ((TextView) findViewById(R.id.record_location)).setText("");
        this.f2307E = (TextView) findViewById(R.id.his_ecg_hr);
        this.f2307E.setText(String.valueOf(this.f2347w.getHeartRate()));
        this.f2334j = (WarickSurfaceView) findViewById(R.id.record_heart_rate_sv);
        this.f2335k = new C0814c();
        this.f2335k.mo2908a(1);
        if (this.f2346v != null) {
            this.f2335k.mo2910a(this.f2345u);
        }
        this.f2337m = new C0815d();
        this.f2338n = new C0812a[2];
        this.f2338n[0] = new C0812a();
        this.f2338n[1] = new C0812a();
        this.f2336l = new C0813b();
        this.f2334j.mo2893a((WarickSurfaceView.C0809a) this.f2336l);
        this.f2334j.mo2893a((WarickSurfaceView.C0809a) this.f2335k);
        this.f2334j.mo2893a((WarickSurfaceView.C0809a) this.f2337m);
        this.f2334j.mo2893a((WarickSurfaceView.C0809a) this.f2338n[0]);
        this.f2334j.mo2893a((WarickSurfaceView.C0809a) this.f2338n[1]);
        this.f2334j.mo2892a(25);
        this.f2334j.setOnTouchListener(this.f2322T);
        this.f2343s = m2374a(this.f2347w.getFilePath());
        if (this.f2343s != null) {
            this.f2335k.mo2911a(this.f2314L);
            this.f2335k.mo2912a(this.f2343s);
            this.f2349y.init();
            this.f2350z.ECGAnaysisInit();
            for (int i = 0; i < this.f2334j.getCanvasWidth(); i++) {
                this.f2349y.mo2439a(this.f2343s[i]);
                this.f2350z.setECGData(this.f2343s[i]);
            }
            if (this.f2349y.getHeartRate() != 0) {
                textView = this.f2307E;
                str = String.valueOf(this.f2349y.getHeartRate());
            } else {
                textView = this.f2307E;
                str = "NaN";
            }
            textView.setText(str);
            this.f2330f = (SeekBar) findViewById(R.id.record_scrol_sb);
            this.f2330f.setMax(this.f2343s.length);
            this.f2330f.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    TextView a;
                    String str;
                    HistoryECGDisplayActivity.this.f2335k.mo2922e(i);
                    HistoryECGDisplayActivity.this.f2338n[0].mo2904a(false);
                    HistoryECGDisplayActivity.this.f2338n[1].mo2904a(false);
                    HistoryECGDisplayActivity.this.f2337m.mo2930a(0, false);
                    HistoryECGDisplayActivity.this.f2337m.mo2930a(1, false);
                    HistoryECGDisplayActivity.this.f2349y.init();
                    HistoryECGDisplayActivity.this.f2350z.ECGAnaysisInit();
                    int i2 = i;
                    while (i2 < HistoryECGDisplayActivity.this.f2343s.length && i2 < HistoryECGDisplayActivity.this.f2334j.getWidth() + i) {
                        HistoryECGDisplayActivity.this.f2349y.mo2439a(HistoryECGDisplayActivity.this.f2343s[i2]);
                        i2++;
                    }
                    int i3 = i;
                    while (i3 < HistoryECGDisplayActivity.this.f2343s.length && i3 < HistoryECGDisplayActivity.this.f2335k.mo2915b() + i) {
                        HistoryECGDisplayActivity.this.f2349y.mo2439a(HistoryECGDisplayActivity.this.f2343s[i3]);
                        HistoryECGDisplayActivity.this.f2350z.setECGData(HistoryECGDisplayActivity.this.f2343s[i3]);
                        int rRWidth = HistoryECGDisplayActivity.this.f2349y.getRRWidth();
                        if (rRWidth != 0) {
                            int round = Math.round(((float) HistoryECGDisplayActivity.this.f2349y.mo2438a()) / 2.0f);
                            HistoryECGDisplayActivity.this.f2350z.dealRR(rRWidth, HistoryECGDisplayActivity.this.f2349y.getRWaveOffset() + round, HistoryECGDisplayActivity.this.f2349y.getSWaveOffset() + round);
                            HistoryECGDisplayActivity.this.f2350z.getPeaks();
                        }
                        i3++;
                    }
                    if (HistoryECGDisplayActivity.this.f2349y.getHeartRate() != 0) {
                        a = HistoryECGDisplayActivity.this.f2307E;
                        str = String.valueOf(HistoryECGDisplayActivity.this.f2349y.getHeartRate());
                    } else {
                        a = HistoryECGDisplayActivity.this.f2307E;
                        str = "NaN";
                    }
                    a.setText(str);
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
        this.f2326a = (TextView) findViewById(R.id.record_a1);
        this.f2327c = (TextView) findViewById(R.id.record_a2);
        this.f2328d = (TextView) findViewById(R.id.record_a1a2);
        this.f2329e = (TextView) findViewById(R.id.ecg_scale_info);
        m2382c();
    }

    /* renamed from: c */
    private void m2382c() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_tool, (ViewGroup) null);
        this.f2308F = new PopupWindow(inflate, -2, -2, false);
        this.f2308F.setBackgroundDrawable(new BitmapDrawable());
        this.f2308F.setOutsideTouchable(true);
        this.f2308F.setFocusable(true);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_share, (ViewGroup) null);
        this.f2309G = new PopupWindow(inflate2, -2, -2, false);
        this.f2309G.setBackgroundDrawable(new BitmapDrawable());
        this.f2309G.setOutsideTouchable(true);
        this.f2309G.setFocusable(true);
        this.f2331g = (Switch) inflate.findViewById(R.id.filter_switch);
        this.f2331g.setChecked(this.f2314L);
        this.f2331g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                boolean unused = HistoryECGDisplayActivity.this.f2314L = z;
                HistoryECGDisplayActivity.this.f2335k.mo2911a(HistoryECGDisplayActivity.this.f2314L);
                if (HistoryECGDisplayActivity.this.f2317O == 0) {
                    HistoryECGDisplayActivity.this.f2332h.setVisible(HistoryECGDisplayActivity.this.f2314L);
                    HistoryECGDisplayActivity.this.f2333i.setVisible(!HistoryECGDisplayActivity.this.f2314L);
                }
            }
        });
        ((Button) inflate.findViewById(R.id.btn_reverse)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.f2335k.mo2924g();
            }
        });
        ((RadioGroup) inflate.findViewById(R.id.ecg_scale_type)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                HistoryECGDisplayActivity historyECGDisplayActivity;
                TextView textView;
                int i2;
                switch (i) {
                    case R.id.ecg_scale_type_12_5:
                        HistoryECGDisplayActivity.this.f2335k.specialMo2907a(1.0f);
                        textView = HistoryECGDisplayActivity.this.f2329e;
                        historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_12_5;
                        break;
                    case R.id.ecg_scale_type_25 /*2131165298*/:
                        HistoryECGDisplayActivity.this.f2335k.mo2907a(1.0f);
                        textView = HistoryECGDisplayActivity.this.f2329e;
                        historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_25;
                        break;
                    case R.id.ecg_scale_type_50 /*2131165299*/:
                        HistoryECGDisplayActivity.this.f2335k.mo2907a(2.0f);
                        textView = HistoryECGDisplayActivity.this.f2329e;
                        historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_50;
                        break;
                    default:
                        return;
                }
                textView.setText(historyECGDisplayActivity.getString(i2));
            }
        });
        this.f2329e.setText(getString(R.string.l_ecg_scale_25));
        if (this.f2346v != null) {
            ((Button) inflate.findViewById(R.id.btn_previous)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (HistoryECGDisplayActivity.this.f2346v != null && HistoryECGDisplayActivity.this.f2317O != 2) {
                        int progress = HistoryECGDisplayActivity.this.f2330f.getProgress();
                        int b = HistoryECGDisplayActivity.this.f2345u.mo2781b(progress);
                        if (b >= 2) {
                            HistoryECGDisplayActivity.this.f2330f.setProgress((int) (((double) HistoryECGDisplayActivity.this.f2346v[b - 2]) * 0.25d));
                        }
                        if (b <= 2 && ((double) progress) >= ((double) HistoryECGDisplayActivity.this.f2346v[0]) * 0.25d) {
                            Toast.makeText(HistoryECGDisplayActivity.this, HistoryECGDisplayActivity.this.getString(R.string.p_first_exc), 0).show();
                        }
                    }
                }
            });
            ((Button) inflate.findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (HistoryECGDisplayActivity.this.f2346v != null && HistoryECGDisplayActivity.this.f2317O != 2 && HistoryECGDisplayActivity.this.f2346v != null && HistoryECGDisplayActivity.this.f2317O != 2) {
                        int progress = HistoryECGDisplayActivity.this.f2330f.getProgress();
                        int c = HistoryECGDisplayActivity.this.f2345u.mo2784c(progress);
                        double d = (double) progress;
                        if (d < ((double) HistoryECGDisplayActivity.this.f2346v[HistoryECGDisplayActivity.this.f2346v.length - 2]) * 0.25d) {
                            HistoryECGDisplayActivity.this.f2330f.setProgress((int) (((double) HistoryECGDisplayActivity.this.f2346v[c + 2]) * 0.25d));
                        }
                        if (c == HistoryECGDisplayActivity.this.f2346v.length - 4 && d <= ((double) HistoryECGDisplayActivity.this.f2346v[HistoryECGDisplayActivity.this.f2346v.length - 1]) * 0.25d) {
                            Toast.makeText(HistoryECGDisplayActivity.this, HistoryECGDisplayActivity.this.getString(R.string.p_last_exc), 0).show();
                        }
                    }
                }
            });
        } else {
            inflate.findViewById(R.id.btn_previous).setVisibility(8);
            inflate.findViewById(R.id.btn_next).setVisibility(8);
        }
        ((Button) inflate2.findViewById(R.id.btn_share_picture)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (HistoryECGDisplayActivity.this.f2309G != null) {
                    HistoryECGDisplayActivity.this.f2309G.dismiss();
                }
                HistoryECGDisplayActivity.this.m2383c(HistoryECGDisplayActivity.this.getString(R.string.generating_image));
                try {
                    HistoryECGDisplayActivity.this.m2365a((Context) HistoryECGDisplayActivity.this);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) inflate2.findViewById(R.id.btn_send_email)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.m2366a((Context) HistoryECGDisplayActivity.this, HistoryECGDisplayActivity.this.f2347w.getFilePath());
            }
        });
        ((Button) inflate2.findViewById(R.id.btn_upload)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.m2371a(HistoryECGDisplayActivity.this.f2347w);
            }
        });
        View inflate3 = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_comment, (ViewGroup) null);
        this.f2310H = new PopupWindow(inflate3, -2, -2, true);
        this.f2310H.setOutsideTouchable(false);
        this.f2310H.setFocusable(true);
        this.f2304B = (EditText) inflate3.findViewById(R.id.ecg_comment_content);
        try {
            String a = C0770f.m2775a(new File(this.f2347w.getFilePath()), "text");
            if (a.equals(this.f2347w.getDescription())) {
                this.f2304B.setText(this.f2347w.getDescription());
            } else {
                this.f2304B.setText(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((Button) inflate3.findViewById(R.id.ecg_comment_ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProgressDialog unused = HistoryECGDisplayActivity.this.f2312J = ProgressDialog.show(HistoryECGDisplayActivity.this, (CharSequence) null, HistoryECGDisplayActivity.this.getString(R.string.saving_edit), true, true, (DialogInterface.OnCancelListener) null);
                new Thread(new Runnable() {
                    public void run() {
                        String obj = HistoryECGDisplayActivity.this.f2304B.getText().toString();
                        if (obj != null) {
                            try {
                                C0770f.m2780a(new File(HistoryECGDisplayActivity.this.f2347w.getFilePath()), "text", obj);
                                HistoryECGDisplayActivity.this.f2347w.setDescription(obj);
                                new C0740b(HistoryECGDisplayActivity.this.getApplicationContext()).mo2471b(HistoryECGDisplayActivity.this.f2347w);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (HistoryECGDisplayActivity.this.f2312J != null) {
                            HistoryECGDisplayActivity.this.f2312J.dismiss();
                        }
                        ProgressDialog unused = HistoryECGDisplayActivity.this.f2312J = null;
                    }
                }).start();
                HistoryECGDisplayActivity.this.f2310H.dismiss();
            }
        });
        ((Button) inflate3.findViewById(R.id.ecg_comment_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.f2304B.setText(HistoryECGDisplayActivity.this.f2347w.getDescription());
                HistoryECGDisplayActivity.this.f2310H.dismiss();
            }
        });
        View inflate4 = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
        this.f2311I = new PopupWindow(inflate4, -2, -2, true);
        this.f2311I.setOutsideTouchable(false);
        this.f2311I.setFocusable(true);
        this.f2305C = (EditText) inflate4.findViewById(R.id.login_user_name);
        this.f2305C.setText(((ECGApplication) getApplication()).f2081b.getName());
        this.f2306D = (EditText) inflate4.findViewById(R.id.login_user_pwd);
        this.f2306D.requestFocus();
        ((Button) inflate4.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.f2340p.mo2829c(HistoryECGDisplayActivity.this.f2305C.getText().toString(), HistoryECGDisplayActivity.this.f2306D.getText().toString());
                HistoryECGDisplayActivity.this.m2383c(HistoryECGDisplayActivity.this.getString(R.string.p_login_login));
            }
        });
        ((Button) inflate4.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.f2311I.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2383c(String str) {
        if (this.f2325W == null) {
            this.f2325W = new ProgressDialog(this);
            this.f2325W.setCanceledOnTouchOutside(false);
        }
        this.f2325W.setMessage(str);
        if (!this.f2325W.isShowing()) {
            this.f2325W.show();
        }
    }

    /* renamed from: d */
    private void m2385d() {
        String mark_time = this.f2347w.getMark_time();
        if (mark_time != null && !mark_time.equals("none")) {
            String[] split = mark_time.split("\\|");
            int[] iArr = new int[(split.length * 2)];
            int i = 0;
            int i2 = 0;
            while (i < split.length) {
                String[] split2 = split[i].split(",");
                int i3 = i2;
                for (String parseInt : split2) {
                    iArr[i3] = Integer.parseInt(parseInt);
                    i3++;
                }
                i++;
                i2 = i3;
            }
            if (iArr.length >= 4 && iArr[0] == 0 && iArr[2] == 0) {
                this.f2346v = new int[2];
                for (int i4 = 0; i4 < iArr.length; i4 += 2) {
                    if (iArr[i4] == 0) {
                        this.f2346v[0] = iArr[i4];
                        this.f2346v[1] = iArr[i4 + 1];
                    } else {
                        this.f2346v = Arrays.copyOf(this.f2346v, this.f2346v.length + 2);
                        this.f2346v[this.f2346v.length - 2] = iArr[i4];
                        this.f2346v[this.f2346v.length - 1] = iArr[i4 + 1];
                    }
                }
            } else {
                this.f2346v = iArr;
            }
            this.f2345u.mo2778a(this.f2346v);
        }
    }

    /* renamed from: e */
    private void m2387e() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Tip));
        builder.setMessage(getString(R.string.p_file_not_exist));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public void m2389f() {
        if (this.f2325W != null && this.f2325W.isShowing()) {
            this.f2325W.dismiss();
        }
    }

    /* renamed from: k */
    static /* synthetic */ int m2394k(HistoryECGDisplayActivity historyECGDisplayActivity) {
        int i = historyECGDisplayActivity.f2316N;
        historyECGDisplayActivity.f2316N = i + 1;
        return i;
    }

    /* renamed from: m */
    static /* synthetic */ int m2396m(HistoryECGDisplayActivity historyECGDisplayActivity) {
        int i = historyECGDisplayActivity.f2316N;
        historyECGDisplayActivity.f2316N = i - 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Uri data;
        super.onCreate(bundle);
        setContentView(R.layout.activity_history_ecg_display);
        this.f2339o = (ECGApplication) getApplication();
        this.f2347w = (ECGRecord) getIntent().getSerializableExtra("record");
        if (this.f2347w == null && (data = getIntent().getData()) != null) {
            this.f2347w = m2377b(data.getPath());
            if (this.f2347w == null) {
                System.exit(0);
            }
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_rev_model));
        this.f2342r = new C0740b(getApplicationContext());
        this.f2349y = new HeartRateCounter3();
        this.f2349y.init();
        this.f2345u = new C0772h();
        m2385d();
        this.f2350z = new ECGAnaysis();
        this.f2350z.ECGAnaysisInit();
        bindService(new Intent(this, NetService.class), this.f2323U, 1);
        bindService(new Intent(this, MainService.class), this.f2323U, 1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.holptruly.ecg.services.NetService.BEGIN_UPLOAD_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.END_FAIL_UPLOAD_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.END_SUCCESS_UPLOAD_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.END_FAIL_UPLOAD_EXIST_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.NEED_LOGIN");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_FAILE");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS");
        intentFilter.addAction("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2324V, intentFilter);
        this.f2315M = new Object();
        m2378b();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Menu menu2 = menu;
        getMenuInflater().inflate(R.menu.ecg_history_display_menu, menu2);
        this.f2332h = menu2.findItem(R.id.action_ecg_filter_on);
        this.f2333i = menu2.findItem(R.id.action_ecg_filter_off);
        if (this.f2317O == 0) {
            this.f2332h.setVisible(this.f2314L);
            this.f2333i.setVisible(!this.f2314L);
            menu2.findItem(R.id.action_model_cut).setVisible(true);
            menu2.findItem(R.id.action_model_back).setVisible(false);
            menu2.findItem(R.id.action_ecg_reverse).setVisible(true);
            menu2.findItem(R.id.action_share).setVisible(true);
            menu2.findItem(R.id.action_ecg_edit_cmt).setVisible(true);
            menu2.findItem(R.id.action_cut_start).setVisible(false);
            menu2.findItem(R.id.action_cut_end).setVisible(false);
            menu2.findItem(R.id.action_cut).setVisible(false);
            menu2.findItem(R.id.action_save_cut).setVisible(false);
            menu2.findItem(R.id.action_cancel_cut).setVisible(false);
            this.f2326a.setTextColor(getResources().getColor(R.color.green1));
            this.f2326a.setText("A1：-- mV");
            this.f2327c.setTextColor(getResources().getColor(R.color.green1));
            this.f2327c.setText("A2：-- mV");
            this.f2328d.setVisibility(0);
            this.f2328d.setTextColor(getResources().getColor(R.color.green1));
            return true;
        } else if (this.f2317O == 1) {
            this.f2332h.setVisible(false);
            this.f2333i.setVisible(false);
            menu2.findItem(R.id.action_model_cut).setVisible(false);
            menu2.findItem(R.id.action_model_back).setVisible(true);
            menu2.findItem(R.id.action_ecg_reverse).setVisible(false);
            menu2.findItem(R.id.action_share).setVisible(false);
            menu2.findItem(R.id.action_ecg_edit_cmt).setVisible(false);
            menu2.findItem(R.id.action_cut_start).setVisible(true);
            menu2.findItem(R.id.action_cut_end).setVisible(true);
            menu2.findItem(R.id.action_cut).setVisible(true);
            menu2.findItem(R.id.action_save_cut).setVisible(false);
            menu2.findItem(R.id.action_cancel_cut).setVisible(false);
            this.f2326a.setTextColor(getResources().getColor(R.color.green1));
            TextView textView = this.f2326a;
            textView.setText(getString(R.string.start) + "：-- ms");
            this.f2327c.setTextColor(getResources().getColor(R.color.green1));
            TextView textView2 = this.f2327c;
            textView2.setText(getString(R.string.end) + "：-- ms");
            this.f2328d.setVisibility(View.GONE);
            return true;
        } else {
            if (this.f2317O == 2) {
                this.f2332h.setVisible(false);
                this.f2333i.setVisible(false);
                menu2.findItem(R.id.action_ecg_reverse).setVisible(false);
                menu2.findItem(R.id.action_model_cut).setVisible(false);
                menu2.findItem(R.id.action_model_back).setVisible(false);
                menu2.findItem(R.id.action_share).setVisible(false);
                menu2.findItem(R.id.action_ecg_edit_cmt).setVisible(false);
                menu2.findItem(R.id.action_cut_start).setVisible(false);
                menu2.findItem(R.id.action_cut_end).setVisible(false);
                menu2.findItem(R.id.action_cut).setVisible(false);
                menu2.findItem(R.id.action_save_cut).setVisible(true);
                menu2.findItem(R.id.action_cancel_cut).setVisible(true);
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.f2336l != null) {
            this.f2336l.mo2905a();
        }
        unbindService(this.f2323U);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2324V);
        super.onDestroy();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x01db, code lost:
        r12.mo2927a(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x01de, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0290, code lost:
        android.widget.Toast.makeText(r12, getString(r0), 0).show();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x029b, code lost:
        return true;
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Context applicationContext;
        int i;
        C0815d dVar;
        int i2;
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.action_cancel_cut /*2131165199*/:
                this.f2335k.mo2917c();
                this.f2335k.mo2912a(this.f2343s);
                this.f2335k.mo2922e(0);
                this.f2330f.setProgress(0);
                this.f2330f.setMax(this.f2343s.length);
                this.f2318P = false;
                this.f2319Q = false;
                this.f2320R = 0;
                this.f2321S = 0;
                this.f2317O = 1;
                setTitle(getString(R.string.title_cut_model));
                invalidateOptionsMenu();
                return true;
            case R.id.action_cut /*2131165201*/:
                if (this.f2321S == 0) {
                    applicationContext = getApplicationContext();
                    i = R.string.p_ecg_rec_no_end_point;
                    break;
                } else {
                    this.f2317O = 2;
                    setTitle(getString(R.string.title_rev_model));
                    invalidateOptionsMenu();
                    this.f2344t = m2375a(this.f2343s, this.f2320R, this.f2321S);
                    this.f2335k.mo2912a(this.f2344t);
                    this.f2335k.mo2922e(0);
                    this.f2330f.setMax(this.f2344t.length);
                    this.f2330f.setProgress(0);
                    this.f2335k.mo2920d();
                    return true;
                }
            case R.id.action_cut_end /*2131165202*/:
                this.f2318P = false;
                this.f2319Q = true;
                TextView textView = this.f2327c;
                textView.setText(getString(R.string.end) + "：--ms");
                applicationContext = getApplicationContext();
                i = R.string.p_ecg_rec_end_cut_point;
                break;
            case R.id.action_cut_start /*2131165203*/:
                this.f2318P = true;
                this.f2319Q = false;
                TextView textView2 = this.f2326a;
                textView2.setText(getString(R.string.start) + "：--ms");
                applicationContext = getApplicationContext();
                i = R.string.p_ecg_rec_start_cut_point;
                break;
            case R.id.action_ecg_edit_cmt /*2131165205*/:
                this.f2310H.showAtLocation(findViewById(R.id.record_heart_rate_sv), 17, 0, 0);
                return true;
            case R.id.action_ecg_filter_off /*2131165206*/:
                this.f2314L = true;
                this.f2335k.mo2911a(this.f2314L);
                this.f2331g.setChecked(this.f2314L);
                this.f2332h.setVisible(true);
                this.f2333i.setVisible(false);
                return true;
            case R.id.action_ecg_filter_on /*2131165207*/:
                this.f2314L = false;
                this.f2335k.mo2911a(this.f2314L);
                this.f2331g.setChecked(this.f2314L);
                this.f2332h.setVisible(false);
                this.f2333i.setVisible(true);
                return true;
            case R.id.action_ecg_reverse /*2131165209*/:
                this.f2335k.mo2924g();
                return true;
            case R.id.action_menu_options /*2131165217*/:
                this.f2308F.showAsDropDown(findViewById(menuItem.getItemId()), -450, 0);
                return true;
            case R.id.action_model_back /*2131165222*/:
                this.f2337m.mo2930a(0, false);
                this.f2337m.mo2930a(1, false);
                this.f2338n[0].mo2904a(false);
                this.f2338n[1].mo2904a(false);
                this.f2317O = 0;
                setTitle(getString(R.string.title_rev_model));
                invalidateOptionsMenu();
                dVar = this.f2337m;
                i2 = -16711936;
                break;
            case R.id.action_model_cut /*2131165223*/:
                this.f2337m.mo2930a(0, false);
                this.f2337m.mo2930a(1, false);
                this.f2338n[0].mo2904a(false);
                this.f2338n[1].mo2904a(false);
                this.f2317O = 1;
                setTitle(getString(R.string.title_cut_model));
                invalidateOptionsMenu();
                this.f2318P = false;
                this.f2319Q = false;
                this.f2320R = 0;
                this.f2321S = 0;
                dVar = this.f2337m;
                i2 = Color.parseColor("#179E98");
                break;
            case R.id.action_save_cut /*2131165229*/:
                m2383c(getString(R.string.saving_to_file));
                this.f2348x = new ECGRecord();
                this.f2348x.setUser(this.f2347w.getUser());
                this.f2348x.setHeartRate(this.f2347w.getHeartRate());
                this.f2348x.setMachine(this.f2347w.getMachine());
                this.f2348x.setLeadType(this.f2347w.getLeadType());
                long currentTimeMillis = System.currentTimeMillis();
                this.f2348x.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Long.valueOf(currentTimeMillis)));
                int abs = Math.abs((this.f2321S * 4) - (this.f2320R * 4)) / 1000;
                int i3 = abs / 3600;
                int i4 = abs % 3600;
                ECGRecord eCGRecord = this.f2348x;
                eCGRecord.setPeriod(String.format("%02d", new Object[]{Integer.valueOf(i3)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(i4 / 60)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(i4 % 60)}));
                String str = f2303A;
                StringBuilder sb = new StringBuilder();
                sb.append("xml:");
                sb.append(this.f2347w.getFilePath());
                C0771g.m2784a(str, sb.toString());
                try {
                    ECGEntity a = C0770f.m2773a(this.f2347w.getFilePath());
                    a.setStartTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(Long.valueOf(currentTimeMillis)));
                    a.setEndTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(Long.valueOf(currentTimeMillis + ((long) Math.abs((this.f2321S * 4) - (this.f2320R * 4))))));
                    a.setMark_time((String) null);
                    a.setMark_period((int[]) null);
                    this.f2348x.setEcgEntity(a);
                    this.f2348x.setMark_time(a.getMark_time());
                    this.f2341q.mo2722a(this.f2348x, m2375a(this.f2343s, this.f2320R, this.f2321S));
                    this.f2317O = 0;
                    setTitle(getString(R.string.title_rev_model));
                    invalidateOptionsMenu();
                    return true;
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                    return true;
                } catch (SAXException e2) {
                    e2.printStackTrace();
                    return true;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return true;
                }
            case R.id.action_share /*2131165231*/:
                try {
                    this.f2309G.showAsDropDown(findViewById(menuItem.getItemId()), -410, 0);
                    return true;
                } catch (Exception e4) {
                    e4.printStackTrace();
                    return true;
                }
            default:
                
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.f2308F.dismiss();
        this.f2309G.dismiss();
        super.onStop();
    }
}
