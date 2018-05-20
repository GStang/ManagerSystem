package com.swpuiot.managersystem.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassManagerAdapter;
import com.swpuiot.managersystem.adapter.SignInformationAdapter;
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.entity.AttendanceKey;
import com.swpuiot.managersystem.entity.HttpResult;
import com.swpuiot.managersystem.entity.Leave;
import com.swpuiot.managersystem.entity.StudentAndClassInfo;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.httpinterface.GradeService;
import com.swpuiot.managersystem.httpinterface.LeaveService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

public class StuClassManagerActivity extends AppCompatActivity {

    @BindView(R.id.btn_askleave)
    Button askforleave;

    @BindView(R.id.btn_attendence)
    Button attendence;
    @BindView(R.id.tv_stu_class_manager_none)
    TextView none;

    @BindView(R.id.tv_grade)
    TextView grade;

    @BindView(R.id.rv_attendance_log)
    RecyclerView attendance_record;
    ArrayList<Attendance> list = new ArrayList<>();
    SignInformationAdapter adapter;
    int mYear;
    int mMonth;
    int mDay;
    //班级ID
    long cid;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_class_manager);
        ButterKnife.bind(this);
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        Intent intent = getIntent();
        cid = intent.getLongExtra("stuClass", 0);
        adapter = new SignInformationAdapter(this, list, 1);
        attendance_record.setLayoutManager(new LinearLayoutManager(this));
        attendance_record.setAdapter(adapter);
        getAttendanceRecord();
        getGrade();
        getPermission();
        getKey();
        getLocation();

    }

    String GDkey = "";
    Location location = null;
    double jingdu;
    double weidu;
    public void getLocation() {
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(StuClassManagerActivity.this, "当前无法进行定位，请打开GPS和定位权限", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(StuClassManagerActivity.this, "没有位置权限，请在设置中打开", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            Log.e("location", "latitude is " + location.getLatitude() + "longitude is " + location.getLongitude());
            jingdu = location.getLongitude();
            weidu = location.getLatitude();
        }
    }

    public void getKey() {
        try {
            Properties properties = new Properties();
            InputStream stream = this.getAssets().open("base.properties");
            properties.load(stream);
            GDkey = (String) properties.get("key");
            stream.close();
            properties.clear();

        } catch (IOException e) {
            e.printStackTrace();

        }

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

        }
    };

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

    public void getPermission() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[2];
                permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
                permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.requestPermissions(permissions, 1);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void getGrade() {
        retrofit.create(GradeService.class).getUserGrade(MyUser.getUser().getId(), cid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200)
                    if (response.body() != null) {
                        try {
                            String s = response.body().string();
                            grade.setText(s);
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

    public void getAttendanceRecord() {
        retrofit.create(AttendanceService.class).getSomeoneAttendance(MyUser.getUser().getId(), cid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Attendance[] attendances = mapper.readValue(s, Attendance[].class);
                    for (Attendance e :
                            attendances) {
                        list.add(e);
                    }
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list.size() != 0) {
                    none.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    Retrofit retrofit = RetrofitUtil.getRetrofit();
    String s;
    Leave leave = new Leave();
    TextView time;
    EditText reason;
    Gson gson = new Gson();
    EditText invalidNumber;
    StudentAndClassInfo info = new StudentAndClassInfo();
    Attendance attandance = new Attendance();
    AttendanceKey key = new AttendanceKey();
    ObjectMapper mapper = new ObjectMapper();
    String str;

    @OnClick(R.id.btn_attendence)
    public void attendance() {
        final Dialog mdialog = new AlertDialog.Builder(this)
                .setView(R.layout.attendencepassword)
                .setPositiveButton(R.string.positivebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = invalidNumber.getText().toString() + "";
                        attandance.setAttend("出席");
                        key.setId(MyUser.getUser().getId());
                        key.setcNo(cid);
                        attandance.setId(key);
                        info.setAttendance(attandance);
                        info.setInvalidNumber(s);
                        info.setJingdu(jingdu);
                        info.setWeidu(weidu);
                        try {
                            str = mapper.writeValueAsString(info);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        attendanceNet(str);
                    }

                }).setNegativeButton(R.string.negitavebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        mdialog.setTitle("请填写签到码");
        mdialog.setCancelable(true);
        mdialog.show();
        invalidNumber = (EditText) mdialog.findViewById(R.id.et_invalid_number);
    }

    public void attendanceNet(final String s) {
//        System.out.println(s);
        retrofit.create(AttendanceService.class).checkAttendance(RequestBody.create(MediaType.parse("application/json"), s)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code() + "Test");
                if (response.code() == 200) {
                    try {
                        String result = response.body().string();
                        Toast.makeText(StuClassManagerActivity.this, result, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.btn_askleave)
    public void askleave() {

        final Dialog mdialog = new AlertDialog.Builder(this)
                .setView(R.layout.askforleave)
                .setPositiveButton(R.string.positivebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String rea = reason.getText().toString();
//                        Toast.makeText(StuClassManagerActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        leave.setReason(reason.getText().toString());
                        leave.setUid(MyUser.getUser().getId());
                        leave.setResult("待审批");
                        leave.setCid(cid);
                        leave.setDate("2018-5-3");
                        initLeave();

//                        leave.setDate(new Date());
                    }

                }).setNegativeButton(R.string.negitavebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        mdialog.setTitle("填写请假信息");
        mdialog.setCancelable(true);
        mdialog.show();
        reason = (EditText) mdialog.findViewById(R.id.et_reason);
        time = (TextView) mdialog.findViewById(R.id.et_time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StuClassManagerActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                Toast.makeText(StuClassManagerActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void initLeave() {
        String temp = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            temp = mapper.writeValueAsString(leave);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        s = gson.toJson(leave);
        System.out.println(temp);

        retrofit.create(LeaveService.class).addLeave(RequestBody.create(MediaType.parse("application/json"), temp)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200)
                    Toast.makeText(StuClassManagerActivity.this, "请假申请成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StuClassManagerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            time.setText(days);
        }
    };

}
