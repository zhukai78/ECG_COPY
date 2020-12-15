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
import com.hopetruly.ecg.util.ECGRecordUtils;
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
    public static final String TAG = "EcgRecListActivity";

    /* renamed from: a */
    ListView lv_ecg_rec;

    /* renamed from: c */
    List<ECGRecord> mErecords;

    /* renamed from: d */
    EcgListAdapter mEcgListAdapter;

    /* renamed from: e */
    SqlManager ecgrecSqlManager;

    /* renamed from: f */
    ECGApplication ecgrecApp;

    /* renamed from: g */
    NetService ecgrecNetService;

    /* renamed from: h */
    FileService ecgrecFileService;

    /* renamed from: i */
    EcgrecAsyncTask mEcgrecAsyncTask;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public PopupWindow pupop_login;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public EditText edt_login_user_name;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public EditText edt_login_user_pwd;

    /* renamed from: n */
    private ServiceConnection ecgrecNetServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            EcgRecListActivity.this.ecgrecNetService = ((NetService.NetSerBinder) iBinder).getNetSerBinder();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            EcgRecListActivity.this.ecgrecNetService = null;
        }
    };

    /* renamed from: o */
    private ServiceConnection ecgrecFileServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            EcgRecListActivity.this.ecgrecFileService = ((FileService.FileServiceBinder) iBinder).getFileService();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            EcgRecListActivity.this.ecgrecFileService = null;
        }
    };

    /* renamed from: p */
    private BroadcastReceiver fileImportBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Context applicationContext;
            EcgRecListActivity ecgRecListActivity;
            int i;
            String action = intent.getAction();
            if (action.equals("com.holptruly.ecg.services.FileService.FILE_IMPORT_START")) {
                EcgRecListActivity.this.showmProgressDialog(EcgRecListActivity.this.getString(R.string.p_importing_file));
            } else if (action.equals("com.holptruly.ecg.services.FileService.FILE_IMPORT_SUCCESS")) {
                ECGRecord a = ECGRecordUtils.m2774a((Context) EcgRecListActivity.this, intent.getStringExtra("com.holptruly.ecg.services.FileService.EXTRA_FILE"));
                if (a != null) {
                    EcgRecListActivity.this.ecgrecSqlManager.insertEcgRecord(a);
                    EcgRecListActivity.this.dismissProgressDialog();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.p_import_success;
                } else {
                    EcgRecListActivity.this.dismissProgressDialog();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.p_import_error;
                }
                Toast.makeText(applicationContext, ecgRecListActivity.getString(i), Toast.LENGTH_SHORT).show();
                EcgRecListActivity.this.startAcyn();
            } else if (action.equals("com.holptruly.ecg.services.FileService.FILE_IMPORT_FAIL")) {
                EcgRecListActivity.this.dismissProgressDialog();
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
                EcgRecListActivity.this.showmProgressDialog(EcgRecListActivity.this.getString(R.string.downloading));
            } else if (action.equals("com.holptruly.ecg.services.NetService.SYNC_DATA_SUCCESS_ACTION")) {
                List list = (List) intent.getSerializableExtra("records");
                if (list.size() > 0) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        ECGRecord eCGRecord = (ECGRecord) list.get(i2);
                        try {
                            ECGEntity a = ECGRecordUtils.m2773a(eCGRecord.getFilePath());
                            eCGRecord.setMark_time(a.getMark_time());
                            if (a.getLeadExten().equals(ECGEntity.LEAD_PART_HAND)) {
                                eCGRecord.setLeadType(0);
                            } else if (a.getLeadExten().equals(ECGEntity.LEAD_PART_CHEST)) {
                                eCGRecord.setLeadType(1);
                            }
                            EcgRecListActivity.this.ecgrecSqlManager.insertEcgRecord(eCGRecord);
                            EcgRecListActivity.this.ecgrecSqlManager.closeDatabase();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                EcgRecListActivity.this.dismissProgressDialog();
                EcgRecListActivity.this.startAcyn();
            } else {
                if (action.equals("com.holptruly.ecg.services.NetService.SYNC_DATA_FAIL_ACTION")) {
                    EcgRecListActivity.this.dismissProgressDialog();
                    EcgRecListActivity.this.startAcyn();
                    applicationContext = EcgRecListActivity.this.getApplicationContext();
                    ecgRecListActivity = EcgRecListActivity.this;
                    i = R.string.l_no_data;
                } else if (action.equals("com.holptruly.ecg.services.NetService.NEED_LOGIN")) {
                    Log.d(EcgRecListActivity.TAG, "Receive need log message");
                    EcgRecListActivity.this.dismissProgressDialog();
                    EcgRecListActivity.this.pupop_login.showAtLocation(EcgRecListActivity.this.findViewById(R.id.ecg_rec_list), 17, 0, 0);
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL")) {
                    EcgRecListActivity.this.dismissProgressDialog();
                    EcgRecListActivity.this.pupop_login.dismiss();
                    EcgRecListActivity.this.ecgrecNetService.mo2821a(EcgRecListActivity.this.ecgrecApp.mUserInfo.getId());
                    return;
                } else if (action.equals("com.holptruly.ecg.services.NetService.LOGIN_FAILE")) {
                    EcgRecListActivity.this.dismissProgressDialog();
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
    private AdapterView.OnItemClickListener adOnItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            Intent intent = new Intent(EcgRecListActivity.this, HistoryECGDisplayActivity.class);
            intent.putExtra("record", EcgRecListActivity.this.mErecords.get(i));
            EcgRecListActivity.this.startActivity(intent);
        }
    };

    /* renamed from: s */
    private ProgressDialog mProgressDialog;

    /* renamed from: com.hopetruly.ecg.activity.EcgRecListActivity$a */
    class EcgrecAsyncTask extends AsyncTask<Void, Void, List<ECGRecord>> {
        EcgrecAsyncTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public List<ECGRecord> doInBackground(Void... voidArr) {
            EcgRecListActivity.this.mErecords = EcgRecListActivity.this.ecgrecSqlManager.getECGRecord(EcgRecListActivity.this.ecgrecApp.mUserInfo.getId());
            for (ECGRecord user : EcgRecListActivity.this.mErecords) {
                user.setUser(EcgRecListActivity.this.ecgrecApp.mUserInfo);
            }
            return EcgRecListActivity.this.mErecords;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(List<ECGRecord> list) {
            if (!isCancelled()) {
                if (list.size() > 0) {
                    EcgRecListActivity.this.mEcgListAdapter.notifyDataSetChanged();
                }
                super.onPostExecute(list);
            }
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.EcgRecListActivity$b */
    private class EcgListAdapter extends BaseAdapter {

        /* renamed from: a */
        final int int2;

        /* renamed from: b */
        final int int0;

        /* renamed from: c */
        final int int1;

        /* renamed from: com.hopetruly.ecg.activity.EcgRecListActivity$b$a */
        private class ViewHolder {

            /* renamed from: a */
            TextView tv_ecg_rec_date;

            /* renamed from: b */
            TextView tv_ecg_rec_period;

            /* renamed from: c */
            TextView tv_ecg_rec_comment;

            /* renamed from: d */
            TextView tv_ecg_rec_avg_hr;

            /* renamed from: e */
            TextView tv_ecg_rec_name;

            /* renamed from: f */
            TextView tv_ecg_lead_type;

            private ViewHolder() {
            }
        }

        private EcgListAdapter() {
            this.int2 = 2;
            this.int0 = 0;
            this.int1 = 1;
        }

        /* renamed from: a */
        private String appendTip(String str, int i) {
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
            return EcgRecListActivity.this.mErecords.size();
        }

        public Object getItem(int i) {
            if (EcgRecListActivity.this.mErecords != null) {
                return EcgRecListActivity.this.mErecords.get(i);
            }
            return null;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            String mark_time = EcgRecListActivity.this.mErecords.get(i).getMark_time();
            return (mark_time == null || mark_time.equals("none")) ? 0 : 1;
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x00d9  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x00e8  */
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder aVar;
            View view2;
            TextView textView;
            int i2 = 0;
            int i3;
            EcgRecListActivity ecgRecListActivity;
            TextView textView2;
            ECGRecord eCGRecord = EcgRecListActivity.this.mErecords.get(i);
            int itemViewType = getItemViewType(i);
            if (view == null) {
                aVar = new ViewHolder();
                view2 = LayoutInflater.from(EcgRecListActivity.this.getApplicationContext()).inflate(R.layout.ecg_rec_list_item, (ViewGroup) null);
                aVar.tv_ecg_rec_name = (TextView) view2.findViewById(R.id.ecg_rec_name);
                aVar.tv_ecg_rec_date = (TextView) view2.findViewById(R.id.ecg_rec_date);
                aVar.tv_ecg_rec_period = (TextView) view2.findViewById(R.id.ecg_rec_period);
                aVar.tv_ecg_rec_avg_hr = (TextView) view2.findViewById(R.id.ecg_rec_avg_hr);
                aVar.tv_ecg_lead_type = (TextView) view2.findViewById(R.id.ecg_lead_type);
                aVar.tv_ecg_rec_comment = (TextView) view2.findViewById(R.id.ecg_rec_comment);
                view2.setTag(aVar);
            } else {
                view2 = view;
                aVar = (ViewHolder) view.getTag();
            }
            try {
                aVar.tv_ecg_rec_name.setText(URLDecoder.decode(eCGRecord.getFileName(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            aVar.tv_ecg_rec_date.setText(eCGRecord.getTime());
            aVar.tv_ecg_rec_period.setText(eCGRecord.getPeriod());
            aVar.tv_ecg_rec_avg_hr.setText(String.valueOf(eCGRecord.getHeartRate()));
            switch (eCGRecord.getLeadType()) {
                case 0:
                    textView2 = aVar.tv_ecg_lead_type;
                    ecgRecListActivity = EcgRecListActivity.this;
                    i3 = R.string.l_with_hand;
                    break;
                case 1:
                    textView2 = aVar.tv_ecg_lead_type;
                    ecgRecListActivity = EcgRecListActivity.this;
                    i3 = R.string.l_with_chest;
                    break;
                default:
                    aVar.tv_ecg_rec_comment.setText(appendTip(eCGRecord.getDescription(), 25));
                    if (itemViewType != 1) {
                        textView = aVar.tv_ecg_rec_name;
                        i2 = Color.rgb(255, 165, 0);
                    } else if (itemViewType != 0) {
                        return view2;
                    } else {
                        textView = aVar.tv_ecg_rec_name;
                        i2 = -16777216;
                    }
                    textView.setTextColor(i2);
                    return view2;
            }
            textView2.setText(ecgRecListActivity.getString(i3));
            aVar.tv_ecg_rec_comment.setText(appendTip(eCGRecord.getDescription(), 25));
            if (itemViewType != 1) {
            }
            aVar.tv_ecg_rec_name.setTextColor(i2);
            return view2;
        }

        public int getViewTypeCount() {
            return 2;
        }
    }

    /* renamed from: a */
    private void showDeletDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.delete));
        builder.setMessage(getResources().getString(R.string.delete_rec));
        builder.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String filePath = EcgRecListActivity.this.mErecords.get(i).getFilePath();
                if (filePath == null || filePath.length() <= 0) {
                    EcgRecListActivity.this.ecgrecSqlManager.deleteECGRec(String.valueOf(EcgRecListActivity.this.mErecords.get(i).getId()));
                    EcgRecListActivity.this.mErecords.remove(i);
                } else {
                    try {
                        File file = new File(filePath);
                        if (!file.exists() || !file.isFile()) {
                            String a = EcgRecListActivity.TAG;
                            Log.d(a, "File:[" + filePath + "] is not exists file");
                        } else {
                            file.delete();
                            EcgRecListActivity.this.ecgrecSqlManager.deleteECGRec(String.valueOf(EcgRecListActivity.this.mErecords.get(i).getId()));
                            EcgRecListActivity.this.mErecords.remove(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                EcgRecListActivity.this.mEcgListAdapter.notifyDataSetChanged();
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
    public void showmProgressDialog(String str) {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(this);
            this.mProgressDialog.setCanceledOnTouchOutside(false);
        }
        this.mProgressDialog.setMessage(str);
        if (!this.mProgressDialog.isShowing()) {
            this.mProgressDialog.show();
        }
    }

    /* renamed from: a */
    private boolean annotatedECG(File file) {
        try {
            ECGRecordUtils.annotatedECG(file, "AnnotatedECG");
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
    private void initPopView() {
        if (this.pupop_login == null) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.pupop_login, (ViewGroup) null);
            this.pupop_login = new PopupWindow(inflate, -2, -2, true);
            this.pupop_login.setOutsideTouchable(false);
            this.pupop_login.setFocusable(true);
            this.edt_login_user_name = (EditText) inflate.findViewById(R.id.login_user_name);
            this.edt_login_user_name.setText(((ECGApplication) getApplication()).mUserInfo.getName());
            this.edt_login_user_pwd = (EditText) inflate.findViewById(R.id.login_user_pwd);
            this.edt_login_user_pwd.requestFocus();
            ((Button) inflate.findViewById(R.id.login_btn_login)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        EcgRecListActivity.this.ecgrecNetService.getEcgFilesAsyn(EcgRecListActivity.this.edt_login_user_name.getText().toString(), EcgRecListActivity.this.edt_login_user_pwd.getText().toString());
                        EcgRecListActivity.this.showmProgressDialog(EcgRecListActivity.this.getString(R.string.p_login_login));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ((Button) inflate.findViewById(R.id.login_btn_cancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    EcgRecListActivity.this.pupop_login.dismiss();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void startAcyn() {
        this.mErecords.clear();
        this.mEcgrecAsyncTask = new EcgrecAsyncTask();
        this.mEcgrecAsyncTask.execute(new Void[0]);
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void dismissProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
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
            } else if (!file.isFile() || !annotatedECG(file)) {
                string = getResources().getString(R.string.p_not_valid_ecg_rec_file);
            } else {
                String[] split = stringExtra.split("/");
                this.ecgrecFileService.mo2702a(stringExtra, split[split.length - 1], this.ecgrecApp.mUserInfo.getName());
                return;
            }
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        setContentView(R.layout.activity_ecg_rec_list);
        super.onCreate(bundle);
        bindService(new Intent(this, FileService.class), this.ecgrecFileServiceConn, Context.BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_START");
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_SUCCESS");
        intentFilter.addAction("com.holptruly.ecg.services.FileService.FILE_IMPORT_FAIL");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.fileImportBroadcastReceiver, intentFilter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onDestroy() {
        if (this.ecgrecFileService != null) {
            Log.d(TAG, "fileService unbindService");
            unbindService(this.ecgrecFileServiceConn);
            this.ecgrecFileService = null;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.fileImportBroadcastReceiver);
        if (this.mEcgrecAsyncTask != null && this.mEcgrecAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.mEcgrecAsyncTask.cancel(true);
            this.mEcgrecAsyncTask = null;
        }
        super.onDestroy();
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        showDeletDialog(i);
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
                    this.ecgrecNetService.mo2821a(this.ecgrecApp.mUserInfo.getId());
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
        Log.d(TAG, "onPause");
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.f2159q);
        if (this.ecgrecNetService != null) {
            Log.d(TAG, "netService unbindService");
            unbindService(this.ecgrecNetServiceConn);
            this.ecgrecNetService = null;
        }
        super.onPause();
    }

    public void onResume() {
        startAcyn();
        super.onResume();
    }

    public void onStart() {
        Log.d(TAG, "OnStart");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.ecgrecApp = (ECGApplication) getApplication();
        bindService(new Intent(this, NetService.class), this.ecgrecNetServiceConn, Context.BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.holptruly.ecg.services.NetService.SYNC_DATA_BEGIN_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.SYNC_DATA_SUCCESS_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.SYNC_DATA_FAIL_ACTION");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.NEED_LOGIN");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_SUCCESSFUL");
        intentFilter.addAction("com.holptruly.ecg.services.NetService.LOGIN_FAILE");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.f2159q, intentFilter);
        this.lv_ecg_rec = (ListView) findViewById(R.id.ecg_rec_list);
        this.mEcgListAdapter = new EcgListAdapter();
        this.mErecords = new ArrayList();
        this.lv_ecg_rec.setAdapter(this.mEcgListAdapter);
        this.lv_ecg_rec.setOnItemClickListener(this.adOnItemClickListener);
        this.lv_ecg_rec.setOnItemLongClickListener(this);
        this.ecgrecSqlManager = new SqlManager(this);
        initPopView();
        super.onStart();
    }
}
