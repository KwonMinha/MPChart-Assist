package com.appjam.assist.assist.data;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.adapter.WriteRecyclerAdapter;
import com.appjam.assist.assist.data.adapter.WriteRecyclerAdapter2;
import com.appjam.assist.assist.data.adapter.WriteRecyclerAdapter3;
import com.appjam.assist.assist.data.adapter.WriteRecyclerAdapter_h;
import com.appjam.assist.assist.data.fragment.TestFragment;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview2;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview3;
import com.appjam.assist.assist.data.item.Itemdata_recyclerview_h;
import com.appjam.assist.assist.login.SearchResult;
import com.appjam.assist.assist.login.SearchTeamResultFragment;
import com.appjam.assist.assist.model.request.GameEvent;
import com.appjam.assist.assist.model.request.GameReport;
import com.appjam.assist.assist.model.request.GameReportResult;
import com.appjam.assist.assist.model.request.PlayerList;
import com.appjam.assist.assist.model.response.ATK_member;
import com.appjam.assist.assist.model.response.ATK_report;
import com.appjam.assist.assist.model.response.DF_member;
import com.appjam.assist.assist.model.response.DF_report;
import com.appjam.assist.assist.model.response.GK_member;
import com.appjam.assist.assist.model.response.GK_report;
import com.appjam.assist.assist.model.response.MF_member;
import com.appjam.assist.assist.model.response.MF_report;
import com.appjam.assist.assist.model.response.ResultMessage;
import com.appjam.assist.assist.model.response.SUB_member;
import com.appjam.assist.assist.model.response.SUB_report;
import com.appjam.assist.assist.model.response.TacticMemberResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WriteGameRecordActivity2 extends AppCompatActivity implements TestFragment.OnClickButtonId {
    FrameLayout test_view;
    ScrollView scrollView;

    Button goal_btn, lose_btn, help_btn, pre_btn, next_btn, topBackBtn, completeBtn;
    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView_h;

    ArrayList<Itemdata_recyclerview> itemdata;
    ArrayList<Itemdata_recyclerview2> itemdata2;
    ArrayList<Itemdata_recyclerview3> itemdata3;

    WriteRecyclerAdapter recyclerAdapter;   // score
    WriteRecyclerAdapter2 recyclerAdapter2; // assist
    WriteRecyclerAdapter3 recyclerAdapter3; // score_against
    WriteRecyclerAdapter_h recyclerAdapter_h;

    LinearLayoutManager layoutManager, layoutManager2, layoutManager3, layoutManager_h;

    Fragment fragment;
    String back_num, back_num2;

    int imgid1, imgid2, imgid3, imgNo;

    Boolean isBtn1 = false; // 경기장 플레이어 선택
    Boolean isBtn2 = false; // 후보 플레이어 선택

    int whatGoal = 0; // 1 득점 2 실점
    Boolean isAssist = false;

    int count_goal, count_con;
    int now_fragment;
    private NetworkService networkService;

    // 기록 조회할때 멤버 or 기록 전송
    private ArrayList<ATK_report> atk_report;
    private ArrayList<MF_report> mf_report;
    private ArrayList<DF_report> df_report;
    private ArrayList<GK_report> gk_report;
    private ArrayList<SUB_report> sub_report;

    // 전술에 따른 멤버
    private ArrayList<ATK_member> atk_member;
    private ArrayList<MF_member> mf_member;
    private ArrayList<DF_member> df_member;
    private ArrayList<GK_member> gk_member;
    private ArrayList<SUB_member> sub_member;

    private Game gameData;
    private TextView tv_date, tv_name1, tv_name2, tv_score1, tv_score2;
    private ImageView iv_logo1, iv_logo2;
    private int schedule_id;
    private String tactic;
    private ArrayList<GameEvent> eventList;
    private int select_player_id;
    private int select_sub_id;
    private String type;    // 기록 or 조회 구분하는 변수
    private String score_name, assist_name;
    private String get_tactic;
    private TextView title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_game_record2);

        intent = getIntent();
        tactic = intent.getStringExtra("tactic");
        schedule_id = intent.getIntExtra("schedule_id", 0);
        type = intent.getStringExtra("type");
        gameData = (Game) getIntent().getSerializableExtra("gameData");
        initNetworkBase();
        init();
        initData(); // 상단 스코어 데이터

        if (type.equals("scheduled")) {
            title.setText("경기기록조회");
            goal_btn.setVisibility(View.GONE);
            help_btn.setVisibility(View.GONE);
            lose_btn.setVisibility(View.GONE);
            completeBtn.setVisibility(View.GONE);

            initNetwork1();
        } else {
            title.setText("경기기록입력");
            goal_btn.setVisibility(View.VISIBLE);
            help_btn.setVisibility(View.VISIBLE);
            lose_btn.setVisibility(View.VISIBLE);
            completeBtn.setVisibility(View.VISIBLE);

            eventList = new ArrayList<>();
            setData2();
            // initNetwork2(tactic, schedule_id);
        }

        itemdata = new ArrayList<Itemdata_recyclerview>();
        recyclerAdapter = new WriteRecyclerAdapter(itemdata);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        // 리사이클러뷰 222222222222222222
        itemdata2 = new ArrayList<Itemdata_recyclerview2>();
        recyclerAdapter2 = new WriteRecyclerAdapter2(itemdata2);
        recyclerView2.setAdapter(recyclerAdapter2);

        // 리사이클러뷰 33333333333333333333
        itemdata3 = new ArrayList<Itemdata_recyclerview3>();
        recyclerAdapter3 = new WriteRecyclerAdapter3(itemdata3);
        recyclerView3.setAdapter(recyclerAdapter3);

        imgid1 = R.drawable.goal_gamedata;
        imgid2 = R.drawable.assist_gamedata;
        imgid3 = R.drawable.goal_conceded_gamedata;
        imgNo = R.drawable.small_navy_button;

        topBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog2();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_h.getLayoutManager().scrollToPosition(layoutManager_h.findLastVisibleItemPosition() + 1);
                //recyclerView_h.scrollToPosition(8);
            }
        });

        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_h.getLayoutManager().scrollToPosition(layoutManager_h.findFirstVisibleItemPosition() - 1);
            }
        });

        // 이건 골버튼이다 골이 눌려졌을때!!!!!!!!!어떻게해야대?어?????
        goal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBtn1 == false && isBtn2 == false) // 프래그먼트의 버튼, 후보의 버튼 둘 다 안눌렸을 때
                    Toast.makeText(getApplicationContext(), "플레이어를 선택해주세요", Toast.LENGTH_SHORT).show();
                else if (isBtn1 == true && isBtn2 == false) {// 프래그먼트의 버튼, 즉 지금 뛰는 선수를 눌렀을 때
                    itemdata.add(new Itemdata_recyclerview(back_num, imgid1));
                    GameEvent event = new GameEvent();
                    event.setType("score");
                    event.setPlayer_id(select_player_id);
                    eventList.add(event);
                    itemdata3.add(new Itemdata_recyclerview3("", imgNo));
                    whatGoal = 1;
                    isBtn1 = false;
                    Update();
                    scrollToEnd();
                } else { // 후보 선수 버튼을 눌렀을 때
                    itemdata.add(new Itemdata_recyclerview(back_num2, imgid1));
                    itemdata3.add(new Itemdata_recyclerview3("", imgNo));
                    GameEvent event = new GameEvent();
                    event.setType("score");
                    event.setPlayer_id(select_sub_id);
                    eventList.add(event);

                    whatGoal = 1;
                    isBtn2 = false;
                    Update();
                    scrollToEnd();
                }
            }
        });

        lose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isBtn1 == false && isBtn2 == false) // 프래그먼트의 버튼, 후보의 버튼 둘 다 안눌렸을 때
