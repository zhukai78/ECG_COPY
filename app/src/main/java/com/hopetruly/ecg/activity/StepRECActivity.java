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
import com.hopetruly.ecg.sql.SqlManager;

import java.util.ArrayList;
import java.util.Calendar;

public class StepRECActivity extends BaseActivity {

    /* renamed from: a */
    final String TAG = "StepRECActivity";

    /* renamed from: c */
    ListView lv_step_rec;

    /* renamed from: d */
    RecBaseAdapter mRecBaseAdapter;

    /* renamed from: e */
    ArrayList<PedometerRecord> mPedometerRecords = new ArrayList<>();

    /* renamed from: f */
    Calendar mCalendar;

    /* renamed from: g */
    ECGApplication mECGApplication;

    /* renamed from: h */
    SqlManager mSqlManager;

    /* renamed from: i */
    GetPedometerRecordAsyncTask mGetPedometerRecordAsyncTask;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public boolean isGattStop;

    /* renamed from: com.hopetruly.ecg.activity.StepRECActivity$a */
    class GetPedometerRecordAsyncTask extends AsyncTask<String, Void, ArrayList<PedometerRecord>> {
        GetPedometerRecordAsyncTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<PedometerRecord> doInBackground(String... strArr) {
            return StepRECActivity.this.mSqlManager.getPedometerRecord(strArr[0]);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(ArrayList<PedometerRecord> arrayList) {
            if (isCancelled() || arrayList == null) {
                return;
            }
            if (arrayList.size() > 0) {
                StepRECActivity.this.mPedometerRecords = arrayList;
                StepRECActivity.this.mRecBaseAdapter.notifyDataSetChanged();
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
    class RecBaseAdapter extends BaseAdapter {

        /* renamed from: com.hopetruly.ecg.activity.StepRECActivity$b$a */
        class ViewHolder {

            /* renamed from: a */
            TextView tv_step_rec_year_month;

            /* renamed from: b */
            TextView tv_step_rec_day;

            /* renamed from: c */
            TextView step_rec_cal;

            /* renamed from: d */
            TextView tv_step_rec_step_count;

            /* renamed from: e */
            TextView tv_step_rec_finish_rate;

            /* renamed from: f */
            TextView tv_step_rec_target;

            ViewHolder() {
            }
        }

        RecBaseAdapter() {
        }

        public int getCount() {
            return StepRECActivity.this.mPedometerRecords.size();
        }

        public Object getItem(int i) {
            return StepRECActivity.this.mPedometerRecords.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder aVar;
            TextView textView;
            String str;
            PedometerRecord pedometerRecord = StepRECActivity.this.mPedometerRecords.get(i);
            if (view == null) {
                view = LayoutInflater.from(StepRECActivity.this.getApplicationContext()).inflate(R.layout.step_rec_lv_item, (ViewGroup) null);
                aVar = new ViewHolder();
                aVar.tv_step_rec_year_month = (TextView) view.findViewById(R.id.step_rec_year_month);
                aVar.tv_step_rec_day = (TextView) view.findViewById(R.id.step_rec_day);
                aVar.step_rec_cal = (TextView) view.findViewById(R.id.step_rec_cal);
                aVar.tv_step_rec_step_count = (TextView) view.findViewById(R.id.step_rec_step_count);
                aVar.tv_step_rec_finish_rate = (TextView) view.findViewById(R.id.step_rec_finish_rate);
                aVar.tv_step_rec_target = (TextView) view.findViewById(R.id.step_rec_target);
                view.setTag(aVar);
            } else {
                aVar = (ViewHolder) view.getTag();
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pedometerRecord.getYear());
            stringBuffer.append("-");
            stringBuffer.append(pedometerRecord.getMonth());
            stringBuffer.append("-");
            aVar.tv_step_rec_year_month.setText(stringBuffer.toString());
            aVar.tv_step_rec_day.setText(String.valueOf(pedometerRecord.getDay()));
            aVar.step_rec_cal.setText(String.valueOf(pedometerRecord.getCal()));
            aVar.tv_step_rec_step_count.setText(String.valueOf(pedometerRecord.getCount()));
            aVar.tv_step_rec_target.setText(String.valueOf(pedometerRecord.getTarget()));
            if (pedometerRecord.getTarget() > 0) {
                textView = aVar.tv_step_rec_finish_rate;
                str = ((pedometerRecord.getCount() * 100) / pedometerRecord.getTarget()) + "";
            } else {
                textView = aVar.tv_step_rec_finish_rate;
                str = "0";
            }
            textView.setText(str);
            return view;
        }
    }

    /* renamed from: a */
    private void initView() {
        Log.d("StepRECActivity", "initView");
        this.lv_step_rec = (ListView) findViewById(R.id.step_rec_lv);
        this.mRecBaseAdapter = new RecBaseAdapter();
        this.lv_step_rec.setAdapter(this.mRecBaseAdapter);
        this.lv_step_rec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                boolean unused = StepRECActivity.this.isGattStop = StepRECActivity.this.mECGApplication.appMainService.getisGattStop();
                if (StepRECActivity.this.isGattStop) {
                    StepRECActivity.this.showRunStepAlertDialog();
                    return false;
                }
                StepRECActivity.this.showDeleteRecAlertDialog(i);
                return false;
            }
        });
        startPedometerRecordAsyncTask(this.mECGApplication.mUserInfo.getId());
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void showDeleteRecAlertDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.delete));
        builder.setMessage(getResources().getString(R.string.delete_rec));
        builder.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                StepRECActivity.this.mSqlManager.deleteStepRecord(StepRECActivity.this.mPedometerRecords.get(i));
                StepRECActivity.this.mPedometerRecords.remove(i);
                StepRECActivity.this.mRecBaseAdapter.notifyDataSetChanged();
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
    private void startPedometerRecordAsyncTask(String str) {
        if (this.mGetPedometerRecordAsyncTask == null || this.mGetPedometerRecordAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            this.mGetPedometerRecordAsyncTask = new GetPedometerRecordAsyncTask();
            this.mGetPedometerRecordAsyncTask.execute(new String[]{str});
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void showRunStepAlertDialog() {
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
        this.mECGApplication = (ECGApplication) getApplication();
        this.mCalendar = Calendar.getInstance();
        this.mSqlManager = new SqlManager(getApplicationContext());
        initView();
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
