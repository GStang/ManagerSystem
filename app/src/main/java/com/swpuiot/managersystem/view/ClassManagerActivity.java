package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassManagerAdapter;
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClassManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClassManagerAdapter adapter;
    private ArrayList<Long>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manager);
        getList();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_class_manager);
        adapter = new ClassManagerAdapter(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    private void getList(){
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        AttendanceService service = retrofit.create(AttendanceService.class);
        service.get(getIntent().getLongExtra("teaClass",0)).enqueue(new Callback<ResponseBody>() {
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
//                        list = new ArrayList<Class>();
                        //加强for循环遍历JsonArray
                        for (JsonElement user : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            Attendance userBean = gson.fromJson(user, Attendance.class);
//                            list.add(userBean.get);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    }

