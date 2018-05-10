package com.swpuiot.managersystem.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.swpuiot.managersystem.httpinterface.GradeService;
import com.swpuiot.managersystem.util.ExcelUtils;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.File;
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
    @BindView(R.id.fab_grade)
    public FloatingActionButton grade;
    @BindView(R.id.fab_leavepass)
    public FloatingActionButton leavePass;
    @BindView(R.id.fab_out)
    public FloatingActionButton out;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
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

    Attendance[] results2;
    @OnClick(R.id.fab_out)
    public void putoutExcel() {
        progressBar.setVisibility(View.VISIBLE);
        // 首先判断设备是否挂载SDCard
        boolean isMounted = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (isMounted) {
            if (results == null || results.length == 0) {
                Toast.makeText(ClassManagerActivity.this, "没有数据，不能导出", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            ExcelUtils.initExcel("test.xls", results.length);
            for (int i = 0;i<results.length;i++){
                final int finalI = i;
                RetrofitUtil.getRetrofit().create(AttendanceService.class)
                        .getSometimeAttendence(results[0].getId().getcNo(), results[i].getId().getDate()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            try {
                                if (response.body() != null) {
                                    String s = response.body().string();
                                    System.out.println(s);
                                    ObjectMapper mapper = new ObjectMapper();
                                    results2 = mapper.readValue(s, Attendance[].class);
                                    Log.e("写入","写入");
                                    ExcelUtils.writeObjListToExcel(results2,"test.xls",ClassManagerActivity.this, finalI);
                                }
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
//            ExcelUtils.writeObjListToExcel();
            Toast.makeText(ClassManagerActivity.this, "操作成功，请在SD卡目录中查看导出信息", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            Log.d("SDCard错误", "未安装SDCard！");
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.fab_grade)
    public void gradeClick() {
        new AlertDialog.Builder(this).setTitle("确定给所有学生打出成绩？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        retrofit.create(GradeService.class).addUserGradeByAttendance(cid).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200)
                                    Toast.makeText(ClassManagerActivity.this, "更新成绩成功", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(ClassManagerActivity.this, "更新成绩失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    Attendance[] results;

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
                            results = mapper.readValue(s, Attendance[].class);
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
    public void leavePass() {
        Intent intent = new Intent(this, LeavePassActivity.class);
        intent.putExtra("class", cid);
        this.startActivity(intent);
    }
}

