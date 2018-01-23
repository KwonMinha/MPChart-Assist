package com.appjam.assist.assist.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.MainActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.login.LoginActivity;
import com.appjam.assist.assist.model.response.Player;
import com.appjam.assist.assist.model.response.PlayerResult;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPageActivity1 extends BaseActivity {
    private Button adjust_btn;
    private ImageButton home_btn, mypage_logout;
    private TextView mypage1_sign_up_name, mypage1_sign_up_email, mypage1_sign_up_age, mypage1_sign_up_height,
            mypage1_sign_up_weight, mypage1_sign_up_foot, mypage1_sign_up_number, mypage1_sign_up_position, mypage1_sign_up_deposition;
    private ImageView mypage_image1, mypage_image2;
    final static int CODE = 1;
    private NetworkService networkService;
    private int player_id;
    private Player player;
    private TeamProfile team;
    public static int LOGOUT = 1000;
    private LinearLayout lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage1);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        player_id = preferences.getInt("user_id", 0);

        initView();
        initNetwork();

        lin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        mypage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(LOGOUT, intent);
                finish();
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        adjust_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity1.this, MyPageActivity2.class);
                intent.putExtra("name", mypage1_sign_up_name.getText().toString());
                intent.putExtra("email", mypage1_sign_up_email.getText().toString());
                intent.putExtra("age", mypage1_sign_up_age.getText().toString());
                intent.putExtra("height", mypage1_sign_up_height.getText().toString());
                intent.putExtra("weight", mypage1_sign_up_weight.getText().toString());
                intent.putExtra("foot", mypage1_sign_up_foot.getText().toString());
                intent.putExtra("number", mypage1_sign_up_number.getText().toString());
                intent.putExtra("position", mypage1_sign_up_position.getText().toString());
                intent.putExtra("deposition", mypage1_sign_up_deposition.getText().toString());

                if (mypage1_sign_up_position.getText().toString().equals("DEF")) {
                    intent.putExtra("count", 0);

                    if (mypage1_sign_up_deposition.getText().toString().equals("CD"))
                        intent.putExtra("decount", 0);
                    if (mypage1_sign_up_deposition.getText().toString().equals("FB"))
                        intent.putExtra("decount", 1);
                    if (mypage1_sign_up_deposition.getText().toString().equals("WB"))
                        intent.putExtra("decount", 2);
                } else if (mypage1_sign_up_position.getText().toString().equals("GK")) {
                    intent.putExtra("count", 1);

                    if (mypage1_sign_up_deposition.getText().toString().equals("GK"))
                        intent.putExtra("decount", 0);

                } else if (mypage1_sign_up_position.getText().toString().equals("ATK")) {
                    intent.putExtra("count", 2);

                    if (mypage1_sign_up_deposition.getText().toString().equals("ST)"))
                        intent.putExtra("decount", 0);
                    if (mypage1_sign_up_deposition.getText().toString().equals("LW"))
                        intent.putExtra("decount", 1);
                    if (mypage1_sign_up_deposition.getText().toString().equals("RW"))
                        intent.putExtra("decount", 1);
                    if (mypage1_sign_up_deposition.getText().toString().equals("CF"))
                        intent.putExtra("decount", 2);
                } else if (mypage1_sign_up_position.getText().toString().equals("MF")) {
                    intent.putExtra("count", 3);

                    if (mypage1_sign_up_deposition.getText().toString().equals("AM)"))
                        intent.putExtra("decount", 0);
                    if (mypage1_sign_up_deposition.getText().toString().equals("DM"))
                        intent.putExtra("decount", 1);
                    if (mypage1_sign_up_deposition.getText().toString().equals("WM)"))
                        intent.putExtra("decount", 2);
                }
                startActivityForResult(intent, CODE);
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();


        Call<PlayerResult> resultCall = networkService.getPlayerInfo(player_id);
        resultCall.enqueue(new Callback<PlayerResult>() {
            @Override
            public void onResponse(Call<PlayerResult> call, Response<PlayerResult> response) {
                if (response.isSuccessful()) {
                    player = response.body().response;

                    Log.i("mytag", "MypageActiivty1, team_id : " + player.getTeam_id());
                    networkTeam(player.getTeam_id());
                    setData();
                }
            }

            @Override
            public void onFailure(Call<PlayerResult> call, Throwable t) {

            }
        });
    }

    private void networkTeam(int team_id) {

        Call<TeamProfileResult> result = networkService.getTeamProfile(team_id);
        result.enqueue(new Callback<TeamProfileResult>() {
            @Override
            public void onResponse(Call<TeamProfileResult> call, Response<TeamProfileResult> response) {
                if (response.isSuccessful()) {
                    team = response.body().response;
                    setTeamData();
                }
            }

            @Override
            public void onFailure(Call<TeamProfileResult> call, Throwable t) {
            }
        });
    }

    private void setTeamData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + team.getProfile_pic_url())
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(mypage_image1);
    }

    private void setData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/player/" + player.getProfile_pic_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(mypage_image2);

        mypage1_sign_up_name.setText(player.getUsername());
        mypage1_sign_up_email.setText(player.getEmail());
        mypage1_sign_up_position.setText(player.getPosition());
        mypage1_sign_up_deposition.setText(player.getPosition_detail());
        mypage1_sign_up_age.setText(String.valueOf(player.getAge()));
        mypage1_sign_up_height.setText(String.valueOf(player.getHeight()));
        mypage1_sign_up_weight.setText(String.valueOf(player.getWeight()));
        mypage1_sign_up_foot.setText(player.getFoot());
        mypage1_sign_up_number.setText(String.valueOf(player.getBacknumber()));
    }

    private void initView() {
        home_btn = (ImageButton) findViewById(R.id.mypage_back_btn);
        adjust_btn = (Button) findViewById(R.id.mypage_finish_btn);
        mypage_logout = (ImageButton) findViewById(R.id.mypage_logout);

        mypage1_sign_up_name = (TextView) findViewById(R.id.mypage1_sign_up_name);
        mypage1_sign_up_email = (TextView) findViewById(R.id.mypage1_sign_up_email);
        mypage1_sign_up_age = (TextView) findViewById(R.id.mypage1_sign_up_age);
        mypage1_sign_up_height = (TextView) findViewById(R.id.mypage1_sign_up_height);
        mypage1_sign_up_weight = (TextView) findViewById(R.id.mypage1_sign_up_weight);
        mypage1_sign_up_foot = (TextView) findViewById(R.id.mypage1_sign_up_foot);
        mypage1_sign_up_number = (TextView) findViewById(R.id.mypage1_sign_up_number);
        mypage1_sign_up_position = (TextView) findViewById(R.id.mypage1_sign_up_position);
        mypage1_sign_up_deposition = (TextView) findViewById(R.id.mypage1_sign_up_deposition);

        mypage_image1 = (ImageView) findViewById(R.id.mypage_image1);
        mypage_image2 = (ImageView) findViewById(R.id.mypage_image2);
        lin = (LinearLayout)findViewById(R.id.activity_my_page);
        //mypage_image2.setBackground(new ShapeDrawable(new OvalShape()));
        //mypage_image2.setClipToOutline(true);
    }
}

