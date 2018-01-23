package com.appjam.assist.assist.mypage;

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
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.MainActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.Player;
import com.appjam.assist.assist.model.response.PlayerResult;
import com.appjam.assist.assist.model.response.ResultMessage;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity2 extends BaseActivity {
    Uri data;
    final int REQ_CODE_SELECT_IMAGE=100;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    String choice_do = "";
    String choice_se = "";
    private TextView mypage2_sign_up_email_edit;
    private EditText mypage2_sign_up_name_edit, mypage2_sign_up_age_edit,
            mypage2_sign_up_height_edit, mypage2_sign_up_weight_edit, mypage2_sign_up_foot_edit,
            mypage2_sign_up_number_edit;
    private Button mypage2_finish_btn;
    private ImageButton mypage2_back_btn, mypage2_upload_img;
    private ImageView mypage2_image1, mypage2_image2;
    // private Spinner spinnermyPosition1, spinnermyPosition2;
    private NetworkService networkService;
    private int player_id;
    private Player player;
    private TeamProfile team;

    private String imgUrl = "";
    Uri initial_imgUrl;
    private HashMap<String, RequestBody> map;
    private MultipartBody.Part profile_pic;
    private LinearLayout lin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page2);
        BaseActivity.setGlobalFont(this, getWindow().getDecorView());
        changeBarColor();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        player_id = preferences.getInt("user_id", 0);


        init();
        initNetwork();
        initNetwork2();

        lin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });


        final Spinner spinnermyPosition1 = (Spinner)findViewById(R.id.spinnermyPosition1) ;
        final Spinner spinnermyPosition2 = (Spinner)findViewById(R.id.spinnermyPosition2);

        //나를 호출한 인텐트 얻어오기
        Intent intent = getIntent();

        Log.i("mytag", "인텐트 전 imgUrl값은? " + imgUrl);

        //인텐트에 담겨있는 값 꺼내오기
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String age = intent.getStringExtra("age");
        String height = intent.getStringExtra("height");
        String weight = intent.getStringExtra("weight");
        String foot = intent.getStringExtra("foot");
        String number = intent.getStringExtra("number");
        Log.d("숫자값","숫자값");
        String position = intent.getExtras().getString("position");
        String deposition = intent.getExtras().getString("deposition");

        final int count = intent.getExtras().getInt("count");
        final int decount = intent.getExtras().getInt("decount");

        //에디트 텍스트에 문자열 넣기기-->스피너에도 담겨있는 값을 넣어야할듯??
        mypage2_sign_up_name_edit.setText(name);
        mypage2_sign_up_email_edit.setText(email);
        mypage2_sign_up_age_edit.setText(age);
        mypage2_sign_up_height_edit.setText(height);
        mypage2_sign_up_weight_edit.setText(weight);
        mypage2_sign_up_foot_edit.setText(foot);
        mypage2_sign_up_number_edit.setText(number);


        adspin1 = ArrayAdapter.createFromResource(this, R.array.spinnermyPosition1_do, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(R.layout.spinner_item);

        // adspin1 =ArrayAdapter.createFromResource(this, R.array.spinnermyPosition1_do,android.R.layout.simple_spinner_dropdown_item);
        // adspin1.setDropDownViewResource(R.layout.spinner_item);

        spinnermyPosition1.setAdapter(adspin1);


        if(count == 0) {
            Log.d("수초기","초기");
            spinnermyPosition1.setSelection(0);
        }
        else if(count==1) {
            Log.d("골초기","초기");
            spinnermyPosition1.setSelection(1);
        }
        else if(count==2) {
            Log.d("공초기","초기");
            spinnermyPosition1.setSelection(2);

        }
        else if(count==3)
        {
            Log.d("미초기","초기");
            spinnermyPosition1.setSelection(3);
        }


        spinnermyPosition1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (adspin1.getItem(i).equals("수비수")) {

                    choice_do = "DEF";
                    adspin2 = ArrayAdapter.createFromResource(MyPageActivity2.this, R.array.spinnermyDefense,
                            android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);

                    spinnermyPosition2.setAdapter(adspin2);

                    String item = (String) parent.getItemAtPosition(i);
                    ((TextView) parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                    if(decount == 0) {
                        spinnermyPosition2.setSelection(0);
                    }
                    else if(decount ==1) {
                        spinnermyPosition2.setSelection(1);
                    }
                    else if(decount==2) {
                        spinnermyPosition2.setSelection(2);

                    }

                    spinnermyPosition2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                            String item = (String) adapterView.getItemAtPosition(i);
                            ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                } else if (adspin1.getItem(i).equals("골키퍼")) {//똑같은 소스에 반복입니다. 인천부분입니다.
                    choice_do = "GK";
                    adspin2 = ArrayAdapter.createFromResource(MyPageActivity2.this, R.array.spinnermyGoalkeeper, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinnermyPosition2.setAdapter(adspin2);
                    String item = (String) parent.getItemAtPosition(i);
                    ((TextView) parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));


                    spinnermyPosition2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                            String item = (String) adapterView.getItemAtPosition(i);
                            ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else if (adspin1.getItem(i).equals("공격수")) {//똑같은 소스에 반복입니다. 인천부분입니다.
                    choice_do = "ATK";
                    adspin2 = ArrayAdapter.createFromResource(MyPageActivity2.this, R.array.spinnermyStriker, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinnermyPosition2.setAdapter(adspin2);

                    String item = (String) parent.getItemAtPosition(i);
                    ((TextView) parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                    if(decount == 0) {
                        spinnermyPosition2.setSelection(0);
                    }
                    else if(decount ==1) {
                        spinnermyPosition2.setSelection(1);
                    }
                    else if(decount==2) {
                        spinnermyPosition2.setSelection(2);

                    }


                    spinnermyPosition2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                            String item = (String) adapterView.getItemAtPosition(i);
                            ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else if (adspin1.getItem(i).equals("미드필더")) {//똑같은 소스에 반복입니다
                    choice_do = "MF";
                    adspin2 = ArrayAdapter.createFromResource(MyPageActivity2.this, R.array.spinnermyMid, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(R.layout.spinner_item);
                    spinnermyPosition2.setAdapter(adspin2);

                    String item = (String) parent.getItemAtPosition(i);
                    ((TextView) parent.getChildAt(0)).setTextSize(13);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                    if(decount == 0) {
                        spinnermyPosition2.setSelection(0);
                    }
                    else if(decount ==1) {
                        spinnermyPosition2.setSelection(1);
                    }
                    else if(decount==2) {
                        spinnermyPosition2.setSelection(2);

                    }
                    spinnermyPosition2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();

                            String item = (String) adapterView.getItemAtPosition(i);
                            ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        mypage2_back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        mypage2_upload_img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Intent.ACTION_PICK);
                intent3.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent3.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent3, REQ_CODE_SELECT_IMAGE);

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        // post로 보내는 버튼이다!!!!!!!!!!!!
        mypage2_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makingRequestBody();
            }
        });
    }

    private void init() {
        mypage2_sign_up_name_edit = (EditText) findViewById(R.id.mypage2_sign_up_name_edit);
        mypage2_sign_up_age_edit = (EditText) findViewById(R.id.mypage2_sign_up_age_edit);
        mypage2_sign_up_height_edit = (EditText) findViewById(R.id.mypage2_sign_up_height_edit);
        mypage2_sign_up_weight_edit = (EditText) findViewById(R.id.mypage2_sign_up_weight_edit);
        mypage2_sign_up_foot_edit = (EditText) findViewById(R.id.mypage2_sign_up_foot_edit);
        mypage2_sign_up_number_edit = (EditText) findViewById(R.id.mypage2_sign_up_number_edit);
        mypage2_sign_up_email_edit = (TextView) findViewById(R.id.mypage2_sign_up_email_edit);

        mypage2_back_btn = (ImageButton) findViewById(R.id.mypage2_back_btn);
        mypage2_finish_btn = (Button) findViewById(R.id.mypage2_finish_btn);
        mypage2_upload_img = (ImageButton) findViewById(R.id.mypage2_upload_img);

        mypage2_image1 = (ImageView) findViewById(R.id.mypage2_image1);
        mypage2_image2 = (ImageView) findViewById(R.id.mypage2_image2);
//        mypage2_image2.setBackground(new ShapeDrawable(new OvalShape()));
        lin = (LinearLayout)findViewById(R.id.activity_my_page2);
//        mypage2_image2.setClipToOutline(true);
    }

    ////
    private void makingRequestBody() {

        RequestBody email = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_email_edit.getText().toString());
        RequestBody username = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_name_edit.getText().toString());
        RequestBody age = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_age_edit.getText().toString());
        RequestBody height = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_height_edit.getText().toString());
        RequestBody weight = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_weight_edit.getText().toString());
        RequestBody foot = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_foot_edit.getText().toString());
        RequestBody position = RequestBody.create(MediaType.parse("text/pain"), choice_do);
        RequestBody position_detail = RequestBody.create(MediaType.parse("text/pain"), choice_se);
        RequestBody backnumber = RequestBody.create(MediaType.parse("text/pain"), mypage2_sign_up_number_edit.getText().toString());

        map = new HashMap<>();
        map.put("email", email);
        map.put("username", username);
        map.put("age", age);
        map.put("height", height);
        map.put("weight", weight);
        map.put("foot", foot);
        map.put("position", position);
        map.put("position_detail", position_detail);
        map.put("backnumber", backnumber);


        // 프로필 사진
        if (imgUrl == "") {
            networkMakeTeam();

        } else {
            Log.i("mytag", "갤러리에서 이미지 선택해서 여기옴~~~~");
            BitmapFactory.Options options = new BitmapFactory.Options();

            InputStream in = null; // here, you need to get your context.
            try {
                //in = getApplicationContext().getContentResolver().openInputStream(data);
                in = getContentResolver().openInputStream(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

            File photo = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다
            Log.i("mytag", "photo? " + photo);
            Log.i("mytag", "photoBody?  " + photoBody);

            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            Log.i("mytag", "photo.getName()? " + photo.getName());
            profile_pic = MultipartBody.Part.createFormData("profile_pic", photo.getName(), photoBody);
            Log.i("mytag", "profile_pic? " + profile_pic);

            networkMakeTeam();
        }

    }
    ////

    private void networkMakeTeam() {
        Call<ResultMessage> result = networkService.modifiedMypage(player_id, map, profile_pic);
        Log.i("mytag", "수정 완료 버튼 누름");

        result.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                if (response.isSuccessful()) {
                    Log.i("mytag", "마이페이지 성공");

                    Intent intent = new Intent(getApplicationContext(), MyPageActivity1.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                Log.i("mytag", "마이페이지 실패");

            }
        });
    }

    // 프로필 이미지, 팀 이미지 가져올라고 썼다~~~
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

    private void initNetwork2() {
        networkService = ApplicationController.getInstance().getNetworkService();
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
                .into(mypage2_image1);
    }

    private void setData() {
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/player/" + player.getProfile_pic_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(mypage2_image2);
    }
    ////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try{
                    Bitmap image_bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),image_bitmap);
                    // bitmapDrawable.setCornerRadius(Math.max(image_bitmap.getWidth(), image_bitmap.getHeight())/2.0f);
                    bitmapDrawable.setAntiAlias(true);
                    String name_Str = getImageNameToUri(data.getData());
                    Log.i("mytag", "언제 여기 갈까? 갤러리 이미지 선택 후 이미지 변환될 때");
                    Log.i("mytag", "data값은? " + data.getData());
//                    uri_team = data.getData();
                    this.data = data.getData();
                    Glide.with(mypage2_image2.getContext())
                            .load(data.getData())
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(mypage2_image2);

                    // mypage2_image2.setImageDrawable(bitmapDrawable);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public String getImageNameToUri(Uri data) {
        Log.i("mytag", "언제 여기 갈까? getImageNameToUri");
        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getActivity().managedQuery(data, proj, null, null, null);
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        imgUrl = imgPath;
        Log.i("mytag", "imgUrl은? " + imgUrl);
        Log.i("mytag", "imgName은? " + imgName);
        return imgName;
    }
}
