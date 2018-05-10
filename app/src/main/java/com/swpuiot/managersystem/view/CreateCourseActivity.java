package com.swpuiot.managersystem.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.CourseTypeAdapter;
import com.swpuiot.managersystem.entity.Course;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.httpinterface.CourseService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateCourseActivity extends AppCompatActivity {
    Retrofit retrofit = RetrofitUtil.getRetrofit();
    @BindView(R.id.et_id)
    EditText courseid;
    @BindView(R.id.et_coursename)
    EditText coursename;
    @BindView(R.id.et_englishname)
    EditText englishname;
    @BindView(R.id.btn_addcourse)
    Button addCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        ButterKnife.bind(this);
    }

    Course course = new Course();

    public boolean initcourse() {
        if (courseid.getText().toString().equals("") || englishname.getText().toString().equals("") || coursename.getText().toString().equals(""))
            return false;
        course.setId(Long.valueOf(courseid.getText().toString()));
        course.setEnglish(englishname.getText().toString());
        course.setName(coursename.getText().toString());
        return true;
    }

    @OnClick(R.id.btn_addcourse)
    public void addCourse() {
        if (!initcourse()) {
            Toast.makeText(CreateCourseActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(Course.class, new CourseTypeAdapter()).create();
        String s = gson.toJson(course);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        retrofit.create(CourseService.class).addCourse(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    Toast.makeText(CreateCourseActivity.this, "添加课程成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateCourseActivity.this, CreateClassActivity.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateCourseActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
