package com.hopetruly.ecg.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hopetruly.ecg.ECGApplication;
import com.hopetruly.ecg.R;
import com.hopetruly.ecg.entity.PedometerRecord;
import com.hopetruly.ecg.p022b.SqlManager;

import java.util.ArrayList;
import java.util.Calendar;

public class StepRECActivity extends BaseActivity {

    /* renamed from: a */
    final String f2666a = "StepRECActivity";

    /* renamed from: c */
    ListView f2667c;

    /* renamed from: d */
    C0719b f2668d;

    /* renamed from: e */
    ArrayList<PedometerRecord> f2669e = new ArrayList<>();

    /* renamed from: f */
    Calendar f2670f;

    /* renamed from: g */
    ECGApplication f2671g;

    /* renamed from: h */
    SqlManager f2672h;

    /* renamed from: i */
    C0718a f2673i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public boolean f2674j;

    /* renamed from: com.hopetruly.ecg.activity.StepRECActivity$a */
    class C0718a extends AsyncTask<String, Void, ArrayList<PedometerRecord>> {
        C0718a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<PedometerRecord> doInBackground(String... strArr) {
            return StepRECActivity.this.f2672h.getPedometerRecord(strArr[0]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(ArrayList<PedometerRecord> arrayList) {
            if (isCancelled() || arrayList == null) {
                return;
            }
            if (arrayList.size() > 0) {
                StepRECActivity.this.f2669e = arrayList;
                StepRECActivity.this.f2668d.notifyDataSetChanged();
                return;
            }
            Toast.makeText(StepRECActivity.this.getApplicationContext(), StepRECActivity.this.getString(R.string.l_no_data), Toast.LENGTH_LONG).show();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }
    }

    /* renamed from: com.hopetruly.ecg.activity.StepRECActivity$b */
    class C0719b extends BaseAdapter {

        /* renamed from: com.hopetruly.ecg.activity.StepRECActivity$b$a */
        class C0720a {

            /* renamed from: a */
            TextView f2682a;

            /* renamed from: b */
            TextView f2683b;

            /* renamed from: c */
            TextView f2684c;

            /* renamed from: d */
            TextView f2685d;

            /* renamed from: e */
            TextView f2686e;

            /* renamed from: f */
            TextView f2687f;

            C0720a() {
            }
        }

        C0719b() {
        }

        public int getCount() {
            return StepRECActivity.this.f2669e.size();
        }

        public Object getItem(int i) {
            return StepRECActivity.this.f2669e.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            C0720a aVar;
            TextView textView;
            String str;
            PedometerRecord pedometerRecord = StepRECActivity.this.f2669e.get(i);
            if (view == null) {
                view = LayoutInflater.from(StepRECActivity.this.getApplicationContext()).inflate(R.layout.step_rec_lv_item, (ViewGroup) null);
                aVar = new C0720a();
                aVar.f2682a = (TextView) view.findViewById(R.id.step_rec_year_month);
                aVar.f2683b = (TextView) view.findViewById(R.id.step_rec_day);
                aVar.f2684c = (TextView) view.findViewById(R.id.step_rec_cal);
                aVar.f2685d = (TextView) view.findViewById(R.id.step_rec_step_count);
                aVar.f2686e = (TextView) view.findViewById(R.id.step_rec_finish_rate);
                aVar.f2687f = (TextView) view.findViewById(R.id.step_rec_target);
                view.setTag(aVar);
            } else {
                aVar = (C0720a) view.getTag();
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pedometerRecord.getYear());
            stringBuffer.append("-");
            stringBuffer.append(pedometerRecord.getMonth());
            stringBuffer.append("-");
            aVar.f2682a.setText(stringBuffer.toString());
            aVar.f2683b.setText(String.valueOf(pedometerRecord.getDay()));
            aVar.f2684c.setText(String.valueOf(pedometerRecord.getCal()));
            aVar.f2685d.setText(String.valueOf(pedometerRecord.getCount()));
            aVar.f2687f.setText(String.valueOf(pedometerRecord.getTarget()));
            if (pedometerRecord.getTarget() > 0) {
                textView = aVar.f2686e;
                str = ((pedometerRecord.getCount() * 100) / pedometerRecord.getTarget()) + "";
            } else {
                textView = aVar.f2686e;
                str = "0";
            }
            textView.setText(str);
            return view;
        }
    }

    /* renamed from: a */
    private void m2555a() {
        Log.d("StepRECActivity", "initView");
        this.f2667c = (ListView) findViewById(R.id.step_rec_lv);
        this.f2668d = new C0719b();
        this.f2667c.setAdapter(this.f2668d);
        this.f2667c.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                boolean unused = StepRECActivity.this.f2674j = StepRECActivity.this.f2671g.appMainService.getisGattStop();
                if (StepRECActivity.this.f2674j) {
                    StepRECActivity.this.m2561b();
                    return false;
                }
                StepRECActivity.this.m2556a(i);
                return false;
            }
        });
        m2558a(this.f2671g.mUserInfo.getId());
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2556a(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.delete));
        builder.setMessage(getResources().getString(R.string.delete_rec));
        builder.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                StepRECActivity.this.f2672h.deleteStepRecord(StepRECActivity.this.f2669e.get(i));
                StepRECActivity.this.f2669e.remove(i);
                StepRECActivity.this.f2668d.notifyDataSetChanged();
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

    /* renamed from: a */
    private void m2558a(String str) {
        if (this.f2673i == null || this.f2673i.getStatus() != AsyncTask.Status.RUNNING) {
            this.f2673i = new C0718a();
            this.f2673i.execute(new String[]{str});
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m2561b() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Tip));
        builder.setMessage(getResources().getString(R.string.runing_step));
        builder.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_step_rec);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.f2671g = (ECGApplication) getApplication();
        this.f2670f = Calendar.getInstance();
        this.f2672h = new SqlManager(getApplicationContext());
        m2555a();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.step_rec, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
