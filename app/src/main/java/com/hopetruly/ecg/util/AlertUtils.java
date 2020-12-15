package com.hopetruly.ecg.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.hopetruly.ecg.R;

/* renamed from: com.hopetruly.ecg.util.a */
public class AlertUtils {

    /* renamed from: e */
    private static int f2896e = 2131427328;

    /* renamed from: a */
    private SoundPool mSoundPool = new SoundPool(1, 3, 0);

    /* renamed from: b */
    private int autoPlay;

    /* renamed from: c */
    private int alarm_sound;

    /* renamed from: d */
    private Context mctx;

    /* renamed from: f */
    private boolean isPlay;

    public AlertUtils(Context context) {
        this.mctx = context;
        this.alarm_sound = this.mSoundPool.load(this.mctx, R.raw.alarm_sound_dede, 1);
        this.isPlay = false;
    }

    /* renamed from: a */
    public void stopPlay() {
        this.mSoundPool.stop(this.autoPlay);
    }

    /* renamed from: a */
    public void initAlert(int i) {
        AudioManager audioManager = (AudioManager) this.mctx.getSystemService("audio");
        float streamVolume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
        this.autoPlay = this.mSoundPool.play(this.alarm_sound, streamVolume, streamVolume, 1, i, 1.0f);
        if (-1 == i) {
            this.isPlay = true;
        }
    }

    /* renamed from: b */
    public void clearAndRelease() {
        stopPlay();
        this.mSoundPool.release();
    }

    /* renamed from: c */
    public void pausePlay() {
        this.mSoundPool.pause(this.autoPlay);
    }

    /* renamed from: d */
    public void resumePlay() {
        this.mSoundPool.resume(this.autoPlay);
    }

    /* renamed from: e */
    public boolean getmisPlay() {
        return this.isPlay;
    }
}
