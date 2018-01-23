package com.appjam.assist.assist.ranking;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.Ranking;

import java.util.ArrayList;

/**
 * Created by minha on 2017-07-04.
 */

public class RankingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Ranking> item;
    int id;
    static final int TYPE_HEADER = 0;
    static final int TYPE_ITEM = 1;

    public RankingRecyclerAdapter(Context context, ArrayList<Ranking> item, int id) {
        this.context = context;
        this.item = item;
        this.id = id;
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
            View v = LayoutInflater.from(context).inflate(R.layout.item_rank_recyclerview_header, parent, false);
            BaseActivity.setGlobalFont(context, v);
            return new HeaderViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_rank_recyclerview, parent, false);
            BaseActivity.setGlobalFont(context, v);
            return new BaseViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.custom_item_rank.setText("등수");
            headerViewHolder.custom_item_team_name.setText("팀명");
            headerViewHolder.custom_item_all.setText("총 경기");
            headerViewHolder.custom_item_win.setText("승");
            headerViewHolder.custom_item_draw.setText("무");
            headerViewHolder.custom_item_lose.setText("패");
            headerViewHolder.custom_item_rate.setText("승률");
        } else if (holder instanceof BaseViewHolder) {
            final BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
            Ranking currentItem = item.get(position - 1);
            baseViewHolder.custom_item_rank.setText(String.valueOf(currentItem.getRank()));
            baseViewHolder.custom_item_team_name.setText(currentItem.getTeamname());
            baseViewHolder.custom_item_all.setText(String.valueOf(currentItem.getTotal_game()));
            baseViewHolder.custom_item_win.setText(String.valueOf(currentItem.getWin_game()));
            baseViewHolder.custom_item_draw.setText(String.valueOf(currentItem.getDraw_game()));
            baseViewHolder.custom_item_lose.setText(String.valueOf(currentItem.getLose_game()));

            if (currentItem.getTotal_game() != 0) {
                float me_total = ((float) currentItem.getWin_game() * 100 / currentItem.getTotal_game());
                String str_rank_total = String.format("%.2f", me_total);
                baseViewHolder.custom_item_rate.setText(str_rank_total);
            } else {
                String str_rank_total = "0";
                baseViewHolder.custom_item_rate.setText(str_rank_total);
            }
            if (currentItem.getId() == id) {
                baseViewHolder.linearLayout.setBackgroundColor(Color.rgb(70, 178, 206));
            }
        }
    }

    @Override
    public int getItemCount() {
        return item.size() + 1;
    }
    // return (itemdatas != null ? itemdatas.size() : 0);


    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView custom_item_rank;
        TextView custom_item_team_name;
        TextView custom_item_all;
        TextView custom_item_win;
        TextView custom_item_draw;
        TextView custom_item_lose;
        TextView custom_item_rate;

        public HeaderViewHolder(View v) {
            super(v);
            custom_item_rank = (TextView) itemView.findViewById(R.id.rank);
            custom_item_team_name = (TextView) itemView.findViewById(R.id.team_name);
            custom_item_all = (TextView) itemView.findViewById(R.id.all);
            custom_item_win = (TextView) itemView.findViewById(R.id.win);
            custom_item_draw = (TextView) itemView.findViewById(R.id.draw);
            custom_item_lose = (TextView) itemView.findViewById(R.id.lose);
            custom_item_rate = (TextView) itemView.findViewById(R.id.rate);
        }
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView custom_item_rank;
        TextView custom_item_team_name;
        TextView custom_item_all;
        TextView custom_item_win;
        TextView custom_item_draw;
        TextView custom_item_lose;
        TextView custom_item_rate;
        LinearLayout linearLayout;

        public BaseViewHolder(View v) {
            super(v);
            custom_item_rank = (TextView) itemView.findViewById(R.id.rank);
            custom_item_team_name = (TextView) itemView.findViewById(R.id.team_name);
            custom_item_all = (TextView) itemView.findViewById(R.id.all);
            custom_item_win = (TextView) itemView.findViewById(R.id.win);
            custom_item_draw = (TextView) itemView.findViewById(R.id.draw);
            custom_item_lose = (TextView) itemView.findViewById(R.id.lose);
            custom_item_rate = (TextView) itemView.findViewById(R.id.rate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_rank_recyclerview);
        }
    }
}


