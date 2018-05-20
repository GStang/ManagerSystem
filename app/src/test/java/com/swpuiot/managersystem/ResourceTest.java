package com.swpuiot.managersystem;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by DELL on 2018/5/18.
 */
public class ResourceTest {
    @Test
    public void get() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("/assets/base.properties"));

            String s = (String) properties.get("host");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
