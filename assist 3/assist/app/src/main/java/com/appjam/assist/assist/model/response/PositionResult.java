package com.appjam.assist.assist.model.response;

/**
 * Created by gominju on 2017. 7. 4..
 */

public class PositionResult {
    public int status;
    public PositionList response;

    public class PositionList {
        public Pos_ATK ATK;
        public Pos_MF MF;
        public Pos_DF DF;
        public Pos_GK GK;
        public Pos_SUB SUB;

    }
}

