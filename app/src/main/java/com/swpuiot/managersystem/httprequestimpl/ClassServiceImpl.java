package com.swpuiot.managersystem.httprequestimpl;

import okhttp3.RequestBody;

/**
 * Created by DELL on 2018/4/30.
 *
 * @GET("/get/all") Call<ResponseBody> getAllCLass(@Query("id") int id);
 * @POST("/class") Call<ResponseBody> addClass(@Body RequestBody body);
 * @DELETE("/class") Call<ResponseBody> deleteClass(@Body RequestBody body);
 */
public class ClassServiceImpl {
    public void getAllClass(long id){}

    public void addClass(RequestBody body){}

    public void deleteClass(RequestBody body){}
}
