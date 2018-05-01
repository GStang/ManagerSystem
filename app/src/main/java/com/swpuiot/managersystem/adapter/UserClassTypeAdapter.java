package com.swpuiot.managersystem.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.swpuiot.managersystem.entity.UserClass;

import java.io.IOException;

/**
 * Created by DELL on 2018/5/1.
 */
public class UserClassTypeAdapter extends TypeAdapter<UserClass> {
    @Override
    public void write(JsonWriter out, UserClass value) throws IOException {
        out.beginObject();
//        out.name("id").value(value.getId());
//        out.name("con").value(value.getCon());
        out.endObject();
    }

    @Override
    public UserClass read(JsonReader in) throws IOException {
        return null;
    }
}
