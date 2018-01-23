package com.appjam.assist.assist.data.ViewPagerFragment;

import android.content.SharedPreferences;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.Pos_ATK;
import com.appjam.assist.assist.model.response.Pos_DF;
import com.appjam.assist.assist.model.response.Pos_GK;
import com.appjam.assist.assist.model.response.Pos_MF;
import com.appjam.assist.assist.model.response.PositionResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class PersonalThirdFragment extends Fragment {
    private View v;
    private TextView tv_att_percent, tv_mf_percent, tv_gk_percent, tv_att_goal, tv_att_assist, tv_att_total, tv_att_against, tv_mf_goal, tv_mf_assist,
            tv_mf_total, tv_mf_against, tv_gk_goal, tv_gk_total, tv_gk_against;
    private ProgressBar bar_att, bar_mf, bar_gk;
    private NetworkService networkService;
    private int player_id;
    private Pos_ATK atk;
    private Pos_DF df;
    private Pos_GK gk;
    private Pos_MF mf;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    public PersonalThirdFragment newInstance(Pos_ATK atk, Pos_DF df, Pos_GK gk, Pos_MF mf) {
        PersonalThirdFragment fragment = new PersonalThirdFragment();
        this.atk = atk;
        this.df = df;
        this.mf = mf;
        this.gk = gk;

        Bundle bundle = new Bundle();
        bundle.putSerializable("atk", atk);
        bundle.putSerializable("df", df);
        bundle.putSerializable("mf", mf);
        bundle.putSerializable("gk", gk);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vp_personal_third, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        atk = (Pos_ATK) getArguments().getSerializable("atk");
        df = (Pos_DF) getArguments().getSerializable("df");
        mf = (Pos_MF) getArguments().getSerializable("mf");
        gk = (Pos_GK) getArguments().getSerializable("gk");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        player_id = preferences.getInt("user_id", 0);

        initView();
        setData();

        return v;
    }

    private void setData() {
        // 평균 득점 : 골 수 / 총 경기수
        // 평균 실점 : 실점 / 총 경기수
        // 승률 : 승 경기수 / 총 게임수 * 100
        int att_percent;
        tv_att_goal.setText(String.valueOf(atk.getScore()));
        tv_att_assist.setText(String.valueOf(atk.getAssist()));
        if (atk.getTotal_game() == 0) {
            tv_att_total.setText(String.valueOf(0.0));
            tv_att_against.setText(String.valueOf(0.0));
            att_percent = 0;
            doAttBar(att_percent);

        } else {
            float att_total =  ((float)atk.getScore_team() / atk.getTotal_game());
            String str_att_total = String.format("%.2f", att_total);
            tv_att_total.setText(str_att_total);
            float att_against =  ((float)atk.getScore_against_team() / atk.getTotal_game());
            String str_att_against = String.format("%.2f", att_against);
            tv_att_against.setText(str_att_against);
            att_percent = (atk.getWin_game() * 100 / atk.getTotal_game());
            doAttBar(att_percent);
        }

        int mf_percent;
        tv_mf_goal.setText(String.valueOf(mf.getScore()));
        tv_mf_assist.setText(String.valueOf(mf.getAssist()));
        if (mf.getTotal_game() == 0) {
            tv_mf_total.setText(String.valueOf(0.0));
            tv_mf_against.setText(String.valueOf(0.0));
            mf_percent = 0;
            doMfBar(mf_percent);
        } else {
            float mf_total =  ((float)mf.getScore_team() / mf.getTotal_game());
            String str_mf_total = String.format("%.2f", mf_total);
            tv_mf_total.setText(str_mf_total);
            float mf_against =  ((float)mf.getScore_against_team() / mf.getTotal_game());
            String str_mf_against = String.format("%.2f", mf_against);
            tv_mf_against.setText(str_mf_against);
            mf_percent = (mf.getWin_game() * 100 / mf.getTotal_game());
            doMfBar(mf_percent);
        }

        // 무실점 경기, 평균 득점, 평균 실점
        tv_gk_goal.setText(String.valueOf(gk.getNo_score_against() + df.getNo_score_against()));
        int avg_total = gk.getScore() + df.getScore();
        int avg_against = gk.getScore_against_team() + df.getScore_against_team();
        int total = gk.getTotal_game() + df.getTotal_game();
        int total_win = gk.getWin_game() + df.getWin_game();
        int gk_percent;
        if (avg_total != 0) {
            float gk_total =  ((float)avg_total / total);
            String str_gk_total = String.format("%.2f", gk_total);
            tv_gk_total.setText(str_gk_total);
        } else {
            tv_gk_total.setText("0.0");
        }
        if (avg_against != 0) {
            float gk_against = ((float) avg_against / total);
            String str_gk_against = String.format("%.2f", gk_against);
            tv_gk_against.setText(str_gk_against);
        } else {
            tv_gk_against.setText(String.valueOf(0.0));
        }
        if (total_win != 0) {
            gk_percent = total_win * 100 / total;
            doGkBar(gk_percent);
        } else {
            gk_percent = 0;
            doGkBar(gk_percent);
        }
    }

    private void doAttBar(final int percent_num) {
        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < percent_num) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            bar_att.setProgress(mProgressStatus);
                            tv_att_percent.setText("" + mProgressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void doMfBar(final int percent_num) {
        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < percent_num) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            bar_mf.setProgress(mProgressStatus);
                            tv_mf_percent.setText("" + mProgressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void doGkBar(final int percent_num) {
        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < percent_num) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            bar_gk.setProgress(mProgressStatus);
                            tv_gk_percent.setText("" + mProgressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void initView() {
        tv_att_percent = (TextView) v.findViewById(R.id.tv_attack_percent);
        tv_mf_percent = (TextView) v.findViewById(R.id.tv_mf_percent);
        tv_gk_percent = (TextView) v.findViewById(R.id.tv_gk_percent);
        tv_att_goal = (TextView) v.findViewById(R.id.tv_attack_goal);
        tv_att_assist = (TextView) v.findViewById(R.id.tv_attack_assist);
        tv_att_total = (TextView) v.findViewById(R.id.tv_attack_avg_total);
        tv_att_against = (TextView) v.findViewById(R.id.tv_attack_avg_against);
        tv_mf_goal = (TextView) v.findViewById(R.id.tv_mf_goal);
        tv_mf_assist = (TextView) v.findViewById(R.id.tv_mf_assist);
        tv_mf_total = (TextView) v.findViewById(R.id.tv_mf_avg_total);
        tv_mf_against = (TextView) v.findViewById(R.id.tv_mf_avg_against);
        tv_gk_goal = (TextView) v.findViewById(R.id.tv_gk_nogoal);
        tv_gk_total = (TextView) v.findViewById(R.id.tv_gk_avg_total);
        tv_gk_against = (TextView) v.findViewById(R.id.tv_gk_avg_against);
        bar_att = (ProgressBar) v.findViewById(R.id.bar_attack);
        bar_gk = (ProgressBar) v.findViewById(R.id.bar_gk);
        bar_mf = (ProgressBar) v.findViewById(R.id.bar_mf);
    }
}
