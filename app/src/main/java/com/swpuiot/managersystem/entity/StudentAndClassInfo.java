package com.swpuiot.managersystem.entity;

/**
 * Created by DELL on 2018/5/2.
 */
public class StudentAndClassInfo {
    private Attendance attendance;

    private String invalidNumber;

    private double jingdu;

    private double weidu;

    public double getJingdu() {
        return jingdu;
    }

    public void setJingdu(double jingdu) {
        this.jingdu = jingdu;
    }

    public double getWeidu() {
        return weidu;
    }

    public void setWeidu(double weidu) {
        this.weidu = weidu;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public String getInvalidNumber() {
        return invalidNumber;
    }

    public void setInvalidNumber(String invalidNumber) {
        this.invalidNumber = invalidNumber;
    }

}
