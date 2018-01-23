package com.appjam.assist.assist.model.request;

import com.appjam.assist.assist.model.response.ATK_member;
import com.appjam.assist.assist.model.response.ATK_report;
import com.appjam.assist.assist.model.response.DF_member;
import com.appjam.assist.assist.model.response.DF_report;
import com.appjam.assist.assist.model.response.GK_member;
import com.appjam.assist.assist.model.response.GK_report;
import com.appjam.assist.assist.model.response.MF_member;
import com.appjam.assist.assist.model.response.MF_report;
import com.appjam.assist.assist.model.response.SUB_member;
import com.appjam.assist.assist.model.response.SUB_report;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 7. 5..
 */

public class PlayerList {
    public ArrayList<ATK_report> ATK;
    public ArrayList<MF_report> MF;
    public ArrayList<DF_report> DF;
    public ArrayList<GK_report> GK;
    public ArrayList<SUB_report> SUB;
}
