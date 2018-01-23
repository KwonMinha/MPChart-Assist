package com.appjam.assist.assist.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.request.SignUp;
import com.appjam.assist.assist.model.response.Backnumber;
import com.appjam.assist.assist.model.response.ResultCheck;
import com.appjam.assist.assist.model.response.ResultMessage;
import com.appjam.assist.assist.model.response.TeamId;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupDetailActivity extends AppCompatActivity {
    private Button btn_done, btn_back;
    private Button btn_check_backnumber;
    private LinearLayout linearLayout;
    String email, name, pwd, age, height, weight;
    ArrayAdapter<CharSequence> adspin1, adspin2, adspin_foot;
    private Spinner spinner1, spinner2;
    String choice1, choice2, choice_foot;
    private ImageView iv_profile, iv_gallery;
    private static final int PICK_FROM_GALLERY = 0;
    private MultipartBody.Part body;
    private EditText edit_age, edit_height, edit_weight, edit_backnum; //edit_position, edit_position_detail; edit_foot,
    private Spinner spinner_foot;
    private boolean isCheck = false; // 등번호 체크에 사용
    private NetworkService networkService;
    private HashMap<String, RequestBody> map, map2;
    private int team_id;
    private SignUp signUp;
    private MultipartBody.Part profile_pic, profile_pic2;
    private String imgUrl = "";
    private Uri uri_data;
    private String whatView = "";
    private int type;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_detail);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        initView();
        initNetwork();

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));


