package com.swpuiot.managersystem.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassAdapter;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.StuJoinedClassEntity;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstActivity extends AppCompatActivity {
    @BindView(R.id.fab_add)
    FloatingActionButton add;
    @BindView(R.id.rv_class)
    RecyclerView classlist;
    private User user;
    List<StuJoinedClassEntity> userBeanList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private SystemBarTintManager tintManager;
    private NavigationView navigationView;
    ImageView menu;
    ClassAdapter adapter;
    private MyUser myUser;
    private TextView name;
    private TextView note;
    @BindView(R.id.sr_refresh)
    public  SwipeRefreshLayout refreshLayout;

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.setRefreshing(true);
        if (user.getRole() == 0)
            getclass();//学生
        if (user.getRole() == 1)
            getTeacherClass();//老师
    }

    public User getUser() {
        return user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ButterKnife.bind(this);
        classlist.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassAdapter(this,userBeanList);
        classlist.setAdapter(adapter);
        user = (User) getIntent().getSerializableExtra("user");
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (user.getRole() == 0)
                    getclass();//学生
                if (user.getRole() == 1)
                    getTeacherClass();//老师
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_first);
        navigationView = (NavigationView) findViewById(R.id.nva);

        assert navigationView != null;
        MenuItem itemID = navigationView.getMenu().findItem(R.id.menu_id);
        itemID.setTitle(MyUser.getUser().getId()+"");
        MenuItem itemPhone = navigationView.getMenu().findItem(R.id.menu_phone);
        itemPhone.setTitle(MyUser.getUser().getPhone()+"");
        MenuItem itemEmail = navigationView.getMenu().findItem(R.id.menu_email);
        itemEmail.setTitle(MyUser.getUser().getEmail());
        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_tv_first_name);
        name.setText(MyUser.getUser().getName());


        menu= (ImageView) findViewById(R.id.main_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //item.setChecked(true);
                Toast.makeText(FirstActivity.this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }


    /**
     * 教师用户的创建课程
     * 学生用户的加入课程
     */
    @OnClick(R.id.fab_add)
    public void onAdd() {
        //教师用户跳转到选择课程的界面进行创建班级
        if (user.getRole() == 1) {
            Intent intent = new Intent(FirstActivity.this, ChooseCourseActivity.class);
            startActivity(intent);

        }else{
            //学生用户挑战到选择班级的界面
            Intent intent = new Intent(FirstActivity.this, StuChooseClassActivity.class);
            startActivity(intent);

        }
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
                   String s =  response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    userBeanList  =  Arrays.asList(mapper.readValue(s, StuJoinedClassEntity[].class));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
//            Log.e("Test", userBeanList.get(0).getCno()+"");
//                System.out.println("cNO"+userBeanList.get(0).getcNo());
            adapter.changeList(userBeanList);
            refreshLayout.setRefreshing(false);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });
    }



    public void getclass() {

        service.getAllClassByUserID(user.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    try {
                        String s =  response.body().string();
                        ObjectMapper mapper = new ObjectMapper();
                        userBeanList  =  Arrays.asList(mapper.readValue(s, StuJoinedClassEntity[].class));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                Log.e("Test", userBeanList.get(0).getCno()+"");
//                System.out.println("cNO"+userBeanList.get(0).getcNo());
                adapter.changeList(userBeanList);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                refreshLayout.setRefreshing(false);

            }
        });
    }

}
