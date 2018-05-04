package com.swpuiot.managersystem.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassManagerAdapter;
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.HttpResult;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClassManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClassManagerAdapter adapter;
    private ArrayList<Long> list = new ArrayList<>();
    private Long cid;
    private TextView noneText;
    @BindView(R.id.fab_sign)
    public FloatingActionButton sign;

    @BindView(R.id.fab_leavepass)
    public FloatingActionButton leavePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manager);
        ButterKnife.bind(this);
        cid = getIntent().getLongExtra("teaClass", 0L);
        System.out.println(cid);

        getList();

        noneText = (TextView) findViewById(R.id.tv_class_manager_none);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_class_manager);
        adapter = new ClassManagerAdapter(list, this, cid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getList() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        AttendanceService service = retrofit.create(AttendanceService.class);
        service.getCount(cid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    try {
                        if (response.body() != null) {
                            String s = response.body().string();
                            System.out.println(s);
                            ObjectMapper mapper = new ObjectMapper();
                            Attendance[] results = mapper.readValue(s, Attendance[].class);
                            for (int i = 0; i < results.length; i++) {
                                list.add(results[i].getId().getDate());
                            }
                        }

                        if (list.size() != 0) {
                            noneText.setVisibility(View.INVISIBLE);
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

    AlertDialog dialog;
    Retrofit retrofit = RetrofitUtil.getRetrofit();

    @OnClick(R.id.fab_sign)
    public void startSign() {
        retrofit.create(AttendanceService.class).getValidateNumber(cid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String s = response.body().string();
                        dialog = new AlertDialog.Builder(ClassManagerActivity.this).setTitle("您的签到号码为").setMessage(s).create();
                        dialog.show();
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

    @OnClick(R.id.fab_leavepass)
    public void leavePass(){
        Intent intent = new Intent(this, LeavePassActivity.class);
        intent.putExtra("class", cid);
        this.startActivity(intent);
    }
}

