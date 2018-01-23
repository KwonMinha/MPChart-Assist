package com.appjam.assist.assist.data;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.MainActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.fragment.GameRecordFragment;
import com.appjam.assist.assist.data.fragment.PersonalRecordFragment;
import com.appjam.assist.assist.data.fragment.TeamRecordFragment;

public class DataActivity extends BaseActivity {
    private Button btn_home, btn_game, btn_team, btn_personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        initView();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new GameRecordFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_game.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_team.setTextColor(getResources().getColor(R.color.colorTextGray));
                btn_personal.setTextColor(getResources().getColor(R.color.colorTextGray));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new GameRecordFragment());
                fragmentTransaction.commit();
            }
        });

        btn_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_game.setTextColor(getResources().getColor(R.color.colorTextGray));
                btn_team.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_personal.setTextColor(getResources().getColor(R.color.colorTextGray));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new TeamRecordFragment());
                fragmentTransaction.commit();
            }
        });

        btn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_game.setTextColor(getResources().getColor(R.color.colorTextGray));
                btn_team.setTextColor(getResources().getColor(R.color.colorTextGray));
                btn_personal.setTextColor(getResources().getColor(R.color.colorWhite));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace( R.id.fragment_container, new PersonalRecordFragment());
                fragmentTransaction.commit();
            }
        });
    }

    private void initView() {
        btn_home = (Button) findViewById(R.id.btn_home);
        btn_game = (Button) findViewById(R.id.btn_game);
        btn_team = (Button) findViewById(R.id.btn_team);
        btn_personal = (Button) findViewById(R.id.btn_personal);

        btn_game.setTextColor(getResources().getColor(R.color.colorWhite));
        btn_team.setTextColor(getResources().getColor(R.color.colorTextGray));
        btn_personal.setTextColor(getResources().getColor(R.color.colorTextGray));
    }
}
