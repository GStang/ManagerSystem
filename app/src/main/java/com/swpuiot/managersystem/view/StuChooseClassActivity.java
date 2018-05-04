package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ChooseClassAdapter;
import com.swpuiot.managersystem.entity.Class;
import com.swpuiot.managersystem.entity.StuJoinedClassEntity;
import com.swpuiot.managersystem.httpinterface.ClassService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StuChooseClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChooseClassAdapter adapter;
    private ArrayList<Class>list = new ArrayList<Class>();
    @BindView(R.id.tv_stu_choose_class_none)
    TextView none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_choose_class);

        init();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_choose_class);
        adapter = new ChooseClassAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void init(){
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        ClassService service = retrofit.create(ClassService.class);
        service.getAllCLass().enqueue(new Callback<ResponseBody>() {
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
                            Class userBean = gson.fromJson(user, Class.class);
                            list.add(userBean);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
}
