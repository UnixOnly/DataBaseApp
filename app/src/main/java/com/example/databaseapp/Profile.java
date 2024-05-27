package com.example.databaseapp;

import java.util.SplittableRandom;

public class Profile {
    public long _id;
    public String sName;
    public String name;
    public int age;
    public int photo;


    public Profile(long _id, String sName, String name, int age, int photo) {
        this._id = _id;
        this.sName = sName;
        this.name = name;
        this.age = age;
        this.photo = photo;
    }

    public Profile(String sName, String name, int age, int photo) {
        this.sName = sName;
        this.name = name;
        this.age = age;
        this.photo = photo;
    }

    public Profile() {
    }
}
