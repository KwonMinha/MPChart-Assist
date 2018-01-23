package com.appjam.assist.assist.data.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;
import com.appjam.assist.assist.model.response.ATK_member;
import com.appjam.assist.assist.model.response.DF_member;
import com.appjam.assist.assist.model.response.GK_member;
import com.appjam.assist.assist.model.response.MF_member;

import junit.framework.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by minha on 2017-06-30.
 */

public class TestFragment extends Fragment {
    View v;
    private Button btn_keeper;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_10;

    int now_fragment;
    private ArrayList<ATK_member> ATK_list;
    private ArrayList<MF_member> MF_list;
    private ArrayList<DF_member> DF_list;
    private ArrayList<GK_member> GK_list;

    public interface OnClickButtonId {
        void getButtonId(String num);
        void getPlayerId(int player_id);
    }

    private OnClickButtonId mOnClickButtonId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof OnClickButtonId) {
            mOnClickButtonId = (OnClickButtonId) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        now_fragment = getArguments().getInt("now_fragment");
        ATK_list = (ArrayList<ATK_member>) getArguments().get("ATK");
        MF_list = (ArrayList<MF_member>) getArguments().get("MF");
        DF_list = (ArrayList<DF_member>) getArguments().get("DF");
        GK_list = (ArrayList<GK_member>) getArguments().get("GK");

        if (now_fragment == 0 || now_fragment == 1) {
            v = inflater.inflate(R.layout.fragment_formation_442, container, false);
        } else if (now_fragment == 2) {
            v = inflater.inflate(R.layout.fragment_formation_343, container, false);
        } else if (now_fragment == 3) {
            v = inflater.inflate(R.layout.fragment_formation_433, container, false);
        } else if (now_fragment == 4) {
            v = inflater.inflate(R.layout.fragment_formation_352, container, false);
        } else {
            v = inflater.inflate(R.layout.fragment_formation_451, container, false);
        }
        BaseActivity.setGlobalFont(getContext(), getActivity().getWindow().getDecorView());

        init();
        setBackNumber(now_fragment);


        btn_keeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_keeper.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_keeper.getText().toString())));
                }
            }
        });

        // 버튼을 클릭할시 백넘버에 해당하는 이름을 보내주기

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_1.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_1.getText().toString())));
                }
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_2.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_2.getText().toString())));
                }
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_3.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_3.getText().toString())));
                }
            }
        });

        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_4.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_4.getText().toString())));
                }
            }
        });

        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_5.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_5.getText().toString())));
                }
            }
        });

        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_6.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_6.getText().toString())));
                }
            }
        });

        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_7.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_7.getText().toString())));
                }
            }
        });

        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_8.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_8.getText().toString())));
                }
            }
        });

        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_9.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_9.getText().toString())));
                }
            }
        });

        btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickButtonId != null) {
                    mOnClickButtonId.getButtonId(matchUserName(Integer.parseInt(btn_10.getText().toString())));
                    mOnClickButtonId.getPlayerId(matchPlayerId(Integer.parseInt(btn_10.getText().toString())));
                }
            }
        });

        return v;
    }

    private void setBackNumber(int now_fragment) {
        switch (now_fragment) {
            case 0:
                setBackNumber442();
                break;
            case 1:
                setBackNumber442();
                break;
            case 2:
                setBackNumber343();
                break;
            case 3:
                setBackNumber433();
                break;
            case 4:
                setBackNumber352(); // df-mf-att
                break;
            case 5:
                setBackNumber451();
                break;
            default:
                break;
        }

    }


    private String matchUserName(int backnumber) {
        String playerName = "";

        for (int i = 0; i < ATK_list.size(); i++) {
            if (backnumber == ATK_list.get(i).getBacknumber()) {
                playerName = ATK_list.get(i).getUsername();
            }
        }
        for (int i = 0; i < MF_list.size(); i++) {
            if (backnumber == MF_list.get(i).getBacknumber()) {
                playerName = MF_list.get(i).getUsername();
            }
        }
        for (int i = 0; i < DF_list.size(); i++) {
            if (backnumber == DF_list.get(i).getBacknumber()) {
                playerName = DF_list.get(i).getUsername();
            }
        }
        for (int i = 0; i < GK_list.size(); i++) {
            if (backnumber == GK_list.get(i).getBacknumber()) {
                playerName = GK_list.get(i).getUsername();
            }
        }
        return playerName;
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

    private void setBackNumber442() {
        btn_keeper.setText(String.valueOf(GK_list.get(0).getBacknumber()));
        btn_1.setText(String.valueOf(DF_list.get(0).getBacknumber()));
        btn_2.setText(String.valueOf(DF_list.get(1).getBacknumber()));
        btn_3.setText(String.valueOf(DF_list.get(2).getBacknumber()));
        btn_4.setText(String.valueOf(DF_list.get(3).getBacknumber()));

        btn_5.setText(String.valueOf(MF_list.get(0).getBacknumber()));
        btn_6.setText(String.valueOf(MF_list.get(1).getBacknumber()));
        btn_7.setText(String.valueOf(MF_list.get(2).getBacknumber()));
        btn_8.setText(String.valueOf(MF_list.get(3).getBacknumber()));

        btn_9.setText(String.valueOf(ATK_list.get(0).getBacknumber()));
        btn_10.setText(String.valueOf(ATK_list.get(1).getBacknumber()));
    }

    private void setBackNumber451() {
        btn_keeper.setText(String.valueOf(GK_list.get(0).getBacknumber()));
        btn_1.setText(String.valueOf(DF_list.get(0).getBacknumber()));
        btn_2.setText(String.valueOf(DF_list.get(1).getBacknumber()));
        btn_3.setText(String.valueOf(DF_list.get(2).getBacknumber()));
        btn_4.setText(String.valueOf(DF_list.get(3).getBacknumber()));

        btn_5.setText(String.valueOf(MF_list.get(0).getBacknumber()));
        btn_6.setText(String.valueOf(MF_list.get(1).getBacknumber()));
        btn_7.setText(String.valueOf(MF_list.get(2).getBacknumber()));
        btn_8.setText(String.valueOf(MF_list.get(3).getBacknumber()));
        btn_9.setText(String.valueOf(MF_list.get(4).getBacknumber()));

        btn_10.setText(String.valueOf(ATK_list.get(0).getBacknumber()));
    }

    private void setBackNumber352() {
        btn_keeper.setText(String.valueOf(GK_list.get(0).getBacknumber()));
        btn_1.setText(String.valueOf(DF_list.get(0).getBacknumber()));
        btn_2.setText(String.valueOf(DF_list.get(1).getBacknumber()));
        btn_3.setText(String.valueOf(DF_list.get(2).getBacknumber()));

        btn_4.setText(String.valueOf(MF_list.get(0).getBacknumber()));
        btn_5.setText(String.valueOf(MF_list.get(1).getBacknumber()));
        btn_6.setText(String.valueOf(MF_list.get(2).getBacknumber()));
        btn_7.setText(String.valueOf(MF_list.get(3).getBacknumber()));
        btn_8.setText(String.valueOf(MF_list.get(4).getBacknumber()));

        btn_9.setText(String.valueOf(ATK_list.get(0).getBacknumber()));
        btn_10.setText(String.valueOf(ATK_list.get(1).getBacknumber()));
    }

    private void setBackNumber433() {
        btn_keeper.setText(String.valueOf(GK_list.get(0).getBacknumber()));
        btn_1.setText(String.valueOf(DF_list.get(0).getBacknumber()));
        btn_2.setText(String.valueOf(DF_list.get(1).getBacknumber()));
        btn_3.setText(String.valueOf(DF_list.get(2).getBacknumber()));
        btn_4.setText(String.valueOf(DF_list.get(3).getBacknumber()));

        btn_5.setText(String.valueOf(MF_list.get(0).getBacknumber()));
        btn_6.setText(String.valueOf(MF_list.get(1).getBacknumber()));
        btn_7.setText(String.valueOf(MF_list.get(2).getBacknumber()));

        btn_8.setText(String.valueOf(ATK_list.get(0).getBacknumber()));
        btn_9.setText(String.valueOf(ATK_list.get(1).getBacknumber()));
        btn_10.setText(String.valueOf(ATK_list.get(2).getBacknumber()));
    }

    private void setBackNumber343() {
        btn_keeper.setText(String.valueOf(GK_list.get(0).getBacknumber()));
        btn_1.setText(String.valueOf(DF_list.get(0).getBacknumber()));
        btn_2.setText(String.valueOf(DF_list.get(1).getBacknumber()));
        btn_3.setText(String.valueOf(DF_list.get(2).getBacknumber()));

        btn_4.setText(String.valueOf(MF_list.get(0).getBacknumber()));
        btn_5.setText(String.valueOf(MF_list.get(1).getBacknumber()));
        btn_6.setText(String.valueOf(MF_list.get(2).getBacknumber()));
        btn_7.setText(String.valueOf(MF_list.get(3).getBacknumber()));

        btn_8.setText(String.valueOf(ATK_list.get(0).getBacknumber()));
        btn_9.setText(String.valueOf(ATK_list.get(1).getBacknumber()));
        btn_10.setText(String.valueOf(ATK_list.get(2).getBacknumber()));
    }


    private void init() {
        btn_keeper = (Button) v.findViewById(R.id.goal_keeper);
        btn_1 = (Button) v.findViewById(R.id.player1);
        btn_2 = (Button) v.findViewById(R.id.player2);
        btn_3 = (Button) v.findViewById(R.id.player3);
        btn_4 = (Button) v.findViewById(R.id.player4);
        btn_5 = (Button) v.findViewById(R.id.player5);
        btn_6 = (Button) v.findViewById(R.id.player6);
        btn_7 = (Button) v.findViewById(R.id.player7);
        btn_8 = (Button) v.findViewById(R.id.player8);
        btn_9 = (Button) v.findViewById(R.id.player9);
        btn_10 = (Button) v.findViewById(R.id.player10);
    }

}
