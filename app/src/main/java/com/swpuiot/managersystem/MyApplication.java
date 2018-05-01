package com.swpuiot.managersystem;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by DELL on 2018/4/30.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences per = this.getSharedPreferences("application", MODE_PRIVATE);
        SharedPreferences.Editor editor = per.edit();
        editor.putLong("id", 123456);
    }
}
