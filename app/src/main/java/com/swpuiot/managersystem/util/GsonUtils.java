package com.swpuiot.managersystem.util;

import com.google.gson.Gson;

/**
 * Created by DELL on 2018/4/26.
 */
public class GsonUtils {
    static Gson gson = new Gson();

    private GsonUtils() {

    }

    public static Gson getGson() {
        return gson;
    }
}
