package com.appjam.assist.assist.model.response;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class Tactic implements Serializable{
    private String tactic;
    private int total_game;
    private int win_game;
    private int draw_game;
    private int lose_game;

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
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

    public int getDraw_game() {
        return draw_game;
    }

    public void setDraw_game(int draw_game) {
        this.draw_game = draw_game;
    }

    public int getLose_game() {
        return lose_game;
    }

    public void setLose_game(int lose_game) {
        this.lose_game = lose_game;
    }
}