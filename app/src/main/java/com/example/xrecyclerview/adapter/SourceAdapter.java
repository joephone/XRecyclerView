package com.example.xrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xrecyclerview.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String tag = this.getClass().getName();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<String> sourceList;

    public SourceAdapter(Context context, List<String> sourceList) {
        this.sourceList = sourceList;
        Log.i(tag,"SourceAdapter:"+sourceList.size());
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MerVideoViewHolder(mLayoutInflater.inflate(R.layout.menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MerVideoViewHolder viewHolder = (MerVideoViewHolder) holder;
        final String name = sourceList.get(position);

        viewHolder.tabTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public class MerVideoViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.tab_tv)
        TextView tabTv;

        MerVideoViewHolder(View view) {
            super(view);
//            R.layout.menu_item;
        }
    }

    public interface Event{
        public void getCurrentPos();
    }
}
