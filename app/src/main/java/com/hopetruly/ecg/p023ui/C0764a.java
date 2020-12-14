package com.hopetruly.ecg.p023ui;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;

/* renamed from: com.hopetruly.ecg.ui.a */
public class C0764a {
    /* renamed from: a */
    public static void m2746a(View view) {
        if (view != null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(300);
            alphaAnimation.setInterpolator(new LinearInterpolator());
            alphaAnimation.setRepeatCount(-1);
            alphaAnimation.setRepeatMode(2);
            view.startAnimation(alphaAnimation);
        }
    }

    /* renamed from: b */
    public static void m2747b(View view) {
        if (view != null) {
            view.clearAnimation();
        }
    }
}
