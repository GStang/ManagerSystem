package com.swpuiot.managersystem.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.gson.Gson;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.Leave;
import com.swpuiot.managersystem.httpinterface.LeaveService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class StuClassManagerActivity extends AppCompatActivity {

    @BindView(R.id.btn_askleave)
    Button askforleave;

    Button attendence;
    Button attendenceRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_class_manager);
        ButterKnife.bind(this);

    }

    Retrofit retrofit = RetrofitUtil.getRetrofit();
    String s;
    Leave leave = new Leave();

    @OnClick(R.id.btn_askleave)
    public void askleave() {

        // TODO: 2018/5/2 一个Dialog,填写请假信息.
        Dialog dialog = new AlertDialog.Builder(this).setView(R.layout.askforleave).setPositiveButton(R.string.positivebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton(R.string.negitavebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        dialog.setTitle("填写请假信息");
        dialog.setCancelable(true);


    }

    public void initLeave() {
        Gson gson = new Gson();
        s = gson.toJson(leave);
        System.out.println(s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        retrofit.create(LeaveService.class).addLeave(body);
    }


}
