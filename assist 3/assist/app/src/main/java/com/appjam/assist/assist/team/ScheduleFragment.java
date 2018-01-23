package com.appjam.assist.assist.team;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.request.Attend;
import com.appjam.assist.assist.model.response.AttendListResult;
import com.appjam.assist.assist.model.response.AttendMember;
import com.appjam.assist.assist.model.response.NoAttendMember;
import com.appjam.assist.assist.model.response.ScheduleResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 2017. 6. 26..
 */

public class ScheduleFragment extends Fragment {
    private View v;
    private Button add_schedule, btn_pre, btn_next;
    public GregorianCalendar cal_month, cal_month_copy;
    private TextView tv_month_year;
    private GridView gridView;
    private CalendarAdapter cal_adapter;
    private String selectedGridDate;
    private LinearLayout layout_noSchedule, layout_schedule, layout_basic;
    private boolean isScheduled;
    private TextView tv_where, tv_when, tv_other, tv_content;
    private Schedule schedule;
    private NetworkService networkService;
    private int team_id;
    private int cur_month, cur_year;
    private ArrayList<Schedule> list;
    private Button btn_attend, btn_no_attend;
    private int schedule_id;
    private RecyclerView recycler_attend, recycler_no_attend;
    private LinearLayoutManager layoutManager;
    private AttendListAdapter attendAdapter;
    private NoAttendListAdapter noAttendAdapter;
    private LinearLayout recycler_layout;
    private int player_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_schedule, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        team_id = preference.getInt("team_id", 0);
        player_id = preference.getInt("user_id", 0);

