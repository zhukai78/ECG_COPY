package com.hopetruly.ecg.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareBaseOnWidthLayout extends RelativeLayout {
    public SquareBaseOnWidthLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SquareBaseOnWidthLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SquareBaseOnWidthLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getDefaultSize(0, i), 1073741824);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }
}
