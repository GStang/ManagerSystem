package com.swpuiot.managersystem.entity;

import java.io.Serializable;

/**
 * Created by DELL on 2018/5/1.
 */
public class UserClass implements Serializable {
    private UserClassKey id;

    public UserClassKey getId() {
        return id;
    }

    public void setId(UserClassKey id) {
        this.id = id;
    }
}
