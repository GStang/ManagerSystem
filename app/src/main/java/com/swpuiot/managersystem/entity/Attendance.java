package com.swpuiot.managersystem.entity;



public class Attendance {

    private AttendanceKey id;

    private String attend;

    public AttendanceKey getId() {
        return id;
    }

    public void setId(AttendanceKey id) {
        this.id = id;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", attend='" + attend + '\'' +
                '}';
    }
}

