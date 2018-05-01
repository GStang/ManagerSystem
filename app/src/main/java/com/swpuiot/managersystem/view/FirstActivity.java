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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

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

public class FirstActivity extends AppCompatActivity {
    @BindView(R.id.fab_add)
    FloatingActionButton add;
    @BindView(R.id.rv_class)
    RecyclerView classlist;
    private User user;
    ArrayList<StuJoinedClassEntity> userBeanList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private SystemBarTintManager tintManager;
    private NavigationView navigationView;
    ImageView menu;
    ClassAdapter adapter;

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
        if (user.getRole() == 0)
            getclass();//学生
        if (user.getRole() == 1)
            getTeacherClass();//老师

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_first);
        navigationView = (NavigationView) findViewById(R.id.nva);
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


    private void initWindow() {//初始化窗口属性，让状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            int statusColor = Color.parseColor("#1976d2");
            tintManager.setStatusBarTintColor(statusColor);
            tintManager.setStatusBarTintEnabled(true);
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
            adapter.changeList(userBeanList);
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
                adapter.changeList(userBeanList);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
