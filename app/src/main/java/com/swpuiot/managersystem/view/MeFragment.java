package com.swpuiot.managersystem.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.managersystem.R;
import com.swpuiot.managersystem.adapter.ClassAdapter;
import com.swpuiot.managersystem.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DELL on 2018/4/26.
 */
public class MeFragment extends Fragment {
    public static MeFragment newInstance() {
        MeFragment newFragment = new MeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("name", name);
//        bundle.putString("passwd", passwd);
//        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Unbinder unbinder;
    @BindView(R.id.tv_namevalue)
    TextView username;
    @BindView(R.id.tv_idvalue)
    TextView id;
    @BindView(R.id.tv_phonevalue)
    TextView phone;
    @BindView(R.id.tv_emailvalue)
    TextView email;
    @BindView(R.id.tv_sex)
    TextView sex;
    @BindView(R.id.tv_rolevalue)
    TextView role;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        User user = ((FirstActivity) getActivity()).getUser();
        username.setText(user.getUsername());
        id.setText(user.getId()+"");
        sex.setText(user.getSex());
        phone.setText(user.getPhone() + "");
        email.setText(user.getEmail());
        role.setText(user.getRole()+"");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
