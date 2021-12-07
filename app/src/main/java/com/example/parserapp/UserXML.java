package com.example.parserapp;

import androidx.annotation.NonNull;

public class UserXML {
    private String name;
    private String age;

    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(String age){
        this.age = age;
    }

    @NonNull
    public String toString(){
        return  "User: " + name + " - " + age;
    }
}
