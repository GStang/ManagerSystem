package com.swpuiot.managersystem.httpinterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DELL on 2018/4/29.
 */
public interface GradeService {
    @GET("/grade")
    Call<ResponseBody> getUserGrade(Long userId, Long classId);

}

