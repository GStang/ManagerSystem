package com.swpuiot.managersystem.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ChooseCourseAdapter;
import com.swpuiot.managersystem.adapter.ClassAdapter;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.CourseResponse;
import com.swpuiot.managersystem.httpinterface.CourseService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *  这个是老师创建班级的时候选择课程的界面
 */
public class ChooseCourseActivity extends AppCompatActivity {

    @BindView(R.id.rv_class)
    RecyclerView courselist;
    @BindView(R.id.fab_add)
    FloatingActionButton add;
    @BindView(R.id.tv_choose_course_none)
    TextView none;
    List list = new ArrayList<>();
    ChooseCourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecourse);
        ButterKnife.bind(this);

        adapter = new ChooseCourseAdapter(this, list);
        courselist.setLayoutManager(new LinearLayoutManager(this));
        courselist.setAdapter(adapter);
        getCourse();
    }

    /**
     * 跳转到创建CourseActivity*/
    @OnClick(R.id.fab_add)
    public void AddCouseOnclick() {
        Intent intent = new Intent(this, CreateCourseActivity.class);
        startActivity(intent);

    }
    private void getCourse() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        CourseService service = retrofit.create(CourseService.class);
        service.findAllCourse().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if (response.code() == 404) {
                    System.out.println(response.message());
                }
                if (response.code() == 200) {
                    try {
//                        System.out.println(response.body().string());
                        String s = response.body().string();
                        System.out.println(s);
                        Gson gson = new Gson();
                        CourseResponse res = gson.fromJson(s, CourseResponse.class);
//                        System.out.println(res.getData().get(0).getId());
                        showCourse(res.getData());
//                        System.out.println("success");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (list.size()!=0){
                    none.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void showCourse(List<CourseResponse.DataBean> list) {
        adapter.changelist(list);
        adapter.notifyDataSetChanged();
    }
}