//                    Toast.makeText(getApplicationContext(), "플레이어를 선택해주세요", Toast.LENGTH_SHORT).show();
                if (isBtn1 == true && isBtn2 == false) // 프래그먼트의 버튼, 즉 지금 뛰는 선수를 눌렀을 때
                {
                    itemdata3.add(new Itemdata_recyclerview3("실점", imgid3));
                    itemdata.add(new Itemdata_recyclerview("", imgNo));
                    GameEvent event = new GameEvent();
                    event.setType("score_against");
//                    event.setPlayer_id(select_player_id);
                    eventList.add(event);

                    whatGoal = 2;
                    isBtn1 = false;
                    back_num = "done";
                    Update();
                    scrollToEnd();
                } else { // 후보 선수 버튼을 눌렀을 때
                    itemdata3.add(new Itemdata_recyclerview3("실점", imgid3));
                    itemdata.add(new Itemdata_recyclerview("", imgNo));
                    GameEvent event = new GameEvent();
                    event.setType("score_against");
//                    event.setPlayer_id(select_sub_id);
                    eventList.add(event);

                    whatGoal = 2;
                    isBtn2 = false;
                    back_num = "done";
                    Update();
                    scrollToEnd();
                }
            }
        });

        // 득점자와 도움자가 같을 수 없습니다
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBtn1 == false && isBtn2 == false) // 프래그먼트의 버튼, 후보의 버튼 둘 다 안눌렸을 때
                    Toast.makeText(getApplicationContext(), "플레이어를 선택해주세요", Toast.LENGTH_SHORT).show();
                else if (isBtn1 == true && isBtn2 == false) {// 프래그먼트의 버튼, 즉 지금 뛰는 선수를 눌렀을 때
                    if (whatGoal == 1) {
                        int goal_size = itemdata.size();
                        int assist_size = itemdata2.size();
                        if (goal_size > assist_size) {
                            int gap = goal_size - assist_size;
                            for (int i = 0; i < gap - 1; i++) {
                                itemdata2.add(new Itemdata_recyclerview2("", imgNo));
                            }

                            itemdata2.add(new Itemdata_recyclerview2(back_num, imgid2));
                            GameEvent event = new GameEvent();
                            event.setType("assist");
                            event.setPlayer_id(select_player_id);
                            eventList.add(event);

                            whatGoal = 0;
                            isBtn1 = false;
                            Update();
                            scrollToEnd();
                        } else if (goal_size == assist_size) {
                            itemdata2.add(new Itemdata_recyclerview2(back_num, imgid2));
                            GameEvent event = new GameEvent();
                            event.setType("assist");
                            event.setPlayer_id(select_player_id);
                            eventList.add(event);

                            whatGoal = 0;
                            isBtn1 = false;
                            Update();
                            scrollToEnd();
                        }
                    } else if (whatGoal == 2) {
                        Toast.makeText(getApplicationContext(), "득점을 먼저 입력해주세요", Toast.LENGTH_LONG).show();
                        whatGoal = 0;
                    } else {
                        Toast.makeText(getApplicationContext(), "득점을 먼저 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                } else { // 후보 선수 버튼을 눌렀을 때
                    if (whatGoal == 1) {
                        int goal_size = itemdata.size();
                        int assist_size = itemdata2.size();
                        if (goal_size > assist_size) {
                            int gap = goal_size - assist_size;
                            for (int i = 0; i < gap - 1; i++) {
                                itemdata2.add(new Itemdata_recyclerview2("", imgNo));
                            }
                            itemdata2.add(new Itemdata_recyclerview2(back_num2, imgid2));
                            GameEvent event = new GameEvent();
                            event.setType("assist");
                            event.setPlayer_id(select_sub_id);
                            eventList.add(event);

                            whatGoal = 0;
                            isBtn2 = false;
                            Update();
                            scrollToEnd();
                        } else if (goal_size == assist_size) {
                            itemdata2.add(new Itemdata_recyclerview2(back_num2, imgid2));
                            GameEvent event = new GameEvent();
                            event.setType("assist");
                            event.setPlayer_id(select_sub_id);
                            eventList.add(event);

                            whatGoal = 0;
                            isBtn2 = false;
                            Update();
                            scrollToEnd();
                        }
                    } else if (whatGoal == 2) {
                        Toast.makeText(getApplicationContext(), "득점을 먼저 입력해주세요", Toast.LENGTH_LONG).show();
                        whatGoal = 0;
                    } else {
                        Toast.makeText(getApplicationContext(), "득점을 먼저 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initData() {
        tv_date.setText(gameData.getDate().substring(0, 4) + "년 " + gameData.getDate().substring(5, 7) + "월 " + gameData.getDate().substring(8, 10) + "일");
        tv_name1.setText(gameData.getName1());
        tv_name2.setText(gameData.getName2());
        if (gameData.getScore1() == -1) {
            tv_score1.setText("-");
            tv_score2.setText("-");
        } else {
            tv_score1.setText(String.valueOf(gameData.getScore1()));
            tv_score2.setText(String.valueOf(gameData.getScore2()));
        }
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + gameData.getProfile_url())
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(iv_logo1);
    }

    private void initNetworkBase() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void initNetwork1() {
        Call<GameReportResult> result = networkService.getGameReport(schedule_id);
        result.enqueue(new Callback<GameReportResult>() {
            @Override
            public void onResponse(Call<GameReportResult> call, Response<GameReportResult> response) {
                if (response.isSuccessful()) {
                    get_tactic = response.body().response.tactic;
                    eventList = response.body().response.event;
                    atk_report = response.body().response.player.ATK;
                    mf_report = response.body().response.player.MF;
                    df_report = response.body().response.player.DF;
                    gk_report = response.body().response.player.GK;

                    initNetwork2(get_tactic, schedule_id);
                }
            }

            @Override
            public void onFailure(Call<GameReportResult> call, Throwable t) {
                Log.i("mytag", "실패");
            }
        });
    }

    private void setEventData() {
        for (int i = 0; i < eventList.size(); i++) {
            Log.i("MyTag", eventList.get(i).getType() + " " + eventList.get(i).getPlayer_id());
        }

//        recyclerAdapter = new WriteRecyclerAdapter(itemdata);
//        recyclerView.setAdapter(recyclerAdapter);
//        recyclerAdapter2 = new WriteRecyclerAdapter2(itemdata2);
//        recyclerView2.setAdapter(recyclerAdapter2);
//        recyclerAdapter3 = new WriteRecyclerAdapter3(itemdata3);
//        recyclerView3.setAdapter(recyclerAdapter3);
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getType().equals("score")) {
                score_name = getPlayerName(eventList.get(i).getPlayer_id());
                itemdata.add(new Itemdata_recyclerview(score_name, imgid1));
                itemdata2.add(new Itemdata_recyclerview2("", imgNo));
                itemdata3.add(new Itemdata_recyclerview3("", imgNo));
            }
            if (eventList.get(i).getType().equals("assist")) {
                itemdata2.remove(itemdata2.size() - 1);
                assist_name = getPlayerName(eventList.get(i).getPlayer_id());
                itemdata2.add(new Itemdata_recyclerview2(assist_name, imgid2));
            }
            if (eventList.get(i).getType().equals("score_against")) {
                itemdata.add(new Itemdata_recyclerview("", imgNo));
                itemdata2.add(new Itemdata_recyclerview2("", imgNo));
                itemdata3.add(new Itemdata_recyclerview3("실점", imgid3));
            }
            Update();
            scrollToEnd();
        }
    }

    private String getPlayerName(int id) {
        for (int i = 0; i < atk_report.size(); i++) {
            if (id == atk_report.get(i).getPlayer_id()) {
                score_name = atk_report.get(i).getUsername();
            }
        }
        for (int i = 0; i < mf_report.size(); i++) {
            if (id == mf_report.get(i).getPlayer_id()) {
                score_name = mf_report.get(i).getUsername();
            }
        }
        for (int i = 0; i < df_report.size(); i++) {
            if (id == df_report.get(i).getPlayer_id()) {
                score_name = df_report.get(i).getUsername();
            }
        }
        for (int i = 0; i < gk_report.size(); i++) {
            if (id == gk_report.get(i).getPlayer_id()) {
                score_name = gk_report.get(i).getUsername();
            }
        }
        return score_name;
    }

    private void initNetwork2(String tactic, int schedule_id) {
        Call<TacticMemberResult> result = networkService.getTacticMembers(schedule_id, tactic);
        result.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    setData1();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });
    }

    private void setData1() {
        recyclerAdapter_h = new WriteRecyclerAdapter_h(getApplicationContext(), sub_member, clickSubEvent);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.test_view);

        if (get_tactic.equals("4-4-2")) {
            now_fragment = 0;
        } else if (get_tactic.equals("3-4-3")) {
            now_fragment = 2;
        } else if (get_tactic.equals("4-3-3")) {
            now_fragment = 3;
        } else if (get_tactic.equals("3-5-2")) {
            now_fragment = 4;
        } else if (get_tactic.equals("4-5-1")) {
            now_fragment = 5;
        }

        if (fragment == null) {
            fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("now_fragment", now_fragment);
            bundle.putSerializable("ATK", atk_member);
            bundle.putSerializable("MF", mf_member);
            bundle.putSerializable("DF", df_member);
            bundle.putSerializable("GK", gk_member);

            fragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.test_view, fragment).commit();
        }

        setEventData();
    }

    private void setData2() {
        atk_member = (ArrayList<ATK_member>) intent.getSerializableExtra("atk_member");
        mf_member = (ArrayList<MF_member>) intent.getSerializableExtra("mf_member");
        df_member = (ArrayList<DF_member>) intent.getSerializableExtra("df_member");
        gk_member = (ArrayList<GK_member>) intent.getSerializableExtra("gk_member");
        sub_member = (ArrayList<SUB_member>) intent.getSerializableExtra("sub_member");

        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.test_view);

        Intent getData = getIntent();
        now_fragment = getData.getExtras().getInt("now_fragment");


        if (fragment == null) {
            fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("now_fragment", now_fragment);
            bundle.putSerializable("ATK", atk_member);
            bundle.putSerializable("MF", mf_member);
            bundle.putSerializable("DF", df_member);
            bundle.putSerializable("GK", gk_member);

            fragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.test_view, fragment).commit();
        }
    }


    public View.OnClickListener clickSubEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int item = recyclerView_h.getChildPosition(v);
