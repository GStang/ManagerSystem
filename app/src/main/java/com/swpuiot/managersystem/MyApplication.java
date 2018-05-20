package com.swpuiot.managersystem;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.swpuiot.managersystem.util.RetrofitUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by DELL on 2018/4/30.
 */
public class MyApplication extends Application {
    private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Properties properties = new Properties();
            InputStream stream = this.getAssets().open("base.properties");
            properties.load(stream);
            Log.e("gethost", "success");
            String s = (String) properties.get("host");
            System.out.println(s);
            stream.close();
            properties.clear();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }

}
