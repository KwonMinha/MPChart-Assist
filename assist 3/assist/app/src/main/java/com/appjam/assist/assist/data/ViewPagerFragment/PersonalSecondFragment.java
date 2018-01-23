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
import com.appjam.assist.assist.model.response.PlayerMonth;
import com.appjam.assist.assist.model.response.PlayerMonthResult;
import com.appjam.assist.assist.model.response.TeamMonth;
import com.appjam.assist.assist.model.response.TeamMonthResult;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class PersonalSecondFragment extends Fragment {
    private View v;
    private NetworkService networkService;
    private int player_id;
    private ArrayList<PlayerMonth> list;
    private LineChart lineChart;

    public PersonalSecondFragment newInstance(ArrayList<PlayerMonth> list) {
        PersonalSecondFragment fragment = new PersonalSecondFragment();
        this.list = list;
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vp_personal_second, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        list = (ArrayList<PlayerMonth>) getArguments().getSerializable("data");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        player_id = preferences.getInt("user_id", 0);
        lineChart = (LineChart) v.findViewById(R.id.chart);

        setGraphData(list);

        return v;
    }


    private void setGraphData(ArrayList<PlayerMonth> list) {
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
        float score1 = 0, score2 = 0, score3 = 0, score4 = 0, score5 = 0, score6 = 0;
        float assist1 = 0, assist2 = 0, assist3 = 0, assist4 = 0, assist5 = 0, assist6 = 0;
        float lose1 = 0, lose2 = 0, lose3 = 0, lose4 = 0, lose5 = 0, lose6 = 0;

        int cnt = 0;

        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score6 = 0;
                    assist6 = 0;
                    lose6 = 0;
                    break;
                } else {
                    score6 = list.get(cnt).getScore();
                    lose6 = list.get(cnt).getScore_against();
                    assist6 = list.get(cnt).getAssist();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score5 = 0;
                    assist5 = 0;
                    lose5 = 0;
                    break;
                } else {
                    score5 = list.get(cnt).getScore();
                    lose5 = list.get(cnt).getScore_against();
                    assist5 = list.get(cnt).getAssist();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score4 = 0;
                    assist4 = 0;
                    lose4 = 0;
                    break;
                } else {
                    score4 = list.get(cnt).getScore();
                    lose4 = list.get(cnt).getScore_against();
                    assist4 = list.get(cnt).getAssist();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score3 = 0;
                    assist3 = 0;
                    lose3 = 0;
                    break;
                } else {
                    score3 = list.get(cnt).getScore();
                    lose3 = list.get(cnt).getScore_against();
                    assist3 = list.get(cnt).getAssist();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score2 = 0;
                    assist2 = 0;
                    lose2 = 0;
                    break;
                } else {
                    score2 = list.get(cnt).getScore();
                    lose2 = list.get(cnt).getScore_against();
                    assist2 = list.get(cnt).getAssist();
                    cnt++;
                    break;
                }
            }
        }
        if (cnt < list_num) {
            while (true) {
                if (cnt >= list_num) {
                    score1 = 0;
                    assist1 = 0;
                    lose1 = 0;
                    break;
                } else {
                    score1 = list.get(cnt).getScore();
                    lose1 = list.get(cnt).getScore_against();
                    assist1 = list.get(cnt).getAssist();
                    cnt++;
                    break;
                }
            }
        }
        // 그래프 3번째 데이터 추가
        ArrayList<Entry> entries3 = new ArrayList<>();
        entries3.add(new Entry(lose1, 1));
        entries3.add(new Entry(lose2, 2));
        entries3.add(new Entry(lose3, 3));
        entries3.add(new Entry(lose4, 4));
        entries3.add(new Entry(lose5, 5));
        entries3.add(new Entry(lose6, 6));


        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(assist1, 1));
        entries2.add(new Entry(assist2, 2));
        entries2.add(new Entry(assist3, 3));
        entries2.add(new Entry(assist4, 4));
        entries2.add(new Entry(assist5, 5));
        entries2.add(new Entry(assist6, 6));


        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(score1, 1));
        entries1.add(new Entry(score2, 2));
        entries1.add(new Entry(score3, 3));
        entries1.add(new Entry(score4, 4));
        entries1.add(new Entry(score5, 5));
        entries1.add(new Entry(score6, 6));


        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
        LineDataSet lineData1 = new LineDataSet(entries1, "평균 득점");
        lineData1.setColor(Color.rgb(231, 55, 112));
        lineData1.setCircleColor(Color.rgb(231, 55, 112));
        lineData1.setCircleColorHole(Color.rgb(231, 55, 112));
        lineData1.setCircleSize(3f);
        lineData1.setLineWidth(2f);

        lines.add(lineData1);
        lines.add(new LineDataSet(entries2, "평균 도움"));
        lines.add(new LineDataSet(entries3, "평균 실점"));

        lines.get(1).setCircleColorHole(Color.rgb(70, 178, 206));
        lines.get(1).setCircleColor(Color.rgb(70, 178, 206));
        lines.get(1).setColor(Color.rgb(70, 178, 206));
        lines.get(1).setCircleSize(3f);
        lines.get(1).setLineWidth(2f);

        lines.get(2).setCircleColorHole(Color.rgb(254, 212, 0));
        lines.get(2).setCircleColor(Color.rgb(254, 212, 0));
        lines.get(2).setColor(Color.rgb(254, 212, 0));
        lines.get(2).setCircleSize(3f);
        lines.get(2).setLineWidth(2f);

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
        lines.get(2).setDrawValues(false);


        leftAxis.setValueFormatter(new MyValueFormatter());


        ///////////////////////////////////////////////////////////////////////////////////

        lineChart.invalidate();// 적용 완료
        lineChart.notifyDataSetChanged();
    }

}
