package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class TeamAffectResult {
    public int status;
    public TeamAffect response;

    public class TeamAffect {
        public AffectMe attend;
        public AffectNoMe noattend;
    }
}
