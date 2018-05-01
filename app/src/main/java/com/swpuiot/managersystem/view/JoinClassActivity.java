package com.swpuiot.managersystem.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.UserClassTypeAdapter;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.UserClass;
import com.swpuiot.managersystem.entity.UserClassKey;
import com.swpuiot.managersystem.httpinterface.UserClassService;
import com.swpuiot.managersystem.httpinterface.UserService;
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

public class JoinClassActivity extends AppCompatActivity {
    @BindView(R.id.tv_classname)
    TextView className;
    @BindView(R.id.tv_teacher)
    TextView teacher;
    @BindView(R.id.btn_join)
    Button join;
    Class myclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        myclass = (Class) intent.getSerializableExtra("class");
        String teacherName = getTeacherName(myclass.getcId());
        teacher.setText(teacherName);
        className.setText(myclass.getcName());

    }

    UserClass userclass = new UserClass();
    Retrofit retrofit = RetrofitUtil.getRetrofit();

    @OnClick(R.id.btn_join)
    public void joinOnClick() {
        initEntity();
        Gson gson = new Gson();
        String s = gson.toJson(userclass);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        retrofit.create(UserClassService.class).addToClass(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                Toast.makeText(JoinClassActivity.this, "加入课堂成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void initEntity() {
        UserClassKey key = new UserClassKey();
        key.setCno(myclass.getcNo());
        key.setId(MyUser.getUser().getId());
        userclass.setId(key);
//        userclass.setId(MyUser.getUser().getId());
    }

    public String getTeacherName(Long id) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        final String[] s = new String[1];
        retrofit.create(UserService.class).getTeacherName(123).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.code());
                    s[0] = response.body().string();
                    System.out.println(s[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String s = response.message();
                System.out.println(s);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return s[0];
    }
}
