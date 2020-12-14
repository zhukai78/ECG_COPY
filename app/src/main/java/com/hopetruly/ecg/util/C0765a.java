package com.hopetruly.ecg.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.hopetruly.ecg.R;

/* renamed from: com.hopetruly.ecg.util.a */
public class C0765a {

    /* renamed from: e */
    private static int f2896e = 2131427328;

    /* renamed from: a */
    private SoundPool f2897a = new SoundPool(1, 3, 0);

    /* renamed from: b */
    private int f2898b;

    /* renamed from: c */
    private int f2899c;

    /* renamed from: d */
    private Context f2900d;

    /* renamed from: f */
    private boolean f2901f;

    public C0765a(Context context) {
        this.f2900d = context;
        this.f2899c = this.f2897a.load(this.f2900d, R.raw.alarm_sound_dede, 1);
        this.f2901f = false;
    }

    /* renamed from: a */
    public void mo2763a() {
        this.f2897a.stop(this.f2898b);
    }

    /* renamed from: a */
    public void mo2764a(int i) {
        AudioManager audioManager = (AudioManager) this.f2900d.getSystemService("audio");
        float streamVolume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
        this.f2898b = this.f2897a.play(this.f2899c, streamVolume, streamVolume, 1, i, 1.0f);
        if (-1 == i) {
            this.f2901f = true;
        }
    }

    /* renamed from: b */
    public void mo2765b() {
        mo2763a();
        this.f2897a.release();
    }

    /* renamed from: c */
    public void mo2766c() {
        this.f2897a.pause(this.f2898b);
    }

    /* renamed from: d */
    public void mo2767d() {
        this.f2897a.resume(this.f2898b);
    }

    /* renamed from: e */
    public boolean mo2768e() {
        return this.f2901f;
    }
}
