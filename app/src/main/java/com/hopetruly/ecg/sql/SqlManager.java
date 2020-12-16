package com.hopetruly.ecg.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hopetruly.ecg.entity.ECGRecord;
import com.hopetruly.ecg.entity.Machine;
import com.hopetruly.ecg.entity.PedometerRecord;
import com.hopetruly.ecg.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.hopetruly.ecg.b.b */
public class SqlManager {

    /* renamed from: a */
    MySQLiteOpenHelper mMySQLiteOpenHelper;

    /* renamed from: b */
    private SQLiteDatabase mSQLiteDatabase = null;

    public SqlManager(Context context) {
        this.mMySQLiteOpenHelper = MySQLiteOpenHelper.getInstance(context);
    }

    /* renamed from: a */
    public PedometerRecord getPedometerRecord(String str, int i, int i2, int i3) {
        PedometerRecord pedometerRecord;
        SQLiteDatabase readableDatabase = this.mMySQLiteOpenHelper.getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("select * from step_record where userId=? and year=? and month=? and day=?", new String[]{str, String.valueOf(i), String.valueOf(i2), String.valueOf(i3)});
        if (rawQuery.moveToFirst()) {
            pedometerRecord = new PedometerRecord();
            pedometerRecord.setUserId(rawQuery.getString(rawQuery.getColumnIndex("userId")));
            pedometerRecord.setTarget((long) rawQuery.getInt(rawQuery.getColumnIndex("target")));
            pedometerRecord.setCount((long) rawQuery.getInt(rawQuery.getColumnIndex("step")));
            pedometerRecord.setCal((long) rawQuery.getInt(rawQuery.getColumnIndex("cal")));
            pedometerRecord.setYear(rawQuery.getInt(rawQuery.getColumnIndex("year")));
            pedometerRecord.setMonth(rawQuery.getInt(rawQuery.getColumnIndex("month")));
            pedometerRecord.setDay(rawQuery.getInt(rawQuery.getColumnIndex("day")));
            pedometerRecord.setDesc(rawQuery.getString(rawQuery.getColumnIndex("desc")));
        } else {
            pedometerRecord = null;
        }
        rawQuery.close();
        readableDatabase.close();
        return pedometerRecord;
    }

