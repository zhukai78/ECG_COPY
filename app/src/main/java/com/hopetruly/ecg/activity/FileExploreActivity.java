package com.hopetruly.ecg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hopetruly.ecg.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileExploreActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    /* renamed from: a */
    String TAG = "FileExplore";

    /* renamed from: c */
    ListView lv_file_explore;

    /* renamed from: d */
    List<Map<String, Object>> files;

    /* renamed from: e */
    String sdPath;

    /* renamed from: a */
    private void isroot_dir() {
        File parentFile = new File(this.sdPath).getParentFile();
        if (parentFile == null) {
            Toast.makeText(this, getString(R.string.p_is_root_dir), Toast.LENGTH_SHORT).show();
        } else {
            this.sdPath = parentFile.getAbsolutePath();
        }
        openDIr(this.sdPath);
    }

    /* renamed from: b */
    private void openDIr(String str) {
        setTitle("文件浏览器 > " + str);
        this.files = getFiles(str);
        this.lv_file_explore.setAdapter(new SimpleAdapter(this, this.files, R.layout.flile_explore_lv_item, new String[]{"name", "path"}, new int[]{R.id.file_name, R.id.file_path}));
        this.lv_file_explore.setOnItemClickListener(this);
        this.lv_file_explore.setSelection(0);
    }

    /* renamed from: c */
    private List<Map<String, Object>> getFiles(String str) {
        String str2;
        String name;
        if (new File(str).canRead()) {
            File[] listFiles = new File(str).listFiles();
            ArrayList arrayList = new ArrayList(listFiles.length);
            HashMap hashMap = new HashMap();
            hashMap.put("name", "/");
            hashMap.put("img", (Object) null);
            hashMap.put("path", "去根目录");
            arrayList.add(hashMap);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("name", "../");
            hashMap2.put("img", (Object) null);
            hashMap2.put("path", "返回上一目录");
            arrayList.add(hashMap2);
            for (File file : listFiles) {
                HashMap hashMap3 = new HashMap();
                if (file.isDirectory()) {
                    hashMap3.put("img", (Object) null);
                    str2 = "name";
                    name = "/" + file.getName();
                } else {
                    hashMap3.put("img", (Object) null);
                    str2 = "name";
                    name = file.getName();
                }
                hashMap3.put(str2, name);
                hashMap3.put("path", file.getPath());
                arrayList.add(hashMap3);
            }
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList(0);
        HashMap hashMap4 = new HashMap();
        hashMap4.put("name", "/");
        hashMap4.put("img", (Object) null);
        hashMap4.put("path", "去根目录");
        arrayList2.add(hashMap4);
        HashMap hashMap5 = new HashMap();
        hashMap5.put("name", "../");
        hashMap5.put("img", (Object) null);
        hashMap5.put("path", "返回上一目录");
        arrayList2.add(hashMap5);
        return arrayList2;
    }

    /* renamed from: a */
    public void pick_file_resume(String str) {
        Intent intent = new Intent();
        intent.setAction("com.hopetruly.ecg.FileExplore.PICK_FILE_RESUME");
        intent.putExtra("file_path", str);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        setResult(-1, intent);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(bundle);
        setContentView(R.layout.activity_file_explore);
        this.lv_file_explore = (ListView) findViewById(R.id.file_explore_lv);
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.sdPath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath()).toString();
            openDIr(this.sdPath);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String str = this.TAG;
        Log.i(str, "item clicked! [" + i + "]");
        if (i == 0) {
            this.sdPath = "/";
        } else if (i == 1) {
            isroot_dir();
            return;
        } else {
            this.sdPath = (String) this.files.get(i).get("path");
            if (!new File(this.sdPath).isDirectory()) {
                Toast.makeText(this, this.sdPath, Toast.LENGTH_SHORT).show();
                pick_file_resume(this.sdPath);
                finish();
                return;
            }
        }
        openDIr(this.sdPath);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
