package com.appjam.assist.assist.ranking;

/**
 * Created by minha on 2017-07-04.
 */

public class Itemdata_Ranking {
    public String rank;
    public String teamname;
    public String all;
    public String win;
    public String draw;
    public String lose;
    public String rate;
    public int teamid;

    public Itemdata_Ranking(String rank, String teamname, String all, String win, String draw, String lose, String rate, int teamid) {
        this.rank = rank;
        this.teamname = teamname;
        this.all = all;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.rate = rate;
        this.teamid = teamid;
    }
}