    /* renamed from: a */
    public List<ECGRecord> getECGRecord(String str) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = this.mMySQLiteOpenHelper.getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("select * from ecg_record where userId=? order by record_id desc", new String[]{str});
        if (rawQuery.moveToFirst()) {
            do {
                ECGRecord eCGRecord = new ECGRecord();
                eCGRecord.setId(rawQuery.getInt(rawQuery.getColumnIndex("record_id")));
                eCGRecord.setUser(new UserInfo());
                eCGRecord.getUser().setId(rawQuery.getString(rawQuery.getColumnIndex("userId")));
                eCGRecord.setMachine(new Machine());
                eCGRecord.getMachine().setId(rawQuery.getString(rawQuery.getColumnIndex("machineId")));
                eCGRecord.getMachine().setName(rawQuery.getString(rawQuery.getColumnIndex("machineName")));
                eCGRecord.setFileName(rawQuery.getString(rawQuery.getColumnIndex("fileName")));
                eCGRecord.setHeartRate(rawQuery.getInt(rawQuery.getColumnIndex("heartRate")));
                eCGRecord.setFilePath(rawQuery.getString(rawQuery.getColumnIndex("filepath")));
                eCGRecord.setTime(rawQuery.getString(rawQuery.getColumnIndex("time")));
                eCGRecord.setPeriod(rawQuery.getString(rawQuery.getColumnIndex("period")));
                eCGRecord.setDescription(rawQuery.getString(rawQuery.getColumnIndex("discription")));
                eCGRecord.setDiagnosis(rawQuery.getString(rawQuery.getColumnIndex("diagnosis")));
                eCGRecord.setLeadType(rawQuery.getInt(rawQuery.getColumnIndex("lead_type")));
                eCGRecord.setMark_time(rawQuery.getString(rawQuery.getColumnIndex("mark_time")));
                arrayList.add(eCGRecord);
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        readableDatabase.close();
        closeDatabase();
        return arrayList;
    }

    /* renamed from: a */
    public void closeDatabase() {
        if (this.mSQLiteDatabase != null) {
            this.mSQLiteDatabase.close();
            this.mSQLiteDatabase = null;
        }
    }

    /* renamed from: a */
    public void insertEcgRecord(ECGRecord eCGRecord) {
        this.mSQLiteDatabase = this.mMySQLiteOpenHelper.getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = this.mSQLiteDatabase;
        String[] strArr = new String[12];
        strArr[0] = eCGRecord.getUser().getId();
        strArr[1] = eCGRecord.getMachine() == null ? "null" : eCGRecord.getMachine().getId();
        strArr[2] = eCGRecord.getMachine() == null ? "null" : eCGRecord.getMachine().getName();
        strArr[3] = String.valueOf(eCGRecord.getHeartRate());
        strArr[4] = eCGRecord.getTime();
        strArr[5] = eCGRecord.getPeriod();
        strArr[6] = eCGRecord.getDescription();
        strArr[7] = eCGRecord.getFileName();
        strArr[8] = eCGRecord.getFilePath();
        strArr[9] = eCGRecord.getDiagnosis() == null ? "none" : eCGRecord.getDiagnosis();
        strArr[10] = String.valueOf(eCGRecord.getLeadType());
        strArr[11] = eCGRecord.getMark_time() == null ? "none" : eCGRecord.getMark_time();
        sQLiteDatabase.execSQL("insert into ecg_record(userId,machineId,machineName,heartRate,time,period,discription,fileName,filepath,diagnosis, lead_type, mark_time) values(?,?,?,?,?,?,?,?,?,?,?,?)", strArr);
    }

    /* renamed from: a */
    public void insertStepRecord(PedometerRecord pedometerRecord) {
        SQLiteDatabase writableDatabase = this.mMySQLiteOpenHelper.getWritableDatabase();
        writableDatabase.execSQL("insert into step_record(userId,target,step,cal,year,month,day,desc) values(?,?,?,?,?,?,?,?)", new String[]{pedometerRecord.getUserId(), String.valueOf(pedometerRecord.getTarget()), String.valueOf(pedometerRecord.getCount()), String.valueOf(pedometerRecord.getCal()), String.valueOf(pedometerRecord.getYear()), String.valueOf(pedometerRecord.getMonth()), String.valueOf(pedometerRecord.getDay()), pedometerRecord.getDesc()});
        writableDatabase.close();
    }

    /* renamed from: b */
    public void updateEcgRecord(ECGRecord eCGRecord) {
        SQLiteDatabase writableDatabase = this.mMySQLiteOpenHelper.getWritableDatabase();
        String[] strArr = new String[13];
        strArr[0] = eCGRecord.getUser().getId();
        strArr[1] = eCGRecord.getMachine() == null ? "null" : eCGRecord.getMachine().getId();
        strArr[2] = eCGRecord.getMachine() == null ? "null" : eCGRecord.getMachine().getName();
        strArr[3] = String.valueOf(eCGRecord.getHeartRate());
        strArr[4] = eCGRecord.getTime();
        strArr[5] = eCGRecord.getPeriod();
        strArr[6] = eCGRecord.getDescription();
        strArr[7] = eCGRecord.getFileName();
        strArr[8] = eCGRecord.getFilePath();
        strArr[9] = eCGRecord.getDiagnosis() == null ? "none" : eCGRecord.getDiagnosis();
        strArr[10] = String.valueOf(eCGRecord.getLeadType());
        strArr[11] = eCGRecord.getMark_time() == null ? "none" : eCGRecord.getMark_time();
        strArr[12] = String.valueOf(eCGRecord.getId());
        writableDatabase.execSQL("update ecg_record set userId=?, machineId=?,machineName=?,heartRate=?,time=?,period=?,discription=?,fileName=?,filepath=?,diagnosis=?, lead_type=?, mark_time=? where record_id=?", strArr);
        writableDatabase.close();
    }

    /* renamed from: b */
    public void deleteStepRecord(PedometerRecord pedometerRecord) {
        SQLiteDatabase writableDatabase = this.mMySQLiteOpenHelper.getWritableDatabase();
        writableDatabase.execSQL("delete from step_record where userId=? and year=? and month=? and day=?", new String[]{pedometerRecord.getUserId(), String.valueOf(pedometerRecord.getYear()), String.valueOf(pedometerRecord.getMonth()), String.valueOf(pedometerRecord.getDay())});
        writableDatabase.close();
    }

    /* renamed from: b */
    public boolean selectEcgRecodBynum(String str) {
        SQLiteDatabase readableDatabase = this.mMySQLiteOpenHelper.getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("select count(*) as num from ecg_record where fileName=?", new String[]{str});
        rawQuery.moveToFirst();
        int i = rawQuery.getInt(rawQuery.getColumnIndex("num"));
        rawQuery.close();
        readableDatabase.close();
        return i > 0;
    }

    /* renamed from: c */
    public void writeStepRec(PedometerRecord pedometerRecord) {
        SQLiteDatabase writableDatabase = this.mMySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("step", Long.valueOf(pedometerRecord.getCount()));
        contentValues.put("cal", Long.valueOf(pedometerRecord.getCal()));
        contentValues.put("target", Long.valueOf(pedometerRecord.getTarget()));
        writableDatabase.update("step_record", contentValues, "userId=? and year=? and month=? and day=?", new String[]{pedometerRecord.getUserId(), String.valueOf(pedometerRecord.getYear()), String.valueOf(pedometerRecord.getMonth()), String.valueOf(pedometerRecord.getDay())});
        writableDatabase.close();
    }

    /* renamed from: c */
    public void deleteECGRec(String str) {
        SQLiteDatabase writableDatabase = this.mMySQLiteOpenHelper.getWritableDatabase();
        writableDatabase.execSQL("delete from ecg_record where record_id=?", new String[]{str});
        writableDatabase.close();
    }

    /* renamed from: d */
    public int rawQueryEcgRecord(String str) {
        if (str == null) {
            str = "null";
        }
        Cursor rawQuery = this.mMySQLiteOpenHelper.getReadableDatabase().rawQuery("select count(1) from ecg_record where userId=?", new String[]{str});
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(0);
        }
        return 0;
    }

