package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.Attendance;
import com.swpuiot.managersystem.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SignInformationAdapter extends RecyclerView.Adapter<SignInformationAdapter.MyViewHolder> {

    private ArrayList<Attendance> list = new ArrayList<Attendance>();
    private Context context;
    private int form;

    public SignInformationAdapter(Context context, ArrayList<Attendance> list, int form) {
        this.list = list;
        this.form = form;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sign_information, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (form == 1) {
            holder.tvName.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(list.get(position).getId().getDate())).toString());
        } else {
            holder.tvName.setText(list.get(position).getId().getId() + "");

        }
        if (list.get(position).getAttend().equals("缺席")) {
            holder.ivAttend.setImageResource(R.drawable.signing);
        } else if (list.get(position).getAttend().equals("出席")) {
            holder.ivAttend.setImageResource(R.drawable.signed);
        } else {
            holder.ivAttend.setImageResource(R.drawable.leave);
        }


    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivAttend;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_sign_name);
            ivAttend = (ImageView) itemView.findViewById(R.id.iv_item_sign_attend);
        }
    }
}

