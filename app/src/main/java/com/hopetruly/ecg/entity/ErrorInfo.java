package com.hopetruly.ecg.entity;

import java.io.Serializable;

public class ErrorInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private String AndroidVer;
    private String CPU_ABI;
    private String Model;
    private String androudSdk;
    private String errorInfo;
    private String firmwareVer = null;
    private String manufacturer;
    private String opDesc;
    private String solfwareId;
    private String time;
    private String versionCode;
    private String versionName;

    public String getAndroidVer() {
        return this.AndroidVer;
    }

    public String getAndroudSdk() {
        return this.androudSdk;
    }

    public String getCPU_ABI() {
        return this.CPU_ABI;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public String getFirmwareVer() {
        return this.firmwareVer;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getModel() {
        return this.Model;
    }

    public String getOpDesc() {
        return this.opDesc;
    }

    public String getSolfwareId() {
        return this.solfwareId;
    }

    public String getTime() {
        return this.time;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setAndroidVer(String str) {
        this.AndroidVer = str;
    }

    public void setAndroudSdk(String str) {
        this.androudSdk = str;
    }

    public void setCPU_ABI(String str) {
        this.CPU_ABI = str;
    }

    public void setErrorInfo(String str) {
        this.errorInfo = str;
    }

    public void setFirmwareVer(String str) {
        this.firmwareVer = str;
    }

    public void setManufacturer(String str) {
        this.manufacturer = str;
    }

    public void setModel(String str) {
        this.Model = str;
    }

    public void setOpDesc(String str) {
        this.opDesc = str;
    }

    public void setSolfwareId(String str) {
        this.solfwareId = str;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public void setVersionCode(String str) {
        this.versionCode = str;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }
}
