package com.appjam.assist.assist.model.response;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class MF_member implements Serializable {
    private int id;
    private String username;
    private int backnumber;

    public MF_member(int id, String username, int backnumber) {
        this.id = id;
        this.username = username;
        this.backnumber = backnumber;
    }

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

    public int getBacknumber() {
        return backnumber;
    }

    public void setBacknumber(int backnumber) {
        this.backnumber = backnumber;
    }
}
