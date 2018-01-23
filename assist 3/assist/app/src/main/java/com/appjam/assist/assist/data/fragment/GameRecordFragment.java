package com.appjam.assist.assist.data.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.Game;
import com.appjam.assist.assist.data.WriteGameRecordActivity;
import com.appjam.assist.assist.data.WriteGameRecordActivity2;
import com.appjam.assist.assist.data.adapter.DataRecyclerAdapter;
import com.appjam.assist.assist.login.SearchResult;
import com.appjam.assist.assist.login.SearchTeamResultFragment;
import com.appjam.assist.assist.model.response.TeamPlay;
import com.appjam.assist.assist.model.response.TeamPlayResult;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by gominju on 2017. 6. 29..
 */

public class GameRecordFragment extends Fragment {
    private View v;

    private RecyclerView recyclerView;
    private DataRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    int goal_count;
    int goal_conceded;
    //    int selectPosition;
//    boolean isPosition = false;
    static final int REQUEST_CODE = 1000;

    private NetworkService networkService;
    private int team_id;
    private ArrayList<TeamPlay> playList;
    private TeamProfile teamProfile;
    private int schedule_id;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            goal_count = data.getExtras().getInt("write1_goal");
//            goal_conceded = data.getExtras().getInt("write1_conceded");

            networkTeamProfile();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        networkTeamList(teamProfile);
//        networkTeamProfile();
        setData(teamProfile);
//        networkTeamList(teamProfile);
        recyclerAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_record_game, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preferences.getInt("team_id", 0);

        initView();
        initNetwork();
        networkTeamProfile();

        return v;
    }

    private void networkTeamProfile() {
        Call<TeamProfileResult> result = networkService.getTeamProfile(team_id);
        result.enqueue(new Callback<TeamProfileResult>() {
            @Override
            public void onResponse(Call<TeamProfileResult> call, Response<TeamProfileResult> response) {
                if (response.isSuccessful()) {
                    teamProfile = response.body().response;
                    networkTeamList(teamProfile);
                }
            }

            @Override
            public void onFailure(Call<TeamProfileResult> call, Throwable t) {

            }
        });
    }

    private void networkTeamList(final TeamProfile teamProfile) {
        Call<TeamPlayResult> result = networkService.getTeamPlayList(team_id);
        result.enqueue(new Callback<TeamPlayResult>() {
            @Override
            public void onResponse(Call<TeamPlayResult> call, Response<TeamPlayResult> response) {
                if (response.isSuccessful()) {
                    playList = response.body().response;
                    setData(teamProfile);
                }
            }

            @Override
            public void onFailure(Call<TeamPlayResult> call, Throwable t) {

            }
        });
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void setData(final TeamProfile teamProfile) {
        recyclerAdapter = new DataRecyclerAdapter(getApplicationContext(), playList, teamProfile, clickEvent);
        recyclerAdapter.update(playList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);

            Game gameData = new Game();
            gameData.setDate(playList.get(itemPosition).getGame_dt());
            gameData.setProfile_url(teamProfile.getProfile_pic_url());
            gameData.setName1(teamProfile.getTeamname());
            gameData.setName2(playList.get(itemPosition).getAgainst_team());
            gameData.setScore1(playList.get(itemPosition).getScore_team());
            gameData.setScore2(playList.get(itemPosition).getScore_against_team());
            gameData.setId(playList.get(itemPosition).getId());

            int attendee = playList.get(itemPosition).getAttendee();
            if (playList.get(itemPosition).isSchedule()) {  // 게임이 있는 경우
                if (attendee >= 11) {
                    Intent intent = new Intent(getApplicationContext(), WriteGameRecordActivity2.class);
                    intent.putExtra("gameData", gameData);
                    intent.putExtra("schedule_id", gameData.getId());
                    intent.putExtra("type", "scheduled");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "참여하는 선수의 수가 11명 이상이 되어야 합니다. (경기기록 있음)", Toast.LENGTH_SHORT).show();
                }

            } else {    // 게임이 없는 경우

                if (attendee >= 11) {
                    Intent intent = new Intent(getApplicationContext(), WriteGameRecordActivity.class);
                    intent.putExtra("gameData", gameData);
                    intent.putExtra("type", "no_schedule");
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "참여하는 선수의 수가 11명 이상이 되어야 합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void initView() {
        recyclerView = (RecyclerView) v.findViewById(R.id.data_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}