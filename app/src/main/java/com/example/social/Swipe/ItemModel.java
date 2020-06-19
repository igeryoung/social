package com.example.social.Swipe;

import android.net.Uri;

public class ItemModel {

    private String name, city, age;
    private String image;

    public ItemModel(String image, String name, String city, String age) {
        this.image = image;
        this.name = name;
        this.city = city;
        this.age = age;
    }

    public Uri getImage() {
        return Uri.parse(image);
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAge() {
        return age;
    }
}
