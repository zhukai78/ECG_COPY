package com.hopetruly.ecg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hopetruly.ecg.R;

public class DeclareActivity extends Activity {

    /* renamed from: a */
    Runnable declareRunnable = new Runnable() {
        public void run() {
            int scrollY = DeclareActivity.this.mScrollView.getScrollY();
            if (DeclareActivity.this.scrollY != scrollY) {
                int unused = DeclareActivity.this.scrollY = DeclareActivity.this.mScrollView.getScrollY();
                DeclareActivity.this.mhandler.postDelayed(DeclareActivity.this.declareRunnable, 100);
            } else if (DeclareActivity.this.mdeclareOnTouchListener != null && scrollY == (DeclareActivity.this.mScrollView.getChildAt(DeclareActivity.this.mScrollView.getChildCount() - 1).getBottom() + DeclareActivity.this.mScrollView.getPaddingBottom()) - DeclareActivity.this.mScrollView.getHeight()) {
                DeclareActivity.this.cb_chk_agree.setEnabled(true);
            }
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: b */
    public CheckBox cb_chk_agree;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public Button btn_go_on;

    /* renamed from: d */
    private Button btn_exit_btn;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public ScrollView mScrollView;

    /* renamed from: f */
    private TextView tv_Statement_content;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public declareOnTouchListener mdeclareOnTouchListener;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public int scrollY = 0;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public Handler mhandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.DeclareActivity$a */
    private class declareOnTouchListener implements View.OnTouchListener {
        private declareOnTouchListener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 1:
                    int unused = DeclareActivity.this.scrollY = view.getScrollY();
                    DeclareActivity.this.mhandler.postDelayed(DeclareActivity.this.declareRunnable, 100);
                    break;
                case 2:
                    if (view.getScrollY() + view.getHeight() == DeclareActivity.this.mScrollView.getChildAt(0).getMeasuredHeight()) {
                        DeclareActivity.this.cb_chk_agree.setEnabled(true);
                        return false;
                    }
                    break;
                default:
                    return false;
            }
            return false;
        }
    }

    /* renamed from: a */
    private void initView() {
        this.cb_chk_agree = (CheckBox) findViewById(R.id.chk_agree);
        this.btn_go_on = (Button) findViewById(R.id.go_on_btn);
        this.btn_exit_btn = (Button) findViewById(R.id.exit_btn);
        this.tv_Statement_content = (TextView) findViewById(R.id.Statement_content);
        this.mScrollView = (ScrollView) findViewById(R.id.scrollView1);
        this.mdeclareOnTouchListener = new declareOnTouchListener();
        this.mScrollView.setOnTouchListener(this.mdeclareOnTouchListener);
        this.cb_chk_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Button a;
                boolean z2;
                if (z) {
                    a = DeclareActivity.this.btn_go_on;
                    z2 = true;
                } else {
                    a = DeclareActivity.this.btn_go_on;
                    z2 = false;
                }
                a.setEnabled(z2);
            }
        });
        this.btn_go_on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeclareActivity.this.setResult(-1, new Intent());
                DeclareActivity.this.finish();
            }
        });
        this.btn_exit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeclareActivity.this.setResult(0, new Intent());
                DeclareActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_declare);
        initView();
        this.btn_go_on.setEnabled(false);
        this.cb_chk_agree.setEnabled(false);
        if (this.tv_Statement_content.getText().equals("")) {
            this.cb_chk_agree.setEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.declare, menu);
        return true;
    }
}
