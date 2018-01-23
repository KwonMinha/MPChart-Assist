package com.appjam.assist.assist.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.viewholder.WriteViewHolder;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview_h;
import com.appjam.assist.assist.model.response.SUB_member;
import com.appjam.assist.assist.model.response.TeamList;

import java.util.ArrayList;

/**
 * Created by minha on 2017-06-30.
 */

public class WriteRecyclerAdapter_h extends RecyclerView.Adapter<WriteViewHolder> {
    private Context context;
    private View.OnClickListener onClickListener;
    ArrayList<SUB_member> itemdatas;

    public WriteRecyclerAdapter_h(Context context, ArrayList<SUB_member> itemdatas, View.OnClickListener onClickListener) {
        this.context = context;
        this.itemdatas = itemdatas;
        this.onClickListener = onClickListener;
    }

    public void updateAdapter(ArrayList<SUB_member> list) {
        itemdatas = list;
        notifyDataSetChanged();
    }

    @Override
    public WriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_game_r2_h, parent, false);
//        BaseActivity.setGlobalFont(context, view);
        WriteViewHolder viewHolder = new WriteViewHolder(view);
        view.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WriteViewHolder holder, int position) {
        final SUB_member item = itemdatas.get(position);
        holder.h_item_btn.setText(String.valueOf(item.getBacknumber()));

    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}