    /* renamed from: e */
    public ArrayList<PedometerRecord> getPedometerRecord(String str) {
        SQLiteDatabase readableDatabase = this.mMySQLiteOpenHelper.getReadableDatabase();
        ArrayList<PedometerRecord> arrayList = new ArrayList<>();
        Cursor rawQuery = readableDatabase.rawQuery("select * from step_record where userId=? order by record_id desc", new String[]{str});
        if (rawQuery.moveToFirst()) {
            do {
                PedometerRecord pedometerRecord = new PedometerRecord();
                pedometerRecord.setUserId(rawQuery.getString(rawQuery.getColumnIndex("userId")));
                pedometerRecord.setTarget((long) rawQuery.getInt(rawQuery.getColumnIndex("target")));
                pedometerRecord.setCount((long) rawQuery.getInt(rawQuery.getColumnIndex("step")));
                pedometerRecord.setCal((long) rawQuery.getInt(rawQuery.getColumnIndex("cal")));
                pedometerRecord.setYear(rawQuery.getInt(rawQuery.getColumnIndex("year")));
                pedometerRecord.setMonth(rawQuery.getInt(rawQuery.getColumnIndex("month")));
                pedometerRecord.setDay(rawQuery.getInt(rawQuery.getColumnIndex("day")));
                pedometerRecord.setDesc(rawQuery.getString(rawQuery.getColumnIndex("desc")));
                arrayList.add(pedometerRecord);
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }
}
