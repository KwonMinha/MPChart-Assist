package com.appjam.assist.assist.team;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.MainActivity;
import com.appjam.assist.assist.R;


public class TeamMainActivity extends BaseActivity {
    static final int ADD_SCHEDULE = 1000;
    Button  btn_main, btn_schedule, btn_member;
    private Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        btn_home = (Button) findViewById(R.id.btn_home);
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_schedule = (Button) findViewById(R.id.btn_schedule);
        btn_member = (Button) findViewById(R.id.btn_member);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new TeamMainFragment().newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
        btn_main.setTextColor(getResources().getColor(R.color.colorWhite));
        btn_schedule.setTextColor(getResources().getColor(R.color.colorGray));
        btn_member.setTextColor(getResources().getColor(R.color.colorGray));

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_main.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_schedule.setTextColor(getResources().getColor(R.color.colorGray));
                btn_member.setTextColor(getResources().getColor(R.color.colorGray));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new TeamMainFragment());
                fragmentTransaction.commit();
            }
        });

        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_main.setTextColor(getResources().getColor(R.color.colorGray));
                btn_schedule.setTextColor(getResources().getColor(R.color.colorGray));
                btn_member.setTextColor(getResources().getColor(R.color.colorWhite));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new TeamMemberFragment());
                fragmentTransaction.commit();
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_main.setTextColor(getResources().getColor(R.color.colorGray));
                btn_schedule.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_member.setTextColor(getResources().getColor(R.color.colorGray));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ScheduleFragment());
                fragmentTransaction.commit();
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
}
