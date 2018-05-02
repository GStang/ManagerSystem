package com.swpuiot.managersystem;

import com.google.gson.Gson;
import com.swpuiot.managersystem.entity.Leave;

import org.junit.Test;

import java.util.Date;

/**
 * Created by DELL on 2018/5/2.
 */
public class LeaveJson {
    @Test
    public void fun1(){
        Leave leave = new Leave();
        leave.setId(2L);
        leave.setUid(123L);
        leave.setDate("2018-5-2");
        leave.setResult("待审批");
        leave.setCid(1L);
        leave.setReason("请假一天");
        Gson gson = new Gson();
        String s = gson.toJson(leave);
        System.out.println(s);
    }

}