        init();
        initNetwork();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String cur_date = sdf.format(date);
        cur_month = Integer.parseInt(cur_date.substring(5, 7));
        cur_year = Integer.parseInt(cur_date.substring(0, 4));
        getScheduleList(team_id, cur_year, cur_month);

        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CalendarAdapter) parent.getAdapter()).setSelected(v, position);
                selectedGridDate = CalendarAdapter.day_string.get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }

                ((CalendarAdapter) parent.getAdapter()).setSelectedPosition(selectedGridDate);
                ((CalendarAdapter) parent.getAdapter()).setSelected(v, position);
                isScheduled = ((CalendarAdapter) parent.getAdapter()).isScheduled(selectedGridDate);
                setLayout(isScheduled);
                cal_adapter.notifyDataSetChanged();
            }
        });


        add_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String getTime = sdf.format(date);
                String input_date = (selectedGridDate == null) ? getTime : selectedGridDate;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AddScheduleFragment().newInstance(input_date));
                fragmentTransaction.commit();
            }
        });

        btn_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_attend.setBackgroundResource(R.drawable.participation_pink_button);
                btn_no_attend.setBackgroundResource(R.drawable.nonparticipation_blue_button);
                networkAttend(schedule_id, "attend");
            }
        });

        btn_no_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_no_attend.setBackgroundResource(R.drawable.nonparticipation_pink_button);
                btn_attend.setBackgroundResource(R.drawable.participation_blue_button);
                networkAttend(schedule_id, "noattend");
            }
        });

        return v;
    }

    private void networkAttend(final int schedule_id, String msg) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        int player_id = preference.getInt("user_id", 0);

        Attend attend = new Attend();
        attend.setPlayer_id(player_id);
        attend.setAttendance(msg);

        Call<Void> result = networkService.setAttend(schedule_id, attend);
        result.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    networkAttendList(schedule_id);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    private void networkAttendList(int schedule_id) {
        Call<AttendListResult> result = networkService.getAttendList(schedule_id);
        result.enqueue(new Callback<AttendListResult>() {
            @Override
            public void onResponse(Call<AttendListResult> call, Response<AttendListResult> response) {
                if (response.isSuccessful()) {
                    ArrayList<AttendMember> attendList = response.body().response.attend;
                    ArrayList<NoAttendMember> noAttendList = response.body().response.noattend;

                    attendAdapter = new AttendListAdapter(getContext(), attendList);
                    noAttendAdapter = new NoAttendListAdapter(getContext(), noAttendList);
                    recycler_attend.setAdapter(attendAdapter);
                    recycler_no_attend.setAdapter(noAttendAdapter);
                    attendAdapter.notifyDataSetChanged();
                    noAttendAdapter.notifyDataSetChanged();
                    recycler_layout.setVisibility(View.VISIBLE);

                    for (int i = 0; i < attendList.size(); i++) {
                        if (player_id == attendList.get(i).getId()) {
                            btn_attend.setBackgroundResource(R.drawable.participation_pink_button);
                            btn_no_attend.setBackgroundResource(R.drawable.nonparticipation_blue_button);
                        }
                    }
                    for (int i = 0; i < noAttendList.size(); i++) {
                        if (player_id == noAttendList.get(i).getId()) {
                            btn_attend.setBackgroundResource(R.drawable.participation_blue_button);
                            btn_no_attend.setBackgroundResource(R.drawable.nonparticipation_pink_button);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<AttendListResult> call, Throwable t) {
            }
        });
    }

    private void getScheduleList(int team_id, int cur_year, int cur_month) {
        Call<ScheduleResult> result = networkService.getMonthSchedule(team_id, cur_year, cur_month);
        result.enqueue(new Callback<ScheduleResult>() {
            @Override
            public void onResponse(Call<ScheduleResult> call, Response<ScheduleResult> response) {
                if (response.isSuccessful()) {
                    list = response.body().response;
                    cal_adapter = new CalendarAdapter(getContext(), cal_month, list);
                    gridView.setAdapter(cal_adapter);
                    cal_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ScheduleResult> call, Throwable t) {
            }
        });
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void setLayout(boolean isScheduled) {
        if (isScheduled) {  // 일정이 있는 경우
            layout_schedule.setVisibility(View.VISIBLE);
            layout_noSchedule.setVisibility(View.GONE);
            layout_basic.setVisibility(View.GONE);

            for (int i = 0; i < list.size(); i++) {
                Schedule item = list.get(i);
                String date = item.getGame_dt().substring(0, 10);
                String time = item.getGame_dt().substring(11, 13) + "시 " + item.getGame_dt().substring(14, 16) + "분 ";
                if (date.equals(selectedGridDate)) {
                    tv_when.setText(date + " " + time);
                    tv_where.setText(item.getPlace());
                    tv_other.setText(item.getAgainst_team());
                    tv_content.setText(item.getMessage());
                    schedule_id = item.getId();

                    // 참가 불참가 인원 가져와서, 버튼 색 표시하기
                    btn_attend.setBackgroundResource(R.drawable.participation_blue_button);
                    btn_no_attend.setBackgroundResource(R.drawable.nonparticipation_blue_button);
                    networkAttendList(schedule_id);
                }
            }
        } else {
            layout_noSchedule.setVisibility(View.VISIBLE);
            layout_schedule.setVisibility(View.GONE);
            layout_basic.setVisibility(View.GONE);
        }
    }

    private void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    private void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month_year.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

//    public String makeMonth(String MMMM) {
//        String month = "";
//        if (MMMM.equals("January")) {
//            month =  "1";
//        }
//        return month;
//    }

    private void init() {
        add_schedule = (Button) v.findViewById(R.id.btn_add);
        btn_pre = (Button) v.findViewById(R.id.btn_pre);
        btn_next = (Button) v.findViewById(R.id.btn_next);

        gridView = (GridView) v.findViewById(R.id.gv_calendar);
        tv_when = (TextView) v.findViewById(R.id.tv_date);
        tv_where = (TextView) v.findViewById(R.id.tv_place);
        tv_other = (TextView) v.findViewById(R.id.tv_other);
        tv_content = (TextView) v.findViewById(R.id.tv_content);
        layout_noSchedule = (LinearLayout) v.findViewById(R.id.layout_no_schedule);
        layout_schedule = (LinearLayout) v.findViewById(R.id.layout_schedule);
        layout_basic = (LinearLayout) v.findViewById(R.id.layout_basic);
        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        tv_month_year = (TextView) v.findViewById(R.id.tv_month_year);
        btn_attend = (Button) v.findViewById(R.id.btn_attend);
        btn_no_attend = (Button) v.findViewById(R.id.btn_no_attend);

        btn_attend.setMinWidth(0);
        btn_attend.setMinHeight(0);
        btn_attend.setMinimumWidth(0);
        btn_attend.setMinimumHeight(0);
        btn_attend.setPadding(0,0,0,0);

        btn_no_attend.setMinWidth(0);
        btn_no_attend.setMinHeight(0);
        btn_no_attend.setMinimumWidth(0);
        btn_no_attend.setMinimumHeight(0);
        btn_no_attend.setPadding(0,0,0,0);

        tv_month_year.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        recycler_attend = (RecyclerView) v.findViewById(R.id.attend_recycler);
        recycler_no_attend = (RecyclerView) v.findViewById(R.id.no_attend_recycler);
        recycler_attend.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_attend.setLayoutManager(layoutManager);
        recycler_no_attend.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_no_attend.setLayoutManager(layoutManager);
        recycler_layout = (LinearLayout) v.findViewById(R.id.recycler_layout);
    }
}