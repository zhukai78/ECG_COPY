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

public class FileExploreActivity extends C0721a implements AdapterView.OnItemClickListener {

    /* renamed from: a */
    String f2211a = "FileExplore";

    /* renamed from: c */
    ListView f2212c;

    /* renamed from: d */
    List<Map<String, Object>> f2213d;

    /* renamed from: e */
    String f2214e;

    /* renamed from: a */
    private void m2285a() {
        File parentFile = new File(this.f2214e).getParentFile();
        if (parentFile == null) {
            Toast.makeText(this, getString(R.string.p_is_root_dir), Toast.LENGTH_SHORT).show();
        } else {
            this.f2214e = parentFile.getAbsolutePath();
        }
        m2286b(this.f2214e);
    }

    /* renamed from: b */
    private void m2286b(String str) {
        setTitle("文件浏览器 > " + str);
        this.f2213d = m2287c(str);
        this.f2212c.setAdapter(new SimpleAdapter(this, this.f2213d, R.layout.flile_explore_lv_item, new String[]{"name", "path"}, new int[]{R.id.file_name, R.id.file_path}));
        this.f2212c.setOnItemClickListener(this);
        this.f2212c.setSelection(0);
    }

    /* renamed from: c */
    private List<Map<String, Object>> m2287c(String str) {
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
    public void mo2178a(String str) {
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
        this.f2212c = (ListView) findViewById(R.id.file_explore_lv);
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.f2214e = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath()).toString();
            m2286b(this.f2214e);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        String str = this.f2211a;
        Log.i(str, "item clicked! [" + i + "]");
        if (i == 0) {
            this.f2214e = "/";
        } else if (i == 1) {
            m2285a();
            return;
        } else {
            this.f2214e = (String) this.f2213d.get(i).get("path");
            if (!new File(this.f2214e).isDirectory()) {
                Toast.makeText(this, this.f2214e, Toast.LENGTH_SHORT).show();
                mo2178a(this.f2214e);
                finish();
                return;
            }
        }
        m2286b(this.f2214e);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
