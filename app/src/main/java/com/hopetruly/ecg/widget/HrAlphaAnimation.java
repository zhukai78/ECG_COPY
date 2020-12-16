package com.hopetruly.ecg.widget;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;

public class HrAlphaAnimation {
    /* renamed from: a */
    public static void startAnimation(View view) {
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
    public static void clearAnimation(View view) {
        if (view != null) {
            view.clearAnimation();
        }
    }
}
