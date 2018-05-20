package com.swpuiot.managersystem.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import com.swpuiot.managersystem.entity.LocationEntity;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.httpinterface.GaoDeMapService;
import com.swpuiot.managersystem.httpinterface.GradeService;
import com.swpuiot.managersystem.util.ExcelUtils;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manager);
        ButterKnife.bind(this);
        noneText = (TextView) findViewById(R.id.tv_class_manager_none);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_class_manager);
        cid = getIntent().getLongExtra("teaClass", 0L);
        adapter = new ClassManagerAdapter(list, this, cid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        getList();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
//                   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                          int[] grantResults)
                String[] permissions = new String[2];
                permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
                permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.requestPermissions(permissions, 1);
                }
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.e("location", "latitude is " + location.getLatitude() + "longitude is " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(ClassManagerActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

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
            for (int i = 0; i < results.length; i++) {
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
                                    Log.e("写入", "写入");
                                    ExcelUtils.writeObjListToExcel(results2, "test.xls", ClassManagerActivity.this, finalI);
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

    /**
     * 签到
     */
    @OnClick(R.id.fab_sign)
    public void startSign() {
        String key = "";
        progressBar.setVisibility(View.VISIBLE);
        try {
            Properties properties = new Properties();
            InputStream stream = this.getAssets().open("base.properties");
            properties.load(stream);
            key = (String) properties.get("key");
            stream.close();
            properties.clear();

        } catch (IOException e) {
            e.printStackTrace();

        }


        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(ClassManagerActivity.this, "当前无法进行定位，请打开GPS和定位权限", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);

        Location location = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ClassManagerActivity.this, "没有位置权限，请在设置中打开", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        final String[] locationinf = new String[1];
        if (location != null) {
            Log.e("location", "latitude is " + location.getLatitude() + "longitude is " + location.getLongitude());
            final double jingdu =location.getLongitude();
            final double weidu = location.getLatitude();
            Retrofit.Builder builder = new Retrofit.Builder();
            Retrofit retrofit2 = builder.baseUrl("http://restapi.amap.com").build();
            retrofit2.create(GaoDeMapService.class)
                    .getGeo(key, location.getLongitude() + "," + location.getLatitude())
                    .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() != null) {
                        try {
                            String s = response.body().string();
                            System.out.println(s);
                            ObjectMapper mapper = new ObjectMapper();
                            LocationEntity locationentity = mapper.readValue(s, LocationEntity.class);
                            locationinf[0] = locationentity.getRegeocode().getFormatted_address();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    new AlertDialog.Builder(ClassManagerActivity.this).setTitle("定位成功，请确认是否发起签到")
                            .setMessage("您现在在"+locationinf[0]+"附近")
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }).setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressBar.setVisibility(View.VISIBLE);
                            retrofit.create(AttendanceService.class).getValidateNumber(cid,jingdu,weidu).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.body() != null) {
                                        try {
                                            String s = response.body().string();
                                            new AlertDialog.Builder(ClassManagerActivity.this).setTitle("您的签到号码为").setMessage(s).create().show();
                                            progressBar.setVisibility(View.INVISIBLE);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            progressBar.setVisibility(View.INVISIBLE);

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(ClassManagerActivity.this, "请检查您的网络", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);

                                }
                            });
                        }
                    }).create().show();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }


    }

    @OnClick(R.id.fab_leavepass)
    public void leavePass() {
        Intent intent = new Intent(this, LeavePassActivity.class);
        intent.putExtra("class", cid);
        this.startActivity(intent);
    }
}

