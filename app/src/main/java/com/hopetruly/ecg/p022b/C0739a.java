package com.hopetruly.ecg.p022b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* renamed from: com.hopetruly.ecg.b.a */
public class C0739a extends SQLiteOpenHelper {

    /* renamed from: a */
    private static final String f2764a = "a";

    /* renamed from: b */
    private static C0739a f2765b;

    private C0739a(Context context) {
        super(context, "heatrate.db", (SQLiteDatabase.CursorFactory) null, 3);
    }

    /* renamed from: a */
    public static C0739a m2607a(Context context) {
        if (f2765b == null) {
            f2765b = new C0739a(context);
        }
        return f2765b;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE ecg_record(record_id INTEGER primary key autoincrement,netId char(100),userId char(50),machineId char(50),machineName char(100),heartRate integer,time char(50),period char(50),discription TEXT,fileName char(100),filepath TEXT,fileUrl TEXT,diagnosis TEXT,lead_type integer,mark_time char(100))");
        sQLiteDatabase.execSQL("CREATE TABLE step_record(record_id INTEGER primary key autoincrement,userId char(50),target integer,step integer,cal char(100),year integer,month integer,day integer,desc TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE step_hour_record(record_id INTEGER primary key autoincrement,recId char(50),year integer,month integer,day integer,hour integer,count integer,desc TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        String str = f2764a;
        Log.d(str, "onUpgrade oldVersion:" + i + " newVersion:" + i2);
        switch (i) {
            case 1:
                sQLiteDatabase.execSQL("alter table ecg_record add column lead_type integer");
                break;
            case 2:
                break;
            default:
                return;
        }
        sQLiteDatabase.execSQL("alter table ecg_record add column mark_time char(100)");
    }
}
