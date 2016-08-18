package com.example.xrecyclerview.mogu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.xrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiangjieqiang on 16/1/18.
 */
public class ListFragment extends Fragment {

    private static final int LIST_NUM = 20;
    @Bind(R.id.id_listview)
    ListView listView;


    private View view;
    private int type;
    private List<String> stringList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment, null);
        ButterKnife.bind(this, view);

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        initData();
        initListView();

        return view;
    }

    private void initData() {
        stringList.clear();
        for (int i = 0; i < LIST_NUM; i++) {
            stringList.add("列表" + type + "：" + i);
        }
    }

    private void initListView() {
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList));
//        listView.setAdapter(new SourceAdapter(getActivity(), stringList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
