package com.swpuiot.managersystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DELL on 2018/4/30.
 */
public class StuJoinedClassEntity {


    /**
     * cno : 1
     * cName : 操作系统一班
     * cId : 123
     * courseId : 1
     */

    private long cno;
    @JsonProperty("cName")
    private String cname;
    @JsonProperty("cId")
    private long cId;
    private long courseId;

    public long getCno() {
        return cno;
    }

    public void setCno(long cno) {
        this.cno = cno;
    }

    public String getCName() {
        return cname;
    }

    public void setCName(String cName) {
        this.cname = cName;
    }

    public long getCId() {
        return cId;
    }

    public void setCId(long cId) {
        this.cId = cId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
