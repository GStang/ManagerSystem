package com.swpuiot.managersystem.entity;

import java.io.Serializable;


public class AttendanceKey implements Serializable {

    private Long id;

    private Long cNo;

    //    @Convert(converter = TimestampLongConverter.class)
    private Long date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getcNo() {
        return cNo;
    }

    public void setcNo(Long cNo) {
        this.cNo = cNo;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AttendanceKey{" +
                "id=" + id +
                ", cNo=" + cNo +
                ", date=" + date +
                '}';
    }
}
