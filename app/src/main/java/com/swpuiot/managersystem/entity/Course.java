package com.swpuiot.managersystem.entity;

/**
 * Created by DELL on 2018/4/29.
 */
public class Course {


    private Long id;
    private String name;
    private String english;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

}