package com.appjam.assist.assist.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.request.SignUp;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by gominju on 2017. 6. 28..
 */

public class SearchTeamResultFragment extends Fragment {
    // circle progress bar
    private ProgressBar progBar;
    private TextView percent;
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;
    View v;
    private Button btn_done;
    private NetworkService networkService;
    private HashMap<String, RequestBody> map;
    private SignUp signUp;
    private int teamId;
    private TeamProfile teamProfile;
    private TextView tv_result_teamname, tv_result_region, tv_result_manager;
    private SearchResult searchResult;
    private ImageView iv_profile;
    private TextView tv_teamname, tv_rank, tv_total, tv_record, tv_avr_total, tv_avr_against;
    public float percent_num;
    private TextView tv_region, tv_manager, tv_found, tv_message;
    private String result_name, result_region, result_manager;
//    private MultipartBody.Part profile_pic;
//    private String imgUrl;
//    private Uri uri_data;


    //public SearchTeamResultFragment getInstance(SignUp signUp, int teamId, SearchResult searchResult, String imgUrl, Uri uri_data)
    public SearchTeamResultFragment getInstance(SignUp signUp, int teamId, SearchResult searchResult) {
        SearchTeamResultFragment fragement = new SearchTeamResultFragment();
        this.signUp = signUp;
        this.teamId = teamId;
        this.searchResult = searchResult;

//        this.imgUrl = imgUrl;
//        this.uri_data = uri_data;

        Bundle args = new Bundle();
        args.putInt("teamId", teamId);
        args.putSerializable("search_name", searchResult.name);
        args.putSerializable("search_region", searchResult.region);
        args.putSerializable("search_manager", searchResult.manager);
//        args.putSerializable("imgUrl", imgUrl);
//        args.putParcelable("uri_data", uri_data);
        fragement.setArguments(args);
        return fragement;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team_search_result, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());
        Bundle bundle = getArguments();
        teamId = bundle.getInt("teamId");
        result_name = bundle.getString("search_name");
        result_region = bundle.getString("search_region");
        result_manager = bundle.getString("search_manager");

//        imgUrl = bundle.getString("imgUrl");
//        uri_data = bundle.getParcelable("uri_data");

        initView();
        initNetwork();

        // 프로그래스
        progBar = (ProgressBar) v.findViewById(R.id.progressBar);
        percent = (TextView) v.findViewById(R.id.tv_percent);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.signUp.setTeam_id(teamProfile.getId());
                SignUp.signUp.setWhatView("search");

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.putInt("team_id", teamProfile.getId());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), SignupDetailActivity.class);
                intent.putExtra("team_id", teamProfile.getId());
                intent.putExtra("whatFrag", "search");
                Log.i("mytag", "team id : " + teamProfile.getId());
                Log.i("mytag", "SearchTeamREsultFragment, type = " + SignUp.signUp.getType());

                startActivity(intent);
            }
        });
        return v;
    }

    private void setData() {
        // 검색 결과
        tv_result_teamname.setText(result_name);
        tv_result_manager.setText(result_manager);
        tv_result_region.setText(result_region);

//        // 프로필 세팅
        Glide.with(this)
                .load("http://13.124.136.174:3388/static/images/profileImg/team/" + teamProfile.getProfile_pic_url())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_profile);
        tv_teamname.setText(teamProfile.getTeamname());
        tv_rank.setText(teamProfile.getRank() + "위");
        tv_total.setText(teamProfile.getTotal_game() + "전");
        tv_record.setText(teamProfile.getWin_game() + "승 " + teamProfile.getDraw_game() + "무 " + teamProfile.getLose_game() + "패");
        if (teamProfile.getTotal_game() == 0) {
            tv_avr_total.setText("평균 득점 : 0.00");
            tv_avr_against.setText("평균 실점 : 0.00");
            percent_num = 0;
            dosomething((int)percent_num);
        } else {
            // 평균 득점 : 득점 / 총 경기수 * 100
            float avg_total = (float) (teamProfile.getWin_game()) / teamProfile.getTotal_game() ;
            String str_avg_total = String.format("%.2f", avg_total);
            tv_avr_total.setText("평균 득점 " + str_avg_total);
            // 평균 실점
            float avg_against = (float) (teamProfile.getLose_game()) / teamProfile.getTotal_game();
            String str_avg_against = String.format("%.2f", avg_against);
            tv_avr_against.setText("평균 실점 " + str_avg_against);
            // 승률 : 승 경기수 / 승 게임수
            percent_num = (float) (teamProfile.getWin_game() * 100/ teamProfile.getTotal_game()) ;
            Log.i("mytag", "SearchTeamResultFragment, percent_num : " + percent_num);
            dosomething(((int) percent_num));
        }



        // 팀 소개
        tv_region.setText(teamProfile.getRegion());
        tv_manager.setText(teamProfile.getManager());
        tv_found.setText(teamProfile.getFound_dt().substring(0, 10));
        tv_message.setText(teamProfile.getMessage());

    }


