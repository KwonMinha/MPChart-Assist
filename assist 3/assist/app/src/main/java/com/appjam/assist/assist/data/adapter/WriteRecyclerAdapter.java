package com.appjam.assist.assist.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.viewholder.WriteViewHolder;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview;

import java.util.ArrayList;

/**
 * Created by minha on 2017-06-30.
 */

public class WriteRecyclerAdapter extends RecyclerView.Adapter<WriteViewHolder> {
    ArrayList<Itemdata_recyclerview> itemdatas;
    ArrayList<Itemdata_recyclerview> goaldatas;
    int goal_count = 0;

    public WriteRecyclerAdapter(ArrayList<Itemdata_recyclerview> itemdatas) {
        this.itemdatas = itemdatas;
        this.goaldatas = new ArrayList<Itemdata_recyclerview>();
        this.goaldatas.addAll(itemdatas);
    }

    @Override
    public WriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_game_1, parent, false);
//        BaseActivity.setGlobalFont(parent.getContext(), view);

        WriteViewHolder viewHolder = new WriteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WriteViewHolder holder, int position) {
        holder.custom_item_text1.setText(itemdatas.get(position).text1);
        holder.custom_item_img1.setImageResource(itemdatas.get(position).img1);
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }

    public int getGoalCount() {
        for(Itemdata_recyclerview item : goaldatas) {
            if(item.getText1().length() != 0){
                goal_count++;
            }
        } return goal_count;
    }
}
