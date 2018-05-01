package com.swpuiot.managersystem.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.StuJoinedClassEntity;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstActivity extends AppCompatActivity {
    //    @BindView(R.id.content_view)
//    FrameLayout content;
    @BindView(R.id.rg_toolbar)
    RadioGroup tab;
    @BindView(R.id.rb_main)
    RadioButton main;
    @BindView(R.id.rb_me)
    RadioButton me;
    FragmentManager manager;
    MainFragment fragment;
    Fragment meFragment;
    private User user;

    public User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra("user");
        manager = getSupportFragmentManager();
        fragment = MainFragment.newInstance();
        meFragment = MeFragment.newInstance();
        manager.beginTransaction().add(R.id.content_view, fragment).add(R.id.content_view, meFragment).hide(meFragment).commit();
        System.out.println(tab);
        tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_me) {
                    manager.beginTransaction().hide(fragment).show(meFragment).commit();
                } else
                    manager.beginTransaction().hide(meFragment).show(fragment).commit();
            }
        });
        if (user.getRole() == 0)
            getclass();//学生
        if (user.getRole() == 1)
            getTeacherClass();//老师
    }

    Retrofit retrofit = RetrofitUtil.getRetrofit();
    ClassService service = retrofit.create(ClassService.class);
    private void getTeacherClass() {
    service.getAllTeachersClss(user.getId()).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            System.out.println(response.code());
            if (response.code() == 200) {
                try {
                    String s = response.body().string();
                    System.out.println(s);
                    JsonParser parser = new JsonParser();
                    //将JSON的String 转成一个JsonArray对象
                    JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                    Gson gson = new Gson();
                    userBeanList = new ArrayList<>();
                    //加强for循环遍历JsonArray
                    for (JsonElement user : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        StuJoinedClassEntity userBean = gson.fromJson(user, StuJoinedClassEntity.class);
                        userBeanList.add(userBean);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fragment.Notidydata(userBeanList);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });

    }

    ArrayList<StuJoinedClassEntity> userBeanList;

    public void getclass() {

        service.getAllClassByUserID(user.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    try {
                        String s = response.body().string();
                        System.out.println(s);
                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                        Gson gson = new Gson();
                        userBeanList = new ArrayList<>();
                        //加强for循环遍历JsonArray
                        for (JsonElement user : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            StuJoinedClassEntity userBean = gson.fromJson(user, StuJoinedClassEntity.class);
                            userBeanList.add(userBean);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                fragment.Notidydata(userBeanList);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
