package com.swpuiot.managersystem.httpinterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by DELL on 2018/4/26.
 */

public interface LoginService {
    @POST("/user/login")
    Call<ResponseBody> login(@Body RequestBody body);

}
