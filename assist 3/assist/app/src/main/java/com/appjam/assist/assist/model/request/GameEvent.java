package com.appjam.assist.assist.model.request;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class GameEvent {
    private  String type;
    private  int player_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
