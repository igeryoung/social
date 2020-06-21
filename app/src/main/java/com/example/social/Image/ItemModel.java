package com.example.social.Image;

import android.net.Uri;
// Model that stores the attribute of the card
public class ItemModel {

    private String name, city, age;
    private String image;
    //constructor
    public ItemModel(String image, String name, String city, String age) {
        this.image = image;
        this.name = name;
        this.city = city;
        this.age = age;
    }
    //getter and setter
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
