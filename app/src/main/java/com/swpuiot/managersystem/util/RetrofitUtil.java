package com.swpuiot.managersystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 2018/4/26.
 */
public class RetrofitUtil {
    {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("/properties/base.properties"));
            String s = (String) properties.get("ip");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private static Retrofit.Builder builder = new Retrofit.Builder();
    private static Retrofit retrofit = builder
            .baseUrl("http://10.24.39.77:8080")
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
