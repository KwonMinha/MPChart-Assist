package com.appjam.assist.assist.data.ViewPagerFragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.MyValueFormatter;
import com.appjam.assist.assist.model.response.Pos_GK;
import com.appjam.assist.assist.model.response.TeamMonth;
import com.appjam.assist.assist.model.response.TeamMonthResult;
import com.appjam.assist.assist.model.response.TeamPlay;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 29..
 */

public class TeamRecordSecondFragment extends Fragment {
    private View v;
    private NetworkService networkService;
    private int team_id;
    private ArrayList<TeamMonth> list;
    private LineChart lineChart;

    public TeamRecordSecondFragment newInstance(ArrayList<TeamMonth> list) {
        TeamRecordSecondFragment fragment = new TeamRecordSecondFragment();
        this.list = list;

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vp_team_second, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        list = (ArrayList<TeamMonth>) getArguments().getSerializable("data");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preferences.getInt("team_id", 0);
        lineChart = (LineChart) v.findViewById(R.id.chart);

        setGraphData(list);

        return v;
    }


    private void setGraphData(ArrayList<TeamMonth> list) {
        float score1 = 0, score2 = 0, score3 = 0, score4 = 0, score5 = 0, score6 = 0;
        float ag_score1 = 0, ag_score2 = 0, ag_score3 = 0, ag_score4 = 0, ag_score5 = 0, ag_score6 = 0;

        ArrayList<String> labels = new ArrayList<String>();
        labels.add(" ");
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("");

        int list_num = list.size();
        int cnt = 0;

        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score6 = 0;
                    ag_score6 = 0;
                    break;
                } else {
                    score6 = list.get(cnt).getAvg_score();
                    ag_score6 = list.get(cnt).getAvg_score_against();
                    cnt++;
                    break;
                }
            }

        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score5 = 0;
                    ag_score5 = 0;
                    break;
                } else {
                    score5 = list.get(cnt).getAvg_score();
                    ag_score5 = list.get(cnt).getAvg_score_against();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score4 = 0;
                    ag_score4 = 0;
                    break;
                } else {
                    score4 = list.get(cnt).getAvg_score();
                    ag_score4 = list.get(cnt).getAvg_score_against();
                    cnt++;
                    break;
                }
            }
        }

        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score3 = 0;
                    ag_score3 = 0;
                    break;
                } else {
                    score3 = list.get(cnt).getAvg_score();
                    ag_score3 = list.get(cnt).getAvg_score_against();
                    cnt++;
                    break;
                }
            }

        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score2 = 0;
                    ag_score2 = 0;
                    break;
                } else {
                    score2 = list.get(cnt).getAvg_score();
                    ag_score2 = list.get(cnt).getAvg_score_against();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score1 = 0;
                    ag_score1 = 0;
                    break;
                } else {
                    score1 = list.get(cnt).getAvg_score();
                    ag_score1 = list.get(cnt).getAvg_score_against();
                    cnt++;
                    break;
                }
            }

        }

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(score1, 1));
        entries2.add(new Entry(score2, 2));
        entries2.add(new Entry(score3, 3));
        entries2.add(new Entry(score4, 4));
        entries2.add(new Entry(score5, 5));
        entries2.add(new Entry(score6, 6));

        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(ag_score1, 1));
        entries1.add(new Entry(ag_score2, 2));
        entries1.add(new Entry(ag_score3, 3));
        entries1.add(new Entry(ag_score4, 4));
        entries1.add(new Entry(ag_score5, 5));
        entries1.add(new Entry(ag_score6, 6));


        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
        LineDataSet lineData1 = new LineDataSet(entries1, "평균 실점");
        lineData1.setColor(Color.rgb(231, 55, 112));
        lineData1.setCircleColor(Color.rgb(231, 55, 112));
        lineData1.setCircleColorHole(Color.rgb(231, 55, 112));
        lineData1.setCircleSize(3f);
        lineData1.setLineWidth(2f);

        lines.add(lineData1);
        lines.add(new LineDataSet(entries2, "평균 득점"));

        lines.get(1).setCircleColorHole(Color.rgb(70, 178, 206));
        lines.get(1).setCircleColor(Color.rgb(70, 178, 206));
        lines.get(1).setColor(Color.rgb(70, 178, 206));
        lines.get(1).setCircleSize(3f);
        lines.get(1).setLineWidth(2f);

        lineChart.setData(new LineData(labels, lines));

        lineChart.setTouchEnabled(false); // 터치 금지
        lineChart.setDragEnabled(false); // 드래그 금지

        XAxis xAxis = lineChart.getXAxis();
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();

        Legend legend = lineChart.getLegend();

        legend.setEnabled(false);

//        legend.setTextColor(Color.WHITE);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // x축 레이블 하단에 배치
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineWidth(2f);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextSize(15f);


        leftAxis.setLabelCount(6, true);
        leftAxis.setAxisMaxValue(5);
        leftAxis.setAxisMinValue(1);


        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisLineWidth(2f);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextSize(15f);

        rightAxis.setDrawLabels(false); // y우측 레이블 안보이게
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);

        lineChart.setDescription(""); // 설명 없애기
        for (int i = 0; i < 100; i++) {
            lineChart.animateX(4000, Easing.EasingOption.Linear); // 애니메이션 효과

        }
        lineChart.setBorderColor(Color.WHITE);
        lineChart.setGridBackgroundColor(Color.argb(0, 1, 42, 106));

        lineData1.setDrawValues(false); // 점 마다의 값 지우기
        lines.get(1).setDrawValues(false);


        leftAxis.setValueFormatter(new MyValueFormatter());


        ///////////////////////////////////////////////////////////////////////////////////

        lineChart.invalidate();// 적용 완료
        lineChart.notifyDataSetChanged();
    }
}