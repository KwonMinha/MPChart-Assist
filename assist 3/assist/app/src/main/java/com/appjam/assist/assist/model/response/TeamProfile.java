package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 6. 30..
 */

public class TeamProfile {
    private int id;
    private String profile_pic_url;
    private String teamname;
    private int rank;
    private int total_game;
    private int win_game;
    private int draw_game;
    private int lose_game;
    private int total_score;
    private int total_score_against;
    private String region;
    private String manager;
    private String found_dt;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getTotal_score_against() {
        return total_score_against;
    }

    public void setTotal_score_against(int total_score_against) {
        this.total_score_against = total_score_against;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getFound_dt() {
        return found_dt;
    }

    public void setFound_dt(String found_dt) {
        this.found_dt = found_dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
