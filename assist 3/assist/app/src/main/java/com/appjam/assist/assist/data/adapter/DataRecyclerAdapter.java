package com.appjam.assist.assist.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.viewholder.DataViewHolder;
import com.appjam.assist.assist.data.item.Data_Itemtdata;
import com.appjam.assist.assist.model.response.TeamPlay;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by gominju on 2017. 7. 3..
 */
public class DataRecyclerAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private Context context;
    private ArrayList<TeamPlay> itemdatas;
    public int get_postion;
    public String get_date, get_name2;
    public int get_score1, get_score2;
    private TeamProfile teamProfile;
    private View.OnClickListener onClickListener;
    public boolean isScheduled;

    public DataRecyclerAdapter(Context context, ArrayList<TeamPlay> itemdatas, TeamProfile teamProfile, View.OnClickListener onClickListener) {
        this.context = context;
        this.itemdatas = itemdatas;
        this.teamProfile = teamProfile;
        this.onClickListener = onClickListener;
    }

    public void update(ArrayList<TeamPlay> list) {
        itemdatas = list;
        notifyDataSetChanged();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_record, parent, false);
//        BaseActivity.setGlobalFont(context, view);
        DataViewHolder viewHolder = new DataViewHolder(view);
        view.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {

        final TeamPlay item = itemdatas.get(position);
        get_date = item.getGame_dt().substring(0, 4) + "년 " + item.getGame_dt().substring(5, 7) + "월 " + item.getGame_dt().substring(8, 10) + "일";
        holder.item_date.setText(get_date);
        Glide.with(context)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + teamProfile.getProfile_pic_url())
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.item_logo1);
        holder.item_name1.setText(teamProfile.getTeamname());
        holder.item_name2.setText(item.getAgainst_team());
        if (item.getScore_team() == -1) {
            holder.item_score1.setText("-");
            holder.item_score2.setText("-");
            holder.noData_layout.setBackgroundResource(R.drawable.pink_background_gamedata);
            item.setSchedule(false);
        } else {
            holder.item_score1.setText(String.valueOf(item.getScore_team()));
            holder.item_score2.setText(String.valueOf(item.getScore_against_team()));
            holder.noData_layout.setBackgroundResource(R.drawable.blue_background_gamedata);
            item.setSchedule(true);
        }

    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}