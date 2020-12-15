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
import com.hopetruly.ecg.util.LogUtils;
import com.hopetruly.part.net.MyHttpHelper;

public class ErrorActivity extends Activity {

    /* renamed from: a */
    String TAG = "ErrorActivity";

    /* renamed from: b */
    TextView tv_err_info;

    /* renamed from: c */
    EditText edt_err_desc;

    /* renamed from: d */
    Button btn_err_ok;

    /* renamed from: e */
    ErrorInfo errorInfo;

    /* renamed from: b */
    private void initView() {
        TextView textView;
        String string;
        this.tv_err_info = (TextView) findViewById(R.id.err_info);
        if (this.errorInfo != null) {
            textView = this.tv_err_info;
            string = this.errorInfo.getErrorInfo();
        } else {
            textView = this.tv_err_info;
            string = getString(R.string.unknow_error);
        }
        textView.setText(string);
        this.edt_err_desc = (EditText) findViewById(R.id.err_desc);
        this.btn_err_ok = (Button) findViewById(R.id.err_ok_btn);
        this.btn_err_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ErrorActivity.this.sendErrorInfo();
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void sendErrorInfo() {
        String obj = this.edt_err_desc.getText().toString();
        if (this.errorInfo == null) {
            this.errorInfo = new ErrorInfo();
        }
        if (!TextUtils.isEmpty(obj)) {
            this.errorInfo.setOpDesc(obj);
        }
        new Thread(new Runnable() {
            public void run() {
                MyHttpHelper.get_error_report(ErrorActivity.this.errorInfo);
            }
        }).start();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtils.logI(this.TAG, "onCreate~~~");
        this.errorInfo = (ErrorInfo) getIntent().getSerializableExtra("error");
        setContentView(R.layout.activity_error);
        initView();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LogUtils.logI(this.TAG, "onDestroy~~~");
        super.onDestroy();
    }
}
