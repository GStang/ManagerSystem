package com.swpuiot.managersystem.entity;


import java.io.Serializable;

public class HttpResult<T> implements Serializable {

    private int status;

    private String msg;

    private T data;

    public HttpResult() {
    }

    public HttpResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public HttpResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public HttpResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

