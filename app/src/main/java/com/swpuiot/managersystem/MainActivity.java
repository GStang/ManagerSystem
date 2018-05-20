package com.swpuiot.managersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.swpuiot.managersystem.entity.LoginEntity;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httprequestimpl.LoginRequest;
import com.swpuiot.managersystem.view.FirstActivity;
import com.swpuiot.managersystem.view.MyUser;
import com.swpuiot.managersystem.view.RegisterActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoginRequest.Getresponse {
    @BindView(R.id.btn_register)
    TextView mbtnregister;
    @BindView(R.id.btn_login)
    Button mbtnlogin;
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password)
    EditText password;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
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
        progressBar.setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        if (username.getText().toString().equals("") || password.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        else {
            Long susername = Long.valueOf(username.getText().toString());
            String spassword = password.getText().toString();
            LoginEntity entity = new LoginEntity(susername, spassword);
            String s = gson.toJson(entity);
            LoginRequest request = new LoginRequest(this);
            request.login(s);
        }
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
                Toast.makeText(MainActivity.this, "网络故障，请检查您的网络", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void getresult(Response<ResponseBody> body) {
        codejudge(body);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void failed(Throwable t) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "连接失败，请检查你的网络", Toast.LENGTH_SHORT).show();
    }
}
