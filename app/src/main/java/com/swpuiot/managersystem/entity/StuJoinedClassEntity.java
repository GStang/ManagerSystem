package com.swpuiot.managersystem.entity;

/**
 * Created by DELL on 2018/4/30.
 */
public class StuJoinedClassEntity {


    /**
     * cNo : 1
     * cName : 操作系统一班
     * cId : 123
     * courseId : 1
     */

    private int cNo; //班级号
    private String cName; //班级名字
    private int cId; // 用户ID
    private int courseId; //课程ID

    public int getCNo() {
        return cNo;
    }

    public void setCNo(int cNo) {
        this.cNo = cNo;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public int getCId() {
        return cId;
    }

    public void setCId(int cId) {
        this.cId = cId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
