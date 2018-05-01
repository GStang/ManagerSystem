package com.swpuiot.managersystem.httprequestimpl;

import com.swpuiot.managersystem.httpinterface.LoginService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DELL on 2018/4/30.
 */
public class LoginRequest {
   public interface Getresponse{
        void getresult(Response<ResponseBody> body);

    }
        Getresponse listener;
    public LoginRequest(Getresponse listener) {
//        listener.getresult();
        this.listener = listener;
    }
//    public void getresult(Response<ResponseBody> body){
//
//    }

    public void login(String s) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), s);
        LoginService service = retrofit.create(LoginService.class);
        Call<ResponseBody> call = service.login(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.getresult(response);
//                getresult(response);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
