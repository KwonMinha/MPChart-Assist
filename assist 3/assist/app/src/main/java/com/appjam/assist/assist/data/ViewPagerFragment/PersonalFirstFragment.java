package com.appjam.assist.assist.data.ViewPagerFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.AffectMe;
import com.appjam.assist.assist.model.response.AffectNoMe;
import com.appjam.assist.assist.model.response.Player;
import com.appjam.assist.assist.model.response.PlayerResult;
import com.appjam.assist.assist.model.response.TeamAffectResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class PersonalFirstFragment extends Fragment {
    private View v;
    private TextView tv_total, tv_goal, tv_assist, tv_lose, tv_me_rate, tv_me_avgtotal, tv_me_avglose, tv_rate, tv_not_total, tv_not_lose;
    private NetworkService networkService;
    private int player_id;
    private AffectMe me;
    private AffectNoMe nome;
    private Player player;


    public PersonalFirstFragment newInstance(Player player, AffectMe me, AffectNoMe nome) {
        PersonalFirstFragment fragment = new PersonalFirstFragment();
        this.player = player;
        this.me = me;
        this.nome = nome;

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", player);
        bundle.putSerializable("me", me);
        bundle.putSerializable("nome", nome);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vp_personal_first, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        player = (Player) getArguments().getSerializable("data");
        me = (AffectMe) getArguments().getSerializable("me");
        nome = (AffectNoMe) getArguments().getSerializable("nome");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        player_id = preferences.getInt("user_id", 0);

        initView();
        setData();
        setAffectData();
        return v;
    }


    private void setAffectData() {
        // 승률 : 승 경기수 / 총 게임수 * 100
        if (me.getTotal_game() == 0) {
            tv_me_rate.setText("0%");
            tv_me_avgtotal.setText("0.00");
            tv_me_avglose.setText("0.00");
        } else {
            tv_me_rate.setText(me.getWin_game() * 100 / me.getTotal_game() + "%");
            float me_total =  ((float)me.getScore_team() / me.getTotal_game());
            String str_me_total = String.format("%.2f", me_total);
            tv_me_avgtotal.setText(str_me_total);
            float me_lose = ((float) me.getScore_against_team() / me.getTotal_game());
            String str_me_lose = String.format("%.2f", me_lose);
            tv_me_avglose.setText(str_me_lose);
        }

        if (nome.getTotal_game() == 0) {
            tv_rate.setText("0%");
            tv_not_total.setText("0.00");
            tv_not_lose.setText("0.00");
        } else {
            tv_rate.setText(nome.getWin_game() * 100 / nome.getTotal_game() + "%");
            float not_total =  ((float)nome.getScore_team() / nome.getTotal_game());
            String str_not_total = String.format("%.2f", not_total);
            tv_not_total.setText(str_not_total);
            float not_lose =  ((float)nome.getScore_against_team() / nome.getTotal_game());
            String str_not_lose = String.format("%.2f", not_lose);
            tv_not_lose.setText(str_not_lose);
        }
    }

    private void setData() {
        tv_total.setText(player.getTotal_game() + "경기");
        tv_goal.setText(player.getScore() + "득점");
        tv_assist.setText(player.getAssist() + "도움");
        tv_lose.setText(player.getScore_against() + "실점");
    }

    private void initView() {
        tv_total = (TextView) v.findViewById(R.id.tv_total_game);
        tv_goal = (TextView) v.findViewById(R.id.tv_goal);
        tv_assist = (TextView) v.findViewById(R.id.tv_assist);
        tv_lose = (TextView) v.findViewById(R.id.tv_lose);
        tv_me_rate = (TextView) v.findViewById(R.id.tv_me_rate);
        tv_me_avgtotal = (TextView) v.findViewById(R.id.tv_me_avg_total);
        tv_me_avglose = (TextView) v.findViewById(R.id.tv_me_avg_lose);
        tv_rate = (TextView) v.findViewById(R.id.tv_not_rate);
        tv_not_total = (TextView) v.findViewById(R.id.tv_not_total);
        tv_not_lose = (TextView) v.findViewById(R.id.tv_not_lose);
    }
}
