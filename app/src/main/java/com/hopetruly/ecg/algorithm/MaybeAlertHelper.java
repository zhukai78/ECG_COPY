package com.hopetruly.ecg.algorithm;

import android.content.Context;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.util.AlertUtils;
import com.hopetruly.ecg.util.VibratorUtils;

import java.sql.Date;

/* renamed from: com.hopetruly.ecg.algorithm.b */
public class MaybeAlertHelper {

    /* renamed from: c */
    private static int INT_10 = 10;

    /* renamed from: d */
    private static int INT_60 = 60;

    /* renamed from: a */
    ECGApplication mECGApplication;

    /* renamed from: b */
    AlertUtils mAlertUtils;

    /* renamed from: e */
    private int alertCnt = INT_60;

    /* renamed from: f */
    private Context mContext;

    /* renamed from: g */
    private int hrIndex = 0;

    /* renamed from: h */
    private int[] hrBuff = {70, 70, 70, 70, 70, 70, 70, 70, 70, 70};

    /* renamed from: i */
    private long firstTime = 0;

    /* renamed from: j */
    private long nextAlertTime = 0;

    /* renamed from: k */
    private boolean isVibrator;

    /* renamed from: l */
    private boolean ispauseVibrator;

    public MaybeAlertHelper(Context context) {
        this.mContext = context;
        this.mECGApplication = (ECGApplication) this.mContext.getApplicationContext();
        this.mAlertUtils = new AlertUtils(this.mContext);
        this.isVibrator = false;
        this.ispauseVibrator = false;
    }

    /* renamed from: k */
    private void startVibrator() {
        if (!this.isVibrator) {
            switch (this.mECGApplication.appECGConf.getECG_ALARM_TYPE()) {
                case 0:
                    VibratorUtils.goVibrator(this.mContext, 1, 100);
                    break;
                case 1:
                    break;
                case 2:
                    VibratorUtils.goVibrator(this.mContext, 1, 100);
                    break;
            }
            if (this.mAlertUtils.getmisPlay()) {
                this.mAlertUtils.resumePlay();
            } else {
                this.mAlertUtils.initAlert(-1);
            }
            this.isVibrator = true;
        }
    }

    /* renamed from: a */
    public void pauseVibrator() {
        if (this.isVibrator) {
            switch (this.mECGApplication.appECGConf.getECG_ALARM_TYPE()) {
                case 0:
                    VibratorUtils.canvelVibrator();
                    break;
                case 1:
                    break;
                case 2:
                    VibratorUtils.canvelVibrator();
                    break;
            }
            this.mAlertUtils.pausePlay();
            this.isVibrator = false;
        }
    }

    /* renamed from: a */
    public void checkAlert(int i) {
        if (i == 0) {
            pauseVibrator();
            return;
        }
        this.hrBuff[getHrInddex()] = i;
        if (alertCheck()) {
            Date date = new Date(System.currentTimeMillis());
            if (this.firstTime == 0) {
                this.firstTime = date.getTime();
            } else {
                this.nextAlertTime = date.getTime();
            }
            if (this.firstTime != 0 && this.nextAlertTime != 0 && this.nextAlertTime - this.firstTime >= ((long) (this.mECGApplication.appECGConf.getECG_ALARM_DELAY() * 1000))) {
                this.firstTime = 0;
                this.nextAlertTime = 0;
                startVibrator();
                return;
            }
            return;
        }
        this.firstTime = 0;
        this.nextAlertTime = 0;
        pauseVibrator();
    }

    /* renamed from: b */
    public boolean getECG_ALARM_ENABLE() {
        return this.mECGApplication.appECGConf.getECG_ALARM_ENABLE();
    }

    /* renamed from: c */
    public void stopAlarm() {
        pauseVibrator();
        this.ispauseVibrator = true;
        this.firstTime = 0;
        this.nextAlertTime = 0;
    }

    /* renamed from: d */
    public void resetVibrator() {
        this.ispauseVibrator = false;
    }

    /* renamed from: e */
    public void reduceAlertCnt() {
        if (this.ispauseVibrator) {
            this.alertCnt--;
            if (this.alertCnt == 0) {
                this.ispauseVibrator = false;
                this.alertCnt = INT_60;
            }
        }
    }

    /* renamed from: f */
    public boolean getIspauseVibrator() {
        return this.ispauseVibrator;
    }

    /* renamed from: g */
    public boolean getisVibrator() {
        return this.isVibrator;
    }

    /* renamed from: h */
    public boolean alertCheck() {
        if (!this.mECGApplication.appECGConf.getECG_ALARM_ENABLE() || this.ispauseVibrator) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < INT_10; i3++) {
            if (this.hrBuff[i3] > this.mECGApplication.appECGConf.getECG_ALARM_RATE_MAX()) {
                i++;
            }
            if (this.hrBuff[i3] < this.mECGApplication.appECGConf.getECG_ALARM_RATE_MIN()) {
                i2++;
            }
        }
        return ((double) i) > ((double) INT_10) * 0.7d || ((double) i2) > ((double) INT_10) * 0.7d;
    }

    /* renamed from: i */
    public int getHrInddex() {
        if (this.hrIndex >= INT_10) {
            this.hrIndex = 0;
            return 0;
        }
        int i = this.hrIndex;
        this.hrIndex = i + 1;
        return i;
    }

    /* renamed from: j */
    public void stopeVibrator() {
        pauseVibrator();
        this.mAlertUtils.clearAndRelease();
    }
}
