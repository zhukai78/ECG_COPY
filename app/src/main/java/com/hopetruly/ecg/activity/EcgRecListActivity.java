package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.ECGEntity;
import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.p022b.SqlManager;
import com.hopetruly.ecg.services.FileService;
import com.hopetruly.ecg.util.C0770f;
import com.hopetruly.part.net.NetService;

import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class EcgRecListActivity extends BaseActivity implements AdapterView.OnItemLongClickListener {
    /* access modifiers changed from: private */

    /* renamed from: j */
    public static final String f2144j = "EcgRecListActivity";

    /* renamed from: a */
    ListView f2145a;

    /* renamed from: c */
    List<ECGRecord> f2146c;

    /* renamed from: d */
    C0589b f2147d;

    /* renamed from: e */
    SqlManager f2148e;

    /* renamed from: f */
    ECGApplication f2149f;

    /* renamed from: g */
    NetService f2150g;

    /* renamed from: h */
    FileService f2151h;

    /* renamed from: i */
    C0588a f2152i;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public PopupWindow f2153k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public EditText f2154l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public EditText f2155m;

    /* renamed from: n */
    private ServiceConnection f2156n = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            EcgRecListActivity.this.f2150g = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            EcgRecListActivity.this.f2150g = null;
        }
    };

    /* renamed from: o */
    private ServiceConnection f2157o = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            EcgRecListActivity.this.f2151h = ((FileService.FileServiceBinder) iBinder).getFileService();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            EcgRecListActivity.this.f2151h = null;
        }
    };

    /* renamed from: p */
    private BroadcastReceiver f2158p = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Context applicationContext;
            EcgRecListActivity ecgRecListActivity;
            int i;
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.FileService.FILE_IMPORT_START")) {
                EcgRecListActivity.this.m2256a(EcgRecListActivity.this.getString(R.string.p_importing_file));
            } else if (action.equals("com.holptruly.ecg.services.FileService.FILE_IMPORT_SUCCESS")) {
                ECGRecord a = C0770f.m2774a((Context) EcgRecListActivity.this, intent.getStringExtra("com.holptruly.ecg.services.FileService.EXTRA_FILE"));
                if (a != null) {
                    EcgRecListActivity.this.f2148e.mo2469a(a);
                    EcgRecListActivity.this.m2262d();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.p_import_success;
                } else {
                    EcgRecListActivity.this.m2262d();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.p_import_error;
                }
                Toast.makeText(applicationContext, ecgRecListActivity.getString(i), Toast.LENGTH_SHORT).show();
                EcgRecListActivity.this.m2261c();
            } else if (action.equals("com.holptruly.ecg.services.FileService.FILE_IMPORT_FAIL")) {
                EcgRecListActivity.this.m2262d();
                Toast.makeText(EcgRecListActivity.this.getApplicationContext(), EcgRecListActivity.this.getString(R.string.p_import_failed), Toast.LENGTH_SHORT).show();
            }
        }
    };

    /* renamed from: q */
    private BroadcastReceiver f2159q = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Context applicationContext;
            EcgRecListActivity ecgRecListActivity;
            int i;
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.NetService.SYNC_DATA_BEGIN_ACTION")) {
                EcgRecListActivity.this.m2256a(EcgRecListActivity.this.getString(R.string.downloading));
            } else if (action.equals("com.holptruly.ecg.services.NetService.SYNC_DATA_SUCCESS_ACTION")) {
                List list = (List) intent.getSerializableExtra("records");
                if (list.size() > 0) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        ECGRecord eCGRecord = (ECGRecord) list.get(i2);
                        try {
                            ECGEntity a = C0770f.m2773a(eCGRecord.getFilePath());
                            eCGRecord.setMark_time(a.getMark_time());
                            if (a.getLeadExten().equals(ECGEntity.LEAD_PART_HAND)) {
                                eCGRecord.setLeadType(0);
                            } else if (a.getLeadExten().equals(ECGEntity.LEAD_PART_CHEST)) {
                                eCGRecord.setLeadType(1);
                            }
                            EcgRecListActivity.this.f2148e.mo2469a(eCGRecord);
                            EcgRecListActivity.this.f2148e.mo2468a();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                EcgRecListActivity.this.m2262d();
                EcgRecListActivity.this.m2261c();
            } else {
                if (action.equals("com.holptruly.ecg.services.NetService.SYNC_DATA_FAIL_ACTION")) {
                    EcgRecListActivity.this.m2262d();
                    EcgRecListActivity.this.m2261c();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.l_no_data;
                } else if (action.equals("com.holptruly.ecg.services.NetService.NEED_LOGIN")) {
                    Log.d(EcgRecListActivity.f2144j, "Receive need log message");
                    EcgRecListActivity.this.m2262d();
                    EcgRecListActivity.this.f2153k.showAtLocation(EcgRecListActivity.this.findViewById(R.id.ecg_rec_list), 17, 0, 0);
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL")) {
                    EcgRecListActivity.this.m2262d();
                    EcgRecListActivity.this.f2153k.dismiss();
                    EcgRecListActivity.this.f2150g.mo2821a(EcgRecListActivity.this.f2149f.mUserInfo.getId());
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_FAILE")) {
                    EcgRecListActivity.this.m2262d();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.p_login_fail;
                } else {
                    return;
                }
                Toast.makeText(applicationContext, ecgRecListActivity.getString(i), Toast.LENGTH_SHORT).show();
            }
        }
    };

    /* renamed from: r */
    private AdapterView.OnItemClickListener f2160r = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            Intent intent = new Intent(EcgRecListActivity.this, HistoryECGDisplayActivity.class);
            intent.putExtra("record", EcgRecListActivity.this.f2146c.get(i));
            EcgRecListActivity.this.startActivity(intent);
        }
    };

    /* renamed from: s */
    private ProgressDialog f2161s;

    /* renamed from: com.hopetruly.ecg.activity.EcgRecListActivity$a */
    class C0588a extends AsyncTask<Void, Void, List<ECGRecord>> {
        C0588a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public List<ECGRecord> doInBackground(Void... voidArr) {
            EcgRecListActivity.this.f2146c = EcgRecListActivity.this.f2148e.mo2467a(EcgRecListActivity.this.f2149f.mUserInfo.getId());
            for (ECGRecord user : EcgRecListActivity.this.f2146c) {
                user.setUser(EcgRecListActivity.this.f2149f.mUserInfo);
            }
            return EcgRecListActivity.this.f2146c;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(List<ECGRecord> list) {
            if (!isCancelled()) {
                if (list.size() > 0) {
                    EcgRecListActivity.this.f2147d.notifyDataSetChanged();
                }
                super.onPostExecute(list);
            }
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.EcgRecListActivity$b */
    private class C0589b extends BaseAdapter {

        /* renamed from: a */
        final int f2173a;

        /* renamed from: b */
        final int f2174b;

        /* renamed from: c */
        final int f2175c;

        /* renamed from: com.hopetruly.ecg.activity.EcgRecListActivity$b$a */
        private class C0590a {

            /* renamed from: a */
            TextView f2177a;

            /* renamed from: b */
            TextView f2178b;

            /* renamed from: c */
            TextView f2179c;

            /* renamed from: d */
            TextView f2180d;

            /* renamed from: e */
            TextView f2181e;

            /* renamed from: f */
            TextView f2182f;

            private C0590a() {
            }
        }

        private C0589b() {
            this.f2173a = 2;
            this.f2174b = 0;
            this.f2175c = 1;
        }

        /* renamed from: a */
        private String m2267a(String str, int i) {
            if (str == null || str.length() == 0 || i < 0) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer(str.replaceAll("\n", ""));
            if (stringBuffer.length() > i) {
                stringBuffer.replace(i, stringBuffer.length(), "");
                stringBuffer.append("...");
            }
            return stringBuffer.toString();
        }

        public int getCount() {
            return EcgRecListActivity.this.f2146c.size();
        }

        public Object getItem(int i) {
            if (EcgRecListActivity.this.f2146c != null) {
                return EcgRecListActivity.this.f2146c.get(i);
            }
            return null;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            String mark_time = EcgRecListActivity.this.f2146c.get(i).getMark_time();
            return (mark_time == null || mark_time.equals("none")) ? 0 : 1;
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x00d9  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x00e8  */
        public View getView(int i, View view, ViewGroup viewGroup) {
            C0590a aVar;
            View view2;
            TextView textView;
            int i2 = 0;
            int i3;
            EcgRecListActivity ecgRecListActivity;
            TextView textView2;
            ECGRecord eCGRecord = EcgRecListActivity.this.f2146c.get(i);
            int itemViewType = getItemViewType(i);
            if (view == null) {
                aVar = new C0590a();
                view2 = LayoutInflater.from(EcgRecListActivity.this.getApplicationContext()).inflate(R.layout.ecg_rec_list_item, (ViewGroup) null);
                aVar.f2181e = (TextView) view2.findViewById(R.id.ecg_rec_name);
                aVar.f2177a = (TextView) view2.findViewById(R.id.ecg_rec_date);
                aVar.f2178b = (TextView) view2.findViewById(R.id.ecg_rec_period);
                aVar.f2180d = (TextView) view2.findViewById(R.id.ecg_rec_avg_hr);
                aVar.f2182f = (TextView) view2.findViewById(R.id.ecg_lead_type);
                aVar.f2179c = (TextView) view2.findViewById(R.id.ecg_rec_comment);
                view2.setTag(aVar);
            } else {
                view2 = view;
                aVar = (C0590a) view.getTag();
            }
            try {
                aVar.f2181e.setText(URLDecoder.decode(eCGRecord.getFileName(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            aVar.f2177a.setText(eCGRecord.getTime());
            aVar.f2178b.setText(eCGRecord.getPeriod());
            aVar.f2180d.setText(String.valueOf(eCGRecord.getHeartRate()));
            switch (eCGRecord.getLeadType()) {
                case 0:
                    textView2 = aVar.f2182f;
                    ecgRecListActivity = EcgRecListActivity.this;
                    i3 = R.string.l_with_hand;
                    break;
                case 1:
                    textView2 = aVar.f2182f;
                    ecgRecListActivity = EcgRecListActivity.this;
                    i3 = R.string.l_with_chest;
                    break;
                default:
                    aVar.f2179c.setText(m2267a(eCGRecord.getDescription(), 25));
                    if (itemViewType != 1) {
                        textView = aVar.f2181e;
                        i2 = Color.rgb(255, 165, 0);
                    } else if (itemViewType != 0) {
                        return view2;
                    } else {
                        textView = aVar.f2181e;
                        i2 = -16777216;
                    }
                    textView.setTextColor(i2);
                    return view2;
            }
            textView2.setText(ecgRecListActivity.getString(i3));
            aVar.f2179c.setText(m2267a(eCGRecord.getDescription(), 25));
            if (itemViewType != 1) {
            }
            aVar.f2181e.setTextColor(i2);
            return view2;
        }

        public int getViewTypeCount() {
            return 2;
        }
    }

    /* renamed from: a */
    private void m2254a(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.delete));
        builder.setMessage(getResources().getString(R.string.delete_rec));
        builder.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String filePath = EcgRecListActivity.this.f2146c.get(i).getFilePath();
                if (filePath == null || filePath.length() <= 0) {
                    EcgRecListActivity.this.f2148e.mo2475c(String.valueOf(EcgRecListActivity.this.f2146c.get(i).getId()));
                    EcgRecListActivity.this.f2146c.remove(i);
                } else {
                    try {
                        File file = new File(filePath);
                        if (!file.exists() || !file.isFile()) {
                            String a = EcgRecListActivity.f2144j;
                            Log.d(a, "File:[" + filePath + "] is not exists file");
                        } else {
                            file.delete();
                            EcgRecListActivity.this.f2148e.mo2475c(String.valueOf(EcgRecListActivity.this.f2146c.get(i).getId()));
                            EcgRecListActivity.this.f2146c.remove(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                EcgRecListActivity.this.f2147d.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2256a(String str) {
        if (this.f2161s == null) {
            this.f2161s = new ProgressDialog(this);
            this.f2161s.setCanceledOnTouchOutside(false);
        }
        this.f2161s.setMessage(str);
        if (!this.f2161s.isShowing()) {
            this.f2161s.show();
        }
    }

    /* renamed from: a */
    private boolean m2257a(File file) {
        try {
            C0770f.m2775a(file, "AnnotatedECG");
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e2) {
            e2.printStackTrace();
            return false;
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        } catch (NullPointerException e4) {
            e4.printStackTrace();
            return false;
        }
    }

    /* renamed from: b */
    private void m2259b() {
        if (this.f2153k == null) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
            this.f2153k = new PopupWindow(inflate, -2, -2, true);
            this.f2153k.setOutsideTouchable(false);
            this.f2153k.setFocusable(true);
            this.f2154l = (EditText) inflate.findViewById(R.id.login_user_name);
            this.f2154l.setText(((ECGApplication) getApplication()).mUserInfo.getName());
            this.f2155m = (EditText) inflate.findViewById(R.id.login_user_pwd);
            this.f2155m.requestFocus();
            ((Button) inflate.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        EcgRecListActivity.this.f2150g.mo2829c(EcgRecListActivity.this.f2154l.getText().toString(), EcgRecListActivity.this.f2155m.getText().toString());
                        EcgRecListActivity.this.m2256a(EcgRecListActivity.this.getString(R.string.p_login_login));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ((Button) inflate.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    EcgRecListActivity.this.f2153k.dismiss();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m2261c() {
        this.f2146c.clear();
        this.f2152i = new C0588a();
        this.f2152i.execute(new Void[0]);
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m2262d() {
        if (this.f2161s != null && this.f2161s.isShowing()) {
            this.f2161s.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        String string;
        if (i == 3001 && i2 == -1) {
            String stringExtra = intent.getStringExtra("file_path");
            File file = new File(stringExtra);
            if (file.isDirectory()) {
                string = getResources().getString(R.string.p_file_not_exist);
            } else if (!file.isFile() || !m2257a(file)) {
                string = getResources().getString(R.string.p_not_valid_ecg_rec_file);
            } else {
                String[] split = stringExtra.split("/");
                this.f2151h.mo2702a(stringExtra, split[split.length - 1], this.f2149f.mUserInfo.getName());
                return;
            }
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        setContentView(R.layout.activity_ecg_rec_list);
        super.onCreate(bundle);
        bindService(new Intent(this, FileService.class), this.f2157o, Context.BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_START");
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_SUCCESS");
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_FAIL");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2158p, intentFilter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onDestroy() {
        if (this.f2151h != null) {
            Log.d(f2144j, "fileService unbindService");
            unbindService(this.f2157o);
            this.f2151h = null;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2158p);
        if (this.f2152i != null && this.f2152i.getStatus() == AsyncTask.Status.RUNNING) {
            this.f2152i.cancel(true);
            this.f2152i = null;
        }
        super.onDestroy();
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        m2254a(i);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 16908332) {
            switch (itemId) {
                case R.id.history_action_import /*2131165327*/:
                    startActivityForResult(new Intent(this, FileExploreActivity.class), 3001);
                    return true;
                case R.id.history_action_sync /*2131165328*/:
                    this.f2150g.mo2821a(this.f2149f.mUserInfo.getId());
                    return true;
                default:
                    return super.onOptionsItemSelected(menuItem);
            }
        } else {
            onBackPressed();
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.d(f2144j, "onPause");
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2159q);
        if (this.f2150g != null) {
            Log.d(f2144j, "netService unbindService");
            unbindService(this.f2156n);
            this.f2150g = null;
        }
        super.onPause();
    }

    public void onResume() {
        m2261c();
        super.onResume();
    }

    public void onStart() {
        Log.d(f2144j, "OnStart");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.f2149f = (ECGApplication) getApplication();
        bindService(new Intent(this, NetService.class), this.f2156n, Context.BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.holptruly.ecg.services.NetService.SYNC_DATA_BEGIN_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.SYNC_DATA_SUCCESS_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.SYNC_DATA_FAIL_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.NEED_LOGIN");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_FAILE");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2159q, intentFilter);
        this.f2145a = (ListView) findViewById(R.id.ecg_rec_list);
        this.f2147d = new C0589b();
        this.f2146c = new ArrayList();
        this.f2145a.setAdapter(this.f2147d);
        this.f2145a.setOnItemClickListener(this.f2160r);
        this.f2145a.setOnItemLongClickListener(this);
        this.f2148e = new SqlManager(this);
        m2259b();
        super.onStart();
    }
}
