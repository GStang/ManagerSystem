package com.swpuiot.managersystem.httpinterface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DELL on 2018/5/2.
 */
public interface LeaveService {
    @POST("/addleave")
    Call<ResponseBody> addLeave(@Body RequestBody Leave);

    /**
     * 通过请假ID查询
     */
    @GET("leave/findbyid")
    Call<ResponseBody> findById(@Query("id") Long id);

    /**
     * 通过用户查询
     */
    @GET("leave/findbyuid")
    Call<ResponseBody> findByUId(@Query("id") Long id);

    /**
     * 通过班级查询请假信息
     */
    @GET("leave/findbycno")
    Call<ResponseBody> findByCon(@Query("id") Long id);

    /**
     * 审批请假的接口
     * status=1是不通过
     * status=2是通过
     */
    @GET("leave/updateStatus")
    Call<ResponseBody> updateStatus(@Query("id") Long id, @Query("status") Long status);

    @GET("leave/waitpass")
    Call<ResponseBody> waitPass(@Query("id") Long id);

}
