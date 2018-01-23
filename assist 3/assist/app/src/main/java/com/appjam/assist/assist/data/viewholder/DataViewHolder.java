package com.appjam.assist.assist.data.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.R;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class DataViewHolder extends RecyclerView.ViewHolder {
    public ImageView item_logo1;
    public ImageView item_logo2;
    public TextView item_name1;
    public TextView item_name2;
    public TextView item_score1;
    public TextView item_score2;
    public TextView item_date;
    public LinearLayout item_layout;
    public ImageView noData_layout;

    public DataViewHolder(View itemView) {
        super(itemView);

        item_logo1 = (ImageView)itemView.findViewById(R.id.logo1);
        item_logo2 = (ImageView)itemView.findViewById(R.id.logo2);
        item_name1 = (TextView)itemView.findViewById(R.id.name1);
        item_name2 = (TextView)itemView.findViewById(R.id.name2);
        item_score1 = (TextView)itemView.findViewById(R.id.score1);
        item_score2 = (TextView)itemView.findViewById(R.id.score2);
        item_date = (TextView)itemView.findViewById(R.id.date);
        item_layout = (LinearLayout)itemView.findViewById(R.id.layout);
        noData_layout = (ImageView)itemView.findViewById(R.id.noData_layout);
    }
}
