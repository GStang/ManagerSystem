package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.Class;

import java.util.ArrayList;

public class ChooseClassAdapter extends RecyclerView.Adapter<ChooseClassAdapter.MyViewHolder> {
    Context context;
    ArrayList<Class>list = new ArrayList<Class>();

    public ChooseClassAdapter(Context context,ArrayList<Class>list){
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_class, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getcName());

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_choose_class_name);
        }
    }
}

