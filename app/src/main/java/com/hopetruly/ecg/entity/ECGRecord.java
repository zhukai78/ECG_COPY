package com.hopetruly.ecg.entity;

import java.io.Serializable;

public class ECGRecord implements Serializable {
    public static final String LEAD_AVF = "LEAD AVF";
    public static final String LEAD_AVL = "LEAD AVL";
    public static final String LEAD_AVR = "LEAD AVR";
    public static final String LEAD_I = "LEAD I";
    public static final String LEAD_II = "LEAD II";
    public static final String LEAD_III = "LEAD III";
    public static final int LEAD_I_HAND = 0;
    public static final int LEAD_I_HEART = 1;
    public static final String LEAD_V1 = "LEAD V1";
    public static final String LEAD_V2 = "LEAD V2";
    public static final String LEAD_V3 = "LEAD V3";
    public static final String LEAD_V4 = "LEAD V4";
    public static final String LEAD_V5 = "LEAD V5";
    public static final String LEAD_V6 = "LEAD V6";
    private static final long serialVersionUID = 1;
    private String description;
    private String diagnosis;
    private ECGEntity ecgEntity = null;
    private String fileName;
    private String filePath;
    private int heartRate;

    /* renamed from: id */
    private int f2799id;
    private int lead_type;
    private Machine machine;
    private String mark_time;
    private String netId;
    private String netPath;
    private String period;
    private String time;
    private UserInfo user;

    public String getDescription() {
        return this.description;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public ECGEntity getEcgEntity() {
        return this.ecgEntity;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public int getHeartRate() {
        return this.heartRate;
    }

    public int getId() {
        return this.f2799id;
    }

    public int getLeadType() {
        return this.lead_type;
    }

    public Machine getMachine() {
        return this.machine;
    }

    public String getMark_time() {
        return this.mark_time;
    }

    public String getNetId() {
        return this.netId;
    }

    public String getNetPath() {
        return this.netPath;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getTime() {
        return this.time;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public void setDescription(String str) {
        this.description = str;
        if (this.ecgEntity != null) {
            this.ecgEntity.setDesc(str);
        }
    }

    public void setDiagnosis(String str) {
        this.diagnosis = str;
    }

    public void setEcgEntity(ECGEntity eCGEntity) {
        this.ecgEntity = eCGEntity;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public void setHeartRate(int i) {
        this.heartRate = i;
    }

    public void setId(int i) {
        this.f2799id = i;
    }

    public void setLeadType(int i) {
        this.lead_type = i;
    }

    public void setMachine(Machine machine2) {
        this.machine = machine2;
    }

    public void setMark_time(String str) {
        this.mark_time = str;
    }

    public void setNetId(String str) {
        this.netId = str;
    }

    public void setNetPath(String str) {
        this.netPath = str;
    }

    public void setPeriod(String str) {
        this.period = str;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public void setUser(UserInfo userInfo) {
        this.user = userInfo;
    }
}
