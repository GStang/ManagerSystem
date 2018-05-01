package com.swpuiot.managersystem.httpinterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/5/1.
 */
public interface UserService {
    @GET("/get/UserName")
    Call<ResponseBody> getTeacherName(@Query("id") long id);
}
