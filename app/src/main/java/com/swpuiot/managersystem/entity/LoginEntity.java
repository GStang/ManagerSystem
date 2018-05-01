package com.swpuiot.managersystem.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by DELL on 2018/4/26.
 */

public class LoginEntity {
    Long id;
    String password;

    public LoginEntity(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
