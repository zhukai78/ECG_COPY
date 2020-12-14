package com.hopetruly.ecg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.R;

import java.io.File;

public class SendActivity extends BaseActivity {

    /* renamed from: a */
    Button f2576a;

    /* renamed from: c */
    EditText f2577c;

    /* renamed from: d */
    EditText f2578d;

    /* renamed from: e */
    EditText f2579e;

    /* renamed from: f */
    EditText f2580f;

    /* renamed from: g */
    private BroadcastReceiver f2581g = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.hopetruly.ecg.FileExplore.PICK_FILE_RESUME")) {
                SendActivity.this.f2580f.setText(intent.getStringExtra("path"));
            }
        }
    };

    /* renamed from: h */
    private View.OnClickListener f2582h = new View.OnClickListener() {
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.eamil_send_btn) {
                String obj = SendActivity.this.f2577c.getText().toString();
                String obj2 = SendActivity.this.f2579e.getText().toString();
                String obj3 = SendActivity.this.f2578d.getText().toString();
                String obj4 = SendActivity.this.f2580f.getText().toString();
                Log.e("filepath", obj4);
                if (obj != null && obj.length() > 0) {
                    SendActivity.m2536b(SendActivity.this, new String[]{obj}, obj3, obj2, obj4);
                }
            } else if (id == R.id.email_extra) {
                SendActivity.this.startActivity(new Intent(SendActivity.this, FileExploreActivity.class));
            }
        }
    };

    /* renamed from: a */
    private void m2533a() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2581g, new IntentFilter("com.hopetruly.ecg.FileExplore.PICK_FILE_RESUME"));
    }

    /* renamed from: b */
    private void m2535b() {
        this.f2577c = (EditText) findViewById(R.id.email_to);
        this.f2578d = (EditText) findViewById(R.id.email_title);
        this.f2579e = (EditText) findViewById(R.id.email_content);
        this.f2580f = (EditText) findViewById(R.id.email_extra);
        this.f2580f.setOnClickListener(this.f2582h);
        this.f2576a = (Button) findViewById(R.id.eamil_send_btn);
        this.f2576a.setOnClickListener(this.f2582h);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static void m2536b(Context context, String[] strArr, String str, String str2, String str3) {
        Intent intent = new Intent("android.intent.action.SEND");
        if (strArr != null) {
            intent.putExtra("android.intent.extra.EMAIL", strArr);
        }
        if (str != null) {
            intent.putExtra("android.intent.extra.SUBJECT", str);
        }
        if (str2 != null) {
            intent.putExtra("android.intent.extra.TEXT", str2);
        }
        if (str3 != null) {
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(str3)));
            intent.setType("image/png");
        }
        context.startActivity(Intent.createChooser(intent, "请选择发送软件"));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_send);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        m2533a();
        m2535b();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2581g);
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
