package com.appjam.assist.assist.model.response;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class AttendListResult {
    public int status;
    public AttendList response;


    public class AttendList {
        public ArrayList<NoAttendMember> noattend;
        public ArrayList<AttendMember> attend;
    }
}