//            back_num2 = SUB.get(item).getBacknumber();
            back_num2 = sub_member.get(item).getUsername();
            select_sub_id = sub_member.get(item).getId();
            isBtn2 = true;
            isBtn1 = false;
        }
    };


    @Override
    public void getButtonId(String num) {
        back_num = num;
        isBtn1 = true;
        isBtn2 = false;
    }

    @Override
    public void getPlayerId(int player_id) {
        select_player_id = player_id;
    }

    private void Update() {
        recyclerAdapter = new WriteRecyclerAdapter(itemdata);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

        recyclerAdapter2 = new WriteRecyclerAdapter2(itemdata2);
        recyclerView2.setAdapter(recyclerAdapter2);
        recyclerAdapter2.notifyDataSetChanged();

        recyclerAdapter3 = new WriteRecyclerAdapter3(itemdata3);
        recyclerView3.setAdapter(recyclerAdapter3);
        recyclerAdapter3.notifyDataSetChanged();
    }

    public void scrollToEnd() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void showDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.custom_dialog2, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        Button btn_no = (Button) dialogLayout.findViewById(R.id.btnNo);
        Button btn_yes = (Button) dialogLayout.findViewById(R.id.btnYes);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                WriteGameRecordActivity write1Activity = (WriteGameRecordActivity) WriteGameRecordActivity.Write1Activity;
                write1Activity.finish();
                finish();
            }
        });
    }

    private void showDialog2() {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.custom_dialog3, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        Button btn_no = (Button) dialogLayout.findViewById(R.id.btnNo);
        Button btn_yes = (Button) dialogLayout.findViewById(R.id.btnYes);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                networkSetGameReport();

//                count_goal = recyclerAdapter.getGoalCount();
//                count_con = recyclerAdapter3.getConCount();
//
//                Intent intent = new Intent();
//                intent.putExtra("write2_goal", count_goal);
//                intent.putExtra("write2_conceded", count_con);
//                setResult(RESULT_OK, intent);
//                finish();
            }
        });
    }

    private void networkSetGameReport() {
        makePlayer();

        GameReport report = new GameReport();
        report.player = new PlayerList();
        report.player.ATK = new ArrayList<>();
        report.player.DF = new ArrayList<>();
        report.player.GK = new ArrayList<>();
        report.player.MF = new ArrayList<>();
        report.player.SUB = new ArrayList<>();

        report.tactic = tactic;
        report.player.ATK = atk_report;
        report.player.DF = df_report;
        report.player.GK = gk_report;
        report.player.MF = mf_report;
        report.player.SUB = sub_report;
        report.event = eventList;

        Call<ResultMessage> result = networkService.setGameReport(schedule_id, report);
        result.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                if (response.isSuccessful()) {
                    count_goal = recyclerAdapter.getGoalCount();
                    count_con = recyclerAdapter3.getConCount();

                    Intent intent = new Intent();
                    intent.putExtra("write2_goal", count_goal);
                    intent.putExtra("write2_conceded", count_con);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {

            }
        });
    }

    private void makePlayer() {
        atk_report = new ArrayList<>();
        mf_report = new ArrayList<>();
        df_report = new ArrayList<>();
        gk_report = new ArrayList<>();
        sub_report = new ArrayList<>();

        for (int i = 0; i < atk_member.size(); i++) {
            atk_report.add(new ATK_report(atk_member.get(i).getId()));
        }
        for (int i = 0; i < mf_member.size(); i++) {
            mf_report.add(new MF_report(mf_member.get(i).getId()));
        }
        for (int i = 0; i < df_member.size(); i++) {
            df_report.add(new DF_report(df_member.get(i).getId()));
        }
        for (int i = 0; i < gk_member.size(); i++) {
            gk_report.add(new GK_report(gk_member.get(i).getId()));
        }
        for (int i = 0; i < sub_member.size(); i++) {
            sub_report.add(new SUB_report(sub_member.get(i).getId()));
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (!type.equals("scheduled")) {
            showDialog();
        } else {
            finish();
        }
    }

    private void init() {
        test_view = (FrameLayout) findViewById(R.id.test_view);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        goal_btn = (Button) findViewById(R.id.goalBtn);
        lose_btn = (Button) findViewById(R.id.loseBtn);
        help_btn = (Button) findViewById(R.id.helpBtn);
        pre_btn = (Button) findViewById(R.id.pre_player);
        next_btn = (Button) findViewById(R.id.next_player);
        topBackBtn = (Button) findViewById(R.id.btn_back);
        completeBtn = (Button) findViewById(R.id.completeBtn);
        tv_date = (TextView) findViewById(R.id.date);
        tv_name1 = (TextView) findViewById(R.id.name1);
        tv_name2 = (TextView) findViewById(R.id.name2);
        tv_score1 = (TextView) findViewById(R.id.score1);
        tv_score2 = (TextView) findViewById(R.id.score2);
        iv_logo1 = (ImageView) findViewById(R.id.logo1);
        iv_logo2 = (ImageView) findViewById(R.id.logo2);
        title = (TextView) findViewById(R.id.tv_title);

        // 리사이클러뷰 111111111111111111111
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView_h = (RecyclerView) findViewById(R.id.recyclerView_h);
        recyclerView_h.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager_h = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager_h.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView_h.setLayoutManager(layoutManager_h);

        recyclerAdapter_h = new WriteRecyclerAdapter_h(getApplicationContext(), sub_member, clickSubEvent);
        recyclerView_h.setAdapter(recyclerAdapter_h);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(layoutManager3);

    }
}
