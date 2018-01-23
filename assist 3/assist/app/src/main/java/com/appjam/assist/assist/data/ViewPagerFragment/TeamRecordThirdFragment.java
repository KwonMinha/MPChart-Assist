package com.appjam.assist.assist.data.ViewPagerFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.adapter.TacticsAdapter;
import com.appjam.assist.assist.model.response.Tactic;
import com.appjam.assist.assist.model.response.TacticResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 29..
 */

public class TeamRecordThirdFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private TacticsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Tactic> list;
    private NetworkService networkService;
    private int team_id;

    public TeamRecordThirdFragment newInstance(ArrayList<Tactic> list) {
        TeamRecordThirdFragment fragment = new TeamRecordThirdFragment();
        this.list = list;

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vp_team_third, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        list = (ArrayList<Tactic>)getArguments().getSerializable("data");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preferences.getInt("team_id", 0);

        initView();

        adapter = new TacticsAdapter(getContext(), list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void initView() {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}