package com.appjam.assist.assist.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.MainActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.SplashActivity;
import com.appjam.assist.assist.model.request.Login;
import com.appjam.assist.assist.model.response.NomalLoginResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends FragmentActivity {
    private CallbackManager callbackManager;
    private LoginButton btn_facebook;
    private Button btn_login, btn_signup;
    private EditText et_id, et_pwd;
    private NetworkService networkService;
    private String email, pwd;
    private CheckBox check_id;
    private SharedPreferences preferences, preferences2;
    private boolean saveLoginId, saveAutoLogin;
    private String savedId, savedPwd;
    private LinearLayout lin;
    private CheckBox check_auto_login;
    private boolean loginChecked;
    private SharedPreferences.Editor editor, editor2;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);
        initView();
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();
        initNetwork();

        // 스플래쉬
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        LoginManager.getInstance().logOut();


        preferences = getSharedPreferences("appData", MODE_PRIVATE);
        preferences2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        editor2 = preferences2.edit();
        loadStoredInfo();

        if (saveLoginId) {
            et_id.setText(savedId);
            check_id.setChecked(saveLoginId);
        }

        if (saveAutoLogin) {
            et_id.setText(savedId);
            et_pwd.setText(savedPwd);
            check_auto_login.setChecked(saveAutoLogin);
            loginChecked = true;

//            if (loginValidation(savedId, savedPwd)) {
                Login login = new Login();
                login.setEmail(savedId);
                login.setPassword(savedPwd);
                login.setToken("");

                networkLogin(login);
//            }
        }

        check_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginChecked = true;
                } else {
                    // if unChecked, removeAll
                    loginChecked = false;
                    editor.clear();
                    editor.commit();
                }
            }
        });


        lin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFacebook();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {

                    Login login = new Login();
                    login.setEmail(email);
                    login.setPassword(pwd);
                    login.setToken("");
                    networkLogin(login);
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void networkLogin(Login login) {
        Call<NomalLoginResult> result = networkService.getLoginResult(login);
        result.enqueue(new Callback<NomalLoginResult>() {
            @Override
            public void onResponse(Call<NomalLoginResult> call, Response<NomalLoginResult> response) {
                if (response.isSuccessful()) {

                    if (loginChecked) {
                        editor.putBoolean("SAVE_LOGIN_AUTO", true);
                        editor.putString("ID", et_id.getText().toString());
                        editor.putString("PWD", et_pwd.getText().toString());
                        editor.putBoolean("SAVE_LOGIN_AUTO", loginChecked);
                        editor.commit();
                    } else {
                        editor.putBoolean("SAVE_LOGIN_AUTO", false);
                        editor.commit();
                    }
                    editor2.putInt("user_id", response.body().response.id);
                    editor2.putInt("team_id", response.body().response.team_id);
                    editor2.commit();
                    saveId();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<NomalLoginResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
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

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void saveId() {
        editor.putBoolean("SAVE_LOGIN_DATA", check_id.isChecked());
        editor.putString("ID", et_id.getText().toString());
        editor.apply();
    }

    private void loadStoredInfo() {
        saveLoginId = preferences.getBoolean("SAVE_LOGIN_DATA", false);
        saveAutoLogin = preferences.getBoolean("SAVE_LOGIN_AUTO", false);
        savedId = preferences.getString("ID", "");
        savedPwd = preferences.getString("PWD", "");

    }

    private boolean checkValid() {
        //Todo 이메일 형식 검
        email = et_id.getText().toString();
        pwd = et_pwd.getText().toString();

//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(LoginActivity.this, "이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        //else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", pwd)) {
        //Toast.makeText(LoginActivity.this, "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
        //return false;
        // }
//        else {
//            return true;
//        }
        return true;
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void initFacebook() {
        callbackManager = CallbackManager.Factory.create();

        btn_facebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        //LoginManager.getInstance().logInWithReadPermissions(getApplicationContext(), Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        String id = "";
                                        String name = "";
                                        String pwd = "";
                                        try {
                                            id = object.getString("id");
                                            name = object.getString("name");
                                            if (!object.has("email"))
                                                email = id;
                                            else
                                                email = object.getString("email");

                                            // App code
                                            Log.i("mytag", "LoginActivity, 성공, id : " + id);
                                            final Login login = new Login();
                                            login.setEmail(email);
//                                            login.setToken(loginResult.getAccessToken().getCurrentAccessToken().getToken());
                                            login.setToken(id);
//                                            login.setName(name);
                                            login.setPassword(pwd);
                                            Call<NomalLoginResult> result = networkService.getLoginResult(login);
                                            final String finalName = name;
                                            final String finalId = id;
                                            result.enqueue(new Callback<NomalLoginResult>() {
                                                @Override
                                                public void onResponse(Call<NomalLoginResult> call, Response<NomalLoginResult> response) {
                                                    if (response.isSuccessful()) {
                                                        Log.i("mytag", "페이스북 로그인 성공");
                                                        SharedPreferences.Editor editor3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                                        editor3.putInt("user_id", response.body().response.id);
                                                        Log.i("mytag", "facebook player_id : " + response.body().response.id);
                                                        editor3.putInt("team_id", response.body().response.team_id);
                                                        editor3.commit();
                                                        saveId();

                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);

                                                    } else {
                                                        Log.i("mytag", "페이스북으로 회원가입 안한 사람,  ");
                                                        Intent intent = new Intent(getApplicationContext(), MakeTeamActivity.class);
                                                        intent.putExtra("type", 1); // type 0 : 일반가입, 1 : 페이스북
                                                        intent.putExtra("token", finalId);
                                                        intent.putExtra("name", finalName);
                                                        intent.putExtra("email", login.getEmail());
                                                        startActivity(intent);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<NomalLoginResult> call, Throwable t) {
                                                    Log.i("mytag", "페이스북 로그인 실패");
                                                    LoginManager.getInstance().logOut();
                                                }
                                            });

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email, picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i("mytag", "LoginActivity, 취소");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                        Log.e("mytag", "LoginActivity, 에러" + exception.getLocalizedMessage());

                    }
                });
    }

    private void initView() {
        btn_facebook = (LoginButton) findViewById(R.id.btn_facebook);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        et_id = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_password);
        check_id = (CheckBox) findViewById(R.id.check_id);
        lin = (LinearLayout) findViewById(R.id.lin);
        check_auto_login = (CheckBox) findViewById(R.id.check2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}