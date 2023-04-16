package com.example.appnghenhac.models;

public class Singer {
    public int id;
    public String name;
    public String url;
    public String dob;

    public Singer(int id, String name, String url, String dob) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDob() {
        return dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
