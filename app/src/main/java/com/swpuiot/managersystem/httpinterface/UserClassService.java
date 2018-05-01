package com.swpuiot.managersystem.httpinterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/5/1.
 */
public interface UserClassService {
    @POST("/add/userclass")
    Call<ResponseBody> addToClass(@Body RequestBody body);
}
