package com.swpuiot.managersystem.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.swpuiot.managersystem.entity.Class;

import java.io.IOException;

/**
 * Created by DELL on 2018/4/29.
 * private Long cNo;
 * private String cName;
 * private Long cId;
 * private Long courseId;
 */
public class ClassTypeAdapter extends TypeAdapter<Class> {


    @Override
    public void write(JsonWriter out, Class value) throws IOException {
        out.beginObject();
        out.name("cNo").value(value.getcNo());
        out.name("cName").value(value.getcName());
        out.name("cId").value(value.getcId());
        out.name("courseId").value(value.getCourseId());
        out.endObject();
    }

    @Override
    public Class read(JsonReader in) throws IOException {
        return null;
    }
}
