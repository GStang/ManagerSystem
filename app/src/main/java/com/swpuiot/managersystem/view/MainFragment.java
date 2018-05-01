package com.swpuiot.managersystem.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassAdapter;
import com.swpuiot.managersystem.entity.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DELL on 2018/4/26.
 */
public class MainFragment extends Fragment {
    Context context;
    Handler handler;
    User user;
    public MainFragment() {
        user = MyUser.getUser();
    }

    public static MainFragment newInstance() {
        MainFragment newFragment = new MainFragment();
        Bundle bundle = new Bundle();

//        bundle.putString("name", handler);
//        bundle.putString("passwd", passwd);
//        bundle.putSerializable("handler", handler);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Unbinder unbinder;
    @BindView(R.id.rv_class)
    RecyclerView classlist;
    @BindView(R.id.fab_add)
    FloatingActionButton add;

    List list = new ArrayList();
    ClassAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        unbinder = ButterKnife.bind(this, view);
        classlist.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter = new ClassAdapter(getActivity(),list);
        classlist.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 教师用户的创建课程
     * 学生用户的加入课程
     */
    @OnClick(R.id.fab_add)
    public void onAdd() {
        //教师用户跳转到选择课程的界面进行创建班级
        if (user.getRole() == 1) {
            Intent intent = new Intent(getActivity(), ChooseCourseActivity.class);
            startActivity(intent);

        }else{
            //学生用户挑战到选择班级的界面
            Intent intent = new Intent(getActivity(), StuChooseClassActivity.class);
            startActivity(intent);

        }
    }

    /**
     * 改变data*/
    public void Notidydata(List list){
        this.list = list;
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        Log.d("notify", "success");
    }
}
