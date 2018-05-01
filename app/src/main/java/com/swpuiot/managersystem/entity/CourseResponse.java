package com.swpuiot.managersystem.entity;

import java.util.List;

/**
 * Created by DELL on 2018/4/30.
 */
public class CourseResponse {


    /**
     * status : 200
     * data : [{"id":1,"name":"操作系统","english":"os"}]
     */

    private int status;
    /**
     * id : 1
     * name : 操作系统
     * english : os
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private long id;
        private String name;
        private String english;

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }
    }
}
