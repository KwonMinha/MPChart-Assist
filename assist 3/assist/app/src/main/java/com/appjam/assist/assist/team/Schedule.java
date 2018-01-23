package com.appjam.assist.assist.team;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class Schedule {
    private int id; // 스케줄 아이디
    private String game_dt;
    private String place;
    private String against_team;
    private String message;

    public static ArrayList<Schedule> scheduleArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGame_dt() {
        return game_dt;
    }

    public void setGame_dt(String game_dt) {
        this.game_dt = game_dt;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAgainst_team() {
        return against_team;
    }

    public void setAgainst_team(String against_team) {
        this.against_team = against_team;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    String date;
//    String time;
//    String place;
//    String other;
//    String content;
//
//    public static ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
//
//    public Schedule() {
//
//    }
//
//    public Schedule(String date, String time, String place, String other, String content) {
//        this.date = date;
//        this.time = time;
//        this.place = place;
//        this.other = other;
//        this.content = content;
//    }
}
