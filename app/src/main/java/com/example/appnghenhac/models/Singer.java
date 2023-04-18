package com.example.appnghenhac.models;

public class Singer {
    public int id;
    public String nameSinger;
    public String imageSinger;
    public String dateofBirth;

    public Singer(int id, String nameSinger, String imageSinger, String dateofBirth) {
        this.id = id;
        this.nameSinger = nameSinger;
        this.imageSinger = imageSinger;
        this.dateofBirth = dateofBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getImageSinger() {
        return imageSinger;
    }

    public void setImageSinger(String imageSinger) {
        this.imageSinger = imageSinger;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }
}
