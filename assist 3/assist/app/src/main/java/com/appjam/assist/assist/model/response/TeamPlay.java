package com.appjam.assist.assist.model.response;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class TeamPlay implements Serializable {
    private int id; // 스케줄 아이디
    private String game_dt;
    private String place;
    private String against_team;
    private String message;
    private int score_team;
    private int score_against_team;
    private String tactic;
    private int attendee;
    private boolean isSchedule;

    public int getAttendee() {
        return attendee;
    }

    public void setAttendee(int attendee) {
        this.attendee = attendee;
    }

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

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    public boolean isSchedule() {
        return isSchedule;
    }

    public void setSchedule(boolean schedule) {
        isSchedule = schedule;
    }
}