package com.swpuiot.managersystem.entity;

/**
 * Created by DELL on 2018/4/29.
 */
public class Class {

    private Long cNo;
    private String cName;
    private Long cId;
    private Long courseId;

    public Long getcNo() {
        return cNo;
    }

    public void setcNo(Long cNo) {
        this.cNo = cNo;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

}