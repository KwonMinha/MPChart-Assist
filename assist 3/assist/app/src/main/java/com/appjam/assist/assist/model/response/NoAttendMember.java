package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class NoAttendMember {
    private int id;
    private String username;
    private String position;
    private int backnumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getBacknumber() {
        return backnumber;
    }

    public void setBacknumber(int backnumber) {
        this.backnumber = backnumber;
    }
}
