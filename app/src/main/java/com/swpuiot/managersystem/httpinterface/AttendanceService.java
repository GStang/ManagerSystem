package com.swpuiot.managersystem.httpinterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/5/2.
 */
public interface AttendanceService {
    @GET("/getAll")
    Call<ResponseBody> get(@Query("id") Long id);

    @GET("/validate")
    Call<ResponseBody> getValidateNumber(@Query("id") Long id);

    @POST("/checkAttendance")
    Call<ResponseBody> checkAttendance(@Body RequestBody StudentAndClassInfoBody);

    @GET("/getSomeoneAttendance")
    Call<ResponseBody> getSomeoneAttendance(@Query("id") Long id, @Query("cno") Long cno);
}
