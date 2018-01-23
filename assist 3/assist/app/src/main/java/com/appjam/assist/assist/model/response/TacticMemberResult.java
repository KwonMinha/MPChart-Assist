package com.appjam.assist.assist.model.response;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class TacticMemberResult {
    public int status;
    public TacticMember response;
    public String message;

    public class TacticMember {
        public ArrayList<ATK_member> ATK;
        public ArrayList<MF_member> MF;
        public ArrayList<DF_member> DF;
        public ArrayList<GK_member> GK;
        public ArrayList<SUB_member> SUB;
    }
}
