package com.swpuiot.managersystem.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.swpuiot.managersystem.entity.Course;

import java.io.IOException;

/**
 * Created by DELL on 2018/4/29.
 * private Long id;
 * private String name;
 * private String english;
 */
public class CourseTypeAdapter extends TypeAdapter<Course> {
    @Override
    public void write(JsonWriter out, Course value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("name").value(value.getName());
        out.name("english").value(value.getEnglish());
        out.endObject();
    }

    @Override
    public Course read(JsonReader in) throws IOException {
        Course course = new Course();
        in.beginObject();
        course.setId(in.nextLong());
        course.setName(in.nextString());
        course.setEnglish(in.nextString());

        return course;
    }
}
