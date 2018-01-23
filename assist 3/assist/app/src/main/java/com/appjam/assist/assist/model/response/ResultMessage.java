package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 6. 30..
 */

public class ResultMessage {
    public int status;
    public SignResult response;
    public String message;

    public class SignResult {
        public int id;
        public int team_id;
    }
}
