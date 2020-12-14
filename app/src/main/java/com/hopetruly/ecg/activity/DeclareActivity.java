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
    Runnable f2129a = new Runnable() {
        public void run() {
            int scrollY = DeclareActivity.this.f2133e.getScrollY();
            if (DeclareActivity.this.f2136h != scrollY) {
                int unused = DeclareActivity.this.f2136h = DeclareActivity.this.f2133e.getScrollY();
                DeclareActivity.this.f2137i.postDelayed(DeclareActivity.this.f2129a, 100);
            } else if (DeclareActivity.this.f2135g != null && scrollY == (DeclareActivity.this.f2133e.getChildAt(DeclareActivity.this.f2133e.getChildCount() - 1).getBottom() + DeclareActivity.this.f2133e.getPaddingBottom()) - DeclareActivity.this.f2133e.getHeight()) {
                DeclareActivity.this.f2130b.setEnabled(true);
            }
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: b */
    public CheckBox f2130b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public Button f2131c;

    /* renamed from: d */
    private Button f2132d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public ScrollView f2133e;

    /* renamed from: f */
    private TextView f2134f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public C0578a f2135g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public int f2136h = 0;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public Handler f2137i = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    };

    /* renamed from: com.hopetruly.ecg.activity.DeclareActivity$a */
    private class C0578a implements View.OnTouchListener {
        private C0578a() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 1:
                    int unused = DeclareActivity.this.f2136h = view.getScrollY();
                    DeclareActivity.this.f2137i.postDelayed(DeclareActivity.this.f2129a, 100);
                    break;
                case 2:
                    if (view.getScrollY() + view.getHeight() == DeclareActivity.this.f2133e.getChildAt(0).getMeasuredHeight()) {
                        DeclareActivity.this.f2130b.setEnabled(true);
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
    private void m2246a() {
        this.f2130b = (CheckBox) findViewById(R.id.chk_agree);
        this.f2131c = (Button) findViewById(R.id.go_on_btn);
        this.f2132d = (Button) findViewById(R.id.exit_btn);
        this.f2134f = (TextView) findViewById(R.id.Statement_content);
        this.f2133e = (ScrollView) findViewById(R.id.scrollView1);
        this.f2135g = new C0578a();
        this.f2133e.setOnTouchListener(this.f2135g);
        this.f2130b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Button a;
                boolean z2;
                if (z) {
                    a = DeclareActivity.this.f2131c;
                    z2 = true;
                } else {
                    a = DeclareActivity.this.f2131c;
                    z2 = false;
                }
                a.setEnabled(z2);
            }
        });
        this.f2131c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeclareActivity.this.setResult(-1, new Intent());
                DeclareActivity.this.finish();
            }
        });
        this.f2132d.setOnClickListener(new View.OnClickListener() {
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
        m2246a();
        this.f2131c.setEnabled(false);
        this.f2130b.setEnabled(false);
        if (this.f2134f.getText().equals("")) {
            this.f2130b.setEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.declare, menu);
        return true;
    }
}
