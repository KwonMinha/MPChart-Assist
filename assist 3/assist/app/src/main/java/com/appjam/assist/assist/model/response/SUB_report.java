package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 7. 6..
 */

public class SUB_report {
    private int player_id;
    private String username;

    public SUB_report(int player_id) {
        this.player_id = player_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
