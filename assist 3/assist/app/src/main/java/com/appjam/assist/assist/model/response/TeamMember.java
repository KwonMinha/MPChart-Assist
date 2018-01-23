package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class TeamMember {
    private int id;
    private int backnumber;
    private String username;
    private int age;
    private String position;
    private int total_game;
    private int score;
    private int assist;

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBacknumber() {
        return backnumber;
    }

    public void setBacknumber(int backnumber) {
        this.backnumber = backnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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
}