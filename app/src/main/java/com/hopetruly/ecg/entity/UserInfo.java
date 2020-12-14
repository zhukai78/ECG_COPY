package com.hopetruly.ecg.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    public static final String EMPTY = null;
    private static final long serialVersionUID = 1;
    private String address;
    private int age;
    private String birthday;
    private String email;
    private String emePhone;
    private String firstName;
    private String height;

    /* renamed from: id */
    private String f2801id;
    private String lastName;
    private String medications;
    private String name;
    private String password;
    private String phone;
    private String profession;
    private String sex = "M";
    private String smoker = "no";
    private String userName;
    private String weight;

    public String getAddress() {
        return this.address;
    }

    public int getAge() {
        return this.age;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getEmail() {
        return this.email;
    }

    public String getEmePhone() {
        return this.emePhone;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getHeight() {
        return this.height;
    }

    public String getId() {
        return this.f2801id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMedications() {
        return this.medications;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getProfession() {
        return this.profession;
    }

    public String getSex() {
        return this.sex;
    }

    public String getSmoker() {
        return this.smoker;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public void setBirthday(String str) {
        this.birthday = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setEmePhone(String str) {
        this.emePhone = str;
    }

    public void setFirstName(String str) {
        this.firstName = str;
    }

    public void setHeight(String str) {
        this.height = str;
    }

    public void setId(String str) {
        this.f2801id = str;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setMedications(String str) {
        this.medications = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setProfession(String str) {
        this.profession = str;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public void setSmoker(String str) {
        this.smoker = str;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public void setWeight(String str) {
        this.weight = str;
    }
}
