package com.appjam.assist.assist.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.item.Itemdata_threemonth;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class ThreeMonthAdapter extends RecyclerView.Adapter<ThreeMonthAdapter.MyViewHolder> {
    Context context;
    ArrayList<Itemdata_threemonth> item;

    public ThreeMonthAdapter(Context context, ArrayList<Itemdata_threemonth> item) {
        this.context = context;
        this.item = item;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_threemonth_record, parent, false);
//        BaseActivity.setGlobalFont(context, v);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Itemdata_threemonth posItem = item.get(position);
        holder.month.setText(String.valueOf(posItem.month));
        holder.num.setText(String.valueOf(posItem.num));
        holder.goal.setText(posItem.goal);
        holder.result.setText(posItem.result);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView month;
        public TextView num;
        public TextView result;
        public TextView goal;

        public MyViewHolder(View v) {
            super(v);
            month = (TextView)v.findViewById(R.id.month);
            num = (TextView)v.findViewById(R.id.play_num);
            result = (TextView)v.findViewById(R.id.result);
            goal = (TextView)v.findViewById(R.id.goal);
        }
    }
}