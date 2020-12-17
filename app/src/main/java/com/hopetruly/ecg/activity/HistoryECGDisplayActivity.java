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
import com.hopetruly.ecg.sql.SqlManager;
import com.hopetruly.ecg.services.MainService;
import com.hopetruly.ecg.util.ECGRecordUtils;
import com.hopetruly.ecg.util.EcgParserUtils;
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.ecg.util.HistoryDisplayUtils;
import com.hopetruly.part.net.NetService;
import com.warick.drawable.WarickSurfaceView;
import com.warick.drawable.drawlistener.HisToryEcgProgressDrawListener;
import com.warick.drawable.drawlistener.GrideDrawListener;
import com.warick.drawable.drawlistener.EcgCovertDrawListener;
import com.warick.drawable.drawlistener.HistoryRecordDrawListener;
import com.warick.jni.filter.Fir;

import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

public class HistoryECGDisplayActivity extends BaseActivity {
    /* access modifiers changed from: private */

    /* renamed from: A */
    public static final String TAG = "HistoryECGDisplayActivity";
    /* access modifiers changed from: private */

    /* renamed from: B */
    public EditText edt_ecg_comment_content;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public EditText edt_login_user_name;
    /* access modifiers changed from: private */

    /* renamed from: D */
    public EditText edt_login_user_pwd;
    /* access modifiers changed from: private */

    /* renamed from: E */
    public TextView tv_his_ecg_hr;

    /* renamed from: F */
    private PopupWindow pop_pupop_ecg_tool;
    /* access modifiers changed from: private */

    /* renamed from: G */
    public PopupWindow pop_pupop_ecg_share;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public PopupWindow poppupop_ecg_comment;
    /* access modifiers changed from: private */

    /* renamed from: I */
    public PopupWindow pop_pupop_login;
    /* access modifiers changed from: private */

    /* renamed from: J */
    public ProgressDialog saveProgressDialog = null;

    /* renamed from: K */
    private double[] doubles5 = new double[Fir.getOrder(Fir.Fir_5)];
    /* access modifiers changed from: private */

    /* renamed from: L */
    public boolean isHandOrBody = true;
    /* access modifiers changed from: private */

    /* renamed from: M */
    public Object obj;
    /* access modifiers changed from: private */

    /* renamed from: N */
    public int f2316N = 0;
    /* access modifiers changed from: private */

    /* renamed from: O */
    public int ecgCutMode = 0;
    /* access modifiers changed from: private */

    /* renamed from: P */
    public boolean isStartCut = false;
    /* access modifiers changed from: private */

    /* renamed from: Q */
    public boolean isEndCut = false;
    /* access modifiers changed from: private */

    /* renamed from: R */
    public int f2320R = 0;
    /* access modifiers changed from: private */

    /* renamed from: S */
    public int f2321S = 0;

