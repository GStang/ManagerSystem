package com.swpuiot.managersystem.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 2018/4/26.
 */
public class RetrofitUtil {



    private static Retrofit.Builder builder = new Retrofit.Builder();
    private static Retrofit retrofit = builder
            .baseUrl("http://123.206.130.39:8080")
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
