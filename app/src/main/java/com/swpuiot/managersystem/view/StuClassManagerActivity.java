package com.swpuiot.managersystem.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.entity.AttendanceKey;
import com.swpuiot.managersystem.entity.HttpResult;
import com.swpuiot.managersystem.entity.Leave;
import com.swpuiot.managersystem.entity.StudentAndClassInfo;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.httpinterface.LeaveService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    Button attendenceRecord;
    int mYear;
    int mMonth;
    int mDay;
    //班级ID
    long cid;

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
                        String s = invalidNumber.getText().toString()+"";
                        attandance.setAttend("出席");
                        key.setId(MyUser.getUser().getId());
                        key.setcNo(cid);
                        attandance.setId(key);
                        info.setAttendance(attandance);
                        info.setInvalidNumber(s);
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
                System.out.println(response.code()+"Test");
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

        // TODO: 2018/5/2 一个Dialog,填写请假信息.
        final Dialog mdialog = new AlertDialog.Builder(this)
                .setView(R.layout.askforleave)
                .setPositiveButton(R.string.positivebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String rea = reason.getText().toString();
                        Toast.makeText(StuClassManagerActivity.this, "Click", Toast.LENGTH_SHORT).show();
//                        initLeave(ti,rea);
                        leave.setReason(reason.getText().toString());
                        leave.setUid(MyUser.getUser().getId());
                        leave.setResult("待审批");
                        leave.setCid(cid);

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
        s = gson.toJson(leave);
        System.out.println(s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        retrofit.create(LeaveService.class).addLeave(body);
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
