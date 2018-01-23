package com.appjam.assist.assist.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.WriteGameRecordActivity;
import com.appjam.assist.assist.data.item.Itemdata_game_record;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 6. 29..
 */

public class GameRecordAdapter extends RecyclerView.Adapter<GameRecordAdapter.ViewHolder> {
    Context context;
    ArrayList<Itemdata_game_record> list;

    public GameRecordAdapter(Context context, ArrayList<Itemdata_game_record> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_game_record, parent, false);
//        BaseActivity.setGlobalFont(context, v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Itemdata_game_record item = list.get(position);
        if (position == 0) {
            holder.first_layout.setBackgroundResource(R.drawable.pink_background_shadow);
        }

        holder.date.setText(item.getDate());
        holder.name1.setText(item.getName1());
        holder.name2.setText(item.getName2());
        holder.score1.setText(String.valueOf(item.getScore1()));
        holder.score2.setText(String.valueOf(item.getScore2()));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, WriteGameRecordActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView name1, name2, score1, score2;
        ImageView logo1, logo2;
        LinearLayout layout;
        LinearLayout first_layout;


        public ViewHolder(View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.date);
            name1 = (TextView) v.findViewById(R.id.name1);
            name2 = (TextView) v.findViewById(R.id.name2);
            score1 = (TextView) v.findViewById(R.id.score1);
            score2 = (TextView) v.findViewById(R.id.score2);
            logo1 = (ImageView) v.findViewById(R.id.logo1);
            logo2 = (ImageView) v.findViewById(R.id.logo2);
            layout = (LinearLayout)v.findViewById(R.id.layout);
            first_layout = (LinearLayout)v.findViewById(R.id.first_layout);
        }
    }
}
