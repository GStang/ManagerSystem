package com.swpuiot.managersystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swpuiot.managersystem.R;

import butterknife.ButterKnife;

public class SignInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_information);
        ButterKnife.bind(this);

    }
}
