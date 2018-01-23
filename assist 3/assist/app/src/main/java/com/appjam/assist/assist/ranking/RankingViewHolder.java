package com.appjam.assist.assist.ranking;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appjam.assist.assist.R;

/**
 * Created by minha on 2017-07-04.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder {
    TextView custom_item_rank;
    TextView custom_item_team_name;
    TextView custom_item_all;
    TextView custom_item_win;
    TextView custom_item_draw;
    TextView custom_item_lose;
    TextView custom_item_rate;


    public RankingViewHolder(View itemView) {
        super(itemView);

        custom_item_rank = (TextView)itemView.findViewById(R.id.rank);
        custom_item_team_name = (TextView)itemView.findViewById(R.id.team_name);
        custom_item_all = (TextView)itemView.findViewById(R.id.all);
        custom_item_win = (TextView)itemView.findViewById(R.id.win);
        custom_item_draw = (TextView)itemView.findViewById(R.id.draw);
        custom_item_lose = (TextView)itemView.findViewById(R.id.lose);
        custom_item_rate = (TextView)itemView.findViewById(R.id.rate);
    }
}
