package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.Course;
import com.swpuiot.managersystem.entity.CourseResponse;
import com.swpuiot.managersystem.view.ChooseCourseActivity;
import com.swpuiot.managersystem.view.CreateClassActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/4/30.
 */
public class ChooseCourseAdapter extends RecyclerView.Adapter<ChooseCourseAdapter.ClassViewHolder> {
    Context context;
    List<CourseResponse.DataBean> list = new ArrayList<>();


    public ChooseCourseAdapter(Context context, List<CourseResponse.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void changelist(List<CourseResponse.DataBean> list) {
        this.list = list;
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false);

        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassViewHolder holder, int position) {
        holder.temp.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            Course course;

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateClassActivity.class);
                course = new Course(list.get(holder.getAdapterPosition()).getId(),
                        list.get(holder.getAdapterPosition()).getName(),
                        list.get(holder.getAdapterPosition()).getEnglish());
                intent.putExtra("course", course);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView temp;

        public ClassViewHolder(View itemView) {
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.tv_temp);
        }
    }
}
