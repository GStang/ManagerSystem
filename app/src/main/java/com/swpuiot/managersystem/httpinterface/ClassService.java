package com.swpuiot.managersystem.httpinterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/4/29.
 */
public interface ClassService {

    @GET("/get/all")
    Call<ResponseBody> getAllCLass(@Query("id") long id);

    @GET("/get/allClass")
    Call<ResponseBody> getAllCLass();
    @POST("/class")
    Call<ResponseBody> addClass(@Body RequestBody body);

    @DELETE("/class")
    Call<ResponseBody> deleteClass(@Body RequestBody body);

    @GET("/get/allByUserId")
    Call<ResponseBody> getAllClassByUserID(@Query("id") long id);

    @GET("/get/getAllTeachersClass")
    Call<ResponseBody> getAllTeachersClss(@Query("id") long teachersid);
}
