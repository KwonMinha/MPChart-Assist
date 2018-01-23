package com.appjam.assist.assist.model.response;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 4..
 */

public class Pos_MF implements Serializable{
    private int total_game;
    private int score;
    private int assist;
    private int score_team;
    private int score_against_team;
    private int no_score_against;
    private int win_game;

    public int getTotal_game() {
        return total_game;
    }

    public void setTotal_game(int total_game) {
        this.total_game = total_game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
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

    public int getNo_score_against() {
        return no_score_against;
    }

    public void setNo_score_against(int no_score_against) {
        this.no_score_against = no_score_against;
    }

    public int getWin_game() {
        return win_game;
    }

    public void setWin_game(int win_game) {
        this.win_game = win_game;
    }
}
