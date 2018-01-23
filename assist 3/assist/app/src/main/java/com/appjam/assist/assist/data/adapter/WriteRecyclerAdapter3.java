package com.appjam.assist.assist.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.viewholder.WriteViewHolder;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview3;

import java.util.ArrayList;

/**
 * Created by minha on 2017-07-03.
 */

public class WriteRecyclerAdapter3 extends RecyclerView.Adapter<WriteViewHolder> {
    ArrayList<Itemdata_recyclerview3> itmedatas;
    ArrayList<Itemdata_recyclerview3> condatas;
    int conceded_count = 0;

    public WriteRecyclerAdapter3(ArrayList<Itemdata_recyclerview3> itmedatas) {
        this.itmedatas = itmedatas;
        this.condatas = new ArrayList<Itemdata_recyclerview3>();
        this.condatas.addAll(itmedatas);
    }

    @Override
    public WriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_game_3, parent, false);
//        BaseActivity.setGlobalFont(parent.getContext(), view);
        WriteViewHolder viewHolder = new WriteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WriteViewHolder holder, int position) {
        holder.custom_item_text3.setText(itmedatas.get(position).text3);
        holder.custom_item_img3.setImageResource(itmedatas.get(position).img3);
    }

    @Override
    public int getItemCount() {
        return itmedatas != null ? itmedatas.size() : 0;
    }

    public int getConCount() {
        for (Itemdata_recyclerview3 item : condatas) {
            if (item.getText3().length() != 0) {
                conceded_count++;
            }
        }
        return conceded_count;
    }
}