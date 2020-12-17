package com.hopetruly.ecg.entity;

import java.io.Serializable;

public class Machine implements Serializable {
    private static final long serialVersionUID = 1;
    private String FwRev;
    private String HwRev;

    /* renamed from: Id */
    private String MacId;
    private String MacAddress;
    private String ManufacturerName;
    private String Model;
    private String Name;
    private int batteryLevel;
    private String serialNum;

    public int getBatteryLevel() {
        return this.batteryLevel;
    }

    public String getFwRev() {
        return this.FwRev;
    }

    public String getHwRev() {
        return this.HwRev;
    }

    public String getId() {
        return this.MacId;
    }

    public String getMacAddress() {
        return this.MacAddress;
    }

    public String getManufacturerName() {
        return this.ManufacturerName;
    }

    public String getModel() {
        return this.Model;
    }

    public String getName() {
        return this.Name;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setBatteryLevel(int i) {
        this.batteryLevel = i;
    }

    public void setFwRev(String str) {
        this.FwRev = str;
    }

    public void setHwRev(String str) {
        this.HwRev = str;
    }

    public void setMac(String str) {
        this.MacId = str;
    }

    public void setMacAddress(String str) {
        this.MacAddress = str;
    }

    public void setManufacturerName(String str) {
        this.ManufacturerName = str;
    }

    public void setModel(String str) {
        this.Model = str;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }
}
