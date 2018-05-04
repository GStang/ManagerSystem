package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.LeaveAdapter;
import com.swpuiot.managersystem.entity.Leave;
import com.swpuiot.managersystem.event.DestoryEvent;
import com.swpuiot.managersystem.event.RegetEvent;
import com.swpuiot.managersystem.httpinterface.LeaveService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

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

public class LeavePassActivity extends AppCompatActivity {

    @BindView(R.id.rv_leaverecord)
    RecyclerView leavepass;
    LeaveAdapter adapter;
    @BindView(R.id.tv_leave_pass_none)
    TextView none;
    List<Leave> list = new ArrayList<>();
    Long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_pass);
        ButterKnife.bind(this);
        adapter = new LeaveAdapter(this, list);
        leavepass.setLayoutManager(new LinearLayoutManager(this));
        leavepass.setAdapter(adapter);
        cid = getIntent().getLongExtra("class", 0);
        EventBus.getDefault().register(this);
    }

    private void getLeave() {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        final ObjectMapper mapper = new ObjectMapper();
        retrofit.create(LeaveService.class).waitPass(cid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                    list.remove()
                    Leave[] leaveWaitPass = mapper.readValue(response.body().string(), Leave[].class);
                    for (Leave e :
                            leaveWaitPass) {
                        list.add(e);
                        System.out.println(e.toString());
                    }
                    adapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list.size()!=0){
                    none.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reGet(RegetEvent messageEvent) {
        System.out.println("reget");
//        getLeave();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getLeave();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new DestoryEvent());
        EventBus.getDefault().unregister(this);
    }
}
