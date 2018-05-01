package com.swpuiot.managersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swpuiot.managersystem.entity.LoginEntity;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httpinterface.LoginService;
import com.swpuiot.managersystem.httprequestimpl.LoginRequest;
import com.swpuiot.managersystem.util.GsonUtils;
import com.swpuiot.managersystem.util.RetrofitUtil;
import com.swpuiot.managersystem.view.FirstActivity;
import com.swpuiot.managersystem.view.MyUser;
import com.swpuiot.managersystem.view.RegisterActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LoginRequest.Getresponse {
    @BindView(R.id.btn_register)
    TextView mbtnregister;
    @BindView(R.id.btn_login)
    Button mbtnlogin;
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password)
    EditText password;

    Unbinder unBinder;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unBinder = ButterKnife.bind(this);
        intent = new Intent(this, FirstActivity.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife
        unBinder.unbind();
    }

    @OnClick(R.id.btn_register)
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void login() {

        Long susername = Long.valueOf(username.getText().toString());
        String spassword = password.getText().toString();
        Gson gson = new Gson();
        LoginEntity entity = new LoginEntity(susername, spassword);
        String s = gson.toJson(entity);
        LoginRequest request = new LoginRequest(this);
        request.login(s);

    }

    Gson gson = new Gson();

    private void codejudge(Response<ResponseBody> response) {
        if (response.code() == 401) {//密码不匹配
            Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
        } else if (response.code() == 404) {
            //帐号不正确
            Toast.makeText(MainActivity.this, "帐号不存在", Toast.LENGTH_SHORT).show();

        } else {
            try {
                if (response.code() == 200) {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    User user = gson.fromJson(response.body().string(), User.class);
                    MyUser.setUser(user);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
                if (response.body() != null) {
                    System.out.println(response.body().string());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getresult(Response<ResponseBody> body) {
        codejudge(body);

    }
}
