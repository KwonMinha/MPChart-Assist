package com.appjam.assist.assist.data;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

/**
 * Created by gominju on 2017. 7. 3..
 */

public class MyValueFormatter implements YAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return Math.round(value) + "";
    }
}
