package com.appjam.assist.assist.test;

/**
 * Created by gominju on 2017. 7. 6..
 */

public class TestActivity {
}
/*

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

public class WriteGameRecordActivity extends AppCompatActivity implements OnClickButtonId {
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
    private RecyclerView recyclerView_h;
    private WriteRecyclerAdapter_h recyclerAdapter_h;
    private Button pre_btn, next_btn;
    private LinearLayoutManager layoutManager_h;

    // 교체
    private int sub_backnum;
    private ArrayList<Member> allList;
    private int select1 = 0, select2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_game_record);

        Write1Activity = WriteGameRecordActivity.this;

        gameData = (Game) getIntent().getSerializableExtra("gameData");
        schedule_id = gameData.getId();
        init();
        initData();
        initNetwork();
        initTactic();

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
                    Toast.makeText(getApplicationContext(), "전술을 선택해주세요 ", Toast.LENGTH_SHORT).show();
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

                networkTactic(tactic);
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_field, new Fragment_442());
//                fragmentTransaction.commit();
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

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_field, new Fragment_343());
                fragmentTransaction.commit();
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

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_field, new Fragment_433());
                fragmentTransaction.commit();
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

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_field, new Fragment_352());
                fragmentTransaction.commit();
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

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_field, new Fragment_451());
                fragmentTransaction.commit();
            }
        });
    }

    private void networkTactic(String tactic) {
        Call<TacticMemberResult> result1 = networkService.getTacticMembers(schedule_id, "4-4-2");
        result1.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList();
                    setMember442();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });
    }

    private void initTactic() {

        Call<TacticMemberResult> result1 = networkService.getTacticMembers(schedule_id, "4-4-2");
        result1.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList();
                    setMember442();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });

        Call<TacticMemberResult> result2 = networkService.getTacticMembers(schedule_id, "3-4-3");
        result2.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList();
                    setMember343();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });

        Call<TacticMemberResult> result3 = networkService.getTacticMembers(schedule_id, "4-3-3");
        result3.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList();
                    setMember433();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });

        Call<TacticMemberResult> result4 = networkService.getTacticMembers(schedule_id, "3-5-2");
        result4.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList();
                    setMember352();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });

        Call<TacticMemberResult> result5 = networkService.getTacticMembers(schedule_id, "4-5-1");
        result5.enqueue(new Callback<TacticMemberResult>() {
            @Override
            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
                if (response.isSuccessful()) {
                    atk_member = response.body().response.ATK;
                    mf_member = response.body().response.MF;
                    df_member = response.body().response.DF;
                    gk_member = response.body().response.GK;
                    sub_member = response.body().response.SUB;

                    makeAllList();
                    setMember451();
                }
            }

            @Override
            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
                Log.i("mytag", "전술 실패");
            }
        });

    }

    private void makeAllList() {
        allList = new ArrayList<>();

        for (int i = 0; i < gk_member.size(); i++) {
            allList.add(new Member(gk_member.get(i).getId()));
        }
        for (int i = 0; i < atk_member.size(); i++) {
            allList.add(new Member(atk_member.get(i).getId()));
        }
        for (int i = 0; i < mf_member.size(); i++) {
            allList.add(new Member(mf_member.get(i).getId()));
        }
        for (int i = 0; i < df_member.size(); i++) {
            allList.add(new Member(df_member.get(i).getId()));
        }
        for (int i = 0; i < sub_member.size(); i++) {
            allList.add(new Member(sub_member.get(i).getId()));
        }
    }

    private void setMember451() {
        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);

        if (fragment == null) {
            fragment = new Fragment_451();
            Bundle bundle = new Bundle();
            bundle.putInt("now_fragment", nowFragment);
            bundle.putSerializable("ATK", atk_member);
            bundle.putSerializable("MF", mf_member);
            bundle.putSerializable("DF", df_member);
            bundle.putSerializable("GK", gk_member);

            fragment.setArguments(bundle);
//            fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();
        }
    }

    private void setMember442() {
        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);

        fragment = new Fragment_442();
        Bundle bundle = new Bundle();
        bundle.putInt("now_fragment", nowFragment);
        bundle.putSerializable("ATK", atk_member);
        bundle.putSerializable("MF", mf_member);
        bundle.putSerializable("DF", df_member);
        bundle.putSerializable("GK", gk_member);

        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();

    }

    private void setMember343() {
        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);

        fragment = new Fragment_343();
        Bundle bundle = new Bundle();
        bundle.putInt("now_fragment", nowFragment);
        bundle.putSerializable("ATK", atk_member);
        bundle.putSerializable("MF", mf_member);
        bundle.putSerializable("DF", df_member);
        bundle.putSerializable("GK", gk_member);

        fragment.setArguments(bundle);
//        fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();
    }

    private void setMember433() {
        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);

        fragment = new Fragment_433();
        Bundle bundle = new Bundle();
        bundle.putInt("now_fragment", nowFragment);
        bundle.putSerializable("ATK", atk_member);
        bundle.putSerializable("MF", mf_member);
        bundle.putSerializable("DF", df_member);
        bundle.putSerializable("GK", gk_member);

        fragment.setArguments(bundle);
//        fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();

    }

    private void setMember352() {
        recyclerAdapter_h.updateAdapter(sub_member);
        recyclerView_h.setAdapter(recyclerAdapter_h);

        final FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_field);

        fragment = new Fragment_352();
        Bundle bundle = new Bundle();
        bundle.putInt("now_fragment", nowFragment);
        bundle.putSerializable("ATK", atk_member);
        bundle.putSerializable("MF", mf_member);
        bundle.putSerializable("DF", df_member);
        bundle.putSerializable("GK", gk_member);

        fragment.setArguments(bundle);
//        fm.beginTransaction().replace(R.id.fragment_field, fragment).commit();

    }


//    private void networkTactic(String tactic) {
//        Call<TacticMemberResult> result = networkService.getTacticMembers(schedule_id, tactic);
//        result.enqueue(new Callback<TacticMemberResult>() {
//            @Override
//            public void onResponse(Call<TacticMemberResult> call, Response<TacticMemberResult> response) {
//                if (response.isSuccessful()) {
//                    atk_member = response.body().response.ATK;
//                    mf_member = response.body().response.MF;
//                    df_member = response.body().response.DF;
//                    gk_member = response.body().response.GK;
//                    sub_member = response.body().response.SUB;
//
//                    setMember();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TacticMemberResult> call, Throwable t) {
//                Log.i("mytag", "전술 실패");
//            }
//        });
//    }

//    private void setMember() {
//        recyclerAdapter_h.updateAdapter(sub_member);
//        recyclerView_h.setAdapter(recyclerAdapter_h);
//
//        final FragmentManager fm = getSupportFragmentManager();
//        fragment = fm.findFragmentById(R.id.fragment_field);
//
//        if (fragment == null) {
//            fragment = new TestFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("now_fragment", nowFragment);
//            bundle.putSerializable("ATK", atk_member);
//            bundle.putSerializable("MF", mf_member);
//            bundle.putSerializable("DF", df_member);
//            bundle.putSerializable("GK", gk_member);
//
//            fragment.setArguments(bundle);
//            fm.beginTransaction().replace(R.id.test_view, fragment).commit();
//        }
//
//
//    }

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
//            back_num2 = SUB.get(item).getBacknumber();
            sub_backnum = sub_member.get(item).getBacknumber();
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
        // 교체


        allList = gk, atk, mf, df,  sub를 다 넣은 배열을 만듬
        클릭을 하면 allList에서 해당하는 id를 가져와서 인덱스를 서로 바꾼다
        클릭하면 티셔츠 이미지를 바꾸고, 클릭1과 클릭2 클릭 시 인덱스를 바꾼다 (클릭1 == 클릭2 같으면 그냥 자기자신 누르는 거니깐 상관 ㄴㄴ)
         인덱스 바꾸고 바뀐 티셔츠 이미지를 원래대로 바꿔준다
        완료 버튼을 누르면 수정된 allList, tactic애 따라 다시 gk, atk, mf, df, sub로 바꿔서  WriteGameRecordAcitivty2 에 보내준다

        select1 = 처음 누른 버튼 아이디
        select2 = 두번째로 누른 버튼 아이디

        allList에서 select1, select2 아이디를 갖는 걸 찾는다,,!

        allList(4)번이랑 allList(6)번이랑 바꾸는 경우
        -> int temp = allList(6).id
           allList(6).id = allList(4).id
           allList(4).id = temp

*/
/*
    int a = 0, b = 0;

        if (select1 == 0) {
                select1 = player_id;
                } else if (select2 == 0) {
                select2 = player_id;
                }
                if (select1 == select2) {
                // 같은 사람 두번 누른 경우 -> 티셔츠 색 원래대로
                } else {
                for (int i = 0; i < allList.size(); i++) {
        if (select1 == allList.get(i).getId()) {
        a = i;
        }
        if (select2 == allList.get(i).getId()) {
        b = i;
        }
        }

        int temp = allList.get(b).getId();
        allList.get(a).setId(allList.get(b).getId());
        allList.get(b).setId(temp);
        }
        }
        }

 */