package com.appjam.assist.assist.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.request.SignUp;
import com.appjam.assist.assist.model.response.Email;
import com.appjam.assist.assist.model.response.ResultCheck;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends BaseActivity {
    private ImageButton btn_back;
    private Button btn_next, btn_check_email;
    private EditText et_name, et_pwd, et_pwd2, et_email;
    private String pwd, pwd2, email, name;
    private NetworkService networkService;
    private boolean isCheck = false;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        initView();
        initNetwork();

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        btn_check_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 중복체크 코드
                if (et_email.equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Email email = new Email();
                        email.setEmail(et_email.getText().toString());

                        Call<ResultCheck> emailResult = networkService.checkEmail(email);
                        emailResult.enqueue(new Callback<ResultCheck>() {
                            @Override
                            public void onResponse(Call<ResultCheck> call, Response<ResultCheck> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().response.equals("noduplicate")) {
                                        isCheck = true;
                                        Toast.makeText(getApplicationContext(), "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        isCheck = false;
                                        Toast.makeText(getApplicationContext(), "이미 있는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultCheck> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid() && checkEmpty()) {
                    // 회원 데이터에 저장하고
                    SignUp.signUp.setEmail(email);
                    SignUp.signUp.setName(name);
                    SignUp.signUp.setPwd(pwd);
                    SignUp.signUp.setType(0);

                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    editor.putString("email", SignUp.signUp.getEmail());
                    editor.putString("username", SignUp.signUp.getName());
                    editor.putString("password", SignUp.signUp.getPwd());
                    editor.commit();

                    pwd = et_pwd.getText().toString();
                    pwd2 = et_pwd2.getText().toString();
                    email = et_email.getText().toString();
                    name = et_name.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), MakeTeamActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("pwd", pwd);
                    intent.putExtra("type", 0); // 일반 회원 가입

                    startActivity(intent);
                }
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private boolean checkValid() {
        pwd = et_pwd.getText().toString();
        pwd2 = et_pwd2.getText().toString();
        email = et_email.getText().toString();
        name = et_name.getText().toString();

        if (!pwd.equals(pwd2)) {
            Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", pwd)) {
            Toast.makeText(getApplicationContext(), "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isCheck == false) {
            Toast.makeText(getApplicationContext(), "이메일 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkEmpty() {
        if (name.equals("") || pwd.equals("") || pwd2.equals("") || email.equals("")) {
            Toast.makeText(getApplicationContext(), "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void initView() {
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_check_email = (Button) findViewById(R.id.check_email);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd2 = (EditText) findViewById(R.id.et_pwd2);
        linearLayout = (LinearLayout) findViewById(R.id.Linearlayaout);

    }
}
