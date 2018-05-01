package com.swpuiot.managersystem.view;

import com.swpuiot.managersystem.entity.User;

/**
 * Created by DELL on 2018/4/30.
 */
public class MyUser {
    private static User user = new User();

    public  static User getUser() {
        return user;
    }

    public static void setUser(User muser) {
        user = muser;
    }
}
