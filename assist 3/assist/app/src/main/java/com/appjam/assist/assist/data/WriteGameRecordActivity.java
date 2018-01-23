package com.appjam.assist.assist.data;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.adapter.WriteRecyclerAdapter_h;
import com.appjam.assist.assist.data.fragment.Fragment_343;
import com.appjam.assist.assist.data.fragment.Fragment_352;
import com.appjam.assist.assist.data.fragment.Fragment_433;
import com.appjam.assist.assist.data.fragment.Fragment_442;
import com.appjam.assist.assist.data.fragment.Fragment_451;
import com.appjam.assist.assist.data.fragment.Fragment_notactic;
import com.appjam.assist.assist.data.fragment.TestFragment;
import com.appjam.assist.assist.model.response.ATK_member;
import com.appjam.assist.assist.model.response.ATK_report;
import com.appjam.assist.assist.model.response.DF_member;
import com.appjam.assist.assist.model.response.DF_report;
import com.appjam.assist.assist.model.response.GK_member;
import com.appjam.assist.assist.model.response.GK_report;
import com.appjam.assist.assist.model.response.MF_member;
import com.appjam.assist.assist.model.response.MF_report;
import com.appjam.assist.assist.model.response.SUB_member;
import com.appjam.assist.assist.model.response.SUB_report;
import com.appjam.assist.assist.model.response.TacticMemberResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteGameRecordActivity extends BaseActivity implements OnClickButtonId {
    public static Activity Write1Activity;

    private Button form1, form2, form3, form4, form5;
    private Button btnPre, btnNext;
    private FrameLayout form_fragment;
    private Fragment fragment;

    int goal_count;
    int conceded_count;

    int nowFragment = 0; // 442, 343, 433, 352, 451순

    static final int REQUEST_CODE = 1000;
    private TextView tv_date, tv_name1, tv_name2, tv_score1, tv_score2;
    private ImageView iv_logo1, iv_logo2;
    private Game gameData;
    private String tactic = "";
    private int schedule_id;
    private NetworkService networkService;

    // 전술에 따른 멤버
    private ArrayList<ATK_member> atk_member;
    private ArrayList<MF_member> mf_member;
    private ArrayList<DF_member> df_member;
    private ArrayList<GK_member> gk_member;
    private ArrayList<SUB_member> sub_member;

    // 선수 교체 이후 멤버
    private ArrayList<ATK_member> atk_update;
    private ArrayList<MF_member> mf_update;
    private ArrayList<DF_member> df_update;
    private ArrayList<GK_member> gk_update;
    private ArrayList<SUB_member> sub_update;

    private RecyclerView recyclerView_h;
    private WriteRecyclerAdapter_h recyclerAdapter_h;
    private Button pre_btn, next_btn;
    private LinearLayoutManager layoutManager_h;

    // 교체
    private int sub_backnum;
    private ArrayList<Member> allList;
    private int select1 = 0, select2 = 0;
    private int click_num = 0;
    private int type_num;     // 0 : 초기, 1 : 업데이트 이후

    // 교체 - 후보
    private int pre_id;
    private boolean clickSub;

    private boolean first_message = true;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MyTag", "123");
        gameData = (Game) getIntent().getSerializableExtra("gameData");
        schedule_id = gameData.getId();
        initData();
        initNetwork();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_game_record);

        Write1Activity = WriteGameRecordActivity.this;
        Toast.makeText(getApplicationContext(), "포메이션을 선택해주세요", Toast.LENGTH_SHORT).show();

        gameData = (Game) getIntent().getSerializableExtra("gameData");
        schedule_id = gameData.getId();
        init();
        initData();
        initNetwork();

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_h.getLayoutManager().scrollToPosition(layoutManager_h.findLastVisibleItemPosition() + 1);
            }
        });

        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_h.getLayoutManager().scrollToPosition(layoutManager_h.findFirstVisibleItemPosition() - 1);
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);
        if (fragment == null) {
            fragment = new Fragment_notactic();
            fm.beginTransaction()
                    .add(R.id.fragment_field, fragment).commit();
        }


        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tactic.equals("")) {
                    Toast.makeText(getApplicationContext(), "포메이션을 선택해주세요 ", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog();
                }
            }
        });

        form1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                form1.setBackgroundResource(R.drawable.formation_select_button);
                form2.setBackgroundResource(R.drawable.formation_button);
                form3.setBackgroundResource(R.drawable.formation_button);
                form4.setBackgroundResource(R.drawable.formation_button);
                form5.setBackgroundResource(R.drawable.formation_button);

                nowFragment = 1;
                tactic = "4-4-2";

                form1.setClickable(false);
                form2.setClickable(false);
                form3.setClickable(false);
                form4.setClickable(false);
                form5.setClickable(false);

                networkTactic(tactic);

            }
        });

        form2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                form2.setBackgroundResource(R.drawable.formation_select_button);
                form1.setBackgroundResource(R.drawable.formation_button);
                form3.setBackgroundResource(R.drawable.formation_button);
                form4.setBackgroundResource(R.drawable.formation_button);
                form5.setBackgroundResource(R.drawable.formation_button);

                nowFragment = 2;
                tactic = "3-4-3";
                form1.setClickable(false);
                form2.setClickable(false);
                form3.setClickable(false);
                form4.setClickable(false);
                form5.setClickable(false);

                networkTactic(tactic);
            }
        });

        form3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                form3.setBackgroundResource(R.drawable.formation_select_button);
                form2.setBackgroundResource(R.drawable.formation_button);
                form1.setBackgroundResource(R.drawable.formation_button);
                form4.setBackgroundResource(R.drawable.formation_button);
                form5.setBackgroundResource(R.drawable.formation_button);

                nowFragment = 3;
                tactic = "4-3-3";
                form1.setClickable(false);
                form2.setClickable(false);
                form3.setClickable(false);
                form4.setClickable(false);
                form5.setClickable(false);


                networkTactic(tactic);
            }
        });

        form4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form4.setBackgroundResource(R.drawable.formation_select_button);
                form2.setBackgroundResource(R.drawable.formation_button);
                form3.setBackgroundResource(R.drawable.formation_button);
                form1.setBackgroundResource(R.drawable.formation_button);
                form5.setBackgroundResource(R.drawable.formation_button);

                nowFragment = 4;
                tactic = "3-5-2";
                form1.setClickable(false);
                form2.setClickable(false);
                form3.setClickable(false);
                form4.setClickable(false);
                form5.setClickable(false);

                networkTactic(tactic);
            }
        });

        form5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form5.setBackgroundResource(R.drawable.formation_select_button);
                form2.setBackgroundResource(R.drawable.formation_button);
                form3.setBackgroundResource(R.drawable.formation_button);
                form4.setBackgroundResource(R.drawable.formation_button);
                form1.setBackgroundResource(R.drawable.formation_button);

                nowFragment = 5;
                tactic = "4-5-1";
                form1.setClickable(false);
                form2.setClickable(false);
                form3.setClickable(false);
                form4.setClickable(false);
                form5.setClickable(false);

                networkTactic(tactic);
            }
        });
    }

    private void networkTactic(final String tactic) {
        if (first_message) {
            Toast.makeText(getApplicationContext(), "교체할 선수를 선택해주세요", Toast.LENGTH_SHORT).show();
            first_message = false;
        }

        Call<TacticMemberResult> result = networkService.getTacticMembers(schedule_id, tactic);
        result.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                form1.setClickable(true);
                form2.setClickable(true);
                form3.setClickable(true);
                form4.setClickable(true);
                form5.setClickable(true);
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList(0);
                    setMember(tactic);
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });
    }

    private void makeAllList(int type_num) {
        this.type_num = type_num;
        allList = new ArrayList<>();
        if (type_num == 0) {
            Log.i("mytag", "gk_member.size :" + gk_member.size());
            for (int i = 0; i < gk_member.size(); i++) {
                allList.add(new Member(gk_member.get(i).getId(), gk_member.get(i).getUsername(), gk_member.get(i).getBacknumber()));
            }
            for (int i = 0; i < df_member.size(); i++) {
                allList.add(new Member(df_member.get(i).getId(), df_member.get(i).getUsername(), df_member.get(i).getBacknumber()));
            }
            for (int i = 0; i < mf_member.size(); i++) {
                allList.add(new Member(mf_member.get(i).getId(), mf_member.get(i).getUsername(), mf_member.get(i).getBacknumber()));
            }
            for (int i = 0; i < atk_member.size(); i++) {
                allList.add(new Member(atk_member.get(i).getId(), atk_member.get(i).getUsername(), atk_member.get(i).getBacknumber()));
            }
            for (int i = 0; i < sub_member.size(); i++) {
                allList.add(new Member(sub_member.get(i).getId(), sub_member.get(i).getUsername(), sub_member.get(i).getBacknumber()));
            }
        } else {
            Log.i("mytag", "gk_update.size :" + gk_update.size());
            for (int i = 0; i < gk_update.size(); i++) {
                allList.add(new Member(gk_update.get(i).getId(), gk_update.get(i).getUsername(), gk_update.get(i).getBacknumber()));
            }
            for (int i = 0; i < df_update.size(); i++) {
                allList.add(new Member(df_update.get(i).getId(), df_update.get(i).getUsername(), df_update.get(i).getBacknumber()));
            }
            for (int i = 0; i < mf_update.size(); i++) {
                allList.add(new Member(mf_update.get(i).getId(), mf_update.get(i).getUsername(), mf_update.get(i).getBacknumber()));
            }
            for (int i = 0; i < atk_update.size(); i++) {
                allList.add(new Member(atk_update.get(i).getId(), atk_update.get(i).getUsername(), atk_update.get(i).getBacknumber()));
            }
            for (int i = 0; i < sub_update.size(); i++) {
                allList.add(new Member(sub_update.get(i).getId(), sub_update.get(i).getUsername(), sub_update.get(i).getBacknumber()));
            }
        }
    }

    private void setMember(String tactic) {
        // TODO  후보 선수 교체
        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);

        if (tactic.equals("4-5-1")) {
            fragment = new Fragment_451();
        } else if (tactic.equals("4-4-2")) {
            fragment = new Fragment_442();
        } else if (tactic.equals("3-4-3")) {
            fragment = new Fragment_343();
        } else if (tactic.equals("3-5-2")) {
            fragment = new Fragment_352();
        } else {
            fragment = new Fragment_433();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("now_fragment", nowFragment);
        bundle.putSerializable("ATK", atk_member);
        bundle.putSerializable("MF", mf_member);
        bundle.putSerializable("DF", df_member);
        bundle.putSerializable("GK", gk_member);

        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
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

    public View.OnClickListener clickSubEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int item = recyclerView_h.getChildPosition(v);
            sub_backnum = sub_member.get(item).getBacknumber();

//            if (pre_id == v.getId() && clickSub) {
//                clickSub = false;
//                v.setBackgroundResource(R.drawable.t_shirts_reserve);
//            } else {
//                changeButtonColor();
//                v.setBackgroundResource(R.drawable.select_shirt);
//                clickSub = true;
//            }
//            pre_id = v.getId();
//
//            select_sub_id = sub_member.get(item).getId();
//            isBtn2 = true;
//            isBtn1 = false;
        }
    };

    private void init() {
        form1 = (Button) findViewById(R.id.formBtn1);
        form2 = (Button) findViewById(R.id.formBtn2);
        form3 = (Button) findViewById(R.id.formBtn3);
        form4 = (Button) findViewById(R.id.formBtn4);
        form5 = (Button) findViewById(R.id.formBtn5);
        btnPre = (Button) findViewById(R.id.previousBtn);
        btnNext = (Button) findViewById(R.id.nextBtn);
        form_fragment = (FrameLayout) findViewById(R.id.fragment_field);
        tv_date = (TextView) findViewById(R.id.date);
        tv_name1 = (TextView) findViewById(R.id.name1);
        tv_name2 = (TextView) findViewById(R.id.name2);
        tv_score1 = (TextView) findViewById(R.id.score1);
        tv_score2 = (TextView) findViewById(R.id.score2);
        iv_logo1 = (ImageView) findViewById(R.id.logo1);
        iv_logo2 = (ImageView) findViewById(R.id.logo2);
        recyclerView_h = (RecyclerView) findViewById(R.id.recyclerView_h);
        pre_btn = (Button) findViewById(R.id.pre_player);
        next_btn = (Button) findViewById(R.id.next_player);

        recyclerView_h.setHasFixedSize(true);
        layoutManager_h = new LinearLayoutManager(this);
        layoutManager_h.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_h.setLayoutManager(layoutManager_h);
        recyclerAdapter_h = new WriteRecyclerAdapter_h(getApplicationContext(), sub_member, clickSubEvent);
        recyclerView_h.setAdapter(recyclerAdapter_h);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            goal_count = data.getExtras().getInt("write2_goal");
//            conceded_count = data.getExtras().getInt("write2_conceded");


            Intent intent = new Intent();
//            intent.putExtra("write1_goal", goal_count);
//            intent.putExtra("write1_conceded", conceded_count);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void showDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.custom_dialog, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        Button btn_ok = (Button) dialogLayout.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dialogLayout.findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                Intent intent2 = new Intent(getApplicationContext(), WriteGameRecordActivity2.class);
                intent2.putExtra("now_fragment", nowFragment);
                intent2.putExtra("tactic", tactic);
                intent2.putExtra("type", "noScheduled");
                intent2.putExtra("gameData", gameData);
                intent2.putExtra("schedule_id", schedule_id);

                // 교체된 선수 목록까지 같이 보낸다,,
                if (type_num == 0) {    // 업데이트 x
                    intent2.putExtra("atk_member", atk_member);
                    intent2.putExtra("gk_member", gk_member);
                    intent2.putExtra("mf_member", mf_member);
                    intent2.putExtra("df_member", df_member);
                    intent2.putExtra("sub_member", sub_member);
                } else {
                    intent2.putExtra("atk_member", atk_update);
                    intent2.putExtra("gk_member", gk_update);
                    intent2.putExtra("mf_member", mf_update);
                    intent2.putExtra("df_member", df_update);
                    intent2.putExtra("sub_member", sub_update);
                }

                startActivityForResult(intent2, REQUEST_CODE);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
    }


    @Override
    public void getPlayerId(int player_id) {

    }


    @Override
    public void getIdAndState(int player_id, boolean touch) {
        /*
         allList = gk, atk, mf, df,  sub를 다 넣은 배열을 만듬
        클릭을 하면 allList에서 해당하는 id를 가져와서 인덱스를 서로 바꾼다
        클릭하면 티셔츠 이미지를 바꾸고, 클릭1과 클릭2 클릭 시 인덱스를 바꾼다 (클릭1 == 클릭2 같으면 그냥 자기자신 누르는 거니깐 상관 ㄴㄴ)
         인덱스 바꾸고 바뀐 티셔츠 이미지를 원래대로 바꿔준다
        완료 버튼을 누르면 수정된 allList, tactic애 따라 다시 gk, atk, mf, df, sub로 바꿔서  WriteGameRecordAcitivty2 에 보내준다
         */

        if (touch) {
            int a = 0, b = 0;
            if (click_num == 0) {
                select1 = player_id;
            } else if (click_num == 1) {
                select2 = player_id;
            }
            ++click_num;

            if (click_num == 2) {
                for (int i = 0; i < allList.size(); i++) {
                    if (select1 == allList.get(i).getId()) {
                        a = i;
                    }
                    if (select2 == allList.get(i).getId()) {
                        b = i;
                    }
                }
                click_num = 0;

                Member temp = allList.get(b);
                allList.set(b, allList.get(a));
                allList.set(a, temp);

                updateMember(tactic);
            }
        } else {
            --click_num;
        }
    }

    private void updateMember(String tactic) {
        /*
        allList 에 들어간 순서대로 다시 gk, atk, mf, df, sub를 만들어준다
        그리고 다시 해당 프레그먼트 호출해서 갱신하기

         */
        df_update = new ArrayList<>();
        mf_update = new ArrayList<>();
        atk_update = new ArrayList<>();
        gk_update = new ArrayList<>();
        sub_update = new ArrayList<>();


        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);
        int j = 0;
        if (tactic.equals("4-5-1")) {
            fragment = new Fragment_451();

            gk_update.add(0, new GK_member(allList.get(0).getId(), allList.get(0).getUsername(), allList.get(0).getBacknumber()));
            for (int i = 1; i < 5; i++) {
                df_update.add(j++, new DF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 5; i < 10; i++) {
                mf_update.add(j++, new MF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 10; i < 11; i++) {
                atk_update.add(j++, new ATK_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 11; i < allList.size(); i++) {
                sub_update.add(j++, new SUB_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;

        } else if (tactic.equals("4-4-2")) {
            fragment = new Fragment_442();

            gk_update.add(0, new GK_member(allList.get(0).getId(), allList.get(0).getUsername(), allList.get(0).getBacknumber()));
            for (int i = 1; i < 5; i++) {
                df_update.add(j++, new DF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 5; i < 9; i++) {
                mf_update.add(j++, new MF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 9; i < 11; i++) {
                atk_update.add(j++, new ATK_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 11; i < allList.size(); i++) {
                sub_update.add(j++, new SUB_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }

        } else if (tactic.equals("3-4-3")) {
            fragment = new Fragment_343();

            gk_update.add(0, new GK_member(allList.get(0).getId(), allList.get(0).getUsername(), allList.get(0).getBacknumber()));
            for (int i = 1; i < 4; i++) {
                df_update.add(j++, new DF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 4; i < 8; i++) {
                mf_update.add(j++, new MF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 8; i < 11; i++) {
                atk_update.add(j++, new ATK_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 11; i < allList.size(); i++) {
                sub_update.add(j++, new SUB_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }

        } else if (tactic.equals("3-5-2")) {
            fragment = new Fragment_352();

            gk_update.add(0, new GK_member(allList.get(0).getId(), allList.get(0).getUsername(), allList.get(0).getBacknumber()));
            for (int i = 1; i < 4; i++) {
                df_update.add(j++, new DF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 4; i < 9; i++) {
                mf_update.add(j++, new MF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 9; i < 11; i++) {
                atk_update.add(j++, new ATK_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 11; i < allList.size(); i++) {
                sub_update.add(j++, new SUB_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }

        } else {
            fragment = new Fragment_433();

            gk_update.add(0, new GK_member(allList.get(0).getId(), allList.get(0).getUsername(), allList.get(0).getBacknumber()));
            for (int i = 1; i < 5; i++) {
                df_update.add(j++, new DF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 5; i < 8; i++) {
                mf_update.add(j++, new MF_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 8; i < 11; i++) {
                atk_update.add(j++, new ATK_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
            j = 0;
            for (int i = 11; i < allList.size(); i++) {
                sub_update.add(j++, new SUB_member(allList.get(i).getId(), allList.get(i).getUsername(), allList.get(i).getBacknumber()));
            }
        }
        recyclerAdapter_h.updateAdapter(sub_update);
        recyclerView_h.setAdapter(recyclerAdapter_h);
        recyclerAdapter_h.notifyDataSetChanged();


        Bundle bundle = new Bundle();
        bundle.putInt("now_fragment", nowFragment);
        bundle.putSerializable("ATK", atk_update);
        bundle.putSerializable("MF", mf_update);
        bundle.putSerializable("DF", df_update);
        bundle.putSerializable("GK", gk_update);

        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();

        makeAllList(1);
    }
}