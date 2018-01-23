package com.appjam.assist.assist.model.response;

import java.util.ArrayList;

/**
 * Created by gominju on 2017. 7. 4..
 */

public class RankingResult {
    public int status;
    public RankingResponse response;

    public class RankingResponse {
        public ArrayList<ScoreRank> score;
        public ArrayList<AssistRank> assist;
    }
}