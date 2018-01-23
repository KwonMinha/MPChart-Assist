package com.appjam.assist.assist.model.response;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class AffectNoMe implements Serializable{
    private int total_game;
    private int win_game;
    private int score_team;
    private int score_against_team;

    public int getTotal_game() {
        return total_game;
    }

    public void setTotal_game(int total_game) {
        this.total_game = total_game;
    }

    public int getWin_game() {
        return win_game;
    }

    public void setWin_game(int win_game) {
        this.win_game = win_game;
    }

    public int getScore_team() {
        return score_team;
    }

    public void setScore_team(int score_team) {
        this.score_team = score_team;
    }

    public int getScore_against_team() {
        return score_against_team;
    }

    public void setScore_against_team(int score_against_team) {
        this.score_against_team = score_against_team;
    }
}
