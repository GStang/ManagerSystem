package com.swpuiot.managersystem.entity;

/**
 * Created by DELL on 2018/5/1.
 */

import java.io.Serializable;

/**
 * Created by DELL on 2018/4/30.
 */

public class UserClassKey implements Serializable {
    private Long id;
    private Long cno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }

}