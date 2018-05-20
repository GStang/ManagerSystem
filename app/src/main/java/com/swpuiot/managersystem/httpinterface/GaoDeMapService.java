package com.swpuiot.managersystem.httpinterface;

import java.security.Key;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/5/18.
 */
public interface GaoDeMapService {
    @GET("/v3/geocode/regeo")
    Call<ResponseBody> getGeo(@Query("key") String key, @Query("location") String address);
}
