package com.appjam.assist.assist.data.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.appjam.assist.assist.data.ViewPagerFragment.PersonalFirstFragment;
import com.appjam.assist.assist.data.ViewPagerFragment.PersonalSecondFragment;
import com.appjam.assist.assist.data.ViewPagerFragment.PersonalThirdFragment;
import com.appjam.assist.assist.model.response.AffectMe;
import com.appjam.assist.assist.model.response.AffectNoMe;
import com.appjam.assist.assist.model.response.Player;
import com.appjam.assist.assist.model.response.PlayerMonth;
import com.appjam.assist.assist.model.response.Pos_ATK;
import com.appjam.assist.assist.model.response.Pos_DF;
import com.appjam.assist.assist.model.response.Pos_GK;
import com.appjam.assist.assist.model.response.Pos_MF;
import com.appjam.assist.assist.model.response.Pos_SUB;
import com.appjam.assist.assist.model.response.TeamAffectResult;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Player playerData;
    private AffectMe me;
    private AffectNoMe nome;
    private ArrayList<PlayerMonth> monthList;
    private Pos_ATK atk;
    private Pos_MF mf;
    private Pos_DF df;
    private Pos_GK gk;
    private Pos_SUB sub;


    public void setPlayerData(Player playerData) {
        this.playerData = playerData;
    }

    public void setMe(AffectMe me) {
        this.me = me;
    }

    public void setNome(AffectNoMe nome) {
        this.nome = nome;
    }

    public void setMonthList(ArrayList<PlayerMonth> monthList) {
        this.monthList = monthList;
    }

    public void setAtk(Pos_ATK atk) {
        this.atk = atk;
    }

    public void setMf(Pos_MF mf) {
        this.mf = mf;
    }

    public void setDf(Pos_DF df) {
        this.df = df;
    }

    public void setGk(Pos_GK gk) {
        this.gk = gk;
    }

    public void setSub(Pos_SUB sub) {
        this.sub = sub;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PersonalFirstFragment fragment1 = new PersonalFirstFragment().newInstance(playerData, me, nome);
                return fragment1;
            case 1:
                PersonalSecondFragment fragment2 = new PersonalSecondFragment().newInstance(monthList);
                return fragment2;
            case 2:
                PersonalThirdFragment fragment3 = new PersonalThirdFragment().newInstance(atk, df, gk, mf);
                return fragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
