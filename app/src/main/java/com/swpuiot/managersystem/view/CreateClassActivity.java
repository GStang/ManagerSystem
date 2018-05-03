package com.swpuiot.managersystem.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassTypeAdapter;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.Course;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;

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

public class CreateClassActivity extends AppCompatActivity {
    @BindView(R.id.btn_addclass)
    Button addClass;
    @BindView(R.id.et_cno)
    EditText classNo;
    @BindView(R.id.et_cname)
    EditText className;
    @BindView(R.id.tv_coursename)
    TextView courseName;
    @BindView(R.id.tv_teacherID)
    TextView teacherID;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra("course");
        teacherID.setText(MyUser.getUser().getId() + "");
        courseName.setText(course.getName());

    }

    com.swpuiot.managersystem.entity.Class myClass = new com.swpuiot.managersystem.entity.Class();
    String s;

    public void initClass() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassTypeAdapter()).create();
        myClass.setCno(Long.valueOf(classNo.getText().toString()));
        myClass.setcName(className.getText().toString());
        myClass.setCourseId(course.getId());
        myClass.setcId(MyUser.getUser().getId());
        s = gson.toJson(myClass);
        System.out.println(s);
    }

    Retrofit retrofit = RetrofitUtil.getRetrofit();
    Gson gson = new Gson();

    @OnClick(R.id.btn_addclass)
    public void addClass() {
        initClass();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        retrofit.create(ClassService.class).addClass(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(CreateClassActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else
                    try {
                        if (response.body() != null)
                            Toast.makeText(CreateClassActivity.this, "添加失败" + response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
