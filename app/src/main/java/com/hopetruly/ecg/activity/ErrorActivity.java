package com.hopetruly.ecg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.ErrorInfo;
import com.hopetruly.ecg.util.C0771g;
import com.hopetruly.part.net.C0791b;

public class ErrorActivity extends Activity {

    /* renamed from: a */
    String f2204a = "ErrorActivity";

    /* renamed from: b */
    TextView f2205b;

    /* renamed from: c */
    EditText f2206c;

    /* renamed from: d */
    Button f2207d;

    /* renamed from: e */
    ErrorInfo f2208e;

    /* renamed from: b */
    private void m2283b() {
        TextView textView;
        String string;
        this.f2205b = (TextView) findViewById(R.id.err_info);
        if (this.f2208e != null) {
            textView = this.f2205b;
            string = this.f2208e.getErrorInfo();
        } else {
            textView = this.f2205b;
            string = getString(R.string.unknow_error);
        }
        textView.setText(string);
        this.f2206c = (EditText) findViewById(R.id.err_desc);
        this.f2207d = (Button) findViewById(R.id.err_ok_btn);
        this.f2207d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ErrorActivity.this.mo2173a();
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo2173a() {
        String obj = this.f2206c.getText().toString();
        if (this.f2208e == null) {
            this.f2208e = new ErrorInfo();
        }
        if (!TextUtils.isEmpty(obj)) {
            this.f2208e.setOpDesc(obj);
        }
        new Thread(new Runnable() {
            public void run() {
                C0791b.m2878a(ErrorActivity.this.f2208e);
            }
        }).start();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        C0771g.m2784a(this.f2204a, "onCreate~~~");
        this.f2208e = (ErrorInfo) getIntent().getSerializableExtra("error");
        setContentView(R.layout.activity_error);
        m2283b();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        C0771g.m2784a(this.f2204a, "onDestroy~~~");
        super.onDestroy();
    }
}