//        Intent intent = getIntent();
//        email = intent.getStringExtra("email");
//        name = intent.getStringExtra("name");
//        pwd = intent.getStringExtra("pwd");
//        type = intent.getIntExtra("type", 0);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.spinnerPosition1_do, android.R.layout.simple_spinner_dropdown_item);
        //adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(adspin1);

        adspin_foot = ArrayAdapter.createFromResource(this, R.array.spinnerFoot, android.R.layout.simple_spinner_dropdown_item);
        //adspin_foot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adspin_foot.setDropDownViewResource(R.layout.spinner_item);
        spinner_foot.setAdapter(adspin_foot);

        spinner_foot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_foot = adspin_foot.getItem(position).toString();
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setText(parent.getSelectedItem().toString());
                tv.setTextSize(13);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adspin1.getItem(position).equals("수비수")) {
                    String item = (String) parent.getItemAtPosition(position);
                    ((TextView)parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                    choice1 = "DF";
                    adspin2 = ArrayAdapter.createFromResource(SignupDetailActivity.this, R.array.spinnerDefense, android.R.layout.simple_spinner_dropdown_item);
                    //adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinner2.setAdapter(adspin2);
                }
                if (adspin1.getItem(position).equals("골키퍼")) {
                    choice1 = "GK";
                    adspin2 = ArrayAdapter.createFromResource(SignupDetailActivity.this, R.array.spinnerGoalkeeper, android.R.layout.simple_spinner_dropdown_item);
                    //adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinner2.setAdapter(adspin2);
                    String item = (String) parent.getItemAtPosition(position);
                    ((TextView)parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                }
                if (adspin1.getItem(position).equals("공격수")) {
                    choice1 = "ATK";
                    adspin2 = ArrayAdapter.createFromResource(SignupDetailActivity.this, R.array.spinnerMid, android.R.layout.simple_spinner_dropdown_item);
                    //adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinner2.setAdapter(adspin2);
                    String item = (String) parent.getItemAtPosition(position);
                    ((TextView)parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                }
                if (adspin1.getItem(position).equals("미드필더")) {
                    choice1 = "MF";
                    adspin2 = ArrayAdapter.createFromResource(SignupDetailActivity.this, R.array.spinnerMid, android.R.layout.simple_spinner_dropdown_item);
                    //adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinner2.setAdapter(adspin2);
                    String item = (String) parent.getItemAtPosition(position);
                    ((TextView)parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice2 = adspin2.getItem(position).toString();
                String item = (String) parent.getItemAtPosition(position);
                ((TextView)parent.getChildAt(0)).setTextSize(13);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setText(parent.getSelectedItem().toString());
                tv.setTextSize(13);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범 호출
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_GALLERY);
            }
        });

        btn_check_backnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backnum_string = edit_backnum.getText().toString();
                whatView = SignUp.signUp.getWhatView();
                if (backnum_string.equals("")) {
                    Toast.makeText(getApplicationContext(), "등번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (whatView.equals("make")) {
                    isCheck = true;
                    Toast.makeText(getApplicationContext(), "어떠한 등번호라도 사용가능 합니다", Toast.LENGTH_SHORT).show();
                } else {
                    int backnum_int = Integer.parseInt(backnum_string);

                    Backnumber backnumber = new Backnumber();
                    backnumber.setBacknumber(backnum_int);

                    Call<ResultCheck> backnumResult = networkService.checkBacknumber(SignUp.signUp.getTeam_id(), backnumber);
                    backnumResult.enqueue(new Callback<ResultCheck>() {
                        @Override
                        public void onResponse(Call<ResultCheck> call, Response<ResultCheck> response) {
                            if (response.isSuccessful()) {
                                if (response.body().response.equals("noduplicate")) {
                                    isCheck = true;
                                    Toast.makeText(getApplicationContext(), "사용 가능한 등번호입니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    isCheck = false;
                                    Toast.makeText(getApplicationContext(), "이미 사용중인 등번호입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultCheck> call, Throwable t) {
                        }
                    });
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SearchTeamResultFragment에서 왔는지 MakeTeamFragment에서 왔는지 확인 후 post를 달리 할 것
                if (SignUp.signUp.getWhatView().equals("search")) { // search에서 왔을 때
                    if (checkValid()) {
                        makingRequestBody();
                        makingProfile(imgUrl, uri_data);

                        Call<ResultMessage> result = networkService.joinMember(map, profile_pic);
                        result.enqueue(new Callback<ResultMessage>() {
                            @Override
                            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<ResultMessage> call, Throwable t) {

                            }
                        });
                    }
                } else { // make에서 왔을 때
                    if (checkValid()) {
                        makingTeamRequestBody();
                    }
                }
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void makingTeamRequestBody() {
        Intent intent = getIntent();
        String make_teamname = intent.getStringExtra("team_name");
        String make_manager = intent.getStringExtra("manager");
        String make_found = intent.getStringExtra("found");
        String make_region = intent.getStringExtra("region");
        String make_msg = intent.getStringExtra("msg");
        Uri uri_team = intent.getParcelableExtra("uri_team");
        String team_imgUrl = intent.getStringExtra("team_imgUrl");

        RequestBody teamname = RequestBody.create(MediaType.parse("text/pain"), make_teamname.toString());
        RequestBody region = RequestBody.create(MediaType.parse("text/pain"), make_region.toString());
        RequestBody manager = RequestBody.create(MediaType.parse("text/pain"), make_manager.toString());
        RequestBody found_at = RequestBody.create(MediaType.parse("text/pain"), make_found.toString());
        RequestBody message = RequestBody.create(MediaType.parse("text/pain"), make_msg.toString());


        map = new HashMap<>();
        map.put("teamname", teamname);
        map.put("region", region);
        map.put("manager", manager);
        map.put("found_dt", found_at);
        map.put("message", message);


        // 팀 프로필 사진
        if (team_imgUrl.equals("")) {
            profile_pic = null;

            networkMakeTeam();
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();

            InputStream in = null; // here, you need to get your context.
            try {
                in = getContentResolver().openInputStream(uri_team);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
            File photo = new File(team_imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다


            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            profile_pic = MultipartBody.Part.createFormData("profile_pic", photo.getName(), photoBody);

            networkMakeTeam();
        }
    }

    private void networkMakeTeam() {
        Call<TeamId> result = networkService.makeTeam(map, profile_pic);

        result.enqueue(new Callback<TeamId>() {
            @Override
            public void onResponse(Call<TeamId> call, Response<TeamId> response) {
                if (response.isSuccessful()) {
                    team_id = response.body().team_id;
                    SignUp.signUp.setTeam_id(team_id);
                    makingSignUpRequestBody();
                }
            }

            @Override
            public void onFailure(Call<TeamId> call, Throwable t) {

            }
        });
    }

    private void networkSignUp() {
        Call<ResultMessage> result = networkService.joinMember(map2, profile_pic2);
        result.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    editor.putInt("team_id", team_id);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
            }
        });
    }

    private void makingSignUpRequestBody() {
        RequestBody password, requestBodytoken;
        if (type == 0) {
            password = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getPwd());
            requestBodytoken = RequestBody.create(MediaType.parse("text/pain"), "");
        } else {
            password = RequestBody.create(MediaType.parse("text/pain"), "");
            requestBodytoken = RequestBody.create(MediaType.parse("text/pain"), token);
        }
        RequestBody username = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getName());
        RequestBody email = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getEmail());
        RequestBody age = RequestBody.create(MediaType.parse("text/pain"), edit_age.getText().toString());
        RequestBody height = RequestBody.create(MediaType.parse("text/pain"), edit_height.getText().toString());
        RequestBody weight = RequestBody.create(MediaType.parse("text/pain"), edit_weight.getText().toString());
        RequestBody foot = RequestBody.create(MediaType.parse("text/pain"), choice_foot);
        RequestBody position = RequestBody.create(MediaType.parse("text/pain"), choice1);
        RequestBody position_detail = RequestBody.create(MediaType.parse("text/pain"), choice2);
        RequestBody backnumber = RequestBody.create(MediaType.parse("text/pain"), edit_backnum.getText().toString());
        RequestBody team_id = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(SignUp.signUp.getTeam_id()));


        map2 = new HashMap<>();
        map2.put("username", username);
        map2.put("email", email);
        map2.put("password", password);
        map2.put("age", age);
        map2.put("height", height);
        map2.put("weight", weight);
        map2.put("foot", foot);
        map2.put("position", position);
        map2.put("position_detail", position_detail);
        map2.put("backnumber", backnumber);
        map2.put("team_id", team_id);
        map2.put("token", requestBodytoken);

        // 프로필 사진
        if (imgUrl.equals("")) {
            profile_pic2 = null;
            networkSignUp();
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();

            InputStream in = null; // here, you need to get your context.
            try {
                in = getContentResolver().openInputStream(uri_data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

            File photo = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다
            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            profile_pic2 = MultipartBody.Part.createFormData("profile_pic", photo.getName(), photoBody);

            networkSignUp();
        }
    }


    private boolean checkValid() {
        age = edit_age.getText().toString();
        height = edit_height.getText().toString();
        weight = edit_height.getText().toString();

        if (age.equals("")) {
            Toast.makeText(getApplicationContext(), "나이를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (height.equals("")) {
            Toast.makeText(getApplicationContext(), "키를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (weight.equals("")) {
            Toast.makeText(getApplicationContext(), "체중을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isCheck == false) {
            if (SignUp.signUp.getWhatView().equals("search")) {
                Toast.makeText(getApplicationContext(), "등번호 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                isCheck = true; // make에서 온 것이라면 등번호 중복체크를 하지 않아도 된다.
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String name_Str = getImageNameToUri(data.getData());
                    this.uri_data = data.getData();
                    Glide.with(iv_profile.getContext())
                            .load(data.getData())
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(iv_profile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imgUrl = "";
            }
        }
    }

    private void makingProfile(String imgUrl, Uri uri_data) {
        if (imgUrl.equals("")) {
            profile_pic = null;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
//                       options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

            InputStream in = null; // here, you need to get your context.
            try {
                in = getContentResolver().openInputStream(uri_data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

            File photo = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다.

            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            profile_pic = MultipartBody.Part.createFormData("profile_pic", photo.getName(), photoBody);
        }
    }

    private void makingRequestBody() {
        RequestBody password, requestBodytoken;
        if (SignUp.signUp.getType() == 0) {
            password = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getPwd());
            requestBodytoken = RequestBody.create(MediaType.parse("text/pain"), "");

        } else {
            password = RequestBody.create(MediaType.parse("text/pain"), "");
            requestBodytoken = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getToken());
        }

        RequestBody username = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getName());
        RequestBody email = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getEmail());
        RequestBody age = RequestBody.create(MediaType.parse("text/pain"), edit_age.getText().toString());
        RequestBody height = RequestBody.create(MediaType.parse("text/pain"), edit_height.getText().toString());
        RequestBody weight = RequestBody.create(MediaType.parse("text/pain"), edit_weight.getText().toString());
        RequestBody foot = RequestBody.create(MediaType.parse("text/pain"), choice_foot);
        RequestBody position = RequestBody.create(MediaType.parse("text/pain"), choice1);
        RequestBody position_detail = RequestBody.create(MediaType.parse("text/pain"), choice2);
        RequestBody backnumber = RequestBody.create(MediaType.parse("text/pain"), edit_backnum.getText().toString());
        RequestBody team_id = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(SignUp.signUp.getTeam_id()));

        map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        map.put("password", password);
        map.put("age", age);
        map.put("height", height);
        map.put("weight", weight);
        map.put("foot", foot);
        map.put("position", position);
        map.put("position_detail", position_detail);
        map.put("backnumber", backnumber);
        map.put("team_id", team_id);

        if (SignUp.signUp.getType() == 1) {
            map.put("token", requestBodytoken);
        }

    }


    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        imgUrl = imgPath;
        return imgName;
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_done = (Button) findViewById(R.id.btn_done);
        btn_check_backnumber = (Button) findViewById(R.id.check_backnum);
        spinner1 = (Spinner) findViewById(R.id.spinnerPostion1);
        spinner2 = (Spinner) findViewById(R.id.spinnerPosition2);
        spinner_foot = (Spinner) findViewById(R.id.spinner_foot);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        iv_gallery = (ImageView) findViewById(R.id.iv_gallery);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_sign_detail);

        edit_age = (EditText) findViewById(R.id.sign_up_detail_age);
        edit_height = (EditText) findViewById(R.id.sign_up_detail_tall);
        edit_weight = (EditText) findViewById(R.id.sign_up_detail_weight);
        //edit_foot = (EditText) findViewById(R.id.sign_up_detail_foot);
        edit_backnum = (EditText) findViewById(R.id.sign_up_detail_number);
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }
}