package com.appjam.assist.assist.data.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.CircleAnimIndicator;
import com.appjam.assist.assist.data.ViewPagerFragment.TeamRecordFirstFragment;
import com.appjam.assist.assist.data.adapter.TacticsAdapter;
import com.appjam.assist.assist.data.adapter.TeamRecordPagerAdapter;
import com.appjam.assist.assist.model.response.AssistRank;
import com.appjam.assist.assist.model.response.RankingResult;
import com.appjam.assist.assist.model.response.ScoreRank;
import com.appjam.assist.assist.model.response.Tactic;
import com.appjam.assist.assist.model.response.TacticResult;
import com.appjam.assist.assist.model.response.TeamMonth;
import com.appjam.assist.assist.model.response.TeamMonthResult;
import com.appjam.assist.assist.model.response.TeamPlay;
import com.appjam.assist.assist.model.response.TeamPlayResult;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 29..
 */

public class TeamRecordFragment extends Fragment {
    private ProgressBar progBar;
    private TextView percent;
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;
    private View v;
    private ViewPager viewPager;
    private CircleAnimIndicator circleAnimIndicator;
    private NetworkService networkService;
    private int team_id;
    private TeamProfile teamProfile;
    private ImageView iv_profile;
    private TextView tv_teamname, tv_total, tv_record, tv_avr_total, tv_avr_against;
    private float percent_num;
    public Thread t;

    private ArrayList<TeamPlay> teamPlay;
    private ArrayList<TeamMonth> monthList;
    private ArrayList<Tactic> tacticList;
    private ArrayList<ScoreRank> scoreRank;
    private ArrayList<AssistRank> assistRank;
    private TeamRecordPagerAdapter viewPagerAdapter;
    private int cnt = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_record_team, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preferences.getInt("team_id", 0);

        viewPagerAdapter = new TeamRecordPagerAdapter(getFragmentManager());
        initView();
        initNetwork();
        initPagerNetwork();

        return v;
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();

        final Call<TeamProfileResult> result = networkService.getTeamProfile(team_id);
        result.enqueue(new Callback<TeamProfileResult>() {
            @Override
            public void onResponse(Call<TeamProfileResult> call, Response<TeamProfileResult> response) {
                teamProfile = response.body().response;
                setMainData();
            }

            @Override
            public void onFailure(Call<TeamProfileResult> call, Throwable t) {

            }
        });
    }

    private void initPagerNetwork() {
        // first pager
        final Call<TeamPlayResult> result1 = networkService.getTeamPlayList(team_id);
        result1.enqueue(new Callback<TeamPlayResult>() {
            @Override
            public void onResponse(Call<TeamPlayResult> call, Response<TeamPlayResult> response) {
                if (response.isSuccessful()) {
                    teamPlay = response.body().response;
                    Log.i("mytag", "TeamRecordFragment, first pager network");
                    viewPagerAdapter.setFirstData(teamPlay);
//                    setData(teamPlay);

                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamPlayResult> call, Throwable t) {

            }
        });


        // second
        Call<TeamMonthResult> result2 = networkService.getMonthList(team_id);
        result2.enqueue(new Callback<TeamMonthResult>() {
            @Override
            public void onResponse(Call<TeamMonthResult> call, Response<TeamMonthResult> response) {
                if (response.isSuccessful()) {
                    monthList = response.body().response;
                    viewPagerAdapter.setSecondData(monthList);
//                    setGraphData(list);
                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamMonthResult> call, Throwable t) {

            }
        });

        Call<TacticResult> result3 = networkService.getTacticList(team_id);
        result3.enqueue(new Callback<TacticResult>() {
            @Override
            public void onResponse(Call<TacticResult> call, Response<TacticResult> response) {
                if (response.isSuccessful()) {
                    tacticList = response.body().response;
                    viewPagerAdapter.setThirdData(tacticList);

//                    adapter = new TacticsAdapter(getContext(), list);
//                    adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<TacticResult> call, Throwable t) {

            }
        });


        Call<RankingResult> result4 = networkService.getRankList(team_id);
        result4.enqueue(new Callback<RankingResult>() {
            @Override
            public void onResponse(Call<RankingResult> call, Response<RankingResult> response) {
                if (response.isSuccessful()) {
                    scoreRank = response.body().response.score;
                    assistRank = response.body().response.assist;
                    viewPagerAdapter.setFrouthData(scoreRank, assistRank);
                    if (--cnt == 0) {
                        initViewPager();
                    }
//                    setListData();
                }
            }

            @Override
            public void onFailure(Call<RankingResult> call, Throwable t) {

            }
        });

    }

    private void setMainData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + teamProfile.getProfile_pic_url())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_profile);
        tv_teamname.setText(teamProfile.getTeamname());
        tv_total.setText(teamProfile.getTotal_game() + "전");

        tv_record.setText(teamProfile.getWin_game() + "승 " + teamProfile.getDraw_game() + "무 " + teamProfile.getLose_game() + "패");
        // 평균 득점 : 득점 / 총 경기수 * 100
        float avg_total = (float) (teamProfile.getTotal_score() / teamProfile.getTotal_game());
        String str_avg_total = String.format("%.2f", avg_total);
        tv_avr_total.setText("평균 득점 " + str_avg_total);
        // 평균 실점 : 실점 / 총 경기수 * 100
        float avg_against = (float) (teamProfile.getTotal_score_against() / teamProfile.getTotal_game());
        String str_avg_against = String.format("%.2f", avg_against);
        tv_avr_against.setText("평균 실점 " + str_avg_against);
        // 승률 : 승 경기수 / 승 게임수
        percent_num = (float) (teamProfile.getWin_game() * 100 / teamProfile.getTotal_game());
        if ((int) percent_num == 0) {
            percent.setText("0%");
        } else {
            dosomething(((int) percent_num));
            t.start();
        }
    }

    private void dosomething(final int percent_num) {
        t = new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < percent_num) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress(mProgressStatus);
                            percent.setText("" + mProgressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initViewPager() {
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        //Indicator 초기화
        initIndicaotor();
    }


    private void initView() {
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        circleAnimIndicator = (CircleAnimIndicator) v.findViewById(R.id.circleAnimIndicator);
        // 프로그래스
        progBar = (ProgressBar) v.findViewById(R.id.progressBar);
        percent = (TextView) v.findViewById(R.id.tv_percent);
        iv_profile = (ImageView) v.findViewById(R.id.iv_profile);
        tv_teamname = (TextView) v.findViewById(R.id.tv_teamname);
        tv_total = (TextView) v.findViewById(R.id.tv_total);
        tv_record = (TextView) v.findViewById(R.id.tv_record);
        tv_avr_total = (TextView) v.findViewById(R.id.tv_avr_total);
        tv_avr_against = (TextView) v.findViewById(R.id.tv_avr_against);
    }

    private void initIndicaotor() {
        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(4, R.drawable.slide_white, R.drawable.slide_yellow);
    }

    /**
     * ViewPager 전환시 호출되는 메서드
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
