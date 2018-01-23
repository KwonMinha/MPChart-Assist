package com.appjam.assist.assist.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.request.SignUp;
import com.appjam.assist.assist.model.response.TeamList;
import com.appjam.assist.assist.model.response.TeamListResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 28..
 */

public class SearchTeamFragment extends Fragment {
    View v;
    private Button btn_clear, btn_search;
    private NetworkService networkService;
    private SignUp signUp;
    private RecyclerView recyclerView;
    private TeamSearchAdapter adapter;
    private ArrayList<TeamList> list;
    private EditText et_teamname, et_manager;
    private Spinner spinner_region;
    private String region;
    private SearchResult searchResult;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private LinearLayout linearLayout;

//    private String imgUrl;
//    private Uri uri_data;

//    public SearchTeamFragment getInstance(SignUp signUp, String imgUrl, Uri uri_data) {
//        SearchTeamFragment fragement = new SearchTeamFragment();
//        this.signUp = signUp;
//        this.imgUrl = imgUrl;
//        this.uri_data = uri_data;
//
//        Bundle args = new Bundle();
//        args.putSerializable("imgUrl", imgUrl);
//        args.putParcelable("uri_data",uri_data);
//        fragement.setArguments(args);
//
//
//        return fragement;
//    }



    public SearchTeamFragment getInstance(SignUp signUp) {
        SearchTeamFragment fragment = new SearchTeamFragment();
        this.signUp = signUp;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team_search, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());
        initView();
        initNetwork();

        Log.i("mytag", "SearchTeamFragment, type = " + SignUp.signUp.getType());

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        arrayAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.spinnerlocal_do, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_region.setAdapter(arrayAdapter);


//        Bundle bundle = getArguments();
//        imgUrl = bundle.getString("imgUrl");
//        uri_data = bundle.getParcelable("uri_data");

        Call<TeamListResult> result = networkService.getTeamList();
        result.enqueue(new Callback<TeamListResult>() {
            @Override
            public void onResponse(Call<TeamListResult> call, Response<TeamListResult> response) {
                if (response.isSuccessful()) {
                    list = response.body().response;
                    adapter = new TeamSearchAdapter(getContext(), list, clickEvent);
                    recyclerView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TeamListResult> call, Throwable t) {

            }
        });

        spinner_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = parent.getItemAtPosition(position).toString();
                String item = (String) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextSize(13);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamname = et_teamname.getText().toString();
                String manager = et_manager.getText().toString();
                ArrayList<TeamList> searchList = new ArrayList<>();

                // 검색버튼 누를시 자동으로 내려가게
                EditText someEditText = (EditText) getActivity().findViewById(R.id.et_teamname);
                someEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(someEditText, InputMethodManager.SHOW_IMPLICIT);
                imm.hideSoftInputFromWindow(someEditText.getWindowToken(), 0);


                for (int i = 0; i < list.size(); i++) {
                    final String list_manager = list.get(i).getManager();
                    final String list_name = list.get(i).getTeamname();
                    final String list_region = list.get(i).getRegion();

                    if ((list_manager.contains(manager) || manager.equals("")) &&
                            (list_name.contains(teamname) || teamname.equals("")) &&
                            (list_region.contains(region) || region.equals(""))) {
                        searchList.add(list.get(i));
                    }


                    if (searchList.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter = new TeamSearchAdapter(getContext(), searchList, clickEvent);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_manager.setText("");
                et_teamname.setText("");
            }
        });

        return v;
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int teamId = list.get(itemPosition).getId();

            searchResult = new SearchResult();
            searchResult.name = list.get(itemPosition).getTeamname();
            searchResult.manager = list.get(itemPosition).getManager();
            searchResult.region = list.get(itemPosition).getRegion();

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container,
//                    new SearchTeamResultFragment().getInstance(signUp, teamId, searchResult, imgUrl, uri_data));
            fragmentTransaction.replace(R.id.fragment_container,
                    new SearchTeamResultFragment().getInstance(signUp, teamId, searchResult));
            fragmentTransaction.commit();
        }
    };

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initView() {
        btn_clear = (Button) v.findViewById(R.id.btn_clear);
        btn_search = (Button) v.findViewById(R.id.btn_search);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        et_teamname = (EditText) v.findViewById(R.id.et_teamname);
        et_manager = (EditText) v.findViewById(R.id.et_manager);
        spinner_region = (Spinner) v.findViewById(R.id.spinneLocal);
        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout_team_search);
    }
}