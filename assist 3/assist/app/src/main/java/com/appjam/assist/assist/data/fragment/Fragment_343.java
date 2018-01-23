package com.appjam.assist.assist.data.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.data.OnClickButtonId;
import com.appjam.assist.assist.model.response.ATK_member;
import com.appjam.assist.assist.model.response.DF_member;
import com.appjam.assist.assist.model.response.GK_member;
import com.appjam.assist.assist.model.response.MF_member;

import java.util.ArrayList;

/**
 * Created by minha on 2017-07-02.
 */

public class Fragment_343 extends Fragment {
    Button btn_keeper;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;

    View v;

    private ArrayList<ATK_member> ATK_list;
    private ArrayList<MF_member> MF_list;
    private ArrayList<DF_member> DF_list;
    private ArrayList<GK_member> GK_list;

    private OnClickButtonId mOnClickButtonId;
    private int pre_id = 0;     // 이전에 클릭한 버튼 아이디 저장
    private boolean b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof OnClickButtonId) {
            mOnClickButtonId = (OnClickButtonId) getActivity();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_formation_343, container, false);
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        ATK_list = (ArrayList<ATK_member>) getArguments().get("ATK");
        MF_list = (ArrayList<MF_member>) getArguments().get("MF");
        DF_list = (ArrayList<DF_member>) getArguments().get("DF");
        GK_list = (ArrayList<GK_member>) getArguments().get("GK");

        init();
        setBackNumber();

        btn_keeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전에 클릭한 버튼 색 없애기
                if (pre_id == btn_keeper.getId() && b0) {
                    b0 = false;
                    btn_keeper.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn_keeper.setBackgroundResource(R.drawable.select_shirt);
                    b0 = true;
                }
                pre_id = btn_keeper.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_keeper.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn_keeper.getText().toString())), b0);
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn1.getId() && b1) {
                    b1 = false;
                    btn1.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn1.setBackgroundResource(R.drawable.select_shirt);
                    b1 = true;
                }

                pre_id = btn1.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn1.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn1.getText().toString())), b1);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn2.getId() && b2) {
                    b2 = false;
                    btn2.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn2.setBackgroundResource(R.drawable.select_shirt);
                    b2 = true;
                }
                pre_id = btn2.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn2.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn2.getText().toString())), b2);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn3.getId() && b3) {
                    b3 = false;
                    btn3.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn3.setBackgroundResource(R.drawable.select_shirt);
                    b3 = true;
                }
                pre_id = btn3.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn3.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn3.getText().toString())), b3);
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn4.getId() && b4) {
                    b4 = false;
                    btn4.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn4.setBackgroundResource(R.drawable.select_shirt);
                    b4 = true;
                }

                pre_id = btn4.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn4.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn4.getText().toString())), b4);
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn5.getId() && b5) {
                    b5 = false;
                    btn5.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn5.setBackgroundResource(R.drawable.select_shirt);
                    b5 = true;
                }

                pre_id = btn5.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn5.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn5.getText().toString())), b5);
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn6.getId() && b6) {
                    b6 = false;
                    btn6.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn6.setBackgroundResource(R.drawable.select_shirt);
                    b6 = true;
                }

                pre_id = btn6.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn6.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn6.getText().toString())), b6);
                }
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn7.getId() && b7) {
                    b7 = false;
                    btn7.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn7.setBackgroundResource(R.drawable.select_shirt);
                    b7 = true;
                }

                pre_id = btn7.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn7.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn7.getText().toString())), b7);
                }
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn8.getId() && b8) {
                    b8 = false;
                    btn8.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn8.setBackgroundResource(R.drawable.select_shirt);
                    b8 = true;
                }

                pre_id = btn8.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn8.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn8.getText().toString())), b8);
                }
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn9.getId() && b9) {
                    b9 = false;
                    btn9.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn9.setBackgroundResource(R.drawable.select_shirt);
                    b9 = true;
                }

                pre_id = btn9.getId();

                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn9.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn9.getText().toString())), b9);
                }
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pre_id == btn10.getId() && b10) {
                    b10 = false;
                    btn10.setBackgroundResource(R.drawable.t_shirts_reserve);
                } else {
                    changeButtonColor();
                    btn10.setBackgroundResource(R.drawable.select_shirt);
                    b10 = true;
                }

                pre_id = btn10.getId();
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn10.getText().toString())));
                    mOnClickButtonId.getIdAndState(matchPlayerId(Integer.parseInt(btn10.getText().toString())), b10);
                }
            }
        });

        return v;
    }

    private void changeButtonColor() {
        switch (pre_id) {
            case 0:
                break;
            case R.id.goal_keeper:
                btn_keeper.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player1:
                btn1.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player2:
                btn2.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player3:
                btn3.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player4:
                btn4.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player5:
                btn5.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player6:
                btn6.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player7:
                btn7.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player8:
                btn8.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player9:
                btn9.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
            case R.id.player10:
                btn10.setBackgroundResource(R.drawable.t_shirts_reserve);
                break;
        }
    }

    private int matchPlayerId(int backnumber) {
        int player_id = 0;
        for (int i = 0; i < ATK_list.size(); i++) {
            if (backnumber == ATK_list.get(i).getBacknumber()) {
                player_id = ATK_list.get(i).getId();
            }
        }
        for (int i = 0; i < MF_list.size(); i++) {
            if (backnumber == MF_list.get(i).getBacknumber()) {
                player_id = MF_list.get(i).getId();
            }
        }
        for (int i = 0; i < DF_list.size(); i++) {
            if (backnumber == DF_list.get(i).getBacknumber()) {
                player_id = DF_list.get(i).getId();
            }
        }
        for (int i = 0; i < GK_list.size(); i++) {
            if (backnumber == GK_list.get(i).getBacknumber()) {
                player_id = GK_list.get(i).getId();
            }
        }
        return player_id;
    }


    private void setBackNumber() {
        btn_keeper.setText(String.valueOf(GK_list.get(0).getBacknumber()));
        btn1.setText(String.valueOf(DF_list.get(0).getBacknumber()));
        btn2.setText(String.valueOf(DF_list.get(1).getBacknumber()));
        btn3.setText(String.valueOf(DF_list.get(2).getBacknumber()));

        btn4.setText(String.valueOf(MF_list.get(0).getBacknumber()));
        btn5.setText(String.valueOf(MF_list.get(1).getBacknumber()));
        btn6.setText(String.valueOf(MF_list.get(2).getBacknumber()));
        btn7.setText(String.valueOf(MF_list.get(3).getBacknumber()));

        btn8.setText(String.valueOf(ATK_list.get(0).getBacknumber()));
        btn9.setText(String.valueOf(ATK_list.get(1).getBacknumber()));
        btn10.setText(String.valueOf(ATK_list.get(2).getBacknumber()));
    }

    private void init() {
        btn_keeper = (Button) v.findViewById(R.id.goal_keeper);
        btn1 = (Button) v.findViewById(R.id.player1);
        btn2 = (Button) v.findViewById(R.id.player2);
        btn3 = (Button) v.findViewById(R.id.player3);
        btn4 = (Button) v.findViewById(R.id.player4);
        btn5 = (Button) v.findViewById(R.id.player5);
        btn6 = (Button) v.findViewById(R.id.player6);
        btn7 = (Button) v.findViewById(R.id.player7);
        btn8 = (Button) v.findViewById(R.id.player8);
        btn9 = (Button) v.findViewById(R.id.player9);
        btn10 = (Button) v.findViewById(R.id.player10);
    }
}
