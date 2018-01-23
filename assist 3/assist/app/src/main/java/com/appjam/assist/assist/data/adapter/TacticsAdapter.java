package com.appjam.assist.assist.data.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.Tactic;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 6. 29..
 */

public class TacticsAdapter extends RecyclerView.Adapter<TacticsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Tactic> list;
    private int win_game;
    private int draw_game;
    private int lose_game;
    private Resources res;
    private Animation growAnim;
    LinearLayout graph_layout;
    private int total;

    public TacticsAdapter(Context context, ArrayList<Tactic> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_vp_team_third, parent, false);
//        BaseActivity.setGlobalFont(context, v);

        res = context.getResources();
        growAnim = AnimationUtils.loadAnimation(context, R.anim.chart_grow);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tactic pos_item = list.get(position);
        total = pos_item.getTotal_game();
        win_game = pos_item.getWin_game();
        draw_game = pos_item.getDraw_game();
        lose_game = pos_item.getLose_game();

        holder.tv_tactic.setText(pos_item.getTactic());
        holder.tv_num.setText(String.valueOf(pos_item.getTotal_game()));
        holder.tv_result.setText(win_game + "승 " + draw_game + "무 " + lose_game + "패");
        if(total==0){
            addItem("win", 0, R.color.colorSkyBlue);
            addItem("draw", 0, R.color.colorTextGray);
            addItem("lose", 0, R.color.colorAccent);

        }
        else {
            addItem("win", win_game * 100 / total, R.color.colorSkyBlue);
            addItem("draw", draw_game * 100 / total, R.color.colorTextGray);
            addItem("lose", lose_game * 100 / total, R.color.colorAccent);
        }
    }

    private void addItem(String name, int value, int color) {
        LinearLayout itemLayout = new LinearLayout(context);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 텍스트뷰 추가
//        TextView textView = new TextView(context);
//        textView.setText(name);
//        params.width = 100;
//        params.setMargins(0, 4, 0, 4);
//        itemLayout.addView(params);

        // 프로그레스바 추가
        ProgressBar proBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(100);
        if (color == R.color.colorSkyBlue)
            proBar.setProgressDrawable(
                    context.getResources().getDrawable(R.drawable.chart_skyblue));
        else if (color == R.color.colorTextGray)
            proBar.setProgressDrawable(
                    context.getResources().getDrawable(R.drawable.chart_gray));
        else if (color == R.color.colorAccent)
            proBar.setProgressDrawable(
                    context.getResources().getDrawable(R.drawable.chart_pink));

        proBar.setAnimation(growAnim);

        params2.height = 20;
        params2.width = value*2;
        params2.gravity = Gravity.CENTER_VERTICAL;
        itemLayout.addView(proBar, params2);

        graph_layout.addView(itemLayout, params3);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tactic;
        TextView tv_num;
        TextView tv_result;

        public ViewHolder(View v) {
            super(v);

            tv_tactic = (TextView) v.findViewById(R.id.tactic);
            tv_num = (TextView) v.findViewById(R.id.num);
            tv_result = (TextView) v.findViewById(R.id.result);
            graph_layout = (LinearLayout) v.findViewById(R.id.layout_chart);
        }
    }
}
