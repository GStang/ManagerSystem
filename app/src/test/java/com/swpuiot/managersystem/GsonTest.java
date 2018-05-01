package com.swpuiot.managersystem;

import com.google.gson.Gson;
import com.swpuiot.managersystem.entity.CourseResponse;

import org.junit.Test;

/**
 * Created by DELL on 2018/4/30.
 */
public class GsonTest {
    @Test
    public void fun1(){
        String s = "{\"status\":200,\"data\":[{\"id\":1,\"name\":\"操作系统\",\"english\":\"os\"}]}";
        System.out.println(s);
        Gson gson = new Gson();
        CourseResponse res = gson.fromJson(s, CourseResponse.class);
        System.out.println(res.getData().get(0).getId());
    }
}
