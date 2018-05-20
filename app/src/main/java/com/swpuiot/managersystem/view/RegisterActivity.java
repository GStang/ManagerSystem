package com.swpuiot.managersystem.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.UserLoginTypeAdapter;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.httpinterface.RegisterService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.util.UUID;

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

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_id)
    EditText id;
    @BindView(R.id.et_name)
    EditText name;
    @BindView(R.id.rg_sex)
    RadioGroup sex;
    @BindView(R.id.et_major)
    EditText major;
    @BindView(R.id.et_rank)
    EditText rank;
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.et_repassword)
    EditText repaswrod;
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_phone)
    EditText phone;
    @BindView(R.id.rg_role)
    RadioGroup role;
    @BindView(R.id.layout)
    LinearLayout layout;
    Retrofit retrofit = RetrofitUtil.getRetrofit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_stu) {
                    layout.setVisibility(View.GONE);
                }else
                    layout.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void register() {
        if (id.getText().toString().equals("") || name.getText().toString().equals("") || major.getText().toString().equals("") || password.getText().toString().equals("")
                || repaswrod.getText().toString().equals("") || email.getText().toString().equals("") || phone.getText().toString().equals("") || phone.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "个人信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.getText().toString().equals(repaswrod.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        Long idvalue = Long.valueOf(id.getText().toString());
        String namevalue = name.getText().toString();
        String sexvalue;
        if (sex.getCheckedRadioButtonId() == R.id.rb_male)
            sexvalue = "男";
        else
            sexvalue = "女";
        String majorvalue = major.getText().toString();
        String rankvalue = rank.getText().toString();
        String passwordvalue = password.getText().toString();
        String repasswordvalue = repaswrod.getText().toString();
        String emailvalue = email.getText().toString();
        Long phonevalue = Long.valueOf(phone.getText().toString());
        int rolevalue;
        if (role.getCheckedRadioButtonId() == R.id.rb_stu) {
            rolevalue = 0;
        }
        else {
            rolevalue = 1;
        }
        final User user = new User();
        user.setId(idvalue);
        user.setName(namevalue);
        user.setSex(sexvalue);
        user.setMajor(majorvalue);
        if (rolevalue==1){
            user.setRank(rankvalue);
        }
        user.setUsername(UUID.fromString(System.currentTimeMillis()+"").toString());
        user.setPassword(passwordvalue);
        user.setEmail(emailvalue);
        user.setPhone(phonevalue);
        user.setRole(rolevalue);
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserLoginTypeAdapter()).create();
        String s = gson.toJson(user);
        System.out.println(s);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        RegisterService service = retrofit.create(RegisterService.class);
        Call<ResponseBody> call = service.register(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 409) {
                    Toast.makeText(RegisterActivity.this,"用户名或电话或邮箱被占用", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 400) {
                    Toast.makeText(RegisterActivity.this, "请填写完整字段", Toast.LENGTH_SHORT).show();
                }
                if (response.body() != null) {
                    try {
                        if (response.code() == 200) {
//                            Intent intent = new Intent(RegisterActivity.this, FirstActivity.class);
//                            intent.putExtra("user", user);
//                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        System.out.println(response.code() + " " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
