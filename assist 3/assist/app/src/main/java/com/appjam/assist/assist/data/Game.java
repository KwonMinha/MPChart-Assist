package com.appjam.assist.assist.data;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class Game implements Serializable{
    private int id;
    private String date;
    private String profile_url;
    private String name1;
    private String name2;
    private int score1;
    private int score2;
    private int attendee;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
}