//    private void makingProfile(String imgUrl, Uri uri_data) {
//        if (imgUrl == "") {
//            profile_pic = null;
//        } else {
//            BitmapFactory.Options options = new BitmapFactory.Options();
////                       options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다
//
//            InputStream in = null; // here, you need to get your context.
//            try {
//                in = getContext().getContentResolver().openInputStream(uri_data);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//            // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
//            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
//
//            File photo = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다
//
//            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
//            profile_pic = MultipartBody.Part.createFormData("profile_pic", photo.getName(), photoBody);
//        }
//    }
//
//    private void makingRequestBody() {
//        RequestBody username = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getName());
//        RequestBody email = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getEmail());
//        RequestBody password = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getPwd());
//        RequestBody age = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(SignUp.signUp.getAge()));
//        RequestBody height = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(SignUp.signUp.getHeight()));
//        RequestBody weight = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(SignUp.signUp.getWeight()));
//        RequestBody foot = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getMain_foot());
//        RequestBody position = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getPosition());
//        RequestBody position_detail = RequestBody.create(MediaType.parse("text/pain"), SignUp.signUp.getDetail_position());
//        RequestBody backnumber = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(SignUp.signUp.getBack_num()));
//        RequestBody team_id = RequestBody.create(MediaType.parse("text/pain"), String.valueOf(teamId));
//
//
//        map = new HashMap<>();
//        map.put("username", username);
//        map.put("email", email);
//        map.put("password", password);
//        map.put("age", age);
//        map.put("height", height);
//        map.put("weight", weight);
//        map.put("foot", foot);
//        map.put("position", position);
//        map.put("position_detail", position_detail);
//        map.put("backnumber", backnumber);
//        map.put("team_id", team_id);
//
//    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<TeamProfileResult> result = networkService.getTeamProfile(teamId);
        result.enqueue(new Callback<TeamProfileResult>() {
            @Override
            public void onResponse(Call<TeamProfileResult> call, Response<TeamProfileResult> response) {
                if (response.isSuccessful()) {
                    teamProfile = response.body().response;
                    setData();
                }
            }

            @Override
            public void onFailure(Call<TeamProfileResult> call, Throwable t) {

            }
        });
    }


    protected void dosomething(final int percent_num) {
        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < percent_num) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress(mProgressStatus);
                            percent.setText("" + mProgressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initView() {
        btn_done = (Button) v.findViewById(R.id.btn_done);
        tv_result_teamname = (TextView) v.findViewById(R.id.team_find_result_team_name);
        tv_result_manager = (TextView) v.findViewById(R.id.team_find_result_team_director);
        tv_result_region = (TextView) v.findViewById(R.id.team_find_result_team_local);
        tv_teamname = (TextView) v.findViewById(R.id.tv_name);
        tv_rank = (TextView) v.findViewById(R.id.tv_rank);
        tv_total = (TextView) v.findViewById(R.id.tv_total);
        tv_record = (TextView) v.findViewById(R.id.tv_record);
        tv_avr_against = (TextView) v.findViewById(R.id.tv_avr_against);
        tv_avr_total = (TextView) v.findViewById(R.id.tv_avr_total);
        tv_region = (TextView) v.findViewById(R.id.tv_region);
        tv_manager = (TextView) v.findViewById(R.id.tv_manager);
        tv_found = (TextView) v.findViewById(R.id.tv_found);
        tv_message = (TextView) v.findViewById(R.id.tv_msg);
        iv_profile = (ImageView) v.findViewById(R.id.iv_profile);
    }
}
