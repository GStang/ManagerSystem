package com.swpuiot.managersystem.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by DELL on 2018/4/26.
 */

public class User implements Serializable{

    /**
     * id : 123456789
     * name : tgs
     * sex : 女
     * major : wulianw
     * username : tgs123tgs
     * password : 123456
     * rank : wu
     * email : 420526316@qq.com
     * phone : 13518488885
     * role : 1
     */

    private long id;
    private String name;
    private String sex;
    private String major;
    private String username;
    private String password;
    private String rank;
    private String email;
    private long phone;
    private int role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
