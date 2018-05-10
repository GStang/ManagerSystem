package com.swpuiot.managersystem.httpinterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/4/29.
 */
public interface GradeService {
    @GET("/grade")
    Call<ResponseBody> getUserGrade(@Query("userId") Long userId, @Query("classId") Long classId);

    @GET("grade/addAll")
    Call<ResponseBody> addUserGradeByAttendance(@Query("classId") Long classId);
}

