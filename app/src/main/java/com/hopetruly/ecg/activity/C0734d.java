package com.hopetruly.ecg.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;

/* renamed from: com.hopetruly.ecg.activity.d */
public class C0734d {
    /* renamed from: a */
    public static void m2582a(View view, float f) {
        RadioButton radioButton = (RadioButton) view;
        Drawable[] compoundDrawables = radioButton.getCompoundDrawables();
        compoundDrawables[1].setBounds(0, 0, (int) (((float) compoundDrawables[1].getMinimumWidth()) * f), (int) (((float) compoundDrawables[1].getMinimumHeight()) * f));
        radioButton.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }
}
