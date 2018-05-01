package com.swpuiot.managersystem.httpinterface;

import com.swpuiot.managersystem.entity.Course;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by DELL on 2018/4/29.
 */
public interface CourseService {
    @POST("/course/add")
     Call<ResponseBody> addCourse(@Body RequestBody course) ;

    @DELETE("/course/id")
     Call<ResponseBody> deleteCourse(Long id) ;

    @GET("/course/id")
     Call<ResponseBody> findCourseById(Long id);

    @GET("/course/name")
     Call<ResponseBody> findCourseByName(String name);

    @GET("/course/all")
     Call<ResponseBody> findAllCourse() ;
}
