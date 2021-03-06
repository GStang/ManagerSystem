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
    @GET("/attend/getAll")
    Call<ResponseBody> get(@Query("id") Long id);

    @GET("/attend/validate")
    Call<ResponseBody> getValidateNumber(@Query("id") Long id,@Query("jingdu")double jingdu,@Query("weidu")double weidu);

    @POST("/attend/checkAttendance")
    Call<ResponseBody> checkAttendance(@Body RequestBody StudentAndClassInfoBody);

    @GET("/attend/getSomeoneAttendance")
    Call<ResponseBody> getSomeoneAttendance(@Query("id") Long id, @Query("cno") Long cno);

    @GET("/attend/getCount")
    Call<ResponseBody> getCount(@Query("id") Long cno);

    @GET("/attend/getSomeTimeAttendance")
    Call<ResponseBody> getSometimeAttendence(@Query("cid") Long cid, @Query("time") Long time);
}
