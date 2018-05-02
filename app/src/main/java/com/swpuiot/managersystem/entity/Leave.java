package com.swpuiot.managersystem.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DELL on 2018/5/1.
 */

public class Leave implements Serializable {

    private Long id;
    private String date;
    private Long uid;
    private String result;
    private Long cid;
    private String reason;


    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String  getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", date=" + date +
                ", uid=" + uid +
                ", result='" + result + '\'' +
                ", cid=" + cid +
                ", reason='" + reason + '\'' +
                '}';
    }
}
