package com.example.social;

import java.io.Serializable;

public class PersonalInformation implements Serializable {
    public static final String DATABASE_TABLE = "PERSONAL_INFORMATION_TABLE";

    public static final String KEY_ID = "_id";
    public static final String KEY_NA = "name";
    public static final String KEY_GR = "graph";
    public static final String KEY_AB = "about";
    public static final String KEY_CO = "college";
    public static final String KEY_CI = "city";
    public static final String KEY_AG = "age";
    public static final String KEY_GE = "gender";
    public static final String KEY_IN = "interest";
    public static final String KEY_PE = "personality";

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

    public PersonalInformation(){}

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

    public PersonalInformation(String info){
        String[] tokens = info.split(",");
        this.id = tokens[0];
        this.name = tokens[1];
        this.age = tokens[2];
        this.gender = tokens[3];
        this.college = tokens[4];
        this.city = tokens[5];
        this.about = tokens[6];
        this.interest = tokens[7];
        this.personality = tokens[8];
        this.graph = tokens[9];
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

    public  String getGraph(String graph){return graph;}
    public String toString(){
        String ret =    "Id = " + this.id + "\n" +
                        "Name = " + this.name + "\n" +
                        "age = " + this.age + "\n" +
                        "gender = " + this.gender + "\n" +
                        "college = " + this.college + "\n" +
                        "city = " + this.city + "\n" +
                        "about = " + this.about + "\n" +
                        "interest = " + this.interest + "\n" +
                        "personality = " + this.personality + "\n" +
                        "graph URL = " + this.graph ;
        return ret;
    }
    public String getAllInString(){
        String s = "";
        s += id + ",";
        s += name + ",";
        s += age + ",";
        s += gender + ",";
        s += college + ",";
        s += city + ",";
        s += about + ",";
        s += interest + ",";
        s += personality + ",";
        s += graph;
        return s;
    }
}
