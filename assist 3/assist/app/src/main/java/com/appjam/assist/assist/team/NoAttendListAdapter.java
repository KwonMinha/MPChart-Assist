package com.appjam.assist.assist.team;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.NoAttendMember;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class NoAttendListAdapter extends RecyclerView.Adapter<NoAttendListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<NoAttendMember> list;

    public NoAttendListAdapter(Context context, ArrayList<NoAttendMember> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_attend_member, parent, false);
        BaseActivity.setGlobalFont(context, v);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NoAttendMember member = list.get(position);
        holder.tv_name.setText(member.getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
