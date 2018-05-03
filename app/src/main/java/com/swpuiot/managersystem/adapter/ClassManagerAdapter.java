package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.view.SignInformationActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassManagerAdapter extends RecyclerView.Adapter<ClassManagerAdapter.MyViewHolder> {

    private ArrayList<Long>list = new ArrayList<Long>();
    private Context context;
    private long cId;

    public ClassManagerAdapter(ArrayList<Long>list,Context context,long cId){
        this.cId=cId;
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class_manager,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.time.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(list.get(position))).toString());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tv_item_sign_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SignInformationActivity.class);
                    intent.putExtra("signID",cId);
                    intent.putExtra("signTime",list.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

