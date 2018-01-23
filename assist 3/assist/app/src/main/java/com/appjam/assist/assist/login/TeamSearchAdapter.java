package com.appjam.assist.assist.login;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.TeamList;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 7. 1..
 */

public class TeamSearchAdapter extends RecyclerView.Adapter<TeamSearchAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TeamList> list;
    private View.OnClickListener onClickListener;

    public TeamSearchAdapter(Context context, ArrayList<TeamList> list, View.OnClickListener onClickListener) {
        this.context = context;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_team_search, parent, false);
        v.setOnClickListener(onClickListener);
        BaseActivity.setGlobalFont(context, v);
        return new TeamSearchAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TeamList posItem = list.get(position);
        holder.name.setText(posItem.getTeamname());
        holder.region.setText(posItem.getRegion());
        holder.manager.setText(posItem.getManager());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView region;
        public TextView manager;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.tv_teamname);
            region = (TextView)v.findViewById(R.id.tv_region);
            manager = (TextView)v.findViewById(R.id.tv_manager);
        }
    }
}
