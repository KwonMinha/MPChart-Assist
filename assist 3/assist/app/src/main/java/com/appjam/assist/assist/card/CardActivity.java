package com.appjam.assist.assist.card;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.MainActivity;
import com.appjam.assist.assist.R;
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
import retrofit2.http.Path;

import static com.appjam.assist.assist.R.id.team;

public class CardActivity extends BaseActivity {
    private TextView tv_score, tv_atk, tv_pac, tv_tec, tv_def, tv_sta, tv_phy, tv_name;
    private ImageView iv_profile, iv_logo, background;
    private NetworkService networkService;
    private int player_id;
    private Player player;
    private TeamProfile team;
    private Button btn_home;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        player_id = preferences.getInt("user_id", 0);

        initView();
        initNetwork();

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        layout.startAnimation(animation);



        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void changeBarColor() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
        Call<PlayerResult> resultCall = networkService.getPlayerInfo(player_id);
        resultCall.enqueue(new Callback<PlayerResult>() {
            @Override
            public void onResponse(Call<PlayerResult> call, Response<PlayerResult> response) {
                if (response.isSuccessful()) {
                    player = response.body().response;
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
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(iv_logo);
    }

    private void setData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/player/" + player.getProfile_pic_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(iv_profile);


        int atk = player.getCard_ATK();
        int pac = player.getCard_PAC();
        int tec = player.getCard_TEC();
        int sta = player.getCard_STA();
        int def = player.getCard_DEF();
        int phy = player.getCard_PHY();
        int avg = (atk + pac + tec + sta + def + phy) / 6;
        tv_atk.setText(String.valueOf(atk));
        tv_pac.setText(String.valueOf(pac));
        tv_tec.setText(String.valueOf(tec));
        tv_sta.setText(String.valueOf(sta));
        tv_def.setText(String.valueOf(def));
        tv_phy.setText(String.valueOf(phy));
        tv_score.setText(String.valueOf(avg));
        tv_name.setText(player.getUsername());
    }

    private void initView() {
        tv_score = (TextView) findViewById(R.id.card_score);
        tv_atk = (TextView) findViewById(R.id.card_atk);
        tv_pac = (TextView) findViewById(R.id.card_pac);
        tv_tec = (TextView) findViewById(R.id.card_tec);
        tv_sta = (TextView) findViewById(R.id.card_sta);
        tv_def = (TextView) findViewById(R.id.card_def);
        tv_phy = (TextView) findViewById(R.id.card_phy);
        iv_profile = (ImageView) findViewById(R.id.card_img);
        iv_logo = (ImageView) findViewById(R.id.tema_logo);
        btn_home = (Button) findViewById(R.id.btn_home);
        tv_name = (TextView)findViewById(R.id.tv_name);
        background = (ImageView)findViewById(R.id.background);
        layout = (RelativeLayout)findViewById(R.id.all_layout);
    }
}
