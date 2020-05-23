package com.example.social;

public class PersonalInformation {
    private String id;
    private String name;
    private String graph;
    private String about;
    private String college;
    private String city;
    private String age;
    private String gender;
    private String interest;
    private String personality;

    public PersonalInformation(String id, String name, String graph, String about, String college, String city,
                               String age, String gender, String interest, String personality) {
        this.id = id;
        this.name = name;
        this.graph = graph;
        this.about = about;
        this.college = college;
        this.city = city;
        this.age = age;
        this.gender = gender;
        this.interest = interest;
        this.personality = personality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }
}
