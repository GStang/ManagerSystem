package com.swpuiot.managersystem.entity;

import java.io.Serializable;

/**
 * Created by DELL on 2018/4/29.
 */
public class Course implements Serializable {


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

    public Course() {
    }

    public Course(Long id, String name, String english) {
        this.id = id;
        this.name = name;
        this.english = english;
    }
}