package com.appjam.assist.assist.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.request.SignUp;

public class MakeTeamActivity extends BaseActivity {
    private Button btn_search, btn_make, btn_back;
    private String name, email, pwd, token;
    private int type;
    //, foot, position, detail_position;
    //int age, backnum;
    //float weight, height;
    //public String imgUrl;
    //public Uri uri_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        btn_search = (Button) findViewById(R.id.btn__search);
        btn_make = (Button) findViewById(R.id.btn_make);
        btn_back = (Button) findViewById(R.id.btn_back);

        btn_search.setTextColor(getResources().getColor(R.color.colorWhite));
        btn_make.setTextColor(getResources().getColor(R.color.colorTextGray));

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        type = intent.getIntExtra("type", 0);

        if (type == 1) {
            token = intent.getStringExtra("token");
            SignUp.signUp.setToken(token);
            SignUp.signUp.setType(1);
        } else {
            pwd = intent.getStringExtra("pwd");
            SignUp.signUp.setType(0);
            SignUp.signUp.setPwd(pwd);
        }

        SignUp.signUp.setName(name);
        SignUp.signUp.setEmail(email);

        Log.i("mytag", "MakeTeamActivity, type = " + SignUp.signUp.getType());

//        SignUp.signUp.setMain_foot(foot);
//        SignUp.signUp.setDetail_position(detail_position);
//        SignUp.signUp.setPosition(position);
//        SignUp.signUp.setAge(age);
//        SignUp.signUp.setBack_num(backnum);
//        SignUp.signUp.setWeight(weight);
//        SignUp.signUp.setHeight(height);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

//        if (fragment == null) {
//            fragment = new SearchTeamFragment().getInstance(SignUp.signUp, imgUrl, uri_data);
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment).commit();
//        }
        if (fragment == null) {
            fragment = new SearchTeamFragment().getInstance(SignUp.signUp);
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        btn_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_search.setTextColor(getResources().getColor(R.color.colorTextGray));
                btn_make.setTextColor(getResources().getColor(R.color.colorWhite));

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new MakeTeamFragment().getInstance(SignUp.signUp, imgUrl, uri_data));
                fragmentTransaction.replace(R.id.fragment_container, new MakeTeamFragment().getInstance(SignUp.signUp));
                fragmentTransaction.commit();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_search.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_make.setTextColor(getResources().getColor(R.color.colorTextGray));
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new SearchTeamFragment().getInstance(SignUp.signUp, imgUrl, uri_data));
                fragmentTransaction.replace(R.id.fragment_container, new SearchTeamFragment().getInstance(SignUp.signUp));
                fragmentTransaction.commit();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
