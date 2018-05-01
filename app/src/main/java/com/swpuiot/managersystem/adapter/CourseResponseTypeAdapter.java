package com.swpuiot.managersystem.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.swpuiot.managersystem.entity.CourseResponse;

import java.io.IOException;

/**
 * Created by DELL on 2018/4/30.
 */
public class CourseResponseTypeAdapter extends TypeAdapter<CourseResponse> {
    @Override
    public void write(JsonWriter out, CourseResponse value) throws IOException {

    }

    @Override
    public CourseResponse read(JsonReader in) throws IOException {
        CourseResponse courseResponse = new CourseResponse();
        in.beginObject();
//        courseResponse.setStatus(in.nextInt());
//        in.beginArray();
//        courseResponse.setData(in.next);
//        in.endArray();
        in.endObject();
        return courseResponse;
    }
}
