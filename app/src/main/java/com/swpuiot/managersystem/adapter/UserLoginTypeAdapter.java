package com.swpuiot.managersystem.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.swpuiot.managersystem.entity.User;

import java.io.IOException;

/**
 * Created by DELL on 2018/4/26.
 */
public class UserLoginTypeAdapter extends TypeAdapter<User> {
    /**
     * id : 123456789
     * name : tgs
     * sex : 女
     * major : wulianw
     * username : tgs123tgs
     * password : 123456
     * rank : wu
     * email : 420526316@qq.com
     * phone : 13518488885
     * role : 1
     */
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("name").value(value.getName());
        out.name("sex").value(value.getSex());
        out.name("major").value(value.getMajor());
        out.name("rank").value(value.getRank());
        out.name("username").value(value.getUsername());
        out.name("password").value(value.getPassword());
        out.name("email").value(value.getEmail());
        out.name("phone").value(value.getPhone());
        out.name("role").value(value.getRole());
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        in.beginObject();
        User user = new User();
        user.setId(in.nextLong());
        user.setName(in.nextName());
        user.setSex(in.nextString());
        user.setMajor(in.nextString());
        user.setRank(in.nextString());
        user.setPassword(in.nextString());
        user.setEmail(in.nextString());
        user.setPhone(in.nextLong());
        user.setRole(in.nextInt());
        in.endObject();
        return null;
    }
}
