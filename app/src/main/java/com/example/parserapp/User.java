package com.example.parserapp;

import androidx.annotation.NonNull;

public class User {

    private String name;
    private String ide;
    private String lang;
    private String sex;

    User(String name, String ide, String lang, String sex){
        this.name = name;
        this.ide = ide;
        this.lang = lang;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @NonNull
    @Override
    public  String toString(){
        return "Имя: " + name + "; IDE: " + ide + "; Lang: " + lang + "; Sex: " + sex;
    }
}