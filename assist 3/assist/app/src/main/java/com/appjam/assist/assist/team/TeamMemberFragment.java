package com.appjam.assist.assist.team;

import android.content.SharedPreferences;
import android.net.Network;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.TeamMember;
import com.appjam.assist.assist.model.response.TeamMemberResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 26..
 */

public class TeamMemberFragment extends Fragment {
    private RecyclerView recyclerView;
//    private ArrayList<Itemdata_team_member> list;
    private MemberRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private NetworkService networkService;
    private int team_id;
    private ArrayList<TeamMember> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team_member, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preferences.getInt("team_id", 0);

        initNetwork();

        return v;
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<TeamMemberResult> result = networkService.getTeamMemberList(team_id);
        result.enqueue(new Callback<TeamMemberResult>() {
            @Override
            public void onResponse(Call<TeamMemberResult> call, Response<TeamMemberResult> response) {
                if (response.isSuccessful()) {
                    list = response.body().response;
                    adapter = new MemberRecyclerAdapter(getContext(), list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TeamMemberResult> call, Throwable t) {

            }
        });
    }
}