package com.appjam.assist.assist.data.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.R;

/**
 * Created by minha on 2017-06-30.
 */

public class WriteViewHolder extends RecyclerView.ViewHolder {

    public TextView custom_item_text1;
    public TextView custom_item_text2;
    public TextView custom_item_text3;

    public ImageView custom_item_img1;
    public ImageView custom_item_img2;
    public ImageView custom_item_img3;

    public Button h_item_btn;
    public LinearLayout h_item_layout;

    public WriteViewHolder(View itemView) {
        super(itemView);

        custom_item_text1 = (TextView)itemView.findViewById(R.id.text1);
        custom_item_text2 = (TextView)itemView.findViewById(R.id.text2);
        custom_item_text3 = (TextView)itemView.findViewById(R.id.text3);

        custom_item_img1 = (ImageView)itemView.findViewById(R.id.img1);
        custom_item_img2 = (ImageView)itemView.findViewById(R.id.img2);
        custom_item_img3 = (ImageView)itemView.findViewById(R.id.img3);

        h_item_btn = (Button)itemView.findViewById(R.id.cplayer);
        h_item_layout = (LinearLayout)itemView.findViewById(R.id.layout_sub);

    }
}
