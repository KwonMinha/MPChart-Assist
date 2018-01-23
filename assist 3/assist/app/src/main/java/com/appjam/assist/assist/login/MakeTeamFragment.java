package com.appjam.assist.assist.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.appjam.assist.assist.network.ApplicationController;
import com.appjam.assist.assist.network.NetworkService;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.appjam.assist.assist.R.id.spinner_region;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by gominju on 2017. 6. 28..
 */

public class MakeTeamFragment extends Fragment {
    private Button btn_done;
    View v;
    private SignUp signUp;
    private String team_imgUrl = "";
    private Uri uri_team;
    private EditText et_teamname, et_manager, et_found, et_msg;
    private LinearLayout linearLayout;
    private Spinner spinner;
    ArrayAdapter<CharSequence> spin_array;

    private String teamname, manager, found, region, msg;
    private ImageView iv_profile, iv_gallery;
    private static final int PICK_FROM_GALLERY = 0;
    private NetworkService networkService;
    private int type;
    private int teamId;

    public MakeTeamFragment getInstance(SignUp signUp) {
        MakeTeamFragment fragement = new MakeTeamFragment();
        this.signUp = signUp;
        return fragement;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team_make, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());
        initView();

//        linearLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                hideKeyboard();
//                return false;
//            }
//        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = spin_array.getItem(position).toString();
                String item = (String) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextSize(13);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        initNetwork();

        spin_array = ArrayAdapter.createFromResource(this.getContext(), R.array.spinnerlocal_do, android.R.layout.simple_spinner_dropdown_item);
        spin_array.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spin_array);

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

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    Intent intent = new Intent(getApplicationContext(), SignupDetailActivity.class);
                    intent.putExtra("team_name", teamname);
                    intent.putExtra("manager", manager);
                    intent.putExtra("found", found);
                    intent.putExtra("msg", msg);
                    intent.putExtra("region", region);
                    intent.putExtra("team_imgUrl", team_imgUrl);
                    intent.putExtra("uri_team", uri_team);
                    intent.putExtra("whatFrag", "make");
                    SignUp.signUp.setWhatView("make");

                    startActivity(intent);
                }
            }
        });
        return v;
    }

    private void initNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

//    private void hideKeyboard() {
//        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String name_Str = getImageNameToUri(data.getData());
                    uri_team = data.getData();
                    Glide.with(iv_profile.getContext())
                            .load(data.getData())
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(iv_profile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                team_imgUrl = "";
            }
        }
    }

    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        team_imgUrl = imgPath;
        return imgName;
    }

    private boolean checkValid() {

        teamname = et_teamname.getText().toString();
        manager = et_manager.getText().toString();
        found = et_found.getText().toString();
        msg = et_msg.getText().toString();

        if (teamname.equals("")) {
            Toast.makeText(getApplicationContext(), "팀명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (manager.equals("")) {
            Toast.makeText(getApplicationContext(), "매니저명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (found.equals("")) {
            Toast.makeText(getApplicationContext(), "창단일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (region.equals("")) {
            Toast.makeText(getApplicationContext(), "지역을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void initView() {
        btn_done = (Button) v.findViewById(R.id.btn_done);
        et_found = (EditText) v.findViewById(R.id.et_found);
        et_manager = (EditText) v.findViewById(R.id.et_manager);
        et_teamname = (EditText) v.findViewById(R.id.et_teamname);
        et_msg = (EditText) v.findViewById(R.id.et_msg);
        iv_gallery = (ImageView) v.findViewById(R.id.iv_gallery);
        iv_profile = (ImageView) v.findViewById(R.id.iv_profile);
        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout_team_make);
        spinner = (Spinner) v.findViewById(spinner_region);
    }
}