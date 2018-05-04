package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.SignInformationAdapter;
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.httpinterface.AttendanceService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInformationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SignInformationAdapter adapter;
    private ArrayList<Attendance>list = new ArrayList<Attendance>();
    private long time;
    private long cId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_information);
        ButterKnife.bind(this);
        time = getIntent().getLongExtra("signTime",0L);
        cId = getIntent().getLongExtra("signID",0L);
        getList();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_sign_information);
        adapter = new SignInformationAdapter(this,list,0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    private void getList() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        AttendanceService service = retrofit.create(AttendanceService.class);
        service.getSometimeAttendence(cId,time).enqueue(new Callback<ResponseBody>() {
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

                                list.add(results[i]);
                            }
                        }
//
//                        if (list.size()!=0){
//                            noneText.setVisibility(View.INVISIBLE);
//                        }
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
