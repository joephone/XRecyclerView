package com.example.xrecyclerview.mugoo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xrecyclerview.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joephone on 2016/7/7.
 */
public class DetailFragment extends Fragment {


    LinearLayout layGroup;
    private String tag = this.getClass().getName();



//    private CatchesRecordCreator actionsCreator;
//    private CatchesRecordStore store;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mer_detail, container, false);
        Log.i(tag, "DetailFragment");
        ButterKnife.bind(this, view);

        initValues();
        return view;
    }

    private void initValues() {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
