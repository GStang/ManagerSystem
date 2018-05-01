package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.CourseTypeAdapter;
import com.swpuiot.managersystem.entity.Course;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateCourseActivity extends AppCompatActivity {
    Retrofit retrofit = RetrofitUtil.getRetrofit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        initcourse();
        addCourse();
    }

    Course course = new Course();

    public void initcourse() {

    }

    public void addCourse() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Course.class, new CourseTypeAdapter()).create();
        String s = gson.toJson(course);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        retrofit.create(ClassService.class).addClass(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
