package com.hopetruly.ecg.entity;

import java.io.Serializable;

public class ECGEntity implements Serializable {
    public static final String LEAD_AVF = "MDC_ECG_LEAD_AVF";
    public static final String LEAD_AVL = "MDC_ECG_LEAD_AVL";
    public static final String LEAD_AVR = "MDC_ECG_LEAD_AVR";
    public static final String LEAD_I = "MDC_ECG_LEAD_I";
    public static final String LEAD_II = "MDC_ECG_LEAD_II";
    public static final String LEAD_III = "MDC_ECG_LEAD_III";
    public static final String LEAD_PART_CHEST = "CHEST";
    public static final String LEAD_PART_HAND = "HAND";
    public static final String LEAD_V1 = "MDC_ECG_LEAD_V1";
    public static final String LEAD_V2 = "MDC_ECG_LEAD_V2";
    public static final String LEAD_V3 = "MDC_ECG_LEAD_V3";
    public static final String LEAD_V4 = "MDC_ECG_LEAD_V4";
    public static final String LEAD_V5 = "MDC_ECG_LEAD_V5";
    public static final String LEAD_V6 = "MDC_ECG_LEAD_V6";
    private static final long serialVersionUID = 1;
    private String Lead = LEAD_I;
    private String Lead_exten;
    private String digits;
    private String ecg_desc;
    private String endTime;
    private int[] mark_period;
    private String mark_time;
    private String scaleUnit = "mV";
    private String scaleUnitValue = "0.001";
    private String srcFilePath;
    private String startTime;
    private String timeUnit = "ms";
    private String timeUnitValue = "4";

    public String getDesc() {
        return this.ecg_desc;
    }

    public String getDigits() {
        return this.digits;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getLead() {
        return this.Lead;
    }

    public String getLeadExten() {
        return this.Lead_exten;
    }

    public int[] getMark_period() {
        return this.mark_period;
    }

    public String getMark_time() {
        return this.mark_time;
    }

    public String getScaleUnit() {
        return this.scaleUnit;
    }

    public String getScaleUnitValue() {
        return this.scaleUnitValue;
    }

    public String getSrcFilePath() {
        return this.srcFilePath;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getTimeUnit() {
        return this.timeUnit;
    }

    public String getTimeUnitValue() {
        return this.timeUnitValue;
    }

    public void setDesc(String str) {
        this.ecg_desc = str;
    }

    public void setDigits(String str) {
        this.digits = str;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public void setLead(String str) {
        this.Lead = str;
    }

    public void setLeadExten(String str) {
        this.Lead_exten = str;
    }

    public void setMark_period(int[] iArr) {
        this.mark_period = iArr;
    }

    public void setMark_time(String str) {
        this.mark_time = str;
    }

    public void setScaleUnit(String str) {
        this.scaleUnit = str;
    }

    public void setScaleUnitValue(String str) {
        this.scaleUnitValue = str;
    }

    public void setSrcFilePath(String str) {
        this.srcFilePath = str;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public void setTimeUnit(String str) {
        this.timeUnit = str;
    }

    public void setTimeUnitValue(String str) {
        this.timeUnitValue = str;
    }
}
