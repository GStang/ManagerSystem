package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.SignInformationAdapter;
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInformationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SignInformationAdapter adapter;
    private ArrayList<Attendance> list = new ArrayList<Attendance>();
    private long time;
    private long cId;
    @BindView(R.id.tv_sign_information_none)
    TextView none;
    @BindView(R.id.tv_total_count)
    TextView totalCount;
    @BindView(R.id.tv_sign_count)
    TextView signCountOut;
    @BindView(R.id.tv_leave_count)
    TextView leaveCountOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_information);
        ButterKnife.bind(this);
        time = getIntent().getLongExtra("signTime", 0L);
        cId = getIntent().getLongExtra("signID", 0L);
        getList();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_sign_information);
        adapter = new SignInformationAdapter(this, list, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    int sumPeople = 0;
    int signCount = 0;
    int leaveCount = 0;

    private void getList() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        AttendanceService service = retrofit.create(AttendanceService.class);
        service.getSometimeAttendence(cId, time).enqueue(new Callback<ResponseBody>() {
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
                            sumPeople = results.length;
                            for (int i = 0; i < results.length; i++) {
                                if (results[i].getAttend().equals("请假"))
                                    leaveCount++;
                                if (results[i].getAttend().equals("出席"))
                                    signCount++;
                                list.add(results[i]);
                            }
                            totalCount.setText("总人数：" + sumPeople);
                            signCountOut.setText("已签到：" + signCount);
                            leaveCountOut.setText("请假数：" + leaveCount);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
}
