package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.StuJoinedClassEntity;
import com.swpuiot.managersystem.entity.User;
import com.swpuiot.managersystem.view.ChooseCourseActivity;
import com.swpuiot.managersystem.view.ClassManagerActivity;
import com.swpuiot.managersystem.view.MyUser;
import com.swpuiot.managersystem.view.StuClassManagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/4/26.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    Context context;
    List<StuJoinedClassEntity> list;
//
    public ClassAdapter(Context context, List<StuJoinedClassEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<StuJoinedClassEntity> list) {
        this.list = list;
    }
    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false);

        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        holder.temp.setText(list.get(position).getCName());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    User user = MyUser.getUser();
    class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView temp;

        public ClassViewHolder(View itemView) {
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.tv_temp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getRole() == 1) {
                        //教师用户跳转到教师管理界面
                        Intent intent = new Intent(context, ClassManagerActivity.class);
                        context.startActivity(intent);
                    }else {
                        //学生用户跳转到学生管理界面
                        Intent intent = new Intent(context, StuClassManagerActivity.class);
                        intent.putExtra("class", list.get(getAdapterPosition()).getCourseId());
                        context.startActivity(intent);

                    }
                }
            });
        }
    }
    public void changeList(List<StuJoinedClassEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
