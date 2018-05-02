package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.swpuiot.managersystem.entity.User;

import java.util.ArrayList;

public class ClassManagerAdapter extends RecyclerView.Adapter<ClassManagerAdapter.MyViewHolder> {

    private ArrayList<User>list = new ArrayList<User>();
    private Context context;

    public ClassManagerAdapter(Context context,ArrayList<User>list){
        this.list = list;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