    /* renamed from: T */
    private View.OnTouchListener historyOnTouchListener = new View.OnTouchListener() {
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
            HistoryRecordDrawListener dVar;
            Object n;
            TextView textView;
            String str;
            String a;
            String str2;
            TextView textView2;
            String str3;
            try {
                DecimalFormat decimalFormat = new DecimalFormat("0.0000");
                if (HistoryECGDisplayActivity.this.ecgCutMode == 0) {
                    switch (motionEvent.getAction() & 255) {
                        case 0:
                            HistoryECGDisplayActivity.m2394k(HistoryECGDisplayActivity.this);
                            Log.i(HistoryECGDisplayActivity.TAG, "onTouchEvent>ACTION_DOWN>mode:" + HistoryECGDisplayActivity.this.f2316N);
                            int x2 = (int) motionEvent.getX();
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, true);
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, false);
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2929a(0, "A1");
                            float f = (float) x2;
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(0, f);
                            float d = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x2));
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2904a(true);
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2904a(false);
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2903a((String) null, f, HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2906a() * d);
                            HistoryECGDisplayActivity.this.tv_record_a1.setText("A1：" + decimalFormat.format((double) (d / 1000.0f)) + " mV");
                            HistoryECGDisplayActivity.this.tv_record_a2.setText("A2：-- mV");
                            textView = HistoryECGDisplayActivity.this.tv_record_a1a2;
                            str = "A1-A2:-- ms";
                            break;
                        case 1:
                            HistoryECGDisplayActivity.m2396m(HistoryECGDisplayActivity.this);
                            a = HistoryECGDisplayActivity.TAG;
                            str2 = "onTouchEvent>ACTION_UP>mode:" + HistoryECGDisplayActivity.this.f2316N;
                            break;
                        case 2:
                            if (HistoryECGDisplayActivity.this.f2316N != 1) {
                                int x3 = (int) motionEvent.getX(0);
                                int x4 = (int) motionEvent.getX(1);
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, true);
                                float f2 = (float) x3;
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(0, f2);
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, true);
                                float f3 = (float) x4;
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(1, f3);
                                float d2 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x3));
                                HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2904a(true);
                                HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2903a((String) null, f2, HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2906a() * d2);
                                float d3 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x4));
                                HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2904a(true);
                                HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2903a((String) null, f3, HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2906a() * d3);
                                HistoryECGDisplayActivity.this.tv_record_a1.setText("A1：" + decimalFormat.format((double) (d2 / 1000.0f)) + " mV");
                                HistoryECGDisplayActivity.this.tv_record_a2.setText("A2：" + decimalFormat.format((double) (d3 / 1000.0f)) + " mV");
                                textView2 = HistoryECGDisplayActivity.this.tv_record_a1a2;
                                str3 = "A1-A2:" + ((float) (Math.abs(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x4) - HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x3)) * 4)) + " ms";
                                break;
                            } else {
                                int x5 = (int) motionEvent.getX();
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, true);
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, false);
                                float f4 = (float) x5;
                                HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(0, f4);
                                float d4 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x5));
                                HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2903a((String) null, f4, HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2906a() * d4);
                                HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2904a(false);
                                HistoryECGDisplayActivity.this.tv_record_a1.setText("A1：" + decimalFormat.format((double) (d4 / 1000.0f)) + " mV");
                                HistoryECGDisplayActivity.this.tv_record_a2.setText("A2：-- mV");
                                textView = HistoryECGDisplayActivity.this.tv_record_a1a2;
                                str = "A1-A2:-- ms";
                                break;
                            }
                        case 5:
                            HistoryECGDisplayActivity.m2394k(HistoryECGDisplayActivity.this);
                            Log.i(HistoryECGDisplayActivity.TAG, "onTouchEvent>ACTION_POINTER_DOWN>mode:" + HistoryECGDisplayActivity.this.f2316N);
                            int x6 = (int) motionEvent.getX(0);
                            int x7 = (int) motionEvent.getX(1);
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, true);
                            float f5 = (float) x6;
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(0, f5);
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, true);
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2929a(1, "A2");
                            float f6 = (float) x7;
                            HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(1, f6);
                            float d5 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x6));
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2904a(true);
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2903a((String) null, f5, d5 * HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2906a());
                            float d6 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x7));
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2904a(true);
                            HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2903a((String) null, f6, HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2906a() * d6);
                            float d7 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2919d(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x6));
                            HistoryECGDisplayActivity.this.tv_record_a1.setText("A1：" + decimalFormat.format((double) (d7 / 1000.0f)) + " mV");
                            HistoryECGDisplayActivity.this.tv_record_a2.setText("A2：" + decimalFormat.format((double) (d6 / 1000.0f)) + " mV");
                            textView2 = HistoryECGDisplayActivity.this.tv_record_a1a2;
                            str3 = "A1-A2:" + ((float) (Math.abs(HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x7) - HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x6)) * 4)) + " ms";
                            break;
                        case 6:
                            HistoryECGDisplayActivity.m2396m(HistoryECGDisplayActivity.this);
                            a = HistoryECGDisplayActivity.TAG;
                            str2 = "onTouchEvent>ACTION_POINTER_UP>mode:" + HistoryECGDisplayActivity.this.f2316N;
                            break;
                    }
                } else if (HistoryECGDisplayActivity.this.ecgCutMode != 1) {
                    return false;
                } else {
                    switch (motionEvent.getAction() & 255) {
                        case 0:
                            if (HistoryECGDisplayActivity.this.isStartCut || HistoryECGDisplayActivity.this.isEndCut) {
                                x = (int) motionEvent.getX();
                                if (HistoryECGDisplayActivity.this.isStartCut) {
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, true);
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2929a(0, HistoryECGDisplayActivity.this.getString(R.string.start));
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(0, (float) x);
                                }
                                if (HistoryECGDisplayActivity.this.isEndCut) {
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, true);
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2929a(1, HistoryECGDisplayActivity.this.getString(R.string.end));
                                    dVar = HistoryECGDisplayActivity.this.mHistoryRecordDrawListener;
                                }
                            }
                            break;
                        case 1:
                            int x8 = (int) motionEvent.getX();
                            if (!HistoryECGDisplayActivity.this.isStartCut) {
                                if (HistoryECGDisplayActivity.this.isEndCut) {
                                    int c = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x8);
                                    HistoryECGDisplayActivity.this.tv_record_a2.setText(HistoryECGDisplayActivity.this.getString(R.string.end) + "：" + (c * 4) + "ms");
                                    int unused = HistoryECGDisplayActivity.this.f2321S = c;
                                    break;
                                }
                            } else {
                                int c2 = HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2916c(x8);
                                HistoryECGDisplayActivity.this.tv_record_a1.setText(HistoryECGDisplayActivity.this.getString(R.string.start) + "：" + (c2 * 4) + "ms");
                                int unused2 = HistoryECGDisplayActivity.this.f2320R = c2;
                                break;
                            }
                            break;
                        case 2:
                            if (HistoryECGDisplayActivity.this.isStartCut || HistoryECGDisplayActivity.this.isEndCut) {
                                x = (int) motionEvent.getX();
                                if (HistoryECGDisplayActivity.this.isStartCut) {
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, true);
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2928a(0, (float) x);
                                }
                                if (HistoryECGDisplayActivity.this.isEndCut) {
                                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, true);
                                    dVar = HistoryECGDisplayActivity.this.mHistoryRecordDrawListener;
                                }
                            }
                            break;
                    }
                    synchronized (HistoryECGDisplayActivity.this.obj) {
                        try {
                            HistoryECGDisplayActivity.this.obj.wait(50);
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
    private ServiceConnection historyServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder instanceof NetService.NetSerBinder) {
                HistoryECGDisplayActivity.this.historyNetSerBinder = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
            } else if (iBinder instanceof MainService.MainBinder) {
                HistoryECGDisplayActivity.this.historyMainBinder = ((MainService.MainBinder) iBinder).getMainBinder();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            HistoryECGDisplayActivity.this.historyNetSerBinder = null;
            HistoryECGDisplayActivity.this.historyMainBinder = null;
        }
    };

    /* renamed from: V */
    private BroadcastReceiver historyBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Context applicationContext;
            HistoryECGDisplayActivity historyECGDisplayActivity;
            int i;
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.NetService.BEGIN_UPLOAD_ACTION")) {
                HistoryECGDisplayActivity.this.showMsgProgressDialog(HistoryECGDisplayActivity.this.getResources().getString(R.string.uploading));
                return;
            }
            if (action.equals("com.holptruly.ecg.services.NetService.END_SUCCESS_UPLOAD_ACTION")) {
                HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                i = R.string.p_upload_success;
            } else if (action.equals("com.holptruly.ecg.services.NetService.END_FAIL_UPLOAD_ACTION")) {
                HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                i = R.string.p_upload_fail;
            } else if (action.equals("com.holptruly.ecg.services.NetService.END_FAIL_UPLOAD_EXIST_ACTION")) {
                HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                i = R.string.p_exist_fail;
            } else {
                if ("com.hopetruly.ecg.services.MainService.FILE_SAVE_SUCCESS".equals(action)) {
                    HistoryECGDisplayActivity.this.ecgMakes = null;
                    HistoryECGDisplayActivity.this.mrecord = HistoryECGDisplayActivity.this.mCutRecord;
                    HistoryECGDisplayActivity.this.f2343s = HistoryECGDisplayActivity.this.hisAnnotatedECG(HistoryECGDisplayActivity.this.mrecord.getFilePath());
                    HistoryECGDisplayActivity.this.mEcgCovertDrawListener.setHistoryEcgs(HistoryECGDisplayActivity.this.f2343s);
                    HistoryECGDisplayActivity.this.mEcgCovertDrawListener.setRecordProgress(0);
                    HistoryECGDisplayActivity.this.sb_His.setProgress(0);
                } else if (action.equals("com.hopetruly.ecg.services.MainService.FILE_SAVE_FAIL")) {
                    Toast.makeText(HistoryECGDisplayActivity.this.getApplicationContext(), HistoryECGDisplayActivity.this.getString(R.string.p_save_fail), 1).show();
                } else if (action.equals("com.holptruly.ecg.services.NetService.NEED_LOGIN")) {
                    HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                    HistoryECGDisplayActivity.this.pop_pupop_login.showAtLocation(HistoryECGDisplayActivity.this.findViewById(R.id.record_heart_rate_sv), 17, 0, 0);
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL")) {
                    HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                    HistoryECGDisplayActivity.this.pop_pupop_login.dismiss();
                    HistoryECGDisplayActivity.this.uploadRecord(HistoryECGDisplayActivity.this.mrecord);
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_FAILE")) {
                    HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                    applicationContext = HistoryECGDisplayActivity.this.getApplicationContext();
                    historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                    i = R.string.p_login_fail;
                } else {
                    return;
                }
                HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                return;
            }
            Toast.makeText(applicationContext, historyECGDisplayActivity.getString(i), Toast.LENGTH_LONG).show();
        }
    };

    /* renamed from: W */
    private ProgressDialog myMsgProgressDialog;

    /* renamed from: a */
    TextView tv_record_a1;

    /* renamed from: c */
    TextView tv_record_a2;

    /* renamed from: d */
    TextView tv_record_a1a2;

    /* renamed from: e */
    TextView tv_ecg_scale_info;

    /* renamed from: f */
    SeekBar sb_His;

    /* renamed from: g */
    Switch sw_filter_switch;

    /* renamed from: h */
    MenuItem menu_action_ecg_filter_on;

    /* renamed from: i */
    MenuItem menu_ecg_filter_off;

    /* renamed from: j */
    WarickSurfaceView mHistoryWarickSurfaceView;

    /* renamed from: k */
    EcgCovertDrawListener mEcgCovertDrawListener;

    /* renamed from: l */
    GrideDrawListener mGrideDrawListener;

    /* renamed from: m */
    HistoryRecordDrawListener mHistoryRecordDrawListener;

    /* renamed from: n */
    HisToryEcgProgressDrawListener[] mHisToryEcgProgressDrawListener = null;

    /* renamed from: o */
    ECGApplication mECGApplication;

    /* renamed from: p */
    NetService historyNetSerBinder;

    /* renamed from: q */
    MainService historyMainBinder;

    /* renamed from: r */
    SqlManager mSqlManager;

    /* renamed from: s */
    float[] f2343s;

    /* renamed from: t */
    float[] f2344t;

    /* renamed from: u */
    EcgParserUtils mEcgParserUtils;

    /* renamed from: v */
    int[] ecgMakes;

    /* renamed from: w */
    ECGRecord mrecord;

    /* renamed from: x */
    ECGRecord mCutRecord;

    /* renamed from: y */
    HeartRateCounter3 mHeartRateCounter3;

    /* renamed from: z */
    ECGAnaysis mECGAnaysis;

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0073  */
    /* renamed from: a */
    public void m2365a(Context context) throws ParserConfigurationException, SAXException, IOException {
        String a;
        float f;
        HistoryDisplayUtils kVar = new HistoryDisplayUtils(context);
        if (this.tv_ecg_scale_info.getText().toString().equals(getString(R.string.l_ecg_scale_25))) {
            f = 1.0f;
        } else {
            if (this.tv_ecg_scale_info.getText().toString().equals(getString(R.string.l_ecg_scale_50))) {
                f = 2.0f;
            }
            kVar.setFilterOpen(this.mEcgCovertDrawListener.isFiterOpen());
            kVar.setReverseMark(this.mEcgCovertDrawListener.getReverseMark());
            a = kVar.drawReport(this.mrecord, this.f2343s, this.sb_His.getProgress(), this.tv_ecg_scale_info.getText().toString(), 1500, 3100);
            Intent intent = new Intent("android.intent.action.SEND");
            if (a == null) {
                File file = new File(a);
                if (file.exists()) {
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    intent.setType("image/png");
                    context.startActivity(Intent.createChooser(intent, "Please select a sending software"));
                } else {
                    showp_file_not_existDialog();
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        HistoryECGDisplayActivity.this.dismissmyMsgProgressDialog();
                    }
                }, 1000);
                return;
            }
            return;
        }
        kVar.mo2792a(f);
        kVar.setFilterOpen(this.mEcgCovertDrawListener.isFiterOpen());
        kVar.setReverseMark(this.mEcgCovertDrawListener.getReverseMark());
        a = kVar.drawReport(this.mrecord, this.f2343s, this.sb_His.getProgress(), this.tv_ecg_scale_info.getText().toString(), 1500, 3100);
        Intent intent2 = new Intent("android.intent.action.SEND");
        if (a == null) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void sendEmail(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                intent.setType("application/octet-stream");
                context.startActivity(Intent.createChooser(intent, "Please select a sending software"));
                return;
            }
            showp_file_not_existDialog();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void uploadRecord(ECGRecord eCGRecord) {
        this.historyNetSerBinder.uploadingRecord(eCGRecord);
    }

    /* renamed from: a */
    private boolean annotatedHisECG(File file) {
        try {
            ECGRecordUtils.annotatedECG(file, "AnnotatedECG");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public float[] hisAnnotatedECG(String str) {
        try {
            String[] split = ECGRecordUtils.annotatedECG(new File(str), "digits").split(" ");
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
    private float[] cutDataStartEnd(float[] fArr, int i, int i2) {
        String str = TAG;
        LogUtils.logE(str, getString(R.string.start) + ":" + i + getString(R.string.end) + ":" + i2);
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
    private ECGRecord assertPathUri(String str) {
        String string;
        File file = new File(str);
        if (file.isDirectory()) {
            string = getResources().getString(R.string.p_file_not_exist);
        } else if (file.isFile() && annotatedHisECG(file)) {
            return ECGRecordUtils.m2774a((Context) this, str);
        } else {
            string = getResources().getString(R.string.p_not_valid_ecg_rec_file);
        }
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        return null;
    }

    /* renamed from: b */
    private void initView() {
        TextView textView;
        String str;
        ((TextView) findViewById(R.id.record_usename)).setText(this.mrecord.getUser().getName());
        ((TextView) findViewById(R.id.record_useage)).setText(this.mrecord.getUser().getAge() + "");
        ((TextView) findViewById(R.id.record_datatime)).setText(this.mrecord.getTime());
        ((TextView) findViewById(R.id.record_period)).setText(getString(R.string.l_ecg_rec_long) + "：" + this.mrecord.getPeriod());
        TextView textView2 = (TextView) findViewById(R.id.record_ecg_description);
        switch (this.mrecord.getLeadType()) {
            case 0:
                textView2.setText(getString(R.string.l_with_hand));
                this.isHandOrBody = true;
                break;
            case 1:
                textView2.setText(getString(R.string.l_with_chest));
                this.isHandOrBody = false;
                break;
        }
        ((TextView) findViewById(R.id.record_heartrate)).setText(String.valueOf(this.mrecord.getHeartRate()));
        ((TextView) findViewById(R.id.record_location)).setText("");
        this.tv_his_ecg_hr = (TextView) findViewById(R.id.his_ecg_hr);
        this.tv_his_ecg_hr.setText(String.valueOf(this.mrecord.getHeartRate()));
        this.mHistoryWarickSurfaceView = (WarickSurfaceView) findViewById(R.id.record_heart_rate_sv);
        this.mEcgCovertDrawListener = new EcgCovertDrawListener();
        this.mEcgCovertDrawListener.setRealHisMode(1);
        if (this.ecgMakes != null) {
            this.mEcgCovertDrawListener.setEcgParserUtils(this.mEcgParserUtils);
        }
        this.mHistoryRecordDrawListener = new HistoryRecordDrawListener();
        this.mHisToryEcgProgressDrawListener = new HisToryEcgProgressDrawListener[2];
        this.mHisToryEcgProgressDrawListener[0] = new HisToryEcgProgressDrawListener();
        this.mHisToryEcgProgressDrawListener[1] = new HisToryEcgProgressDrawListener();
        this.mGrideDrawListener = new GrideDrawListener();
        this.mHistoryWarickSurfaceView.addDrawListener( this.mGrideDrawListener);
        this.mHistoryWarickSurfaceView.addDrawListener( this.mEcgCovertDrawListener);
        this.mHistoryWarickSurfaceView.addDrawListener( this.mHistoryRecordDrawListener);
        this.mHistoryWarickSurfaceView.addDrawListener( this.mHisToryEcgProgressDrawListener[0]);
        this.mHistoryWarickSurfaceView.addDrawListener(this.mHisToryEcgProgressDrawListener[1]);
        this.mHistoryWarickSurfaceView.setSpeed(25);
        this.mHistoryWarickSurfaceView.setOnTouchListener(this.historyOnTouchListener);
        this.f2343s = hisAnnotatedECG(this.mrecord.getFilePath());
        if (this.f2343s != null) {
            this.mEcgCovertDrawListener.offEcg_filter(this.isHandOrBody);
            this.mEcgCovertDrawListener.setHistoryEcgs(this.f2343s);
            this.mHeartRateCounter3.init();
            this.mECGAnaysis.ECGAnaysisInit();
            for (int i = 0; i < this.mHistoryWarickSurfaceView.getCanvasWidth(); i++) {
                this.mHeartRateCounter3.mo2439a(this.f2343s[i]);
                this.mECGAnaysis.setECGData(this.f2343s[i]);
            }
            if (this.mHeartRateCounter3.getHeartRate() != 0) {
                textView = this.tv_his_ecg_hr;
                str = String.valueOf(this.mHeartRateCounter3.getHeartRate());
            } else {
                textView = this.tv_his_ecg_hr;
                str = "NaN";
            }
            textView.setText(str);
            this.sb_His = (SeekBar) findViewById(R.id.record_scrol_sb);
            this.sb_His.setMax(this.f2343s.length);
            this.sb_His.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    TextView a;
                    String str;
                    HistoryECGDisplayActivity.this.mEcgCovertDrawListener.setRecordProgress(i);
                    HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[0].mo2904a(false);
                    HistoryECGDisplayActivity.this.mHisToryEcgProgressDrawListener[1].mo2904a(false);
                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(0, false);
                    HistoryECGDisplayActivity.this.mHistoryRecordDrawListener.mo2930a(1, false);
                    HistoryECGDisplayActivity.this.mHeartRateCounter3.init();
                    HistoryECGDisplayActivity.this.mECGAnaysis.ECGAnaysisInit();
                    int i2 = i;
                    while (i2 < HistoryECGDisplayActivity.this.f2343s.length && i2 < HistoryECGDisplayActivity.this.mHistoryWarickSurfaceView.getWidth() + i) {
                        HistoryECGDisplayActivity.this.mHeartRateCounter3.mo2439a(HistoryECGDisplayActivity.this.f2343s[i2]);
                        i2++;
                    }
                    int i3 = i;
                    while (i3 < HistoryECGDisplayActivity.this.f2343s.length && i3 < HistoryECGDisplayActivity.this.mEcgCovertDrawListener.mo2915b() + i) {
                        HistoryECGDisplayActivity.this.mHeartRateCounter3.mo2439a(HistoryECGDisplayActivity.this.f2343s[i3]);
                        HistoryECGDisplayActivity.this.mECGAnaysis.setECGData(HistoryECGDisplayActivity.this.f2343s[i3]);
                        int rRWidth = HistoryECGDisplayActivity.this.mHeartRateCounter3.getRRWidth();
                        if (rRWidth != 0) {
                            int round = Math.round(((float) HistoryECGDisplayActivity.this.mHeartRateCounter3.mo2438a()) / 2.0f);
                            HistoryECGDisplayActivity.this.mECGAnaysis.dealRR(rRWidth, HistoryECGDisplayActivity.this.mHeartRateCounter3.getRWaveOffset() + round, HistoryECGDisplayActivity.this.mHeartRateCounter3.getSWaveOffset() + round);
                            HistoryECGDisplayActivity.this.mECGAnaysis.getPeaks();
                        }
                        i3++;
                    }
                    if (HistoryECGDisplayActivity.this.mHeartRateCounter3.getHeartRate() != 0) {
                        a = HistoryECGDisplayActivity.this.tv_his_ecg_hr;
                        str = String.valueOf(HistoryECGDisplayActivity.this.mHeartRateCounter3.getHeartRate());
                    } else {
                        a = HistoryECGDisplayActivity.this.tv_his_ecg_hr;
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
        this.tv_record_a1 = (TextView) findViewById(R.id.record_a1);
        this.tv_record_a2 = (TextView) findViewById(R.id.record_a2);
        this.tv_record_a1a2 = (TextView) findViewById(R.id.record_a1a2);
        this.tv_ecg_scale_info = (TextView) findViewById(R.id.ecg_scale_info);
        initPopView();
    }

    /* renamed from: c */
    private void initPopView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_tool, (ViewGroup) null);
        this.pop_pupop_ecg_tool = new PopupWindow(inflate, -2, -2, false);
        this.pop_pupop_ecg_tool.setBackgroundDrawable(new BitmapDrawable());
        this.pop_pupop_ecg_tool.setOutsideTouchable(true);
        this.pop_pupop_ecg_tool.setFocusable(true);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_share, (ViewGroup) null);
        this.pop_pupop_ecg_share = new PopupWindow(inflate2, -2, -2, false);
        this.pop_pupop_ecg_share.setBackgroundDrawable(new BitmapDrawable());
        this.pop_pupop_ecg_share.setOutsideTouchable(true);
        this.pop_pupop_ecg_share.setFocusable(true);
        this.sw_filter_switch = (Switch) inflate.findViewById(R.id.filter_switch);
        this.sw_filter_switch.setChecked(this.isHandOrBody);
        this.sw_filter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                boolean unused = HistoryECGDisplayActivity.this.isHandOrBody = z;
                HistoryECGDisplayActivity.this.mEcgCovertDrawListener.offEcg_filter(HistoryECGDisplayActivity.this.isHandOrBody);
                if (HistoryECGDisplayActivity.this.ecgCutMode == 0) {
                    HistoryECGDisplayActivity.this.menu_action_ecg_filter_on.setVisible(HistoryECGDisplayActivity.this.isHandOrBody);
                    HistoryECGDisplayActivity.this.menu_ecg_filter_off.setVisible(!HistoryECGDisplayActivity.this.isHandOrBody);
                }
            }
        });
        ((Button) inflate.findViewById(R.id.btn_reverse)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.mEcgCovertDrawListener.reverseEcg();
            }
        });
        ((RadioGroup) inflate.findViewById(R.id.ecg_scale_type)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                HistoryECGDisplayActivity historyECGDisplayActivity;
                TextView textView;
                int i2;
                switch (i) {
                    case R.id.ecg_scale_type_12_5:
                        HistoryECGDisplayActivity.this.mEcgCovertDrawListener.setEcg_scale_type_special(1.0f);
                        textView = HistoryECGDisplayActivity.this.tv_ecg_scale_info;
                        historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_12_5;
                        break;
                    case R.id.ecg_scale_type_25 /*2131165298*/:
                        HistoryECGDisplayActivity.this.mEcgCovertDrawListener.setEcg_scale_type(1.0f);
                        textView = HistoryECGDisplayActivity.this.tv_ecg_scale_info;
                        historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_25;
                        break;
                    case R.id.ecg_scale_type_50 /*2131165299*/:
                        HistoryECGDisplayActivity.this.mEcgCovertDrawListener.setEcg_scale_type(2.0f);
                        textView = HistoryECGDisplayActivity.this.tv_ecg_scale_info;
                        historyECGDisplayActivity = HistoryECGDisplayActivity.this;
                        i2 = R.string.l_ecg_scale_50;
                        break;
                    default:
                        return;
                }
                textView.setText(historyECGDisplayActivity.getString(i2));
            }
        });
        this.tv_ecg_scale_info.setText(getString(R.string.l_ecg_scale_25));
        if (this.ecgMakes != null) {
            ((Button) inflate.findViewById(R.id.btn_previous)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (HistoryECGDisplayActivity.this.ecgMakes != null && HistoryECGDisplayActivity.this.ecgCutMode != 2) {
                        int progress = HistoryECGDisplayActivity.this.sb_His.getProgress();
                        int b = HistoryECGDisplayActivity.this.mEcgParserUtils.getHalfLen(progress);
                        if (b >= 2) {
                            HistoryECGDisplayActivity.this.sb_His.setProgress((int) (((double) HistoryECGDisplayActivity.this.ecgMakes[b - 2]) * 0.25d));
                        }
                        if (b <= 2 && ((double) progress) >= ((double) HistoryECGDisplayActivity.this.ecgMakes[0]) * 0.25d) {
                            Toast.makeText(HistoryECGDisplayActivity.this, HistoryECGDisplayActivity.this.getString(R.string.p_first_exc), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            ((Button) inflate.findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (HistoryECGDisplayActivity.this.ecgMakes != null && HistoryECGDisplayActivity.this.ecgCutMode != 2 && HistoryECGDisplayActivity.this.ecgMakes != null && HistoryECGDisplayActivity.this.ecgCutMode != 2) {
                        int progress = HistoryECGDisplayActivity.this.sb_His.getProgress();
                        int c = HistoryECGDisplayActivity.this.mEcgParserUtils.mo2784c(progress);
                        double d = (double) progress;
                        if (d < ((double) HistoryECGDisplayActivity.this.ecgMakes[HistoryECGDisplayActivity.this.ecgMakes.length - 2]) * 0.25d) {
                            HistoryECGDisplayActivity.this.sb_His.setProgress((int) (((double) HistoryECGDisplayActivity.this.ecgMakes[c + 2]) * 0.25d));
                        }
                        if (c == HistoryECGDisplayActivity.this.ecgMakes.length - 4 && d <= ((double) HistoryECGDisplayActivity.this.ecgMakes[HistoryECGDisplayActivity.this.ecgMakes.length - 1]) * 0.25d) {
                            Toast.makeText(HistoryECGDisplayActivity.this, HistoryECGDisplayActivity.this.getString(R.string.p_last_exc), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } else {
            inflate.findViewById(R.id.btn_previous).setVisibility(View.GONE);
            inflate.findViewById(R.id.btn_next).setVisibility(View.GONE);
        }
        ((Button) inflate2.findViewById(R.id.btn_share_picture)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (HistoryECGDisplayActivity.this.pop_pupop_ecg_share != null) {
                    HistoryECGDisplayActivity.this.pop_pupop_ecg_share.dismiss();
                }
                HistoryECGDisplayActivity.this.showMsgProgressDialog(HistoryECGDisplayActivity.this.getString(R.string.generating_image));
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
                HistoryECGDisplayActivity.this.sendEmail((Context) HistoryECGDisplayActivity.this, HistoryECGDisplayActivity.this.mrecord.getFilePath());
            }
        });
        ((Button) inflate2.findViewById(R.id.btn_upload)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.uploadRecord(HistoryECGDisplayActivity.this.mrecord);
            }
        });
        View inflate3 = LayoutInflater.from(this).inflate(R.layout.pupop_ecg_comment, (ViewGroup) null);
        this.poppupop_ecg_comment = new PopupWindow(inflate3, -2, -2, true);
        this.poppupop_ecg_comment.setOutsideTouchable(false);
        this.poppupop_ecg_comment.setFocusable(true);
        this.edt_ecg_comment_content = (EditText) inflate3.findViewById(R.id.ecg_comment_content);
        try {
            String a = ECGRecordUtils.annotatedECG(new File(this.mrecord.getFilePath()), "text");
            if (a.equals(this.mrecord.getDescription())) {
                this.edt_ecg_comment_content.setText(this.mrecord.getDescription());
            } else {
                this.edt_ecg_comment_content.setText(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((Button) inflate3.findViewById(R.id.ecg_comment_ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProgressDialog unused = HistoryECGDisplayActivity.this.saveProgressDialog = ProgressDialog.show(HistoryECGDisplayActivity.this, (CharSequence) null, HistoryECGDisplayActivity.this.getString(R.string.saving_edit), true, true, (DialogInterface.OnCancelListener) null);
                new Thread(new Runnable() {
                    public void run() {
                        String obj = HistoryECGDisplayActivity.this.edt_ecg_comment_content.getText().toString();
                        if (obj != null) {
                            try {
                                ECGRecordUtils.getDescriptionFile(new File(HistoryECGDisplayActivity.this.mrecord.getFilePath()), "text", obj);
                                HistoryECGDisplayActivity.this.mrecord.setDescription(obj);
                                new SqlManager(HistoryECGDisplayActivity.this.getApplicationContext()).updateEcgRecord(HistoryECGDisplayActivity.this.mrecord);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (HistoryECGDisplayActivity.this.saveProgressDialog != null) {
                            HistoryECGDisplayActivity.this.saveProgressDialog.dismiss();
                        }
                        ProgressDialog unused = HistoryECGDisplayActivity.this.saveProgressDialog = null;
                    }
                }).start();
                HistoryECGDisplayActivity.this.poppupop_ecg_comment.dismiss();
            }
        });
        ((Button) inflate3.findViewById(R.id.ecg_comment_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.edt_ecg_comment_content.setText(HistoryECGDisplayActivity.this.mrecord.getDescription());
                HistoryECGDisplayActivity.this.poppupop_ecg_comment.dismiss();
            }
        });
        View inflate4 = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
        this.pop_pupop_login = new PopupWindow(inflate4, -2, -2, true);
        this.pop_pupop_login.setOutsideTouchable(false);
        this.pop_pupop_login.setFocusable(true);
        this.edt_login_user_name = (EditText) inflate4.findViewById(R.id.login_user_name);
        this.edt_login_user_name.setText(((ECGApplication) getApplication()).mUserInfo.getName());
        this.edt_login_user_pwd = (EditText) inflate4.findViewById(R.id.login_user_pwd);
        this.edt_login_user_pwd.requestFocus();
        ((Button) inflate4.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.historyNetSerBinder.getEcgFilesAsyn(HistoryECGDisplayActivity.this.edt_login_user_name.getText().toString(), HistoryECGDisplayActivity.this.edt_login_user_pwd.getText().toString());
                HistoryECGDisplayActivity.this.showMsgProgressDialog(HistoryECGDisplayActivity.this.getString(R.string.p_login_login));
            }
        });
        ((Button) inflate4.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryECGDisplayActivity.this.pop_pupop_login.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void showMsgProgressDialog(String str) {
        if (this.myMsgProgressDialog == null) {
            this.myMsgProgressDialog = new ProgressDialog(this);
            this.myMsgProgressDialog.setCanceledOnTouchOutside(false);
        }
        this.myMsgProgressDialog.setMessage(str);
        if (!this.myMsgProgressDialog.isShowing()) {
            this.myMsgProgressDialog.show();
        }
    }

    /* renamed from: d */
    private void initEcgMarkData() {
        String mark_time = this.mrecord.getMark_time();
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
                this.ecgMakes = new int[2];
                for (int i4 = 0; i4 < iArr.length; i4 += 2) {
                    if (iArr[i4] == 0) {
                        this.ecgMakes[0] = iArr[i4];
                        this.ecgMakes[1] = iArr[i4 + 1];
                    } else {
                        this.ecgMakes = Arrays.copyOf(this.ecgMakes, this.ecgMakes.length + 2);
                        this.ecgMakes[this.ecgMakes.length - 2] = iArr[i4];
                        this.ecgMakes[this.ecgMakes.length - 1] = iArr[i4 + 1];
                    }
                }
            } else {
                this.ecgMakes = iArr;
            }
            this.mEcgParserUtils.addEcgMakes(this.ecgMakes);
        }
    }

    /* renamed from: e */
    private void showp_file_not_existDialog() {
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
    public void dismissmyMsgProgressDialog() {
        if (this.myMsgProgressDialog != null && this.myMsgProgressDialog.isShowing()) {
            this.myMsgProgressDialog.dismiss();
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
        this.mECGApplication = (ECGApplication) getApplication();
        this.mrecord = (ECGRecord) getIntent().getSerializableExtra("record");
        if (this.mrecord == null && (data = getIntent().getData()) != null) {
            this.mrecord = assertPathUri(data.getPath());
            if (this.mrecord == null) {
                System.exit(0);
            }
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_rev_model));
        this.mSqlManager = new SqlManager(getApplicationContext());
        this.mHeartRateCounter3 = new HeartRateCounter3();
        this.mHeartRateCounter3.init();
        this.mEcgParserUtils = new EcgParserUtils();
        initEcgMarkData();
        this.mECGAnaysis = new ECGAnaysis();
        this.mECGAnaysis.ECGAnaysisInit();
        bindService(new Intent(this, NetService.class), this.historyServiceConnection, 1);
        bindService(new Intent(this, MainService.class), this.historyServiceConnection, 1);
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
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.historyBroadcastReceiver, intentFilter);
        this.obj = new Object();
        initView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Menu menu2 = menu;
        getMenuInflater().inflate(R.menu.ecg_history_display_menu, menu2);
        this.menu_action_ecg_filter_on = menu2.findItem(R.id.action_ecg_filter_on);
        this.menu_ecg_filter_off = menu2.findItem(R.id.action_ecg_filter_off);
        if (this.ecgCutMode == 0) {
            this.menu_action_ecg_filter_on.setVisible(this.isHandOrBody);
            this.menu_ecg_filter_off.setVisible(!this.isHandOrBody);
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
            this.tv_record_a1.setTextColor(getResources().getColor(R.color.green1));
            this.tv_record_a1.setText("A1：-- mV");
            this.tv_record_a2.setTextColor(getResources().getColor(R.color.green1));
            this.tv_record_a2.setText("A2：-- mV");
            this.tv_record_a1a2.setVisibility(View.VISIBLE);
            this.tv_record_a1a2.setTextColor(getResources().getColor(R.color.green1));
            return true;
        } else if (this.ecgCutMode == 1) {
            this.menu_action_ecg_filter_on.setVisible(false);
            this.menu_ecg_filter_off.setVisible(false);
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
            this.tv_record_a1.setTextColor(getResources().getColor(R.color.green1));
            TextView textView = this.tv_record_a1;
            textView.setText(getString(R.string.start) + "：-- ms");
            this.tv_record_a2.setTextColor(getResources().getColor(R.color.green1));
            TextView textView2 = this.tv_record_a2;
            textView2.setText(getString(R.string.end) + "：-- ms");
            this.tv_record_a1a2.setVisibility(View.GONE);
            return true;
        } else {
            if (this.ecgCutMode == 2) {
                this.menu_action_ecg_filter_on.setVisible(false);
                this.menu_ecg_filter_off.setVisible(false);
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
        if (this.mGrideDrawListener != null) {
            this.mGrideDrawListener.recycleDrawBitMap();
        }
        unbindService(this.historyServiceConnection);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.historyBroadcastReceiver);
        super.onDestroy();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x01db, code lost:
        r12.mo2927a(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x01de, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0290, code lost:
        android.widget.Toast.makeText(r12, getString(r0), Toast.LENGTH_LONG).show();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x029b, code lost:
        return true;
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Context applicationContext;
        int i;
        HistoryRecordDrawListener dVar;
        int i2;
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.action_cancel_cut /*2131165199*/:
                this.mEcgCovertDrawListener.actionCancelCut();
                this.mEcgCovertDrawListener.setHistoryEcgs(this.f2343s);
                this.mEcgCovertDrawListener.setRecordProgress(0);
                this.sb_His.setProgress(0);
                this.sb_His.setMax(this.f2343s.length);
                this.isStartCut = false;
                this.isEndCut = false;
                this.f2320R = 0;
                this.f2321S = 0;
                this.ecgCutMode = 1;
                setTitle(getString(R.string.title_cut_model));
                invalidateOptionsMenu();
                return true;
            case R.id.action_cut /*2131165201*/:
                if (this.f2321S == 0) {
                    applicationContext = getApplicationContext();
                    i = R.string.p_ecg_rec_no_end_point;
                    break;
                } else {
                    this.ecgCutMode = 2;
                    setTitle(getString(R.string.title_rev_model));
                    invalidateOptionsMenu();
                    this.f2344t = cutDataStartEnd(this.f2343s, this.f2320R, this.f2321S);
                    this.mEcgCovertDrawListener.setHistoryEcgs(this.f2344t);
                    this.mEcgCovertDrawListener.setRecordProgress(0);
                    this.sb_His.setMax(this.f2344t.length);
                    this.sb_His.setProgress(0);
                    this.mEcgCovertDrawListener.mo2920d();
                    return true;
                }
            case R.id.action_cut_end /*2131165202*/:
                this.isStartCut = false;
                this.isEndCut = true;
                TextView textView = this.tv_record_a2;
                textView.setText(getString(R.string.end) + "：--ms");
                applicationContext = getApplicationContext();
                i = R.string.p_ecg_rec_end_cut_point;
                break;
            case R.id.action_cut_start /*2131165203*/:
                this.isStartCut = true;
                this.isEndCut = false;
                TextView textView2 = this.tv_record_a1;
                textView2.setText(getString(R.string.start) + "：--ms");
                applicationContext = getApplicationContext();
                i = R.string.p_ecg_rec_start_cut_point;
                break;
            case R.id.action_ecg_edit_cmt /*2131165205*/:
                this.poppupop_ecg_comment.showAtLocation(findViewById(R.id.record_heart_rate_sv), 17, 0, 0);
                return true;
            case R.id.action_ecg_filter_off /*2131165206*/:
                this.isHandOrBody = true;
                this.mEcgCovertDrawListener.offEcg_filter(this.isHandOrBody);
                this.sw_filter_switch.setChecked(this.isHandOrBody);
                this.menu_action_ecg_filter_on.setVisible(true);
                this.menu_ecg_filter_off.setVisible(false);
                return true;
            case R.id.action_ecg_filter_on /*2131165207*/:
                this.isHandOrBody = false;
                this.mEcgCovertDrawListener.offEcg_filter(this.isHandOrBody);
                this.sw_filter_switch.setChecked(this.isHandOrBody);
                this.menu_action_ecg_filter_on.setVisible(false);
                this.menu_ecg_filter_off.setVisible(true);
                return true;
            case R.id.action_ecg_reverse /*2131165209*/:
                this.mEcgCovertDrawListener.reverseEcg();
                return true;
            case R.id.action_menu_options /*2131165217*/:
                this.pop_pupop_ecg_tool.showAsDropDown(findViewById(menuItem.getItemId()), -450, 0);
                return true;
            case R.id.action_model_back /*2131165222*/:
                this.mHistoryRecordDrawListener.mo2930a(0, false);
                this.mHistoryRecordDrawListener.mo2930a(1, false);
                this.mHisToryEcgProgressDrawListener[0].mo2904a(false);
                this.mHisToryEcgProgressDrawListener[1].mo2904a(false);
                this.ecgCutMode = 0;
                setTitle(getString(R.string.title_rev_model));
                invalidateOptionsMenu();
                dVar = this.mHistoryRecordDrawListener;
                i2 = -16711936;
                break;
            case R.id.action_model_cut /*2131165223*/:
                this.mHistoryRecordDrawListener.mo2930a(0, false);
                this.mHistoryRecordDrawListener.mo2930a(1, false);
                this.mHisToryEcgProgressDrawListener[0].mo2904a(false);
                this.mHisToryEcgProgressDrawListener[1].mo2904a(false);
                this.ecgCutMode = 1;
                setTitle(getString(R.string.title_cut_model));
                invalidateOptionsMenu();
                this.isStartCut = false;
                this.isEndCut = false;
                this.f2320R = 0;
                this.f2321S = 0;
                dVar = this.mHistoryRecordDrawListener;
                i2 = Color.parseColor("#179E98");
                break;
            case R.id.action_save_cut /*2131165229*/:
                showMsgProgressDialog(getString(R.string.saving_to_file));
                this.mCutRecord = new ECGRecord();
                this.mCutRecord.setUser(this.mrecord.getUser());
                this.mCutRecord.setHeartRate(this.mrecord.getHeartRate());
                this.mCutRecord.setMachine(this.mrecord.getMachine());
                this.mCutRecord.setLeadType(this.mrecord.getLeadType());
                long currentTimeMillis = System.currentTimeMillis();
                this.mCutRecord.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Long.valueOf(currentTimeMillis)));
                int abs = Math.abs((this.f2321S * 4) - (this.f2320R * 4)) / 1000;
                int i3 = abs / 3600;
                int i4 = abs % 3600;
                ECGRecord eCGRecord = this.mCutRecord;
                eCGRecord.setPeriod(String.format("%02d", new Object[]{Integer.valueOf(i3)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(i4 / 60)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(i4 % 60)}));
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("xml:");
                sb.append(this.mrecord.getFilePath());
                LogUtils.logI(str, sb.toString());
                try {
                    ECGEntity a = ECGRecordUtils.m2773a(this.mrecord.getFilePath());
                    a.setStartTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(Long.valueOf(currentTimeMillis)));
                    a.setEndTime(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(Long.valueOf(currentTimeMillis + ((long) Math.abs((this.f2321S * 4) - (this.f2320R * 4))))));
                    a.setMark_time((String) null);
                    a.setMark_period((int[]) null);
                    this.mCutRecord.setEcgEntity(a);
                    this.mCutRecord.setMark_time(a.getMark_time());
                    this.historyMainBinder.saveEcgFile(this.mCutRecord, cutDataStartEnd(this.f2343s, this.f2320R, this.f2321S));
                    this.ecgCutMode = 0;
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
                    this.pop_pupop_ecg_share.showAsDropDown(findViewById(menuItem.getItemId()), -410, 0);
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
        this.pop_pupop_ecg_tool.dismiss();
        this.pop_pupop_ecg_share.dismiss();
        super.onStop();
    }
}
