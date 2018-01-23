package com.appjam.assist.assist.model.response;

import java.io.Serializable;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class TeamMonth implements Serializable {
    private int year;
    private int month;
    private float avg_score;
    private float avg_score_against;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(float avg_score) {
        this.avg_score = avg_score;
    }

    public float getAvg_score_against() {
        return avg_score_against;
    }

    public void setAvg_score_against(float avg_score_against) {
        this.avg_score_against = avg_score_against;
    }
}
