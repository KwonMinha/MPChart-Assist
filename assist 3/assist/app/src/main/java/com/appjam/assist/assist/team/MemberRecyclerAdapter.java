package com.appjam.assist.assist.team;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.TeamMember;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 6. 26..
 */

public class MemberRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<TeamMember> item;
    static final int TYPE_HEADER = 0;
    static final int TYPE_ITEM = 1;

    public MemberRecyclerAdapter(Context context, ArrayList<TeamMember> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else
            return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_team_member_header, parent, false);
            BaseActivity.setGlobalFont(context, v);
            return new HeaderViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_team_member, parent, false);
            BaseActivity.setGlobalFont(context, v);
            return new BaseViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {    // 이떄 holder는 만든게 아니고 기본적으로 제공되는 viewholder
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.num.setText("등번호");
            headerViewHolder.name.setText("이름");
            headerViewHolder.age.setText("나이");
            headerViewHolder.member_pos.setText("포지션");
            headerViewHolder.count.setText("출장수");
            headerViewHolder.num_goal.setText("득점수");
            headerViewHolder.assist.setText("도움");

        } else if (holder instanceof BaseViewHolder) {
            final BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            TeamMember currentItem = item.get(position - 1);   // -1 하는 이유 ? : 헤더(index 0), 댓글 (index 1 ~ n-2), 푸터(index n-1)
            baseViewHolder.num.setText(String.valueOf(currentItem.getBacknumber()));
            baseViewHolder.name.setText(currentItem.getUsername());
            baseViewHolder.age.setText(String.valueOf(currentItem.getAge()));
            baseViewHolder.member_pos.setText(currentItem.getPosition());
            baseViewHolder.count.setText(String.valueOf(currentItem.getTotal_game()));
            baseViewHolder.num_goal.setText(String.valueOf(currentItem.getScore()));
            baseViewHolder.assist.setText(String.valueOf(currentItem.getAssist()));
        }
    }

    @Override
    public int getItemCount() {
        return item.size() + 1; // 헤더 더해
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView num;
        TextView name;
        TextView age;
        TextView member_pos;
        TextView count;
        TextView num_goal;
        TextView assist;

        public HeaderViewHolder(View v) {
            super(v);
            num = (TextView) v.findViewById(R.id.num);
            name = (TextView) v.findViewById(R.id.name);
            age = (TextView) v.findViewById(R.id.age);
            member_pos = (TextView) v.findViewById(R.id.pos);
            count = (TextView) v.findViewById(R.id.count);
            num_goal = (TextView) v.findViewById(R.id.goal);
            assist = (TextView) v.findViewById(R.id.assist);
        }
    }


    private class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView num;
        TextView name;
        TextView age;
        TextView member_pos;
        TextView count;
        TextView num_goal;
        TextView assist;

        public BaseViewHolder(View v) {
            super(v);
            num = (TextView) v.findViewById(R.id.num);
            name = (TextView) v.findViewById(R.id.name);
            age = (TextView) v.findViewById(R.id.age);
            member_pos = (TextView) v.findViewById(R.id.pos);
            count = (TextView) v.findViewById(R.id.count);
            num_goal = (TextView) v.findViewById(R.id.goal);
            assist = (TextView) v.findViewById(R.id.assist);
        }
    }
}
