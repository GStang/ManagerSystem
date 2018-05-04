package com.swpuiot.managersystem.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.entity.Leave;
import com.swpuiot.managersystem.event.DestoryEvent;
import com.swpuiot.managersystem.event.RegetEvent;
import com.swpuiot.managersystem.httpinterface.LeaveService;
import com.swpuiot.managersystem.util.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DELL on 2018/5/3.
 */
public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.LeaveHolder> {

    Context context;
    List<Leave> list = new ArrayList<>();
    Retrofit retrofit = RetrofitUtil.getRetrofit();

    public LeaveAdapter(Context context, List<Leave> list) {
        this.context = context;
        this.list = list;
        EventBus.getDefault().register(this);

    }

    @Subscribe
    public void Ondestory(DestoryEvent event){
        EventBus.getDefault().unregister(this);
    }
    @Override
    public LeaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_leave_record, parent, false);
        return new LeaveHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(LeaveHolder holder, int position) {
        holder.id.setText(list.get(position).getUid() + "");
        holder.reason.setText(list.get(position).getReason());
    }

    class LeaveHolder extends RecyclerView.ViewHolder {
        public TextView id;
        TextView reason;

        public LeaveHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            reason = (TextView) itemView.findViewById(R.id.tv_reason);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setPositiveButton("通过", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            retrofit.create(LeaveService.class).updateStatus(list.get(getAdapterPosition()).getId(), 2L).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post(new RegetEvent());
                                        list.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    } else
                                        Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }).setNegativeButton("不通过", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            retrofit.create(LeaveService.class).updateStatus(list.get(getAdapterPosition()).getId(), 1L).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
                                        list.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    } else
                                        Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }).create().show();
                }
            });
        }


    }

}
