package com.appjam.assist.assist.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.viewholder.WriteViewHolder;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview2;

import java.util.ArrayList;

/**
 * Created by minha on 2017-07-03.
 */

public class WriteRecyclerAdapter2 extends RecyclerView.Adapter<WriteViewHolder>{
    ArrayList<Itemdata_recyclerview2> itemdatas;

    public WriteRecyclerAdapter2(ArrayList<Itemdata_recyclerview2> itemdatas) {
        this.itemdatas = itemdatas;
    }

    @Override
    public WriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_game_2, parent, false);
//        BaseActivity.setGlobalFont(parent.getContext(), view);
        WriteViewHolder viewHolder = new WriteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WriteViewHolder holder, int position) {
        holder.custom_item_text2.setText(itemdatas.get(position).text2);
        holder.custom_item_img2.setImageResource(itemdatas.get(position).img2);
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}