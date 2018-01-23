package com.appjam.assist.assist.team;

import android.content.SharedPreferences;
import android.net.Network;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

/**
 * Created by gominju on 2017. 6. 26..
 */

public class TeamMainFragment extends Fragment {
    private View v;
    private ImageView iv_profile;
    private TextView tv_teamname, tv_region, tv_manager, tv_found, tv_msg;
    private NetworkService networkService;
    private int team_id;
    private TeamProfile teamProfile;

    public static TeamMainFragment newInstance() {
        TeamMainFragment fragment = new TeamMainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team_main, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preference.getInt("team_id", 0);

        initView();
        initNetwork();
        return v;
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<TeamProfileResult> result = networkService.getTeamProfile(team_id);
        result.enqueue(new Callback<TeamProfileResult>() {
            @Override
            public void onResponse(Call<TeamProfileResult> call, Response<TeamProfileResult> response) {
                if (response.isSuccessful()) {
                    teamProfile = response.body().response;
                    setData();
                }
            }

            @Override
            public void onFailure(Call<TeamProfileResult> call, Throwable t) {

            }
        });
    }

    private void setData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + teamProfile.getProfile_pic_url())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_profile);
        tv_teamname.setText(teamProfile.getTeamname());
        tv_manager.setText(teamProfile.getManager());
        tv_region.setText(teamProfile.getRegion());
        tv_msg.setText(teamProfile.getMessage());
        tv_found.setText(teamProfile.getFound_dt().substring(0, 10));
    }

    private void initView() {
        iv_profile = (ImageView) v.findViewById(R.id.iv_profile);
        tv_teamname = (TextView) v.findViewById(R.id.tv_teamname);
        tv_region = (TextView) v.findViewById(R.id.tv_region);
        tv_manager = (TextView) v.findViewById(R.id.tv_manager);
        tv_found = (TextView) v.findViewById(R.id.tv_found);
        tv_msg = (TextView) v.findViewById(R.id.tv_msg);
    }
}
