package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 7. 7..
 */

public class PlayerMonth {
    private int year;
    private int month;
    private int total_game;
    private int win_game;
    private int score;
    private int assist;
    private int score_against;

    public int getScore_against() {
        return score_against;
    }

    public void setScore_against(int score_against) {
        this.score_against = score_against;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

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
}
