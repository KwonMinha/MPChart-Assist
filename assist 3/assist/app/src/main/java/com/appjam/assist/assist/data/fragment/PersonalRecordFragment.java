package com.appjam.assist.assist.data.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.CircleAnimIndicator;
import com.appjam.assist.assist.data.adapter.ViewPagerAdapter;
import com.appjam.assist.assist.model.response.AffectMe;
import com.appjam.assist.assist.model.response.AffectNoMe;
import com.appjam.assist.assist.model.response.Player;
import com.appjam.assist.assist.model.response.PlayerMonth;
import com.appjam.assist.assist.model.response.PlayerMonthResult;
import com.appjam.assist.assist.model.response.PlayerResult;
import com.appjam.assist.assist.model.response.Pos_ATK;
import com.appjam.assist.assist.model.response.Pos_DF;
import com.appjam.assist.assist.model.response.Pos_GK;
import com.appjam.assist.assist.model.response.Pos_MF;
import com.appjam.assist.assist.model.response.Pos_SUB;
import com.appjam.assist.assist.model.response.PositionResult;
import com.appjam.assist.assist.model.response.TeamAffectResult;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


/**
 * Created by gominju on 2017. 6. 26..
 */

public class PersonalRecordFragment extends Fragment {
    private View v;
    private ViewPager viewPager;
    private CircleAnimIndicator circleAnimIndicator;
    private ImageView iv_plyer, iv_team;
    private TextView tv_user, tv_team, tv_height, tv_weight, tv_age, tv_position;
    private NetworkService networkService;
    private int player_id;
    private Player player;
    private TeamProfile team;
    private ViewPagerAdapter viewPagerAdapter;

    private Player playerData;
    private AffectMe me;
    private AffectNoMe nome;
    private ArrayList<PlayerMonth> monthList;
    private Pos_ATK atk;
    private Pos_MF mf;
    private Pos_DF df;
    private Pos_GK gk;
    private Pos_SUB sub;
    private int cnt = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_personal_record, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        player_id = preferences.getInt("user_id", 0);

        initLayout();
        initNetwork();

        return v;
    }

    private void initLayout() {
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        circleAnimIndicator = (CircleAnimIndicator) v.findViewById(R.id.circleAnimIndicator);
        iv_plyer = (ImageView) v.findViewById(R.id.iv_player_profile);
        iv_team = (ImageView) v.findViewById(R.id.iv_team_logo);
        tv_user = (TextView) v.findViewById(R.id.tv_name);
        tv_team = (TextView) v.findViewById(R.id.tv_teamname);
        tv_height = (TextView) v.findViewById(R.id.tv_height);
        tv_weight = (TextView) v.findViewById(R.id.tv_weight);
        tv_age = (TextView) v.findViewById(R.id.tv_age);
        tv_position = (TextView) v.findViewById(R.id.tv_position);
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<PlayerResult> resultCall = networkService.getPlayerInfo(player_id);
        resultCall.enqueue(new Callback<PlayerResult>() {
            @Override
            public void onResponse(Call<PlayerResult> call, Response<PlayerResult> response) {
                if (response.isSuccessful()) {
                    player = response.body().response;
                    networkPagerData();
                    networkTeam(player.getTeam_id());
                    setData();
                    //initViewPager();
                }
            }

            @Override
            public void onFailure(Call<PlayerResult> call, Throwable t) {
            }
        });
    }

    private void networkPagerData() {
        cnt = 4;
        Call<PlayerResult> result1 = networkService.getPlayerInfo(player_id);
        result1.enqueue(new Callback<PlayerResult>() {
            @Override
            public void onResponse(Call<PlayerResult> call, Response<PlayerResult> response) {
                if (response.isSuccessful()) {
                    playerData = response.body().response;
                    viewPagerAdapter.setPlayerData(playerData);
//                    setData();
                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlayerResult> call, Throwable t) {

            }
        });

        Call<TeamAffectResult> resultCall = networkService.getAffectList(player_id);
        resultCall.enqueue(new Callback<TeamAffectResult>() {
            @Override
            public void onResponse(Call<TeamAffectResult> call, Response<TeamAffectResult> response) {
                if (response.isSuccessful()) {
                    me = response.body().response.attend;
                    nome = response.body().response.noattend;
                    viewPagerAdapter.setMe(me);
                    viewPagerAdapter.setNome(nome);

                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamAffectResult> call, Throwable t) {
                Log.i("mytag", "PersonalFirstFragment, 실패");
            }
        });


        Call<PlayerMonthResult> result = networkService.getPlayerMonthList(player_id);
        result.enqueue(new Callback<PlayerMonthResult>() {
            @Override
            public void onResponse(Call<PlayerMonthResult> call, Response<PlayerMonthResult> response) {
                if (response.isSuccessful()) {
                    monthList = response.body().response;
                    viewPagerAdapter.setMonthList(monthList);

                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlayerMonthResult> call, Throwable t) {

            }
        });

        Call<PositionResult> result3 = networkService.getPositionList(player_id);
        result3.enqueue(new Callback<PositionResult>() {
            @Override
            public void onResponse(Call<PositionResult> call, Response<PositionResult> response) {
                if (response.isSuccessful()) {
                    atk = response.body().response.ATK;
                    df = response.body().response.DF;
                    gk = response.body().response.GK;
                    mf = response.body().response.MF;
//                    setData();
                    viewPagerAdapter.setAtk(atk);
                    viewPagerAdapter.setDf(df);
                    viewPagerAdapter.setMf(mf);
                    viewPagerAdapter.setGk(gk);

                    if (--cnt == 0) {
                        initViewPager();
                    }
                }
            }

            @Override
            public void onFailure(Call<PositionResult> call, Throwable t) {

            }
        });

    }

    private void networkTeam(int team_id) {
        Call<TeamProfileResult> result = networkService.getTeamProfile(team_id);
        result.enqueue(new Callback<TeamProfileResult>() {
            @Override
            public void onResponse(Call<TeamProfileResult> call, Response<TeamProfileResult> response) {
                if (response.isSuccessful()) {
                    team = response.body().response;
                    setTeamData();
                }
            }

            @Override
            public void onFailure(Call<TeamProfileResult> call, Throwable t) {
                Log.i("mytag", "Team 실패");
            }
        });


    }

    private void setTeamData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + team.getProfile_pic_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_team);
        tv_team.setText(team.getTeamname());
        Log.i("mytag", "PersonalRecordFragment, tv_team : " + team.getTeamname());
    }

    private void setData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/player/" + player.getProfile_pic_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_plyer);
        tv_user.setText(player.getUsername());
        tv_height.setText(String.valueOf(player.getHeight()));
        tv_weight.setText(String.valueOf(player.getWeight()));
        tv_age.setText(String.valueOf(player.getAge()));
        tv_position.setText(player.getPosition());
    }

    private void initViewPager() {

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);


        //Indicator 초기화
        initIndicaotor();
    }

    private void initIndicaotor() {
        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(3, R.drawable.slide_white, R.drawable.slide_yellow);
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
