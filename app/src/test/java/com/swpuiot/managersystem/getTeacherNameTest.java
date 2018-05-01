package com.swpuiot.managersystem;

import com.swpuiot.managersystem.httpinterface.UserService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DELL on 2018/5/1.
 */
public class getTeacherNameTest {
    @Test
    public void fun() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        retrofit.create(UserService.class).getTeacherName(123).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.code());
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String s = response.message();
                System.out.println(s);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
