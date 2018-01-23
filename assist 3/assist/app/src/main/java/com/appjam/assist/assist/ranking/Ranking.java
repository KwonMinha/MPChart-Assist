package com.appjam.assist.assist.model.response;

/**
 * Created by minha on 2017-07-05.
 */

public class Ranking {
    private int id;
    private String teamname;
    private int total_game;
    private int win_game;
    private int draw_game;
    private int lose_game;
    private int rank;

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void setTotal_game(int total_game) {
        this.total_game = total_game;
    }

    public void setWin_game(int win_game) {
        this.win_game = win_game;
    }

    public void setDraw_game(int draw_game) {
        this.draw_game = draw_game;
    }

    public void setLose_game(int lose_game) {
        this.lose_game = lose_game;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public String getTeamname() {
        return teamname;
    }

    public int getTotal_game() {
        return total_game;
    }

    public int getWin_game() {
        return win_game;
    }

    public int getDraw_game() {
        return draw_game;
    }

    public int getLose_game() {
        return lose_game;
    }

    public int getRank() {
        return rank;
    }
}
