package com.ironlibrary.ironLibraryApp.model;

public class Student{
    private String usn;
    private String name;

    public Student (String usn,String name){
        this.usn = usn;
        this.name = name;
    }
//    Getters
    public String getUsn(){
        return usn;
    }
    public String getName(){
        return name;
    }
//    Setters
    public void setUsn(String usn){
        this.usn = usn;
    }
    public void setName(String name){
        this.name = name;
    }
}