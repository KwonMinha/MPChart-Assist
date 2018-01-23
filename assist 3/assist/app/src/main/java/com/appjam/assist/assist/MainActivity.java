package com.appjam.assist.assist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.appjam.assist.assist.card.CardActivity;
import com.appjam.assist.assist.data.DataActivity;
import com.appjam.assist.assist.login.LoginActivity;
import com.appjam.assist.assist.mypage.MyPageActivity1;
import com.appjam.assist.assist.ranking.RankingActivity;
import com.appjam.assist.assist.team.TeamMainActivity;
import com.appjam.assist.assist.util.BackPressCloseHandler;

public class MainActivity extends BaseActivity {
    BackPressCloseHandler backPressCloseHandler;
    ImageView card, data, team, ranking, mypage;
    public static int LOGOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        backPressCloseHandler = new BackPressCloseHandler(this);
        card = (ImageView) findViewById(R.id.card);
        data = (ImageView) findViewById(R.id.data);
        team = (ImageView) findViewById(R.id.team);
        ranking = (ImageView) findViewById(R.id.ranking);
        mypage = (ImageView) findViewById(R.id.mypage);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(intent);
            }
        });

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeamMainActivity.class);
                startActivity(intent);
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                startActivity(intent);
            }
        });

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity1.class);
                startActivityForResult(intent, 2000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("mytag", "main : requestcode : " + requestCode);
        if (requestCode == 2000) {
            Log.i("mytag", "main : ressult code : " + resultCode);
            if (resultCode == LOGOUT) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                SharedPreferences preferences = getSharedPreferences("appData", MODE_PRIVATE);
                //preferences.edit().remove("ID").commit();
                preferences.edit().remove("PWD").commit();
                startActivity(intent);
                finish();
            }
        }

    }

    public void changeBarColor() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
